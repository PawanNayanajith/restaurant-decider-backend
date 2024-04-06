package com.restaurant.decider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    /**
     * The unique identifier for the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;
}