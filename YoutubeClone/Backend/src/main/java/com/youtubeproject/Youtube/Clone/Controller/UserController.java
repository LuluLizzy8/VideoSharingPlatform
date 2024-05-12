package com.youtubeproject.Youtube.Clone.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.youtubeproject.Youtube.Clone.Service.UserRegistrationService;
import com.youtubeproject.Youtube.Clone.Service.UserService;
import com.youtubeproject.Youtube.Clone.dto.VideoDto;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserRegistrationService userRegistrationService;
	private final UserService userService;
	
	@GetMapping("/register")
	@ResponseStatus(HttpStatus.OK)
	public String register(@AuthenticationPrincipal Jwt jwt) {
	    if (jwt == null) {
	        return "No authentication information found.";
	    }
	    return userRegistrationService.registerUser(jwt.getTokenValue());
	}
	
	@PostMapping("subscribe/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public boolean subscribeToUser(@PathVariable String userId) {
		userService.subscribeToUser(userId);
		return true;
	}
	
	@PostMapping("unsubscribe/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public boolean unsubscribeToUser(@PathVariable String userId) {
		userService.unsubscribeToUser(userId);
		return true;
	}
	
	@GetMapping("/history")
	@ResponseStatus(HttpStatus.OK)
	public List<VideoDto> userHistory(){
		return userService.userHistory();
	}
	
	@GetMapping("/subscriptions")
	@ResponseStatus(HttpStatus.OK)
	public Set<String> getSubscriptions(){
		return userService.getSubscriptions();
	}
	
	@GetMapping("/name")
	@ResponseStatus(HttpStatus.OK)
	public String getName(){
		return userService.getName();
	}
}
