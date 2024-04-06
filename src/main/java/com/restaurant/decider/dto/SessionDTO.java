package com.restaurant.decider.dto;

import lombok.*;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a session for deciding on a restaurant.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    /**
     * The unique identifier for the session.
     */
    private Long id;

    /**
     * The initiator of the session.
     */
    private UserDTO initiator;

    /**
     * The list of participants in the session.
     */
    private List<UserDTO> participants;

    /**
     * The list of restaurants suggested in the session.
     */
    private List<RestaurantDTO> restaurants;

    /**
     * A flag indicating whether the session has ended.
     */
    private boolean ended;
}