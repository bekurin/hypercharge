package com.brawl.stars.hypercharge.domain.entity.read

import com.brawl.stars.hypercharge.domain.entity.BaseEntity
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "stat_map_combination",
    indexes = [
        Index(name = "idx_stat_map_combination_map_id", columnList = "map_id")
    ]
)
class StatMapCombination(
    mapId: String,
    brawlerIdList: String
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
}
