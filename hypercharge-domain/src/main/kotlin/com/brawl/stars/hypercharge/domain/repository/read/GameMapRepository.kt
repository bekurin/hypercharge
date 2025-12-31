package com.brawl.stars.hypercharge.domain.repository.read

import com.brawl.stars.hypercharge.domain.entity.read.GameMap
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface GameMapRepository : JpaRepository<GameMap, Long> {
    fun findAllByOrderByUpdatedAtDesc(pageable: Pageable): Page<GameMap>
}
