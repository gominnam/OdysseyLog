package com.example.odysseylog.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var order: Long? = null

    @Column(length = 1000)
    var url: String? = null

    @Column
    var isCompressed: Boolean = false

    @Transient
    var presignedUrl: String? = null

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    var spot: Spot? = null

    override fun toString(): String {
        return "Photo(id=$id, order=$order, url=$url, presignedUrl=$presignedUrl, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}