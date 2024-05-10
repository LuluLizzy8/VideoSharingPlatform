package com.youtubeproject.Youtube.Clone.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Model.Video;
import com.youtubeproject.Youtube.Clone.Repository.VideoRepository;
import com.youtubeproject.Youtube.Clone.dto.UploadVideoResponse;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ServiceClientConfiguration;

@Service
@RequiredArgsConstructor
public class VideoService {
	
	private final AWSService awsService;
	private final VideoRepository videoRepository;
	private final UserService userService;
	
	public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
		String videoURL = awsService.uploadFile(multipartFile);
		
		var video = new Video();
		video.setVideoUrl(videoURL);
		
		var savedVideo = videoRepository.save(video);
		
		return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
		
	}

	public VideoDto editVideo(VideoDto videoDto) {
		//find the video by video id 
		var savedVideo = getVideoById(videoDto.getId());
		
		//map the videodto fields to video
		savedVideo.setTitle(videoDto.getTitle());
		savedVideo.setDescription(videoDto.getDescription());
		savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
		
		//save video to database
		videoRepository.save(savedVideo);
		return videoDto;
		
	}

	public String uploadThumbnail(MultipartFile file, String videoId) {
		var savedVideo = getVideoById(videoId);
		
		String thumbnailUrl = awsService.uploadFile(file);
		savedVideo.setThumbnailUrl(thumbnailUrl);
		videoRepository.save(savedVideo);
		return thumbnailUrl;
	}
	
	public Video getVideoById(String videoId) {
		return videoRepository.findById(videoId)
			.orElseThrow(()->new IllegalArgumentException("video id not found" + videoId));
	}

	public VideoDto getVideoDetails(String videoId) {
		Video savedVideo = getVideoById(videoId);
		
		VideoDto videoDto = new VideoDto();
		videoDto.setVideoUrl(savedVideo.getVideoUrl());
		videoDto.setThumbnailUrl(savedVideo.getThumbnailUrl());
		videoDto.setId(savedVideo.getId());
		videoDto.setTitle(savedVideo.getTitle());
		videoDto.setDescription(savedVideo.getDescription());
		
		return videoDto;
	}
	
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
		
		VideoDto videoDto = new VideoDto();
		videoDto.setVideoUrl(videoById.getVideoUrl());
		videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
		videoDto.setId(videoById.getId());
		videoDto.setTitle(videoById.getTitle());
		videoDto.setDescription(videoById.getDescription());
		videoDto.setLikes(videoById.getLikes().get());
		
		return videoDto;
	}

}
