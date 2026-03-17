import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarUserComponent } from './sidebar-user.component';

describe('SidebarUserComponent', () => {
  let component: SidebarUserComponent;
  let fixture: ComponentFixture<SidebarUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarUserComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
