import { TestBed, ComponentFixture, fakeAsync, tick } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { UserService } from './user.service';
import { of } from 'rxjs';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let mockOidcSecurityService: any;
  let mockUserService: any;

  beforeEach(async () => {
    // Mock the services
    mockOidcSecurityService = jasmine.createSpyObj('OidcSecurityService', ['checkAuth']);
    mockUserService = jasmine.createSpyObj('UserService', ['registerUser']);

    // Configure testing module
    await TestBed.configureTestingModule({
      declarations: [ AppComponent ],
      providers: [
        { provide: OidcSecurityService, useValue: mockOidcSecurityService },
        { provide: UserService, useValue: mockUserService }
      ]
    }).compileComponents();

    // Create component fixture
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should check authentication and register user if authenticated', fakeAsync(() => {
    // Setup the return value of checkAuth
    mockOidcSecurityService.checkAuth.and.returnValue(of({ isAuthenticated: true }));

    // Trigger ngOnInit
    fixture.detectChanges();  // ngOnInit gets called here
    tick();  // Simulate the passage of time until all async operations are complete

    // Verify the service methods are called appropriately
    expect(mockOidcSecurityService.checkAuth).toHaveBeenCalled();
    expect(mockUserService.registerUser).toHaveBeenCalled();
    expect(console.log).toHaveBeenCalledWith('app is authenticated', true);
    expect(console.log).toHaveBeenCalledWith('registered user');
  }));

  it('should not register user if not authenticated', fakeAsync(() => {
    // Setup the return value of checkAuth to simulate not authenticated
    mockOidcSecurityService.checkAuth.and.returnValue(of({ isAuthenticated: false }));

    // Trigger ngOnInit
    fixture.detectChanges();  // ngOnInit gets called here
    tick();  // Simulate the passage of time until all async operations are complete

    // Verify the service methods are called appropriately
    expect(mockOidcSecurityService.checkAuth).toHaveBeenCalled();
    expect(mockUserService.registerUser).not.toHaveBeenCalled();
    expect(console.log).toHaveBeenCalledWith('app is authenticated', false);
  }));

  afterEach(() => {
    // Restore the original console.log to avoid side effects
    spyOn(console, 'log');
  });
});
