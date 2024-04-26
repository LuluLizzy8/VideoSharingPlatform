import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadVideoResponse } from './upload-video/UploadVideoResponse';

@Injectable({
  providedIn: 'root'
})
export class VideoService {
	
  uploadVideoResponse: UploadVideoResponse | undefined;

  constructor(private httpClient: HttpClient) { }
  
  uploadVideo(fileEntry: File): Observable<UploadVideoResponse> {
	const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);
          
	return this.httpClient.post<UploadVideoResponse>("http://localhost:8080/api/videos/", formData);
  }
}
