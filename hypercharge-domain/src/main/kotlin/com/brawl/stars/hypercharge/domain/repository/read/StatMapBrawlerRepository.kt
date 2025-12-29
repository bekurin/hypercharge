package com.brawl.stars.hypercharge.domain.repository.read

import com.brawl.stars.hypercharge.domain.entity.read.StatMapBrawler
import org.springframework.data.jpa.repository.JpaRepository

interface StatMapBrawlerRepository : JpaRepository<StatMapBrawler, Long> {

    fun findByMapId(mapId: String): List<StatMapBrawler>

    fun findByMapIdOrderByWinRateDesc(mapId: String): List<StatMapBrawler>

    fun findByMapIdAndBrawlerId(mapId: String, brawlerId: String): StatMapBrawler?

    fun deleteByMapId(mapId: String)
}
