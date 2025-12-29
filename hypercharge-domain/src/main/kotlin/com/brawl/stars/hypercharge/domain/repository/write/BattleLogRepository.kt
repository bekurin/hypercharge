package com.brawl.stars.hypercharge.domain.repository.write

import com.brawl.stars.hypercharge.domain.entity.write.BattleLog
import org.springframework.data.jpa.repository.JpaRepository

interface BattleLogRepository : JpaRepository<BattleLog, Long>
