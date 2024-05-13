import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VideoDetailComponent } from './video-detail.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';
import { UserService } from '../user.service';
import { of } from 'rxjs';
import { VideoPlayerComponent } from '../video-player/video-player.component';
import { VgCoreModule } from '@videogular/ngx-videogular/core';
import { VgControlsModule } from '@videogular/ngx-videogular/controls';
import { VgOverlayPlayModule } from '@videogular/ngx-videogular/overlay-play';
import { VgBufferingModule } from '@videogular/ngx-videogular/buffering';

describe('VideoDetailComponent', () => {
  let component: VideoDetailComponent;
  let fixture: ComponentFixture<VideoDetailComponent>;
  let mockVideoService: any;
  let mockUserService: any;
  let mockSnackBar: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {
	  // Setup mocks
	  mockVideoService = jasmine.createSpyObj('VideoService', ['getVideo', 'likeVideo', 'viewVideo']);
	  mockUserService = jasmine.createSpyObj('UserService', ['getUserId', 'isSubscribedToUser', 'subscribeToUser', 'unsubscribeToUser']);
	  mockSnackBar = jasmine.createSpyObj('MatSnackBar', ['open']);
	  mockActivatedRoute = {
	    snapshot: {
	      params: { videoId: '123' }
	    }
	  };
	
	  // Configure TestBed
	  await TestBed.configureTestingModule({
	    declarations: [
		    VideoDetailComponent,
		    VideoPlayerComponent
		  ],
	    imports: [
		    MatSnackBarModule,
		    MatIconModule,
		    MatButtonModule,
		    MatDividerModule,
		    MatCardModule,
		    FlexLayoutModule,
		    VgCoreModule,
		    VgControlsModule,
		    VgOverlayPlayModule,
		    VgBufferingModule
		  ],
	    providers: [
	      { provide: VideoService, useValue: mockVideoService },
	      { provide: UserService, useValue: mockUserService },
	      { provide: MatSnackBar, useValue: mockSnackBar },
	      { provide: ActivatedRoute, useValue: mockActivatedRoute }
	    ]
	  }).compileComponents();
	
	  // Ensure the mock returns a valid Observable
	  mockVideoService.getVideo.and.returnValue(of({
	    videoUrl: 'url',
	    title: 'Video Title',
	    description: 'Description',
	    likes: 10,
	    viewCount: 100,
	    userId: 'user123',
	    userName: 'UserName'
	  }));
	
	  // Create component
	  fixture = TestBed.createComponent(VideoDetailComponent);
	  component = fixture.componentInstance;
	  fixture.detectChanges();
	});


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load video details on init', () => {
    expect(component.videoTitle).toEqual('Video Title');
    expect(component.videoDescription).toEqual('Description');
    expect(component.likes).toEqual(10);
    expect(component.viewCount).toEqual(100);
  });

  it('should handle like operation if authenticated', () => {
    component.isAuthenticated = true;
    mockVideoService.likeVideo.and.returnValue(of({ likes: 11 }));
    component.likeVideo();
    expect(mockVideoService.likeVideo).toHaveBeenCalledWith('123');
    expect(component.likes).toEqual(11);
  });

  it('should show login prompt when trying to like and not authenticated', () => {
    component.isAuthenticated = false;
    component.likeVideo();
    expect(mockSnackBar.open).toHaveBeenCalledWith('Login to Like', 'OK');
  });

});


