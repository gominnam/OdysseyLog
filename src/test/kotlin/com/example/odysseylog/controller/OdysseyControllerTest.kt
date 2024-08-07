package com.example.odysseylog.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(OdysseyController::class)
@MockBean(JpaMetamodelMappingContext::class)
class OdysseyControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `when createOdyssey is called with valid request, then return the corresponding presignedUrls`() {

    }

}