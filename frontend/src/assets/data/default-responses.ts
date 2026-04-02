import {ApiHeader, ApiResponse} from '@shared/models/api-docs.model';

export const BAD_REQUEST_EXCEPTION: ApiResponse = {
  code: 400,
  title: 'Bad Request',
  description: 'Bad Request Body, example the wrong type of request',
  exampleJson: '{\n' +
    '    "errorCode": "BAD_REQUEST",\n' +
    '    "message": "You have already hit the maximum number of members. The maximum for this type of channel is 2",\n' +
    '    "status": 400,\n' +
    '    "timestamp": "2026-03-31T21:48:55.580669300Z"\n' +
    '}'
}

export const INVALID_CREDENTIALS_EXCEPTION: ApiResponse = {
  code: 401,
  title: 'Invalid Credentials',
  description: 'Credentials provided are invalid, usually an email or password',
  exampleJson: '{\n' +
    '    "errorCode": "UNAUTHORIZED",\n' +
    '    "message": "Email or password invalid.",\n' +
    '    "status": 401,\n' +
    '    "timestamp": "2026-03-31T21:58:26.060953200Z"\n' +
    '}'
}

export const UNAUTHORIZED_EXCEPTION: ApiResponse = {
  code: 401,
  title: 'Unauthorized',
  description: 'Happens when you attempt to view data, and you are not logged in',
  exampleJson: '{\n' +
    '    "errorCode": "UNAUTHORIZED",\n' +
    '    "message": "Please login or signup before attempting to view this data.",\n' +
    '    "status": 401,\n' +
    '    "timestamp": "2026-03-31T22:00:32.0649541240Z"\n' +
    '}'
}

export const FORBIDDEN_EXCEPTION: ApiResponse = {
  code: 401,
  title: 'Forbidden',
  description: 'Happens when you attempt to view data you are not allowed to see, but you are authenticated',
  exampleJson: '{\n' +
    '    "errorCode": "FORBIDDEN",\n' +
    '    "message": "You are not permitted to see this channel.",\n' +
    '    "status": 401,\n' +
    '    "timestamp": "2026-03-31T22:01:38.06392541211Z"\n' +
    '}'
}

export const NOT_FOUND_EXCEPTION: ApiResponse = {
  code: 404,
  title: 'Not Found',
  description: "Happens when you attempt to get data that is not found, or doesn't exist",
  exampleJson: '{\n' +
    '    "errorCode": "NOT_FOUND",\n' +
    '    "message": "No channel found with that Id.",\n' +
    '    "status": 401,\n' +
    '    "timestamp": "2026-03-31T22:02:11.3122343567"\n' +
    '}'
}

export const EMAIL_IN_USE_EXCEPTION: ApiResponse = {
  code: 409,
  title: 'Email in use',
  description: 'Another user is already using that email',
  exampleJson: '{\n' +
    '    "errorCode": "CONFLICT",\n' +
    '    "message": "This email is already in use.",\n' +
    '    "status": 409,\n' +
    '    "timestamp": "2026-03-31T22:08:56.614495900Z"\n' +
    '}'
}

export const USERNAME_IN_USE_EXCEPTION: ApiResponse = {
  code: 409,
  title: 'Username in use',
  description: 'Another user is already using that username',
  exampleJson: '{\n' +
    '    "errorCode": "CONFLICT",\n' +
    '    "message": "This username is already in use.",\n' +
    '    "status": 409,\n' +
    '    "timestamp": "2026-03-31T22:13:19.903419Z"\n' +
    '}'
}

export const RATELIMIT_EXCEPTION: ApiResponse = {
  code: 429,
  title: 'Rate limit',
  description: "Error showing that you have exceeded the maximum number of requests in a given time",
  exampleJson: '{\n' +
    '    "errorCode": "TOO_MANY_REQUESTS",\n' +
    '    "message": "Too Many Requests. Please try again later.",\n' +
    '    "status": 429,\n' +
    '    "timestamp": "2026-03-31T12:21:36.986531200Z"\n' +
    '}'
}

export const INTERNAL_SERVER_EXCEPTION: ApiResponse = {
  code: 500,
  title: 'Internal Server Error',
  description: 'This is usually something out of your control, something is wrong with the server.',
  exampleJson: '{\n' +
    '    "errorCode": "INTERNAL_SERVER_ERROR",\n' +
    '    "message": "An unknown error occurred.",\n' +
    '    "status": 500,\n' +
    '    "timestamp": "2026-03-31T22:15:25.297525600Z"\n' +
    '}'
}

export const STANDARD_EXCEPTIONS = {
  BAD_REQUEST_EXCEPTION,
  UNAUTHORIZED_EXCEPTION,
  FORBIDDEN_EXCEPTION,
  RATELIMIT_EXCEPTION,
  INTERNAL_SERVER_EXCEPTION,
}

export const AUTHORIZATION_HEADER: ApiHeader = {
  name: 'Authorization',
  type: 'string',
  description: 'Your access token, passed via "Bearer ..."\n\nSee authentication docs for more info',
  exampleValue: 'Bearer nothingtoseehereofficer',
  required: true
}
