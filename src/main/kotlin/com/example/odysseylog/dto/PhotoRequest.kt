package com.example.odysseylog.dto

import java.time.LocalDateTime

data class PhotoRequest(
    var id: Long? = null,
    var order: Int? = null,
    var url: String? = null,
    var presignedUrl: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)