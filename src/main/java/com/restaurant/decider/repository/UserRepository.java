package com.restaurant.decider.repository;

import com.restaurant.decider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing User entities in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // This interface inherits CRUD methods from JpaRepository.
}