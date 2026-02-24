import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class DeviceIDService {
  private readonly storageKey = 'device_id';

  getId(): string {
    let id = localStorage.getItem(this.storageKey);
    if (!id) {
      id = crypto.randomUUID();
      localStorage.setItem(this.storageKey, id);
    }
    return id;
  }
}