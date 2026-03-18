import { RxStompConfig } from '@stomp/rx-stomp';

export const customRxStompConfig: RxStompConfig = {
  brokerURL: 'ws://localhost:8080/ws', // Matches your Spring boot endpoint
  heartbeatIncoming: 0,
  heartbeatOutgoing: 20000,
  reconnectDelay: 5000,
  debug: (msg: string): void => {
    console.log(new Date().toLocaleTimeString(), '- STOMP:', msg);
  },
};
