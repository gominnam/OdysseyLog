package com.example.odysseylog.controller

import com.example.odysseylog.dto.SpotRequest
import com.example.odysseylog.dto.SpotResponse
import com.example.odysseylog.service.SpotService
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/spots")
class SpotController(private val spotService: SpotService) {

    private val logger = LoggerFactory.getLogger(SpotController::class.java)

    @GetMapping("/{id}")
    fun getSpot(
        @Parameter(description = "ID of the spot to be obtained.", required = true)
        @PathVariable id: String
    ): ResponseEntity<SpotResponse> {
        val spot = spotService.getSpot(id)
        return ResponseEntity.ok(spot)
    }

    @PostMapping("/create")
    fun createSpot(
        @Parameter(description = "create new spot", required = true)
        @RequestBody spotRequest: SpotRequest
    ): ResponseEntity<String> {
        spotService.createSpot(spotRequest)
        return ResponseEntity.ok("Spot created")
    }

    @PostMapping("/photos")
    fun getSpotPresignedUrls(
        @Parameter(description = "ID of the spot to be obtained.", required = true)
        @RequestBody request: List<SpotRequest>
    ): ResponseEntity<List<SpotResponse>> {
        logger.info("Received request: $request")
        val photos = spotService.getSpotPresignedUrls(request)
        logger.info("Response: $photos")
        return ResponseEntity.ok(photos)
    }
}