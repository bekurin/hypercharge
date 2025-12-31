package com.brawl.stars.hypercharge.domain.entity.read

import com.brawl.stars.hypercharge.domain.entity.BaseEntity
import com.brawl.stars.hypercharge.domain.support.BrawlerType
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "stat_map_combination",
    indexes = [
        Index(name = "idx_stat_map_combination_map_id", columnList = "map_id"),
    ],
)
class StatMapCombination(
    mapId: String,
    brawlerIdList: String,
) : BaseEntity() {
    @Column(name = "map_id", nullable = false)
    var mapId: String = mapId
        protected set

    @Column(name = "brawler_id_list", nullable = false, columnDefinition = "TEXT")
    var brawlerIdList: String = brawlerIdList
        protected set

    @Column(name = "total_game", nullable = false)
    var totalGame: Long = 0
        protected set

    @Column(name = "total_win", nullable = false)
    var totalWin: Long = 0
        protected set

    @Column(name = "win_rate", precision = 5, scale = 2, nullable = false)
    var winRate: BigDecimal = BigDecimal.ZERO
        protected set

    fun getBrawlerDisplayNames(): List<Pair<String, String>> {
        return brawlerIdList.split(":").map { id ->
            val brawlerType = BrawlerType.fromId(id)
            id to brawlerType.displayName
        }
    }

    fun updateStats(
        totalGame: Long,
        totalWin: Long,
    ) {
        this.totalGame = totalGame
        this.totalWin = totalWin
        this.winRate = calculateWinRate()
    }

    fun addGame(isWin: Boolean) {
        this.totalGame++
        if (isWin) this.totalWin++
        this.winRate = calculateWinRate()
    }

    fun addBulkGames(
        games: Int,
        wins: Int,
    ) {
        this.totalGame += games
        this.totalWin += wins
        this.winRate = calculateWinRate()
    }

    private fun calculateWinRate(): BigDecimal {
        return if (totalGame > 0) {
            BigDecimal(totalWin * 100).divide(BigDecimal(totalGame), 2, java.math.RoundingMode.HALF_UP)
        } else {
            BigDecimal.ZERO
        }
    }
}
