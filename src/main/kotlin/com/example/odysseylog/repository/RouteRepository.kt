package com.example.odysseylog.repository

import com.example.odysseylog.domain.Route
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import java.time.LocalDateTime

interface RouteRepository : JpaRepository<Route, Long>, QuerydslPredicateExecutor<Route> {
    override fun findAll(predicate: Predicate, pageable: Pageable): Page<Route>
    fun findAllByCreatedAtBefore(createdAt: LocalDateTime, pageable: Pageable): Page<Route>
}