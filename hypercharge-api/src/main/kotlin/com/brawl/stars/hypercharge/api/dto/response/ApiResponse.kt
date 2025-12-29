package com.brawl.stars.hypercharge.api.dto.response

data class ApiResponse<T>(
    val totalCount: Int,
    val data: T
)
