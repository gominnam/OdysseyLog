package com.example.odysseylog.dto

data class NotificationRequest(
    val fileKey: String? = null,
    val bucketName: String? = null,
    val url: String? = null,
    val status: String? = null
)