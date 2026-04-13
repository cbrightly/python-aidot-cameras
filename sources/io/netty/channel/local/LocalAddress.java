package io.netty.channel.local;

import io.netty.channel.Channel;
import java.net.SocketAddress;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public final class LocalAddress extends SocketAddress implements Comparable<LocalAddress> {
    public static final LocalAddress ANY = new LocalAddress("ANY");
    private static final long serialVersionUID = 4644331421130916435L;
    private final String id;
    private final String strVal;

    LocalAddress(Channel channel) {
        StringBuilder buf = new StringBuilder(16);
        buf.append("local:E");
        buf.append(Long.toHexString((((long) channel.hashCode()) & 4294967295L) | IjkMediaMeta.AV_CH_WIDE_RIGHT));
        buf.setCharAt(7, ':');
        this.id = buf.substring(6);
        this.strVal = buf.toString();
    }

    public LocalAddress(String id2) {
        if (id2 != null) {
            String id3 = id2.trim().toLowerCase();
            if (!id3.isEmpty()) {
                this.id = id3;
                this.strVal = "local:" + id3;
                return;
            }
            throw new IllegalArgumentException("empty id");
        }
        throw new NullPointerException("id");
    }

    public String id() {
        return this.id;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof LocalAddress)) {
            return false;
        }
        return this.id.equals(((LocalAddress) o).id);
    }

    public int compareTo(LocalAddress o) {
        return this.id.compareTo(o.id);
    }

    public String toString() {
        return this.strVal;
    }
}
