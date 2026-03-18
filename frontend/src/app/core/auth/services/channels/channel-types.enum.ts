class ChannelType {
  private static readonly _values = new Map<string, ChannelType>();

  static readonly DM = new ChannelType("DM", 2);
  static readonly GROUP = new ChannelType("GROUP", 20);
  static readonly CHANNEL = new ChannelType("CHANNEL", Number.MAX_SAFE_INTEGER);

  private constructor(
    public readonly name: string,
    public readonly maxMembers: number
  ) {
    ChannelType._values.set(name, this);
  }

  static fromString(name: string): ChannelType | undefined {
    return ChannelType._values.get(name);
  }

  toString(): string {
    return this.name;
  }
}
