package coil.decode;

import android.graphics.drawable.ColorDrawable;
import kotlin.jvm.internal.k;
import okio.b0;
import okio.e;
import okio.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EmptyDecoder.kt */
public final class g implements e {
    @NotNull
    public static final g a = new g();
    @NotNull
    private static final c b = new c(new ColorDrawable(), false);
    @NotNull
    private static final b0 c = p.b();

    private g() {
    }

    public boolean b(@NotNull e source, @Nullable String mimeType) {
        k.e(source, "source");
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0014, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0015, code lost:
        kotlin.io.b.a(r6, r0);
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull coil.bitmap.c r5, @org.jetbrains.annotations.NotNull okio.e r6, @org.jetbrains.annotations.NotNull coil.size.Size r7, @org.jetbrains.annotations.NotNull coil.decode.m r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super coil.decode.c> r9) {
        /*
            r4 = this;
            r0 = r6
            r1 = 0
            okio.b0 r2 = c     // Catch:{ all -> 0x0012 }
            long r2 = r0.M0(r2)     // Catch:{ all -> 0x0012 }
            kotlin.coroutines.jvm.internal.b.d(r2)     // Catch:{ all -> 0x0012 }
            r0 = 0
            kotlin.io.b.a(r6, r0)
            coil.decode.c r0 = b
            return r0
        L_0x0012:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0014 }
        L_0x0014:
            r1 = move-exception
            kotlin.io.b.a(r6, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.decode.g.a(coil.bitmap.c, okio.e, coil.size.Size, coil.decode.m, kotlin.coroutines.d):java.lang.Object");
    }
}
