import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ChatService } from '@core/services/chat/chat.service';
import { AuthStateService } from '@core/auth/auth-state.service';

import { LucideAngularModule } from 'lucide-angular';
import { SidebarUserComponent } from '@shared/components/sidebar-user/sidebar-user.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
  imports: [
    SidebarUserComponent,
    LucideAngularModule,
    FormsModule
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

      // Subscribe to DMs
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

  /**
   * Adjusts the height of the textarea dynamically as the user types.
   * Limits growth based on the CSS max-height
   */
  adjustHeight(el: HTMLTextAreaElement) {
    el.style.height = 'auto'; // Reset height to recalculate scrollHeight
    el.style.height = el.scrollHeight + 'px';
  }

  handleSend(event: Event, el: HTMLTextAreaElement) {
    // Prevent the default "Enter" behavior (new line) unless Shift is held
    event.preventDefault();

    const message = el.value.trim();

    if (message) {
      console.log('Sending message:', message);

      const payload = {
        content: message,
        channelId: "278835366006784", // To be updated
      };

      this.chatService.sendMessage('/app/chat.private', payload);

      // UI Reset
      el.value = '';
      el.style.height = 'auto';
    }
  }

  /**
   * Original helper method for manual console testing.
   * ng.getComponent(document.querySelector("app-home")).testSend("hello", "278835366006784")
   */
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
