package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

public final class UnsafeHeapSwappedByteBuf extends AbstractUnsafeSwappedByteBuf {
    UnsafeHeapSwappedByteBuf(AbstractByteBuf buf) {
        super(buf);
    }

    private static int idx(ByteBuf wrapped, int index) {
        return wrapped.arrayOffset() + index;
    }

    /* access modifiers changed from: protected */
    public long _getLong(AbstractByteBuf wrapped, int index) {
        return PlatformDependent.getLong(wrapped.array(), idx(wrapped, index));
    }

    /* access modifiers changed from: protected */
    public int _getInt(AbstractByteBuf wrapped, int index) {
        return PlatformDependent.getInt(wrapped.array(), idx(wrapped, index));
    }

    /* access modifiers changed from: protected */
    public short _getShort(AbstractByteBuf wrapped, int index) {
        return PlatformDependent.getShort(wrapped.array(), idx(wrapped, index));
    }

    /* access modifiers changed from: protected */
    public void _setShort(AbstractByteBuf wrapped, int index, short value) {
        PlatformDependent.putShort(wrapped.array(), idx(wrapped, index), value);
    }

    /* access modifiers changed from: protected */
    public void _setInt(AbstractByteBuf wrapped, int index, int value) {
        PlatformDependent.putInt(wrapped.array(), idx(wrapped, index), value);
    }

    /* access modifiers changed from: protected */
    public void _setLong(AbstractByteBuf wrapped, int index, long value) {
        PlatformDependent.putLong(wrapped.array(), idx(wrapped, index), value);
    }
}
