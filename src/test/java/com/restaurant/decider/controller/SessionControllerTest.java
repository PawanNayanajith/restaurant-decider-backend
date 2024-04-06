package com.restaurant.decider.controller;

import com.restaurant.decider.dto.InitiatorDTO;
import com.restaurant.decider.dto.RestaurantDTO;
import com.restaurant.decider.dto.SessionDTO;
import com.restaurant.decider.dto.UserDTO;
import com.restaurant.decider.service.impl.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static com.restaurant.decider.test.utils.TestUtils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @Test
    void testInitiateSession() throws Exception {
        InitiatorDTO initiatorDTO = new InitiatorDTO(1L, Arrays.asList(2L, 3L));
        SessionDTO initiatedSession = new SessionDTO(1L, new UserDTO(1L, "Initiator"),
                Arrays.asList(new UserDTO(2L, "Participant1"), new UserDTO(3L, "Participant2")),
                new ArrayList<>(), false);

        Mockito.when(sessionService.initiateSession(initiatorDTO)).thenReturn(initiatedSession);

        mockMvc.perform(post("/sessions/initiate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(initiatorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.initiator.id").value(1))
                .andExpect(jsonPath("$.participants[0].id").value(2))
                .andExpect(jsonPath("$.participants[1].id").value(3));
    }

    @Test
    void testJoinSession() throws Exception {
        UserDTO userDTO = new UserDTO(2L, "Participant1");

        Mockito.when(sessionService.joinSession(anyLong(), any(UserDTO.class))).thenReturn(true);

        mockMvc.perform(post("/sessions/1/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testSubmitRestaurant() throws Exception {
        RestaurantDTO restaurantDTO = new RestaurantDTO(1L, "Restaurant1");

        Mockito.when(sessionService.submitRestaurant(anyLong(), any(RestaurantDTO.class))).thenReturn(true);

        mockMvc.perform(post("/sessions/1/submit-restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testEndSession() throws Exception {
        RestaurantDTO selectedRestaurant = new RestaurantDTO(1L, "Restaurant1");

        Mockito.when(sessionService.endSession(anyLong())).thenReturn(selectedRestaurant);

        mockMvc.perform(post("/sessions/1/end"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Restaurant1"));
    }

    @Test
    void testGetSessionById() throws Exception {
        SessionDTO sessionDTO = new SessionDTO(1L, new UserDTO(1L, "Initiator"),
                Arrays.asList(new UserDTO(2L, "Participant1"),
                        new UserDTO(3L, "Participant2")), new ArrayList<>(), false);

        Mockito.when(sessionService.getSessionById(1L)).thenReturn(sessionDTO);

        mockMvc.perform(get("/sessions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.initiator.id").value(1))
                .andExpect(jsonPath("$.participants[0].id").value(2))
                .andExpect(jsonPath("$.participants[1].id").value(3));
    }
}
