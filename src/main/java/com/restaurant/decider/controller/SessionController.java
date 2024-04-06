package com.restaurant.decider.controller;

import com.restaurant.decider.dto.InitiatorDTO;
import com.restaurant.decider.dto.RestaurantDTO;
import com.restaurant.decider.dto.SessionDTO;
import com.restaurant.decider.dto.UserDTO;
import com.restaurant.decider.service.impl.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing session-related operations.
 */
@RestController
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    /**
     * Initiates a new session with the specified initiator.
     *
     * @param initiatorDTO The user initiating the session
     * @return ResponseEntity containing the initiated session
     */
    @PostMapping("/initiate")
    public ResponseEntity<SessionDTO> initiateSession(@RequestBody InitiatorDTO initiatorDTO) {
        SessionDTO initiatedSession = sessionService.initiateSession(initiatorDTO);
        return new ResponseEntity<>(initiatedSession, HttpStatus.CREATED);
    }

    /**
     * Joins the specified session with the given user.
     *
     * @param sessionId The ID of the session to join
     * @param userDTO   The user joining the session
     * @return ResponseEntity indicating success or failure of joining the session
     */
    @PostMapping("/{sessionId}/join")
    public ResponseEntity<Void> joinSession(@PathVariable Long sessionId, @RequestBody UserDTO userDTO) {
        sessionService.joinSession(sessionId, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Submits a restaurant suggestion to the specified session.
     *
     * @param sessionId     The ID of the session to submit the restaurant to
     * @param restaurantDTO The restaurant suggestion to submit
     * @return ResponseEntity indicating success or failure of submitting the restaurant
     */
    @PostMapping("/{sessionId}/submit-restaurant")
    public ResponseEntity<Void> submitRestaurant(@PathVariable Long sessionId, @RequestBody RestaurantDTO restaurantDTO) {
        sessionService.submitRestaurant(sessionId, restaurantDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Ends the specified session and randomly selects a restaurant.
     *
     * @param sessionId The ID of the session to end
     * @return ResponseEntity containing the randomly selected restaurant
     */
    @PostMapping("/{sessionId}/end")
    public ResponseEntity<RestaurantDTO> endSession(@PathVariable Long sessionId) {
        RestaurantDTO selectedRestaurant = sessionService.endSession(sessionId);
        return new ResponseEntity<>(selectedRestaurant, HttpStatus.OK);
    }

    /**
     * Retrieves a session by ID.
     *
     * @param id The ID of the session to retrieve
     * @return ResponseEntity containing the retrieved session DTO or an error message
     */
    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable Long id) {
        SessionDTO sessionDTO = sessionService.getSessionById(id);
        return ResponseEntity.ok(sessionDTO);
    }
}
