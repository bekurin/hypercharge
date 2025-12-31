package com.brawl.stars.hypercharge.batch.job.writer

import com.brawl.stars.hypercharge.domain.entity.read.StatMapCombination
import com.brawl.stars.hypercharge.domain.entity.write.BattleParticipants
import com.brawl.stars.hypercharge.domain.repository.read.StatMapCombinationRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.infrastructure.item.Chunk
import org.springframework.batch.infrastructure.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class CombinationStatsWriter(
    private val statMapCombinationRepository: StatMapCombinationRepository
) : ItemWriter<List<BattleParticipants.TeamCombinationData>> {

    private val log = LoggerFactory.getLogger(CombinationStatsWriter::class.java)

    override fun write(chunk: Chunk<out List<BattleParticipants.TeamCombinationData>>) {
        val aggregatedStats = aggregateChunkData(chunk)
        if (aggregatedStats.isEmpty()) return

        val mapIds = aggregatedStats.keys.map { it.first }.toSet()
        val existingEntities = statMapCombinationRepository.findByMapIdIn(mapIds)
            .associateBy { it.mapId to it.brawlerIdList }

        val entitiesToSave = mutableListOf<StatMapCombination>()

        aggregatedStats.forEach { (key, stats) ->
            val (mapId, brawlerIdList) = key
            val existing = existingEntities[key]

            if (existing != null) {
                existing.addBulkGames(stats.games, stats.wins)
                entitiesToSave.add(existing)
            } else {
                val newEntity = StatMapCombination(mapId = mapId, brawlerIdList = brawlerIdList)
                    .apply { addBulkGames(stats.games, stats.wins) }
                entitiesToSave.add(newEntity)
            }
        }

        statMapCombinationRepository.saveAll(entitiesToSave)
        log.debug("Combination stats - saved {} entities", entitiesToSave.size)
    }

    private fun aggregateChunkData(
        chunk: Chunk<out List<BattleParticipants.TeamCombinationData>>
    ): Map<Pair<String, String>, AggregatedCombinationStats> {
        val result = mutableMapOf<Pair<String, String>, AggregatedCombinationStats>()

        chunk.items.flatten().forEach { data ->
            val key = data.mapId to data.brawlerIdList
            val stats = result.getOrPut(key) { AggregatedCombinationStats() }
            stats.games++
            if (data.isWin) stats.wins++
        }

        return result
    }

    private class AggregatedCombinationStats(
        var games: Int = 0,
        var wins: Int = 0
    )
}
