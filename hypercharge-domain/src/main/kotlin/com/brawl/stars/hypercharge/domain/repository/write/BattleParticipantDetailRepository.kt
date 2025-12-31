package com.brawl.stars.hypercharge.domain.repository.write

import com.brawl.stars.hypercharge.domain.entity.write.BattleParticipantDetail
import org.springframework.data.jpa.repository.JpaRepository

interface BattleParticipantDetailRepository : JpaRepository<BattleParticipantDetail, Long>
