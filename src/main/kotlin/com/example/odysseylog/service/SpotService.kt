package com.example.odysseylog.service

import com.example.odysseylog.domain.Spot
import com.example.odysseylog.dto.SpotRequest
import com.example.odysseylog.dto.SpotResponse

interface SpotService {
    fun getSpot(id: Long): SpotResponse
    fun createSpot(spotRequest: SpotRequest): SpotResponse
    fun getSpotEntity(id: Long): Spot
}