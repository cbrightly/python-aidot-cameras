package coil.decode;

import android.content.Context;
import coil.util.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okio.e;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SvgDecoder.kt */
public final class n implements e {
    @NotNull
    private static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    @Deprecated
    private static final f b;
    @NotNull
    @Deprecated
    private static final f c;
    /* access modifiers changed from: private */
    @NotNull
    public final Context d;
    /* access modifiers changed from: private */
    public final boolean e;

    public n(@NotNull Context context, boolean useViewBoundsAsIntrinsicSize) {
        k.e(context, "context");
        this.d = context;
        this.e = useViewBoundsAsIntrinsicSize;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ n(Context context, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? true : z);
    }

    public boolean b(@NotNull e source, @Nullable String mimeType) {
        k.e(source, "source");
        return k.a(mimeType, "image/svg+xml") || e(source);
    }

    private final boolean e(e source) {
        if (source.V(0, c)) {
            if (i.a(source, b, 0, 1024) != -1) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x01a6  */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01a7  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull coil.bitmap.c r25, @org.jetbrains.annotations.NotNull okio.e r26, @org.jetbrains.annotations.NotNull coil.size.Size r27, @org.jetbrains.annotations.NotNull coil.decode.m r28, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super coil.decode.c> r29) {
        /*
            r24 = this;
            r1 = r27
            java.lang.String r0 = "100%"
            r2 = 0
            r3 = 0
            r4 = r29
            r5 = 0
            kotlinx.coroutines.o r6 = new kotlinx.coroutines.o
            kotlin.coroutines.d r7 = kotlin.coroutines.intrinsics.b.c(r4)
            r8 = 1
            r6.<init>(r7, r8)
            r6.w()
            r7 = r6
            r9 = 0
            coil.decode.k r10 = new coil.decode.k     // Catch:{ Exception -> 0x0192 }
            r11 = r26
            r10.<init>(r7, r11)     // Catch:{ Exception -> 0x0190 }
            r12 = r10
            r13 = 0
            okio.e r14 = okio.p.d(r12)     // Catch:{ all -> 0x0181 }
            r15 = 0
            r16 = r14
            r17 = 0
            java.io.InputStream r18 = r16.Y0()     // Catch:{ all -> 0x016e }
            com.caverock.androidsvg.g r18 = com.caverock.androidsvg.g.l(r18)     // Catch:{ all -> 0x016e }
            kotlin.io.b.a(r14, r15)     // Catch:{ all -> 0x0181 }
            r14 = r18
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            android.graphics.RectF r19 = r14.g()     // Catch:{ all -> 0x0181 }
            boolean r8 = r1 instanceof coil.size.PixelSize     // Catch:{ all -> 0x0181 }
            r20 = r2
            r2 = 0
            if (r8 == 0) goto L_0x00c3
            boolean r8 = r24.e     // Catch:{ all -> 0x00ba }
            if (r8 == 0) goto L_0x0066
            if (r19 == 0) goto L_0x0066
            float r8 = r19.width()     // Catch:{ all -> 0x005d }
            float r15 = r19.height()     // Catch:{ all -> 0x005d }
            goto L_0x006e
        L_0x005d:
            r0 = move-exception
            r22 = r3
            r23 = r4
            r4 = r25
            goto L_0x018a
        L_0x0066:
            float r8 = r14.h()     // Catch:{ all -> 0x00ba }
            float r15 = r14.f()     // Catch:{ all -> 0x00ba }
        L_0x006e:
            int r16 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r16 <= 0) goto L_0x00a6
            int r16 = (r15 > r2 ? 1 : (r15 == r2 ? 0 : -1))
            if (r16 <= 0) goto L_0x00a6
            coil.decode.d r16 = coil.decode.d.a     // Catch:{ all -> 0x00ba }
            r16 = r1
            coil.size.PixelSize r16 = (coil.size.PixelSize) r16     // Catch:{ all -> 0x00ba }
            int r2 = r16.d()     // Catch:{ all -> 0x00ba }
            float r2 = (float) r2     // Catch:{ all -> 0x00ba }
            r16 = r1
            coil.size.PixelSize r16 = (coil.size.PixelSize) r16     // Catch:{ all -> 0x00ba }
            r22 = r3
            int r3 = r16.c()     // Catch:{ all -> 0x009f }
            float r3 = (float) r3
            r23 = r4
            coil.size.e r4 = r28.k()     // Catch:{ all -> 0x016a }
            float r2 = coil.decode.d.e(r8, r15, r2, r3, r4)     // Catch:{ all -> 0x016a }
            float r3 = r2 * r8
            int r3 = (int) r3     // Catch:{ all -> 0x016a }
            float r4 = r2 * r15
            int r2 = (int) r4     // Catch:{ all -> 0x016a }
            goto L_0x00f8
        L_0x009f:
            r0 = move-exception
            r23 = r4
            r4 = r25
            goto L_0x018a
        L_0x00a6:
            r22 = r3
            r23 = r4
            r2 = r1
            coil.size.PixelSize r2 = (coil.size.PixelSize) r2     // Catch:{ all -> 0x016a }
            int r2 = r2.d()     // Catch:{ all -> 0x016a }
            r3 = r2
            r2 = r1
            coil.size.PixelSize r2 = (coil.size.PixelSize) r2     // Catch:{ all -> 0x016a }
            int r2 = r2.c()     // Catch:{ all -> 0x016a }
            goto L_0x00f8
        L_0x00ba:
            r0 = move-exception
            r22 = r3
            r23 = r4
            r4 = r25
            goto L_0x018a
        L_0x00c3:
            r22 = r3
            r23 = r4
            boolean r2 = r1 instanceof coil.size.OriginalSize     // Catch:{ all -> 0x016a }
            if (r2 == 0) goto L_0x0162
            float r2 = r14.h()     // Catch:{ all -> 0x016a }
            r8 = r2
            float r2 = r14.f()     // Catch:{ all -> 0x016a }
            r15 = r2
            r2 = 0
            int r3 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x00e1
            int r3 = (r15 > r2 ? 1 : (r15 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x00e1
            int r3 = (int) r8     // Catch:{ all -> 0x016a }
            int r2 = (int) r15     // Catch:{ all -> 0x016a }
            goto L_0x00f8
        L_0x00e1:
            boolean r2 = r24.e     // Catch:{ all -> 0x016a }
            if (r2 == 0) goto L_0x00f4
            if (r19 == 0) goto L_0x00f4
            float r2 = r19.width()     // Catch:{ all -> 0x016a }
            int r3 = (int) r2     // Catch:{ all -> 0x016a }
            float r2 = r19.height()     // Catch:{ all -> 0x016a }
            int r2 = (int) r2     // Catch:{ all -> 0x016a }
            goto L_0x00f8
        L_0x00f4:
            r3 = 512(0x200, float:7.175E-43)
            r2 = 512(0x200, float:7.175E-43)
        L_0x00f8:
            if (r19 != 0) goto L_0x0106
            r4 = 0
            int r16 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r16 <= 0) goto L_0x0106
            int r16 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            if (r16 <= 0) goto L_0x0106
            r14.y(r4, r4, r8, r15)     // Catch:{ all -> 0x016a }
        L_0x0106:
            r14.z(r0)     // Catch:{ all -> 0x016a }
            r14.x(r0)     // Catch:{ all -> 0x016a }
            android.graphics.Bitmap$Config r0 = r28.d()     // Catch:{ all -> 0x016a }
            android.graphics.Bitmap$Config r0 = coil.util.i.c(r0)     // Catch:{ all -> 0x016a }
            r4 = r25
            android.graphics.Bitmap r0 = r4.c(r3, r2, r0)     // Catch:{ all -> 0x017f }
            android.graphics.Canvas r1 = new android.graphics.Canvas     // Catch:{ all -> 0x017f }
            r1.<init>(r0)     // Catch:{ all -> 0x017f }
            r14.r(r1)     // Catch:{ all -> 0x017f }
            coil.decode.c r1 = new coil.decode.c     // Catch:{ all -> 0x017f }
            android.content.Context r16 = r24.d     // Catch:{ all -> 0x017f }
            r17 = r2
            android.content.res.Resources r2 = r16.getResources()     // Catch:{ all -> 0x017f }
            r16 = r3
            java.lang.String r3 = "context.resources"
            kotlin.jvm.internal.k.d(r2, r3)     // Catch:{ all -> 0x017f }
            r3 = r0
            r18 = 0
            r21 = r0
            android.graphics.drawable.BitmapDrawable r0 = new android.graphics.drawable.BitmapDrawable     // Catch:{ all -> 0x017f }
            r0.<init>(r2, r3)     // Catch:{ all -> 0x017f }
            r2 = 1
            r1.<init>(r0, r2)     // Catch:{ all -> 0x017f }
            kotlin.o$a r0 = kotlin.o.Companion     // Catch:{ all -> 0x017f }
            java.lang.Object r0 = kotlin.o.m17constructorimpl(r1)     // Catch:{ all -> 0x017f }
            r7.resumeWith(r0)     // Catch:{ all -> 0x017f }
            r10.a()     // Catch:{ Exception -> 0x018e }
            java.lang.Object r0 = r6.t()
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            if (r0 != r1) goto L_0x015f
            kotlin.coroutines.jvm.internal.h.c(r29)
        L_0x015f:
            return r0
        L_0x0162:
            r4 = r25
            kotlin.NoWhenBranchMatchedException r0 = new kotlin.NoWhenBranchMatchedException     // Catch:{ all -> 0x017f }
            r0.<init>()     // Catch:{ all -> 0x017f }
            throw r0     // Catch:{ all -> 0x017f }
        L_0x016a:
            r0 = move-exception
            r4 = r25
            goto L_0x018a
        L_0x016e:
            r0 = move-exception
            r20 = r2
            r22 = r3
            r23 = r4
            r4 = r25
            r1 = r0
            throw r1     // Catch:{ all -> 0x0179 }
        L_0x0179:
            r0 = move-exception
            r2 = r0
            kotlin.io.b.a(r14, r1)     // Catch:{ all -> 0x017f }
            throw r2     // Catch:{ all -> 0x017f }
        L_0x017f:
            r0 = move-exception
            goto L_0x018a
        L_0x0181:
            r0 = move-exception
            r20 = r2
            r22 = r3
            r23 = r4
            r4 = r25
        L_0x018a:
            r10.a()     // Catch:{ Exception -> 0x018e }
            throw r0     // Catch:{ Exception -> 0x018e }
        L_0x018e:
            r0 = move-exception
            goto L_0x019d
        L_0x0190:
            r0 = move-exception
            goto L_0x0195
        L_0x0192:
            r0 = move-exception
            r11 = r26
        L_0x0195:
            r20 = r2
            r22 = r3
            r23 = r4
            r4 = r25
        L_0x019d:
            boolean r1 = r0 instanceof java.lang.InterruptedException
            if (r1 != 0) goto L_0x01a7
            boolean r1 = r0 instanceof java.io.InterruptedIOException
            if (r1 == 0) goto L_0x01a6
            goto L_0x01a7
        L_0x01a6:
            throw r0
        L_0x01a7:
            java.util.concurrent.CancellationException r1 = new java.util.concurrent.CancellationException
            java.lang.String r2 = "Blocking call was interrupted due to parent cancellation."
            r1.<init>(r2)
            java.lang.Throwable r1 = r1.initCause(r0)
            java.lang.String r2 = "CancellationException(\"Blocking call was interrupted due to parent cancellation.\").initCause(exception)"
            kotlin.jvm.internal.k.d(r1, r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.decode.n.a(coil.bitmap.c, okio.e, coil.size.Size, coil.decode.m, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: SvgDecoder.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }

    static {
        f.a aVar = f.Companion;
        b = aVar.d("<svg ");
        c = aVar.d("<");
    }
}
