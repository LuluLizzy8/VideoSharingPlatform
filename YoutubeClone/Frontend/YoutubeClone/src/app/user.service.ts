import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userId: string = "";
  
  constructor(private httpClient: HttpClient) { }
  
  isSubscribedToUser(userId: string): Observable<boolean> {
    return this.httpClient.get<boolean>(`http://localhost:8080/api/user/isSubscribed/${userId}`);
  }
  
  subscribeToUser(userId: string): Observable<boolean> {
	return this.httpClient.post<boolean>("http://localhost:8080/api/user/subscribe/" + userId, null);
  }
  
  unsubscribeToUser(userId: string): Observable<boolean> {
	return this.httpClient.post<boolean>("http://localhost:8080/api/user/unsubscribe/" + userId, null);
  }
  
  registerUser() {
	this.httpClient.get("http://localhost:8080/api/user/register", {responseType: "text"})
		.subscribe( data => {
			this.userId = data;
		})
  }
  
  getSubscriptions(): Observable<Array<string>> {
    return this.httpClient.get<Array<string>>('http://localhost:8080/api/user/subscriptions');
  }
  
  getUserId(): string {
	return this.userId;
  }
  
  getUserName(): Observable<string> {
	return this.httpClient.get<string>('http://localhost:8080/api/user/name');
  }

}