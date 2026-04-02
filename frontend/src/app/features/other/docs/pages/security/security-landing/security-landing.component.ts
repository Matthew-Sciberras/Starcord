import { Component } from '@angular/core';
import {LucideAngularModule, Link} from 'lucide-angular';
import { copyLink } from '../helper-methods';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-security-landing',
  imports: [
    LucideAngularModule,
    RouterLink
  ],
  templateUrl: './security-landing.component.html',
  styleUrls: [
    '../general-style.css',
    'security-landing.component.css'
  ],
})
export class SecurityLandingComponent {
  readonly copyLink = copyLink;

  readonly Link = Link;
}
