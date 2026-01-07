package com.brawl.stars.hypercharge.api.dto.response

import com.brawl.stars.hypercharge.domain.entity.read.BrawlerCalculatedStats
import com.brawl.stars.hypercharge.domain.support.BrawlerTier.Companion.calculateBrawlerTier
import com.brawl.stars.hypercharge.domain.support.BrawlerType
import java.math.BigDecimal
import java.math.RoundingMode

data class BrawlerStatDto(
    val brawlerId: String,
    val brawlerName: String,
    val winRate: BigDecimal,
    val pickRate: BigDecimal,
    val starRate: BigDecimal,
    val totalPick: Long,
    val totalWin: Long,
    val tier: String,
    val totalStarPlayer: Long,
    val bisScore: BigDecimal,
) {
    constructor(stats: BrawlerCalculatedStats) : this(
        brawlerId = stats.brawler.brawlerId,
        brawlerName = BrawlerType.fromId(stats.brawler.brawlerId).displayName,
        winRate = calculateWinRate(stats.brawler.totalWin, stats.brawler.totalPick),
        pickRate = stats.pickRate,
        starRate = stats.starRate,
        totalPick = stats.brawler.totalPick,
        totalWin = stats.brawler.totalWin,
        tier = calculateBrawlerTier(stats.brawler.bisScore).displayName,
        totalStarPlayer = stats.brawler.totalStarPlayer,
        bisScore = stats.brawler.bisScore,
    )

    companion object {
        private fun calculateWinRate(
            totalWin: Long,
            totalPick: Long,
        ): BigDecimal {
            if (totalPick <= 0) return BigDecimal.ZERO
            return BigDecimal(totalWin * 100)
                .divide(BigDecimal(totalPick), 2, RoundingMode.HALF_UP)
        }
    }
}
