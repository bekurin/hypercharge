package com.brawl.stars.hypercharge.api.service

import com.brawl.stars.hypercharge.api.dto.response.ApiResponse
import com.brawl.stars.hypercharge.api.dto.response.CombinationDto
import com.brawl.stars.hypercharge.domain.repository.read.StatMapCombinationRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MapCombinationService(
    private val statMapCombinationRepository: StatMapCombinationRepository
) {

    fun getMapCombinations(
        mapId: String,
        minGames: Int,
        limit: Int
    ): ApiResponse<CombinationDto> {
        val pageable = PageRequest.of(0, limit)
        val combinations = statMapCombinationRepository
            .findByMapIdAndTotalGameGreaterThanEqualOrderByWinRateDesc(mapId, minGames.toLong(), pageable)
            .map { CombinationDto(it) }
        return ApiResponse(combinations)
    }
}
