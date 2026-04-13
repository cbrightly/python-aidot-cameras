package io.ktor.utils.io.core;

import io.ktor.utils.io.bits.d;
import io.ktor.utils.io.core.internal.e;
import java.io.EOFException;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.z;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: IoBufferJVM.kt */
public final class b0 {

    /* compiled from: Require.kt */
    public static final class a extends e {
        final /* synthetic */ int a;
        final /* synthetic */ String b;
        final /* synthetic */ z c;
        final /* synthetic */ ByteBuffer d;
        final /* synthetic */ int e;

        public a(int i, String str, z zVar, ByteBuffer byteBuffer, int i2) {
            this.a = i;
            this.b = str;
            this.c = zVar;
            this.d = byteBuffer;
            this.e = i2;
        }

        @NotNull
        public Void a() {
            throw new EOFException("Not enough bytes to read a " + this.b + " of size " + this.a + '.');
        }
    }

    /* JADX INFO: finally extract failed */
    public static final void a(@NotNull c $this$readFully, @NotNull ByteBuffer dst, int length) {
        ByteBuffer byteBuffer = dst;
        int i = length;
        k.f($this$readFully, "$this$readFully");
        k.f(byteBuffer, "dst");
        z value$iv = new z();
        c $this$read$iv$iv = $this$readFully;
        ByteBuffer memory$iv = $this$read$iv$iv.n();
        int start$iv = $this$read$iv$iv.o();
        if ($this$read$iv$iv.s() - start$iv >= i) {
            int offset = start$iv;
            ByteBuffer memory = memory$iv;
            int limit = dst.limit();
            try {
                byteBuffer.limit(dst.position() + i);
                d.b(memory, byteBuffer, offset);
                byteBuffer.limit(limit);
                value$iv.element = x.a;
                $this$read$iv$iv.g(length);
            } catch (Throwable th) {
                byteBuffer.limit(limit);
                throw th;
            }
        } else {
            new a(length, "buffer content", value$iv, dst, length).a();
            throw null;
        }
    }
}
