package com.brawl.stars.hypercharge.domain.repository.write

import com.brawl.stars.hypercharge.domain.entity.write.BattleLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface BattleLogRepository : JpaRepository<BattleLog, Long> {

    fun existsByBattleTimeAndStarPlayerTagAndMapId(
        battleTime: LocalDateTime,
        starPlayerTag: String?,
        mapId: String
    ): Boolean

    fun countByBattleTimeBefore(cutoffDate: LocalDateTime): Long

    @Modifying
    @Query("DELETE FROM BattleLog b WHERE b.battleTime < :cutoffDate")
    fun deleteByBattleTimeBefore(cutoffDate: LocalDateTime): Int
}
