package com.youtubeproject.Youtube.Clone.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class representing video data in the YouTube Clone application
 * Will be used to map client saved details to video in repository of the Youtube Clone application
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class VideoDto {
	
	/**
     * The unique identifier for the video
     */
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
	private Integer likes;
	
	/**
     * The number of views the video has
     */
	private Integer viewCount;
	
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


}
