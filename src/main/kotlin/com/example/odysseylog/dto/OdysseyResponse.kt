package com.example.odysseylog.dto

data class OdysseyResponse(
    val route: RouteResponse = RouteResponse(),
    val spots: List<SpotResponse> = emptyList()
)
