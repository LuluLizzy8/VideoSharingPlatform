package com.youtubeproject.Youtube.Clone.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;

public class VideoTest {
    private Video video;

    @BeforeEach
    void setUp() {
        video = new Video();
        video.setId("1");
        video.setTitle("Test Video");
        video.setDescription("A test video description");
        video.setUserId("user123");
        video.setUserName("user123name");
        video.setLikes(new AtomicInteger(0));
        video.setViewCount(new AtomicInteger(0));
        video.setVideoUrl("http://example.com/video.mp4");
        video.setThumbnailUrl("http://example.com/thumbnail.jpg");
    }

    @Test
    void testIncrementLikes() {
        video.incrementLikes();
        assertEquals(1, video.getLikes().get());
    }

    @Test
    void testDecrementLikes() {
        video.incrementLikes();
        video.decrementLikes();
        assertEquals(0, video.getLikes().get());
    }

    @Test
    void testIncrementViewCount() {
        video.incrementViewCount();
        assertEquals(1, video.getViewCount().get());
    }

    @Test
    void testVideoInitialization() {
        assertEquals("1", video.getId());
        assertEquals("Test Video", video.getTitle());
        assertEquals("A test video description", video.getDescription());
        assertEquals("user123", video.getUserId());
        assertEquals("user123name", video.getUserName());
        assertEquals(0, video.getLikes().get());
        assertEquals(0, video.getViewCount().get());
        assertEquals("http://example.com/video.mp4", video.getVideoUrl());
        assertEquals("http://example.com/thumbnail.jpg", video.getThumbnailUrl());
    }
}
