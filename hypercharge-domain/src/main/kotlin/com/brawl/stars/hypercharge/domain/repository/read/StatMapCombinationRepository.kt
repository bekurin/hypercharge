package com.brawl.stars.hypercharge.domain.repository.read

import com.brawl.stars.hypercharge.domain.entity.read.StatMapCombination
import org.springframework.data.jpa.repository.JpaRepository

interface StatMapCombinationRepository : JpaRepository<StatMapCombination, Long> {

    fun findByMapId(mapId: String): List<StatMapCombination>

    fun findByMapIdOrderByWinRateDesc(mapId: String): List<StatMapCombination>

    fun findByMapIdAndBrawlerIdList(mapId: String, brawlerIdList: String): StatMapCombination?

    fun findByMapIdAndTotalGameGreaterThanEqualOrderByWinRateDesc(
        mapId: String,
        minGames: Long
    ): List<StatMapCombination>

    fun deleteByMapId(mapId: String)
}
