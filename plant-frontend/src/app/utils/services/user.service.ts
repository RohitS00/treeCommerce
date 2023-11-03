import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { User } from '../modals/User';
import { LoginUser }from '../modals/LoginUser'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080'; 

  private userSubject = new BehaviorSubject<User | null>(null); //store the current user. It starts with null as initially, no user is logged in.
  user$ = this.userSubject.asObservable(); //observable that will allow components to subscribe and get updates whenever the user changes.


  constructor(private http: HttpClient) { }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/registerUser`, user);
  }

  login(user: LoginUser): Observable<any> {
    return this.http.post(`${this.apiUrl}/loginUser`, user)
      .pipe(//update the userSubject with the logged-in user if the login is successful.
        map(response => {
          const loggedInUser = response as User;
          this.userSubject.next(loggedInUser);
          return loggedInUser;
        })
      );
  }
  logout() {
    // Clear the user subject
    this.userSubject.next(null);
  }
}
