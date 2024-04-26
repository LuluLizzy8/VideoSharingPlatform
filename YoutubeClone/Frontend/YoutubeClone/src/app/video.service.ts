import { HttpClient, HttpHeaders } from '@angular/common/http';  // Consolidate imports
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
  
  uploadThumbnail(selectedFile: File, videoId: string): Observable<string> {
    const fd = new FormData();
    fd.append('file', selectedFile, selectedFile.name);
    fd.append('videoId', videoId);
    return this.httpClient.post('http://localhost:8080/api/video/thumbnail/upload', fd,
      {
        headers: new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('access_token')),
        responseType: 'text'
      });
  }
  
}
