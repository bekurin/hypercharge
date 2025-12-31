package com.brawl.stars.hypercharge.api.service

import com.brawl.stars.hypercharge.api.dto.response.MapDto
import com.brawl.stars.hypercharge.api.dto.response.PageResponse
import com.brawl.stars.hypercharge.domain.repository.read.GameMapRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MapListService(
    private val gameMapRepository: GameMapRepository,
) {
    fun getMaps(
        page: Int,
        size: Int,
    ): PageResponse<MapDto> {
        val pageable = PageRequest.of(page, size)
        val mapPage = gameMapRepository.findAllByOrderByUpdatedAtDesc(pageable)
        return PageResponse(mapPage.map { MapDto(it) })
    }
}
