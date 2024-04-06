package com.restaurant.decider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing an initiator for a session.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitiatorDTO {
    /**
     * The ID of the user who initiates the session.
     */
    private Long initiatorId;

    /**
     * The list of IDs of participants invited to the session.
     */
    private List<Long> participantIds;
}