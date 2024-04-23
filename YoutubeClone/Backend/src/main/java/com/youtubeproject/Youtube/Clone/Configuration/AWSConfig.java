package com.youtubeproject.Youtube.Clone.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                       .region(Region.of("ap-northeast-2"))
                       .build();
    }
}