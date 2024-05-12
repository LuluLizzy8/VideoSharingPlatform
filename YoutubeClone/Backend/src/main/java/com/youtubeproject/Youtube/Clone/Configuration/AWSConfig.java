package com.youtubeproject.Youtube.Clone.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures beans for use in AWS3 client configuration.
 * <p>
 * This class is the setup for interacting with Amazon S3 and it
 * specifies the region and other necessary client configuration details.
 * </p>
 */

@Configuration
public class AWSConfig {
	
	/**
     * Creates a bean that provides an Amazon S3 client.
     * <p>
     * This method configures the S3 client for the Asia Pacific (Seoul) region.
     * The S3 client is essential for operations such as uploading, downloading,
     * and managing objects in an Amazon S3 bucket.
     * </p>
     *
     * @return A fully configured instance of {@link S3Client} ready to be used
     *         for interacting with Amazon S3.
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                       .region(Region.of("ap-northeast-2"))
                       .build();
    }
}