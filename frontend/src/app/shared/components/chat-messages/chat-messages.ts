import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewChecked, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '@core/services/chat/chat.service';
import { UserService } from '@core/services/user/user.service';
import { switchMap, filter, map, tap } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { NotificationService } from '@core/services/notification/notification.service';

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
export class ChatMessagesComponent implements OnInit, AfterViewChecked, OnDestroy {
  @ViewChild('scrollContainer') private scrollContainer!: ElementRef;

  currentChannelId: string | null = null;
  groupedMessages: MessageGroup[] = [];
  private shouldScroll = false;
  private subscriptions: Subscription = new Subscription();

  constructor(
    private route: ActivatedRoute,
    private chatService: ChatService,
    private userService: UserService,
    private cdr: ChangeDetectorRef,
    private notificationService: NotificationService,
  ) {}

  ngOnInit(): void {
    this.subscriptions.add(
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
      })
    );

    this.subscriptions.add(
      this.chatService.newMessage$.subscribe((msg) => {
        // If the message has a status already (like 'pending'), treat it as optimistic
        this.appendLiveMessage(msg, !!msg.status);
      })
    );

    this.subscriptions.add(
      this.chatService.watchFeedback().subscribe({
        next: (response) => {
          if (response.status === 200) {
            // Update the pending message to 'sent'
            this.updateMessageStatus(response.data.tempId, 'sent', response.data);
          } else {
            this.notificationService.showError("An error occurred while trying to watch feedback");
            this.updateMessageStatus(response.data?.tempId, 'error');
          }
        },
        error: (err) => console.error('Feedback error:', err)
      })
    );
  }

  private updateMessageStatus(tempId: string, status: 'sent' | 'error', finalData?: any) {
    if (!tempId) return;

    for (const group of this.groupedMessages) {
      const msgIndex = group.messages.findIndex(m => m.uniqueId === tempId);
      if (msgIndex !== -1) {
        group.messages[msgIndex].status = status;
        if (finalData?.messageID) {
          group.messages[msgIndex].uniqueId = String(finalData.messageID);
        }
        break;
      }
    }
    this.cdr.detectChanges();
  }

  private appendLiveMessage(m: any, isOptimistic = false) {
    const authorId = String(m.authorID || m.authorId);
    const user = this.userService.getUserById(authorId);

    const date = new Date(m.timestamp);
    const dateLabel = this.getDateLabel(date);

    const normalizedMsg = {
      ...m,
      displayName: user ? user.displayName : `User ${authorId}`,
      profilePicture: user?.profilePicture || 'assets/images/pfp_christmas.png',
      displayTime: this.formatMessageTime(date),
      uniqueId: String(m.messageID || m.tempId),
      status: m.status || (isOptimistic ? 'pending' : 'sent')
    };

    const lastGroup = this.groupedMessages[this.groupedMessages.length - 1];

    if (lastGroup && lastGroup.dateLabel === dateLabel) {
      lastGroup.messages.push(normalizedMsg);
    } else {
      this.groupedMessages.push({
        dateLabel: dateLabel,
        messages: [normalizedMsg]
      });
    }

    this.shouldScroll = true;
    this.cdr.detectChanges();
  }

  private processMessages(raw: any[]): MessageGroup[] {
    const groups: MessageGroup[] = [];
    raw.forEach(m => {
      const authorId = String(m.authorID || m.authorId);
      const user = this.userService.getUserById(authorId);
      const date = new Date(m.timestamp);
      const dateLabel = this.getDateLabel(date);

      const normalizedMsg = {
        ...m,
        displayName: user ? user.displayName : `User ${authorId}`,
        profilePicture: user?.profilePicture || 'assets/images/pfp_christmas.png',
        displayTime: this.formatMessageTime(date),
        uniqueId: String(m.messageID || m.id),
        status: 'sent' // Loaded history is always 'sent'
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

    if (msgDate.getTime() === today.getTime()) return timeStr;
    if (msgDate.getTime() === yesterday.getTime()) return `Yesterday at ${timeStr}`;
    return `${String(date.getDate()).padStart(2, '0')}/${String(date.getMonth() + 1).padStart(2, '0')}/${date.getFullYear()} ${timeStr}`;
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
        case 1: return "st"; case 2: return "nd"; case 3: return "rd"; default: return "th";
      }
    };
    return `${day}${suffix(day)} ${date.toLocaleString('default', { month: 'long' })} ${date.getFullYear()}`;
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

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}
