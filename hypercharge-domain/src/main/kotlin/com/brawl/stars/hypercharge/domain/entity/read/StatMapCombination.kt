package com.brawl.stars.hypercharge.domain.entity.read

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(
    name = "stat_map_combination",
    indexes = [
        Index(name = "idx_stat_map_combination_map_id", columnList = "map_id")
    ]
)
class StatMapCombination(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "map_id", nullable = false)
    val mapId: String,

    @Column(name = "brawler_id_list", nullable = false, columnDefinition = "TEXT")
    val brawlerIdList: String,

    @Column(name = "total_game", nullable = false)
    var totalGame: Long = 0,

    @Column(name = "total_win", nullable = false)
    var totalWin: Long = 0,

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
