import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUser } from 'src/app/utils/modals/LoginUser';
import { User } from 'src/app/utils/modals/User';
import { AuthService } from 'src/app/utils/services/auth.service';
import { AuthenticationService } from 'src/app/utils/services/authentication-service.service';
import { UserService } from 'src/app/utils/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private userService: UserService, private authenticationService: AuthenticationService,private router: Router, private authService: AuthService) { }
  // Properties for the login form
  loginUser: LoginUser = {
    email: '',
    password: ''
  };

  // Function to handle form submission
  login() {
    if (this.loginUser.email && this.loginUser.password) {
      this.authService.login(this.loginUser).subscribe(
        user => {
          console.log('Login successfulyy', this.authenticationService.getUserId());
          console.log("this is id",this.authenticationService.getUserId);
          this.authenticationService.setUserId(user.id)
          console.log("this is id",this.authenticationService.getUserId())
          // console.log(this.authenticationService.getUserId())
          console.log(user)
          localStorage.setItem('token',user.token);
        
          this.authService.setUserId(user.id);
          console.log("this is working",user.role)
          if(user.role == 'CONSUMER'){
          this.router.navigate(['consumer-view']);}
          else if(user.role == 'PROVIDER'){
            this.router.navigate(['provider-view']);
          }
        
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
