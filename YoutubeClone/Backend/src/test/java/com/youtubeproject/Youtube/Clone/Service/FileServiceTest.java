package com.youtubeproject.Youtube.Clone.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for implementations of the FileService interface.
 */
@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileService fileService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testUploadFileSuccess() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());
        when(fileService.uploadFile(any(MultipartFile.class))).thenReturn("http://example.com/filename.txt");

        String result = fileService.uploadFile(file);

        assertNotNull(result);
        assertEquals("http://example.com/filename.txt", result);
        verify(fileService).uploadFile(file);
    }


    @Test
    void testUploadFileHandlingException() {
        MultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());
        when(fileService.uploadFile(any(MultipartFile.class))).thenThrow(new RuntimeException("Failed to store file"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileService.uploadFile(file);
        });
        assertEquals("Failed to store file", exception.getMessage());
        verify(fileService).uploadFile(file);
    }
}
