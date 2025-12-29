package com.brawl.stars.hypercharge.api.dto.response

import com.brawl.stars.hypercharge.domain.entity.read.StatMapCombination
import com.brawl.stars.hypercharge.domain.support.BrawlerType
import java.math.BigDecimal

data class MapCombinationResponse(
    val combinations: List<CombinationDto>
)

data class CombinationDto(
    val combinationHash: String,
    val brawlers: List<BrawlerDto>,
    val winRate: BigDecimal,
    val totalGame: Long,
    val totalWin: Long
) {
    companion object {
        fun from(entity: StatMapCombination): CombinationDto {
            val brawlerIds = entity.brawlerIdList.split(":")
            val brawlers = brawlerIds.map { id ->
                BrawlerDto(
                    id = id,
                    name = BrawlerType.getDisplayName(id)
                )
            }

            return CombinationDto(
                combinationHash = entity.brawlerIdList,
                brawlers = brawlers,
                winRate = entity.winRate,
                totalGame = entity.totalGame,
                totalWin = entity.totalWin
            )
        }
    }
}

data class BrawlerDto(
    val id: String,
    val name: String
)
