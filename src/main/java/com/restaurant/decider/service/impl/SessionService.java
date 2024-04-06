package com.restaurant.decider.service.impl;

import com.restaurant.decider.dto.InitiatorDTO;
import com.restaurant.decider.dto.RestaurantDTO;
import com.restaurant.decider.dto.SessionDTO;
import com.restaurant.decider.dto.UserDTO;


/**
 * Service interface for managing session-related operations.
 */
public interface SessionService {

    /**
     * Initiates a new session with the specified initiator.
     *
     * @param initiatorDTO The user initiating the session
     * @return The initiated session
     */
    SessionDTO initiateSession(InitiatorDTO initiatorDTO);

    /**
     * Joins the specified session with the given user.
     *
     * @param sessionId The ID of the session to join
     * @param userDTO   The user joining the session
     * @return True if the user successfully joins the session, false otherwise
     */
    boolean joinSession(Long sessionId, UserDTO userDTO);

    /**
     * Submits a restaurant suggestion to the specified session.
     *
     * @param sessionId     The ID of the session to submit the restaurant to
     * @param restaurantDTO The restaurant suggestion to submit
     * @return True if the restaurant suggestion is successfully submitted, false otherwise
     */
    boolean submitRestaurant(Long sessionId, RestaurantDTO restaurantDTO);

    /**
     * Ends the specified session and randomly selects a restaurant.
     *
     * @param sessionId The ID of the session to end
     * @return The randomly selected restaurant
     */
    RestaurantDTO endSession(Long sessionId);

    /**
     * Retrieves a session by ID.
     *
     * @param id The ID of the session to retrieve
     * @return The retrieved session DTO
     */
    SessionDTO getSessionById(Long id);
}
