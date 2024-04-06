package com.restaurant.decider.service.impl;

import com.restaurant.decider.dto.UserDTO;

import java.util.List;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {

    /**
     * Retrieves all users.
     *
     * @return List of all users
     */
    List<UserDTO> getAllUsers();

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve
     * @return The retrieved user, or null if not found
     */
    UserDTO getUserById(Long id);

    /**
     * Adds a new user.
     *
     * @param userDTO The user to add
     * @return The added user
     */
    UserDTO addUser(UserDTO userDTO);
}

