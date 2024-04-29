package com.example.odysseylog.dto

data class RouteRequest(
    var id: Long? = null,
    var title: String? = null,
    var startLatitude: Double? = null,
    var startLongitude: Double? = null,
    var endLatitude: Double? = null,
    var endLongitude: Double? = null,
    var totalDuration: Long? = null, // 총 소요시간 (단위: 초)
    var totalDistance: Double? = null, // 총 이동거리 (단위: 미터)
    var photoUrl: String? = null
)