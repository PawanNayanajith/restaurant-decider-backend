package com.restaurant.decider.service.impl.impl;

import com.restaurant.decider.dto.InitiatorDTO;
import com.restaurant.decider.dto.RestaurantDTO;
import com.restaurant.decider.dto.SessionDTO;
import com.restaurant.decider.dto.UserDTO;
import com.restaurant.decider.exception.GeneralStatusCodeException;
import com.restaurant.decider.model.Restaurant;
import com.restaurant.decider.model.Session;
import com.restaurant.decider.model.SessionRestaurant;
import com.restaurant.decider.model.User;
import com.restaurant.decider.repository.RestaurantRepository;
import com.restaurant.decider.repository.SessionRepository;
import com.restaurant.decider.repository.SessionRestaurantRepository;
import com.restaurant.decider.repository.UserRepository;
import com.restaurant.decider.service.impl.SessionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private SessionRestaurantRepository sessionRestaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Random random = new Random();
    private static final String USER_NOT_FOUND_WITH_ID = "User not found with id: ";
    private static final String SESSION_HAS_ALREADY_ENDED = "Session has already ended";
    private static final String SESSION_WITH_ID = "Session with ID ";
    private static final String HAS_ALREADY_ENDED = " has already ended";
    private static final String SESSION_NOT_FOUND_WITH_ID = "Session not found with id: ";

    @Override
    public SessionDTO initiateSession(InitiatorDTO initiatorDTO) {
        try {
            // Retrieve the initiator user from the database
            Long initiatorId = initiatorDTO.getInitiatorId();
            User initiator = userRepository.findById(initiatorId)
                    .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + initiatorId));

            // Create a new session
            Session session = new Session();
            session.setInitiator(initiator);

            // Retrieve and set participants
            List<User> participants = new ArrayList<>();
            List<Long> participantIds = initiatorDTO.getParticipantIds();
            for (Long participantId : participantIds) {
                User participant = userRepository.findById(participantId)
                        .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + participantId));
                participants.add(participant);
            }
            session.setParticipants(participants);

            // Initialize end flag
            session.setEnded(false);

            // Save the session to the database
            session = sessionRepository.save(session);

            log.info("Session initiated with ID: {}", session.getId());

            // Convert Session entity to SessionDTO and return
            return modelMapper.map(session, SessionDTO.class);
        } catch (Exception ex) {
            log.error("Failed to initiate session: {}", ex.getMessage());
            throw new GeneralStatusCodeException("Failed to initiate session", "E-2001", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean joinSession(Long sessionId, UserDTO userDTO) {
        try {
            // Find the session by ID
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new EntityNotFoundException(SESSION_NOT_FOUND_WITH_ID + sessionId));

            // Check if the session has already ended
            if (session.isEnded()) {
                throw new GeneralStatusCodeException(SESSION_HAS_ALREADY_ENDED, "E-2002", SESSION_WITH_ID + sessionId + HAS_ALREADY_ENDED, HttpStatus.BAD_REQUEST);
            }

            // Retrieve the user from the database using the user ID from the userDTO
            Long userId = userDTO.getId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

            // Add the user to the session participants
            session.getParticipants().add(user);

            // Save the updated session to the database
            sessionRepository.save(session);

            log.info("User with ID {} joined session with ID {}", userId, sessionId);

            return true;
        } catch (Exception ex) {
            log.error("Failed to join session: {}", ex.getMessage());
            throw new GeneralStatusCodeException("Failed to join session", "E-2003", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean submitRestaurant(Long sessionId, RestaurantDTO restaurantDTO) {
        try {
            // Find the session by ID
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new EntityNotFoundException(SESSION_NOT_FOUND_WITH_ID + sessionId));

            // Check if the session has already ended
            if (session.isEnded()) {
                log.error("Failed to submit restaurant: Session with ID {} has already ended", sessionId);
                throw new GeneralStatusCodeException(SESSION_HAS_ALREADY_ENDED, "E-2004", SESSION_WITH_ID + sessionId + HAS_ALREADY_ENDED, HttpStatus.BAD_REQUEST);
            }

            Long restaurantId = restaurantDTO.getId();
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id: " + restaurantId));

            SessionRestaurant sessionRestaurant = new SessionRestaurant();
            sessionRestaurant.setSession(session);
            sessionRestaurant.setRestaurant(restaurant);
            sessionRestaurantRepository.save(sessionRestaurant);

            log.info("Restaurant with ID {} submitted for session with ID {}", restaurantId, sessionId);

            return true;
        } catch (EntityNotFoundException ex) {
            log.error("Failed to submit restaurant: {}", ex.getMessage());
            throw new GeneralStatusCodeException("Failed to submit restaurant", "E-2005", ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("Failed to submit restaurant: {}", ex.getMessage());
            throw new GeneralStatusCodeException("Failed to submit restaurant", "E-2006", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public RestaurantDTO endSession(Long sessionId) {
        try {
            // Find the session by ID
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new EntityNotFoundException(SESSION_NOT_FOUND_WITH_ID + sessionId));

            // Check if the session has already ended
            if (session.isEnded()) {
                log.error("Failed to end session: Session with ID {} has already ended", sessionId);
                throw new GeneralStatusCodeException(SESSION_HAS_ALREADY_ENDED, "E-2008", SESSION_WITH_ID + sessionId + HAS_ALREADY_ENDED, HttpStatus.BAD_REQUEST);
            }

            // Select a random restaurant from the session restaurants
            List<SessionRestaurant> sessionRestaurants = session.getSessionRestaurants();
            if (sessionRestaurants.isEmpty()) {
                log.error("Failed to end session: No restaurants submitted for session with ID {}", sessionId);
                throw new GeneralStatusCodeException("No restaurants submitted for session", "E-2007", "No restaurants submitted for session with ID " + sessionId, HttpStatus.BAD_REQUEST);
            }

            int randomIndex = random.nextInt(sessionRestaurants.size());
            Restaurant randomRestaurant = sessionRestaurants.get(randomIndex).getRestaurant();

            // Mark the session as ended
            session.setEnded(true);
            sessionRepository.save(session);

            log.info("Session with ID {} ended", sessionId);

            // Convert the randomly selected restaurant to RestaurantDTO and return
            return modelMapper.map(randomRestaurant, RestaurantDTO.class);
        } catch (EntityNotFoundException ex) {
            log.error("Failed to end session: {}", ex.getMessage());
            throw new GeneralStatusCodeException("Failed to end session", "E-2008", ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("Failed to end session: {}", ex.getMessage());
            throw new GeneralStatusCodeException("Failed to end session", "E-2009", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a session by ID.
     *
     * @param id The ID of the session to retrieve
     * @return The retrieved session DTO
     */
    @Override
    public SessionDTO getSessionById(Long id) {
        try {
            Session session = sessionRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(SESSION_NOT_FOUND_WITH_ID + id));

            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setId(session.getId());
            sessionDTO.setEnded(session.isEnded());
            sessionDTO.setInitiator(modelMapper.map(session.getInitiator(), UserDTO.class));
            sessionDTO.setParticipants(session.getParticipants().stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .toList());

            List<RestaurantDTO> restaurantDTOs = session.getSessionRestaurants().stream()
                    .map(sessionRestaurant -> modelMapper.map(sessionRestaurant.getRestaurant(), RestaurantDTO.class))
                    .toList();
            sessionDTO.setRestaurants(restaurantDTOs);

            return sessionDTO;
        } catch (EntityNotFoundException ex) {
            throw new GeneralStatusCodeException("Failed to retrieve session", "E-2010", ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            throw new GeneralStatusCodeException("Failed to retrieve session", "E-2011", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
