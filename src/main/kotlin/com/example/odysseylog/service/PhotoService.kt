package com.example.odysseylog.service

import com.example.odysseylog.dto.PhotoResponse
import org.springframework.web.multipart.MultipartFile

interface PhotoService {
    fun getPhotos(key: String): List<PhotoResponse>
    fun savePhoto(key: String, fileUrl: String)
}