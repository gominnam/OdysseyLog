package com.example.odysseylog.dto

data class OdysseyRequest(
    val route: RouteRequest,
    val spots: List<SpotRequest>,
    val photos: List<PhotoRequest>
)