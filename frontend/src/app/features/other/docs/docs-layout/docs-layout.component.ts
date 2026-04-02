import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from '@features/other/docs/components/sidebar/sidebar.component';

@Component({
  selector: 'app-docs-layout',
  standalone: true,
  imports: [RouterOutlet, SidebarComponent],
  templateUrl: './docs-layout.component.html',
  styleUrl: './docs-layout.component.css'
})
export class DocsLayoutComponent {}
