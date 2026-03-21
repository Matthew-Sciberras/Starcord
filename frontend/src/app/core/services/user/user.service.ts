import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, of } from 'rxjs';
import { UserProfile } from '@shared/models/user-profile.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly baseURL = 'http://localhost:8080/api/v1/users';

  // The Source of Truth for user data across the app
  private userCache = new Map<string, UserProfile>();

  constructor(private http: HttpClient) {}

  /**
   * Fetches multiple users and saves them to the local cache automatically.
   */
  getMultipleUsers(userIds: (string | number)[]): Observable<UserProfile[]> {
    const stringIds = userIds.map(id => String(id));
    const body = { users: stringIds };

    return this.http.post<UserProfile[]>(`${this.baseURL}/get`, body).pipe(
      tap(users => {
        users.forEach(user => {
          // Ensure we store by string ID for consistent lookup
          this.userCache.set(String(user.userID), user);
        });
      })
    );
  }

  /**
   * Synchronous lookup for the UI.
   * Returns undefined if the user isn't loaded yet.
   */
  getUserById(userId: string | number | undefined): UserProfile | undefined {
    if (!userId) return undefined;
    return this.userCache.get(String(userId));
  }

  /**
   * Manually add a user to the cache (e.g., the current logged-in user).
   */
  addToCache(user: UserProfile): void {
    if (user && user.userID) {
      this.userCache.set(String(user.userID), user);
    }
  }
}
