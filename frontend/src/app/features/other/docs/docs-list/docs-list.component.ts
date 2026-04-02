import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ApiCategory } from '@shared/models/api-docs.model';
import { DocService } from '@core/services/docs/DocService';

@Component({
  selector: 'app-docs-index',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './docs-list.component.html',
  styleUrl: './docs-list.component.css',
})
export class DocsListComponent implements OnInit {
  categories: ApiCategory[] = [];

  constructor(private docService: DocService) {}

  ngOnInit() {
    this.categories = this.docService.getCategoryStructure();
  }
}
