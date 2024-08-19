package com.example.odysseylog.controller

import com.example.odysseylog.service.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/storage")
class StorageController {
    @Autowired
    private lateinit var storageService: StorageService

    @GetMapping("/upload-presigned-url")
    fun generateUploadPresignedUrl(@PathVariable prefix: String): ResponseEntity<String> {
        val key = storageService.generateUniqueKey(prefix)
        val presignedUrl = storageService.generateUploadPresignedUrl(key)
        return ResponseEntity.ok(presignedUrl)
    }

    @GetMapping("/{key}/download-presigned-url")
    fun generateDownloadPresignedUrl(@PathVariable key: String): ResponseEntity<String> {
        val presignedUrl = storageService.generateDownloadPresignedUrl(key)
        return ResponseEntity.ok(presignedUrl)
    }
}
