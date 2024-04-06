package com.restaurant.decider.service.impl;

import com.restaurant.decider.dto.RestaurantDTO;

import java.util.List;

public interface RestaurantService {

    /**
     * Retrieves all restaurants.
     *
     * @return List of all restaurants
     */
    List<RestaurantDTO> getAllRestaurants();

    /**
     * Retrieves a restaurant by ID.
     *
     * @param id The ID of the restaurant to retrieve
     * @return The retrieved restaurant, or null if not found
     */
    RestaurantDTO getRestaurantById(Long id);

    /**
     * Adds a new restaurant.
     *
     * @param restaurantDTO The restaurant to add
     * @return The added restaurant
     */
    RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO);
}