import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common'; // Required for | json pipe
import { ApiCategory, ApiEndpoint } from '@shared/models/api-docs.model';
import { DocService } from '@core/services/docs/DocService';

@Component({
  selector: 'app-docs-detail',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule
  ],
  templateUrl: './docs-detail.component.html',
  styleUrl: './docs-detail.component.css',
})
export class DocsDetailComponent implements OnInit {
  endpoint: ApiEndpoint | null = null;
  category: ApiCategory | null = null;

  constructor(
    private route: ActivatedRoute,
    private docService: DocService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['endpointId'];
      const catName = params['catName'];

      if (catName) {
        this.category = this.docService.getCategoryByName(catName);
        this.endpoint = null;
      } else if (id) {
        this.endpoint = this.docService.getEndpointById(id);
        this.category = null;
      }
    });
  }
}
