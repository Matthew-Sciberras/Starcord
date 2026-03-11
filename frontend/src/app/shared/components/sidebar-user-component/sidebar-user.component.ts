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
    { name: 'Shrey', status: 'femboy', avatarUrl: '', channelID: '2' },
  ];

  addContact() {
    this.contacts.push({ name: 'New User', status: 'online', avatarUrl: 'assets/default.png', channelID: '3'});
  }
}
