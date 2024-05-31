package com.example.odysseylog.service

import com.example.odysseylog.domain.Photo
import com.example.odysseylog.dto.PhotoResponse
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import com.example.odysseylog.repository.PhotoRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PhotoServiceImpl(
    private val photoRepository: PhotoRepository,
    private val spotService: SpotService
) : PhotoService {

    override fun getPhotos(key: String): List<PhotoResponse> {
        val photos = photoRepository.findBySpotId(key)
        return PhotoResponse.fromPhotos(photos)
    }

//    override fun savePhoto(key: Long, fileUrl: String) {
//        val spot = spotService.getSpot(key)
//        val photo = Photo(url = fileUrl, spot = spot)
//        photoRepository.save(photo)
//    }

}
