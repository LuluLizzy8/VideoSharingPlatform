package com.youtubeproject.Youtube.Clone.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.youtubeproject.Youtube.Clone.Model.User;
import com.youtubeproject.Youtube.Clone.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
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
}
