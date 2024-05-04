package com.example.odysseylog.controller

import com.example.odysseylog.dto.RouteRequest
import com.example.odysseylog.dto.RouteResponse
import com.example.odysseylog.service.RouteService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RouteController::class)
@MockBean(JpaMetamodelMappingContext::class)
class RouteControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var routeService: RouteService

    @Test
    fun `when getRoute is called with valid id, then return the corresponding route`() {
        // given
        val id = 1L
        val expectedRoute = RouteResponse(id, "route-title")
        `when`(routeService.getRoute(id)).thenReturn(expectedRoute)

        // when
        val result: MockHttpServletResponse = mvc.perform(
            get("/api/routes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
         .andReturn().response

        // then
        val actualRoute = objectMapper.readValue(result.contentAsString, RouteResponse::class.java)
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun `when updateRoute is called with valid id and request, then return Route updated`() {
        // given
        val id = 1L
        val routeRequest = RouteRequest(id, "new-route-title")
        val requestBody = objectMapper.writeValueAsString(routeRequest)

        // when
        val result: MockHttpServletResponse = mvc.perform(
            post("/api/routes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk)
         .andReturn().response

        // then
        assertEquals("Route updated", result.contentAsString)
        verify(routeService).updateRoute(routeRequest)
    }

    @Test
    fun `when deleteRoute is called with valid id and request, then return Route deleted`(){
        // given
        val id = 1L

        // when
        val result: MockHttpServletResponse = mvc.perform(
            delete("/api/routes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
         .andReturn().response

        // then
        assertEquals("Route deleted", result.contentAsString)
        verify(routeService).deleteRoute(id)
    }

    @Test
    fun `when createRoute is called with valid request, then return Route created`() {
        // given
        val routeRequest = RouteRequest(1L, "route-title")
        val requestBody = objectMapper.writeValueAsString(routeRequest)

        // when
        val result: MockHttpServletResponse = mvc.perform(
            post("/api/routes/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk)
         .andReturn().response

        // then
        assertEquals("Route created", result.contentAsString)
        verify(routeService).createRoute(routeRequest)
    }
}