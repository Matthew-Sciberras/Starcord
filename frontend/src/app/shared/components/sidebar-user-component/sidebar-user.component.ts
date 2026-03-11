import { Component } from '@angular/core';
import {Contact} from '@shared/components/sidebar-user-component/contact.model';

@Component({
  selector: 'app-sidebar-user',
  imports: [],
  templateUrl: './sidebar-user.component.html',
  styleUrl: './sidebar-user.component.css',
})
export class SidebarUserComponent {
  contacts: Contact[] = [
    { name: 'Star', status: 'coding', avatarUrl: '',  channelID: '1'},
    { name: 'Shrey', status: 'femboy', avatarUrl: 'https://images-ext-1.discordapp.net/external/vOxymJwdFofMOg084iyR4apBrj1Tz-d8OF2QbyRX4P8/%3Fsize%3D4096/https/cdn.discordapp.com/avatars/410400620756074498/b41b13dcc2439ff02c765769aa2a6c10.png?format=webp&quality=lossless&width=230&height=230', channelID: '2' },
  ];

  addContact() {
    this.contacts.push({ name: 'New User', status: 'online', avatarUrl: 'assets/default.png', channelID: '3'});
  }
}
