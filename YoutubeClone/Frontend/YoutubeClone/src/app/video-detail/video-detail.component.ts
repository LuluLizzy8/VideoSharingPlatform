import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { VideoService } from "../video.service";

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrl: './video-detail.component.css'
})
export class VideoDetailComponent {
	
	videoId = "";
	videoUrl!: string;
	videoTitle = "";
	videoDescription = "";
	videoAvaliable: boolean = false;
	likes: number = 0;
	viewCount: number = 0;

	constructor(private activatedRoute: ActivatedRoute, private videoService: VideoService){ 
		this.videoId = this.activatedRoute.snapshot.params['videoId'];
		this.videoService.getVideo(this.videoId).subscribe(data => {
			console.log(data); 
			this.videoUrl = data.videoUrl;
			this.videoTitle = data.title;
			this.videoDescription = data.description;
			this.videoAvaliable = true;
			this.likes = data.likes;
			this.viewCount = data.viewCount;
		})


	}
	
	ngOnInit(): void{
		
	}
}
