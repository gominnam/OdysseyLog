package com.example.odysseylog.controller

import com.example.odysseylog.service.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/storage")
class StorageController {
    @Autowired
    private lateinit var storageService: StorageService

    @GetMapping("/presigned-url")
    fun generateUploadPresignedUrl(): ResponseEntity<String> {
        val presignedUrl = storageService.generateUploadPresignedUrl()
        return ResponseEntity.ok(presignedUrl)
    }
}