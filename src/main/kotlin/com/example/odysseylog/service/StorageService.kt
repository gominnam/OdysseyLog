package com.example.odysseylog.service

import java.time.Duration

interface StorageService {
    fun generateUploadPresignedUrl(key: String, duration: Duration): String
}