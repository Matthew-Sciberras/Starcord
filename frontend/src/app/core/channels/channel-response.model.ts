export interface ChannelResponse {
    channelID: Number;
    channelType: string;
    createdAt: string;
    creatorID: Number;
    image: String | null;
    members: number[];
    name: String;
}