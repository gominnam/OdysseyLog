package com.example.odysseylog.service

import com.example.odysseylog.exception.CustomException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.ArgumentMatchers.any
import org.mockito.kotlin.whenever
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.net.URL
import java.time.Duration


@ExtendWith(MockitoExtension::class)
class S3StorageServiceTest {

    @Mock
    private lateinit var s3Client: S3Client

    @Mock
    private lateinit var s3Presigner: S3Presigner

    private lateinit var s3StorageService: S3StorageService

    private val bucketName: String = "test-bucket"
    private val duration: Duration = Duration.ofMinutes(10)

    @BeforeEach
    fun setup() {
        s3StorageService = S3StorageService(s3Client, s3Presigner, bucketName, duration)
    }

    @Test
    fun `when generateUploadPresignedUrl is called, then return the presignedUrl`() {
        // given
        val key = "test-key"
        val presignedUrl = "http://aws.amazon.com/s3/object/presigned-url"
        val presignedPutObjectRequest: PresignedPutObjectRequest = mock()

        `when`(presignedPutObjectRequest.url()).thenReturn(URL(presignedUrl))
        `when`(s3Presigner.presignPutObject(any(PutObjectPresignRequest::class.java))).thenReturn(presignedPutObjectRequest)

        // when
        val result = s3StorageService.generateUploadPresignedUrl(key)

        // then
        assertEquals(presignedUrl, result)
    }

    @Test
    fun `when generateDownloadPresignedUrl is called, then return the presignedUrl`() {
        // given
        val key = "test-key"
        val presignedUrl = "http://aws.amazon.com/s3/object/presigned-url"
        val presignedGetObjectRequest = mock(PresignedGetObjectRequest::class.java)

        `when`(presignedGetObjectRequest.url()).thenReturn(URL(presignedUrl))
        `when`(s3Presigner.presignGetObject(any(GetObjectPresignRequest::class.java))).thenReturn(presignedGetObjectRequest)

        // when
        val resut = s3StorageService.generateDownloadPresignedUrl(key)

        // then
        assertEquals(presignedUrl, resut)
    }

    @Test
    fun `when generateUploadPresignedUrl is called, should throw CustomException when SdkException occurs`() {
        // Given
        val key = "test-key"
        whenever(s3Presigner.presignPutObject(any((PutObjectPresignRequest::class.java)))).thenThrow(SdkException.builder().message("SDK error").build())

        // When & Then
        assertThrows<CustomException> {
            s3StorageService.generateUploadPresignedUrl(key)
        }
    }

    @Test
    fun `when generateDownloadPresignedUrl is called, should throw CustomException when SdkException occurs`() {
        // Given
        val key = "test-key"
        whenever(s3Presigner.presignGetObject(any(GetObjectPresignRequest::class.java))).thenThrow(SdkException.builder().message("SDK error").build())

        // When & Then
        assertThrows<CustomException> {
            s3StorageService.generateDownloadPresignedUrl(key)
        }
    }

    @Test
    fun `generateUniqueKey should return a unique key`() {
        // when
        val prefix: String = "test-prefix"
        val result = s3StorageService.generateUniqueKey(prefix)

        // then
        assertEquals(true, result.endsWith(".jpg"))
        assertEquals(40, result.length) // prefix-{UUID} + ".jpg" = 36 + 4
    }
}

/*

Why JUnit5 Mockito?

1. 단위 테스트의 격리:
   Mockito를 사용하면 실제 객체의 의존성을 모의 객체(Mock Objects)로 대체할 수 있습니다.
   이를 통해 테스트 대상 코드를 격리시켜, 외부 시스템이나 서비스의 영향 없이 테스트를 수행할 수 있습니다.
2. 행동 검증:
   Mockito는 특정 메소드가 호출되었는지, 몇 번 호출되었는지, 어떤 인자로 호출되었는지 등을 검증할 수 있게 해줍니다.
   이를 통해 테스트 대상 객체가 예상대로 동작하는지 확인할 수 있습니다.
3. 리턴 값 조작:
   Mockito를 사용하면 메소드 호출의 결과를 조작할 수 있습니다.
   예를 들어, 테스트 중인 메소드가 다른 메소드를 호출할 때 특정 값을 반환하도록 설정할 수 있습니다. 이는 복잡한 로직이나 외부 시스템과의 통신 없이도 다양한 시나리오를 테스트할 수 있게 해줍니다.
4. 예외 시뮬레이션:
   Mockito를 사용하면 특정 조건에서 예외를 발생시키도록 설정할 수 있습니다.
   이를 통해 예외 처리 로직이 올바르게 동작하는지 테스트할 수 있습니다.
5. 테스트 코드의 간결성:
   Mockito는 간결하고 이해하기 쉬운 API를 제공합니다.
   이를 통해 테스트 코드를 더 읽기 쉽고 유지보수하기 쉽게 만들 수 있습니다.

 */