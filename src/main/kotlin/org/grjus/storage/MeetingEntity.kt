package org.grjus.storage

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "meeting")
class MeetingEntity(
    val topic: String?,
    @ManyToOne val meetUp: MeetUpEntity
) {
    @Id
    val uuid: UUID = UUID.randomUUID()

    @Column(name = "created_at")
    val createdAt: LocalDate = LocalDate.now()
}
