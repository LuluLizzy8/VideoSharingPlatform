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
    mockVideoService = jasmine.createSpyObj('VideoService', ['getVideoHistory']);
    mockVideoService.getVideoHistory.and.returnValue(of(mockVideos));


    await TestBed.configureTestingModule({
      declarations: [ HistoryComponent ],
      providers: [
        { provide: VideoService, useValue: mockVideoService }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA] 
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); 
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should load video history on init', () => {
    expect(mockVideoService.getVideoHistory).toHaveBeenCalled();


    expect(component.historyVideos.length).toBe(2);
    expect(component.historyVideos).toEqual(mockVideos);
  });


  it('should display the correct number of video cards', () => {
    fixture.detectChanges(); 
    const compiled = fixture.nativeElement as HTMLElement;
    const videoCards = compiled.querySelectorAll('app-video-card');
    expect(videoCards.length).toEqual(2); 
  });
});





