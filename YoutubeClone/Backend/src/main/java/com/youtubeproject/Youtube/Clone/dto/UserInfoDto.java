package com.youtubeproject.Youtube.Clone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing user information.
 * <p>
 * This class is used to transfer user data between the client and server. It includes details
 * for the user's unique ids and name.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
	/**
     * The unique id for the user.
     */
	private String id;
	
	/**
     * The OAuth2 identifier for the user which is mapped from the JSON property "sub".
     */
    @JsonProperty("sub")
    private String sub;
    
    /**
     * The name of the user which is mapped from the JSON property "name".
     */
    @JsonProperty("name")
    private String name;
}
