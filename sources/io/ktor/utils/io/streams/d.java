package io.ktor.utils.io.streams;

import io.ktor.utils.io.core.q;
import java.io.InputStream;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Streams.kt */
public final class d {
    private static final char[] a = new char[8192];

    /* compiled from: Streams.kt */
    public static final class a extends InputStream {
        final /* synthetic */ q c;

        a(q $receiver) {
            this.c = $receiver;
        }

        public int read() {
            if (this.c.w0()) {
                return -1;
            }
            return this.c.readByte() & 255;
        }

        public int available() {
            return (int) Math.min(this.c.P0(), (long) Integer.MAX_VALUE);
        }

        public void close() {
            this.c.release();
        }
    }

    @NotNull
    public static final InputStream a(@NotNull q $this$inputStream) {
        k.f($this$inputStream, "$this$inputStream");
        return new a($this$inputStream);
    }
}
