package com.restaurant.decider.repository;

import com.restaurant.decider.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Session entities in the database.
 */
public interface SessionRepository extends JpaRepository<Session, Long> {
    // This interface inherits CRUD methods from JpaRepository.
}