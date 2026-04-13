package io.ktor.utils.io.bits;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MemoryJvm.kt */
public final class d {
    public static final void a(@NotNull ByteBuffer $this$copyTo, @NotNull byte[] destination, int offset, int length, int destinationOffset) {
        k.f($this$copyTo, "$this$copyTo");
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        if (!$this$copyTo.hasArray() || $this$copyTo.isReadOnly()) {
            $this$copyTo.duplicate().get(destination, destinationOffset, length);
        } else {
            System.arraycopy($this$copyTo.array(), $this$copyTo.arrayOffset() + offset, destination, destinationOffset, length);
        }
    }

    public static final void b(@NotNull ByteBuffer $this$copyTo, @NotNull ByteBuffer destination, int offset) {
        k.f($this$copyTo, "$this$copyTo");
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        int size = destination.remaining();
        if (!$this$copyTo.hasArray() || $this$copyTo.isReadOnly() || !destination.hasArray() || destination.isReadOnly()) {
            ByteBuffer source = $this$copyTo.duplicate();
            ByteBuffer $this$apply = source;
            $this$apply.limit(offset + size);
            $this$apply.position(offset);
            destination.put(source);
            return;
        }
        int dstPosition = destination.position();
        System.arraycopy($this$copyTo.array(), $this$copyTo.arrayOffset() + offset, destination.array(), destination.arrayOffset() + dstPosition, size);
        destination.position(dstPosition + size);
    }

    public static final void c(@NotNull ByteBuffer $this$copyTo, @NotNull ByteBuffer destination, int offset) {
        k.f($this$copyTo, "$this$copyTo");
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        if (!$this$copyTo.hasArray() || $this$copyTo.isReadOnly()) {
            d(destination, offset, $this$copyTo.remaining()).put($this$copyTo);
            return;
        }
        byte[] source$iv = $this$copyTo.array();
        k.b(source$iv, "array()");
        int sourceOffset$iv = $this$copyTo.arrayOffset() + $this$copyTo.position();
        int count$iv = $this$copyTo.remaining();
        ByteBuffer order = ByteBuffer.wrap(source$iv, sourceOffset$iv, count$iv).slice().order(ByteOrder.BIG_ENDIAN);
        k.b(order, "ByteBuffer.wrap(this, of…der(ByteOrder.BIG_ENDIAN)");
        c.c(c.b(order), destination, 0, count$iv, offset);
        $this$copyTo.position($this$copyTo.limit());
    }

    @NotNull
    public static final ByteBuffer d(@NotNull ByteBuffer $this$sliceSafe, int offset, int length) {
        k.f($this$sliceSafe, "$this$sliceSafe");
        ByteBuffer $this$mySlice$iv = $this$sliceSafe.duplicate();
        $this$mySlice$iv.position(offset);
        $this$mySlice$iv.limit(offset + length);
        return $this$mySlice$iv.slice();
    }
}
