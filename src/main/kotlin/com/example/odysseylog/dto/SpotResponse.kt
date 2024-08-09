package com.example.odysseylog.dto

import com.example.odysseylog.domain.Spot
import java.time.LocalDateTime

data class SpotResponse(
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val memo: String = "",
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val routeId: Long = 0,
    val photos: List<PhotoResponse> = emptyList()
) {
    companion object {
        fun fromSpot(spot: Spot, photos: List<PhotoResponse>? = emptyList()): SpotResponse {
            return SpotResponse(
                id = spot.id ?: "",
                latitude = spot.latitude ?: 0.0,
                longitude = spot.longitude ?: 0.0,
                memo = spot.memo ?: "",
                createdAt = spot.createdAt ?: LocalDateTime.now(),
                updatedAt = spot.updatedAt ?: LocalDateTime.now(),
                routeId = spot.route?.id ?: 0,
                photos = spot.photos.map { PhotoResponse.fromPhoto(it) }
            )
        }
    }
}