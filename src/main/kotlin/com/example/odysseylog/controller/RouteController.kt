package com.example.odysseylog.controller

import com.example.odysseylog.dto.RouteResponse
import com.example.odysseylog.service.RouteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/routes")
class RouteController(private val routeService: RouteService) {

    @GetMapping
    fun getRoutes(): String {
        return "Hello, Route!"
    }

    @Operation(summary = "Get Route By Route ID")
    @GetMapping("/{id}")
    fun getRoute(
        @Parameter(description = "ID of the route to be obtained.", required = true)
        @PathVariable id: String,
    ): ResponseEntity<RouteResponse> {
        val route = routeService.getRoute(id.toLong())
        return ResponseEntity.ok(route)
    }
}