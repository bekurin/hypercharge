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
    val mapId: String = mapId

    @Column(name = "brawler_id", nullable = false)
    val brawlerId: String = brawlerId

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
}
