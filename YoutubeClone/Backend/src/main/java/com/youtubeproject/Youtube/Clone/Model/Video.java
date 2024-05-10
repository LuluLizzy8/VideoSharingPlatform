package com.youtubeproject.Youtube.Clone.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "Video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
	
	@Id
	private String id;
	private String title;
	private String description;
	// private String userId;
	private AtomicInteger likes = new AtomicInteger(0);
	private String videoUrl;
	private AtomicInteger viewCount  = new AtomicInteger(0);
	private String thumbnailUrl;
	private List<Comment> commentList = new ArrayList<>();
	
	public void incrementLikes() {
		likes.incrementAndGet();
	}
	
	public void decrementLikes() {
		likes.decrementAndGet();
	}

	public void incrementViewCount() {
		viewCount.incrementAndGet();
	}

	public void addComment(Comment comment) {
		commentList.add(comment);
	}
	
}

