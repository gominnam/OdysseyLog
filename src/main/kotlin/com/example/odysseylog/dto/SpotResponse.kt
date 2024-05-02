package com.example.odysseylog.dto

import com.example.odysseylog.domain.Spot
import java.time.LocalDateTime

data class SpotResponse(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val memo: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val routeId: Long,
    val photos: List<PhotoResponse>
) {
    companion object {
        fun fromSpot(spot: Spot): SpotResponse {
            return SpotResponse(
                id = spot.id ?: 0,
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