package com.example.odysseylog.repository

import com.example.odysseylog.domain.Photo
import com.example.odysseylog.domain.Route
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.transaction.annotation.Transactional

interface PhotoRepository : JpaRepository<Photo, Long>, QuerydslPredicateExecutor<Route> {

    @Modifying
    @Transactional
    @Query("UPDATE Photo p SET p.isCompressed = true WHERE p.url = :photoUrl")
    fun updateIsCompressedByPhotoUrl(photoUrl: String)
}
