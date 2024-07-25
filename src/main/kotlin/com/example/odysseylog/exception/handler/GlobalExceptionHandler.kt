package com.example.odysseylog.exception.handler

import com.example.odysseylog.exception.CustomException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler {

    final val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(ex: CustomException): ResponseEntity<Map<String, Any>> {
        return createResponse(ex.errorCode.status, ex.errorCode.name, ex.errorCode.message)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleGeneralException(ex: Exception): ResponseEntity<Map<String, Any>> {
        log.error("Internal Server Error", ex)
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", ex.message ?: "Internal Server Error")
    }

    private fun createResponse(status: Int, error: String, message: String): ResponseEntity<Map<String, Any>> {
        val body = mapOf(
            "timestamp" to Date(),
            "status" to status,
            "error" to error,
            "message" to message
        )
        return ResponseEntity(body, org.springframework.http.HttpStatus.valueOf(500))
    }
}