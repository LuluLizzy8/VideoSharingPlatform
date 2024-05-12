import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VideoDetailComponent } from './video-detail.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';
import { UserService } from '../user.service';
import { of } from 'rxjs';

describe('VideoDetailComponent', () => {
  let component: VideoDetailComponent;
  let fixture: ComponentFixture<VideoDetailComponent>;
  let mockVideoService: any;
  let mockUserService: any;
  let mockSnackBar: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {
    mockVideoService = jasmine.createSpyObj('VideoService', ['getVideo', 'likeVideo', 'viewVideo']);
    mockUserService = jasmine.createSpyObj('UserService', ['getUserId', 'isSubscribedToUser', 'subscribeToUser', 'unsubscribeToUser']);
    mockSnackBar = jasmine.createSpyObj('MatSnackBar', ['open']);
    mockActivatedRoute = {
      snapshot: {
        params: { videoId: '123' }
      }
    };

    await TestBed.configureTestingModule({
      declarations: [ VideoDetailComponent ],
      providers: [
        { provide: VideoService, useValue: mockVideoService },
        { provide: UserService, useValue: mockUserService },
        { provide: MatSnackBar, useValue: mockSnackBar },
        { provide: ActivatedRoute, useValue: mockActivatedRoute }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VideoDetailComponent);
    component = fixture.componentInstance;
    mockVideoService.getVideo.and.returnValue(of({
      videoUrl: 'url',
      title: 'Video Title',
      description: 'Description',
      likes: 10,
      viewCount: 100,
      userId: 'user123',
      userName: 'UserName'
    }));
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

  // Additional tests for subscription management and error handling
});


