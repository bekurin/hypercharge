package com.brawl.stars.hypercharge.batch.job.processor

import com.brawl.stars.hypercharge.domain.entity.write.BattleLog
import com.brawl.stars.hypercharge.domain.entity.write.BattleParticipants
import org.springframework.batch.infrastructure.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class CombinationStatsProcessor : ItemProcessor<BattleLog, List<BattleParticipants.TeamCombinationData>> {

    override fun process(item: BattleLog): List<BattleParticipants.TeamCombinationData>? {
        if (item.participants.isEmpty()) {
            return null
        }

        val battleParticipants = BattleParticipants(
            participants = item.participants,
            starPlayerBrawlerId = item.starPlayerBrawlerId
        )

        val combinations = battleParticipants.extractTeamCombinations(item.mapId)
        return combinations.ifEmpty { null }
    }
}
