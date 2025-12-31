package com.brawl.stars.hypercharge.api.dto.response

import com.brawl.stars.hypercharge.domain.entity.read.BrawlerCalculatedStats
import com.brawl.stars.hypercharge.domain.support.BrawlerTier.Companion.calculateBrawlerTier
import com.brawl.stars.hypercharge.domain.support.BrawlerType
import java.math.BigDecimal

data class BrawlerStatDto(
    val brawlerId: String,
    val brawlerName: String,
    val winRate: BigDecimal,
    val pickRate: BigDecimal,
    val starRate: BigDecimal,
    val totalPick: Long,
    val totalWin: Long,
    val tier: String,
    val totalStarPlayer: Long
) {
    constructor(stats: BrawlerCalculatedStats) : this(
        brawlerId = stats.brawler.brawlerId,
        brawlerName = BrawlerType.fromId(stats.brawler.brawlerId).displayName,
        winRate = stats.brawler.winRate,
        pickRate = stats.pickRate,
        starRate = stats.starRate,
        totalPick = stats.brawler.totalPick,
        totalWin = stats.brawler.totalWin,
        tier = calculateBrawlerTier(stats.brawler.winRate).displayName,
        totalStarPlayer = stats.brawler.totalStarPlayer
    )
}
