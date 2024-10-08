package com.example.odysseylog.dto

import com.example.odysseylog.domain.Photo
import java.time.LocalDateTime

data class PhotoResponse(
    val id: Long = 0,
    val url: String = "",
    val presignedUrl: String = "",
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {
    companion object {
        fun fromPhoto(photo: Photo): PhotoResponse {
            return PhotoResponse(
                id = photo.id ?: 0,
                url = photo.url ?: "",
                presignedUrl = photo.presignedUrl ?: "",
                createdAt = photo.createdAt ?: LocalDateTime.now(),
                updatedAt = photo.updatedAt ?: LocalDateTime.now()
            )
        }

        fun fromPhotos(photos: List<Photo>): List<PhotoResponse> {
            return photos.map { fromPhoto(it) }
        }
    }
}