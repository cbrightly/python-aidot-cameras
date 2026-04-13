package io.ktor.request;

import io.ktor.features.n;
import io.ktor.http.d;
import io.ktor.http.r;
import io.ktor.http.s;
import io.ktor.http.u;
import java.nio.charset.Charset;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ApplicationRequestProperties.kt */
public final class e {
    @Nullable
    public static final String f(@NotNull d $this$header, @NotNull String name) {
        k.f($this$header, "$this$header");
        k.f(name, "name");
        return $this$header.getHeaders().get(name);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0011, code lost:
        r0 = io.ktor.http.c.e.b(r0);
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final io.ktor.http.c c(@org.jetbrains.annotations.NotNull io.ktor.request.d r3) {
        /*
            java.lang.String r0 = "$this$contentType"
            kotlin.jvm.internal.k.f(r3, r0)
            io.ktor.http.s r0 = io.ktor.http.s.V0
            java.lang.String r0 = r0.s()
            java.lang.String r0 = f(r3, r0)
            if (r0 == 0) goto L_0x001b
            r1 = 0
            io.ktor.http.c$b r2 = io.ktor.http.c.e
            io.ktor.http.c r0 = r2.b(r0)
            if (r0 == 0) goto L_0x001b
            goto L_0x0021
        L_0x001b:
            io.ktor.http.c$b r0 = io.ktor.http.c.e
            io.ktor.http.c r0 = r0.a()
        L_0x0021:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.request.e.c(io.ktor.request.d):io.ktor.http.c");
    }

    @Nullable
    public static final Charset b(@NotNull d $this$contentCharset) {
        k.f($this$contentCharset, "$this$contentCharset");
        return d.a(c($this$contentCharset));
    }

    @NotNull
    public static final String g(@NotNull d $this$path) {
        k.f($this$path, "$this$path");
        return x.Y0(n.a($this$path).getUri(), '?', (String) null, 2, (Object) null);
    }

    @NotNull
    public static final List<io.ktor.http.k> a(@NotNull d $this$acceptCharsetItems) {
        k.f($this$acceptCharsetItems, "$this$acceptCharsetItems");
        return r.b(f($this$acceptCharsetItems, s.V0.d()));
    }

    @NotNull
    public static final String e(@NotNull d $this$uri) {
        k.f($this$uri, "$this$uri");
        return n.a($this$uri).getUri();
    }

    @NotNull
    public static final u d(@NotNull d $this$httpMethod) {
        k.f($this$httpMethod, "$this$httpMethod");
        return n.a($this$httpMethod).getMethod();
    }
}
