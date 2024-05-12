import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VideoDetailComponent } from './video-detail.component';
import { VideoService } from '../video.service';
import { UserService } from '../user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VideoDto } from '../video-dto';

describe('VideoDetailComponent', () => {
  let component: VideoDetailComponent;
  let fixture: ComponentFixture<VideoDetailComponent>;
  let videoServiceMock: jasmine.SpyObj<VideoService>;
  let userServiceMock: jasmine.SpyObj<UserService>;
  let matSnackBarMock: jasmine.SpyObj<MatSnackBar>;
  let activatedRouteMock: any;

  beforeEach(async () => {
    videoServiceMock = jasmine.createSpyObj('VideoService', ['getVideo', 'likeVideo', 'uploadVideo']);
    userServiceMock = jasmine.createSpyObj('UserService', ['getUserId', 'isSubscribedToUser', 'subscribeToUser', 'unsubscribeToUser']);
    matSnackBarMock = jasmine.createSpyObj('MatSnackBar', ['open']);

    activatedRouteMock = {
      snapshot: { params: { videoId: '123' } }
    };

    await TestBed.configureTestingModule({
      declarations: [ VideoDetailComponent ],
      providers: [
        { provide: VideoService, useValue: videoServiceMock },
        { provide: UserService, useValue: userServiceMock },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ],
      imports: [ BrowserAnimationsModule ]  // Needed for MatSnackBar
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VideoDetailComponent);
    component = fixture.componentInstance;

    // Mocking the methods
    videoServiceMock.getVideo.and.returnValue(of({
      id: '1',
      videoUrl: 'http://example.com/video.mp4',
      thumbnailUrl: 'http://example.com/thumbnail.jpg',
      title: 'Test Video',
      description: 'A great video',
      likes: 10,
      viewCount: 100,
      userId: 'user1',
      userName: 'John Doe'
    }));

    userServiceMock.getUserId.and.returnValue('user1'); // Mock authenticated user

    fixture.detectChanges(); // Triggers ngOnInit and other initial logic
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch video details and update component state', () => {
    expect(videoServiceMock.getVideo).toHaveBeenCalledWith('123');
    expect(component.videoTitle).toEqual('Test Video');
    expect(component.videoAvailable).toBeTrue();
  });

  it('should display login prompt if trying to like video while not authenticated', () => {
    userServiceMock.getUserId.and.returnValue(''); // No authenticated user
    component.likeVideo();
    expect(matSnackBarMock.open).toHaveBeenCalledWith('Login to Like', 'OK');
  });

  it('should increment likes if like video when authenticated', () => {
    userServiceMock.getUserId.and.returnValue('user1'); // Authenticated user
    videoServiceMock.likeVideo.and.returnValue(of({
      id: '1',
      videoUrl: 'http://example.com/video.mp4',
      thumbnailUrl: 'http://example.com/thumbnail.jpg',
      title: 'Test Video',
      description: 'A great video',
      likes: 10,
      viewCount: 100,
      userId: 'user1',
      userName: 'John Doe'
    }));
    component.likeVideo();
    fixture.detectChanges();
    expect(component.likes).toEqual(11);
  });

  it('should show subscribe prompt if trying to subscribe while not authenticated', () => {
    userServiceMock.getUserId.and.returnValue(''); // No authenticated user
    component.subscribeToUser();
    expect(matSnackBarMock.open).toHaveBeenCalledWith('Login to Subscribe', 'OK');
  });

  it('should update subscription status when subscribing', () => {
    userServiceMock.getUserId.and.returnValue('user1'); // Authenticated user
    userServiceMock.subscribeToUser.and.returnValue(of(true));
    component.subscribeToUser();
    fixture.detectChanges();
    expect(component.showUnsubscribeButton).toBeTrue();
    expect(component.showSubscribeButton).toBeFalse();
  });

  it('should update subscription status when unsubscribing', () => {
    userServiceMock.getUserId.and.returnValue('user1'); // Authenticated user
    userServiceMock.unsubscribeToUser.and.returnValue(of(false));
    component.unsubscribeToUser();
    fixture.detectChanges();
    expect(component.showUnsubscribeButton).toBeFalse();
    expect(component.showSubscribeButton).toBeTrue();
  });

});
