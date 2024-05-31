package com.example.odysseylog.dto

import com.example.odysseylog.domain.Photo
import java.time.LocalDateTime
import java.util.*

data class PhotoResponse(
    val id: String,
    val url: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun fromPhoto(photo: Photo): PhotoResponse {
            return PhotoResponse(
                id = (photo.id ?: "").toString(),
                url = photo.url ?: "",
                createdAt = photo.createdAt ?: LocalDateTime.now(),
                updatedAt = photo.updatedAt ?: LocalDateTime.now()
            )
        }

        fun fromPhotos(photos: List<Photo>): List<PhotoResponse> {
            return photos.map { fromPhoto(it) }
        }
    }
}