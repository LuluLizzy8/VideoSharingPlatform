package com.youtubeproject.Youtube.Clone.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Service.VideoService;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos/")

public class VideoController {
	
	private final VideoService videoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void uploadVideo(@RequestParam("file") MultipartFile file) {
		videoService.uploadVideo(file);
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
}
