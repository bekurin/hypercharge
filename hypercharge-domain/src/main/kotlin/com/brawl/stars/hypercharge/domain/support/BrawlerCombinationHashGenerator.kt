package com.brawl.stars.hypercharge.domain.support

/**
 * 브롤러 조합의 고유 해시를 생성하는 유틸리티 클래스.
 * 휴먼 에러 방지를 위해 조합 해시 생성 로직을 단일 클래스에서 관리합니다.
 *
 * 정렬 전략: 브롤러 ID를 오름차순으로 정렬 후 ":" 구분자로 연결
 * 예시: ["SHELLY", "BULL", "COLT"] -> "BULL:COLT:SHELLY"
 */
object BrawlerCombinationHashGenerator {

    private const val DELIMITER = ":"
    private const val TEAM_SIZE = 3

    fun generate(brawlerIds: List<String>): String {
        require(brawlerIds.size == TEAM_SIZE) {
            "조합은 정확히 ${TEAM_SIZE}명의 브롤러로 구성되어야 합니다. 현재: ${brawlerIds.size}명"
        }
        return brawlerIds.sorted().joinToString(DELIMITER)
    }

    fun parse(hash: String): List<String> {
        return hash.split(DELIMITER)
    }
}
