import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUser } from 'src/app/utils/modals/LoginUser';
import { User } from 'src/app/utils/modals/User';
import { UserService } from 'src/app/utils/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private userService: UserService, private router: Router) { }
  // Properties for the login form
  loginUser: LoginUser = {
    email: '',
    password: ''
  };

  // Function to handle form submission
  login() {
    if (this.loginUser.email && this.loginUser.password) { // Add if condition here
      this.userService.login(this.loginUser)
        .subscribe(
          user => {
            // Successful login, navigate to a different page
            console.log('success');
            // this.router.navigate(['/dashboard']); // Change this route to your desired page
          },
          error => {
            console.log("err"); // Display error message
          }
        );
    } else {
      console.log('Please enter both email and password.'); // Display error message
    }
  }
}
