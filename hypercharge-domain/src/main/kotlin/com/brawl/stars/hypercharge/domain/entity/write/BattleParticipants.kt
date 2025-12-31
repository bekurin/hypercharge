package com.brawl.stars.hypercharge.domain.entity.write

import com.brawl.stars.hypercharge.domain.support.BrawlerCombinationHashGenerator

class BattleParticipants(
    private val participants: List<BattleParticipantDetail>,
    private val starPlayerBrawlerId: String?,
) {
    companion object {
        private const val TEAM_SIZE = 3
        private const val TEAM_BLUE = 0
        private const val TEAM_RED = 1
    }

    fun extractBrawlerStats(mapId: String): List<BrawlerStatData> {
        return participants.map { participant ->
            BrawlerStatData(
                mapId = mapId,
                brawlerId = participant.brawlerId,
                isWin = participant.isVictory,
                isStarPlayer = participant.brawlerId == starPlayerBrawlerId,
            )
        }
    }

    fun extractTeamCombinations(mapId: String): List<TeamCombinationData> {
        val result = mutableListOf<TeamCombinationData>()

        val blueTeam = participants.filter { it.teamCode == TEAM_BLUE }
        val redTeam = participants.filter { it.teamCode == TEAM_RED }

        if (blueTeam.size == TEAM_SIZE) {
            result.add(createCombinationData(mapId, blueTeam))
        }

        if (redTeam.size == TEAM_SIZE) {
            result.add(createCombinationData(mapId, redTeam))
        }

        return result
    }

    private fun createCombinationData(
        mapId: String,
        team: List<BattleParticipantDetail>,
    ): TeamCombinationData {
        val brawlerIds = team.map { it.brawlerId }
        val hash = BrawlerCombinationHashGenerator.generate(brawlerIds)
        val isWin = team.first().isVictory
        return TeamCombinationData(mapId, hash, isWin)
    }

    data class BrawlerStatData(
        val mapId: String,
        val brawlerId: String,
        val isWin: Boolean,
        val isStarPlayer: Boolean,
    )

    data class TeamCombinationData(
        val mapId: String,
        val brawlerIdList: String,
        val isWin: Boolean,
    )
}
