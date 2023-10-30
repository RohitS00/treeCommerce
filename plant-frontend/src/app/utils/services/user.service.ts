import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../modals/User';
import { LoginUser }from '../modals/LoginUser'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080'; // Replace with your backend URL

  constructor(private http: HttpClient) { }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/registerUser`, user);
  }

  login(user: LoginUser): Observable<any> {
    return this.http.post(`${this.apiUrl}/loginUser`, user);
  }
}
