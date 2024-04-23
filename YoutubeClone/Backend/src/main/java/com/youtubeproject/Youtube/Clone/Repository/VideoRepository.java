package com.youtubeproject.Youtube.Clone.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.youtubeproject.Youtube.Clone.Model.Video;

public interface VideoRepository extends MongoRepository<Video, String>{

}
