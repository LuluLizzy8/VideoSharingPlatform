package com.youtubeproject.Youtube.Clone.Service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface defining the file uploading services.
 * <p>
 * This interface provides a method for uploading files. Implementations of this interface
 * are responsible for handling file storage and management of files.
 * </p>
 */
public interface FileService {
	
	/**
     * Uploads a given file and returns the URL where the file is accessible.
     * <p>
     * Implementations should handle the file upload process and ensure the file is stored
     * securely and is accessible via a URL. The method should return the URL of the uploaded
     * file upon successful upload.
     * </p>
     *
     * @param file The {@link MultipartFile} to be uploaded.
     * @return A {@code String} representing the URL of the uploaded file.
     * @throws IllegalArgumentException if the file parameter is null or if there is an error during the upload process.
     */
	String uploadFile(MultipartFile file);
}
