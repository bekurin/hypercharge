package com.brawl.stars.hypercharge.api.controller

import com.brawl.stars.hypercharge.api.dto.response.ApiResponse
import com.brawl.stars.hypercharge.api.dto.response.CombinationDto
import com.brawl.stars.hypercharge.api.service.MapCombinationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/maps")
class MapController(
    private val mapCombinationService: MapCombinationService
) {

    @GetMapping("/{mapId}/combinations")
    fun getMapCombinations(
        @PathVariable mapId: String,
        @RequestParam(defaultValue = "10") minGames: Int,
        @RequestParam(defaultValue = "20") limit: Int
    ): ResponseEntity<ApiResponse<CombinationDto>> {
        val response = mapCombinationService.getMapCombinations(mapId, minGames, limit)
        return ResponseEntity.ok(response)
    }
}
