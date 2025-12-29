package com.brawl.stars.hypercharge.domain.entity.read

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(
    name = "stat_map_brawler",
    indexes = [
        Index(name = "idx_stat_map_brawler_map_id", columnList = "map_id"),
        Index(name = "idx_stat_map_brawler_brawler_id", columnList = "brawler_id")
    ]
)
class StatMapBrawler(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "map_id", nullable = false)
    val mapId: String,

    @Column(name = "brawler_id", nullable = false)
    val brawlerId: String,

    @Column(name = "total_pick", nullable = false)
    var totalPick: Long = 0,

    @Column(name = "total_win", nullable = false)
    var totalWin: Long = 0,

    @Column(name = "total_star_player", nullable = false)
    var totalStarPlayer: Long = 0,

    @Column(name = "win_rate", precision = 5, scale = 2, nullable = false)
    var winRate: BigDecimal = BigDecimal.ZERO,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
