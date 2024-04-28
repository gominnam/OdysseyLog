package com.example.odysseylog.service

import com.example.odysseylog.domain.Route
import com.example.odysseylog.dto.RouteResponse
import com.example.odysseylog.repository.RouteRepository
import org.springframework.stereotype.Service

@Service
class RouteServiceImpl(private val routeRepository: RouteRepository) : RouteService {

    override fun getRoutes(): String {
        return "Hello, Route!"
    }

    override fun getRoute(id: Long): RouteResponse {
        return routeRepository.findById(id)
            .map(this::toRouteResponse)
            .orElseThrow { NoSuchElementException("Route with id $id not found") }
    }

    fun toRouteResponse(route: Route): RouteResponse {
        val id = route.id ?: throw IllegalArgumentException("Route id cannot be null")
        return RouteResponse(
            id = id,
            title = route.title ?: ""
        )
    }
}