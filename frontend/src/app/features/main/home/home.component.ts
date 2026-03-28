import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ChatService } from '@core/services/chat/chat.service';
import { AuthStateService } from '@core/auth/auth-state.service';
import { LucideAngularModule } from 'lucide-angular';
import { SidebarUserComponent } from '@shared/components/sidebar-user/sidebar-user.component';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { ChannelStateService } from '@core/services/channels/channel-state.service';
import { CommonModule } from '@angular/common';
import { UserProfile } from '@shared/models/user-profile.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    SidebarUserComponent,
    LucideAngularModule,
    FormsModule,
    RouterOutlet,
  ]
})
export class HomeComponent implements OnInit, OnDestroy {
  private dmSub?: Subscription;
  userProfile: UserProfile | null = null;

  constructor(
    private chatService: ChatService,
    private authState: AuthStateService,
    private channelState: ChannelStateService,
  ) {}

  ngOnInit() {
    if (this.authState.isAuthenticated()) {
      this.userProfile = this.authState.getUserProfile();

      this.dmSub = this.chatService.watchDMs().subscribe({
        next: (message) => {
          const activeChannelId = this.channelState.getActiveId();
          if (String(message.channelID) === String(activeChannelId)) {
            this.chatService.announceNewMessage(message);
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
      const activeId = this.channelState.getActiveId();
      const user = this.authState.getUserProfile();
      const tempId = `temp-${Date.now()}-${Math.random().toString(36).substring(2, 9)}`;

      const optimisticMsg = {
        content: message,
        authorID: user?.userID,
        timestamp: new Date().toISOString(),
        tempId: tempId,
        status: 'pending'
      };

      this.chatService.announceNewMessage(optimisticMsg);

      const payload = {
        content: message,
        channelId: activeId,
        tempId: tempId
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
