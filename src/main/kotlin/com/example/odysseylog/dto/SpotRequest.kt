package com.example.odysseylog.dto

data class SpotRequest(
    var id: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var memo: String? = null,
    var photos: List<PhotoResponse> = emptyList(),
    var photoSize: Long? = null
)