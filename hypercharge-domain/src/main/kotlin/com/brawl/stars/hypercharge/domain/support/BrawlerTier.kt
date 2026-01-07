package com.brawl.stars.hypercharge.domain.support

import java.math.BigDecimal

enum class BrawlerTier(
    val displayName: String,
    private val description: String,
) {
    OP("OP", "BIS 점수 50점 이상"),
    TIER_1("1T", "BIS 점수 45 ~ 50점"),
    TIER_2("2T", "BIS 점수 40 ~ 45점"),
    TIER_3("3T", "BIS 점수 35 ~ 40점"),
    TIER_4("4T", "BIS 점수 35점 미만"),
    ;

    companion object {
        private val OP_BIS_THRESHOLD = BigDecimal("50.00")
        private val TIER_1_BIS_THRESHOLD = BigDecimal("45.00")
        private val TIER_2_BIS_THRESHOLD = BigDecimal("40.00")
        private val TIER_3_BIS_THRESHOLD = BigDecimal("35.00")

        fun calculateBrawlerTier(bisScore: BigDecimal): BrawlerTier =
            when {
                bisScore >= OP_BIS_THRESHOLD -> OP
                bisScore >= TIER_1_BIS_THRESHOLD -> TIER_1
                bisScore >= TIER_2_BIS_THRESHOLD -> TIER_2
                bisScore >= TIER_3_BIS_THRESHOLD -> TIER_3
                else -> TIER_4
            }
    }
}
