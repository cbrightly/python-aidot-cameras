package io.ktor.utils.io.bits;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.utils.io.core.internal.d;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MemoryJvm.kt */
public final class c {
    /* access modifiers changed from: private */
    @NotNull
    public static final ByteBuffer a;
    public static final a b = new a((DefaultConstructorMarker) null);

    @NotNull
    public static ByteBuffer b(@NotNull ByteBuffer buffer) {
        k.f(buffer, "buffer");
        return buffer;
    }

    @NotNull
    public static final ByteBuffer e(ByteBuffer $this, int offset, int length) {
        return b(d.d($this, offset, length));
    }

    public static final void c(ByteBuffer $this, @NotNull ByteBuffer destination, int offset, int length, int destinationOffset) {
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        if (!$this.hasArray() || !destination.hasArray() || $this.isReadOnly() || destination.isReadOnly()) {
            ByteBuffer srcCopy = $this.duplicate();
            ByteBuffer $this$apply = srcCopy;
            $this$apply.position(offset);
            $this$apply.limit(offset + length);
            ByteBuffer dstCopy = destination.duplicate();
            dstCopy.position(destinationOffset);
            dstCopy.put(srcCopy);
            return;
        }
        System.arraycopy($this.array(), $this.arrayOffset() + offset, destination.array(), destination.arrayOffset() + destinationOffset, length);
    }

    public static final void d(ByteBuffer $this, @NotNull ByteBuffer destination, long offset, long length, long destinationOffset) {
        ByteBuffer byteBuffer = destination;
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        long $this$toIntOrFail$iv = offset;
        long j = (long) Integer.MAX_VALUE;
        if ($this$toIntOrFail$iv < j) {
            int i = (int) $this$toIntOrFail$iv;
            long $this$toIntOrFail$iv2 = length;
            if ($this$toIntOrFail$iv2 < j) {
                int i2 = (int) $this$toIntOrFail$iv2;
                long $this$toIntOrFail$iv3 = destinationOffset;
                if ($this$toIntOrFail$iv3 < j) {
                    ByteBuffer byteBuffer2 = $this;
                    c($this, destination, i, i2, (int) $this$toIntOrFail$iv3);
                    return;
                }
                ByteBuffer byteBuffer3 = $this;
                d.a($this$toIntOrFail$iv3, "destinationOffset");
                throw null;
            }
            ByteBuffer byteBuffer4 = $this;
            d.a($this$toIntOrFail$iv2, "length");
            throw null;
        }
        ByteBuffer byteBuffer5 = $this;
        d.a($this$toIntOrFail$iv, "offset");
        throw null;
    }

    /* compiled from: MemoryJvm.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final ByteBuffer a() {
            return c.a;
        }
    }

    static {
        ByteBuffer order = ByteBuffer.allocate(0).order(ByteOrder.BIG_ENDIAN);
        k.b(order, "ByteBuffer.allocate(0).order(ByteOrder.BIG_ENDIAN)");
        a = b(order);
    }
}
