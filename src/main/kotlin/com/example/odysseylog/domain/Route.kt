package com.example.odysseylog.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var title: String? = null
    var startLatitude: Double? = null
    var startLongitude: Double? = null
    var endLatitude: Double? = null
    var endLongitude: Double? = null
    var totalDuration: Long? = null // 총 소요시간 (단위: 초)
    var totalDistance: Double? = null // 총 이동거리 (단위: 미터)
    var photoUrl: String? = null

    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @OneToMany(mappedBy = "route", cascade = [CascadeType.ALL], orphanRemoval = true)
    var spots: MutableList<Spot> = mutableListOf()
}