package com.brawl.stars.hypercharge.api.service

import com.brawl.stars.hypercharge.api.dto.response.ApiResponse
import com.brawl.stars.hypercharge.api.dto.response.BrawlerStatDto
import com.brawl.stars.hypercharge.api.dto.response.CombinationDto
import com.brawl.stars.hypercharge.api.dto.response.MapDto
import com.brawl.stars.hypercharge.api.dto.response.PageResponse
import com.brawl.stars.hypercharge.api.support.BrawlerSortType
import org.springframework.stereotype.Service

@Service
class MapStatService(
    private val mapListService: MapListService,
    private val mapBrawlerService: MapBrawlerService,
    private val mapCombinationService: MapCombinationService,
) {
    fun getMaps(
        page: Int,
        size: Int,
    ): PageResponse<MapDto> {
        return mapListService.getMaps(page, size)
    }

    fun getMapBrawlers(
        mapId: String,
        sort: BrawlerSortType,
    ): ApiResponse<BrawlerStatDto> {
        return mapBrawlerService.getMapBrawlers(mapId, sort)
    }

    fun getMapCombinations(
        mapId: String,
        minGames: Int,
        limit: Int,
    ): ApiResponse<CombinationDto> {
        return mapCombinationService.getMapCombinations(mapId, minGames, limit)
    }
}
