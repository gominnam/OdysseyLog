package com.example.odysseylog.service

import com.example.odysseylog.dto.PhotoResponse
import org.springframework.web.multipart.MultipartFile

interface PhotoService {
    fun getPhotos(spotId: Long): List<PhotoResponse>
    fun uploadPhoto(spotId: Long, file: MultipartFile): String
}