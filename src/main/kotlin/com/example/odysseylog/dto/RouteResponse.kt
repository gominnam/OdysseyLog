package com.example.odysseylog.dto

import com.example.odysseylog.domain.Route
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode

data class RouteResponse(
    val id: Long = 0,
    val title: String = "",
    val photoUrl: String = "",
    val presignedUrl: String? = null
) {
    companion object {
        fun fromRoute(route: Route?, presignedUrl: String? = null): RouteResponse {
            val id = route?.id ?: throw CustomException(ErrorCode.ROUTE_ID_NULL)
            return RouteResponse(
                id = id,
                title = route.title ?: "",
                photoUrl = route.photoUrl ?: "",
                presignedUrl = presignedUrl
            )
        }
    }
}