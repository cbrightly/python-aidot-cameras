package io.netty.buffer;

import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.StringUtil;

public class DefaultByteBufHolder implements ByteBufHolder {
    private final ByteBuf data;

    public DefaultByteBufHolder(ByteBuf data2) {
        if (data2 != null) {
            this.data = data2;
            return;
        }
        throw new NullPointerException("data");
    }

    public ByteBuf content() {
        if (this.data.refCnt() > 0) {
            return this.data;
        }
        throw new IllegalReferenceCountException(this.data.refCnt());
    }

    public ByteBufHolder copy() {
        return new DefaultByteBufHolder(this.data.copy());
    }

    public ByteBufHolder duplicate() {
        return new DefaultByteBufHolder(this.data.duplicate());
    }

    public int refCnt() {
        return this.data.refCnt();
    }

    public ByteBufHolder retain() {
        this.data.retain();
        return this;
    }

    public ByteBufHolder retain(int increment) {
        this.data.retain(increment);
        return this;
    }

    public boolean release() {
        return this.data.release();
    }

    public boolean release(int decrement) {
        return this.data.release(decrement);
    }

    /* access modifiers changed from: protected */
    public final String contentToString() {
        return this.data.toString();
    }

    public String toString() {
        return StringUtil.simpleClassName((Object) this) + '(' + contentToString() + ')';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ByteBufHolder) {
            return this.data.equals(((ByteBufHolder) o).content());
        }
        return false;
    }

    public int hashCode() {
        return this.data.hashCode();
    }
}
