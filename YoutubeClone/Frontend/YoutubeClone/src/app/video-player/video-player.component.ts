import { Component, Input } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrl: './video-player.component.css'
})
export class VideoPlayerComponent {
	
	@Input()
	videoUrl!: string | ""; //can then bind this to source attribute
	constructor() {	}

	ngOnIt(): void {
		
	}
}
