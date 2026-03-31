import { Component, OnInit, OnDestroy } from '@angular/core';
import { ChannelService } from '@core/services/channels/channel.service';
import { UserService } from '@core/services/user/user.service';
import { AuthStateService } from '@core/auth/auth-state.service';
import { ChatService } from '@core/services/chat/chat.service';
import { map, Observable, switchMap, BehaviorSubject, Subscription, take } from 'rxjs';
import { AsyncPipe, TitleCasePipe } from '@angular/common';
import { Router } from '@angular/router';
import { ChannelStateService } from '@core/services/channels/channel-state.service';

@Component({
  selector: 'app-sidebar-user',
  standalone: true,
  imports: [AsyncPipe, TitleCasePipe],
  templateUrl: './sidebar-user.component.html',
  styleUrl: './sidebar-user.component.css',
})
export class SidebarUserComponent implements OnInit, OnDestroy {
  private contactsSubject = new BehaviorSubject<any[]>([]);
  contacts$ = this.contactsSubject.asObservable();

  activeChannelId$: Observable<string | null>;
  private readonly currentUserId: number | undefined;
  private socketSub?: Subscription;

  constructor(
    private channelService: ChannelService,
    private channelState: ChannelStateService,
    private userService: UserService,
    private authStateService: AuthStateService,
    private chatService: ChatService,
    private router: Router,
  ) {
    this.activeChannelId$ = this.channelState.selectedChannel$;
    this.currentUserId = this.authStateService.getUserProfile()?.userID;
  }

  ngOnInit(): void {
    this.channelService.getChats().pipe(
      take(1),
      map(res => res.channels),
      switchMap(channels => {
        const allUserIds = [...new Set(channels.flatMap(c => c.members))];
        return this.userService.getMultipleUsers(allUserIds).pipe(
          map(users => this.mapChannelsToContacts(channels, users))
        );
      })
    ).subscribe(processedContacts => {
      this.contactsSubject.next(this.sortContacts(processedContacts));
    });

    this.socketSub = this.chatService.newMessage$.subscribe(msg => {
      this.handleIncomingMessage(msg);
    });
  }

  private mapChannelsToContacts(channels: any[], users: any[]): any[] {
    const userMap = new Map(users.map(u => [u.userID, u]));
    return channels.map(channel => {
      let contact = { ...channel };
      contact.lastActive = channel.lastMessage ? new Date(channel.lastMessage.timestamp) : new Date(channel.createdAt || 0);

      if (channel.channelType === 'DM') {
        const otherUser = channel.members
          .map((id: any) => userMap.get(id))
          .find((u: any) => u?.userID !== this.currentUserId);

        contact.displayName = otherUser?.displayName || 'Unknown User';
        contact.displayImage = otherUser?.profilePicture || 'assets/images/pfp_christmas.png';
        contact.presence = otherUser?.presence || 'OFFLINE';
      } else {
        contact.displayName = channel.name || 'Group';
        contact.displayImage = channel.image || 'assets/default-group-icon.png';
        contact.presence = '';
      }
      return contact;
    });
  }

  private handleIncomingMessage(msg: any) {
    const currentList = this.contactsSubject.value;
    const channelId = String(msg.channelID || msg.channelId);
    const channelIndex = currentList.findIndex(c => String(c.channelID) === channelId);

    if (channelIndex !== -1) {
      const updatedChannel = { ...currentList[channelIndex], lastActive: new Date(msg.timestamp) };
      const newList = [...currentList];
      newList.splice(channelIndex, 1);
      newList.unshift(updatedChannel);
      this.contactsSubject.next(newList);
    }
  }

  private sortContacts(contacts: any[]): any[] {
    return [...contacts].sort((a, b) => b.lastActive.getTime() - a.lastActive.getTime());
  }

  onChannelClick(channelID: string | number): void {
    const id = channelID.toString();
    this.channelState.setActiveChannel(id);
    void this.router.navigate(['/home/channel', id]);
  }

  ngOnDestroy(): void {
    this.socketSub?.unsubscribe();
  }
}
