package org.grjus.meeting

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.Response
import org.grjus.storage.MeetingEntity
import org.grjus.storage.MeetingRepository
import org.grjus.storage.MeetupRepository
import java.net.URI
import java.util.UUID

@ApplicationScoped
class MeetingCommand(
    private val meetingRepository: MeetingRepository,
    private val meetupRepository: MeetupRepository
) {

    fun createMeeting(topic: String?, meetupName: String): UUID {
        val meetupEntity = checkNotNull(meetupRepository.findByName(meetupName))
        val meeting = MeetingEntity(topic, meetupEntity)
        meetingRepository.persist(meeting)
        return meeting.uuid
    }
}

@Path("/meetup/{meetupName}")
class MeetingController(private val meetingCommand: MeetingCommand) {

    @POST
    fun createMeeting(
        @PathParam("meetupName") meetupName: String,
        @QueryParam("topic") topic: String?
    ): Response {
        val uuid = meetingCommand.createMeeting(topic, meetupName)
        return Response.created(URI("/meetup/{meetupName}/$uuid")).build()
    }
}
