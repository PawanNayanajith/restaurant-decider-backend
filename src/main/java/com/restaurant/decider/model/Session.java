package com.restaurant.decider.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing a session for deciding on a restaurant.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    /**
     * The unique identifier for the session.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who initiated the session.
     */
    @ManyToOne
    private User initiator;

    /**
     * The list of users participating in the session.
     */
    @ManyToMany
    private List<User> participants;

    /**
     * The list of session restaurants associated with this session.
     */
    @OneToMany(mappedBy = "session")
    private List<SessionRestaurant> sessionRestaurants;

    /**
     * A flag indicating whether the session has ended.
     */
    private boolean ended;
}
