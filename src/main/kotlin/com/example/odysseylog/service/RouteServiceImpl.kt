package com.example.odysseylog.service

import com.example.odysseylog.domain.Route
import com.example.odysseylog.dto.RouteRequest
import com.example.odysseylog.dto.RouteResponse
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import com.example.odysseylog.repository.RouteRepository
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RouteServiceImpl(
    private val routeRepository: RouteRepository,
    private val modelMapper: ModelMapper,
    private val presignedUrlService: S3StorageService
) : RouteService {

    override fun getRoutes(lastFetchedAt: LocalDateTime, pageable: Pageable): Page<RouteResponse> {
        val routes = routeRepository.findAllByCreatedAtBefore(lastFetchedAt, pageable)
        return routes.map { route ->
            val presignedUrl = presignedUrlService.generateDownloadPresignedUrl(route.photoUrl.toString())
            RouteResponse.fromRoute(route, presignedUrl) }
    }

    @Transactional(readOnly = true)
    override fun getRoute(id: Long): RouteResponse {
        val route = routeRepository.findRouteWithSpots(id)
            ?: throw CustomException(ErrorCode.ROUTE_NOT_FOUND, id.toString())

        val spotsWithPhotos = routeRepository.findSpotsWithPhotosByRouteId(route.id!!)

        route.spots.forEach { spot ->
            val spotWithPhotos = spotsWithPhotos.find { it.id == spot.id }
            spot.photos = spotWithPhotos?.photos ?: mutableListOf()
        }

        return RouteResponse.fromRoute(route)
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
        return routeRepository.findById(id ?: throw CustomException(ErrorCode.ROUTE_ID_NULL))
            .orElseThrow { CustomException(ErrorCode.ROUTE_NOT_FOUND, id) }
    }

    private fun saveRoute(route: Route, errorCode: ErrorCode): RouteResponse {
        return try {
            val savedRoute = routeRepository.save(route)
            RouteResponse.fromRoute(savedRoute)
        } catch (e: Exception) {
            throw CustomException(errorCode, route.id ?: throw CustomException(ErrorCode.ROUTE_ID_NULL))
        }
    }

    private fun updateRouteDetails(routeRequest: RouteRequest, existingRoute: Route): Route {
        modelMapper.map(routeRequest, existingRoute)
        existingRoute.title = routeRequest.title //todo: fix modelmapper issue
        return existingRoute
    }
}
