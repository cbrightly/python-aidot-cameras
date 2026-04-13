package coil.decode;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okio.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: GifDecoder.kt */
public final class i implements e {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public final boolean b;

    public i(boolean enforceMinimumFrameDelay) {
        this.b = enforceMinimumFrameDelay;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ i(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    public boolean b(@NotNull e source, @Nullable String mimeType) {
        k.e(source, "source");
        d dVar = d.a;
        return d.h(source);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:84:0x013b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x013c, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:?, code lost:
        kotlin.io.b.a(r13, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0140, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0141, code lost:
        r0 = th;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:54:0x00ad, B:82:0x013a] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0166  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0167  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull coil.bitmap.c r20, @org.jetbrains.annotations.NotNull okio.e r21, @org.jetbrains.annotations.NotNull coil.size.Size r22, @org.jetbrains.annotations.NotNull coil.decode.m r23, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super coil.decode.c> r24) {
        /*
            r19 = this;
            r1 = 0
            r2 = 0
            r3 = r24
            r4 = 0
            kotlinx.coroutines.o r0 = new kotlinx.coroutines.o
            kotlin.coroutines.d r5 = kotlin.coroutines.intrinsics.b.c(r3)
            r6 = 1
            r0.<init>(r5, r6)
            r5 = r0
            r5.w()
            r7 = r5
            r8 = 0
            coil.decode.k r0 = new coil.decode.k     // Catch:{ Exception -> 0x0154 }
            r9 = r21
            r0.<init>(r7, r9)     // Catch:{ Exception -> 0x0150 }
            r10 = r0
            r11 = r10
            r12 = 0
            boolean r0 = r19.b     // Catch:{ all -> 0x0143 }
            if (r0 == 0) goto L_0x003a
            coil.decode.h r0 = new coil.decode.h     // Catch:{ all -> 0x0031 }
            r0.<init>(r11)     // Catch:{ all -> 0x0031 }
            okio.e r0 = okio.p.d(r0)     // Catch:{ all -> 0x0031 }
            goto L_0x003e
        L_0x0031:
            r0 = move-exception
            r14 = r20
            r17 = r1
            r18 = r2
            goto L_0x014a
        L_0x003a:
            okio.e r0 = okio.p.d(r11)     // Catch:{ all -> 0x0143 }
        L_0x003e:
            r13 = r0
            r14 = r13
            r15 = 0
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0132 }
            r0 = 19
            r17 = r1
            if (r6 < r0) goto L_0x005c
            java.io.InputStream r0 = r14.Y0()     // Catch:{ all -> 0x0054 }
            android.graphics.Movie r0 = android.graphics.Movie.decodeStream(r0)     // Catch:{ all -> 0x0054 }
            r18 = r2
            goto L_0x006a
        L_0x0054:
            r0 = move-exception
            r14 = r20
            r1 = r0
            r18 = r2
            goto L_0x013a
        L_0x005c:
            byte[] r0 = r14.q0()     // Catch:{ all -> 0x012b }
            r6 = 0
            int r1 = r0.length     // Catch:{ all -> 0x012b }
            r18 = r2
            r2 = 0
            android.graphics.Movie r1 = android.graphics.Movie.decodeByteArray(r0, r2, r1)     // Catch:{ all -> 0x0126 }
            r0 = r1
        L_0x006a:
            r1 = 0
            kotlin.io.b.a(r13, r1)     // Catch:{ all -> 0x0122 }
            if (r0 == 0) goto L_0x007f
            int r1 = r0.width()     // Catch:{ all -> 0x0122 }
            if (r1 <= 0) goto L_0x007f
            int r1 = r0.height()     // Catch:{ all -> 0x0122 }
            if (r1 <= 0) goto L_0x007f
            r6 = 1
            goto L_0x0080
        L_0x007f:
            r6 = 0
        L_0x0080:
            if (r6 == 0) goto L_0x0111
            coil.drawable.MovieDrawable r1 = new coil.drawable.MovieDrawable     // Catch:{ all -> 0x0122 }
            boolean r2 = r0.isOpaque()     // Catch:{ all -> 0x0122 }
            if (r2 == 0) goto L_0x0096
            boolean r2 = r23.b()     // Catch:{ all -> 0x0122 }
            if (r2 == 0) goto L_0x0096
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.RGB_565     // Catch:{ all -> 0x0122 }
            goto L_0x00a7
        L_0x0096:
            android.graphics.Bitmap$Config r2 = r23.d()     // Catch:{ all -> 0x0122 }
            boolean r2 = coil.util.GifExtensions.f(r2)     // Catch:{ all -> 0x0122 }
            if (r2 == 0) goto L_0x00a3
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.ARGB_8888     // Catch:{ all -> 0x0122 }
            goto L_0x00a7
        L_0x00a3:
            android.graphics.Bitmap$Config r2 = r23.d()     // Catch:{ all -> 0x0122 }
        L_0x00a7:
            coil.size.e r6 = r23.k()     // Catch:{ all -> 0x0122 }
            r14 = r20
            r1.<init>(r0, r14, r2, r6)     // Catch:{ all -> 0x0141 }
            coil.request.l r2 = r23.i()     // Catch:{ all -> 0x0141 }
            java.lang.Integer r2 = coil.request.h.e(r2)     // Catch:{ all -> 0x0141 }
            if (r2 != 0) goto L_0x00bc
            r2 = -1
            goto L_0x00c0
        L_0x00bc:
            int r2 = r2.intValue()     // Catch:{ all -> 0x0141 }
        L_0x00c0:
            r1.d(r2)     // Catch:{ all -> 0x0141 }
            coil.request.l r2 = r23.i()     // Catch:{ all -> 0x0141 }
            kotlin.jvm.functions.a r2 = coil.request.h.c(r2)     // Catch:{ all -> 0x0141 }
            coil.request.l r6 = r23.i()     // Catch:{ all -> 0x0141 }
            kotlin.jvm.functions.a r6 = coil.request.h.b(r6)     // Catch:{ all -> 0x0141 }
            if (r2 != 0) goto L_0x00d7
            if (r6 == 0) goto L_0x00de
        L_0x00d7:
            androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback r15 = coil.util.GifExtensions.b(r2, r6)     // Catch:{ all -> 0x0141 }
            r1.registerAnimationCallback(r15)     // Catch:{ all -> 0x0141 }
        L_0x00de:
            coil.request.l r15 = r23.i()     // Catch:{ all -> 0x0141 }
            coil.transform.a r15 = coil.request.h.a(r15)     // Catch:{ all -> 0x0141 }
            r1.c(r15)     // Catch:{ all -> 0x0141 }
            coil.decode.c r15 = new coil.decode.c     // Catch:{ all -> 0x0141 }
            r16 = r0
            r0 = 0
            r15.<init>(r1, r0)     // Catch:{ all -> 0x0141 }
            kotlin.o$a r0 = kotlin.o.Companion     // Catch:{ all -> 0x0141 }
            java.lang.Object r0 = kotlin.o.m17constructorimpl(r15)     // Catch:{ all -> 0x0141 }
            r7.resumeWith(r0)     // Catch:{ all -> 0x0141 }
            r10.a()     // Catch:{ Exception -> 0x014e }
            java.lang.Object r0 = r5.t()
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            if (r0 != r1) goto L_0x010e
            kotlin.coroutines.jvm.internal.h.c(r24)
        L_0x010e:
            return r0
        L_0x0111:
            r14 = r20
            r16 = r0
            r0 = 0
            java.lang.String r1 = "Failed to decode GIF."
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0141 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0141 }
            r0.<init>(r1)     // Catch:{ all -> 0x0141 }
            throw r0     // Catch:{ all -> 0x0141 }
        L_0x0122:
            r0 = move-exception
            r14 = r20
            goto L_0x014a
        L_0x0126:
            r0 = move-exception
            r14 = r20
            r1 = r0
            goto L_0x013a
        L_0x012b:
            r0 = move-exception
            r14 = r20
            r18 = r2
            r1 = r0
            goto L_0x013a
        L_0x0132:
            r0 = move-exception
            r14 = r20
            r17 = r1
            r18 = r2
            r1 = r0
        L_0x013a:
            throw r1     // Catch:{ all -> 0x013b }
        L_0x013b:
            r0 = move-exception
            r2 = r0
            kotlin.io.b.a(r13, r1)     // Catch:{ all -> 0x0141 }
            throw r2     // Catch:{ all -> 0x0141 }
        L_0x0141:
            r0 = move-exception
            goto L_0x014a
        L_0x0143:
            r0 = move-exception
            r14 = r20
            r17 = r1
            r18 = r2
        L_0x014a:
            r10.a()     // Catch:{ Exception -> 0x014e }
            throw r0     // Catch:{ Exception -> 0x014e }
        L_0x014e:
            r0 = move-exception
            goto L_0x015d
        L_0x0150:
            r0 = move-exception
            r14 = r20
            goto L_0x0159
        L_0x0154:
            r0 = move-exception
            r14 = r20
            r9 = r21
        L_0x0159:
            r17 = r1
            r18 = r2
        L_0x015d:
            boolean r1 = r0 instanceof java.lang.InterruptedException
            if (r1 != 0) goto L_0x0167
            boolean r1 = r0 instanceof java.io.InterruptedIOException
            if (r1 == 0) goto L_0x0166
            goto L_0x0167
        L_0x0166:
            throw r0
        L_0x0167:
            java.util.concurrent.CancellationException r1 = new java.util.concurrent.CancellationException
            java.lang.String r2 = "Blocking call was interrupted due to parent cancellation."
            r1.<init>(r2)
            java.lang.Throwable r1 = r1.initCause(r0)
            java.lang.String r2 = "CancellationException(\"Blocking call was interrupted due to parent cancellation.\").initCause(exception)"
            kotlin.jvm.internal.k.d(r1, r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.decode.i.a(coil.bitmap.c, okio.e, coil.size.Size, coil.decode.m, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: GifDecoder.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
