import { Component } from '@angular/core';
import {LucideAngularModule, Link} from 'lucide-angular';

@Component({
  selector: 'app-authentication-help',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './authentication-help.component.html',
  styleUrl: './authentication-help.component.css',
})
export class AuthenticationHelpComponent {

  readonly Link = Link;

  copyLink(fragment: string) {
    const url = window.location.origin + window.location.pathname;
    const fullUrl = `${url}#${fragment}`;

    navigator.clipboard.writeText(fullUrl).then(() => {
      console.log('Link copied to clipboard:', fullUrl);
    });
  }
}
