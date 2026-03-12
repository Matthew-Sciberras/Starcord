import { Component } from '@angular/core';
import { ChannelResponse } from '@core/channels/channel-response.model';
import {ChannelService} from '@core/channels/channel.service';
import {map, Observable} from 'rxjs';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-sidebar-user',
  imports: [
    AsyncPipe
  ],
  templateUrl: './sidebar-user.component.html',
  styleUrl: './sidebar-user.component.css',
})
export class SidebarUserComponent {
  contacts$: Observable<ChannelResponse[]>;

  constructor(private channelService: ChannelService) {
    this.contacts$ = this.channelService.getChats().pipe(
      map(res => res.channels)
    );
  }
}
