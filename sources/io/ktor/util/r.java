package io.ktor.util;

import io.ktor.utils.io.core.w;
import io.ktor.utils.io.core.x;
import java.io.InputStream;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: InputJvm.kt */
public final class r {

    /* compiled from: InputJvm.kt */
    public static final class a extends InputStream {
        final /* synthetic */ w c;

        a(w $receiver) {
            this.c = $receiver;
        }

        public int read() {
            if (this.c.w0()) {
                return -1;
            }
            return this.c.readByte();
        }

        public int read(@NotNull byte[] buffer, int offset, int length) {
            k.f(buffer, "buffer");
            if (this.c.w0()) {
                return -1;
            }
            return x.a(this.c, buffer, offset, length);
        }

        public long skip(long count) {
            return this.c.C0(count);
        }

        public void close() {
            this.c.close();
        }
    }

    @NotNull
    public static final InputStream a(@NotNull w $this$asStream) {
        k.f($this$asStream, "$this$asStream");
        return new a($this$asStream);
    }
}
