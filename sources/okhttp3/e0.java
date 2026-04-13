package okhttp3;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.c;
import okhttp3.x;
import okio.e;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ResponseBody.kt */
public abstract class e0 implements Closeable {
    public static final b Companion = new b((DefaultConstructorMarker) null);
    private Reader reader;

    @NotNull
    public static final e0 create(@NotNull String str, @Nullable x xVar) {
        return Companion.a(str, xVar);
    }

    @NotNull
    public static final e0 create(@Nullable x xVar, long j, @NotNull e eVar) {
        return Companion.b(xVar, j, eVar);
    }

    @NotNull
    public static final e0 create(@Nullable x xVar, @NotNull String str) {
        return Companion.c(xVar, str);
    }

    @NotNull
    public static final e0 create(@Nullable x xVar, @NotNull f fVar) {
        return Companion.d(xVar, fVar);
    }

    @NotNull
    public static final e0 create(@Nullable x xVar, @NotNull byte[] bArr) {
        return Companion.e(xVar, bArr);
    }

    @NotNull
    public static final e0 create(@NotNull e eVar, @Nullable x xVar, long j) {
        return Companion.f(eVar, xVar, j);
    }

    @NotNull
    public static final e0 create(@NotNull f fVar, @Nullable x xVar) {
        return Companion.g(fVar, xVar);
    }

    @NotNull
    public static final e0 create(@NotNull byte[] bArr, @Nullable x xVar) {
        return Companion.h(bArr, xVar);
    }

    public abstract long contentLength();

    @Nullable
    public abstract x contentType();

    @NotNull
    public abstract e source();

