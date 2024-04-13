package com.youtubeproject.Youtube.Clone.Model;

import org.springframework.data.annotation.Id;

public class Comment {
	
	@Id
	private String id;
	private String content;
	private String authorId;
	
}
