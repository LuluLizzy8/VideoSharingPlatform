import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadVideoResponse } from './upload-video/UploadVideoResponse';
import { VideoDto } from "./video-dto";

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
  
  getVideo(videoId: string): Observable<VideoDto> {
	//http call to our backend
	return this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/" + videoId); //return videoDto
  }
  
  saveVideo(videoMetaData: VideoDto): Observable<VideoDto> {
	return this.httpClient.put<VideoDto>("http://localhost:8080/api/videos/", videoMetaData);
  }
  
  getAllVideos(): Observable<Array<VideoDto>> {
	return this.httpClient.get<Array<VideoDto>>("http://localhost:8080/api/videos/");
  }
  
  getVideoHistory(): Observable<Array<VideoDto>> {
    return this.httpClient.get<Array<VideoDto>>("http://localhost:8080/api/user/history");
  }
  
  likeVideo(videoId: string): Observable<VideoDto> {
	return this.httpClient.post<VideoDto>("http://localhost:8080/api/videos/" + videoId + "/like", null);
  }
  
  savePlaybackPosition(videoId: string, position: number): Observable<void> {
    const payload = { position }; // Assuming you need to send this
    console.log("Saving position:", payload); // Debugging line
    return this.httpClient.post<void>(`http://localhost:8080/api/videos/savePosition/${videoId}/${position}`, payload);
}

  
  getPlaybackPosition(videoId: string): Observable<number> {
	return this.httpClient.get<number>(`http://localhost:8080/api/videos/getPosition/${videoId}`);
  }

} 



