package com.example.odysseylog.service

import com.example.odysseylog.domain.Spot
import com.example.odysseylog.dto.SpotRequest
import com.example.odysseylog.dto.SpotResponse
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import com.example.odysseylog.repository.SpotRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class SpotServiceImpl(
    private val spotRepository: SpotRepository,
    private val modelMapper: ModelMapper
) : SpotService {

    override fun getSpot(id: Long): SpotResponse {
        return spotRepository.findById(id)
            .map(SpotResponse::fromSpot)
            .orElseThrow { CustomException(ErrorCode.SPOT_NOT_FOUND, id) }
    }

    override fun createSpot(spotRequest: SpotRequest): SpotResponse {
        val spot = modelMapper.map(spotRequest, Spot::class.java)
        return saveSpot(spot, ErrorCode.SPOT_CREATION_FAILED)
    }

    override fun getSpotEntity(id: Long): Spot = spotRepository.findById(id)
        .orElseThrow { CustomException(ErrorCode.SPOT_NOT_FOUND, id) }

    fun saveSpot(spot: Spot, errorCode: ErrorCode): SpotResponse {
        try {
            val savedSpot = spotRepository.save(spot)
            return SpotResponse.fromSpot(savedSpot)
        } catch (e: Exception) {
            throw CustomException(errorCode, spot.id ?: throw CustomException(ErrorCode.SPOT_ID_NULL))
        }
    }
}