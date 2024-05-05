package com.example.odysseylog.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.example.odysseylog.domain.Photo
import com.example.odysseylog.domain.Spot
import com.example.odysseylog.dto.PhotoResponse
import com.example.odysseylog.repository.PhotoRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class PhotoServiceImpl(
    private val amazonS3: AmazonS3,
    private val photoRepository: PhotoRepository,
    private val spotService: SpotService
) : PhotoService {

    @Value("\${aws.s3.bucket-name}")
    private lateinit var bucketName: String

    override fun getPhotos(spotId: Long): List<PhotoResponse> {
        val photos = photoRepository.findBySpotId(spotId)
        return PhotoResponse.fromPhotos(photos)
    }

    override fun uploadPhoto(spotId: Long, file: MultipartFile): String {
        val keyName = "${UUID.randomUUID()}-${file.originalFilename}"
        val metadata = ObjectMetadata().apply {
            contentLength = file.size
            contentType = file.contentType
        }
        amazonS3.putObject(bucketName, keyName, file.inputStream, metadata)
        val url = amazonS3.getUrl(bucketName, keyName).toString()

        val spot = spotService.getSpotEntity(spotId)

        val photo = Photo().apply {
            this.url = url
            this.spot = spot
        }
        photoRepository.save(photo)

        return url
    }
}
