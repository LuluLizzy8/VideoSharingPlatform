package com.youtubeproject.Youtube.Clone.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Model.Video;
import com.youtubeproject.Youtube.Clone.Repository.VideoRepository;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ServiceClientConfiguration;

@Service
@RequiredArgsConstructor
public class VideoService {
	
	private final AWSService awsService;
	private final VideoRepository videoRepository;
	
	public void uploadVideo(MultipartFile multipartFile) {
		String videoURL = awsService.uploadFile(multipartFile);
		
		var video = new Video();
		video.setVideoUrl(videoURL);
		
		videoRepository.save(video);
		
	}

	public VideoDto editVideo(VideoDto videoDto) {
		//find the video by video id 
		Video savedVideo = videoRepository.findById(videoDto.getId())
			.orElseThrow(()->new IllegalArgumentException("video id not found" + videoDto.getId()));
		
		//map the videodto fields to video
		savedVideo.setTitle(videoDto.getTitle());
		savedVideo.setDescription(videoDto.getDescription());
		savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
		
		//save video to database
		videoRepository.save(savedVideo);
		return videoDto;
		
	}

}
