package com.youtubeproject.Youtube.Clone.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.experimental.var;

@Service
public class AWSService implements FileService{
	
	@Override
	public String uploadFile(MultipartFile file) {
		//Upload file to AWS S3
		
		return null;
	}
}
