import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from "@angular/router";


@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrl: './callback.component.css'
})
export class CallbackComponent implements OnInit {

  constructor(private userService: UserService, private router: Router) {
	//this.userService.registerUser();
	//console.log("registered user");
	//this.router.navigateByUrl("");
  }
  
  ngOnInit(): void {
 }
}