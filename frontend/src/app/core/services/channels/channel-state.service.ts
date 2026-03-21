import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChannelStateService {
  private activeChannelId$ = new BehaviorSubject<string | null>(null);

  selectedChannel$ = this.activeChannelId$.asObservable();

  setActiveChannel(id: string | null): void {
    this.activeChannelId$.next(id);
  }

  getActiveId(): string | null {
    return this.activeChannelId$.getValue();
  }
}
