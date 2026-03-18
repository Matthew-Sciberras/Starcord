import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ChatService } from '@core/auth/services/chat/chat.service'; // Update path
import { AuthStateService } from '@core/auth/auth-state.service'; // Update path

import { LucideAngularModule } from 'lucide-angular';
import {SidebarUserComponent} from '@shared/components/sidebar-user/sidebar-user.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [
    SidebarUserComponent,
    LucideAngularModule
  ]
})
export class HomeComponent implements OnInit, OnDestroy {
  private dmSub?: Subscription;

  constructor(
    private chatService: ChatService,
    private authState: AuthStateService
  ) {}

  ngOnInit() {
    if (this.authState.isAuthenticated()) {
      const user = this.authState.getUserProfile();
      console.log(`%c [Starcord] Initializing Chat for ${user?.username} `, 'background: #7289da; color: white');

      // Subscribe to Private Messages
      this.dmSub = this.chatService.watchDMs().subscribe({
        next: (message) => {
          console.log('%c [New Message Received] ', 'color: #43b581; font-weight: bold');
          console.table(message);
        },
        error: (err) => console.error('DM Subscription Error:', err)
      });

    } else {
      console.error('[Starcord] WebSocket aborted: No active session found in AuthStateService.');
    }
  }

  // Helper method to test
  // 'ng.getComponent(document.querySelector("app-home")).testSend("hello", "278835366006784")' in console
  testSend(content: string, channelId: string) {
    const payload = {
      content: content,
      channelId: channelId,
    }
    this.chatService.sendMessage('/app/chat.private', payload);
  }

  ngOnDestroy() {
    if (this.dmSub) {
      this.dmSub.unsubscribe();
      console.log('[Starcord] Cleaned up WebSocket subscriptions.');
    }
  }
}
