package com.youtubeproject.Youtube.Clone.Service;

import java.util.List;
import java.util.Set;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.youtubeproject.Youtube.Clone.Model.User;
import com.youtubeproject.Youtube.Clone.Model.Video;
import com.youtubeproject.Youtube.Clone.Repository.UserRepository;
import com.youtubeproject.Youtube.Clone.Repository.VideoRepository;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final VideoRepository videoRepository;
	
	public User getCurrentUser() {
		String sub = ((Jwt)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");
		return userRepository.findBySub(sub).orElseThrow(() -> new IllegalArgumentException("Cannot find user with sub - " + sub));
	}
	
	public void addToLikedVideos(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.addToLikedVideos(videoId);
		userRepository.save(currentUser);
	}
	
	public void removeFromLikedVideos(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.unLikeVideo(videoId);
		userRepository.save(currentUser);
	}
	
	public boolean ifLikedVideo(String videoId) {
		return getCurrentUser().getLikedVideos().stream().anyMatch(id -> id.equals(videoId));
	}
	
	public void addVideoToHistory(String videoId) {
		User currentUser = getCurrentUser();
		currentUser.addToVideoHistory(videoId);
		userRepository.save(currentUser);
	}

	public void subscribeToUser(String userId) {
		User currentUser = getCurrentUser();
		currentUser.addToSubscribedUsers(userId);
		User targetUser = getUserById(userId);
        targetUser.addToSubscribers(targetUser.getId());
        userRepository.save(currentUser);
        userRepository.save(targetUser);
	}
	
	public void unsubscribeToUser(String userId) {
		User currentUser = getCurrentUser();
		currentUser.removeFromSubscribedUsers(userId);
		User targetUser = getUserById(userId);
        targetUser.removeFromSubscribers(targetUser.getId());
        userRepository.save(currentUser);
        userRepository.save(targetUser);
	}
	
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
		
		return videoDto;
	}

	public List<VideoDto> userHistory() {
	    User user = getCurrentUser();
	    Set<String> historyIds = user.getVideoHistory();
	    return videoRepository.findAllById(historyIds).stream().map(this::mapToVideoDto).toList();
	}


	private User getUserById(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Cannot find user with userId - " + userId));
	}

}
