package com.example.odysseylog.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class AmazonS3Config {
    @Value("\${aws.access-key-id}")
    private lateinit var accessKeyId: String

    @Value("\${aws.secret-access-key}")
    private lateinit var secretAccessKey: String

    @Value("\${aws.s3.region}")
    private lateinit var region: String

    @Bean
    fun s3Client(): S3Client {
        val awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey)
        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build()
    }
}