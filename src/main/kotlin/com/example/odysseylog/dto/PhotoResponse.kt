package com.example.odysseylog.dto

import com.example.odysseylog.domain.Photo
import java.time.LocalDateTime

data class PhotoResponse(
    val id: Long,
    val url: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun fromPhoto(photo: Photo): PhotoResponse {
            return PhotoResponse(
                id = photo.id ?: 0,
                url = photo.url ?: "",
                createdAt = photo.createdAt ?: LocalDateTime.now(),
                updatedAt = photo.updatedAt ?: LocalDateTime.now()
            )
        }
    }
}