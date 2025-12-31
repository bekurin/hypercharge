package com.brawl.stars.hypercharge.batch.dto

data class RankingResponse(
    val items: List<RankedPlayer>,
)

data class RankedPlayer(
    val tag: String,
    val name: String,
    val trophies: Int,
    val rank: Int,
)

data class BattleLogResponse(
    val items: List<BattleLogItem>,
)

data class BattleLogItem(
    val battleTime: String,
    val event: BattleEvent,
    val battle: Battle,
)

data class BattleEvent(
    val id: Long?,
    val mode: String?,
    val map: String?,
)

data class Battle(
    val mode: String?,
    val type: String?,
    val result: String?,
    val duration: Int?,
    val starPlayer: BattlePlayer?,
    val teams: List<List<BattlePlayer>>?,
)

data class BattlePlayer(
    val tag: String,
    val name: String,
    val brawler: Brawler,
)

data class Brawler(
    val id: Long,
    val name: String,
    val power: Int?,
    val trophies: Int?,
)
