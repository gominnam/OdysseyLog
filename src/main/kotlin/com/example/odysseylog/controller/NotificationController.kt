package com.example.odysseylog.controller

import com.example.odysseylog.service.PhotoService
import com.example.odysseylog.service.RouteService
import com.example.odysseylog.util.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notifications")
class NotificationController(
    private val routeService: RouteService,
    private val photoService: PhotoService
) {

    @PostMapping("/compressed-image")
    fun notifyCompressedImage(
        @RequestParam("url") url: String
    ): ResponseEntity<String> {
        return when {
            url.startsWith(Constants.ROUTE_PREFIX) -> {
                routeService.updateCompressedStatus(url)
                ResponseEntity.ok("Route compressed image status updated")
            }

            url.startsWith(Constants.PHOTO_PREFIX) -> {
                photoService.updateCompressedStatus(url)
                ResponseEntity.ok("Photo compressed image status updated")
            }
            else -> ResponseEntity.badRequest().body("Invalid URL")
        }
    }
}