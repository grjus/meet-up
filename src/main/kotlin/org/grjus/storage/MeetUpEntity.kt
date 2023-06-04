package org.grjus.storage

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "meet_up")
class MeetUpEntity(
    @Column(unique = true)
    val name: String,
    val description: String?
) {

    @Id
    val uuid: UUID = UUID.randomUUID()

    @Column(name = "created_at")
    val createdAt = LocalDate.now()

    @OneToMany(mappedBy = "meetup", cascade = [CascadeType.ALL])
    val meetings: MutableList<MeetingEntity> = mutableListOf()
}

data class MeetupDto(val name: String, val description: String?)

fun MeetUpEntity.toDto(): MeetupDto = MeetupDto(this.name, this.description)
