package io.netty.buffer;

import io.netty.util.ResourceLeakTracker;
import java.nio.ByteOrder;

public class SimpleLeakAwareCompositeByteBuf extends WrappedCompositeByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    final ResourceLeakTracker<ByteBuf> leak;

    static {
        Class<SimpleLeakAwareCompositeByteBuf> cls = SimpleLeakAwareCompositeByteBuf.class;
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.Object, io.netty.util.ResourceLeakTracker<io.netty.buffer.ByteBuf>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    SimpleLeakAwareCompositeByteBuf(io.netty.buffer.CompositeByteBuf r2, io.netty.util.ResourceLeakTracker<io.netty.buffer.ByteBuf> r3) {
        /*
            r1 = this;
            r1.<init>(r2)
            java.lang.String r0 = "leak"
            java.lang.Object r0 = io.netty.util.internal.ObjectUtil.checkNotNull(r3, r0)
            io.netty.util.ResourceLeakTracker r0 = (io.netty.util.ResourceLeakTracker) r0
            r1.leak = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.SimpleLeakAwareCompositeByteBuf.<init>(io.netty.buffer.CompositeByteBuf, io.netty.util.ResourceLeakTracker):void");
    }

    public boolean release() {
        ByteBuf unwrapped = unwrap();
        if (!super.release()) {
            return false;
        }
        closeLeak(unwrapped);
        return true;
    }

    public boolean release(int decrement) {
        ByteBuf unwrapped = unwrap();
        if (!super.release(decrement)) {
            return false;
        }
        closeLeak(unwrapped);
        return true;
    }

    private void closeLeak(ByteBuf trackedByteBuf) {
        if (!this.leak.close(trackedByteBuf)) {
            throw new AssertionError();
        }
    }

    public ByteBuf order(ByteOrder endianness) {
        if (order() == endianness) {
            return this;
        }
        return newLeakAwareByteBuf(super.order(endianness));
    }

    public ByteBuf slice() {
        return newLeakAwareByteBuf(super.slice());
    }

    public ByteBuf slice(int index, int length) {
        return newLeakAwareByteBuf(super.slice(index, length));
    }

    public ByteBuf duplicate() {
        return newLeakAwareByteBuf(super.duplicate());
    }

    public ByteBuf readSlice(int length) {
        return newLeakAwareByteBuf(super.readSlice(length));
    }

    private SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf wrapped) {
        return newLeakAwareByteBuf(wrapped, unwrap(), this.leak);
    }

    /* access modifiers changed from: protected */
    public SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf wrapped, ByteBuf trackedByteBuf, ResourceLeakTracker<ByteBuf> leakTracker) {
        return new SimpleLeakAwareByteBuf(wrapped, trackedByteBuf, leakTracker);
    }
}
