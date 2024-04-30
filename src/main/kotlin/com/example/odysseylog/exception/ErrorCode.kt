package com.example.odysseylog.exception

enum class ErrorCode(val status: Int, val message: String) {
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    ROUTE_NOT_FOUND(1001, "Route with id %s not found"),
    ROUTE_CREATION_FAILED(1002, "Route creation failed"),
    ROUTE_UPDATE_FAILED(1003, "Route update failed"),
    ROUTE_ID_NULL(1004, "Route id is null"),

    API_CALL_FAILED(10001, "API Call Failed")
}