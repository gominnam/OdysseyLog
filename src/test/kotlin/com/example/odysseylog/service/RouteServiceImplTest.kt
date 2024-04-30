package com.example.odysseylog.service

import com.example.odysseylog.domain.Route
import com.example.odysseylog.dto.RouteRequest
import com.example.odysseylog.dto.RouteResponse
import com.example.odysseylog.exception.CustomException
import com.example.odysseylog.exception.ErrorCode
import com.example.odysseylog.repository.RouteRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.modelmapper.ModelMapper
import java.util.*

@ExtendWith(MockitoExtension::class)
class RouteServiceImplTest {

    @Mock
    private lateinit var routeRepository: RouteRepository

    @Mock
    private lateinit var modelMapper: ModelMapper

    @InjectMocks
    private lateinit var routeService: RouteServiceImpl

    @Test
    fun `when getRoute is called with valid id, then return the corresponding route`() {
        // given
        val id = 1L
        val route = Route().apply {
            this.id = 1L
            this.title = "route-title"
        }
        val routeResponse = RouteResponse.fromRoute(route)
        `when`(routeRepository.findById(id)).thenReturn(Optional.of(route))

        // when
        val result = routeService.getRoute(id)

        // then
        assertEquals(routeResponse, result)
    }

    @Test
    fun `when getRoute is called with invalid id, then throw NoSuchElementException`() {
        // given
        val id = 1L
        `when`(routeRepository.findById(id)).thenReturn(Optional.empty())

        // when
        val exception = assertThrows<CustomException> {
            routeService.getRoute(id)
        }

        // then
        assertEquals(ErrorCode.ROUTE_NOT_FOUND, exception.errorCode)
    }

    @Test
    fun `when deleteRoute is called with valid id, then the route is deleted`() {
        // given
        val id = 1L
        val route = Route().apply {
            this.id = 1L
            this.title = "route-title"
        }
        `when`(routeRepository.findById(id)).thenReturn(Optional.of(route))

        // when
        routeService.deleteRoute(id)

        // then
        verify(routeRepository).delete(route)
    }

    @Test
    fun `when deleteRoute is called with invalid id, then throw CustomException`() {
        // given
        val id = 1L
        `when`(routeRepository.findById(id)).thenReturn(Optional.empty())

        // when
        val exception = assertThrows<CustomException> {
            routeService.deleteRoute(id)
        }

        // then
        assertEquals(ErrorCode.ROUTE_NOT_FOUND, exception.errorCode)
    }

    @Test
    fun `when createRoute is called, then a new route is saved`() {
        // given
        val routeRequest = RouteRequest(id = 1L, title = "route-title")
        val route = Route().apply {
            this.id = 1L
            this.title = "route-title"
        }
        val routeResponse = RouteResponse(id = 1L, title = "route-title")
        `when`(modelMapper.map(routeRequest, Route::class.java)).thenReturn(route)
        `when`(routeRepository.save(route)).thenReturn(route)

        // when
        val result = routeService.createRoute(routeRequest)

        // then
        assertEquals(routeResponse, result)
        verify(routeRepository).save(route)
    }

    @Captor
    private lateinit var routeCaptor: ArgumentCaptor<Route>

    @Test
    fun `when updateRoute is called, then the existing route is updated`() {
        // given
        val routeRequest = RouteRequest(id = 1L, title = "new-route-title")
        val existingRoute = Route().apply {
            this.id = 1L
            this.title = "route-title"
        }
        val updatedRoute = Route().apply {
            this.id = 1L
            this.title = "new-route-title"
        }
        val routeResponse = RouteResponse.fromRoute(updatedRoute)
        `when`(routeRepository.findById(routeRequest.id!!)).thenReturn(Optional.of(existingRoute))
        `when`(routeRepository.save(any(Route::class.java))).thenReturn(updatedRoute)
        //saveRoute

        // when
        val result = routeService.updateRoute(routeRequest)

        // then
        assertEquals(routeResponse, result)
        verify(routeRepository, times(1)).save(routeCaptor.capture())
        assertEquals(updatedRoute.title, routeCaptor.value.title)
    }
}
