import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUser } from 'src/app/utils/modals/LoginUser';
import { User } from 'src/app/utils/modals/User';
import { AuthenticationService } from 'src/app/utils/services/authentication-service.service';
import { UserService } from 'src/app/utils/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private userService: UserService, private authService: AuthenticationService,private router: Router) { }
  // Properties for the login form
  loginUser: LoginUser = {
    email: '',
    password: ''
  };

  // Function to handle form submission
  login() {
    if (this.loginUser.email && this.loginUser.password) {
      this.userService.login(this.loginUser).subscribe(
        user => {
          console.log('Login successful');
          this.authService.setUserId(user.id);
          this.router.navigate(['/']);
        },
        error => {
          console.log('Login failed');
        }
      );
    } else {
      console.log('Please enter both email and password.');
    }
  }
  
}
