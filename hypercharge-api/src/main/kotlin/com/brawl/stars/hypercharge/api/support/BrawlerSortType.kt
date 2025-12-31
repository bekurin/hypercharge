package com.brawl.stars.hypercharge.api.support

enum class BrawlerSortType(
    private val description: String
) {
    WIN_RATE("승률 기준 정렬"),
    PICK_RATE("픽률 기준 정렬"),
    STAR_RATE("스타플레이어 선정율 기준 정렬"),
}
