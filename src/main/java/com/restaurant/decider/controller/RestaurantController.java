package com.restaurant.decider.controller;

import com.restaurant.decider.dto.RestaurantDTO;
import com.restaurant.decider.service.impl.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing restaurant-related operations.
 */
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Retrieves all restaurants.
     *
     * @return ResponseEntity containing a list of all restaurants
     */
    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    /**
     * Retrieves a restaurant by ID.
     *
     * @param id The ID of the restaurant to retrieve
     * @return ResponseEntity containing the retrieved restaurant
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
        RestaurantDTO restaurant = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    /**
     * Adds a new restaurant.
     *
     * @param restaurantDTO The restaurant to add
     * @return ResponseEntity containing the added restaurant
     */
    @PostMapping
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO addedRestaurant = restaurantService.addRestaurant(restaurantDTO);
        return new ResponseEntity<>(addedRestaurant, HttpStatus.CREATED);
    }

}