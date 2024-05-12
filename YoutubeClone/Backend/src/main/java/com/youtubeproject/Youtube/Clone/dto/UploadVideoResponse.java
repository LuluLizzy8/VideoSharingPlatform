package com.youtubeproject.Youtube.Clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing the response returned after a video upload operation.
 * <p>
 * This class encapsulates the response data for a video upload, including the video's identifier
 * and the URL where the uploaded video can be accessed.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadVideoResponse {
	/**
     * The unique id for the uploaded video.
     */
	private String videoId;
	/**
     * The url for the uploaded video.
     */
	private String videoUrl;
}
