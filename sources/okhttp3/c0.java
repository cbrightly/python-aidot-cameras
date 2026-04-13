package okhttp3;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.nio.charset.Charset;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.x;
import okio.d;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RequestBody.kt */
public abstract class c0 {
    public static final a Companion = new a((DefaultConstructorMarker) null);

    @NotNull
    public static final c0 create(@NotNull File file, @Nullable x xVar) {
        return Companion.a(file, xVar);
    }

    @NotNull
    public static final c0 create(@NotNull String str, @Nullable x xVar) {
        return Companion.b(str, xVar);
    }

    @NotNull
    public static final c0 create(@Nullable x xVar, @NotNull File file) {
        return Companion.c(xVar, file);
    }

    @NotNull
    public static final c0 create(@Nullable x xVar, @NotNull String str) {
        return Companion.d(xVar, str);
    }

    @NotNull
    public static final c0 create(@Nullable x xVar, @NotNull f fVar) {
        return Companion.e(xVar, fVar);
    }

    @NotNull
    public static final c0 create(@Nullable x xVar, @NotNull byte[] bArr) {
        return a.j(Companion, xVar, bArr, 0, 0, 12, (Object) null);
    }

    @NotNull
    public static final c0 create(@Nullable x xVar, @NotNull byte[] bArr, int i) {
        return a.j(Companion, xVar, bArr, i, 0, 8, (Object) null);
    }

    @NotNull
    public static final c0 create(@Nullable x xVar, @NotNull byte[] bArr, int i, int i2) {
        return Companion.f(xVar, bArr, i, i2);
    }

    @NotNull
    public static final c0 create(@NotNull f fVar, @Nullable x xVar) {
        return Companion.g(fVar, xVar);
    }

    @NotNull
    public static final c0 create(@NotNull byte[] bArr) {
        return a.k(Companion, bArr, (x) null, 0, 0, 7, (Object) null);
    }

    @NotNull
    public static final c0 create(@NotNull byte[] bArr, @Nullable x xVar) {
        return a.k(Companion, bArr, xVar, 0, 0, 6, (Object) null);
    }

    @NotNull
    public static final c0 create(@NotNull byte[] bArr, @Nullable x xVar, int i) {
        return a.k(Companion, bArr, xVar, i, 0, 4, (Object) null);
    }

    @NotNull
    public static final c0 create(@NotNull byte[] bArr, @Nullable x xVar, int i, int i2) {
        return Companion.h(bArr, xVar, i, i2);
    }

    @Nullable
    public abstract x contentType();

    public abstract void writeTo(@NotNull d dVar);

    public long contentLength() {
        return -1;
    }

    public boolean isDuplex() {
        return false;
    }

    public boolean isOneShot() {
        return false;
    }

    /* compiled from: RequestBody.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static /* synthetic */ c0 i(a aVar, String str, x xVar, int i, Object obj) {
            if ((i & 1) != 0) {
                xVar = null;
            }
            return aVar.b(str, xVar);
        }

        @NotNull
        public final c0 b(@NotNull String $this$toRequestBody, @Nullable x contentType) {
            k.f($this$toRequestBody, "$this$toRequestBody");
            Charset charset = kotlin.text.c.a;
            x finalContentType = contentType;
            if (contentType != null) {
                Charset resolvedCharset = x.e(contentType, (Charset) null, 1, (Object) null);
                if (resolvedCharset == null) {
                    charset = kotlin.text.c.a;
                    x.a aVar = x.c;
                    finalContentType = aVar.b(contentType + "; charset=utf-8");
                } else {
                    charset = resolvedCharset;
                }
            }
            byte[] bytes = $this$toRequestBody.getBytes(charset);
            k.b(bytes, "(this as java.lang.String).getBytes(charset)");
            return h(bytes, finalContentType, 0, bytes.length);
        }

        /* compiled from: RequestBody.kt */
        public static final class b extends c0 {
            final /* synthetic */ f a;
            final /* synthetic */ x b;

            b(f $receiver, x $captured_local_variable$1) {
                this.a = $receiver;
                this.b = $captured_local_variable$1;
            }

            @Nullable
            public x contentType() {
                return this.b;
            }

            public long contentLength() {
                return (long) this.a.size();
            }

            public void writeTo(@NotNull d sink) {
                k.f(sink, "sink");
                sink.write(this.a);
            }
        }

        @NotNull
        public final c0 g(@NotNull f $this$toRequestBody, @Nullable x contentType) {
            k.f($this$toRequestBody, "$this$toRequestBody");
            return new b($this$toRequestBody, contentType);
        }

