package com.brawl.stars.hypercharge.batch.job.processor

import com.brawl.stars.hypercharge.batch.client.BrawlStarsApiClient
import com.brawl.stars.hypercharge.batch.dto.BattleLogItem
import com.brawl.stars.hypercharge.batch.dto.RankedPlayer
import com.brawl.stars.hypercharge.batch.job.ProcessedBattleData
import com.brawl.stars.hypercharge.domain.entity.write.BattleLog
import com.brawl.stars.hypercharge.domain.entity.write.BattleParticipantDetail
import org.slf4j.LoggerFactory
import org.springframework.batch.infrastructure.item.ItemProcessor
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class BattleLogProcessor(
    private val brawlStarsApiClient: BrawlStarsApiClient,
) : ItemProcessor<RankedPlayer, List<ProcessedBattleData>> {
    private val log = LoggerFactory.getLogger(BattleLogProcessor::class.java)
    private val battleTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSS'Z'")

    override fun process(player: RankedPlayer): List<ProcessedBattleData>? {
        return try {
            val battleLogResponse = brawlStarsApiClient.getBattleLogs(player.tag)
            val processedData =
                battleLogResponse.items
                    .filter { isValidBattleLog(it) }
                    .mapNotNull { convertToBattleData(it) }

            log.info("Processed ${processedData.size} battle logs for player: ${player.name}")
            processedData.ifEmpty { null }
        } catch (e: Exception) {
            log.error("Failed to process battle logs for player: ${player.tag}", e)
            null
        }
    }

    private fun isValidBattleLog(battleLogItem: BattleLogItem): Boolean {
        return battleLogItem.event.id != null &&
            battleLogItem.event.map != null &&
            battleLogItem.event.mode != null &&
            battleLogItem.battle.teams != null &&
            battleLogItem.battle.result != null &&
            battleLogItem.battle.type == "ranked"
    }

    private fun convertToBattleData(battleLogItem: BattleLogItem): ProcessedBattleData? {
        val battleTime = parseBattleTime(battleLogItem.battleTime) ?: return null

        val battleLog =
            BattleLog(
                battleTime = battleTime,
                mapId = battleLogItem.event.id.toString(),
                mode = battleLogItem.event.mode ?: return null,
                starPlayerTag = battleLogItem.battle.starPlayer?.tag,
                starPlayerBrawlerId =
                    battleLogItem.battle.starPlayer
                        ?.brawler
                        ?.name,
                duration = battleLogItem.battle.duration,
            )

        addParticipants(battleLog, battleLogItem)

        return ProcessedBattleData(battleLog)
    }

    private fun parseBattleTime(battleTimeStr: String): LocalDateTime? {
        return try {
            LocalDateTime.parse(battleTimeStr, battleTimeFormatter)
        } catch (e: Exception) {
            log.warn("Failed to parse battle time: $battleTimeStr")
            null
        }
    }

    private fun addParticipants(
        battleLog: BattleLog,
        battleLogItem: BattleLogItem,
    ) {
        val teams = battleLogItem.battle.teams ?: return
        val result = battleLogItem.battle.result ?: return

        teams.forEachIndexed { teamIndex, team ->
            val isVictory =
                when {
                    result == "victory" && teamIndex == 0 -> true
                    result == "defeat" && teamIndex == 1 -> true
                    else -> false
                }

            team.forEach { player ->
                val participant =
                    BattleParticipantDetail(
                        battleLog = battleLog,
                        playerTag = player.tag,
                        brawlerId = player.brawler.name,
                        isVictory = isVictory,
                        teamCode = teamIndex,
                    )
                battleLog.addParticipant(participant)
            }
        }
    }
}
