package com.example.odysseylog.exception

class CustomException(val errorCode: ErrorCode) : Throwable(errorCode.message)