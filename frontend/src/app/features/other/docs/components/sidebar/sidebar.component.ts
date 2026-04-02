import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd, RouterLink, RouterLinkActive } from '@angular/router';
import { filter } from 'rxjs/operators';
import {LucideAngularModule, ChevronRight, BookOpen, Key, Search, Lock} from 'lucide-angular';
import { DocService } from '@core/services/docs/DocService';
import { ApiCategory } from '@shared/models/api-docs.model';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, LucideAngularModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
})
export class SidebarComponent implements OnInit {
  menuData: ApiCategory[] = [];
  expandedCategories: { [key: string]: boolean } = {};

  readonly ChevronRight = ChevronRight;
  readonly BookOpen = BookOpen;
  readonly Key = Key;
  readonly Search = Search;

  constructor(private docService: DocService, private router: Router) {
    this.menuData = this.docService.getCategoryStructure();
  }

  ngOnInit() {
    this.checkActiveRoute();

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.checkActiveRoute();
    });
  }

  private checkActiveRoute() {
    this.menuData.forEach(cat => {
      const isChildActive = cat.endpoints.some(end => this.router.url.includes(end.id));
      const isCategoryPageActive = this.router.url.includes(`/category/${cat.name.toLowerCase()}`);

      if (isChildActive || isCategoryPageActive) {
        this.expandedCategories[cat.name] = true;
      }
    });
  }

  toggleCategory(name: string) {
    this.expandedCategories[name] = !this.expandedCategories[name];
  }

  protected readonly Lock = Lock;
}
