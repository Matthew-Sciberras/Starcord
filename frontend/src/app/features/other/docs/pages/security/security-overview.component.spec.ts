import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecurityOverviewComponent } from './security-overview.component';

describe('SecurityOverviewComponent', () => {
  let component: SecurityOverviewComponent;
  let fixture: ComponentFixture<SecurityOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SecurityOverviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SecurityOverviewComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
