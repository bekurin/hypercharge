package com.brawl.stars.hypercharge.domain.repository.write

import com.brawl.stars.hypercharge.domain.entity.write.BattleLog
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface BattleLogRepository : JpaRepository<BattleLog, Long> {

    fun existsByBattleTimeAndStarPlayerTagAndMapId(
        battleTime: LocalDateTime,
        starPlayerTag: String?,
        mapId: String
    ): Boolean
}
