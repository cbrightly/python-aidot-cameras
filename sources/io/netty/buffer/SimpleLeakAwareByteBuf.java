package io.netty.buffer;

import io.netty.util.ResourceLeakTracker;
import java.nio.ByteOrder;

public class SimpleLeakAwareByteBuf extends WrappedByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    final ResourceLeakTracker<ByteBuf> leak;
    private final ByteBuf trackedByteBuf;

    static {
        Class<SimpleLeakAwareByteBuf> cls = SimpleLeakAwareByteBuf.class;
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.Object, io.netty.util.ResourceLeakTracker<io.netty.buffer.ByteBuf>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    SimpleLeakAwareByteBuf(io.netty.buffer.ByteBuf r2, io.netty.buffer.ByteBuf r3, io.netty.util.ResourceLeakTracker<io.netty.buffer.ByteBuf> r4) {
        /*
            r1 = this;
            r1.<init>(r2)
            java.lang.String r0 = "trackedByteBuf"
            java.lang.Object r0 = io.netty.util.internal.ObjectUtil.checkNotNull(r3, r0)
            io.netty.buffer.ByteBuf r0 = (io.netty.buffer.ByteBuf) r0
            r1.trackedByteBuf = r0
            java.lang.String r0 = "leak"
            java.lang.Object r0 = io.netty.util.internal.ObjectUtil.checkNotNull(r4, r0)
            io.netty.util.ResourceLeakTracker r0 = (io.netty.util.ResourceLeakTracker) r0
            r1.leak = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.SimpleLeakAwareByteBuf.<init>(io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ResourceLeakTracker):void");
    }

    SimpleLeakAwareByteBuf(ByteBuf wrapped, ResourceLeakTracker<ByteBuf> leak2) {
        this(wrapped, wrapped, leak2);
    }

    public ByteBuf slice() {
        return newSharedLeakAwareByteBuf(super.slice());
    }

    public ByteBuf slice(int index, int length) {
        return newSharedLeakAwareByteBuf(super.slice(index, length));
    }

    public ByteBuf duplicate() {
        return newSharedLeakAwareByteBuf(super.duplicate());
    }

    public ByteBuf readSlice(int length) {
        return newSharedLeakAwareByteBuf(super.readSlice(length));
    }

    public boolean release() {
        if (!super.release()) {
            return false;
        }
        closeLeak();
        return true;
    }

    public boolean release(int decrement) {
        if (!super.release(decrement)) {
            return false;
        }
        closeLeak();
        return true;
    }

    private void closeLeak() {
        if (!this.leak.close(this.trackedByteBuf)) {
            throw new AssertionError();
        }
    }

    public ByteBuf order(ByteOrder endianness) {
        if (order() == endianness) {
            return this;
        }
        return newSharedLeakAwareByteBuf(super.order(endianness));
    }

    private SimpleLeakAwareByteBuf newSharedLeakAwareByteBuf(ByteBuf wrapped) {
        return newLeakAwareByteBuf(wrapped, this.trackedByteBuf, this.leak);
    }

    /* access modifiers changed from: protected */
    public SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf buf, ByteBuf trackedByteBuf2, ResourceLeakTracker<ByteBuf> leakTracker) {
        return new SimpleLeakAwareByteBuf(buf, trackedByteBuf2, leakTracker);
    }
}
