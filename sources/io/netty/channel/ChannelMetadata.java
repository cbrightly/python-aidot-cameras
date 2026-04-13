package io.netty.channel;

public final class ChannelMetadata {
    private final boolean hasDisconnect;

    public ChannelMetadata(boolean hasDisconnect2) {
        this.hasDisconnect = hasDisconnect2;
    }

    public boolean hasDisconnect() {
        return this.hasDisconnect;
    }
}
