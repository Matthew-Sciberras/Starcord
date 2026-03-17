import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthStateService} from '@core/auth/auth-state.service';
import {Observable} from 'rxjs';
import {UserProfile} from '@shared/models/user-profile.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly baseURL = 'http://localhost:8080/api/v1/users';

  constructor(
    private http: HttpClient,
    private authStateService: AuthStateService,
  ) {}

  getMultipleUsers(userIds: number[]): Observable<UserProfile[]> {
    const body = {
      users: userIds
    };

    return this.http.post<UserProfile[]>(`${this.baseURL}/get`, body);
  }
}
