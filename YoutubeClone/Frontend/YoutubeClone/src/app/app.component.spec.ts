import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfileComponent } from './profile/profile.component';
import { UserService } from './user.service';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let userServiceMock: jasmine.SpyObj<UserService>;

  beforeEach(async () => {
    // Create a spy object for UserService with a mock method 'getUserId'
    userServiceMock = jasmine.createSpyObj('UserService', ['getUserId']);

    await TestBed.configureTestingModule({
      declarations: [ ProfileComponent ],
      // Provide the mock instead of the actual UserService
      providers: [ { provide: UserService, useValue: userServiceMock } ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    
    // Mock the return value of getUserId
    userServiceMock.getUserId.and.returnValue('user123');
    
    // Trigger initial data binding and ngOnInit lifecycle hook
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set userId based on UserService', () => {
    // Ensure the service method is called
    expect(userServiceMock.getUserId).toHaveBeenCalled();

    // Check if the component's userId is updated correctly
    expect(component.userId).toEqual('user123');
  });
});
