import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RxStomp, RxStompState } from '@stomp/rx-stomp';
import { map } from 'rxjs/operators';
import { Observable, Subject } from 'rxjs';
import { AuthStateService } from '@core/auth/auth-state.service';
import { customRxStompConfig } from '@core/config/websocket.config';

@Injectable({ providedIn: 'root' })
export class ChatService {
  private rxStomp: RxStomp;
  private readonly API_URL = 'http://localhost:8080/api/v1/messages';

  // NEW: The "Bus" that allows components to hear live messages
  private newMessageSource = new Subject<any>();
  public newMessage$ = this.newMessageSource.asObservable();

  constructor(
    private authState: AuthStateService,
    private http: HttpClient
  ) {
    this.rxStomp = new RxStomp();

    if (this.authState.isAuthenticated()) {
      this.connect();
    }
  }

  /**
   * NEW: Helper to push a received message into the local UI stream
   */
  announceNewMessage(message: any) {
    this.newMessageSource.next(message);
  }

  private connect() {
    const token = this.authState.getAccessToken();

    this.rxStomp.configure({
      ...customRxStompConfig,
      connectHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    this.rxStomp.activate();

    this.rxStomp.connectionState$.subscribe(state => {
      console.log('Chat WebSocket State:', RxStompState[state]);
    });
  }

  getMessagesByChannel(channelId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/${channelId}`);
  }

  watchDMs(): Observable<any> {
    return this.rxStomp.watch('/user/queue/messages').pipe(
      map(message => JSON.parse(message.body))
    );
  }

  watchGroup(groupId: string): Observable<any> {
    return this.rxStomp.watch(`/topic/group.${groupId}`).pipe(
      map(message => JSON.parse(message.body))
    );
  }

  sendMessage(destination: string, payload: any) {
    this.rxStomp.publish({
      destination: destination,
      body: JSON.stringify(payload)
    });
  }
}
