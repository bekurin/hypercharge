package com.brawl.stars.hypercharge.domain.repository.write

import com.brawl.stars.hypercharge.domain.entity.write.BattleParticipantDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface BattleParticipantDetailRepository : JpaRepository<BattleParticipantDetail, Long> {

    @Modifying
    @Query("""
        DELETE FROM BattleParticipantDetail p
        WHERE p.battleLog.id IN (
            SELECT b.id FROM BattleLog b WHERE b.battleTime < :cutoffDate
        )
    """)
    fun deleteByBattleLogBattleTimeBefore(cutoffDate: LocalDateTime): Int
}
