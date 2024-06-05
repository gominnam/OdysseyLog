package com.example.odysseylog.service

import com.example.odysseylog.domain.Photo
import com.example.odysseylog.domain.Route
import com.example.odysseylog.domain.Spot
import com.example.odysseylog.dto.OdysseyRequest
import com.example.odysseylog.dto.OdysseyResponse
import jakarta.transaction.Transactional
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class OdysseyServiceImpl(
    private val routeService: RouteService,
    private val spotService: SpotService,
    private val photoService: PhotoService,
    private val s3StorageService: StorageService,
    private val modelMapper: ModelMapper
) : OdysseyService {

    @Transactional
    override fun createOdyssey(request : OdysseyRequest): List<OdysseyResponse> {
        // Step 1: Convert RouteRequest to Route entity and save it
        val savedRoute = routeService.createRoute(request.route)

        // Step 2: Convert SpotRequest to Spot entity, set Route ID, and save them
        val spots = request.spots.map { spotRequest ->
            val spotEntity = modelMapper.map(spotRequest, Spot::class.java)
            spotEntity.route = Route().apply { this.id = savedRoute.id }
            spotService.save(spotEntity)
            spotEntity
        }

        // Step 3: Convert PhotoRequest to Photo entity, set Spot, and save them
        val presignedUrlResponses = request.photos.map { photoRequest ->
            val photoEntity = modelMapper.map(photoRequest, Photo::class.java).apply{
                this.spot = spots.find { it.id == photoRequest.spotId }
            }

            val presignedUrl = s3StorageService.generateUploadPresignedUrl()
            val odysseyResponse = OdysseyResponse(photoRequest.temporaryId, presignedUrl)
            photoService.save(photoEntity)
            odysseyResponse
        }

        // Return the list of presigned URLs to the client
        return presignedUrlResponses
    }
}
