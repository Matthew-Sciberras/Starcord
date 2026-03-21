import { Component, OnInit, ViewChild, ElementRef, AfterViewChecked, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '@core/services/chat/chat.service';
import { UserService } from '@core/services/user/user.service';
import { switchMap, filter, map, tap } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

interface MessageGroup {
  dateLabel: string;
  messages: any[];
}

@Component({
  selector: 'app-chat-messages',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './chat-messages.html',
  styleUrl: './chat-messages.css',
})
export class ChatMessagesComponent implements OnInit, AfterViewChecked {
  @ViewChild('scrollContainer') private scrollContainer!: ElementRef;

  currentChannelId: string | null = null;
  groupedMessages: MessageGroup[] = []; // Grouped for headers
  private shouldScroll = false;

  constructor(
    private route: ActivatedRoute,
    private chatService: ChatService,
    private userService: UserService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.paramMap.pipe(
      map(params => params.get('id')),
      filter(id => !!id),
      tap(id => {
        this.groupedMessages = [];
        this.currentChannelId = id;
        this.cdr.detectChanges();
      }),
      switchMap(id => this.chatService.getMessagesByChannel(id!).pipe(
        map((res: any) => this.processMessages(res.messages || []))
      ))
    ).subscribe({
      next: (groups) => {
        this.groupedMessages = groups;
        this.shouldScroll = true;
        this.cdr.detectChanges();
      }
    });
  }

  private processMessages(raw: any[]): MessageGroup[] {
    const groups: MessageGroup[] = [];

    raw.forEach(m => {
      const user = this.userService.getUserById(m.authorID);
      const date = new Date(m.timestamp);
      const dateLabel = this.getDateLabel(date);

      const normalizedMsg = {
        ...m,
        displayName: user ? user.displayName : `User ${m.authorID}`,
        profilePicture: user?.profilePicture || 'assets/images/pfp_christmas.png',
        // New conditional time display logic
        displayTime: this.formatMessageTime(date),
        uniqueId: typeof m.messageID === 'object' ? m.messageID.source : String(m.messageID)
      };

      const lastGroup = groups[groups.length - 1];
      if (lastGroup && lastGroup.dateLabel === dateLabel) {
        lastGroup.messages.push(normalizedMsg);
      } else {
        groups.push({ dateLabel, messages: [normalizedMsg] });
      }
    });

    return groups;
  }

  private formatMessageTime(date: Date): string {
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    const yesterday = new Date(today);
    yesterday.setDate(yesterday.getDate() - 1);

    const msgDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    const timeStr = date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false });

    if (msgDate.getTime() === today.getTime()) {
      return timeStr;
    } else if (msgDate.getTime() === yesterday.getTime()) {
      return `Yesterday at ${timeStr}`;
    } else {
      const day = String(date.getDate()).padStart(2, '0');
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const year = date.getFullYear();
      return `${day}/${month}/${year} ${timeStr}`;
    }
  }

  private getDateLabel(date: Date): string {
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    const yesterday = new Date(today);
    yesterday.setDate(yesterday.getDate() - 1);

    const msgDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());

    if (msgDate.getTime() === today.getTime()) return 'Today';
    if (msgDate.getTime() === yesterday.getTime()) return 'Yesterday';

    const day = date.getDate();
    const suffix = (d: number) => {
      if (d > 3 && d < 21) return 'th';
      switch (d % 10) {
        case 1: return "st";
        case 2: return "nd";
        case 3: return "rd";
        default: return "th";
      }
    };
    const month = date.toLocaleString('default', { month: 'long' });
    return `${day}${suffix(day)} ${month} ${date.getFullYear()}`;
  }

  ngAfterViewChecked() {
    if (this.shouldScroll) {
      this.scrollToBottom();
      this.shouldScroll = false;
    }
  }

  private scrollToBottom(): void {
    if (!this.scrollContainer) return;
    setTimeout(() => {
      this.scrollContainer.nativeElement.scrollTop = this.scrollContainer.nativeElement.scrollHeight;
    }, 50);
  }
}
