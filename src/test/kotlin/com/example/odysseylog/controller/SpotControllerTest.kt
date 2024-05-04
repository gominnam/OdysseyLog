package com.example.odysseylog.controller

import com.example.odysseylog.dto.SpotResponse
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import com.example.odysseylog.exception.handler.GlobalExceptionHandler
import com.example.odysseylog.service.SpotService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(controllers = [SpotController::class], includeFilters = [
    ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [GlobalExceptionHandler::class])
])
@MockBean(JpaMetamodelMappingContext::class)
class SpotControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var spotService: SpotService

    @Test
    fun `when getSpot is called with valid id, then return the corresponding spot`() {
        // given
        val id = 1L
        val expectedSpot = SpotResponse(id, 37.1234, 126.1234,
            "memo", LocalDateTime.now(), LocalDateTime.now() ,1L,
            emptyList()
        )
        `when`(spotService.getSpot(id)).thenReturn(expectedSpot)

        // when
        val result: MockHttpServletResponse = mvc.perform(
            get("/api/spots/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
         .andReturn().response

        // then
        val actualSpot = objectMapper.readValue(result.contentAsString, SpotResponse::class.java)
        assertEquals(expectedSpot, actualSpot)
    }

    @Test
    fun `when getSpot is called with invalid id, then return Exception Not Found`() {
        // given
        val id = 1L
        `when`(spotService.getSpot(id)).thenThrow(CustomException(ErrorCode.SPOT_NOT_FOUND, id))

        // when
        val result = mvc.perform(
            get("/api/spots/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn()

        // then
        assertEquals(500, result.response.status)
        val responseBody = objectMapper.readValue(result.response.contentAsString, Map::class.java)
        assertEquals(2001, responseBody["status"])
        assertEquals("Spot with id %s not found", responseBody["message"])
    }

    @Test
    fun `when createSpot is called with valid request, then return Spot created`() {
        // given
        val spotRequest = SpotResponse(1, 37.1234, 126.1234,
            "memo", LocalDateTime.now(), LocalDateTime.now() ,1L,
            emptyList()
        )
        val requestBody = objectMapper.writeValueAsString(spotRequest)

        // when
        val result: MockHttpServletResponse = mvc.perform(
            post("/api/spots/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk)
         .andReturn().response

        // then
        assertEquals("Spot created", result.contentAsString)
    }
}