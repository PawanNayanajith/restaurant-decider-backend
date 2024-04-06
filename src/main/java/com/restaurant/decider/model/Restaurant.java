package com.restaurant.decider.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing a restaurant.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    /**
     * The unique identifier for the restaurant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the restaurant.
     */
    private String name;

    /**
     * The list of session restaurants associated with this restaurant.
     */
    @OneToMany(mappedBy = "restaurant")
    private List<SessionRestaurant> sessionRestaurants;
}
