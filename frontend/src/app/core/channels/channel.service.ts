import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ChannelResponse } from "./channel-response.model";
import {Observable, tap} from "rxjs";

@Injectable({
providedIn: 'root'
})
export class ChannelService {
  private readonly baseURL = 'http://localhost:8080/api/v1/channels';

  constructor(private http: HttpClient) {}

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
}
