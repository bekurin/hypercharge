package com.brawl.stars.hypercharge.batch.job.processor

import com.brawl.stars.hypercharge.domain.entity.write.BattleLog
import com.brawl.stars.hypercharge.domain.entity.write.BattleParticipants
import org.springframework.batch.infrastructure.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class BrawlerStatsProcessor : ItemProcessor<BattleLog, List<BattleParticipants.BrawlerStatData>> {

    override fun process(item: BattleLog): List<BattleParticipants.BrawlerStatData>? {
        if (item.participants.isEmpty()) {
            return null
        }

        val battleParticipants = BattleParticipants(
            participants = item.participants,
            starPlayerBrawlerId = item.starPlayerBrawlerId
        )

        val stats = battleParticipants.extractBrawlerStats(item.mapId)
        return stats.ifEmpty { null }
    }
}
