package com.youtubeproject.Youtube.Clone.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for web MVC settings, specifically CORS (Cross-Origin Resource Sharing).
 * <p>
 * This class implements the {@link WebMvcConfigurer} interface to provide custom configurations
 * for the Spring MVC framework used. The purpose is to set up CORS to allow cross-origin 
 * requests from web clients.
 * </p>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	/**
     * Configures the CORS settings.
     * <p>
     * This method sets up the CORS mappings to allow requests from any origin. It also allows
     * common HTTP methods and headers. It also sets the maximum time that the response from a preflight
     * request can be cached by clients to 3600 seconds.
     * </p>
     *
     * @param corsRegistry the {@link CorsRegistry} used to register CORS configurations
     */
	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.allowedHeaders("*")
			.maxAge(3600);
	}

}
