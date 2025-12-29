package com.brawl.stars.hypercharge.domain.entity.write

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "battle_log",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_battle_log_time_star_player_map",
            columnNames = ["battle_time", "star_player_tag", "map_id"]
        )
    ]
)
class BattleLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "battle_time", nullable = false)
    val battleTime: LocalDateTime,

    @Column(name = "map_id", nullable = false)
    val mapId: String,

    @Column(name = "mode", nullable = false)
    val mode: String,

    @Column(name = "star_player_tag")
    val starPlayerTag: String? = null,

    @Column(name = "star_player_brawler_id")
    val starPlayerBrawlerId: String? = null,

    @Column(name = "duration")
    val duration: Int? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "battleLog", cascade = [CascadeType.ALL], orphanRemoval = true)
    val participants: MutableList<BattleParticipantDetail> = mutableListOf()
) {
    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }

    fun addParticipant(participant: BattleParticipantDetail) {
        participants.add(participant)
    }
}
