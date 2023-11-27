import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/utils/modals/User';
import { AuthService } from 'src/app/utils/services/auth.service';
import { UserService } from 'src/app/utils/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private userService: UserService, private router: Router, private authService: AuthService) { }

  // Properties for the registration form
  registerUser: User = {
    username: '',
    email: '',
    password: '',
    phoneNumber: '',
    role: 'CONSUMER' // Assuming 'consumer' is the default role for registration
  };

  // Function to handle form submission
  register() {
    if (this.registerUser.username && this.registerUser.email && this.registerUser.password && this.registerUser.phoneNumber) {
      this.authService.register(this.registerUser)
        .subscribe(
          user => {
            console.log('registered!!!!')
            // Successful registration, navigate to a different page
            // this.router.navigate(['/dashboard']); // Change this route to your desired page
          },
          error => {
            console.log(error); // Display error message
          }
        );
    } else {
      console.log('Please fill in all the fields.'); // Display error message
    }
  }
}
