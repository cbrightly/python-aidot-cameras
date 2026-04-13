package com.didichuxing.doraemonkit.kit.network.utils;

import androidx.annotation.RestrictTo;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.nio.charset.Charset;
import kotlin.jvm.internal.k;
import okhttp3.d0;
import okhttp3.e0;
import okhttp3.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: OkHttpResponse.kt */
public final class OkHttpResponseKt {
    @Nullable
    public static final String encoding(@NotNull d0 $this$encoding) {
        k.f($this$encoding, "$this$encoding");
        String n = $this$encoding.n("content-encoding");
        return n != null ? n : $this$encoding.n(HttpHeaders.HEAD_KEY_CONTENT_ENCODING);
    }

    @NotNull
    public static final Charset charset(@NotNull d0 $this$charset) {
        x contentType;
        Charset c;
        k.f($this$charset, "$this$charset");
        String it = encoding($this$charset);
        if (it != null) {
            if (!Charset.isSupported(it)) {
                it = null;
            }
            if (it != null) {
                Charset forName = Charset.forName(it);
                k.b(forName, "Charset.forName(it)");
                return forName;
            }
        }
        e0 a = $this$charset.a();
        if (a != null && (contentType = a.contentType()) != null && (c = contentType.c()) != null) {
            return c;
        }
        Charset defaultCharset = Charset.defaultCharset();
        k.b(defaultCharset, "Charset.defaultCharset()");
        return defaultCharset;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x005a, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005b, code lost:
        kotlin.io.b.a(r5, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x005e, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0068, code lost:
        r0 = (r0 = r3.clone()).x0(charset(r12));
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String bodyContent(@org.jetbrains.annotations.NotNull okhttp3.d0 r12) {
        /*
            java.lang.String r0 = "$this$bodyContent"
            kotlin.jvm.internal.k.f(r12, r0)
            okhttp3.e0 r0 = r12.a()
            if (r0 == 0) goto L_0x0073
            r1 = 0
            okio.e r2 = r0.source()
            r3 = r2
            r4 = 0
            r5 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r3.request(r5)
            java.lang.String r3 = "source"
            kotlin.jvm.internal.k.b(r2, r3)
            okio.c r3 = r2.getBuffer()
            java.lang.String r4 = encoding(r12)
            r5 = 1
            java.lang.String r6 = "gzip"
            boolean r5 = kotlin.text.w.y(r6, r4, r5)
            if (r5 == 0) goto L_0x005f
            okio.m r5 = new okio.m
            okio.c r6 = r3.clone()
            r5.<init>(r6)
            r6 = 0
            r7 = r5
            r8 = 0
            okio.c r9 = new okio.c     // Catch:{ all -> 0x0058 }
            r9.<init>()     // Catch:{ all -> 0x0058 }
            r10 = r9
            r11 = 0
            r10.writeAll(r7)     // Catch:{ all -> 0x0058 }
            r3 = r9
            kotlin.x r7 = kotlin.x.a     // Catch:{ all -> 0x0058 }
            kotlin.io.b.a(r5, r6)
            goto L_0x005f
        L_0x0058:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x005a }
        L_0x005a:
            r7 = move-exception
            kotlin.io.b.a(r5, r6)
            throw r7
        L_0x005f:
            if (r3 == 0) goto L_0x0073
            okio.c r0 = r3.clone()
            if (r0 == 0) goto L_0x0073
            java.nio.charset.Charset r1 = charset(r12)
            java.lang.String r0 = r0.x0(r1)
            if (r0 == 0) goto L_0x0073
            goto L_0x0075
        L_0x0073:
            java.lang.String r0 = ""
        L_0x0075:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.network.utils.OkHttpResponseKt.bodyContent(okhttp3.d0):java.lang.String");
    }
}
