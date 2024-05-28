package com.example.odysseylog.controller

import com.example.odysseylog.service.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("api/storage")
class StorageController {
    @Value("\${aws.s3.presigned-url-duration}")
    private lateinit var duration: String

    @Autowired
    private lateinit var storageService: StorageService

    @GetMapping("/presigned-url")
    fun generateUploadPresignedUrl(
        @RequestParam key: String
    ): ResponseEntity<String> {
        val duration = Duration.ofSeconds(duration.toLong())
        storageService.generateUploadPresignedUrl(key, duration)
        return ResponseEntity.ok("Presigned URL generated")
    }
}