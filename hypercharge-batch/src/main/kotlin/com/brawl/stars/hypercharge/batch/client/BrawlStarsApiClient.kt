package com.brawl.stars.hypercharge.batch.client

import com.brawl.stars.hypercharge.batch.dto.BattleLogResponse
import com.brawl.stars.hypercharge.batch.dto.RankingResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange

interface BrawlStarsApiClient {
    @GetExchange("/rankings/{countryCode}/players")
    fun getGlobalRankings(
        @PathVariable countryCode: String,
    ): RankingResponse

    @GetExchange("/players/{playerTag}/battlelog")
    fun getBattleLogs(
        @PathVariable playerTag: String,
    ): BattleLogResponse
}
