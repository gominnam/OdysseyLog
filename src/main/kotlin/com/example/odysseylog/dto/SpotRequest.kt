package com.example.odysseylog.dto

data class SpotRequest(
    var id: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var memo: String? = null,
    var photoSize: Long? = null
)