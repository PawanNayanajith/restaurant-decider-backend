package com.restaurant.decider.controller;

import com.restaurant.decider.dto.UserDTO;
import com.restaurant.decider.service.impl.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing user-related operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing a list of all users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve
     * @return ResponseEntity containing the retrieved user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Adds a new user.
     *
     * @param userDTO The user to add
     * @return ResponseEntity containing the added user
     */
    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        UserDTO addedUser = userService.addUser(userDTO);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }
}
