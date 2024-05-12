package com.youtubeproject.Youtube.Clone.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtubeproject.Youtube.Clone.Model.User;
import com.youtubeproject.Youtube.Clone.Repository.UserRepository;
import com.youtubeproject.Youtube.Clone.dto.UserInfoDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

	@Value("${auth0.userinfoEndpoint}")
	private String userInfoEndpoint;
	
	private final UserRepository userRepository;
	
	//tokenValue is the Bearer Authentication Token
	public String registerUser(String tokenValue) {
		//make call to userinfo endpoint
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(userInfoEndpoint))
				.setHeader("Authorization", String.format("Bearer %s", tokenValue))
				.build();

		HttpClient httpClient = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.build();
		
		try {
			HttpResponse<String> responseString = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
			String body = responseString.body();
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			UserInfoDto userInfoDto = objectMapper.readValue(body, UserInfoDto.class);
			
			//check
			Optional<User> userBySubject = userRepository.findBySub(userInfoDto.getSub());
			if(userBySubject.isPresent()) {
				return userBySubject.get().getId();
			} else {
				User user = new User();
				user.setFullName(userInfoDto.getName());
				user.setSub(userInfoDto.getSub());
				
				return userRepository.save(user).getId();
			}

		} catch (Exception exception) {
			throw new RuntimeException("Exception occured when registering user", exception);
		}
		
		//fetch user details and save them to the database
	}
}
