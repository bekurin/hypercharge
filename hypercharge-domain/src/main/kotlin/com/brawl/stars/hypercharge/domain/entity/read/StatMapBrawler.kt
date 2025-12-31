package com.brawl.stars.hypercharge.domain.entity.read

import com.brawl.stars.hypercharge.domain.entity.BaseEntity
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "stat_map_brawler",
    indexes = [
        Index(name = "idx_stat_map_brawler_map_id", columnList = "map_id"),
        Index(name = "idx_stat_map_brawler_brawler_id", columnList = "brawler_id")
    ]
)
class StatMapBrawler(
    mapId: String,
    brawlerId: String
) : BaseEntity() {

    @Column(name = "map_id", nullable = false)
    var mapId: String = mapId
        protected set

    @Column(name = "brawler_id", nullable = false)
    var brawlerId: String = brawlerId
        protected set

    @Column(name = "total_pick", nullable = false)
    var totalPick: Long = 0
        protected set

    @Column(name = "total_win", nullable = false)
    var totalWin: Long = 0
        protected set

    @Column(name = "total_star_player", nullable = false)
    var totalStarPlayer: Long = 0
        protected set

    @Column(name = "win_rate", precision = 5, scale = 2, nullable = false)
    var winRate: BigDecimal = BigDecimal.ZERO
        protected set

    fun updateStats(totalPick: Long, totalWin: Long, totalStarPlayer: Long) {
        this.totalPick = totalPick
        this.totalWin = totalWin
        this.totalStarPlayer = totalStarPlayer
        this.winRate = calculateWinRate()
    }

    fun addPick(isWin: Boolean, isStarPlayer: Boolean) {
        this.totalPick++
        if (isWin) this.totalWin++
        if (isStarPlayer) this.totalStarPlayer++
        this.winRate = calculateWinRate()
    }

    fun addBulkPicks(picks: Int, wins: Int, starPlayers: Int) {
        this.totalPick += picks
        this.totalWin += wins
        this.totalStarPlayer += starPlayers
        this.winRate = calculateWinRate()
    }

    private fun calculateWinRate(): BigDecimal {
        return if (totalPick > 0) {
            BigDecimal(totalWin * 100).divide(BigDecimal(totalPick), 2, java.math.RoundingMode.HALF_UP)
        } else {
            BigDecimal.ZERO
        }
    }
}
