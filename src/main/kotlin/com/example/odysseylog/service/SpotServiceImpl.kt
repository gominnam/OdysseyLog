package com.example.odysseylog.service

import com.example.odysseylog.domain.Spot
import com.example.odysseylog.dto.*
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import com.example.odysseylog.repository.SpotRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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
        return request.map { spot ->
            val photosWithPresignedUrls = spot.photos.map { photo ->
                val presignedUrl = if (photo.url?.isEmpty() != false) {
                    ""
                } else {
                     presignedUrlService.generateDownloadPresignedUrl(photo.url!!)
                }
                mapPhotoRequestToResponse(photo, presignedUrl)
            }
            val spotEntity = modelMapper.map(spot, Spot::class.java)
            SpotResponse.fromSpot(spotEntity, photosWithPresignedUrls)
        }
    }

    fun mapPhotoRequestToResponse(photoRequest: PhotoRequest, presignedUrl: String): PhotoResponse {
        return PhotoResponse(
            id = photoRequest.id ?: 0,
            url = photoRequest.url ?: "",
            presignedUrl = presignedUrl,
            createdAt = photoRequest.createdAt ?: LocalDateTime.now(),
            updatedAt = photoRequest.updatedAt ?: LocalDateTime.now()
        )
    }
}