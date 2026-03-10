import { Component } from '@angular/core';
import {Contact} from '@shared/components/sidebar-user-component/contact.model';

@Component({
  selector: 'app-sidebar-user',
  imports: [],
  templateUrl: './sidebar-user.component.html',
  styleUrl: './sidebar-user.component.css',
})
@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html'
})
export class ContactListComponent {
  contacts: Contact[] = [
    { name: 'Star', status: 'coding', avatarUrl: '' },
    { name: 'Shrey', status: 'femboy', avatarUrl: '' },
  ];

  addContact() {
    this.contacts.push({ name: 'New User', status: 'online', avatarUrl: 'assets/default.png' });
  }
}
