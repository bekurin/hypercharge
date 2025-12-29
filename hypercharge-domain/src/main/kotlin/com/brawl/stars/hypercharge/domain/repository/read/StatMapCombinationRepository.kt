package com.brawl.stars.hypercharge.domain.repository.read

import com.brawl.stars.hypercharge.domain.entity.read.StatMapCombination
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface StatMapCombinationRepository : JpaRepository<StatMapCombination, Long> {

    fun findByMapId(mapId: String): List<StatMapCombination>

    @Query(
        """
        SELECT s FROM StatMapCombination s
        WHERE s.mapId = :mapId AND s.totalGame >= :minGames
        ORDER BY s.winRate DESC
        """
    )
    fun findTopCombinations(
        @Param("mapId") mapId: String,
        @Param("minGames") minGames: Long,
        pageable: Pageable
    ): List<StatMapCombination>
}
