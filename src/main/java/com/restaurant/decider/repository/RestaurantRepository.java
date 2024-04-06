package com.restaurant.decider.repository;

import com.restaurant.decider.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Restaurant entities in the database.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // This interface inherits CRUD methods from JpaRepository.
}