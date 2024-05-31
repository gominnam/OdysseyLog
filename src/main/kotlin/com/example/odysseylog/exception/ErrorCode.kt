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

    SPOT_NOT_FOUND(2001, "Spot with id %s not found"),
    SPOT_CREATION_FAILED(2002, "Spot creation failed"),
    SPOT_UPDATE_FAILED(2003, "Spot update failed"),
    SPOT_ID_NULL(2004, "Spot id is null"),

    FILE_NAME_NULL(3005, "File name must not be null"),

    API_CALL_FAILED(10001, "API Call Failed")
}