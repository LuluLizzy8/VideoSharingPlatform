package com.youtubeproject.Youtube.Clone.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set; //use set or list?


@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String picture;
    private String email;
    private String sub;
    private Set<String> subscribedTo = new HashSet<>();
    private Set<String> subscribers = new HashSet<>();
    private Set<String> videoHistory = new LinkedHashSet<>();
    private Set<String> likedVideos = new HashSet<>();
	
	public void addToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void unLikeVideo(String videoId) {
        likedVideos.remove(videoId);
    }

    public void addToSubscribedUsers(String userId) {
        subscribedTo.add(userId);
    }

    public void addToSubscribers(String userId) {
        subscribers.add(userId);
    }

}
