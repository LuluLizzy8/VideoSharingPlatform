package com.youtubeproject.Youtube.Clone.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Service.VideoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/videos")
public class VideoController {
	
	private final VideoService videoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void uploadVideo(@RequestParam("file") MultipartFile file) {
		videoService.uploadVideo(file);
	}
}
