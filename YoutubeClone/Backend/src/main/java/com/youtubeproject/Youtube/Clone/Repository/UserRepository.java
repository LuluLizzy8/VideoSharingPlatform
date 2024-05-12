package com.youtubeproject.Youtube.Clone.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.youtubeproject.Youtube.Clone.Model.User;

/**
 * Repository interface for managing user data in the YouTube Clone application
 */
public interface UserRepository extends MongoRepository<User, String> {
	
	/**
	 * Finds a user by their login token
	 * @param sub The user login token to search for
	 * @return An optional containing the user if found, otherwise empty
	 */
	Optional<User> findBySub(String sub);
}
