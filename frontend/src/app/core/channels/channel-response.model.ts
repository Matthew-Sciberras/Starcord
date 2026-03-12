export interface ChannelResponse {
    channelID: number;
    channelType: string;
    createdAt: string;
    creatorID: number;
    image: string | null;
    members: number[];
    name: string;
}

export interface GetChannelResponse {
  channels: ChannelResponse[];
  timestamp: number;
}
