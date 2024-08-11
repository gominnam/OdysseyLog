package com.example.odysseylog.controller

import com.example.odysseylog.service.PhotoService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest
@MockBean(JpaMetamodelMappingContext::class)
class PhotoControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var photoService: PhotoService

//    @Test
//    fun `when get is called with valid id, then return the corresponding route`() {
//
//    }
}