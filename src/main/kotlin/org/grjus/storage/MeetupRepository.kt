package org.grjus.storage

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class MeetupRepository : PanacheRepositoryBase<MeetUpEntity, UUID> {

    fun findByName(name: String?): MeetUpEntity? {
        return find("name", name!!).firstResult()
    }
}
