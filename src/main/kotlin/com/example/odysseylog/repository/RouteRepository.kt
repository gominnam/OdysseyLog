package com.example.odysseylog.repository

import com.example.odysseylog.domain.Route
import com.example.odysseylog.domain.Spot
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import java.time.LocalDateTime

interface RouteRepository : JpaRepository<Route, Long>, QuerydslPredicateExecutor<Route> {
    override fun findAll(predicate: Predicate, pageable: Pageable): Page<Route>

    fun findAllByCreatedAtBefore(createdAt: LocalDateTime, pageable: Pageable): Page<Route>

    @Query("SELECT r FROM Route r JOIN FETCH r.spots WHERE r.id = :id")
    fun findRouteWithSpots(id: Long): Route?

    @Query("SELECT s FROM Spot s JOIN FETCH s.photos WHERE s.route.id = :routeId")
    fun findSpotsWithPhotosByRouteId(routeId: Long): List<Spot>
}