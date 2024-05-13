import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UploadVideoComponent } from './upload-video.component';
import { VideoService } from '../video.service';
import { UserService } from '../user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { UploadVideoResponse } from './UploadVideoResponse';

describe('UploadVideoComponent', () => {
  let component: UploadVideoComponent;
  let fixture: ComponentFixture<UploadVideoComponent>;
  let videoService: jasmine.SpyObj<VideoService>;
  let userService: jasmine.SpyObj<UserService>;
  let matSnackBar: jasmine.SpyObj<MatSnackBar>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const videoServiceSpy = jasmine.createSpyObj('VideoService', ['uploadVideo']);
    const userServiceSpy = jasmine.createSpyObj('UserService', ['getUserId']);
    const matSnackBarSpy = jasmine.createSpyObj('MatSnackBar', ['open']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigateByUrl']);

    await TestBed.configureTestingModule({
      declarations: [ UploadVideoComponent ],
      providers: [
        { provide: VideoService, useValue: videoServiceSpy },
        { provide: UserService, useValue: userServiceSpy },
        { provide: MatSnackBar, useValue: matSnackBarSpy },
        { provide: Router, useValue: routerSpy }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(UploadVideoComponent);
    component = fixture.componentInstance;

    videoService = TestBed.inject(VideoService) as jasmine.SpyObj<VideoService>;
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    matSnackBar = TestBed.inject(MatSnackBar) as jasmine.SpyObj<MatSnackBar>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

 
  it('should upload video and navigate on success', () => {
	  const mockFile = new File([''], 'video.mp4', { type: 'video/mp4' });
	  const videoResponse: UploadVideoResponse = {
	    videoId: 'abc123',
	    videoUrl: 'http://example.com/video.mp4'  // Ensure this matches the expected structure
	  };
	  videoService.uploadVideo.and.returnValue(of(videoResponse)); // Correctly returning an Observable of UploadVideoResponse
	
	  component.isAuthenticated = true;
	  component.fileEntry = jasmine.createSpyObj('FileSystemFileEntry', ['file']);
	  if (component.fileEntry && 'file' in component.fileEntry) {
	    (component.fileEntry as jasmine.SpyObj<FileSystemFileEntry>).file.and.callFake((callback: (file: File) => void) => callback(mockFile));
	    component.uploadVideo();
	
	    expect(videoService.uploadVideo).toHaveBeenCalledWith(mockFile);
	    expect(router.navigateByUrl).toHaveBeenCalledWith('/save-video-details/abc123');
	  }
	});


  it('should show snackbar when uploading without authentication', () => {
    component.isAuthenticated = false; // User is not authenticated

    component.uploadVideo();

    expect(matSnackBar.open).toHaveBeenCalledWith('Login to Upload Videos', 'OK');
  });
});
