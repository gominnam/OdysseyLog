package com.example.odysseylog.service

import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.util.*

@Service
class S3StorageService(
    private val s3Client: S3Client,
    private val s3Presigner: S3Presigner,
    @Value("\${aws.s3.bucket-name}") private val bucketName: String,
    @Value("\${aws.s3.presigned-url-duration}") private val duration: Duration
) : StorageService {
    private val logger = LoggerFactory.getLogger(S3StorageService::class.java)

    override fun generateUploadPresignedUrl(key: String): String {
        return try {
            val key = UUID.randomUUID().toString() + ".jpg"

            val putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build()

            val presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(duration)
                .putObjectRequest(putObjectRequest)
                .build()

            val presignedRequest = s3Presigner.presignPutObject(presignRequest)
            presignedRequest.url().toString()
        } catch (e: SdkException) {
            logger.error("Failed to generate presigned URL", e)
            throw CustomException(ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }
}