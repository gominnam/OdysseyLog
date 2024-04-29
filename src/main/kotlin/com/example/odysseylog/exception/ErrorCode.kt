package com.example.odysseylog.exception

enum class ErrorCode(val status: Int, val message: String) {
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    API_CALL_FAILED(1000, "API Call Failed")
}