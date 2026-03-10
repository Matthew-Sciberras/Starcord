import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStateService } from '@app/core/auth/auth-state.service';
import {ChannelService} from '@core/channels/channel.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  constructor(
    private authStateService: AuthStateService,
    private channelService: ChannelService,
  ) {}

  private router = inject(Router);

  ngOnInit() {
    if (!this.authStateService.isAuthenticated()) {
      //this.router.navigateByUrl('/login');
    }
  }

  public clickTest() {
    this.channelService.getAllChannels().subscribe(channels => {
      console.log("Channels:", channels);
    })
  }
}
