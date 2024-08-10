package com.example.odysseylog.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Spot {
    @Id
    var id: String? = null

    var latitude: Double? = null
    var longitude: Double? = null
    var memo: String? = null

    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    var route: Route? = null

    @OneToMany(mappedBy = "spot", cascade = [CascadeType.ALL], orphanRemoval = true)
    var photos: MutableList<Photo> = mutableListOf()

    override fun toString(): String {
        return "Spot(id=$id, latitude=$latitude, longitude=$longitude, memo=$memo, createdAt=$createdAt, updatedAt=$updatedAt, photos=${photos.map { it.toString() }})"
    }
}