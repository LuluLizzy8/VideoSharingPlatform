import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FeaturedComponent } from './featured.component';
import { VideoService } from '../video.service';
import { VideoDto } from '../video-dto';
import { of } from 'rxjs';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('FeaturedComponent', () => {
 let component: FeaturedComponent;
 let fixture: ComponentFixture<FeaturedComponent>;
 let mockVideoService: any;
 const mockVideos: VideoDto[] = [
   { id: '1', userId:'11', userName:'A', title: 'Video 1', description: 'Desc 1', videoUrl: 'url1', thumbnailUrl: 'thumb1', viewCount: 100, likes: 10 },
   { id: '2', userId:'22', userName:'B', title: 'Video 2', description: 'Desc 2', videoUrl: 'url2', thumbnailUrl: 'thumb2', viewCount: 200, likes: 20 }
 ];

 beforeEach(async () => {
   mockVideoService = jasmine.createSpyObj('VideoService', ['getAllVideos']);
   mockVideoService.getAllVideos.and.returnValue(of(mockVideos));

   await TestBed.configureTestingModule({
     declarations: [ FeaturedComponent ],
     providers: [
       { provide: VideoService, useValue: mockVideoService }
     ],
     schemas: [CUSTOM_ELEMENTS_SCHEMA]
   })
   .compileComponents();

   fixture = TestBed.createComponent(FeaturedComponent);
   component = fixture.componentInstance;
   fixture.detectChanges();
 });

 it('should create', () => {
   expect(component).toBeTruthy();
 });

 it('should load featured videos on init', () => {
   expect(mockVideoService.getAllVideos).toHaveBeenCalled();

   expect(component.featuredVideos.length).toBe(2);
   expect(component.featuredVideos).toEqual(mockVideos);
 });

 it('should display the correct number of video cards', () => {
   fixture.detectChanges();
   const compiled = fixture.nativeElement as HTMLElement;
   const videoCards = compiled.querySelectorAll('app-video-card');
   expect(videoCards.length).toEqual(2);
 });
});

