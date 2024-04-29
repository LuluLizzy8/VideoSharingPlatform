import { Component, OnInit } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'YoutubeClone';
  
  constructor(private oidcSecurityService: OidcSecurityService) { }
  
  ngOnInit() {
    this.oidcSecurityService
      .checkAuth()
      .subscribe(({ isAuthenticated }) => {
        // If LoginResponse is a type that has isAuthenticated, make sure it's imported
        console.log('app is authenticated', isAuthenticated);
      });
  }

}