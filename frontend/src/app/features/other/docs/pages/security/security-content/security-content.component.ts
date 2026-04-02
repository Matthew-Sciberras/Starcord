import { ChangeDetectorRef, Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-security-content',
  standalone: true,
  imports: [LucideAngularModule], // Must be imported to avoid template errors
  template: `
    <div class="category-view" (click)="handleGlobalClick($event)">
      <div [innerHTML]="safeContent"></div>
    </div>
  `,
  styleUrls: ['./pages-styles.css'],
  encapsulation: ViewEncapsulation.None
})
export class SecurityContentComponent implements OnInit {
  // 1. Missing Property Declarations
  safeContent?: SafeHtml;
  private cache = new Map<string, string>();
  private preloaded = false;

  // 2. Path Fix: Based on your files, they are in a 'pages' folder
  private readonly priorityDocs = ['auth-flow'];

  // 3. Missing Constructor Injection
  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private sanitizer: DomSanitizer,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.preloadCommonDocs();

    this.route.params.subscribe(params => {
      const topicId = params['id'];
      if (topicId) this.loadDocumentation(topicId);
    });
  }

  private preloadCommonDocs() {
    if (this.preloaded) return;

    this.priorityDocs.forEach(id => {
      // Adjusted path to include /pages/ per your file tree
      this.http.get(`assets/docs/security/${id}.html`, { responseType: 'text' })
        .subscribe(html => this.cache.set(id, html));
    });
    this.preloaded = true;
  }

  private loadDocumentation(topicId: string) {
    if (this.cache.has(topicId)) {
      this.render(this.cache.get(topicId)!);
      return;
    }

    this.http.get(`assets/docs/security/${topicId}.html`, { responseType: 'text' })
      .subscribe({
        next: (html) => {
          this.cache.set(topicId, html);
          this.render(html);
        },
        error: (err) => {
          console.error('Docs fetch failed:', err);
          this.render('<h1 style="padding: 50px">Content not found.</h1>');
        }
      });
  }

  // 4. Missing handleGlobalClick logic for the [innerHTML] click events
  handleGlobalClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    const btn = target.closest('.copy-section-link');

    if (btn) {
      const fragment = btn.getAttribute('data-fragment');
      if (fragment) {
        const fullUrl = `${window.location.origin}${window.location.pathname}#${fragment}`;
        navigator.clipboard.writeText(fullUrl).then(() => {
          console.log('Link copied:', fullUrl);
        });
      }
    }
  }

  private render(html: string) {
    this.safeContent = this.sanitizer.bypassSecurityTrustHtml(html);
    this.cdr.detectChanges();
  }
}
