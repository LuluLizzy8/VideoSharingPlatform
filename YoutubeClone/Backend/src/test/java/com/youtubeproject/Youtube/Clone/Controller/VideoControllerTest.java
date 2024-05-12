package com.youtubeproject.Youtube.Clone.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.mockito.junit.jupiter.MockitoExtension;

import com.youtubeproject.Youtube.Clone.Service.VideoService;
import com.youtubeproject.Youtube.Clone.dto.UploadVideoResponse;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class VideoControllerTest {

    @Mock
    private VideoService videoService;

    @InjectMocks
    private VideoController videoController;

    @BeforeEach
    void setUp() {
        // This is setup before each test
    }

    @Test
    void uploadVideo_ReturnsUploadVideoResponse() {
        // Arrange
        MultipartFile file = new MockMultipartFile("video", "video.mp4", "video/mp4", "video content".getBytes());
        UploadVideoResponse expectedResponse = new UploadVideoResponse("videoId123", "http://example.com/video.mp4");
        when(videoService.uploadVideo(file)).thenReturn(expectedResponse);

        // Act
        UploadVideoResponse response = videoController.uploadVideo(file);

        // Assert
        assertNotNull(response);
        assertEquals("videoId123", response.getVideoId());
        assertEquals("http://example.com/video.mp4", response.getVideoUrl());
        verify(videoService).uploadVideo(file);
    }

    @Test
    void uploadThumbnail_ReturnsThumbnailUrl() {
        MultipartFile file = new MockMultipartFile("image", "thumbnail.jpg", "image/jpeg", "image content".getBytes());
        String expectedUrl = "http://example.com/thumbnail.jpg";
        when(videoService.uploadThumbnail(file, "videoId123")).thenReturn(expectedUrl);

        String url = videoController.uploadThumbnail(file, "videoId123");

        assertEquals(expectedUrl, url);
        verify(videoService).uploadThumbnail(file, "videoId123");
    }

    @Test
    void editVideoMetadata_ReturnsUpdatedVideoDto() {
        VideoDto videoDto = new VideoDto(); // Set appropriate properties
        when(videoService.editVideo(videoDto)).thenReturn(videoDto);

        VideoDto result = videoController.editVideoMetadata(videoDto);

        assertEquals(videoDto, result);
        verify(videoService).editVideo(videoDto);
    }

    @Test
    void getVideoDetails_ReturnsVideoDto() {
        VideoDto expectedDto = new VideoDto(); // Set appropriate properties
        when(videoService.getVideoDetails("videoId123")).thenReturn(expectedDto);

        VideoDto result = videoController.getVideoDetails("videoId123");

        assertEquals(expectedDto, result);
        verify(videoService).getVideoDetails("videoId123");
    }

    @Test
    void likeVideo_ReturnsLikedVideoDto() {
        VideoDto expectedDto = new VideoDto(); // Set appropriate properties
        when(videoService.likeVideo("videoId123")).thenReturn(expectedDto);

        VideoDto result = videoController.likeVideo("videoId123");

        assertEquals(expectedDto, result);
        verify(videoService).likeVideo("videoId123");
    }

    @Test
    void getAllVideos_ReturnsListOfVideoDtos() {
        List<VideoDto> expectedList = Arrays.asList(new VideoDto(), new VideoDto()); // Assume VideoDto is properly set up
        when(videoService.getAllVideos()).thenReturn(expectedList);

        List<VideoDto> result = videoController.getAllVideos();

        assertEquals(expectedList, result);
        verify(videoService).getAllVideos();
    }

    @Test
    void savePlaybackPosition_ReturnsOkStatus() {
        ResponseEntity<Void> response = videoController.savePlaybackPosition("videoId123", 120.5f);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(videoService).savePlaybackPosition("videoId123", 120.5f);
    }

    @Test
    void getPlaybackPosition_ReturnsPosition() {
        when(videoService.getPlaybackPosition("videoId123")).thenReturn(120.5f);

        ResponseEntity<Float> response = videoController.getPlaybackPosition("videoId123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(120.5f, response.getBody());
    }
}
