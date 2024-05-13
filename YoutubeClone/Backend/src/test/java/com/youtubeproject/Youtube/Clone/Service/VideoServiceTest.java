package com.youtubeproject.Youtube.Clone.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Model.User;
import com.youtubeproject.Youtube.Clone.Model.Video;
import com.youtubeproject.Youtube.Clone.Repository.VideoRepository;
import com.youtubeproject.Youtube.Clone.dto.UploadVideoResponse;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;

    @Mock
    private AWSService awsService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private UserService userService;

    @Mock
    private Video video;

    @Mock
    private User user;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(userService.getCurrentUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-id");
        when(user.getFullName()).thenReturn("John Doe");
    }

    @Test
    void testUploadVideo() {
        when(file.getOriginalFilename()).thenReturn("video.mp4");
        when(awsService.uploadFile(file)).thenReturn("http://example.com/video.mp4");
        when(videoRepository.save(any(Video.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UploadVideoResponse response = videoService.uploadVideo(file);

        assertNotNull(response);
        assertEquals("http://example.com/video.mp4", response.getVideoUrl());
        verify(videoRepository).save(any(Video.class));
    }

    @Test
    void testGetVideoDetails() {
        String videoId = "1";
        Video video = new Video();
        video.setId(videoId);
        video.setTitle("Sample Video");
        video.setDescription("Sample Description");
        video.setVideoUrl("http://s3.video.url");
        video.setThumbnailUrl("http://s3.thumbnail.url");

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));
        when(userService.hasCurrentUser()).thenReturn(true);
        doNothing().when(userService).addVideoToHistory(videoId);

        VideoDto result = videoService.getVideoDetails(videoId);

        assertNotNull(result);
        assertEquals(videoId, result.getId());
        assertEquals("Sample Video", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertEquals("http://s3.video.url", result.getVideoUrl());
        assertEquals("http://s3.thumbnail.url", result.getThumbnailUrl());

        verify(videoRepository, times(1)).save(any(Video.class)); // To check if view count is incremented
        verify(userService, times(1)).addVideoToHistory(videoId); // Ensure it adds video to history for logged in user
    }

    
    @Test
    void testLikeVideo() {
        Video video = new Video();
        video.setId("1");

        when(videoRepository.findById("1")).thenReturn(Optional.of(video));
        when(userService.ifLikedVideo("1")).thenReturn(false);
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        VideoDto videoDto = videoService.likeVideo("1");

        assertNotNull(videoDto);
        assertEquals(1, videoDto.getLikes());
    }

    @Test
    void testUnlikeVideo() {
        Video video = new Video();
        video.setId("1");
        video.setLikes(new java.util.concurrent.atomic.AtomicInteger(1));  // Assume video initially has 1 like

        when(videoRepository.findById("1")).thenReturn(Optional.of(video));
        when(userService.ifLikedVideo("1")).thenReturn(true);  // User has already liked the video
        when(videoRepository.save(any(Video.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VideoDto videoDto = videoService.likeVideo("1");

        assertNotNull(videoDto);
        assertEquals(0, videoDto.getLikes());  // Expect likes to decrement
        verify(userService).removeFromLikedVideos("1");  // Ensure we remove the like
        verify(videoRepository).save(video);
    }

    @Test
    void testUploadThumbnail() {
        when(awsService.uploadFile(file)).thenReturn("http://example.com/thumbnail.jpg");
        when(videoRepository.findById("video-id")).thenReturn(Optional.of(video));

        String thumbnailUrl = videoService.uploadThumbnail(file, "video-id");

        assertEquals("http://example.com/thumbnail.jpg", thumbnailUrl);
        verify(video).setThumbnailUrl("http://example.com/thumbnail.jpg");
        verify(videoRepository).save(video);
    }

    @Test
    void testSavePlaybackPosition() {
        when(videoRepository.findById("video-id")).thenReturn(Optional.of(video));
        videoService.savePlaybackPosition("video-id", 120f);

        verify(video).getPlaybackPositions();
        verify(videoRepository).save(video);
    }

    @Test
    void testGetPlaybackPosition() {
        when(videoRepository.findById("video-id")).thenReturn(Optional.of(video));
        when(video.getPlaybackPositions()).thenReturn(Map.of("user-id", 120f));

        float position = videoService.getPlaybackPosition("video-id");

        assertEquals(120f, position);
    }
    
    @Test
    void testEditVideo() {
        VideoDto videoDto = new VideoDto();
        videoDto.setId("1");
        videoDto.setTitle("New Title");
        videoDto.setDescription("New Description");
        videoDto.setThumbnailUrl("http://s3.thumbnail.url");

        Video video = new Video();
        video.setId("1");
        video.setTitle("Old Title");
        video.setDescription("Old Description");

        when(videoRepository.findById("1")).thenReturn(Optional.of(video));
        when(videoRepository.save(any(Video.class))).thenReturn(video);

        VideoDto updatedVideoDto = videoService.editVideo(videoDto);

        assertNotNull(updatedVideoDto);
        assertEquals("New Title", updatedVideoDto.getTitle());
        assertEquals("New Description", updatedVideoDto.getDescription());
        assertEquals("http://s3.thumbnail.url", updatedVideoDto.getThumbnailUrl());
    }
    
    void testGetAllVideos() {
        Video video1 = new Video();
        video1.setId("1");
        video1.setTitle("Video Title 1");
        video1.setVideoUrl("url1");
        Video video2 = new Video();
        video2.setId("2");
        video2.setTitle("Video Title 2");
        video2.setVideoUrl("url2");
        List<Video> videos = Arrays.asList(video1, video2);
        when(videoRepository.findAll()).thenReturn(videos);
        VideoDto videoDto1 = new VideoDto();
        videoDto1.setId("1");
        videoDto1.setTitle("Video Title 1");
        videoDto1.setVideoUrl("url1");
        VideoDto videoDto2 = new VideoDto();
        videoDto2.setId("2");
        videoDto2.setTitle("Video Title 2");
        videoDto2.setVideoUrl("url2");
        when(videoService.mapToVideoDto(video1)).thenReturn(videoDto1);
        when(videoService.mapToVideoDto(video2)).thenReturn(videoDto2);

        List<VideoDto> result = videoService.getAllVideos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Video Title 1", result.get(0).getTitle());
        assertEquals("Video Title 2", result.get(1).getTitle());

        verify(videoRepository).findAll();
        verify(videoService).mapToVideoDto(video1);
        verify(videoService).mapToVideoDto(video2);
    }
}
