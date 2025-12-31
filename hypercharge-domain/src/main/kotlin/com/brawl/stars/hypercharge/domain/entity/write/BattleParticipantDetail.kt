package com.brawl.stars.hypercharge.domain.entity.write

import com.brawl.stars.hypercharge.domain.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "battle_participant_detail")
class BattleParticipantDetail(
    battleLog: BattleLog,
    playerTag: String,
    brawlerId: String,
    isVictory: Boolean,
    teamCode: Int,
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_log_id", nullable = false)
    var battleLog: BattleLog = battleLog
        protected set

    @Column(name = "player_tag", nullable = false)
    var playerTag: String = playerTag
        protected set

    @Column(name = "brawler_id", nullable = false)
    var brawlerId: String = brawlerId
        protected set

    @Column(name = "is_victory", nullable = false)
    var isVictory: Boolean = isVictory
        protected set

    @Column(name = "team_code", nullable = false)
    var teamCode: Int = teamCode
        protected set
}
