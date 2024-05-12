package com.youtubeproject.Youtube.Clone.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Service.VideoService;
import com.youtubeproject.Youtube.Clone.dto.UploadVideoResponse;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;

/**
 * Controller class for managing video-related endpoints in the YouTube Clone application
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos/")

public class VideoController {
	
	private final VideoService videoService;
	
	/**
	 * Endpoint for uploading a video file
	 * @param file The video file to upload
	 * @return An UploadVideoResponse containing information about the uploaded video
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UploadVideoResponse uploadVideo(@RequestParam("file") MultipartFile file) {
		return videoService.uploadVideo(file);
	}
	
	/**
	 * Endpoint for uploading a thumbnail image for a video
	 * @param file The thumbnail image file to upload
	 * @param videoId The ID of the video associated with the thumbnail
	 * @return The URL of the uploaded thumbnail
	 */
	@PostMapping("/thumbnail")
	@ResponseStatus(HttpStatus.CREATED)
	public String uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId) {
		return videoService.uploadThumbnail(file, videoId);
	}
	
	/**
	 * Endpoint for editing metadata of a video
	 * @param videoDto The DTO containing the updated metadata for the video
	 * @return The DTO containing the edited video metadata
	 */
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public VideoDto editVideoMetadata(@RequestBody VideoDto videoDto) {
		return videoService.editVideo(videoDto);
		
	}
	
	/**
	 * Endpoint for retrieving details of a specific video
	 * @param videoId The ID of the video to retrieve details for
	 * @return The DTO containing the details of the specified video
	 */
	@GetMapping("/{videoId}")
	@ResponseStatus(HttpStatus.OK)
	public VideoDto getVideoDetails(@PathVariable String videoId) {
		return videoService.getVideoDetails(videoId);	
	}
	
	/**
	 * Endpoint for liking a video
	 * @param videoId The ID of the video to like
	 * @return The DTO containing the details of the liked video
	 */
	@PostMapping("{videoId}/like")
	@ResponseStatus(HttpStatus.OK)
	public VideoDto likeVideo(@PathVariable String videoId) {
		return videoService.likeVideo(videoId);
	}
	
	/**
	 * Endpoint for retrieving details of all videos
	 * @return A list of DTOs containing details of all videos
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<VideoDto> getAllVideos(){
		return videoService.getAllVideos();
	}
	
	/**
	 * Endpoint for saving the timestamp position of a video
	 * @param videoId The ID of the video to save the timestamp position for
	 * @param position The timestamp position to save
	 * @return A response entity indicating the success of the operation
	 */
	@PostMapping("savePosition/{videoId}/{position}")
	public ResponseEntity<Void> savePlaybackPosition(@PathVariable String videoId, @PathVariable Float position) {
	    videoService.savePlaybackPosition(videoId, position);
	    return ResponseEntity.ok().build();
	}

	/**
	 * Endpoint for retrieving the timestamp position of a video
	 * @param videoId The ID of the video to retrieve the timestamp position for
	 * @return A response entity containing the timestamp position of the video
	 */
	@GetMapping("getPosition/{videoId}")
	public ResponseEntity<Float> getPlaybackPosition(@PathVariable String videoId) {
	    float position = videoService.getPlaybackPosition(videoId);
	    return ResponseEntity.ok(position);
	}
	
	
}