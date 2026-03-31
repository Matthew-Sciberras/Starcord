import { ApiCategory } from '@shared/models/api-docs.model';
import {
  BAD_REQUEST_EXCEPTION,
  EMAIL_IN_USE_EXCEPTION,
  FORBIDDEN_EXCEPTION, INTERNAL_SERVER_EXCEPTION, INVALID_CREDENTIALS_EXCEPTION, NOT_FOUND_EXCEPTION,
  RATELIMIT_EXCEPTION,
  STANDARD_EXCEPTIONS, UNAUTHORIZED_EXCEPTION,
  USERNAME_IN_USE_EXCEPTION
} from './default-responses';

export const API_STRUCTURE: ApiCategory[] = [
  // Auth
  {
    name: 'Auth',
    basePath: 'http://localhost:8080/api/v1/auth',
    description: 'Authentication endpoint',
    endpoints: [
      {
        id: 'auth-signup',
        path: '/signup',
        title: 'Create account',
        method: 'POST',
        description: 'Create a new account. \n\n You will still need to log in after the signup request',
        bodyParams: [
          { name: 'username', type: 'string', description: 'Your username', exampleValue: 'star123best@gmail.com' },
          { name: 'password', type: 'string', description: 'Your password', exampleValue: '##########'},
          { name: 'email', type: 'string', description: 'Your email', exampleValue: 'star123best@gmail.com' },
          { name: 'displayName', type: 'string', description: 'A display name', exampleValue: 'Star', required: false },
        ],
        responses: [
          { code: 200, title: 'Success', description: 'Successful login', exampleJson: '{\n' +
              '    "createdAt": 1774996384,\n' +
              '    "displayName": "Star",\n' +
              '    "email": "star123best@gmail.com",\n' +
              '    "userID": 283880589279232,\n' +
              '    "username": "star123best"\n' +
              '}' },
          BAD_REQUEST_EXCEPTION,
          EMAIL_IN_USE_EXCEPTION,
          USERNAME_IN_USE_EXCEPTION,
          RATELIMIT_EXCEPTION,
          INTERNAL_SERVER_EXCEPTION
        ].sort((a, b) => a.code - b.code),
        rateLimit: 10,
        authenticated: false
      },
      {
        id: 'auth-login',
        path: '/login',
        title: 'Login',
        method: 'POST',
        description: 'Authenticate and receive a JWT.',
        bodyParams: [
          { name: 'email', type: 'string', description: 'Your email', exampleValue: 'star123best@gmail.com' },
          { name: 'password', type: 'string', description: 'Your password', exampleValue: '##########'}
        ],
        responses: [
          { code: 200, title: 'Success', description: 'Successful login', exampleJson: '{\n' +
              '    "accessToken": "nuh uh not so fast",\n' +
              '    "createdAt": 1770677612,\n' +
              '    "displayName": "Star",\n' +
              '    "email": "star123best@gmail.com",\n' +
              '    "profilePicture": "https://someurl.com",\n' +
              '    "userID": 266190899122176,\n' +
              '    "username": "star123best"\n' +
              '}' },
          BAD_REQUEST_EXCEPTION,
          INVALID_CREDENTIALS_EXCEPTION,
          RATELIMIT_EXCEPTION,
          INTERNAL_SERVER_EXCEPTION
        ],
        rateLimit: 10,
        authenticated: false
      },
      //TODO: Add a header for this endpoint (X-DEVICE-ID)
      {
        id: 'auth-logout',
        path: '/logout',
        title: 'Logout',
        method: 'POST',
        description: 'Log out of an account via a specific device-id.',
        responses: [
          { code: 200, title: 'Success', description: 'Successful logout', exampleJson: '{\n' +
              '    "data": null,\n' +
              '    "message": "Successful Logout",\n' +
              '    "status": 200,\n' +
              '    "timestamp": "2026-03-31T22:42:19.252440200Z"\n' +
              '}' },
          BAD_REQUEST_EXCEPTION,
          UNAUTHORIZED_EXCEPTION,
          INVALID_CREDENTIALS_EXCEPTION,
          NOT_FOUND_EXCEPTION,
          RATELIMIT_EXCEPTION,
          INTERNAL_SERVER_EXCEPTION
        ],
        rateLimit: 10,
        authenticated: true
      },
      {
        id: 'auth-logout-all',
        path: '/logoutAll',
        title: 'Logout all',
        method: 'POST',
        description: 'Log out of all sessions of an account irrespective of device.',
        responses: [
          { code: 200, title: 'Success', description: 'Successful logout', exampleJson: '{\n' +
              '    "data": null,\n' +
              '    "message": "Successful Logout",\n' +
              '    "status": 200,\n' +
              '    "timestamp": "2026-03-31T22:42:19.252440200Z"\n' +
              '}' },
          BAD_REQUEST_EXCEPTION,
          UNAUTHORIZED_EXCEPTION,
          INVALID_CREDENTIALS_EXCEPTION,
          NOT_FOUND_EXCEPTION,
          RATELIMIT_EXCEPTION,
          INTERNAL_SERVER_EXCEPTION
        ],
        rateLimit: 10,
        authenticated: true
      },
      // TODO: Add a header here too
      {
        id: 'auth-refresh-token',
        path: '/refresh',
        title: 'Refresh Token',
        method: 'POST',
        description: 'Refreshes the access token via a refresh token (see Authentication help tab for more).',
        responses: [
          { code: 200, title: 'Success', description: 'Successful logout', exampleJson: '{\n' +
              '    "createdAt": 1774997415,\n' +
              '    "expiresAt": 1774998315,\n' +
              '    "token": "wouldn\'t you like to know weather boy"\n' +
              '}' },
          BAD_REQUEST_EXCEPTION,
          UNAUTHORIZED_EXCEPTION,
          INVALID_CREDENTIALS_EXCEPTION,
          NOT_FOUND_EXCEPTION,
          RATELIMIT_EXCEPTION,
          INTERNAL_SERVER_EXCEPTION
        ],
        rateLimit: 10,
        authenticated: true
      },
      {
        id: 'auth-delete-account',
        path: '/:id',
        title: 'Delete Account',
        method: 'DELETE',
        description: 'Deletes an account, no second chances (sorry little timmy)',
        responses: [
          { code: 200, title: 'Success', description: 'Successful logout', exampleJson: '{\n' +
              '    "data": null,\n' +
              '    "message": "Account deleted successfully",\n' +
              '    "status": 200,\n' +
              '    "timestamp": "2026-03-31T22:42:19.252440200Z"\n' +
              '}' },
          BAD_REQUEST_EXCEPTION,
          UNAUTHORIZED_EXCEPTION,
          FORBIDDEN_EXCEPTION,
          NOT_FOUND_EXCEPTION,
          RATELIMIT_EXCEPTION,
          INTERNAL_SERVER_EXCEPTION
        ],
        rateLimit: 10,
        authenticated: true
      }
    ]
  },
  // Channels
  {
    name: 'Channels',
    basePath: 'http://localhost:8080/api/v1/channels',
    description: 'Channels Endpoint',
    endpoints : [
      {
        id: 'get-channel-data',
        path: '/get/:id',
        title: 'Get Channel Data',
        method: 'GET',
        description: 'Get the data for a specific channel',
        responses: [
          { code: 200, title: 'Success', description: 'All is good', exampleJson: '{"token": "xyz"}' },
          RATELIMIT_EXCEPTION
        ],
        rateLimit: 20,
      },
      {
        id: 'channel-create',
        path: '/create',
        title: 'Create Channel',
        method: 'POST',
        description: 'Create a new channel',
        bodyParams: [
          { name: 'channelType', type: 'string', description: 'ChannelType enum', exampleValue: 'DM', required: true},
          { name: 'members', type: 'object', description: 'List of members ids', exampleValue: '[123, 321]', required: false}
        ],
        responses: [
          { code: 200, title: 'Success', description: 'All is good', exampleJson: '{"token": "xyz"}' },
        ],
        rateLimit: 20,
      },
      {
        id: 'channel-delete',
        path: "/:id",
        title: 'Delete Channel',
        method: 'DELETE',
        description: 'Delete a channel',
        responses: [
          { code: 200, title: 'Success', description: 'All is good', exampleJson: '{"token": "xyz"}' },
          RATELIMIT_EXCEPTION
        ],
        rateLimit: 20,
      },
      {
        id: 'channel-update',
        path: "/:id",
        title: 'Update Channel',
        method: 'PATCH',
        description: 'Update a channel',
        responses: [
          { code: 200, title: 'Success', description: 'All is good', exampleJson: '{"token": "xyz"}' },
          RATELIMIT_EXCEPTION
        ],
        rateLimit: 20,
      }
    ]
  },
  // Users
  {
    name: 'Users',
    basePath: 'http://localhost:8080/api/v1/users',
    description: 'Users Endpoint',
    endpoints : [
      {
        id: 'get-presence',
        path: '/:id/presence',
        title: 'Get Presence',
        method: 'GET',
        description: 'Get a users presence',
        responses: [
          { code: 200, title: 'Success', description: 'All is good', exampleJson: '{"token": "xyz"}' },
          RATELIMIT_EXCEPTION
        ],
        rateLimit: 20,
      },
      {
        id: 'update-presence',
        path: '/:id/presence',
        title: 'Update Presence',
        method: 'PATCH',
        description: 'Update the presence for a specific user',
        responses: [
          { code: 200, title: 'Success', description: 'All is good', exampleJson: '{"token": "xyz"}' },
          RATELIMIT_EXCEPTION
        ],
        rateLimit: 20,
      },
    ]
  }
];
