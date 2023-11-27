import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/utils/services/auth.service';
import { UserService } from 'src/app/utils/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(private userService: UserService, private router: Router, private authService: AuthService){}
  // get isLoggedIn() {
  //   let isLoggedIn = false;
  //   this.userService.user$.subscribe(user => {
  //     isLoggedIn = user !== null;
  //   });
  //   return isLoggedIn;
  // }
  get isLoggedIn(){
    return this.authService.isLoggedIn();
  }
  
  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  
}
