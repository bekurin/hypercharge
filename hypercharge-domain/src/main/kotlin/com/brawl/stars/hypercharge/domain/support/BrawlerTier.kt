package com.brawl.stars.hypercharge.domain.support

import java.math.BigDecimal

enum class BrawlerTier(
    val displayName: String,
    private val description: String,
) {
    OP("OP", "승률 60% 초과"),
    TIER_1("1T", "승률 55% ~ 60%"),
    TIER_2("2T", "승률 50% ~ 55%"),
    TIER_3("3T", "승률 45% ~ 50%"),
    TIER_4("4T", "승률 45% 미만"),
    ;

    companion object {
        private val OP_THRESHOLD = BigDecimal("60.00")
        private val TIER_1_THRESHOLD = BigDecimal("55.00")
        private val TIER_2_THRESHOLD = BigDecimal("50.00")
        private val TIER_3_THRESHOLD = BigDecimal("45.00")

        fun calculateBrawlerTier(winRate: BigDecimal): BrawlerTier =
            when {
                winRate > OP_THRESHOLD -> OP
                winRate > TIER_1_THRESHOLD -> TIER_1
                winRate > TIER_2_THRESHOLD -> TIER_2
                winRate > TIER_3_THRESHOLD -> TIER_3
                else -> TIER_4
            }
    }
}
