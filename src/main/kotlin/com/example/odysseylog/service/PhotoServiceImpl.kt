package com.example.odysseylog.service

import com.example.odysseylog.domain.Photo
import com.example.odysseylog.dto.PhotoRequest
import com.example.odysseylog.dto.PhotoResponse
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import com.example.odysseylog.repository.PhotoRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PhotoServiceImpl(
    private val photoRepository: PhotoRepository
) : PhotoService {
//    override fun getPhotos(key: String): List<PhotoResponse> {
//        val photo = photoRepository.findById(key)
//        return listOf(PhotoResponse.fromPhoto(photo))
//    }

    override fun save(photo: Photo): Photo = photoRepository.save(photo)

    override fun updateCompressedStatus(photoUrl: String): ResponseEntity<String> {
        return try {
            photoRepository.updateIsCompressedByPhotoUrl(photoUrl)
            ResponseEntity.ok("Photo compressed image status is updated")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to ${photoUrl} update compressed status")
        }
    }
}
