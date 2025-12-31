package com.brawl.stars.hypercharge.api.dto.response

import org.springframework.data.domain.Page

data class PageResponse<T : Any>(
    val totalCount: Long,
    val totalPages: Int,
    val currentPage: Int,
    val size: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val isFirst: Boolean,
    val isLast: Boolean,
    val data: List<T>
) {
    constructor(page: Page<T>) : this(
        totalCount = page.totalElements,
        totalPages = page.totalPages,
        currentPage = page.number,
        size = page.size,
        hasNext = page.hasNext(),
        hasPrevious = page.hasPrevious(),
        isFirst = page.isFirst,
        isLast = page.isLast,
        data = page.content
    )
}
