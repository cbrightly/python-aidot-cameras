package io.ktor.util;

import io.ktor.utils.io.core.a0;
import io.ktor.utils.io.core.c;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BufferViewJvm.kt */
public final class e {

    /* compiled from: Require.kt */
    public static final class a extends io.ktor.utils.io.core.internal.e {
        final /* synthetic */ int a;
        final /* synthetic */ int b;

        public a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        @NotNull
        public Void a() {
            throw new IllegalArgumentException("size " + this.a + " is greater than buffer's remaining capacity " + this.b);
        }
    }

    public static final int a(@NotNull ReadableByteChannel $this$read, @NotNull a0 buffer) {
        k.f($this$read, "$this$read");
        k.f(buffer, "buffer");
        c this_$iv = buffer;
        int m = this_$iv.m() - this_$iv.s();
        boolean condition$iv$iv = false;
        if (m == 0) {
            return 0;
        }
        c this_$iv2 = buffer;
        c this_$iv$iv = this_$iv2;
        int rem$iv = this_$iv$iv.m() - this_$iv$iv.s();
        if (1 <= rem$iv) {
            condition$iv$iv = true;
        }
        if (condition$iv$iv) {
            ByteBuffer buffer$iv = this_$iv2.n().duplicate();
            if (buffer$iv == null) {
                k.n();
            }
            int writePosition$iv = this_$iv2.s();
            buffer$iv.limit(this_$iv2.m());
            buffer$iv.position(writePosition$iv);
            int count = $this$read.read(buffer$iv);
            int delta$iv = buffer$iv.position() - writePosition$iv;
            if (delta$iv < 0 || delta$iv > rem$iv) {
                io.ktor.utils.io.internal.jvm.a.a(delta$iv, 1);
                throw null;
            }
            this_$iv2.a(delta$iv);
            return count;
        }
        new a(1, rem$iv).a();
        throw null;
    }
}
