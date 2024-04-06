package com.restaurant.decider.service.impl.impl;

import com.restaurant.decider.dto.UserDTO;
import com.restaurant.decider.exception.GeneralStatusCodeException;
import com.restaurant.decider.model.User;
import com.restaurant.decider.repository.UserRepository;
import com.restaurant.decider.service.impl.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation class for the UserService interface.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all users.
     *
     * @return List of all users
     */
    @Override
    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .toList();
        } catch (Exception e) {
            log.error("Failed to retrieve users: {}", e.getMessage());
            throw new GeneralStatusCodeException("Failed to retrieve users", "E-1001", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve
     * @return The retrieved user
     */
    @Override
    public UserDTO getUserById(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
            return modelMapper.map(user, UserDTO.class);
        } catch (Exception e) {
            log.error("Failed to retrieve user with ID {}: {}", id, e.getMessage());
            throw new GeneralStatusCodeException("Failed to retrieve user", "E-1002", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Adds a new user.
     *
     * @param userDTO The user to add
     * @return The added user
     */
    @Override
    public UserDTO addUser(UserDTO userDTO) {
        try {
            User user = modelMapper.map(userDTO, User.class);
            user = userRepository.save(user);
            return modelMapper.map(user, UserDTO.class);
        } catch (Exception e) {
            log.error("Failed to add user: {}", e.getMessage());
            throw new GeneralStatusCodeException("Failed to add user", "E-1003", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
