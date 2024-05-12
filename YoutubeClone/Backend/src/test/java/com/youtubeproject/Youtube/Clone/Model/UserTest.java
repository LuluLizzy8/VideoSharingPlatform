package com.youtubeproject.Youtube.Clone.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setFullName("John Doe");
        user.setSub("123456789");
        user.setSubscribedTo(new HashSet<>());
        user.setSubscribers(new HashSet<>());
        user.setVideoHistory(new HashSet<>());
        user.setLikedVideos(new HashSet<>());
    }

    @Test
    void testAddToLikedVideos() {
        String videoId = "video123";
        user.addToLikedVideos(videoId);
        assertTrue(user.getLikedVideos().contains(videoId));
    }

    @Test
    void testUnLikeVideo() {
        String videoId = "video123";
        user.addToLikedVideos(videoId);
        user.unLikeVideo(videoId);
        assertFalse(user.getLikedVideos().contains(videoId));
    }

    @Test
    void testAddToSubscribedUsers() {
        String userId = "user456";
        user.addToSubscribedUsers(userId);
        assertTrue(user.getSubscribedTo().contains(userId));
    }

    @Test
    void testRemoveFromSubscribedUsers() {
        String userId = "user456";
        user.addToSubscribedUsers(userId);
        user.removeFromSubscribedUsers(userId);
        assertFalse(user.getSubscribedTo().contains(userId));
    }

    @Test
    void testAddToSubscribers() {
        String userId = "user789";
        user.addToSubscribers(userId);
        assertTrue(user.getSubscribers().contains(userId));
    }

    @Test
    void testRemoveFromSubscribers() {
        String userId = "user789";
        user.addToSubscribers(userId);
        user.removeFromSubscribers(userId);
        assertFalse(user.getSubscribers().contains(userId));
    }

    @Test
    void testAddToVideoHistory() {
        String videoId = "video321";
        user.addToVideoHistory(videoId);
        assertTrue(user.getVideoHistory().contains(videoId));
    }
}
