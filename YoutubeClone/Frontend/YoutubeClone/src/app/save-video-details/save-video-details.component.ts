import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { VideoService } from "../video.service";
import { MatSnackBar } from '@angular/material/snack-bar';
import {VideoDto} from "../video-dto";
import { UserService } from '../user.service';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrl: './save-video-details.component.css'
})

export class SaveVideoDetailsComponent {
	
	saveVideoDetailsForm: FormGroup;

	
	title: FormControl = new FormControl("");
	description: FormControl = new FormControl("");
	selectedFile!: File;
	selectedFileName = "";
	videoId = "";
	fileSelected = false;
	videoUrl!: string;
	thumbnailUrl!: string;
	videoAvaliable: boolean = false;
	userId = "";
	userName = "";
	
	constructor(private activatedRoute: ActivatedRoute, 
				private videoService: VideoService, 
				private matSnackBar: MatSnackBar,
				private userService: UserService,
				private httpClient: HttpClient) {
		
		this.videoId = this.activatedRoute.snapshot.params['videoId'];	
		
		this.videoService.getVideo(this.videoId).subscribe(data => {
			this.videoUrl = data.videoUrl;
			this.thumbnailUrl = data.thumbnailUrl;
			this.videoAvaliable = true;
		})
			
		this.saveVideoDetailsForm = new FormGroup({
			title: this.title,
			description: this.description,
		})
	
	}
	
	
	ngOnInit(): void {
	
	}
	
	onFileSelected(event: any) {
		this.selectedFile = event.target.files[0]; 
		this.selectedFileName = this.selectedFile.name; 
		this.fileSelected = true;
	}
	
	saveVideo() {
	  const formData = new FormData();
	  formData.append('file', this.selectedFile, this.selectedFileName);
	  formData.append('videoId', this.videoId);
	
	  // Post the form data and then assign the response to `thumbnailUrl`
	  this.httpClient.post<string>('http://localhost:8080/api/videos/thumbnail', formData, {
	    responseType: 'text' as 'json'
	  }).subscribe(
	    response => {
	      const videoMetadata: VideoDto = {
	        id: this.videoId,
	        title: this.saveVideoDetailsForm.get('title')?.value,
	        description: this.saveVideoDetailsForm.get('description')?.value,
	        userId: '',
	        userName: '',
	        videoUrl: this.videoUrl,
	        thumbnailUrl: response,
	        likes: 0,
	        viewCount: 0
	      };
	
	      this.videoService.saveVideo(videoMetadata).subscribe(data => {
	        this.matSnackBar.open("Video Metadata Updated successfully", "OK");
	      });
	    },
	    error => {
	      console.error('Failed to upload thumbnail', error);
	    }
	  );
	}


}
