import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ChannelResponse } from "./channel-response.model";
import { Observable } from "rxjs";

@Injectable({
providedIn: 'root'   // Makes it globally accessable
})
export class ChannelService {
  private readonly baseURL = 'http://localhost:8080/api/v1/channels';

  constructor(private http: HttpClient) {}

  getChannel(channelID: Number): Observable<ChannelResponse> {
      return this.http.get<ChannelResponse>(
        `${this.baseURL}/get/${channelID}`
      );
    }
}
