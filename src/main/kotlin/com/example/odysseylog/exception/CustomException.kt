package com.example.odysseylog.exception

class CustomException(val errorCode: ErrorCode, vararg args: Any)
    : RuntimeException(String.format(errorCode.message, *args))
