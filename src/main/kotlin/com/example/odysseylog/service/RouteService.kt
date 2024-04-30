package com.example.odysseylog.service

import com.example.odysseylog.dto.RouteRequest
import com.example.odysseylog.dto.RouteResponse

interface RouteService {
    fun getRoutes(): String
    fun getRoute(id: Long): RouteResponse
    fun deleteRoute(id: Long)
    fun createRoute(routeRequest: RouteRequest): RouteResponse
    fun updateRoute(routeRequest: RouteRequest): RouteResponse
}