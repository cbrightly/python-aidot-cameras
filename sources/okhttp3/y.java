package okhttp3;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.yanzhenjie.andserver.util.h;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.c0;
import okhttp3.u;
import okhttp3.x;
import okio.d;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: MultipartBody.kt */
public final class y extends c0 {
    @NotNull
    public static final x a;
    @NotNull
    public static final x b;
    @NotNull
    public static final x c;
    @NotNull
    public static final x d;
    @NotNull
    public static final x e;
    private static final byte[] f = {(byte) 58, (byte) 32};
    private static final byte[] g = {(byte) 13, (byte) 10};
    private static final byte[] h;
    public static final b i = new b((DefaultConstructorMarker) null);
    private final x j;
    private long k = -1;
    private final f l;
    @NotNull
    private final x m;
    @NotNull
    private final List<c> n;

    public y(@NotNull f boundaryByteString, @NotNull x type, @NotNull List<c> parts) {
        k.f(boundaryByteString, "boundaryByteString");
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.f(parts, "parts");
        this.l = boundaryByteString;
        this.m = type;
        this.n = parts;
        x.a aVar = x.c;
        this.j = aVar.a(type + "; boundary=" + a());
    }

    @NotNull
    public final String a() {
        return this.l.utf8();
    }

    @NotNull
    public x contentType() {
        return this.j;
    }

    public long contentLength() {
        long result = this.k;
        if (result != -1) {
            return result;
        }
        long result2 = b((d) null, true);
        this.k = result2;
        return result2;
    }

    public void writeTo(@NotNull d sink) {
        k.f(sink, "sink");
        b(sink, false);
    }

    private final long b(d sink, boolean countBytes) {
        d sink2 = sink;
        long byteCount = 0;
        okio.c byteCountBuffer = null;
        if (countBytes) {
            byteCountBuffer = new okio.c();
            sink2 = byteCountBuffer;
        }
        int size = this.n.size();
        for (int p = 0; p < size; p++) {
            c part = this.n.get(p);
            u headers = part.c();
            c0 body = part.a();
            if (sink2 == null) {
                k.n();
            }
            sink2.write(h);
            sink2.write(this.l);
            sink2.write(g);
            if (headers != null) {
                int size2 = headers.size();
                for (int h2 = 0; h2 < size2; h2++) {
                    sink2.writeUtf8(headers.b(h2)).write(f).writeUtf8(headers.h(h2)).write(g);
                }
            }
            x contentType = body.contentType();
            if (contentType != null) {
                sink2.writeUtf8("Content-Type: ").writeUtf8(contentType.toString()).write(g);
            }
            long contentLength = body.contentLength();
            if (contentLength != -1) {
                sink2.writeUtf8("Content-Length: ").writeDecimalLong(contentLength).write(g);
            } else if (countBytes) {
                if (byteCountBuffer == null) {
                    k.n();
                }
                byteCountBuffer.clear();
                return -1;
            }
            byte[] bArr = g;
            sink2.write(bArr);
            if (countBytes) {
                byteCount += contentLength;
            } else {
                body.writeTo(sink2);
            }
            sink2.write(bArr);
        }
        if (sink2 == null) {
            k.n();
        }
        byte[] bArr2 = h;
        sink2.write(bArr2);
        sink2.write(this.l);
        sink2.write(bArr2);
        sink2.write(g);
        if (!countBytes) {
            return byteCount;
        }
        if (byteCountBuffer == null) {
            k.n();
        }
        long byteCount2 = byteCount + byteCountBuffer.e1();
        byteCountBuffer.clear();
        return byteCount2;
    }

    /* compiled from: MultipartBody.kt */
    public static final class c {
        public static final a a = new a((DefaultConstructorMarker) null);
        @Nullable
        private final u b;
        @NotNull
        private final c0 c;

        @NotNull
        public static final c b(@NotNull String str, @Nullable String str2, @NotNull c0 c0Var) {
            return a.c(str, str2, c0Var);
        }

        private c(u headers, c0 body) {
            this.b = headers;
            this.c = body;
        }

        public /* synthetic */ c(u headers, c0 body, DefaultConstructorMarker $constructor_marker) {
            this(headers, body);
        }

        @Nullable
        public final u c() {
            return this.b;
        }

        @NotNull
        public final c0 a() {
            return this.c;
        }

        /* compiled from: MultipartBody.kt */
        public static final class a {
            private a() {
            }

