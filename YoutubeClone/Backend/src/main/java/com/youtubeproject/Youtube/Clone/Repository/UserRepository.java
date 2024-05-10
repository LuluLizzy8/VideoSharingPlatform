package com.youtubeproject.Youtube.Clone.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.youtubeproject.Youtube.Clone.Model.User;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findBySub(String sub);
}
