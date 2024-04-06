package com.restaurant.decider.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a session-restaurant association.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionRestaurant {
    /**
     * The unique identifier for the session-restaurant association.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The session associated with this session-restaurant association.
     */
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    /**
     * The restaurant associated with this session-restaurant association.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
