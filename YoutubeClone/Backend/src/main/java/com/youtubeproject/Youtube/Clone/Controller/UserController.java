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

/**
 * Controller responsible for handling user related API requests.
 * Provides endpoints for user registration, subscription management, and retrieval of user data.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserRegistrationService userRegistrationService;
	private final UserService userService;
	
	/**
     * Registers a new user using the JWT token provided with the request.
     *
     * @param jwt The JWT token containing the user's authentication data.
     * @return A string response indicating the registration outcome.
     */
	@GetMapping("/register")
	@ResponseStatus(HttpStatus.OK)
	public String register(@AuthenticationPrincipal Jwt jwt) {
	    if (jwt == null) {
	        return "No authentication information found.";
	    }
	    return userRegistrationService.registerUser(jwt.getTokenValue());
	}
	
	/**
     * Checks if the current user is subscribed to another user.
     *
     * @param userId The ID of the user that current user is checking to see if is subscribed to.
     * @return true if subscribed to target user, false otherwise.
     */
	@GetMapping("/isSubscribed/{userId}")
	  public boolean isSubscribedToUser(@PathVariable String userId) {
	    return userService.isSubscribedToUser(userId);
	  }
	
	/**
     * Subscribes the current user to target user.
    *
    * @param userId The id of the user to subscribe to.
    * @return true if the subscribe was successful, false otherwise.
    */
	@PostMapping("subscribe/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public boolean subscribeToUser(@PathVariable String userId) {
		userService.subscribeToUser(userId);
		return true;
	}
	
	/**
     * Unsubscribes the current user from target user.
     *
     * @param userId The id of the user to unsubscribe from.
     * @return true if the unsubscribe was successful, false otherwise.
     */
	@PostMapping("unsubscribe/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public boolean unsubscribeToUser(@PathVariable String userId) {
		userService.unsubscribeToUser(userId);
		return true;
	}
	
	/**
     * Retrieves a list of videos previously watched by current user.
     *
     * @return A list of {@link VideoDto} for the user's video history.
     */
	@GetMapping("/history")
	@ResponseStatus(HttpStatus.OK)
	public List<VideoDto> userHistory(){
		return userService.userHistory();
	}
	
	/**
     * Retrieves a set of full names to which the current user is subscribed.
     *
     * @return A set of full names.
     */
	@GetMapping("/subscriptions")
	@ResponseStatus(HttpStatus.OK)
	public Set<String> getSubscriptions(){
		return userService.getSubscriptions();
	}
	
	/**
     * Retrieves the full name of the current user.
     *
     * @return The user's full name.
     */
	@GetMapping("/name")
	@ResponseStatus(HttpStatus.OK)
	public String getName(){
		return userService.getName();
	}
}
