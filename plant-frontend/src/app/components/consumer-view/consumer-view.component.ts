import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/utils/services/auth.service';
import { UserService } from 'src/app/utils/services/user.service';

@Component({
  selector: 'app-consumer-view',
  templateUrl: './consumer-view.component.html',
  styleUrls: ['./consumer-view.component.css']
})
export class ConsumerViewComponent {
  constructor(private userService: UserService, private router: Router, private authService: AuthService){}
  get isLoggedIn(){
    return this.authService.isLoggedIn();
  }
  
  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
}
}