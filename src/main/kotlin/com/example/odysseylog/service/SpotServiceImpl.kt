package com.example.odysseylog.service

import com.example.odysseylog.domain.Spot
import com.example.odysseylog.dto.RouteResponse
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
    private val modelMapper: ModelMapper,
    private val presignedUrlService: S3StorageService
) : SpotService {

    override fun getSpot(id: String): SpotResponse {
        val spot = spotRepository.findById(id)
            ?: throw CustomException(ErrorCode.SPOT_NOT_FOUND, id)
        return SpotResponse.fromSpot(spot)
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

    override fun save(spot: Spot): Spot = spotRepository.save(spot)

    override fun getSpotPresignedUrls(request: List<SpotRequest>): List<SpotResponse> {
        return request.mapNotNull { spot ->
            val photosWithPresignedUrls = spot.photos.mapNotNull { photo ->
                if (photo.url.isEmpty()) {
                    return@mapNotNull null
                }
                val presignedUrl = presignedUrlService.generateDownloadPresignedUrl(photo.url)
                photo.copy(presignedUrl = presignedUrl)
            }
            if (photosWithPresignedUrls.isEmpty()) {
                return@mapNotNull null
            }
            val spotEntity = modelMapper.map(spot, Spot::class.java)
            SpotResponse.fromSpot(spotEntity, photosWithPresignedUrls)
        }
    }
}