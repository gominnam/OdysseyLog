package com.example.odysseylog.service

interface StorageService {
    fun generateUploadPresignedUrl(key: String): String
    fun generateDownloadPresignedUrl(key: String): String
    fun generateUniqueKey(): String
}