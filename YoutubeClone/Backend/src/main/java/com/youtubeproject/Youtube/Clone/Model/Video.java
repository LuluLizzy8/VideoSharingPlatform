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
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public String getUserId() {
//		return userId;
//	}
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//	public Integer getLikes() {
//		return likes;
//	}
//	public void setLikes(Integer likes) {
//		this.likes = likes;
//	}
//	public String getVideoUrl() {
//		return videoUrl;
//	}
//	public void setVideoUrl(String videoUrl) {
//		this.videoUrl = videoUrl;
//	}
//	public String getThumbnailUrl() {
//		return thumbnailUrl;
//	}
//	public void setThumbnailUrl(String thumbnailUrl) {
//		this.thumbnailUrl = thumbnailUrl;
//	}
//	public List<Comment> getCommentList() {
//		return commentList;
//	}
//	public void setCommentList(List<Comment> commentList) {
//		this.commentList = commentList;
//	}
	
	
}

