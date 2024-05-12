package com.youtubeproject.Youtube.Clone.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.youtubeproject.Youtube.Clone.Model.Video;

/**
 * Repository interface for managing video data in the YouTube Clone application.
 */
public interface VideoRepository extends MongoRepository<Video, String>{

}