    @NotNull
    public final InputStream byteStream() {
        return source().Y0();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0054, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0055, code lost:
        kotlin.io.b.a(r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0058, code lost:
        throw r6;
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final byte[] bytes() {
        /*
            r9 = this;
            r0 = r9
            r1 = 0
            long r2 = r0.contentLength()
            r4 = 2147483647(0x7fffffff, float:NaN)
            long r4 = (long) r4
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 > 0) goto L_0x0059
            okio.e r4 = r0.source()
            r5 = 0
            r6 = r4
            r7 = 0
            byte[] r8 = r6.q0()     // Catch:{ all -> 0x0052 }
            kotlin.io.b.a(r4, r5)
            r4 = r8
            r5 = r4
            r6 = 0
            int r5 = r5.length
            r6 = -1
            int r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r6 == 0) goto L_0x0050
            long r6 = (long) r5
            int r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r6 != 0) goto L_0x002c
            goto L_0x0050
        L_0x002c:
            java.io.IOException r6 = new java.io.IOException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Content-Length ("
            r7.append(r8)
            r7.append(r2)
            java.lang.String r8 = ") and stream length ("
            r7.append(r8)
            r7.append(r5)
            java.lang.String r8 = ") disagree"
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L_0x0050:
            return r4
        L_0x0052:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x0054 }
        L_0x0054:
            r6 = move-exception
            kotlin.io.b.a(r4, r5)
            throw r6
        L_0x0059:
            java.io.IOException r4 = new java.io.IOException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Cannot buffer entire body for content length: "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.e0.bytes():byte[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0057, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0058, code lost:
        kotlin.io.b.a(r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005b, code lost:
        throw r6;
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final okio.f byteString() {
        /*
            r9 = this;
            r0 = r9
            r1 = 0
            long r2 = r0.contentLength()
            r4 = 2147483647(0x7fffffff, float:NaN)
            long r4 = (long) r4
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 > 0) goto L_0x005c
            okio.e r4 = r0.source()
            r5 = 0
            r6 = r4
            r7 = 0
            okio.f r8 = r6.D0()     // Catch:{ all -> 0x0055 }
            kotlin.io.b.a(r4, r5)
            r4 = r8
            r5 = r4
            r6 = 0
            int r5 = r5.size()
            r6 = -1
            int r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r6 == 0) goto L_0x0053
            long r6 = (long) r5
            int r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r6 != 0) goto L_0x002f
            goto L_0x0053
        L_0x002f:
            java.io.IOException r6 = new java.io.IOException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Content-Length ("
            r7.append(r8)
            r7.append(r2)
            java.lang.String r8 = ") and stream length ("
            r7.append(r8)
            r7.append(r5)
            java.lang.String r8 = ") disagree"
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L_0x0053:
            return r4
        L_0x0055:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x0057 }
        L_0x0057:
            r6 = move-exception
            kotlin.io.b.a(r4, r5)
            throw r6
        L_0x005c:
            java.io.IOException r4 = new java.io.IOException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Cannot buffer entire body for content length: "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.e0.byteString():okio.f");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005e, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005f, code lost:
        kotlin.jvm.internal.j.b(1);
        kotlin.io.b.a(r3, r4);
        kotlin.jvm.internal.j.a(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0068, code lost:
        throw r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final <T> T consumeSource(kotlin.jvm.functions.l<? super okio.e, ? extends T> r9, kotlin.jvm.functions.l<? super T, java.lang.Integer> r10) {
        /*
            r8 = this;
            r0 = 0
            long r1 = r8.contentLength()
            r3 = 2147483647(0x7fffffff, float:NaN)
            long r3 = (long) r3
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 > 0) goto L_0x0069
            okio.e r3 = r8.source()
            r4 = 0
            r5 = 1
            java.lang.Object r6 = r9.invoke(r3)     // Catch:{ all -> 0x005c }
            kotlin.jvm.internal.j.b(r5)
            kotlin.io.b.a(r3, r4)
            kotlin.jvm.internal.j.a(r5)
            r3 = r6
            java.lang.Object r4 = r10.invoke(r3)
            java.lang.Number r4 = (java.lang.Number) r4
            int r4 = r4.intValue()
            r5 = -1
            int r5 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r5 == 0) goto L_0x005b
            long r5 = (long) r4
            int r5 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r5 != 0) goto L_0x0037
            goto L_0x005b
        L_0x0037:
            java.io.IOException r5 = new java.io.IOException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Content-Length ("
            r6.append(r7)
            r6.append(r1)
            java.lang.String r7 = ") and stream length ("
            r6.append(r7)
            r6.append(r4)
            java.lang.String r7 = ") disagree"
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x005b:
            return r3
        L_0x005c:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x005e }
        L_0x005e:
            r6 = move-exception
            kotlin.jvm.internal.j.b(r5)
            kotlin.io.b.a(r3, r4)
            kotlin.jvm.internal.j.a(r5)
            throw r6
        L_0x0069:
            java.io.IOException r3 = new java.io.IOException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Cannot buffer entire body for content length: "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.e0.consumeSource(kotlin.jvm.functions.l, kotlin.jvm.functions.l):java.lang.Object");
    }

    @NotNull
    public final Reader charStream() {
        Reader reader2 = this.reader;
        if (reader2 != null) {
            return reader2;
        }
        a it = new a(source(), charset());
        this.reader = it;
        return it;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0019, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        kotlin.io.b.a(r0, r1);
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String string() {
        /*
            r4 = this;
            okio.e r0 = r4.source()
            r1 = r0
            r2 = 0
            java.nio.charset.Charset r3 = r4.charset()     // Catch:{ all -> 0x0017 }
            java.nio.charset.Charset r3 = okhttp3.internal.b.F(r1, r3)     // Catch:{ all -> 0x0017 }
            java.lang.String r3 = r1.x0(r3)     // Catch:{ all -> 0x0017 }
            r1 = 0
            kotlin.io.b.a(r0, r1)
            return r3
        L_0x0017:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0019 }
        L_0x0019:
            r2 = move-exception
            kotlin.io.b.a(r0, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.e0.string():java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r0.d(kotlin.text.c.a);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.nio.charset.Charset charset() {
        /*
            r2 = this;
            okhttp3.x r0 = r2.contentType()
            if (r0 == 0) goto L_0x000f
            java.nio.charset.Charset r1 = kotlin.text.c.a
            java.nio.charset.Charset r0 = r0.d(r1)
            if (r0 == 0) goto L_0x000f
            goto L_0x0011
        L_0x000f:
            java.nio.charset.Charset r0 = kotlin.text.c.a
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.e0.charset():java.nio.charset.Charset");
    }

    public void close() {
        okhttp3.internal.b.j(source());
    }

    /* compiled from: ResponseBody.kt */
    public static final class a extends Reader {
        private boolean c;
        private Reader d;
        private final e f;
        private final Charset q;

        public a(@NotNull e source, @NotNull Charset charset) {
            k.f(source, "source");
            k.f(charset, "charset");
            this.f = source;
            this.q = charset;
        }

        public int read(@NotNull char[] cbuf, int off, int len) {
            k.f(cbuf, "cbuf");
            if (!this.c) {
                Reader finalDelegate = this.d;
                if (finalDelegate == null) {
                    finalDelegate = new InputStreamReader(this.f.Y0(), okhttp3.internal.b.F(this.f, this.q));
                    this.d = finalDelegate;
                }
                return finalDelegate.read(cbuf, off, len);
            }
            throw new IOException("Stream closed");
        }

        public void close() {
            this.c = true;
            Reader reader = this.d;
            if (reader != null) {
                reader.close();
            } else {
                this.f.close();
            }
        }
    }

    /* compiled from: ResponseBody.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final e0 a(@NotNull String $this$toResponseBody, @Nullable x contentType) {
            k.f($this$toResponseBody, "$this$toResponseBody");
            Charset charset = c.a;
            x finalContentType = contentType;
            if (contentType != null) {
                Charset resolvedCharset = x.e(contentType, (Charset) null, 1, (Object) null);
                if (resolvedCharset == null) {
                    charset = c.a;
                    x.a aVar = x.c;
                    finalContentType = aVar.b(contentType + "; charset=utf-8");
                } else {
                    charset = resolvedCharset;
                }
            }
            okio.c buffer = new okio.c().writeString($this$toResponseBody, charset);
            return f(buffer, finalContentType, buffer.e1());
        }

        public static /* synthetic */ e0 i(b bVar, byte[] bArr, x xVar, int i, Object obj) {
            if ((i & 1) != 0) {
                xVar = null;
            }
            return bVar.h(bArr, xVar);
        }

        @NotNull
        public final e0 h(@NotNull byte[] $this$toResponseBody, @Nullable x contentType) {
            k.f($this$toResponseBody, "$this$toResponseBody");
            return f(new okio.c().write($this$toResponseBody), contentType, (long) $this$toResponseBody.length);
        }

        @NotNull
        public final e0 g(@NotNull f $this$toResponseBody, @Nullable x contentType) {
            k.f($this$toResponseBody, "$this$toResponseBody");
            return f(new okio.c().write($this$toResponseBody), contentType, (long) $this$toResponseBody.size());
        }

        /* compiled from: ResponseBody.kt */
        public static final class a extends e0 {
            final /* synthetic */ e c;
            final /* synthetic */ x d;
            final /* synthetic */ long f;

            a(e $receiver, x $captured_local_variable$1, long $captured_local_variable$2) {
                this.c = $receiver;
                this.d = $captured_local_variable$1;
                this.f = $captured_local_variable$2;
            }

            @Nullable
            public x contentType() {
                return this.d;
            }

            public long contentLength() {
                return this.f;
            }

            @NotNull
            public e source() {
                return this.c;
            }
        }

        @NotNull
        public final e0 f(@NotNull e $this$asResponseBody, @Nullable x contentType, long contentLength) {
            k.f($this$asResponseBody, "$this$asResponseBody");
            return new a($this$asResponseBody, contentType, contentLength);
        }

        @NotNull
        public final e0 c(@Nullable x contentType, @NotNull String content) {
            k.f(content, FirebaseAnalytics.Param.CONTENT);
            return a(content, contentType);
        }

        @NotNull
        public final e0 e(@Nullable x contentType, @NotNull byte[] content) {
            k.f(content, FirebaseAnalytics.Param.CONTENT);
            return h(content, contentType);
        }

        @NotNull
        public final e0 d(@Nullable x contentType, @NotNull f content) {
            k.f(content, FirebaseAnalytics.Param.CONTENT);
            return g(content, contentType);
        }

        @NotNull
        public final e0 b(@Nullable x contentType, long contentLength, @NotNull e content) {
            k.f(content, FirebaseAnalytics.Param.CONTENT);
            return f(content, contentType, contentLength);
        }
    }
}
