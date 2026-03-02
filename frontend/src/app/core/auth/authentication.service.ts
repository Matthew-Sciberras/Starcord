import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '@app/features/auth/login/login-request.model';
import { LoginResponse } from '@app/features/auth/login/login-response.model';
import { catchError, Observable, throwError } from 'rxjs';
import { DeviceIDService } from './device-id.service';
import { SignupRequest } from '@app/features/auth/signup/signup-request.model';
import { ApiError } from '@app/shared/models/api-error.model';

@Injectable({
  providedIn: 'root', // Makes it globally accessable
})
export class AuthService {
  private readonly baseURL = 'http://localhost:8080/api/v1/auth';

  constructor(
    private http: HttpClient,
    private deviceIDService: DeviceIDService,
  ) {}

  login(request: LoginRequest): Observable<LoginResponse> {
    const headers = new HttpHeaders({
      'X-Device-Id': this.deviceIDService.getId(),
      'Content-Type': 'application/json',
    });

    return this.http.post<LoginResponse>(`${this.baseURL}/login`, request, {
      headers,
      withCredentials: true,
    });
  }

  signup(request: LoginRequest): Observable<void> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    return this.http.post<void>(`${this.baseURL}/signup`, request, { headers }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error.error as ApiError);
      }),
    );
  }
}
