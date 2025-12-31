package com.brawl.stars.hypercharge.domain.entity.write

import com.brawl.stars.hypercharge.domain.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "battle_log",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_battle_log_time_star_player_map",
            columnNames = ["battle_time", "star_player_tag", "map_id"],
        ),
    ],
)
class BattleLog(
    battleTime: LocalDateTime,
    mapId: String,
    mode: String,
    starPlayerTag: String? = null,
    starPlayerBrawlerId: String? = null,
    duration: Int? = null,
) : BaseEntity() {
    @Column(name = "battle_time", nullable = false)
    var battleTime: LocalDateTime = battleTime
        protected set

    @Column(name = "map_id", nullable = false)
    var mapId: String = mapId
        protected set

    @Column(name = "mode", nullable = false)
    var mode: String = mode
        protected set

    @Column(name = "star_player_tag")
    var starPlayerTag: String? = starPlayerTag
        protected set

    @Column(name = "star_player_brawler_id")
    var starPlayerBrawlerId: String? = starPlayerBrawlerId
        protected set

    @Column(name = "duration")
    var duration: Int? = duration
        protected set

    @OneToMany(mappedBy = "battleLog", cascade = [CascadeType.ALL], orphanRemoval = true)
    var participants: MutableList<BattleParticipantDetail> = mutableListOf()
        protected set

    fun addParticipant(participant: BattleParticipantDetail) {
        participants.add(participant)
    }
}
