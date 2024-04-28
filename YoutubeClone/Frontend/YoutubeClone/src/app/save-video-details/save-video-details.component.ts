import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { VideoService } from "../video.service";
import { MatSnackBar } from '@angular/material/snack-bar';

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
	fileSelected = false;
	
	constructor(private activatedRoute: ActivatedRoute, 
				private videoService: VideoService, 
				private matSnackBar: MatSnackBar) {
		
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

}
