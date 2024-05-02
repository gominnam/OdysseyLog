package com.example.odysseylog.dto

import com.example.odysseylog.domain.Route
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode

data class RouteResponse(
    val id: Long,
    val title: String
) {
    companion object {
        fun fromRoute(route: Route?): RouteResponse {
            val id = route?.id ?: throw CustomException(ErrorCode.ROUTE_ID_NULL)
            return RouteResponse(
                id = id,
                title = route.title ?: ""
            )
        }
    }
}