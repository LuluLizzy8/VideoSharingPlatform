import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    httpController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpController.verify();
  });

  it('should subscribe to a user', () => {
    const userId = '123';
    service.subscribeToUser(userId).subscribe(response => {
      expect(response).toBeTrue();
    });

    const req = httpController.expectOne(`http://localhost:8080/api/user/subscribe/${userId}`);
    expect(req.request.method).toBe('POST');
    req.flush(true); 
  });

  it('should unsubscribe from a user', () => {
    const userId = '123';
    service.unsubscribeToUser(userId).subscribe(response => {
      expect(response).toBeTrue();
    });

    const req = httpController.expectOne(`http://localhost:8080/api/user/unsubscribe/${userId}`);
    expect(req.request.method).toBe('POST');
    req.flush(true); 
  });

  it('should register a user and set userId', () => {
    const expectedUserId = 'user123';
    service.registerUser();
    const req = httpController.expectOne('http://localhost:8080/api/user/register');
    req.flush(expectedUserId);

    expect(service.getUserId()).toBe(expectedUserId);
  });
});
