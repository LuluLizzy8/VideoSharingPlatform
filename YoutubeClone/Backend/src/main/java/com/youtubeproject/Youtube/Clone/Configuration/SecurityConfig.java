package com.youtubeproject.Youtube.Clone.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up security and JWT token validation.
 * <p>
 * This class enables web security and configures JWT authentication. It also specifies how the security filters
 * are applied to HTTP requests to specify what requests can only be accessed by authorized users.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Value("${auth0.audience}")
    private String audience;
    
    /**
     * Configures the security filter chain to apply JWT authentication and rules for what http methods need authentication
     * to incoming requests.
     *
     * @param http the {@link HttpSecurity} to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     * @throws HttpSecurity 
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(a -> a
        		.requestMatchers(HttpMethod.GET, "/api/videos/**").permitAll()  // Permit all GET requests to /api/videos
                .requestMatchers(HttpMethod.POST, "/api/videos/**").authenticated()
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));
        
        return http.build();
    }
    
    /**
     * Validator for checking if the JWT contains the required audience.
     */
    public static class AudienceValidator implements OAuth2TokenValidator<Jwt> {
        private final String audience;
        
        /**
         * Constructs a new AudienceValidator with the specified audience.
         *
         * @param audience the audience to validate within the JWT
         */
        public AudienceValidator(String audience) {
            this.audience = audience;
        }
        
        /**
         * Validates if the JWT contains the specified audience.
         *
         * @param jwt the JWT to validate
         * @return the result of the token validation
         */
        @Override
        public OAuth2TokenValidatorResult validate(Jwt jwt) {
            if (jwt.getAudience().contains(audience)) {
                return OAuth2TokenValidatorResult.success();
            }
            return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "The required audience is missing", null));
        }
    }

    /**
     * Configures and returns a {@link JwtDecoder} with issuer and audience validations.
     *
     * @return a configured {@link JwtDecoder}
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withIssuerLocation(issuer).build();
       
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }
    
    
}