        public static /* synthetic */ c0 k(a aVar, byte[] bArr, x xVar, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                xVar = null;
            }
            if ((i3 & 2) != 0) {
                i = 0;
            }
            if ((i3 & 4) != 0) {
                i2 = bArr.length;
            }
            return aVar.h(bArr, xVar, i, i2);
        }

        /* compiled from: RequestBody.kt */
        public static final class c extends c0 {
            final /* synthetic */ byte[] a;
            final /* synthetic */ x b;
            final /* synthetic */ int c;
            final /* synthetic */ int d;

            c(byte[] $receiver, x $captured_local_variable$1, int $captured_local_variable$2, int $captured_local_variable$3) {
                this.a = $receiver;
                this.b = $captured_local_variable$1;
                this.c = $captured_local_variable$2;
                this.d = $captured_local_variable$3;
            }

            @Nullable
            public x contentType() {
                return this.b;
            }

            public long contentLength() {
                return (long) this.c;
            }

            public void writeTo(@NotNull d sink) {
                k.f(sink, "sink");
                sink.write(this.a, this.d, this.c);
            }
        }

        @NotNull
        public final c0 h(@NotNull byte[] $this$toRequestBody, @Nullable x contentType, int offset, int byteCount) {
            k.f($this$toRequestBody, "$this$toRequestBody");
            okhttp3.internal.b.i((long) $this$toRequestBody.length, (long) offset, (long) byteCount);
            return new c($this$toRequestBody, contentType, byteCount, offset);
        }

        /* renamed from: okhttp3.c0$a$a  reason: collision with other inner class name */
        /* compiled from: RequestBody.kt */
        public static final class C0458a extends c0 {
            final /* synthetic */ File a;
            final /* synthetic */ x b;

            C0458a(File $receiver, x $captured_local_variable$1) {
                this.a = $receiver;
                this.b = $captured_local_variable$1;
            }

            @Nullable
            public x contentType() {
                return this.b;
            }

            public long contentLength() {
                return this.a.length();
            }

            /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
                throw r2;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:8:0x0018, code lost:
                r2 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
                kotlin.io.b.a(r0, r1);
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void writeTo(@org.jetbrains.annotations.NotNull okio.d r4) {
                /*
                    r3 = this;
                    java.lang.String r0 = "sink"
                    kotlin.jvm.internal.k.f(r4, r0)
                    java.io.File r0 = r3.a
                    okio.e0 r0 = okio.p.k(r0)
                    r1 = r0
                    r2 = 0
                    r4.writeAll(r1)     // Catch:{ all -> 0x0016 }
                    r1 = 0
                    kotlin.io.b.a(r0, r1)
                    return
                L_0x0016:
                    r1 = move-exception
                    throw r1     // Catch:{ all -> 0x0018 }
                L_0x0018:
                    r2 = move-exception
                    kotlin.io.b.a(r0, r1)
                    throw r2
                */
                throw new UnsupportedOperationException("Method not decompiled: okhttp3.c0.a.C0458a.writeTo(okio.d):void");
            }
        }

        @NotNull
        public final c0 a(@NotNull File $this$asRequestBody, @Nullable x contentType) {
            k.f($this$asRequestBody, "$this$asRequestBody");
            return new C0458a($this$asRequestBody, contentType);
        }

        @NotNull
        public final c0 d(@Nullable x contentType, @NotNull String content) {
            k.f(content, FirebaseAnalytics.Param.CONTENT);
            return b(content, contentType);
        }

        @NotNull
        public final c0 e(@Nullable x contentType, @NotNull f content) {
            k.f(content, FirebaseAnalytics.Param.CONTENT);
            return g(content, contentType);
        }

        public static /* synthetic */ c0 j(a aVar, x xVar, byte[] bArr, int i, int i2, int i3, Object obj) {
            if ((i3 & 4) != 0) {
                i = 0;
            }
            if ((i3 & 8) != 0) {
                i2 = bArr.length;
            }
            return aVar.f(xVar, bArr, i, i2);
        }

        @NotNull
        public final c0 f(@Nullable x contentType, @NotNull byte[] content, int offset, int byteCount) {
            k.f(content, FirebaseAnalytics.Param.CONTENT);
            return h(content, contentType, offset, byteCount);
        }

        @NotNull
        public final c0 c(@Nullable x contentType, @NotNull File file) {
            k.f(file, "file");
            return a(file, contentType);
        }
    }
}
