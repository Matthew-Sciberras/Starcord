import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthStateService } from './auth-state.service';

const AUTH_BASE_URL = 'http://localhost:8080/api/v1/auth';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const authState = inject(AuthStateService);
  const token = authState.getAccessToken();

  if (req.url.startsWith(AUTH_BASE_URL)) {
    return next(req);
  }

  if (!token) {
    return next(req);
  }

  const newReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });

  return next(newReq);
}
