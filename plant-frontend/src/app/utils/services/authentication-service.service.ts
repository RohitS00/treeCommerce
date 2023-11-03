// authentication.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private userId: number | undefined;

  setUserId(userId: number) {
    this.userId = userId;
  }

  getUserId(): number | undefined{
    return this.userId;
  }

  isAuthenticated(): boolean {
    return !!this.userId;
  }
}
