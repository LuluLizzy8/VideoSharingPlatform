package com.youtubeproject.Youtube.Clone.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtDebugController {

    @GetMapping("/debug/jwt")
    public String printJwt(@AuthenticationPrincipal Jwt jwt) {
        // Print the entire token and claims to the console
        System.out.println("JWT Token: " + jwt.getTokenValue());
        System.out.println("Claims: " + jwt.getClaims());
        
        return "Check your console for the JWT details!";
    }
}
