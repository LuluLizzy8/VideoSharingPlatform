package com.youtubeproject.Youtube.Clone.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Document(value = "Video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
	
	@Id
	private String id;
	private String title;
	private String description;
	private String userId;
	private Integer likes;
	private String videoUrl;
	//private Integer viewCount;
	private String thumbnailUrl;
	private List<Comment> commentList;
	
}
