import { Routes } from '@angular/router';
import { LoginComponent } from '@features/auth/login/login.component';
import { SignupComponent } from '@features/auth/signup/signup.component';
import { HomeComponent } from '@features/main/home/home.component';
import {ChatMessagesComponent} from '@shared/components/chat-messages/chat-messages';
import {endpointResolver} from '@shared/resolvers/endpoint-resolver';
import {DocsLayoutComponent} from '@features/other/docs/docs-layout/docs-layout.component';
import {DocsListComponent} from '@features/other/docs/docs-list/docs-list.component';
import {DocsDetailComponent} from '@features/other/docs/docs-detail/docs-detail.component';
import {GettingStartedComponent} from '@features/other/docs/pages/getting-started/getting-started.component';
import {
  AuthenticationHelpComponent
} from '@features/other/docs/pages/security/pages/authentication-help/authentication-help.component';
import {SecurityOverviewComponent} from '@features/other/docs/pages/security/security-overview.component';
import {
  SecurityLandingComponent
} from '@features/other/docs/pages/security/security-landing/security-landing.component';
import {
  SecurityContentComponent
} from '@features/other/docs/pages/security/security-content/security-content.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' }, // Better than repeating LoginComponent twice
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  {
    path: 'home',
    component: HomeComponent,
    children: [
      { path: 'channel/:id', component: ChatMessagesComponent }
    ],
    title: "Starcord | Home"
  },
  {
    path: 'docs',
    component: DocsLayoutComponent,
    children: [
      {
        path: '',
        component: DocsListComponent,
        resolve: { docData: endpointResolver }
      },
      { path: 'getting-started', component: GettingStartedComponent },

      {
        path: 'security',
        children: [
          { path: '', component: SecurityLandingComponent },
          { path: ':id', component: SecurityContentComponent }
        ]
      },
      { path: 'category/:catName', component: DocsDetailComponent },

      { path: ':endpointId', component: DocsDetailComponent },
    ],
    title: "Starcord | Docs"
  }
];
