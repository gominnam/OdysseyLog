package com.example.odysseylog.dto

import com.example.odysseylog.domain.Route

data class RouteResponse(
    val id: Long,
    val title: String
) {
    companion object {
        fun fromRoute(route: Route?): RouteResponse {
            val id = route?.id ?: throw IllegalArgumentException("Route id cannot be null")
            return RouteResponse(
                id = id,
                title = route.title ?: ""
            )
        }
    }
}