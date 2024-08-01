package com.example.odysseylog.dto

import com.example.odysseylog.domain.Route
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import java.time.LocalDateTime

data class RouteResponse(
    val id: Long = 0,
    val title: String = "",
    val startLatitude: Double? = 0.0,
    val startLongitude: Double? = 0.0,
    val endLatitude: Double? = 0.0,
    val endLongitude: Double? = 0.0,
    val photoUrl: String = "",
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
    val spots: List<SpotResponse>? = emptyList(),
    val presignedUrl: String? = null
) {
    companion object {
        fun fromRoute(route: Route?, presignedUrl: String? = null): RouteResponse {
            val id = route?.id ?: throw CustomException(ErrorCode.ROUTE_ID_NULL)
            return RouteResponse(
                id = id,
                title = route.title ?: "",
                startLatitude = route.startLatitude ?: 0.0,
                startLongitude = route.startLongitude ?: 0.0,
                endLatitude = route.endLatitude ?: 0.0,
                endLongitude = route.endLongitude ?: 0.0,
                photoUrl = route.photoUrl ?: "",
                createdAt = route.createdAt ?: LocalDateTime.now(),
                updatedAt = route.updatedAt ?: LocalDateTime.now(),
                spots = route.spots.map { SpotResponse.fromSpot(it) },
                presignedUrl = presignedUrl
            )
        }
    }
}