import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatMessages } from './chat-messages';

describe('ChatMessages', () => {
  let component: ChatMessages;
  let fixture: ComponentFixture<ChatMessages>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChatMessages]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatMessages);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
