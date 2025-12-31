package com.brawl.stars.hypercharge.api.dto.response

data class ApiResponse<T>(
    val totalCount: Int,
    val data: List<T>,
) {
    constructor(data: List<T>) : this(
        totalCount = data.size,
        data = data,
    )
}
