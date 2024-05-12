package com.youtubeproject.Youtube.Clone.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a video in the YouTube Clone application
 */
@Document(value = "Video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
	
	/**
     * The unique identifier for the video
     */
	@Id
	private String id;
	
	/**
     * The title of the video
     */
	private String title;
	
	/**
     * The description of the video
     */
	private String description;
	
	/**
     * The user ID of the uploader of the video
     */
	private String userId;
	
	/**
     * The username of the uploader of the video
     */
	private String userName;
	
	/**
     * The number of likes the video received
     */
	private AtomicInteger likes = new AtomicInteger(0);
	
	/**
     * The number of views the video has
     */
	private AtomicInteger viewCount  = new AtomicInteger(0);
	
	/**
     * The URL of the video
     */
	private String videoUrl;
	
	/**
     * The URL of the video thumbnail
     */
	private String thumbnailUrl;
	
	/**
     * A map of user IDs and timestamps in a video
     */
	private Map<String, Float> playbackPositions = new HashMap<>();
	
	/**
     * Increments the number of likes for the video.
     */
	public void incrementLikes() {
		likes.incrementAndGet();
	}
	
	/**
     * Decrements the number of likes for the video.
     */
	public void decrementLikes() {
		likes.decrementAndGet();
	}

	/**
     * Increments the view count for the video.
     */
	public void incrementViewCount() {
		viewCount.incrementAndGet();
	}

}

