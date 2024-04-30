package com.youtubeproject.Youtube.Clone.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youtubeproject.Youtube.Clone.Service.UserRegistrationService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("api/user/")
@RequiredArgsConstructor
public class UserController {

	private final UserRegistrationService userRegistrationService;
	
	@GetMapping("/register")
	public String register(Authentication authentication) {
		//jsn web token obj
		Jwt jwt = (Jwt)authentication.getPrincipal();
		
		//call user info endpoint
		userRegistrationService.registerUser(jwt.getTokenValue());
		return "User Registeration Successful";
	}
}
