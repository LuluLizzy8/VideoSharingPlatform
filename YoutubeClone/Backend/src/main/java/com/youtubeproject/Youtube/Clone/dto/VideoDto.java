package com.youtubeproject.Youtube.Clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

//on the client side
//request body
public class VideoDto {
	private String id;
	private String title;
	private String description;
	private String userId;
	private Integer likes;
	private Integer viewCount;
	private String videoUrl;
	private String thumbnailUrl;

}
