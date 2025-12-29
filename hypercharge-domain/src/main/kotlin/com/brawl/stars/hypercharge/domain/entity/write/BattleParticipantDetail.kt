package com.brawl.stars.hypercharge.domain.entity.write

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "battle_participant_detail")
class BattleParticipantDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_log_id", nullable = false)
    val battleLog: BattleLog,

    @Column(name = "player_tag", nullable = false)
    val playerTag: String,

    @Column(name = "brawler_id", nullable = false)
    val brawlerId: String,

    @Column(name = "is_victory", nullable = false)
    val isVictory: Boolean,

    @Column(name = "team_code", nullable = false)
    val teamCode: Int,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
