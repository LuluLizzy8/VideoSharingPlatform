package com.youtubeproject.Youtube.Clone.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
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
	private String userId;
	private String userName;
	private AtomicInteger likes = new AtomicInteger(0);
	private String videoUrl;
	private AtomicInteger viewCount  = new AtomicInteger(0);
	private String thumbnailUrl;
	private Map<String, Float> playbackPositions = new HashMap<>();
	
	public void incrementLikes() {
		likes.incrementAndGet();
	}
	
	public void decrementLikes() {
		likes.decrementAndGet();
	}

	public void incrementViewCount() {
		viewCount.incrementAndGet();
	}

}

