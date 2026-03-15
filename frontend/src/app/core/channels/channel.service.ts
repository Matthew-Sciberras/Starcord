import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {ChannelResponse, GetChannelResponse} from "./channel-response.model";
import {Observable, tap} from "rxjs";
import {AuthStateService} from '@core/auth/auth-state.service';

@Injectable({
providedIn: 'root'
})
export class ChannelService {
  private readonly baseURL = 'http://localhost:8080/api/v1/channels';

  constructor(
    private http: HttpClient,
    private authStateService: AuthStateService,
  ) {}

  getChannel(channelID: Number): Observable<ChannelResponse> {
    return this.http.get<ChannelResponse>(
      `${this.baseURL}/get/${channelID}`
    );
  }

  getAllChannels(): Observable<ChannelResponse> {
    return this.http.get<ChannelResponse>(`${this.baseURL}/getAll`).pipe(
      tap(data => {
        console.log("Data received:", data);
      })
    );
  }

  getChats(): Observable<GetChannelResponse> {
    return this.http.get<GetChannelResponse>(`${this.baseURL}/getAll/chats`).pipe(
      tap(data => console.log("Data received:", data))
    );
  }
}
