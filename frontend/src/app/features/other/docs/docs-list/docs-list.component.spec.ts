import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocsListComponent } from './docs-list.component';

describe('DocsListComponent', () => {
  let component: DocsListComponent;
  let fixture: ComponentFixture<DocsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DocsListComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
