import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HistoryComponent } from './history.component';
import { VideoService } from '../video.service';
import { VideoDto } from '../video-dto';
import { of } from 'rxjs';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';


describe('HistoryComponent', () => {
  let component: HistoryComponent;
  let fixture: ComponentFixture<HistoryComponent>;
  let mockVideoService: any;
  const mockVideos: VideoDto[] = [
    { id: '1', userId:'11', userName:'A', title: 'Video Title 1', description: 'Description 1', videoUrl: 'url1', thumbnailUrl: 'thumb1', viewCount: 150, likes: 25 },
    { id: '2', userId:'22', userName:'B', title: 'Video Title 2', description: 'Description 2', videoUrl: 'url2', thumbnailUrl: 'thumb2', viewCount: 250, likes: 35 }
  ];


  beforeEach(async () => {
    // Create a spy for the VideoService with a mock 'getVideoHistory' method
    mockVideoService = jasmine.createSpyObj('VideoService', ['getVideoHistory']);
    mockVideoService.getVideoHistory.and.returnValue(of(mockVideos));


    await TestBed.configureTestingModule({
      declarations: [ HistoryComponent ],
      providers: [
        { provide: VideoService, useValue: mockVideoService }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA] // This will ignore any unknown elements and attributes
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); // triggers ngOnInit
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should load video history on init', () => {
    // Check if the VideoService was called
    expect(mockVideoService.getVideoHistory).toHaveBeenCalled();


    // Check if the component correctly updated its historyVideos property
    expect(component.historyVideos.length).toBe(2);
    expect(component.historyVideos).toEqual(mockVideos);
  });


  it('should display the correct number of video cards', () => {
    fixture.detectChanges(); // Update the view with the fetched videos
    const compiled = fixture.nativeElement as HTMLElement;
    const videoCards = compiled.querySelectorAll('app-video-card');
    expect(videoCards.length).toEqual(2); // Expect two video cards as per mock data
  });
});





