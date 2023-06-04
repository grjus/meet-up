package org.grjus.storage

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class MeetingRepository : PanacheRepositoryBase<MeetingEntity, UUID>
