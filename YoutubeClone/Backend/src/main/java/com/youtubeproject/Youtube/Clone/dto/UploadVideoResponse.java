package com.youtubeproject.Youtube.Clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadVideoResponse {
	private videoId;
	private videoUrl;

}
