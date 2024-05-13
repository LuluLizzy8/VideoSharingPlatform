import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { VideoService } from './video.service';
import { UploadVideoResponse } from './upload-video/UploadVideoResponse';
import { VideoDto } from './video-dto';

describe('VideoService', () => {
  let service: VideoService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [VideoService]
    });
    service = TestBed.inject(VideoService);
    httpController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpController.verify();
  });

  it('should upload a video and return the response', () => {
    const mockFile = new File([''], 'video.mp4');
    const mockResponse: UploadVideoResponse = { videoId:'1', videoUrl: 'http://localhost:8080/api/videos/some-video-id' };
    
    service.uploadVideo(mockFile).subscribe(response => {
      expect(response.videoUrl).toEqual(mockResponse.videoUrl);
    });

    const req = httpController.expectOne('http://localhost:8080/api/videos/');
    expect(req.request.method).toEqual('POST');
    expect(req.request.body.has('file')).toBeTruthy();
    req.flush(mockResponse);
  });

  it('should retrieve a video by ID', () => {
    const mockVideoId = '123';
    const mockVideoDto: VideoDto = { id: mockVideoId, userName:'A', title: 'Test Video', description: '', userId: '', likes: 0, viewCount: 0, videoUrl: '', thumbnailUrl: '' };

    service.getVideo(mockVideoId).subscribe(video => {
      expect(video).toEqual(mockVideoDto);
    });

    const req = httpController.expectOne(`http://localhost:8080/api/videos/${mockVideoId}`);
    expect(req.request.method).toEqual('GET');
    req.flush(mockVideoDto);
  });
});
