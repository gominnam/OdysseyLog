package com.example.odysseylog.service

import com.example.odysseylog.dto.RouteResponse

interface RouteService {
    fun getRoutes(): String
    fun getRoute(id: Long): RouteResponse
}