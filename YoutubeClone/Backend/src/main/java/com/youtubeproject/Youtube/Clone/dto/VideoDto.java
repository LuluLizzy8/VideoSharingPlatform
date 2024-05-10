package com.youtubeproject.Youtube.Clone.dto;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.youtubeproject.Youtube.Clone.Model.Comment;

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
	//private String userId;
	private Integer likes;
	private String videoUrl;
	private Integer viewCount;
	private String thumbnailUrl;
	//private List<Comment> commentList;


}
