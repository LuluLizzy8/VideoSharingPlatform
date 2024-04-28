import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { VideoService } from "../video.service";

@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrl: './save-video-details.component.css'
})

export class SaveVideoDetailsComponent {
	
	saveVideoDetailsForm: FormGroup;
	
	title: FormControl = new FormControl("");
	description: FormControl = new FormControl("");
	//videostatus 
	selectedFile!: File;
	selectedFileName = "";
	videoId = "";
	
	constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService) {
		
		this.videoId = this.activatedRoute.snapshot.params['videoId'];	
			
		this.saveVideoDetailsForm = new FormGroup({
			title: this.title,
			description: this.description,
			//videostatus
		})
	
	}
	
	
	ngOnInit(): void {
	
	}
	
	onFileSelected(event: any) {
		this.selectedFile = event.target.files[0]; 
		this.selectedFileName = this.selectedFile.name; 
	}
	
	onUpload() {
		this.videoService.uploadThumbnail(this.selectedFile, this.videoId)
			.subscribe(data => {
				console.log(data);
				//show notif for successful upload
			})		
	}

}
