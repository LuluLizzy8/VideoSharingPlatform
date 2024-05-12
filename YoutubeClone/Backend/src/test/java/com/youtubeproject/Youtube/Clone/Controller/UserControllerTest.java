package com.youtubeproject.Youtube.Clone.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.oauth2.jwt.Jwt;
import org.mockito.junit.jupiter.MockitoExtension;

import com.youtubeproject.Youtube.Clone.Service.UserRegistrationService;
import com.youtubeproject.Youtube.Clone.Service.UserService;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserRegistrationService userRegistrationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private Jwt jwt;

    @BeforeEach
    void setUp() {
    }

    @Test
    void register_NewUser_ReturnsExpectedMessage() {
        when(jwt.getTokenValue()).thenReturn("token123");
        when(userRegistrationService.registerUser("token123")).thenReturn("Registration successful");

        String result = userController.register(jwt);

        assertEquals("Registration successful", result);
    }

    @Test
    void register_NoJwt_ReturnsErrorMessage() {
        String result = userController.register(null);

        assertEquals("No authentication information found.", result);
    }

    @Test
    void isSubscribedToUser_ReturnsTrue() {
        when(userService.isSubscribedToUser("123")).thenReturn(true);

        assertTrue(userController.isSubscribedToUser("123"));
    }

    @Test
    void subscribeToUser_ReturnsTrue() {
        assertTrue(userController.subscribeToUser("123"));
        verify(userService).subscribeToUser("123");
    }

    @Test
    void unsubscribeToUser_ReturnsTrue() {
        assertTrue(userController.unsubscribeToUser("123"));
        verify(userService).unsubscribeToUser("123");
    }

    @Test
    void userHistory_ReturnsListOfVideos() {
        VideoDto video = new VideoDto(); // Assuming VideoDto has appropriate fields set up
        when(userService.userHistory()).thenReturn(Arrays.asList(video));

        List<VideoDto> history = userController.userHistory();

        assertFalse(history.isEmpty());
        assertEquals(1, history.size());
        assertEquals(video, history.get(0));
    }

    @Test
    void getSubscriptions_ReturnsSetOfNames() {
        Set<String> subscriptions = new HashSet<>(Arrays.asList("John Doe", "Jane Doe"));
        when(userService.getSubscriptions()).thenReturn(subscriptions);

        Set<String> result = userController.getSubscriptions();

        assertEquals(2, result.size());
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("Jane Doe"));
    }

    @Test
    void getName_ReturnsFullName() {
        when(userService.getName()).thenReturn("John Doe");

        String name = userController.getName();

        assertEquals("John Doe", name);
    }
}
