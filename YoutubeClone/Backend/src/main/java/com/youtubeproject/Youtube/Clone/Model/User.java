package com.youtubeproject.Youtube.Clone.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import java.util.HashSet;
import java.util.Set; //use set or list?


@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	private String id;
	private String userName; 
	private String email;
	private String password;
	private List<String> subscribedTo;
	private List<String> subscribers; 
	private Set<String> likedVideos = new HashSet<>();
    private Set<String> comments = new HashSet<>();
	
	public void addToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void unLikeVideo(String videoId) {
        likedVideos.remove(videoId);
    }

    public void commentVideo(String videoId) {
        comments.add(videoId);
    }
    
    public void deleteComment(String videoId) {
        comments.remove(videoId);
    }

    public void addToSubscribedUsers(String userId) {
        subscribedTo.add(userId);
    }

    public void addToSubscribers(String userId) {
        subscribers.add(userId);
    }

}
