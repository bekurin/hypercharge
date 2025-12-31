package com.brawl.stars.hypercharge.batch.job

import com.brawl.stars.hypercharge.domain.entity.read.GameMap
import com.brawl.stars.hypercharge.domain.entity.write.BattleLog

data class ProcessedBattleData(
    val battleLog: BattleLog,
    val gameMap: GameMap,
)
