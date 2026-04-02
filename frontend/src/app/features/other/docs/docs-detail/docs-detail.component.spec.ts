import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocsDetailComponent } from './docs-detail.component';

describe('DocsDetailComponent', () => {
  let component: DocsDetailComponent;
  let fixture: ComponentFixture<DocsDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocsDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DocsDetailComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
