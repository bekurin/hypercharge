package com.brawl.stars.hypercharge.api.controller

import com.brawl.stars.hypercharge.api.dto.response.ApiResponse
import com.brawl.stars.hypercharge.api.dto.response.BrawlerStatDto
import com.brawl.stars.hypercharge.api.dto.response.CombinationDto
import com.brawl.stars.hypercharge.api.service.MapStatService
import com.brawl.stars.hypercharge.api.support.BrawlerSortType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/maps")
class MapController(
    private val mapStatService: MapStatService
) {

    @GetMapping("/{mapId}/combinations")
    fun getMapCombinations(
        @PathVariable mapId: String,
        @RequestParam(defaultValue = "10") minGames: Int,
        @RequestParam(defaultValue = "20") limit: Int
    ): ResponseEntity<ApiResponse<CombinationDto>> {
        val response = mapStatService.getMapCombinations(mapId, minGames, limit)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{mapId}/brawlers")
    fun getMapBrawlers(
        @PathVariable mapId: String,
        @RequestParam(defaultValue = "WIN_RATE") sort: BrawlerSortType
    ): ResponseEntity<ApiResponse<BrawlerStatDto>> {
        val response = mapStatService.getMapBrawlers(mapId, sort)
        return ResponseEntity.ok(response)
    }
}
