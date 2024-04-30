package com.example.odysseylog.service

import com.example.odysseylog.domain.Route
import com.example.odysseylog.dto.RouteRequest
import com.example.odysseylog.dto.RouteResponse
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import com.example.odysseylog.repository.RouteRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class RouteServiceImpl(
    private val routeRepository: RouteRepository,
    private val modelMapper: ModelMapper
) : RouteService {

    override fun getRoutes(): String {
        return "Hello, Route!"
    }

    override fun getRoute(id: Long): RouteResponse {
        return routeRepository.findById(id)
            .map(RouteResponse::fromRoute)
            .orElseThrow { CustomException(ErrorCode.ROUTE_NOT_FOUND, id) }
    }

    override fun deleteRoute(id: Long) {
        val route = findRouteById(id)
        routeRepository.delete(route)
    }

    override fun createRoute(routeRequest: RouteRequest): RouteResponse {
        // TODO("JWT Authentication is not implemented yet. get and set userid")
        val route = modelMapper.map(routeRequest, Route::class.java)
        return saveRoute(route, ErrorCode.ROUTE_CREATION_FAILED)
    }

    override fun updateRoute(routeRequest: RouteRequest) : RouteResponse {
        val existingRoute = findRouteById(routeRequest.id)
        val updatedRoute = updateRouteDetails(routeRequest, existingRoute)
        return saveRoute(updatedRoute, ErrorCode.ROUTE_UPDATE_FAILED)
    }

    private fun findRouteById(id: Long?): Route {
        return routeRepository.findById(id ?: throw CustomException(ErrorCode.BAD_REQUEST, "Route id is null"))
            .orElseThrow { CustomException(ErrorCode.ROUTE_NOT_FOUND, id) }
    }

    private fun saveRoute(route: Route, errorCode: ErrorCode): RouteResponse {
        return try {
            val savedRoute = routeRepository.save(route)
            RouteResponse.fromRoute(savedRoute)
        } catch (e: Exception) {
            throw CustomException(errorCode, route.id ?: throw IllegalArgumentException("Route id is null"))
        }
    }

    private fun updateRouteDetails(routeRequest: RouteRequest, existingRoute: Route): Route {
        modelMapper.map(routeRequest, existingRoute)
        return existingRoute
    }
}
