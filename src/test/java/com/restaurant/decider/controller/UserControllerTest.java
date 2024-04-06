package com.restaurant.decider.controller;

import com.restaurant.decider.dto.UserDTO;
import com.restaurant.decider.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.restaurant.decider.test.utils.TestUtils.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDTO> users = Arrays.asList(
                new UserDTO(1L, "User1"),
                new UserDTO(2L, "User2")
        );

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("User1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].username", is("User2")));
    }

    @Test
    void testGetUserById() throws Exception {
        UserDTO user = new UserDTO(1L, "User1");

        Mockito.when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("User1")));
    }

    @Test
    void testAddUser() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "New User");
        UserDTO addedUser = new UserDTO(1L, "New User");

        Mockito.when(userService.addUser(userDTO)).thenReturn(addedUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("New User")));
    }
}
