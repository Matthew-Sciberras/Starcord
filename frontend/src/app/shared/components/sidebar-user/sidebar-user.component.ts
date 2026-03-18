import { Component } from '@angular/core';
import { ChannelResponse } from '@core/auth/services/channels/channel-response.model';
import { ChannelService } from '@core/auth/services/channels/channel.service';
import { map, Observable, switchMap, tap } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { UserService } from '@core/auth/services/user/user.service';

@Component({
  selector: 'app-sidebar-user',
  imports: [AsyncPipe],
  templateUrl: './sidebar-user.component.html',
  styleUrl: './sidebar-user.component.css',
})
export class SidebarUserComponent {
  contacts$: Observable<any[]>;
  // Replace with your actual logged-in user ID logic (e.g., from an AuthService)
  private readonly currentUserId = 266190899122176;

  constructor(
    private channelService: ChannelService,
    private userService: UserService,
  ) {
    this.contacts$ = this.channelService.getChats().pipe(
      map(res => res.channels),
      switchMap(channels => {
        const allUserIds = [...new Set(channels.flatMap(c => c.members))];

        return this.userService.getMultipleUsers(allUserIds).pipe(
          tap(users => console.log('Fetched User Models:', users)),
          map(users => {
            const userMap = new Map(users.map(u => [u.userID, u]));

            return channels.map(channel => {
              // Find the other person in the DM
              const otherUser = channel.members
                .map(id => userMap.get(id))
                .find(u => u?.userID !== this.currentUserId);

              return {
                ...channel,
                displayName: otherUser?.displayName || 'Unknown User',
                displayImage: otherUser?.profilePicture || null
              };
            });
          })
        );
      })
    );
  }
}
