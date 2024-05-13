package com.youtubeproject.Youtube.Clone.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Model.User;
import com.youtubeproject.Youtube.Clone.Model.Video;
import com.youtubeproject.Youtube.Clone.Repository.VideoRepository;
import com.youtubeproject.Youtube.Clone.dto.UploadVideoResponse;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;

/**
 * Service class for managing video-related operations in the YouTube Clone application
 */
@Service
@RequiredArgsConstructor
public class VideoService {
	
	private final AWSService awsService;
	private final VideoRepository videoRepository;
	private final UserService userService;
	
	/**
	 * Uploads a video file
	 * @param multipartFile The video file to upload
	 * @return An UploadVideoResponse containing information about the uploaded video
	 */
	public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
		String videoURL = awsService.uploadFile(multipartFile);
		
		var video = new Video();
		video.setVideoUrl(videoURL);
		video.setUserId(userService.getCurrentUser().getId());
		video.setUserName(userService.getCurrentUser().getFullName());
		
		var savedVideo = videoRepository.save(video);
		return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
	}

	
	/**
	 * Edits metadata of a video by finding video and mapping the video dto fields to video and saving it to the repository
	 * @param videoDto The DTO containing the updated metadata for the video
	 * @return The DTO containing the edited video metadata
	 */
	public VideoDto editVideo(VideoDto videoDto) {
		var savedVideo = getVideoById(videoDto.getId());
		
		savedVideo.setTitle(videoDto.getTitle());
		savedVideo.setDescription(videoDto.getDescription());
		savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
		
		videoRepository.save(savedVideo);
		return videoDto;
		
	}

	/**
	 * Uploads a thumbnail image for a video
	 * @param file The thumbnail image file to upload
	 * @param videoId The ID of the video associated with the thumbnail
	 * @return The URL of the uploaded thumbnail
	 */
	public String uploadThumbnail(MultipartFile file, String videoId) {
		var savedVideo = getVideoById(videoId);
		
		String thumbnailUrl = awsService.uploadFile(file);
		savedVideo.setThumbnailUrl(thumbnailUrl);
		videoRepository.save(savedVideo);
		return thumbnailUrl;
	}
	
	/**
	 * Retrieves a video by its ID
	 * @param videoId The ID of the video
	 * @return The video object
	 * @throws IllegalArgumentException If the video ID is not found
	 */
	public Video getVideoById(String videoId) {
		return videoRepository.findById(videoId)
			.orElseThrow(()->new IllegalArgumentException("video id not found" + videoId));
	}

	/**
	 * Retrieves details of a specific video
	 * @param videoId The ID of the video to retrieve details for
	 * @return The DTO containing the details of the specified video
	 */
	public VideoDto getVideoDetails(String videoId) {
		Video savedVideo = getVideoById(videoId);
		
		increaseViewCount(videoId);
		if(userService.hasCurrentUser()) {
			userService.addVideoToHistory(videoId);
		}		
		return mapToVideoDto(savedVideo);
	}
	
	/**
	 * Increases the view count of a video
	 * @param videoId The ID of the video to increase the view count for
	 */
	private void increaseViewCount(String videoId) {
		Video videoById = getVideoById(videoId);
		videoById.incrementViewCount();
		videoRepository.save(videoById);
	}

	/**
	 * Likes or unlikes a video
	 * @param videoId The ID of the video to like/unlike
	 * @return The DTO containing the updated details of the liked/unliked video
	 */
	public VideoDto likeVideo(String videoId) {
		Video videoById = getVideoById(videoId);
		
		if(userService.ifLikedVideo(videoId)) {
			videoById.decrementLikes();
			userService.removeFromLikedVideos(videoId);
		} else {
			videoById.incrementLikes();
			userService.addToLikedVideos(videoId);
		}
		videoRepository.save(videoById);
		return mapToVideoDto(videoById);
	}
	
	/**
	 * Maps a Video object to a VideoDto object
	 * @param video The Video object to map
	 * @return The VideoDto object
	 */
	public VideoDto mapToVideoDto(Video video) {
		VideoDto videoDto = new VideoDto();
		videoDto.setVideoUrl(video.getVideoUrl());
		videoDto.setThumbnailUrl(video.getThumbnailUrl());
		videoDto.setId(video.getId());
		videoDto.setTitle(video.getTitle());
		videoDto.setDescription(video.getDescription());
		videoDto.setLikes(video.getLikes().get());
		videoDto.setViewCount(video.getViewCount().get());
		videoDto.setUserId(video.getUserId());
		videoDto.setUserName(video.getUserName());
		videoDto.setPlaybackPositions(video.getPlaybackPositions());		
		return videoDto;
	}

	/**
	 * Retrieves details of all videos
	 * @return A list of DTOs containing details of all videos
	 */
	public List<VideoDto> getAllVideos() {
		return videoRepository.findAll().stream().map(this::mapToVideoDto).toList();
	}
	
	/**
	 * Saves the timestamp position of a video
	 * @param videoId The ID of the video to save the timestamp position for
	 * @param position The timestamp position saved
	 */
	public void savePlaybackPosition(String videoId, float position) {
	    User currentUser = userService.getCurrentUser();
	    Video video = videoRepository.findById(videoId).orElseThrow();
	    video.getPlaybackPositions().put(currentUser.getId(), position);
	    videoRepository.save(video);
	}

	/**
	 * Retrieves the timestamp position of a video
	 * @param videoId The ID of the video to retrieve the timestamp position for
	 * @return The timestamp position of the video
	 */
	public Float getPlaybackPosition(String videoId) {
	    User currentUser = userService.getCurrentUser();
	    Video video = videoRepository.findById(videoId).orElseThrow();
	    return video.getPlaybackPositions().getOrDefault(currentUser.getId(), (float) 0);
	}
}
