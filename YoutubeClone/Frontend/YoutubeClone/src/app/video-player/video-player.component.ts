import { Component, OnInit, ViewChild, ElementRef, Input, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css']
})
export class VideoPlayerComponent implements OnInit, OnDestroy {
  @Input() videoUrl!: string;
  videoId!: string;
  @ViewChild('media', { static: false }) player!: ElementRef<HTMLVideoElement>;
  private lastSavedPosition: number = 0;

  constructor(
    private route: ActivatedRoute,
    private videoService: VideoService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.videoId = params['videoId'];
      this.videoService.getPlaybackPosition(this.videoId).subscribe(position => {
        this.player.nativeElement.addEventListener('loadedmetadata', () => {
          this.player.nativeElement.currentTime = position;
        });
        this.startPeriodicSave();
      });
    });
  }

  ngOnDestroy(): void {
    this.stopSavingPlaybackPosition();
  }

  startPeriodicSave() {
    this.player.nativeElement.addEventListener('timeupdate', this.handleTimeUpdate);
  }

  handleTimeUpdate = () => {
    if (!this.player.nativeElement.paused) {
      const currentPosition = this.player.nativeElement.currentTime;
      if(Math.abs(currentPosition - this.lastSavedPosition) > 1) {
      	this.savePlaybackPosition();
      }
    }
  }

  savePlaybackPosition() {
    const currentPosition = this.player.nativeElement.currentTime;
    this.videoService.savePlaybackPosition(this.videoId, currentPosition).subscribe(
      () => {
        console.log(`Position saved successfully: ${currentPosition}`);
        this.lastSavedPosition = currentPosition;
      },
      error => console.error('Error saving position', error)
    );
  }

  stopSavingPlaybackPosition() {
    this.player.nativeElement.removeEventListener('timeupdate', this.handleTimeUpdate);
  }
}
