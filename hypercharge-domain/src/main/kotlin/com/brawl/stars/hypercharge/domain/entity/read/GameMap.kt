package com.brawl.stars.hypercharge.domain.entity.read

import com.brawl.stars.hypercharge.domain.entity.TimestampBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "map")
class GameMap(
    id: Long,
    name: String,
    mode: String,
) : TimestampBaseEntity() {
    @Id
    @Column(name = "id")
    val id: Long = id

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "mode", nullable = false)
    var mode: String = mode
        protected set
}
