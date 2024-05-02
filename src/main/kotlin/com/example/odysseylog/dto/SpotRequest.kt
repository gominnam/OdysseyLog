package com.example.odysseylog.dto

data class SpotRequest(
    var id: Long? = null,
    var title: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var address: String? = null,
    var memo: String? = null,
    var photoUrls: List<String>? = null
)