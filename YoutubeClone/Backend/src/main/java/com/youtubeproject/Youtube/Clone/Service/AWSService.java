package com.youtubeproject.Youtube.Clone.Service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class AWSService implements FileService{
	
	public static final String BUCKET_NAME = "youtubeclone-seclass-2024";
	private final S3Client awsS3Client;

	@Override
	public String uploadFile(MultipartFile file) {
		//Prepare key
		var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		var key = UUID.randomUUID().toString() + "." + filenameExtension;
		
        //Create PutObjectRequest with metadata
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(BUCKET_NAME)
				.key(key)
//				.acl(ObjectCannedACL.PUBLIC_READ) 
				.contentType(file.getContentType())
				.build();
		
		try {
			//upload to AWS S3
			awsS3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
	    } catch (IOException ioException) {
	    	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occurred while uploading the file");
    	}
		
		// return the URL of the uploaded object
        return awsS3Client.utilities().getUrl(builder -> builder.bucket(BUCKET_NAME).key(key)).toExternalForm();
    
	}
}