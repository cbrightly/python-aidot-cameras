package io.ktor.response;

import io.ktor.http.c;
import io.ktor.http.content.f;
import io.ktor.http.content.j;
import java.io.File;
import kotlin.coroutines.d;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ApplicationResponseFunctions.kt */
public final class b {

    /* compiled from: ApplicationResponseFunctions.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<j, x> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((j) obj);
            return x.a;
        }

        public final void invoke(@NotNull j $receiver) {
            k.f($receiver, "$receiver");
        }
    }

    public static /* synthetic */ Object c(io.ktor.application.b bVar, File file, kotlin.jvm.functions.l lVar, d dVar, int i, Object obj) {
        if ((i & 2) != 0) {
            lVar = a.INSTANCE;
        }
        return b(bVar, file, lVar, dVar);
    }

    @Nullable
    public static final Object b(@NotNull io.ktor.application.b $this$respondFile, @NotNull File file, @NotNull kotlin.jvm.functions.l<? super j, x> configure, @NotNull d<? super x> $completion) {
        f message = new f(file, (c) null, 2, (DefaultConstructorMarker) null);
        configure.invoke(message);
        io.ktor.application.b $this$respond$iv = $this$respondFile;
        Object e = $this$respond$iv.b().a().e($this$respond$iv, message, $completion);
        if (e == kotlin.coroutines.intrinsics.c.d()) {
            return e;
        }
        return x.a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002a, code lost:
        if (r3 == null) goto L_0x002d;
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final io.ktor.http.c a(@org.jetbrains.annotations.NotNull io.ktor.application.b r5, @org.jetbrains.annotations.Nullable io.ktor.http.c r6) {
        /*
            java.lang.String r0 = "$this$defaultTextContentType"
            kotlin.jvm.internal.k.f(r5, r0)
            if (r6 != 0) goto L_0x0034
            io.ktor.response.a r0 = r5.b()
            io.ktor.response.f r0 = r0.getHeaders()
            io.ktor.http.s r1 = io.ktor.http.s.V0
            java.lang.String r1 = r1.s()
            java.lang.String r0 = r0.d(r1)
            if (r0 == 0) goto L_0x002d
            r1 = r0
            r2 = 0
            io.ktor.http.c$b r3 = io.ktor.http.c.e     // Catch:{ BadContentTypeFormatException -> 0x0025 }
            io.ktor.http.c r3 = r3.b(r0)     // Catch:{ BadContentTypeFormatException -> 0x0025 }
            goto L_0x0028
        L_0x0025:
            r3 = move-exception
            r4 = 0
            r3 = r4
        L_0x0028:
            if (r3 == 0) goto L_0x002d
            goto L_0x0035
        L_0x002d:
            io.ktor.http.c$d r1 = io.ktor.http.c.d.j
            io.ktor.http.c r3 = r1.a()
            goto L_0x0035
        L_0x0034:
            r3 = r6
        L_0x0035:
            r0 = r3
            java.nio.charset.Charset r1 = io.ktor.http.d.a(r0)
            if (r1 != 0) goto L_0x0043
            java.nio.charset.Charset r1 = kotlin.text.c.a
            io.ktor.http.c r1 = io.ktor.http.d.b(r0, r1)
            goto L_0x0044
        L_0x0043:
            r1 = r0
        L_0x0044:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.response.b.a(io.ktor.application.b, io.ktor.http.c):io.ktor.http.c");
    }
}
