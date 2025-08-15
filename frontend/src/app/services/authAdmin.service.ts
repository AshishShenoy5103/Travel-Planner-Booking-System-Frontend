import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthAdminService {
  private baseUrl = '/api/auth/admin';

  constructor(private http: HttpClient) {}

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
