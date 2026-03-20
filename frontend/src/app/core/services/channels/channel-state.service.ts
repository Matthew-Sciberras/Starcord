import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthStateService {
  private currentChatId: string | null = null;

  setCurrentChatId(chatId: string | null): void {
    this.currentChatId = chatId;
  }

  getCurrentChatId(): string | null {
    return this.currentChatId;
  }

  clear() {
    this.currentChatId = null;
  }
}
