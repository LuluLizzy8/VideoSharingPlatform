import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadVideoResponse } from './upload-video/UploadVideoResponse';
import { VideoDto } from "./dto/VideoDto";

@Injectable({
  providedIn: 'root'
})
export class VideoService {
	
  uploadVideoResponse: UploadVideoResponse | undefined;

  constructor(private httpClient: HttpClient) { 
  }
  
  uploadVideo(fileEntry: File): Observable<UploadVideoResponse> {
	const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);
          
	return this.httpClient.post<UploadVideoResponse>("http://localhost:8080/api/videos/", formData);
  }
  
  uploadThumbnail(fileEntry: File, videoId: string): Observable<string> {
	const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);
    formData.append("videoId", videoId);
    
    //http call to upload thumbnail      
	return this.httpClient.post("http://localhost:8080/api/videos/thumbnail", formData, {
		responseType: "text"
	});
  }
  
  getVideo(videoId: string): Observable<VideoDto> {
	//http call to our backend
	return this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/" + videoId); //return videoDto
	
  } 
  

