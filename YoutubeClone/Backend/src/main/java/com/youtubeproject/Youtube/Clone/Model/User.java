package com.youtubeproject.Youtube.Clone.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set; //use set or list?

/**
 * Represents a user in the YouTube Clone application.
 */
@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	/**
     * The unique identifier for the user
     */
	@Id
    private String id;
    
    /**
     * The full name of the user
     */
    private String fullName;
    
    /**
     * The sub token of the user, used for authentication
     */
    private String sub;
    
    /**
     * The set of user IDs to which this user is subscribed.
     */
    private Set<String> subscribedTo = new HashSet<>();
    
    /**
     * The set of user IDs who are subscribed to this user
     */
    private Set<String> subscribers = new HashSet<>();
    
    /**
     * The set of video IDs representing the user's video viewing history
     */
    private Set<String> videoHistory = new LinkedHashSet<>();
    
    /**
     * The set of video IDs representing the videos liked by the user
     */
    private Set<String> likedVideos = new HashSet<>();
	
    
    /**
     * Adds a video ID to the likedVideos HashSet
     * @param videoId The ID of the video to be added to the liked videos
     */
	public void addToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

	/**
     * Removes a video ID from the likedVideos HashSet
     * @param videoId The ID of the video to be removed from the liked videos
     */
    public void unLikeVideo(String videoId) {
        likedVideos.remove(videoId);
    }

    /**
     * Adds a user ID to the subscribedTo HashSet
     * @param userId The ID of the user to be added to subscribed users
     */
    public void addToSubscribedUsers(String userId) {
        subscribedTo.add(userId);
    }
    
    /**
     * Removes a user ID from the subscribedTo HashSet
     * @param userId The ID of the user to be removed from subscribed users
     */
    public void removeFromSubscribedUsers(String userId) {
    	subscribedTo.remove(userId);
    }

    /**
     * Adds a user ID to the subscribers HashSet
     * @param userId The ID of the user to be added to subscribers
     */
    public void addToSubscribers(String userId) {
        subscribers.add(userId);
    }
    
    /**
     * Removes a user ID from the subscribers HashSet
     * @param userId The ID of the user to be removed from subscribers
     */
    public void removeFromSubscribers(String userId) {
    	subscribers.remove(userId);
    }

    /**
     * Adds a video ID to the videoHistory HashSet
     * @param videoId The ID of the video to be added to the viewing history
     */
	public void addToVideoHistory(String videoId) {
		videoHistory.add(videoId);
		
	}

}
