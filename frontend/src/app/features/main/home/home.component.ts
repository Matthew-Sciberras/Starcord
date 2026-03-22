import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ChatService } from '@core/services/chat/chat.service';
import { AuthStateService } from '@core/auth/auth-state.service';
import { LucideAngularModule } from 'lucide-angular';
import { SidebarUserComponent } from '@shared/components/sidebar-user/sidebar-user.component';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { ChannelStateService } from '@core/services/channels/channel-state.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
  imports: [
    SidebarUserComponent,
    LucideAngularModule,
    FormsModule,
    RouterOutlet,
  ]
})
export class HomeComponent implements OnInit, OnDestroy {
  private dmSub?: Subscription;

  constructor(
    private chatService: ChatService,
    private authState: AuthStateService,
    private channelState: ChannelStateService,
  ) {}

  ngOnInit() {
    if (this.authState.isAuthenticated()) {
      const user = this.authState.getUserProfile();
      console.log(`%c [Starcord] Initializing Chat for ${user?.username} `, 'background: #7289da; color: white');

      this.dmSub = this.chatService.watchDMs().subscribe({
        next: (message) => {
          console.log('%c [New Message Received] ', 'color: #43b581; font-weight: bold');
          console.log('Payload:', message);

          const activeChannelId = this.channelState.getActiveId();

          // Using String() to ensure comparison works regardless of type (number vs string)
          if (String(message.channelID) === String(activeChannelId)) {
            this.chatService.announceNewMessage(message);
          } else {
            console.log(`Message for channel ${message.channelID} ignored (Active: ${activeChannelId})`);
          }
        },
        error: (err) => console.error('DM Subscription Error:', err)
      });

    }
  }

  adjustHeight(el: HTMLTextAreaElement) {
    el.style.height = 'auto';
    el.style.height = el.scrollHeight + 'px';
  }

  handleSend(event: Event, el: HTMLTextAreaElement) {
    event.preventDefault();
    const message = el.value.trim();

    if (message) {
      const payload = {
        content: message,
        channelId: this.channelState.getActiveId()
      };
      this.chatService.sendMessage('/app/chat.private', payload);
      el.value = '';
      el.style.height = 'auto';
    }
  }

  ngOnDestroy() {
    if (this.dmSub) {
      this.dmSub.unsubscribe();
    }
  }
}
