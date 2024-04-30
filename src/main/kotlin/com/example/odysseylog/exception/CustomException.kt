package com.example.odysseylog.exception

class CustomException(val errorCode: ErrorCode, vararg val args: Any) : RuntimeException(errorCode.message.format(*args))