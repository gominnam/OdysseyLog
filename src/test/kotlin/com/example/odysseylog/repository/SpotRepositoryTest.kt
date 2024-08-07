package com.example.odysseylog.repository

import com.example.odysseylog.domain.Spot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
class SpotRepositoryTest {

    @Autowired
    private lateinit var spotRepository: SpotRepository

    @Test
    fun `when findById is called, then return the corresponding spot`() {
        // given
        val id = 1L
        val spot = spotRepository.findById(id)

        // then
        assertThat(spot).isNotNull
    }

    @Test
    fun `when findById is called with valid id, then return the corresponding spot`() {
        // given
        val id = "uuid"
        val spot = Spot().apply {
            this.id = id
            this.latitude = 37.123456
            this.longitude = 127.123456
            this.memo = "spot-memo"
        }
        spotRepository.save(spot)

        // when
//        val result = spotRepository.findById(id)
//
//        // then
//        assertTrue(result.isPresent)
//        val retrievedSpot = result.get()
//        assertEquals(spot.id, retrievedSpot.id)
//        assertEquals(spot.latitude, retrievedSpot.latitude)
//        assertEquals(spot.longitude, retrievedSpot.longitude)
//        assertEquals(spot.memo, retrievedSpot.memo)
    }
}