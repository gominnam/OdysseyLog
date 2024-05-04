package com.example.odysseylog.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class AmazonS3Config {
    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .region(Region.of("ap-northeast-2"))
            .build()
    }
}