            public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            @NotNull
            public final c a(@Nullable u headers, @NotNull c0 body) {
                k.f(body, "body");
                boolean z = true;
                if ((headers != null ? headers.a("Content-Type") : null) == null) {
                    if ((headers != null ? headers.a("Content-Length") : null) != null) {
                        z = false;
                    }
                    if (z) {
                        return new c(headers, body, (DefaultConstructorMarker) null);
                    }
                    throw new IllegalArgumentException("Unexpected header: Content-Length".toString());
                }
                throw new IllegalArgumentException("Unexpected header: Content-Type".toString());
            }

            @NotNull
            public final c b(@NotNull String name, @NotNull String value) {
                k.f(name, "name");
                k.f(value, "value");
                return c(name, (String) null, c0.a.i(c0.Companion, value, (x) null, 1, (Object) null));
            }

            @NotNull
            public final c c(@NotNull String name, @Nullable String filename, @NotNull c0 body) {
                k.f(name, "name");
                k.f(body, "body");
                StringBuilder sb = new StringBuilder();
                StringBuilder $this$buildString = sb;
                $this$buildString.append("form-data; name=");
                b bVar = y.i;
                bVar.a($this$buildString, name);
                if (filename != null) {
                    $this$buildString.append("; filename=");
                    bVar.a($this$buildString, filename);
                }
                String disposition = sb.toString();
                k.b(disposition, "StringBuilder().apply(builderAction).toString()");
                return a(new u.a().e(HttpHeaders.HEAD_KEY_CONTENT_DISPOSITION, disposition).f(), body);
            }
        }
    }

    /* compiled from: MultipartBody.kt */
    public static final class a {
        private final f a;
        private x b;
        private final List<c> c;

        public a() {
            this((String) null, 1, (DefaultConstructorMarker) null);
        }

        public a(@NotNull String boundary) {
            k.f(boundary, "boundary");
            this.a = f.Companion.d(boundary);
            this.b = y.a;
            this.c = new ArrayList();
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ a(java.lang.String r1, int r2, kotlin.jvm.internal.DefaultConstructorMarker r3) {
            /*
                r0 = this;
                r2 = r2 & 1
                if (r2 == 0) goto L_0x0011
                java.util.UUID r1 = java.util.UUID.randomUUID()
                java.lang.String r1 = r1.toString()
                java.lang.String r2 = "UUID.randomUUID().toString()"
                kotlin.jvm.internal.k.b(r1, r2)
            L_0x0011:
                r0.<init>(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.y.a.<init>(java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        @NotNull
        public final a f(@NotNull x type) {
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            if (k.a(type.j(), "multipart")) {
                this.b = type;
                return this;
            }
            throw new IllegalArgumentException(("multipart != " + type).toString());
        }

        @NotNull
        public final a c(@Nullable u headers, @NotNull c0 body) {
            k.f(body, "body");
            d(c.a.a(headers, body));
            return this;
        }

        @NotNull
        public final a a(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            d(c.a.b(name, value));
            return this;
        }

        @NotNull
        public final a b(@NotNull String name, @Nullable String filename, @NotNull c0 body) {
            k.f(name, "name");
            k.f(body, "body");
            d(c.a.c(name, filename, body));
            return this;
        }

        @NotNull
        public final a d(@NotNull c part) {
            k.f(part, "part");
            this.c.add(part);
            return this;
        }

        @NotNull
        public final y e() {
            if (!this.c.isEmpty()) {
                return new y(this.a, this.b, okhttp3.internal.b.R(this.c));
            }
            throw new IllegalStateException("Multipart body must have at least one part.".toString());
        }
    }

    /* compiled from: MultipartBody.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final void a(@NotNull StringBuilder $this$appendQuotedString, @NotNull String key) {
            k.f($this$appendQuotedString, "$this$appendQuotedString");
            k.f(key, CacheEntity.KEY);
            $this$appendQuotedString.append(StringUtil.DOUBLE_QUOTE);
            int length = key.length();
            for (int i = 0; i < length; i++) {
                char ch = key.charAt(i);
                if (ch == 10) {
                    $this$appendQuotedString.append("%0A");
                } else if (ch == 13) {
                    $this$appendQuotedString.append("%0D");
                } else if (ch == '\"') {
                    $this$appendQuotedString.append("%22");
                } else {
                    $this$appendQuotedString.append(ch);
                }
            }
            $this$appendQuotedString.append(StringUtil.DOUBLE_QUOTE);
        }
    }

    static {
        x.a aVar = x.c;
        a = aVar.a("multipart/mixed");
        b = aVar.a("multipart/alternative");
        c = aVar.a("multipart/digest");
        d = aVar.a("multipart/parallel");
        e = aVar.a(h.MULTIPART_FORM_DATA_VALUE);
        byte b2 = (byte) 45;
        h = new byte[]{b2, b2};
    }
}
