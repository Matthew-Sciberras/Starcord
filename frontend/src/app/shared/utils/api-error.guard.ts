import { ApiError } from '../models/api-error.model';

export function isApiError(error: unknown): error is ApiError {
  return (
    !!error &&
    typeof error === 'object' &&
    'errorCode' in error &&
    'message' in error &&
    'status' in error
  );
}