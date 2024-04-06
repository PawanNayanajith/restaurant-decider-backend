package com.restaurant.decider.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a user.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     */
    private String username;
}
