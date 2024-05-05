package com.example.odysseylog.repository

import com.example.odysseylog.domain.Photo
import com.example.odysseylog.domain.Route
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface PhotoRepository : JpaRepository<Photo, Long>, QuerydslPredicateExecutor<Route> {
    fun findBySpotId(spotId: Long): List<Photo>
}