import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";

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
	
	constructor() {
		this.saveVideoDetailsForm = new FormGroup({
			title: this.title,
			description: this.description,
			//videostatus
		})
	
	}
	
	ngOnInit(): void {
	
	}

}
