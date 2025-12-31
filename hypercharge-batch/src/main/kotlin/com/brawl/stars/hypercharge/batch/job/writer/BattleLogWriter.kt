package com.brawl.stars.hypercharge.batch.job.writer

import com.brawl.stars.hypercharge.batch.job.ProcessedBattleData
import com.brawl.stars.hypercharge.domain.repository.read.GameMapRepository
import com.brawl.stars.hypercharge.domain.repository.write.BattleLogRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.infrastructure.item.Chunk
import org.springframework.batch.infrastructure.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class BattleLogWriter(
    private val battleLogRepository: BattleLogRepository,
    private val gameMapRepository: GameMapRepository,
) : ItemWriter<List<ProcessedBattleData>> {
    private val log = LoggerFactory.getLogger(BattleLogWriter::class.java)

    override fun write(chunk: Chunk<out List<ProcessedBattleData>>) {
        val allBattleData = chunk.items.flatten()

        saveGameMaps(allBattleData)
        saveBattleLogs(allBattleData)
    }

    private fun saveGameMaps(allBattleData: List<ProcessedBattleData>) {
        val uniqueMaps =
            allBattleData
                .map { it.gameMap }
                .distinctBy { it.id }

        uniqueMaps.forEach { gameMap ->
            if (!gameMapRepository.existsById(gameMap.id)) {
                gameMapRepository.save(gameMap)
                log.debug("Saved new map: id={}, name={}", gameMap.id, gameMap.name)
            }
        }
    }

    private fun saveBattleLogs(allBattleData: List<ProcessedBattleData>) {
        allBattleData.forEach { processedData ->
            val battleLog = processedData.battleLog
            val exists =
                battleLogRepository.existsByBattleTimeAndStarPlayerTagAndMapId(
                    battleLog.battleTime,
                    battleLog.starPlayerTag,
                    battleLog.mapId,
                )

            if (exists) {
                log.debug(
                    "Duplicate battle log skipped: battleTime={}, mapId={}, starPlayerTag={}",
                    battleLog.battleTime,
                    battleLog.mapId,
                    battleLog.starPlayerTag,
                )
            } else {
                battleLogRepository.save(battleLog)
            }
        }
    }
}
