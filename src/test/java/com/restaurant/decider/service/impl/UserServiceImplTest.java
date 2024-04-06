package com.restaurant.decider.service.impl;

import com.restaurant.decider.dto.UserDTO;
import com.restaurant.decider.exception.GeneralStatusCodeException;
import com.restaurant.decider.model.User;
import com.restaurant.decider.repository.UserRepository;
import com.restaurant.decider.service.impl.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetAllUsers() {
        when(modelMapper.map(any(), eq(UserDTO.class))).thenReturn(new UserDTO());
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);
        List<UserDTO> userDTOs = userService.getAllUsers();
        assertNotNull(userDTOs);
        assertEquals(users.size(), userDTOs.size());
    }

    @Test
    void testGetUserById() {
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        UserDTO userDTOMock = new UserDTO();
        userDTOMock.setId(userId);
        when(modelMapper.map(any(), eq(UserDTO.class))).thenReturn(userDTOMock);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserDTO userDTO = userService.getUserById(userId);
        assertNotNull(userDTO);
        assertEquals(userId, userDTO.getId());
    }

    @Test
    void testGetUserById_UserNotFound() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(GeneralStatusCodeException.class, () -> userService.getUserById(userId));
    }

    @Test
    void testAddUser() {
        when(modelMapper.map(any(), eq(UserDTO.class))).thenReturn(new UserDTO());
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        UserDTO addedUserDTO = userService.addUser(userDTO);
        assertNotNull(addedUserDTO);
    }

    @Test
    void testAddUser_ExceptionThrown() {
        UserDTO userDTO = new UserDTO();
        when(modelMapper.map(userDTO, User.class)).thenThrow(new RuntimeException());
        assertThrows(GeneralStatusCodeException.class, () -> userService.addUser(userDTO));
    }

    @Test
    void testGetAllUsers_ExceptionThrown() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Simulated error"));
        GeneralStatusCodeException exception = assertThrows(GeneralStatusCodeException.class, () -> userService.getAllUsers());
        assertEquals("Failed to retrieve users", exception.getMessage());
        assertEquals("E-1001", exception.getCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }
}
