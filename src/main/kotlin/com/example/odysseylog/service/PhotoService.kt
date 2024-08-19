package com.example.odysseylog.service

import com.example.odysseylog.domain.Photo
import com.example.odysseylog.dto.PhotoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface PhotoService {
    fun save(photo: Photo): Photo
    fun updateCompressedStatus(photoUrl: String): ResponseEntity<String>
}
