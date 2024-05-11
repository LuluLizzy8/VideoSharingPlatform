import { Component, OnInit } from '@angular/core';
import { VideoService } from '../video.service';
import { VideoDto } from '../video-dto';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  historyVideos: VideoDto[] = [];

  constructor(private videoService: VideoService) { }

  ngOnInit(): void {
    // Assuming the user ID is available somehow, e.g., from a user service or auth service
    this.videoService.getVideoHistory().subscribe(videos => {
      this.historyVideos = videos;
    });
  }
}
