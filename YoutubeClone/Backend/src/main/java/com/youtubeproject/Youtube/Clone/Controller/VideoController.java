package com.youtubeproject.Youtube.Clone.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Model.Comment;
import com.youtubeproject.Youtube.Clone.Service.VideoService;
import com.youtubeproject.Youtube.Clone.dto.CommentDto;
import com.youtubeproject.Youtube.Clone.dto.UploadVideoResponse;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos/")

public class VideoController {
	
	private final VideoService videoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UploadVideoResponse uploadVideo(@RequestParam("file") MultipartFile file) {
		return videoService.uploadVideo(file);
	}
	
	@PostMapping("/thumbnail")
	@ResponseStatus(HttpStatus.CREATED)
	public String uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId) {
		return videoService.uploadThumbnail(file, videoId);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	
	public VideoDto editVideoMetadata(@RequestBody VideoDto videoDto) {
		return videoService.editVideo(videoDto);
		
	}
	
	@GetMapping("/{videoId}")
	@ResponseStatus(HttpStatus.OK)
	public VideoDto getVideoDetails(@PathVariable String videoId) {
		return videoService.getVideoDetails(videoId);	
	}
	
	@PostMapping("{videoId}/like")
	@ResponseStatus(HttpStatus.OK)
	public VideoDto likeVideo(@PathVariable String videoId) {
		return videoService.likeVideo(videoId);
	}
	
	@PostMapping("{videoId}/comment")
	@ResponseStatus(HttpStatus.OK)
	public void addComment(@PathVariable String videoId, @RequestBody CommentDto commentDto) {
		videoService.addComment(videoId, commentDto);
	}
	
	@PostMapping("{videoId}/comment")
	@ResponseStatus(HttpStatus.OK)
	public List<CommentDto> getAllComments(@PathVariable String videoId){
		return videoService.getAllComments(videoId);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<VideoDto> getAllVideos(){
		return videoService.getAllVideos();
	}
	
}
