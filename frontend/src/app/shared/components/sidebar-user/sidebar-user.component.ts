import { Component } from '@angular/core';
import { ChannelService } from '@core/services/channels/channel.service';
import { UserService } from '@core/services/user/user.service';
import { AuthStateService } from '@core/auth/auth-state.service';
import { map, Observable, switchMap } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { Router } from '@angular/router';
import { ChannelStateService } from '@core/services/channels/channel-state.service';

@Component({
  selector: 'app-sidebar-user',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './sidebar-user.component.html',
  styleUrl: './sidebar-user.component.css',
})
export class SidebarUserComponent {
  contacts$: Observable<any[]>;
  activeChannelId$: Observable<string | null>;
  private readonly currentUserId: number | undefined;

  constructor(
    private channelService: ChannelService,
    private channelState: ChannelStateService,
    private userService: UserService,
    private authStateService: AuthStateService,
    private router: Router,
  ) {
    this.activeChannelId$ = this.channelState.selectedChannel$;
    this.currentUserId = this.authStateService.getUserProfile()?.userID;

    this.contacts$ = this.channelService.getChats().pipe(
      map(res => res.channels),
      switchMap(channels => {
        const allUserIds = [...new Set(channels.flatMap(c => c.members))];

        return this.userService.getMultipleUsers(allUserIds).pipe(
          map(users => {
            const userMap = new Map(users.map(u => [u.userID, u]));

            return channels.map(channel => {
              if (channel.channelType === 'DM') {
                const otherUser = channel.members
                  .map(id => userMap.get(id))
                  .find(u => u?.userID !== this.currentUserId);

                return {
                  ...channel,
                  displayName: otherUser?.displayName || 'Unknown User',
                  displayImage: otherUser?.profilePicture || 'assets/default-avatar.png',
                  status: otherUser?.status || 'Offline'
                };
              }

              if (channel.channelType === 'GROUP') {
                return {
                  ...channel,
                  displayName: channel.name || `Group (${channel.members.length} members)`,
                  displayImage: channel.image || 'assets/default-group-icon.png'
                };
              }

              return {
                ...channel,
                displayName: channel.name || 'General Channel',
                displayImage: 'assets/default-channel-icon.png'
              };
            });
          })
        );
      })
    );
  }

  onChannelClick(channelID: string | number): void {
    const id = channelID.toString();
    this.channelState.setActiveChannel(id);
    void this.router.navigate(['/home/channel', id]);
  }
}
