package com.brawl.stars.hypercharge.api.dto.response

import com.brawl.stars.hypercharge.domain.entity.read.StatMapCombination
import java.math.BigDecimal

data class CombinationDto(
    val combinationHash: String,
    val brawlers: List<BrawlerDto>,
    val winRate: BigDecimal,
    val totalGame: Long,
    val totalWin: Long
) {
    constructor(entity: StatMapCombination) : this(
        combinationHash = entity.brawlerIdList,
        brawlers = entity.getBrawlerDisplayNames().map { (id, name) -> BrawlerDto(id, name) },
        winRate = entity.winRate,
        totalGame = entity.totalGame,
        totalWin = entity.totalWin
    )
}

data class BrawlerDto(
    val id: String,
    val name: String
)
