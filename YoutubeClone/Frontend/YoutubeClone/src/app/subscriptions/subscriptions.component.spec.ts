import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SubscriptionsComponent } from './subscriptions.component';
import { UserService } from '../user.service';
import { of } from 'rxjs';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [
    SubscriptionsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatCardModule 
  ],
  providers: [],
  bootstrap: [SubscriptionsComponent]
})
export class AppModule { }


describe('SubscriptionsComponent', () => {
  let component: SubscriptionsComponent;
  let fixture: ComponentFixture<SubscriptionsComponent>;
  let mockUserService: any;

  beforeEach(async () => {
    mockUserService = jasmine.createSpyObj('UserService', ['getSubscriptions']);
    mockUserService.getSubscriptions.and.returnValue(of(['User One', 'User Two', 'User Three']));

    await TestBed.configureTestingModule({
      declarations: [ SubscriptionsComponent ],
      imports: [MatCardModule],
      providers: [
        { provide: UserService, useValue: mockUserService }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscriptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); 
  });
  
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load subscribed user names on init', () => {
    expect(component.subscribedUserNames.length).toBe(3);
    expect(component.subscribedUserNames).toEqual(['User One', 'User Two', 'User Three']);
    expect(mockUserService.getSubscriptions).toHaveBeenCalled();
  });

});


