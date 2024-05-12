package com.youtubeproject.Youtube.Clone.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.youtubeproject.Youtube.Clone.Model.User;
import com.youtubeproject.Youtube.Clone.Model.Video;
import com.youtubeproject.Youtube.Clone.Repository.UserRepository;
import com.youtubeproject.Youtube.Clone.Repository.VideoRepository;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;

/**
 * Service class for managing user-related operations in the YouTube Clone application
 */
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final VideoRepository videoRepository;
	
	/**
	 * Checks if there is a current user
	 * @return true if there is a current user; otherwise false
	 */
	public boolean hasCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt;
    }
	
	/**
	 * Retrieves the current user
	 * @return The current user class 
	 * @throws IllegalArgumentException If the user with the given token is not found
	 */
	public User getCurrentUser() {
		String sub = ((Jwt)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");
		return userRepository.findBySub(sub).orElseThrow(() -> new IllegalArgumentException("Cannot find user with sub - " + sub));
	}
	
	/**
	 * Adds a video to the current user's liked videos
	 * @param videoId The ID of the video to add
	 */
	public void addToLikedVideos(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.addToLikedVideos(videoId);
		userRepository.save(currentUser);
	}
	
	/**
	 * Removes a video from the current user's liked videos
	 * @param videoId The ID of the video to remove
	 */
	public void removeFromLikedVideos(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.unLikeVideo(videoId);
		userRepository.save(currentUser);
	}
	
	/**
	 * Checks if the current user has liked a video
	 * @param videoId The ID of the video to check
	 * @return true if the video is liked by the current user; otherwise false
	 */
	public boolean ifLikedVideo(String videoId) {
		return getCurrentUser().getLikedVideos().stream().anyMatch(id -> id.equals(videoId));
	}
	
	/**
	 * Adds a video to the current user's viewing history
	 * @param videoId The ID of the video to add
	 */
	public void addVideoToHistory(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.addToVideoHistory(videoId);
		userRepository.save(currentUser);
	}
	
	/**
	 * Checks if the current user is subscribed to another user
	 * @param userId The ID of the user to check if current user is subscribed to
	 * @return true if the current user is subscribed to the specified user; otherwise false
	 */
	public boolean isSubscribedToUser(String userId) {
	    User currentUser = getCurrentUser();
	    return currentUser.getSubscribedTo().contains(userId);
	  }

	/**
	 * Subscribes the current user to another user
	 * @param userId The ID of the user to subscribe to
	 */
	public void subscribeToUser(String userId) {
		User currentUser = getCurrentUser();
		currentUser.addToSubscribedUsers(userId);
		User targetUser = getUserById(userId);
        targetUser.addToSubscribers(currentUser.getId());
        userRepository.save(currentUser);
        userRepository.save(targetUser);
	}
	
	/**
	 * Unsubscribes the current user from another user
	 * @param userId The ID of the user to unsubscribe from
	 */
	public void unsubscribeToUser(String userId) {
		User currentUser = getCurrentUser();
		currentUser.removeFromSubscribedUsers(userId);
		User targetUser = getUserById(userId);
        targetUser.removeFromSubscribers(currentUser.getId());
        userRepository.save(currentUser);
        userRepository.save(targetUser);
	}
	
	/**
	 * Maps a video object to a VideoDto object
	 * @param video The video object to map
	 * @return The VideoDto object
	 */
	private VideoDto mapToVideoDto(Video video) {
		VideoDto videoDto = new VideoDto();
		videoDto.setVideoUrl(video.getVideoUrl());
		videoDto.setThumbnailUrl(video.getThumbnailUrl());
		videoDto.setId(video.getId());
		videoDto.setTitle(video.getTitle());
		videoDto.setDescription(video.getDescription());
		videoDto.setLikes(video.getLikes().get());
		videoDto.setViewCount(video.getViewCount().get());
		videoDto.setUserId(video.getUserId());
		videoDto.setUserName(video.getUserName());
		return videoDto;
	}

	/**
	 * Retrieves the viewing history of the current user
	 * @return A list of DTOs containing details of videos in the user's history
	 */
	public List<VideoDto> userHistory() {
	    User user = getCurrentUser();
	    Set<String> historyIds = user.getVideoHistory();
	    return videoRepository.findAllById(historyIds).stream().map(this::mapToVideoDto).toList();
	}

	/**
	 * Retrieves a user by their ID
	 * @param userId The ID of the user to retrieve
	 * @return The user class
	 * @throws IllegalArgumentException If the user with the given ID is not found
	 */
	private User getUserById(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Cannot find user with userId - " + userId));
	}
	
	/**
	 * Retrieves a set of names of users subscribed to by the current user
	 * @return A set of names of subscribed users the current user is subscribed to
	 */
	public Set<String> getSubscriptions(){
		User user = getCurrentUser();
		Set<String> subscribedToIds = user.getSubscribedTo(); 
		return userRepository.findAllById(subscribedToIds).stream()
                .map(User::getFullName)
                .collect(Collectors.toSet());
	}
	
	/**
	 * Retrieves the full name of the current user
	 * @return The full name of the current user
	 */
	public String getName() {
		return getCurrentUser().getFullName();
	}
}
