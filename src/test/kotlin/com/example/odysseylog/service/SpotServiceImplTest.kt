package com.example.odysseylog.service

import com.example.odysseylog.domain.Spot
import com.example.odysseylog.dto.SpotRequest
import com.example.odysseylog.dto.SpotResponse
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import com.example.odysseylog.repository.SpotRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.modelmapper.ModelMapper
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class SpotServiceImplTest {

    @Mock
    private lateinit var spotRepository: SpotRepository

    @Mock
    private lateinit var modelMapper: ModelMapper

    @InjectMocks
    private lateinit var spotService: SpotServiceImpl

    @Test
    fun `when getSpot is called with valid id, then return the corresponding spot`() {
        // given
        val id = 1L
        val spot = Spot().apply {
            this.id = 1L
            this.latitude = 37.123456
            this.longitude = 127.123456
            this.memo = "spot-memo"
            this.createdAt = LocalDateTime.now()
            this.updatedAt = LocalDateTime.now()
        }
        val spotResponse = SpotResponse.fromSpot(spot)
        `when`(spotRepository.findById(id)).thenReturn(Optional.of(spot))

        // when
        val result = spotService.getSpot(id)

        // then
        assertEquals(spotResponse, result)
    }

    @Test
    fun `when getSpot is called with invalid id, then throw NoSuchElementException`() {
        // given
        val id = 1L
        `when`(spotRepository.findById(id)).thenReturn(Optional.empty())

        // when
        val exception = assertThrows<CustomException> {
            spotService.getSpot(id)
        }

        // then
        assertEquals(CustomException(ErrorCode.SPOT_NOT_FOUND, id).message, exception.message)
    }

    @Test
    fun `when createSpot is called with valid spotRequest, then return the created spot`() {
        // given
        val spotRequest = SpotRequest().apply {
            this.latitude = 37.123456
            this.longitude = 127.123456
            this.memo = "spot-memo"
        }
        val spot = Spot().apply {
            this.id = 1L
            this.latitude = 37.123456
            this.longitude = 127.123456
            this.memo = "spot-memo"
            this.createdAt = LocalDateTime.now()
            this.updatedAt = LocalDateTime.now()
        }
        val spotResponse = SpotResponse.fromSpot(spot)
        `when`(modelMapper.map(spotRequest, Spot::class.java)).thenReturn(spot)
        `when`(spotRepository.save(spot)).thenReturn(spot)

        // when
        val result = spotService.createSpot(spotRequest)

        // then
        assertEquals(spotResponse, result)
    }

    @Test
    fun `when createSpot is called with invalid spotRequest, then throw CustomException`() {
        // given
        val spotRequest = SpotRequest().apply {
            this.latitude = 37.123456
            this.longitude = 127.123456
            this.memo = "spot-memo"
        }
        val spot = Spot().apply {
            this.id = 1L
            this.latitude = 37.123456
            this.longitude = 127.123456
            this.memo = "spot-memo"
            this.createdAt = LocalDateTime.now()
            this.updatedAt = LocalDateTime.now()
        }
        `when`(modelMapper.map(spotRequest, Spot::class.java)).thenReturn(spot)
        `when`(spotRepository.save(spot)).thenThrow(RuntimeException())

        // when
        val exception = assertThrows<CustomException> {
            spotService.createSpot(spotRequest)
        }

        // then
        assertEquals(ErrorCode.SPOT_CREATION_FAILED.message, exception.message)
    }
}
