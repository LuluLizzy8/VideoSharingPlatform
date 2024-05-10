package com.youtubeproject.Youtube.Clone.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youtubeproject.Youtube.Clone.Service.UserRegistrationService;
import com.youtubeproject.Youtube.Clone.Service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserRegistrationService userRegistrationService;
	private final UserService userService;
	
	@GetMapping("/register")
	public ResponseEntity<String> register(@AuthenticationPrincipal Jwt jwt) {
	    if (jwt == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authentication information found.");
	    }
	    userRegistrationService.registerUser(jwt.getTokenValue());
	    return ResponseEntity.ok("User Registration Successful");
	}
	
	@PostMapping("subscribe/{userId}")
	public boolean subscribeToUser(@PathVariable String userId) {
		userService.subscribeToUser(userId);
		return true;
	}
	
	@PostMapping("unsubscribe/{userId}")
	public boolean unsubscribeToUser(@PathVariable String userId) {
		userService.unsubscribeToUser(userId);
		return true;
	}
}
