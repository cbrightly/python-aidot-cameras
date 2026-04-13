package io.netty.buffer;

import java.nio.ByteOrder;

public final class UnreleasableByteBuf extends WrappedByteBuf {
    private SwappedByteBuf swappedBuf;

    UnreleasableByteBuf(ByteBuf buf) {
        super(buf);
    }

    public ByteBuf order(ByteOrder endianness) {
        if (endianness == null) {
            throw new NullPointerException("endianness");
        } else if (endianness == order()) {
            return this;
        } else {
            SwappedByteBuf swappedBuf2 = this.swappedBuf;
            if (swappedBuf2 != null) {
                return swappedBuf2;
            }
            SwappedByteBuf swappedByteBuf = new SwappedByteBuf(this);
            SwappedByteBuf swappedBuf3 = swappedByteBuf;
            this.swappedBuf = swappedByteBuf;
            return swappedBuf3;
        }
    }

    public ByteBuf readSlice(int length) {
        return new UnreleasableByteBuf(this.buf.readSlice(length));
    }

    public ByteBuf slice() {
        return new UnreleasableByteBuf(this.buf.slice());
    }

    public ByteBuf slice(int index, int length) {
        return new UnreleasableByteBuf(this.buf.slice(index, length));
    }

    public ByteBuf duplicate() {
        return new UnreleasableByteBuf(this.buf.duplicate());
    }

    public ByteBuf retain(int increment) {
        return this;
    }

    public ByteBuf retain() {
        return this;
    }

    public boolean release() {
        return false;
    }

    public boolean release(int decrement) {
        return false;
    }
}
