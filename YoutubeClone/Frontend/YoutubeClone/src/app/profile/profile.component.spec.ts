import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfileComponent } from './profile.component';
import { UserService } from '../user.service';
import { By } from '@angular/platform-browser';


describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let mockUserService: any;
  const mockUserId = 'user123';


  beforeEach(async () => {
    // Create a spy for UserService
    mockUserService = jasmine.createSpyObj('UserService', ['getUserId']);
    mockUserService.getUserId.and.returnValue(mockUserId);


    await TestBed.configureTestingModule({
      declarations: [ProfileComponent],
      providers: [
        { provide: UserService, useValue: mockUserService } // Provide the mock service
      ]
    })
    .compileComponents();


    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); // triggers ngOnInit and retrieves the userId
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should initialize userId from UserService', () => {
    // Verify the userId is fetched from the UserService
    expect(component.userId).toEqual(mockUserId);
    // Verify that the service method was called
    expect(mockUserService.getUserId).toHaveBeenCalled();
  });


  it('should display the userId in the template', () => {
    // Check if the rendered userId is the same as the one fetched from the service
    const userIdParagraph = fixture.debugElement.query(By.css('p')).nativeElement.textContent;
    expect(userIdParagraph).toContain(mockUserId);
  });
});





