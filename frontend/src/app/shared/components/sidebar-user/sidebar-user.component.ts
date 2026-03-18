import { Component } from '@angular/core';
import { ChannelResponse } from '@core/auth/services/channels/channel-response.model';
import { ChannelService } from '@core/auth/services/channels/channel.service';
import { map, Observable, switchMap, tap } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { UserService } from '@core/auth/services/user/user.service';
import { AuthStateService } from '@core/auth/auth-state.service';

@Component({
  selector: 'app-sidebar-user',
  imports: [AsyncPipe],
  templateUrl: './sidebar-user.component.html',
  styleUrl: './sidebar-user.component.css',
})
export class SidebarUserComponent {
  contacts$: Observable<any[]>;
  private readonly currentUserId: number | undefined;

  constructor(
    private channelService: ChannelService,
    private userService: UserService,
    private authStateService: AuthStateService,
  ) {
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
                  displayImage: otherUser?.profilePicture || 'assets/default-avatar.png'
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
}
