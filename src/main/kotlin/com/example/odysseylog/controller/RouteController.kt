package com.example.odysseylog.controller

import com.example.odysseylog.dto.RouteRequest
import com.example.odysseylog.dto.RouteResponse
import com.example.odysseylog.service.RouteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/routes")
class RouteController(private val routeService: RouteService) {

    @GetMapping("/")
    fun getRoute(
        @RequestParam(value = "timestamp", required = false) timestamp: LocalDateTime?,
        @RequestParam(value = "size", defaultValue = "9") size: Int, // 15, 일단 2개로 테스트
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "sort", defaultValue = "createdAt") sort: String
    ): ResponseEntity<Page<RouteResponse>> {
        val adjustedTimestamp = timestamp ?: LocalDateTime.now()
        val routes = routeService.getRoutes(adjustedTimestamp, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)))
        return ResponseEntity.ok(routes)
    }

    @Operation(summary = "Get Route By Route ID")
    @GetMapping("/{id}")
    fun getRoute(
        @Parameter(description = "ID of the route to be obtained.", required = true)
        @PathVariable id: Long,
    ): ResponseEntity<RouteResponse> {
        val route = routeService.getRoute(id)
        return ResponseEntity.ok(route)
    }

    @PostMapping("/{id}")
    fun updateRoute(
        @Parameter(description = "update route", required = true)
        @RequestBody routeRequest: RouteRequest
    ): ResponseEntity<String> {
        routeService.updateRoute(routeRequest)
        return ResponseEntity.ok("Route updated")
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(
        @Parameter(description = "delete route", required = true)
        @PathVariable id: Long,
    ): ResponseEntity<String> {
        routeService.deleteRoute(id)
        return ResponseEntity.ok("Route deleted")
    }

    @PostMapping("/create")
    fun createRoute(
        @Parameter(description = "create new route", required = true)
        @RequestBody routeRequest: RouteRequest
    ): ResponseEntity<String> {
        routeService.createRoute(routeRequest)
        return ResponseEntity.ok("Route created")
    }
}