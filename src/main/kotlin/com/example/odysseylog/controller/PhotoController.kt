package com.example.odysseylog.controller

import com.example.odysseylog.service.PhotoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/photos")
class PhotoController {

    @Autowired
    private lateinit var photoService: PhotoService

    @PostMapping("/")
    fun uploadPhoto(
        @RequestParam("spotId") key: String,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<String> {
//        val message = photoService.uploadPhoto(key, file)
        val message  = "t"
        return ResponseEntity.ok(message)
    }
}