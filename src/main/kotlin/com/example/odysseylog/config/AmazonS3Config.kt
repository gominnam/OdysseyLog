package com.example.odysseylog.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonS3Config {
    @Value("\${aws.access-key-id}")
    private lateinit var accessKeyId: String

    @Value("\${aws.secret-access-key}")
    private lateinit var secretAccessKey: String

    @Value("\${aws.s3.region}")
    private lateinit var region: String

    @Bean
    fun amazonS3Client(): AmazonS3 {
        val awsCredentials = BasicAWSCredentials(accessKeyId, secretAccessKey)
        return AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .build()
    }
}