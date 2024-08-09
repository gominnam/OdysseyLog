package com.example.odysseylog.repository

import com.example.odysseylog.domain.Spot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import java.util.*

interface SpotRepository: JpaRepository<Spot, Long>, QuerydslPredicateExecutor<Spot> {
    fun findById(spotId: String): Spot?
}