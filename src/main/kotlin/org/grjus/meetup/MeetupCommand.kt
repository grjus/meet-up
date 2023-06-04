package org.grjus.meetup

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.grjus.storage.MeetUpEntity
import org.grjus.storage.MeetupRepository
import java.net.URI
import java.util.UUID

@ApplicationScoped
class MeetupCommand(private val meetupRepository: MeetupRepository) {
    @Transactional
    fun create(name: String, description: String): UUID {
        check(meetupRepository.findByName(name) == null) {
            "Meetup with that name already exists"
        }
        val meetupEntity = MeetUpEntity(name, description)
        meetupRepository.persist(meetupEntity)
        return meetupEntity.uuid
    }
}

@Path("/meetup")
class MeetupCommandController(private val meetupCommand: MeetupCommand) {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun createMeetup(command: CreateMeetuoCommand): Response {
        return try {
            val uuid = with(command) {
                meetupCommand.create(this.name, this.description)
            }
            Response.created(URI("/meetup/$uuid")).build()
        } catch (e: IllegalStateException) {
            Response.status(409, "Meetup already exists").build()
        }
    }
}

data class CreateMeetuoCommand(
    val name: String,
    val description: String
)
