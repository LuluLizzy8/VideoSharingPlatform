import { Component, OnInit, ViewChild, ElementRef, Input, OnDestroy } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { takeWhile } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service'; // Adjust the path as necessary

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css']
})

export class VideoPlayerComponent implements OnInit, OnDestroy {
  @Input() videoUrl!: string;
  videoId!: string;
  @ViewChild('media', { static: false }) player!: ElementRef<HTMLVideoElement>;
  private alive = true; // Flag to control the interval
  private saveInterval!: Subscription; // Subscription to manage interval

  constructor(
    private route: ActivatedRoute,
    private videoService: VideoService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.videoId = params['videoId'];
      this.videoService.getPlaybackPosition(this.videoId).subscribe(position => {
        setTimeout(() => {
          this.player.nativeElement.currentTime = position;
        }, 0);
      });
    });

    this.startPeriodicSave();
  }

  ngOnDestroy(): void {
    this.alive = false; // Stop the interval
    if (this.saveInterval) {
      this.saveInterval.unsubscribe(); // Clean up the interval
    }
    this.saveFinalPlaybackPosition();
  }

  startPeriodicSave() {
    this.saveInterval = interval(5000) // every 5 seconds
      .pipe(takeWhile(() => this.alive))
      .subscribe(() => {
        this.savePlaybackPosition();
      });
  }

  savePlaybackPosition() {
    let position = this.player.nativeElement.currentTime;
    //console.log(`Saving position ${position} for video ${this.videoId}`); // Debug log
    this.videoService.savePlaybackPosition(this.videoId, position)//.subscribe(
        //() => console.log('Position saved successfully'),
        //error => console.error('Error saving position', error)
    //)
    ;
}

  saveFinalPlaybackPosition() {
    let position = this.player.nativeElement.currentTime;
    this.videoService.savePlaybackPosition(this.videoId, position).subscribe();
  }
}
