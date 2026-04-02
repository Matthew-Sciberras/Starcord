import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthenticationHelpComponent } from './authentication-help.component';

describe('AuthenticationHelpComponent', () => {
  let component: AuthenticationHelpComponent;
  let fixture: ComponentFixture<AuthenticationHelpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthenticationHelpComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthenticationHelpComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
