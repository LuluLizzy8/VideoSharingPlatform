package com.youtubeproject.Youtube.Clone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
	private String id;
    @JsonProperty("sub")
    private String sub;
    @JsonProperty("name")
    private String name;
}
