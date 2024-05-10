import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { VideoService } from "../video.service";
import { MatSnackBar } from '@angular/material/snack-bar';
import {VideoDto} from "../video-dto";

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
	
	constructor(private activatedRoute: ActivatedRoute, 
				private videoService: VideoService, 
				private matSnackBar: MatSnackBar) {
		
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
	
	onUpload() {
		this.videoService.uploadThumbnail(this.selectedFile, this.videoId)
			.subscribe(data => {
				console.log(data);
				
				//show notif for successful upload
				this.matSnackBar.open("Thumbnail Uploaded Successfully", "OK");
			})		
	}
	
	saveVideo() {
		const videoMetadata: VideoDto = {
			"id": this.videoId,
			"userId": "", //add user id
			"title": this.saveVideoDetailsForm.get('title')?.value,
			"description": this.saveVideoDetailsForm.get('description')?.value,
			"videoUrl": this.videoUrl,
			"thumbnailUrl": this.thumbnailUrl,
			"likes": 0,
		}
		this.videoService.saveVideo(videoMetadata).subscribe(data =>{
			this.matSnackBar.open("Video Metadata Updated successfully", "OK");
		})
	}

}
