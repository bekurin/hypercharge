package com.brawl.stars.hypercharge.api.service

import com.brawl.stars.hypercharge.api.dto.response.ApiResponse
import com.brawl.stars.hypercharge.api.dto.response.BrawlerStatDto
import com.brawl.stars.hypercharge.api.support.BrawlerSortType
import com.brawl.stars.hypercharge.domain.entity.read.StatMapBrawlers
import com.brawl.stars.hypercharge.domain.repository.read.StatMapBrawlerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MapBrawlerService(
    private val statMapBrawlerRepository: StatMapBrawlerRepository
) {

    fun getMapBrawlers(
        mapId: String,
        sort: BrawlerSortType
    ): ApiResponse<BrawlerStatDto> {
        val brawlers = StatMapBrawlers(statMapBrawlerRepository.findByMapId(mapId))
        val brawlerStats = brawlers.calculateStats().map { BrawlerStatDto(it) }

        val sortedStats = when (sort) {
            BrawlerSortType.WIN_RATE -> brawlerStats.sortedByDescending { it.winRate }
            BrawlerSortType.PICK_RATE -> brawlerStats.sortedByDescending { it.pickRate }
            BrawlerSortType.STAR_RATE -> brawlerStats.sortedByDescending { it.starRate }
        }

        return ApiResponse(sortedStats)
    }
}
