import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecurityLandingComponent } from './security-landing.component';

describe('SecurityLandingComponent', () => {
  let component: SecurityLandingComponent;
  let fixture: ComponentFixture<SecurityLandingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SecurityLandingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SecurityLandingComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
