package com.restaurant.decider.controller;

import com.restaurant.decider.dto.RestaurantDTO;
import com.restaurant.decider.service.impl.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void testGetAllRestaurants() throws Exception {
        List<RestaurantDTO> restaurants = Arrays.asList(
                new RestaurantDTO(1L, "Restaurant1"),
                new RestaurantDTO(2L, "Restaurant2")
        );

        Mockito.when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Restaurant1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Restaurant2")));
    }

    @Test
    void testGetRestaurantById() throws Exception {
        RestaurantDTO restaurant = new RestaurantDTO(1L, "Restaurant1");

        Mockito.when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant);

        mockMvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Restaurant1")));
    }

    @Test
    void testAddRestaurant() throws Exception {
        RestaurantDTO restaurantDTO = new RestaurantDTO(1L, "New Restaurant");
        RestaurantDTO addedRestaurant = new RestaurantDTO(1L, "New Restaurant");

        Mockito.when(restaurantService.addRestaurant(restaurantDTO)).thenReturn(addedRestaurant);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Restaurant")));
    }

    @Test
    void testGetRestaurantById_NotFound() throws Exception {
        Mockito.when(restaurantService.getRestaurantById(anyLong()))
                .thenThrow(new EntityNotFoundException("Restaurant not found"));

        mockMvc.perform(get("/restaurants/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testAddRestaurant_InternalServerError() throws Exception {
        RestaurantDTO restaurantDTO = new RestaurantDTO(1L, "New Restaurant");

        Mockito.when(restaurantService.addRestaurant(restaurantDTO))
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(restaurantDTO)))
                .andExpect(status().isInternalServerError());
    }
}
