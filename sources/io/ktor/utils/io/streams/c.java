package io.ktor.utils.io.streams;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.utils.io.core.a;
import io.ktor.utils.io.pool.d;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: Input.kt */
public final class c extends a {
    private final InputStream a1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(@NotNull InputStream stream, @NotNull d<io.ktor.utils.io.core.internal.a> pool) {
        super((io.ktor.utils.io.core.internal.a) null, 0, pool, 3, (DefaultConstructorMarker) null);
        k.f(stream, "stream");
        k.f(pool, "pool");
        this.a1 = stream;
    }

    /* access modifiers changed from: protected */
    public int E(@NotNull ByteBuffer destination, int offset, int length) {
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        if (destination.hasArray() && !destination.isReadOnly()) {
            return n.b(this.a1.read(destination.array(), destination.arrayOffset() + offset, length), 0);
        }
        byte[] buffer = a.a().p0();
        try {
            int rc = this.a1.read(buffer, 0, Math.min(buffer.length, length));
            if (rc == -1) {
                return 0;
            }
            ByteBuffer $this$storeByteArray$iv = destination;
            ByteBuffer order = ByteBuffer.wrap(buffer, 0, rc).slice().order(ByteOrder.BIG_ENDIAN);
            k.b(order, "ByteBuffer.wrap(this, of…der(ByteOrder.BIG_ENDIAN)");
            io.ktor.utils.io.bits.c.c(io.ktor.utils.io.bits.c.b(order), $this$storeByteArray$iv, 0, rc, offset);
            a.a().N0(buffer);
            return rc;
        } finally {
            a.a().N0(buffer);
        }
    }

    /* access modifiers changed from: protected */
    public void l() {
        this.a1.close();
    }
}
