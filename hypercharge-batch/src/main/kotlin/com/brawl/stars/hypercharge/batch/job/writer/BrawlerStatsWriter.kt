package com.brawl.stars.hypercharge.batch.job.writer

import com.brawl.stars.hypercharge.domain.entity.read.StatMapBrawler
import com.brawl.stars.hypercharge.domain.entity.write.BattleParticipants
import com.brawl.stars.hypercharge.domain.repository.read.StatMapBrawlerRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.infrastructure.item.Chunk
import org.springframework.batch.infrastructure.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class BrawlerStatsWriter(
    private val statMapBrawlerRepository: StatMapBrawlerRepository,
) : ItemWriter<List<BattleParticipants.BrawlerStatData>> {
    private val log = LoggerFactory.getLogger(BrawlerStatsWriter::class.java)

    override fun write(chunk: Chunk<out List<BattleParticipants.BrawlerStatData>>) {
        val aggregatedStats = aggregateChunkData(chunk)
        if (aggregatedStats.isEmpty()) return

        val mapIds = aggregatedStats.keys.map { it.first }.toSet()
        val existingEntities =
            statMapBrawlerRepository
                .findByMapIdIn(mapIds)
                .associateBy { it.mapId to it.brawlerId }

        val entitiesToSave = mutableListOf<StatMapBrawler>()

        aggregatedStats.forEach { (key, stats) ->
            val (mapId, brawlerId) = key
            val existing = existingEntities[key]

            if (existing != null) {
                existing.addBulkPicks(stats.picks, stats.wins, stats.starPlayers)
                entitiesToSave.add(existing)
            } else {
                val newEntity =
                    StatMapBrawler(mapId = mapId, brawlerId = brawlerId)
                        .apply { addBulkPicks(stats.picks, stats.wins, stats.starPlayers) }
                entitiesToSave.add(newEntity)
            }
        }

        statMapBrawlerRepository.saveAll(entitiesToSave)
        log.debug("Brawler stats - saved {} entities", entitiesToSave.size)
    }

    private fun aggregateChunkData(chunk: Chunk<out List<BattleParticipants.BrawlerStatData>>): Map<Pair<String, String>, AggregatedBrawlerStats> {
        val result = mutableMapOf<Pair<String, String>, AggregatedBrawlerStats>()

        chunk.items.flatten().forEach { data ->
            val key = data.mapId to data.brawlerId
            val stats = result.getOrPut(key) { AggregatedBrawlerStats() }
            stats.picks++
            if (data.isWin) stats.wins++
            if (data.isStarPlayer) stats.starPlayers++
        }

        return result
    }

    private class AggregatedBrawlerStats(
        var picks: Int = 0,
        var wins: Int = 0,
        var starPlayers: Int = 0,
    )
}
