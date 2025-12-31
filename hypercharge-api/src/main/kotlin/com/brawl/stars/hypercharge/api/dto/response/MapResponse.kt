package com.brawl.stars.hypercharge.api.dto.response

import com.brawl.stars.hypercharge.domain.entity.read.GameMap
import java.time.LocalDateTime

data class MapDto(
    val mapId: Long,
    val mapName: String,
    val mode: String,
    val lastUpdatedAt: LocalDateTime
) {
    constructor(entity: GameMap) : this(
        mapId = entity.id,
        mapName = entity.name,
        mode = entity.mode,
        lastUpdatedAt = entity.updatedAt
    )
}
