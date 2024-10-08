package com.example.odysseylog.service

import com.example.odysseylog.domain.Photo
import com.example.odysseylog.domain.Route
import com.example.odysseylog.domain.Spot
import com.example.odysseylog.dto.*
import com.example.odysseylog.util.Constants
import jakarta.transaction.Transactional
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OdysseyServiceImpl(
    private val routeService: RouteService,
    private val spotService: SpotService,
    private val photoService: PhotoService,
    private val s3StorageService: StorageService,
    private val modelMapper: ModelMapper
) : OdysseyService {

    @Transactional
    override fun createOdyssey(request : OdysseyRequest): OdysseyResponse {
        // Step 1: Convert RouteRequest to Route entity and save it
        val routeKey = s3StorageService.generateUniqueKey(Constants.ROUTE_PREFIX)
        val routePresignedUrl = s3StorageService.generateUploadPresignedUrl(routeKey)
        request.route.photoUrl = routeKey
        val savedRoute = routeService.createRoute(request.route)

        // Step 2: Convert SpotRequest to Spot entity, set Route ID, and save them
        val spotResponse = request.spots.map { spotRequest ->
            val spotEntity = modelMapper.map(spotRequest, Spot::class.java)
            spotEntity.route = Route().apply { this.id = savedRoute.id }

            val photos = mutableListOf<Photo>()
            val photoResponses = mutableListOf<PhotoResponse>()
            for(i in 0 until spotRequest.photoSize!!) {
                val path = s3StorageService.generateUniqueKey(Constants.PHOTO_PREFIX)
                val presignedUrl = s3StorageService.generateUploadPresignedUrl(path)
                val photoEntity = Photo().apply {
                    this.order = i
                    this.spot = spotEntity
                    this.url = path
                    this.presignedUrl = presignedUrl
                }
                photoService.save(photoEntity)
                photos.add(photoEntity)

                val photoResponse = PhotoResponse.fromPhoto(photoEntity)
                photoResponses.add(photoResponse)
            }

            spotEntity.photos = photos
            spotService.save(spotEntity)

            val spotResponse = SpotResponse(
                id = spotEntity.id ?: "",
                latitude = spotEntity.latitude ?: 0.0,
                longitude = spotEntity.longitude ?: 0.0,
                memo = spotEntity.memo ?: "",
                createdAt = spotEntity.createdAt ?: LocalDateTime.now(),
                updatedAt = spotEntity.updatedAt ?: LocalDateTime.now(),
                routeId = spotEntity.route?.id ?: 0,
                photos = photoResponses
            )

            spotResponse
        }

        val routeResponse = RouteResponse(
            id = savedRoute.id ?: 0,
            title = savedRoute.title ?: "",
            photoUrl = routePresignedUrl
        )

        return OdysseyResponse(
            route = routeResponse,
            spots = spotResponse
        )
    }
}
