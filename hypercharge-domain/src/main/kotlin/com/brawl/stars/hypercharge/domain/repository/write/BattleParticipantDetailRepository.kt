package com.brawl.stars.hypercharge.domain.repository.write

import com.brawl.stars.hypercharge.domain.entity.write.BattleParticipantDetail
import org.springframework.data.jpa.repository.JpaRepository

interface BattleParticipantDetailRepository : JpaRepository<BattleParticipantDetail, Long> {

    fun findByBattleLogId(battleLogId: Long): List<BattleParticipantDetail>

    fun findByPlayerTag(playerTag: String): List<BattleParticipantDetail>

    fun deleteByBattleLogId(battleLogId: Long)
}
