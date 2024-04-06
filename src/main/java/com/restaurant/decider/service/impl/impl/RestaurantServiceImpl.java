package com.restaurant.decider.service.impl.impl;

import com.restaurant.decider.dto.RestaurantDTO;
import com.restaurant.decider.exception.GeneralStatusCodeException;
import com.restaurant.decider.model.Restaurant;
import com.restaurant.decider.repository.RestaurantRepository;
import com.restaurant.decider.service.impl.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        try {
            List<Restaurant> restaurants = restaurantRepository.findAll();
            log.info("Retrieved all restaurants");
            return restaurants.stream()
                    .map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class))
                    .toList();
        } catch (Exception ex) {
            log.error("Failed to retrieve restaurants: {}", ex.getMessage());
            throw new GeneralStatusCodeException("Failed to retrieve restaurants", "E-1001", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestaurantDTO getRestaurantById(Long id) {
        try {
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
            if (optionalRestaurant.isPresent()) {
                log.info("Retrieved restaurant with ID: {}", id);
                return modelMapper.map(optionalRestaurant.get(), RestaurantDTO.class);
            } else {
                log.error("Restaurant not found with ID: {}", id);
                throw new GeneralStatusCodeException("Restaurant not found with ID: " + id, "E-1002", "Restaurant with ID " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve restaurant with ID {}: {}", id, ex.getMessage());
            throw new GeneralStatusCodeException("Failed to retrieve restaurant with ID: " + id, "E-1002", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO) {
        try {
            Restaurant restaurant = modelMapper.map(restaurantDTO, Restaurant.class);
            restaurant = restaurantRepository.save(restaurant);
            log.info("Added new restaurant: {}", restaurant.getId());
            return modelMapper.map(restaurant, RestaurantDTO.class);
        } catch (Exception ex) {
            log.error("Failed to add restaurant: {}", ex.getMessage());
            throw new GeneralStatusCodeException("Failed to add restaurant", "E-1003", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
