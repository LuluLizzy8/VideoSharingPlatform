import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { VideoService } from "../video.service";
import { UserService } from '../user.service';

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
	videoAvailable: boolean = false;
	likes: number = 0;
	viewCount: number = 0;
	userId!: string;
	showSubscribeButton: boolean = true;
	showUnsubscribeButton: boolean = false;

	constructor(private activatedRoute: ActivatedRoute, 
				private videoService: VideoService,
				private userService: UserService){ 
		this.videoId = this.activatedRoute.snapshot.params['videoId'];
		this.videoService.getVideo(this.videoId).subscribe(data => {
			console.log(data); 
			this.videoUrl = data.videoUrl;
			this.videoTitle = data.title;
			this.videoDescription = data.description;
			this.videoAvailable = true;
			this.likes = data.likes;
			this.viewCount = data.viewCount;
			this.userId = data.userId;
		})


	}
	
	ngOnInit(): void {
		
	}
	
	likeVideo() {
		this.videoService.likeVideo(this.videoId).subscribe( data => {
			this.likes = data.likes;
		})
	}
	
	subscribeToUser(){
		let userId = this.userService.getUserId();
		this.userService.subscribeToUser(userId).subscribe( data => {
			this.showUnsubscribeButton = true;
			this.showSubscribeButton = false;
		});
	}
	
	unsubscribeToUser(){
		let userId = this.userService.getUserId();
		this.userService.unsubscribeToUser(userId).subscribe( data => {
			this.showUnsubscribeButton = false;
			this.showSubscribeButton = true;
		});
	}
}
