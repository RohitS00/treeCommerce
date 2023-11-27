import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BehaviorSubject, Observable, map, tap } from "rxjs";
import { LoginUser } from "../modals/LoginUser";
import { User } from "../modals/User";

@Injectable({
    providedIn: 'root'
})
export class AuthService{
  private userId: number | undefined;
    token:string="";
    constructor(private http:HttpClient,private router:Router){}
    private userSubject = new BehaviorSubject<User | null>(null); //store the current user. It starts with null as initially, no user is logged in.
  user$ = this.userSubject.asObservable(); //observable that will allow components to subscribe and get updates whenever the user changes.
    isLoggedIn():boolean{
        if(localStorage.getItem('token'))
            return true;
        else
        return false;
    }
    login(user: LoginUser):Observable<any>{
     
        return this.http.post('http://localhost:8080/api/v1/auth/signin',user)
        .pipe(//update the userSubject with the logged-in user if the login is successful.
        map(response => {
          const loggedInUser = response as User;
          // console.log("loogin:",loggedInUser);
          this.userSubject.next(loggedInUser);
          return loggedInUser;
        })
      );
        
    }
    logout(){
        localStorage.removeItem('token');
        this.userSubject.next(null);
        this.router.navigate(['login']);
    }

    register(user: User): Observable<any> {
      return this.http.post<AuthResponse>('http://localhost:8080/api/v1/auth/signup', user);
    }
    setUserId(userId: number) {
      this.userId = userId;
    }
  
    getUserId(): number | undefined{
      return this.userId;
    }
}
interface AuthResponse {
    token: string;
    // You can include other properties from your response if needed
  }
  