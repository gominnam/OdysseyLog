package com.example.odysseylog.controller

import com.example.odysseylog.dto.OdysseyRequest
import com.example.odysseylog.dto.OdysseyResponse
import com.example.odysseylog.service.OdysseyService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/odyssey")
class OdysseyController(
    private val odysseyService: OdysseyService
) {
    private val logger = LoggerFactory.getLogger(OdysseyController::class.java)

    @PostMapping("/")
    fun createOdyssesy(
        @RequestBody request: OdysseyRequest
    ) : ResponseEntity<OdysseyResponse> {
        val response = odysseyService.createOdyssey(request)
        logger.info("response: $response")
        return ResponseEntity.ok(response)
    }

}
