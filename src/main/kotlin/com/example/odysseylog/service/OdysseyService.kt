package com.example.odysseylog.service

import com.example.odysseylog.dto.OdysseyRequest
import com.example.odysseylog.dto.OdysseyResponse

interface OdysseyService {
    fun createOdyssey(request: OdysseyRequest): OdysseyResponse
}