import { Injectable } from '@angular/core';
import {UserProfile} from '@shared/models/user-profile.model';

@Injectable({ providedIn: 'root' })
export class AuthStateService {
  private accessToken: string | null = null;
  private userProfile: UserProfile | null = null;

  setAccessToken(token: string) {
    this.accessToken = token;
  }

  getAccessToken(): string | null {
    return this.accessToken;
  }

  setUserProfile(userProfile: UserProfile) {
    this.userProfile = userProfile;
  }

  getUserProfile(): UserProfile | null {
    return this.userProfile;
  }

  clear() {
    this.accessToken = null;
    this.userProfile = null;
  }

  isAuthenticated(): boolean {
    return this.accessToken !== null;
  }
}
