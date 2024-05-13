import { Component, OnInit } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'YoutubeClone';
  
  constructor(private oidcSecurityService: OidcSecurityService, private userService: UserService) { }
  
  ngOnInit() {
    this.oidcSecurityService
      .checkAuth()
      .subscribe(({ isAuthenticated }) => {
        console.log('app is authenticated', isAuthenticated);
        
        if(isAuthenticated == true){
        	this.userService.registerUser();
			console.log("registered user");
        }
      });
  }

}