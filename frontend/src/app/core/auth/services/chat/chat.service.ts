import { Injectable } from '@angular/core';
import { RxStomp, RxStompState } from '@stomp/rx-stomp';
import { map } from 'rxjs/operators';
import { AuthStateService } from '@core/auth/auth-state.service';
import {customRxStompConfig} from '@core/config/websocket.config';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ChatService {
  private rxStomp: RxStomp;

  constructor(private authState: AuthStateService) {
    this.rxStomp = new RxStomp();

    if (this.authState.isAuthenticated()) {
      this.connect();
    }
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

    // Log connection state changes to console
    this.rxStomp.connectionState$.subscribe(state => {
      console.log('Connection State:', RxStompState[state]);
    });
  }

  // Listener for Private DMs
  watchDMs(): Observable<any> {
    return this.rxStomp.watch('/user/queue/messages').pipe(
      map(message => JSON.parse(message.body))
    );
  }

  // Listener for Groups
  watchGroup(groupId: string): Observable<any> {
    return this.rxStomp.watch(`/topic/group.${groupId}`).pipe(
      map(message => JSON.parse(message.body))
    );
  }

  // Method to send a message
  sendMessage(destination: string, payload: any) {
    this.rxStomp.publish({
      destination: destination,
      body: JSON.stringify(payload)
    });
  }
}
