package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

public final class UnsafeDirectSwappedByteBuf extends AbstractUnsafeSwappedByteBuf {
    UnsafeDirectSwappedByteBuf(AbstractByteBuf buf) {
        super(buf);
    }

    private static long addr(AbstractByteBuf wrapped, int index) {
        return wrapped.memoryAddress() + ((long) index);
    }

    /* access modifiers changed from: protected */
    public long _getLong(AbstractByteBuf wrapped, int index) {
        return PlatformDependent.getLong(addr(wrapped, index));
    }

    /* access modifiers changed from: protected */
    public int _getInt(AbstractByteBuf wrapped, int index) {
        return PlatformDependent.getInt(addr(wrapped, index));
    }

    /* access modifiers changed from: protected */
    public short _getShort(AbstractByteBuf wrapped, int index) {
        return PlatformDependent.getShort(addr(wrapped, index));
    }

    /* access modifiers changed from: protected */
    public void _setShort(AbstractByteBuf wrapped, int index, short value) {
        PlatformDependent.putShort(addr(wrapped, index), value);
    }

    /* access modifiers changed from: protected */
    public void _setInt(AbstractByteBuf wrapped, int index, int value) {
        PlatformDependent.putInt(addr(wrapped, index), value);
    }

    /* access modifiers changed from: protected */
    public void _setLong(AbstractByteBuf wrapped, int index, long value) {
        PlatformDependent.putLong(addr(wrapped, index), value);
    }
}
