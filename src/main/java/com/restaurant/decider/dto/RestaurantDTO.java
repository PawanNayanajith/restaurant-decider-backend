package com.restaurant.decider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a restaurant.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    /**
     * The unique identifier for the restaurant.
     */
    private Long id;

    /**
     * The name of the restaurant.
     */
    private String name;
}