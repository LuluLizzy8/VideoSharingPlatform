package com.youtubeproject.Youtube.Clone.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.youtubeproject.Youtube.Clone.Model.User;
import com.youtubeproject.Youtube.Clone.Repository.UserRepository;
import com.youtubeproject.Youtube.Clone.Repository.VideoRepository;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Jwt jwt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaim("sub")).thenReturn("user-sub");
    }

    @Test
    void testGetCurrentUser() {
        User expectedUser = new User();
        expectedUser.setSub("user-sub");
        when(userRepository.findBySub("user-sub")).thenReturn(Optional.of(expectedUser));
        User result = userService.getCurrentUser();
        assertNotNull(result);
        assertEquals("user-sub", result.getSub());
    }

    @Test
    void testGetCurrentUser_NotFound() {
        when(userRepository.findBySub("user-sub")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.getCurrentUser());
    }

    @Test
    void testAddToLikedVideos() {
        User user = new User();
        user.setSub("user-sub");
        when(userRepository.findBySub("user-sub")).thenReturn(Optional.of(user));
        userService.addToLikedVideos("video1");
        assertTrue(user.getLikedVideos().contains("video1"));
        verify(userRepository).save(user);
    }

    @Test
    void testRemoveFromLikedVideos() {
        User user = new User();
        user.setSub("user-sub");
        user.addToLikedVideos("video1");
        when(userRepository.findBySub("user-sub")).thenReturn(Optional.of(user));
        userService.removeFromLikedVideos("video1");
        assertFalse(user.getLikedVideos().contains("video1"));
        verify(userRepository).save(user);
    }

    @Test
    void testIfLikedVideo() {
        User user = new User();
        user.setSub("user-sub");
        user.addToLikedVideos("video1");
        when(userRepository.findBySub("user-sub")).thenReturn(Optional.of(user));
        assertTrue(userService.ifLikedVideo("video1"));
        assertFalse(userService.ifLikedVideo("video2"));
    }

    @Test
    void testSubscribeToUser() {
        User currentUser = new User();
        currentUser.setSub("user-sub");
        currentUser.setId("user-id");
        User targetUser = new User();
        targetUser.setSub("target-sub");
        when(userRepository.findBySub("user-sub")).thenReturn(Optional.of(currentUser));
        when(userRepository.findById("target-user-id")).thenReturn(Optional.of(targetUser));
        userService.subscribeToUser("target-user-id");
        
        assertTrue(currentUser.getSubscribedTo().contains("target-user-id"));
        assertTrue(targetUser.getSubscribers().contains("user-id"));
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    void testUnsubscribeToUser() {
        User currentUser = new User();
        currentUser.setSub("user-sub");
        currentUser.setId("user-id");
        currentUser.addToSubscribedUsers("target-user-id");
        User targetUser = new User();
        targetUser.setSub("target-sub");
        targetUser.addToSubscribers("user-id");
        when(userRepository.findBySub("user-sub")).thenReturn(Optional.of(currentUser));
        when(userRepository.findById("target-user-id")).thenReturn(Optional.of(targetUser));
        userService.unsubscribeToUser("target-user-id");
        assertFalse(currentUser.getSubscribedTo().contains("target-user-id"));
        assertFalse(targetUser.getSubscribers().contains("user-id"));
        verify(userRepository, times(2)).save(any(User.class));
    }
}
