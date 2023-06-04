package org.grjus.meetup

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.Response
import org.grjus.storage.MeetupDto
import org.grjus.storage.MeetupRepository
import org.grjus.storage.toDto

@ApplicationScoped
class MeetupQuery(private val meetupRepository: MeetupRepository) {

    fun findMeetupByName(name: String): MeetupDto? {
        return meetupRepository.findByName(name)?.toDto()
    }
}

@Path("/meetup/{name}")
class MeetupController(private val meetupQuery: MeetupQuery) {

    @GET
    fun getByName(@PathParam("name") name: String): Response {
        meetupQuery.findMeetupByName(name)?.let {
            return Response.ok(it).build()
        }
        return Response.status(404, "Meetup not found").build()
    }
}
