import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/utils/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(private userService: UserService, private router: Router){}
  get isLoggedIn() {
    let isLoggedIn = false;
    this.userService.user$.subscribe(user => {
      isLoggedIn = user !== null;
    });
    return isLoggedIn;
  }
  
  logout() {
    this.userService.logout();
    this.router.navigate(['/']);
  }

  
}
