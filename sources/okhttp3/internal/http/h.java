package okhttp3.internal.http;

import kotlin.jvm.internal.k;
import okhttp3.e0;
import okhttp3.x;
import okio.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealResponseBody.kt */
public final class h extends e0 {
    private final String c;
    private final long d;
    private final e f;

    public h(@Nullable String contentTypeString, long contentLength, @NotNull e source) {
        k.f(source, "source");
        this.c = contentTypeString;
        this.d = contentLength;
        this.f = source;
    }

    public long contentLength() {
        return this.d;
    }

    @Nullable
    public x contentType() {
        String str = this.c;
        if (str != null) {
            return x.c.b(str);
        }
        return null;
    }

    @NotNull
    public e source() {
        return this.f;
    }
}
