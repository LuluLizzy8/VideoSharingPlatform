import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrl: './subscriptions.component.css'
})
export class SubscriptionsComponent implements OnInit {
  subscribedUserNames: string[] = []; 

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getSubscriptions().subscribe(names => {
      this.subscribedUserNames = names;
    });
  }
}