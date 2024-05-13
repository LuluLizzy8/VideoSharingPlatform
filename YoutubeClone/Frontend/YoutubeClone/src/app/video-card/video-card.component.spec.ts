import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VideoCardComponent } from './video-card.component';
import { VideoDto } from "../video-dto";
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { By } from '@angular/platform-browser';


describe('VideoCardComponent', () => {
  let component: VideoCardComponent;
  let fixture: ComponentFixture<VideoCardComponent>;
  let testVideo: VideoDto = {
    id: '1',
    userId: '11',
    title: 'Test Video',
    description: 'A great video',
    videoUrl: 'https://example.com/video.mp4',
    thumbnailUrl: 'https://example.com/thumbnail.jpg',
    userName: 'TestUser',
    viewCount: 150,
    likes: 10
  };


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VideoCardComponent ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA] // to ignore elements like mat-card
    })
    .compileComponents();
  
    fixture = TestBed.createComponent(VideoCardComponent);
    component = fixture.componentInstance;
    component.video = testVideo; // Set input
    fixture.detectChanges(); // Update the component with the input
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should display video title', () => {
    const titleElement = fixture.debugElement.query(By.css('mat-card-title')).nativeElement;
    expect(titleElement.textContent).toContain(testVideo.title);
  });


  it('should display video uploader', () => {
    const subtitleElement = fixture.debugElement.query(By.css('mat-card-subtitle')).nativeElement;
    expect(subtitleElement.textContent).toContain(`Uploader: ${testVideo.userName}`);
  });


  it('should display view count', () => {
    const viewsElement = fixture.debugElement.queryAll(By.css('mat-card-subtitle'))[1].nativeElement;
    expect(viewsElement.textContent).toContain(`${testVideo.viewCount} Views`);
  });

  it('should display the correct thumbnail image', () => {
    const imgElement = fixture.debugElement.query(By.css('img')).nativeElement;
    expect(imgElement.src).toBe(testVideo.thumbnailUrl);
  });
});





