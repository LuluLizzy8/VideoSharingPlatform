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
    MatCardModule  // Add MatCardModule here
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
    // Create a mock for UserService with an observable that returns an array of user names
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
    fixture.detectChanges();  // trigger initial data binding
  });
  
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load subscribed user names on init', () => {
    // After ngOnInit, the subscribedUserNames should be filled with the mock data
    expect(component.subscribedUserNames.length).toBe(3);
    expect(component.subscribedUserNames).toEqual(['User One', 'User Two', 'User Three']);
    expect(mockUserService.getSubscriptions).toHaveBeenCalled();
  });

  // You can add more tests here to check for potential error handling or different response scenarios
});


