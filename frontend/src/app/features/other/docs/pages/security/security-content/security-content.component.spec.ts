import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecurityContentComponent } from './security-content.component';

describe('SecurityContentComponent', () => {
  let component: SecurityContentComponent;
  let fixture: ComponentFixture<SecurityContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SecurityContentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SecurityContentComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
