package com.youtubeproject.Youtube.Clone.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	
	@Id
	private String id;
	private String userId;
	private String content;
	
}
