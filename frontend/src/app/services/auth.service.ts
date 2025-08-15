import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = '/api/auth/user';

  constructor(private http: HttpClient) {}

  register(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, userData);
  }

  login(loginData: any): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.baseUrl}/login`, loginData)
      .pipe(
        tap(response => {
        // console.log(loginData);
        // console.log(response);
          // Save token to localStorage or sessionStorage
          localStorage.setItem('authToken', response.token);
        })
      );
  }

}
