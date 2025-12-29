package com.brawl.stars.hypercharge.api.service

import com.brawl.stars.hypercharge.api.dto.response.ApiResponse
import com.brawl.stars.hypercharge.api.dto.response.CombinationDto
import com.brawl.stars.hypercharge.api.dto.response.MapCombinationResponse
import com.brawl.stars.hypercharge.domain.repository.read.StatMapCombinationRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MapCombinationService(
    private val statMapCombinationRepository: StatMapCombinationRepository
) {

    companion object {
        const val DEFAULT_MIN_GAMES = 10
        const val DEFAULT_LIMIT = 20
    }

    fun getMapCombinations(
        mapId: String,
        minGames: Int = DEFAULT_MIN_GAMES,
        limit: Int = DEFAULT_LIMIT
    ): ApiResponse<MapCombinationResponse> {
        val pageable = PageRequest.of(0, limit)

        val combinations = statMapCombinationRepository
            .findTopCombinations(mapId, minGames.toLong(), pageable)
            .map { CombinationDto.from(it) }

        return ApiResponse(
            totalCount = combinations.size,
            data = MapCombinationResponse(combinations = combinations)
        )
    }
}
