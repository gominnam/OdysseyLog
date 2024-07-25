package com.example.odysseylog.service

import com.example.odysseylog.dto.RouteRequest
import com.example.odysseylog.dto.RouteResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface RouteService {
    fun getRoute(id: Long): RouteResponse
    fun getRoutes(lastFetchedAt: LocalDateTime, pageable: Pageable): Page<RouteResponse>
    fun deleteRoute(id: Long)
    fun createRoute(routeRequest: RouteRequest): RouteResponse
    fun updateRoute(routeRequest: RouteRequest): RouteResponse
}