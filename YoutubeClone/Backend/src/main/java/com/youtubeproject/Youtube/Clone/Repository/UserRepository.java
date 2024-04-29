package com.youtubeproject.Youtube.Clone.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.youtubeproject.Youtube.Clone.Model.User;

public interface UserRepository extends MongoRepository<User, String>{

}
