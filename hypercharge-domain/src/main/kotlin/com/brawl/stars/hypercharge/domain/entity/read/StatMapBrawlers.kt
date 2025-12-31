package com.brawl.stars.hypercharge.domain.entity.read

import java.math.BigDecimal
import java.math.RoundingMode

class StatMapBrawlers(private val values: List<StatMapBrawler>) {

    private val totalPicks: Long = values.sumOf { it.totalPick }

    fun calculateStats(): List<BrawlerCalculatedStats> {
        return values.map { brawler ->
            BrawlerCalculatedStats(
                brawler = brawler,
                pickRate = calculatePickRate(brawler),
                starRate = calculateStarRate(brawler)
            )
        }
    }

    private fun calculatePickRate(brawler: StatMapBrawler): BigDecimal {
        if (totalPicks <= 0) return BigDecimal.ZERO
        return BigDecimal(brawler.totalPick)
            .multiply(BigDecimal(100))
            .divide(BigDecimal(totalPicks), 2, RoundingMode.HALF_UP)
    }

    private fun calculateStarRate(brawler: StatMapBrawler): BigDecimal {
        if (brawler.totalPick <= 0) return BigDecimal.ZERO
        return BigDecimal(brawler.totalStarPlayer)
            .multiply(BigDecimal(100))
            .divide(BigDecimal(brawler.totalPick), 2, RoundingMode.HALF_UP)
    }
}

data class BrawlerCalculatedStats(
    val brawler: StatMapBrawler,
    val pickRate: BigDecimal,
    val starRate: BigDecimal
)
