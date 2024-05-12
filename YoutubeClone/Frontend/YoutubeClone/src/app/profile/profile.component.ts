import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service'; 

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  userId = "";

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userId = this.userService.getUserId();
  }
}
