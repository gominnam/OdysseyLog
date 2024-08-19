package com.example.odysseylog.controller

import org.junit.jupiter.api.Assertions.assertEquals
import com.example.odysseylog.service.PhotoService
import com.example.odysseylog.service.RouteService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@MockBean(JpaMetamodelMappingContext::class)
@WebMvcTest(NotificationController::class)
class NotificationControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var routeService: RouteService

    @MockBean
    private lateinit var photoService: PhotoService

    @Test
    fun `when notifyCompressedImage is called with valid routeUrl, then return ResponseEntity with message`() {
        // given
        val routeUrl = "route-{route-uuid}.png"
        val expectedMessage = "Route compressed image status updated"
        val expectedResponse = ResponseEntity.ok(expectedMessage)
        `when`(routeService.updateCompressedStatus(routeUrl)).thenReturn(expectedResponse)

        // when
        val result = mvc.perform(
            post("/api/notifications/compressed-image")
                .param("url", routeUrl)
        ).andExpect(status().isOk)
         .andReturn().response

        // then
        assertEquals("Route compressed image status updated", result.contentAsString)
    }

    @Test
    fun `when notifyCompressedImage is called with valid photoUrl, then return ResponseEntity with message`() {
        // given
        val photoUrl = "photo-{photo-uuid}.png"
        val expectedMessage = "Photo compressed image status updated"
        val expectedResponse = ResponseEntity.ok(expectedMessage)

        `when`(photoService.updateCompressedStatus(photoUrl)).thenReturn(expectedResponse)

        // when
        val result = mvc.perform(
            post("/api/notifications/compressed-image")
                .param("url", photoUrl)
        ).andExpect(status().isOk)
         .andReturn().response

        // then
        assertEquals("Photo compressed image status updated", result.contentAsString)
    }
}