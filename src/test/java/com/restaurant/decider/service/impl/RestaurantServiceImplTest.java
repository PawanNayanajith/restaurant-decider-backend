package com.restaurant.decider.service.impl;

import com.restaurant.decider.dto.RestaurantDTO;
import com.restaurant.decider.exception.GeneralStatusCodeException;
import com.restaurant.decider.model.Restaurant;
import com.restaurant.decider.repository.RestaurantRepository;
import com.restaurant.decider.service.impl.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Test
    void testGetAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());
        restaurants.add(new Restaurant());
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        when(modelMapper.map(any(), eq(RestaurantDTO.class))).thenReturn(new RestaurantDTO());
        List<RestaurantDTO> result = restaurantService.getAllRestaurants();
        assertNotNull(result);
        assertEquals(restaurants.size(), result.size());
    }

    @Test
    void testGetRestaurantById() {
        long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        RestaurantDTO restaurantDTOMock = new RestaurantDTO();
        restaurantDTOMock.setId(restaurantId);
        when(modelMapper.map(any(), eq(RestaurantDTO.class))).thenReturn(restaurantDTOMock);
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        RestaurantDTO result = restaurantService.getRestaurantById(restaurantId);
        assertNotNull(result);
        assertEquals(restaurantId, result.getId());
    }

    @Test
    void testGetRestaurantById_RestaurantNotFound() {
        long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());
        assertThrows(GeneralStatusCodeException.class, () -> restaurantService.getRestaurantById(restaurantId));
    }

    @Test
    void testAddRestaurant() {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        Restaurant restaurant = new Restaurant();
        when(modelMapper.map(restaurantDTO, Restaurant.class)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(modelMapper.map(restaurant, RestaurantDTO.class)).thenReturn(new RestaurantDTO());
        RestaurantDTO result = restaurantService.addRestaurant(restaurantDTO);
        assertNotNull(result);
    }

    @Test
    void testAddRestaurant_ExceptionThrown() {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        when(modelMapper.map(restaurantDTO, Restaurant.class)).thenThrow(new RuntimeException());
        assertThrows(GeneralStatusCodeException.class, () -> restaurantService.addRestaurant(restaurantDTO));
    }

    @Test
    void testGetAllRestaurants_ExceptionThrown() {
        when(restaurantRepository.findAll()).thenThrow(new RuntimeException("Simulated error"));
        GeneralStatusCodeException exception = assertThrows(GeneralStatusCodeException.class, () -> restaurantService.getAllRestaurants());
        assertEquals("Failed to retrieve restaurants", exception.getMessage());
        assertEquals("E-1001", exception.getCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }
}
