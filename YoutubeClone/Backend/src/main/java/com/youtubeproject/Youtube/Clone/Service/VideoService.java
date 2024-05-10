package com.youtubeproject.Youtube.Clone.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Model.Comment;
import com.youtubeproject.Youtube.Clone.Model.Video;
import com.youtubeproject.Youtube.Clone.Repository.VideoRepository;
import com.youtubeproject.Youtube.Clone.dto.CommentDto;
import com.youtubeproject.Youtube.Clone.dto.UploadVideoResponse;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;

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
		
		increaseViewCount(videoId);
		userService.addVideoToHistory(videoId);
		
		return mapToVideoDto(savedVideo);
	}
	
	private void increaseViewCount(String videoId) {
		Video videoById = getVideoById(videoId);
		videoById.incrementViewCount();
		videoRepository.save(videoById);
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
		
		return mapToVideoDto(videoById);
	}
	
	private VideoDto mapToVideoDto(Video video) {
		VideoDto videoDto = new VideoDto();
		videoDto.setVideoUrl(video.getVideoUrl());
		videoDto.setThumbnailUrl(video.getThumbnailUrl());
		videoDto.setId(video.getId());
		videoDto.setTitle(video.getTitle());
		videoDto.setDescription(video.getDescription());
		videoDto.setLikes(video.getLikes().get());
		videoDto.setViewCount(video.getViewCount().get());
		
		return videoDto;
	}

	public void addComment(String videoId, CommentDto commentDto) {
		Video video = getVideoById(videoId);
		Comment comment = new Comment();
		comment.setContent(commentDto.getContent());
		comment.setUserId(commentDto.getUserId());
		video.addComment(comment);
		
		videoRepository.save(video);
	}

	public List<CommentDto> getAllComments(String videoId) {
		Video video = getVideoById(videoId);
		List<Comment> commentList = video.getCommentList();
		
		return commentList.stream().map(this::mapToCommentDto).toList();
	}
	
	private CommentDto mapToCommentDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setContent(comment.getContent());
		commentDto.setUserId(comment.getUserId());
		return null;
	}

	public List<VideoDto> getAllVideos() {
		return videoRepository.findAll().stream().map(this::mapToVideoDto).toList();
	}
}
