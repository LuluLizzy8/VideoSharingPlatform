package com.youtubeproject.Youtube.Clone.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.youtubeproject.Youtube.Clone.Model.Video;
import com.youtubeproject.Youtube.Clone.Repository.VideoRepository;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ServiceClientConfiguration;

@Service
public class VideoService {
	
	private final AWSService awsService;
	private final VideoRepository videoRepository;
	
	public VideoService(AWSService awsService, VideoRepository videoRepository) {
		this.awsService = awsService;
		this.videoRepository = videoRepository;
		}
	
	public void uploadVideo(MultipartFile multipartFile) {
		String videoURL = awsService.uploadFile(multipartFile);
		
		var video = new Video();
		video.setVideoUrl(videoURL);
		
		videoRepository.save(video);
		
	}

}
