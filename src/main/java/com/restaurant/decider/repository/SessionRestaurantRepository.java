package com.restaurant.decider.repository;

import com.restaurant.decider.model.SessionRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing SessionRestaurant entities in the database.
 */
public interface SessionRestaurantRepository extends JpaRepository<SessionRestaurant, Long> {
    // This interface inherits CRUD methods from JpaRepository.
}