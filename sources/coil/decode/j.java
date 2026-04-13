package coil.decode;

import android.content.Context;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.RequiresApi;
import coil.request.h;
import coil.size.PixelSize;
import coil.size.Size;
import coil.util.GifExtensions;
import java.io.File;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.w;
import kotlin.jvm.internal.z;
import kotlin.x;
import kotlinx.coroutines.o0;
import okio.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiresApi(28)
/* compiled from: ImageDecoderDecoder.kt */
public final class j implements e {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public final boolean b;
    /* access modifiers changed from: private */
    @Nullable
    public final Context c;

    @f(c = "coil.decode.ImageDecoderDecoder", f = "ImageDecoderDecoder.kt", l = {174, 148}, m = "decode")
    /* compiled from: ImageDecoderDecoder.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(j jVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = jVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a((coil.bitmap.c) null, (e) null, (Size) null, (m) null, this);
        }
    }

    private j(boolean enforceMinimumFrameDelay, Context context) {
        this.b = enforceMinimumFrameDelay;
        this.c = context;
    }

    /* compiled from: ImageDecoder.kt */
    public static final class d implements ImageDecoder.OnHeaderDecodedListener {
        final /* synthetic */ z a;
        final /* synthetic */ Size b;
        final /* synthetic */ m c;
        final /* synthetic */ w d;

        public d(z zVar, Size size, m mVar, w wVar) {
            this.a = zVar;
            this.b = size;
            this.c = mVar;
            this.d = wVar;
        }

        public final void onHeaderDecoded(@NotNull ImageDecoder decoder, @NotNull ImageDecoder.ImageInfo info, @NotNull ImageDecoder.Source source) {
            int i;
            int i2;
            k.e(decoder, "decoder");
            k.e(info, "info");
            k.e(source, "source");
            ImageDecoder $this$decode_u24lambda_u2d4_u24lambda_u2d3 = decoder;
            ImageDecoder.Source source2 = source;
            ImageDecoder.ImageInfo info2 = info;
            File file = (File) this.a.element;
            if (file != null) {
                file.delete();
            }
            if (this.b instanceof PixelSize) {
                android.util.Size $this$component2$iv = info2.getSize();
                k.d($this$component2$iv, "size");
                int srcWidth = $this$component2$iv.getWidth();
                int srcHeight = $this$component2$iv.getHeight();
                d dVar = d.a;
                double multiplier = d.d(srcWidth, srcHeight, ((PixelSize) this.b).d(), ((PixelSize) this.b).c(), this.c.k());
                w wVar = this.d;
                boolean z = multiplier < 1.0d;
                wVar.element = z;
                if (z || !this.c.a()) {
                    $this$decode_u24lambda_u2d4_u24lambda_u2d3.setTargetSize(kotlin.math.b.a(((double) srcWidth) * multiplier), kotlin.math.b.a(((double) srcHeight) * multiplier));
                }
            }
            if (GifExtensions.f(this.c.d())) {
                i = 3;
            } else {
                i = 1;
            }
            $this$decode_u24lambda_u2d4_u24lambda_u2d3.setAllocator(i);
            if (this.c.b()) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            $this$decode_u24lambda_u2d4_u24lambda_u2d3.setMemorySizePolicy(i2);
            if (this.c.c() != null) {
                $this$decode_u24lambda_u2d4_u24lambda_u2d3.setTargetColorSpace(this.c.c());
            }
            $this$decode_u24lambda_u2d4_u24lambda_u2d3.setUnpremultipliedRequired(!this.c.j());
            coil.transform.a a2 = h.a(this.c.i());
            $this$decode_u24lambda_u2d4_u24lambda_u2d3.setPostProcessor(a2 == null ? null : GifExtensions.c(a2));
        }
    }

    public j() {
        this(false, (Context) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public j(@NotNull Context context) {
        this(false, context);
        k.e(context, "context");
    }

    public boolean b(@NotNull e source, @Nullable String mimeType) {
        k.e(source, "source");
        d dVar = d.a;
        return d.h(source) || d.g(source) || (Build.VERSION.SDK_INT >= 30 && d.f(source));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v14, resolved type: kotlin.jvm.internal.w} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v21, resolved type: coil.decode.m} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v31, resolved type: android.graphics.drawable.Drawable} */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0254, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x026c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x026d, code lost:
        r7 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:?, code lost:
        kotlin.io.b.a(r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0271, code lost:
        throw r7;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:85:0x018a, B:134:0x026b] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01da A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01db  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x01e8  */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0249  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x02b4 A[Catch:{ all -> 0x02bd }] */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x02e8  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x02e9  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x013c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01b3 A[Catch:{ all -> 0x02bd }] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01d5  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull coil.bitmap.c r27, @org.jetbrains.annotations.NotNull okio.e r28, @org.jetbrains.annotations.NotNull coil.size.Size r29, @org.jetbrains.annotations.NotNull coil.decode.m r30, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super coil.decode.c> r31) {
        /*
            r26 = this;
            r0 = r31
            boolean r1 = r0 instanceof coil.decode.j.b
            if (r1 == 0) goto L_0x0018
            r1 = r0
            coil.decode.j$b r1 = (coil.decode.j.b) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r26
            goto L_0x0020
        L_0x0018:
            coil.decode.j$b r1 = new coil.decode.j$b
            r2 = r26
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r1.label
            r5 = 0
            r6 = 0
            switch(r4) {
                case 0: goto L_0x0071;
                case 1: goto L_0x004a;
                case 2: goto L_0x0035;
                default: goto L_0x002d;
            }
        L_0x002d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0035:
            r0 = r6
            java.lang.Object r4 = r1.L$2
            r0 = r4
            android.graphics.drawable.Drawable r0 = (android.graphics.drawable.Drawable) r0
            java.lang.Object r4 = r1.L$1
            kotlin.jvm.internal.w r4 = (kotlin.jvm.internal.w) r4
            java.lang.Object r5 = r1.L$0
            coil.decode.m r5 = (coil.decode.m) r5
            kotlin.p.b(r3)
            r19 = r3
            goto L_0x0238
        L_0x004a:
            r4 = r30
            r7 = r6
            r8 = r5
            r5 = 0
            r8 = 0
            java.lang.Object r9 = r1.L$4
            r7 = r9
            kotlin.jvm.internal.w r7 = (kotlin.jvm.internal.w) r7
            java.lang.Object r9 = r1.L$3
            r4 = r9
            coil.decode.m r4 = (coil.decode.m) r4
            java.lang.Object r9 = r1.L$2
            coil.size.Size r9 = (coil.size.Size) r9
            java.lang.Object r9 = r1.L$1
            okio.e r9 = (okio.e) r9
            java.lang.Object r9 = r1.L$0
            coil.decode.j r9 = (coil.decode.j) r9
            kotlin.p.b(r3)
            r2 = r3
            r19 = r2
            r3 = r8
            r8 = r4
            r4 = r7
            goto L_0x01e0
        L_0x0071:
            kotlin.p.b(r3)
            r4 = r26
            r7 = r28
            r8 = r30
            r9 = r27
            r10 = r29
            kotlin.jvm.internal.w r11 = new kotlin.jvm.internal.w
            r11.<init>()
            r12 = 0
            r9 = 0
            r1.L$0 = r4
            r1.L$1 = r7
            r1.L$2 = r10
            r1.L$3 = r8
            r1.L$4 = r11
            r13 = 1
            r1.label = r13
            r14 = r1
            r15 = 0
            kotlinx.coroutines.o r5 = new kotlinx.coroutines.o
            kotlin.coroutines.d r6 = kotlin.coroutines.intrinsics.b.c(r14)
            r5.<init>(r6, r13)
            r5.w()
            r6 = r5
            r16 = 0
            coil.decode.k r13 = new coil.decode.k     // Catch:{ Exception -> 0x02d2 }
            r13.<init>(r6, r7)     // Catch:{ Exception -> 0x02d2 }
            r17 = r13
            r18 = 0
            kotlin.jvm.internal.z r19 = new kotlin.jvm.internal.z     // Catch:{ all -> 0x02bf }
            r19.<init>()     // Catch:{ all -> 0x02bf }
            r28 = r19
            okio.e r19 = okio.p.d(r17)     // Catch:{ all -> 0x029e }
            r29 = r19
            boolean r19 = r4.b     // Catch:{ all -> 0x029e }
            if (r19 == 0) goto L_0x00f8
            coil.decode.d r19 = coil.decode.d.a     // Catch:{ all -> 0x00e7 }
            boolean r19 = coil.decode.d.h(r29)     // Catch:{ all -> 0x00e7 }
            if (r19 == 0) goto L_0x00f8
            coil.decode.h r2 = new coil.decode.h     // Catch:{ all -> 0x00e7 }
            r19 = r3
            r3 = r29
            r2.<init>(r3)     // Catch:{ all -> 0x00d8 }
            okio.e r2 = okio.p.d(r2)     // Catch:{ all -> 0x00d8 }
            goto L_0x00fd
        L_0x00d8:
            r0 = move-exception
            r21 = r4
            r20 = r7
            r27 = r12
            r30 = r14
            r4 = r28
            r28 = r9
            goto L_0x02ad
        L_0x00e7:
            r0 = move-exception
            r19 = r3
            r21 = r4
            r20 = r7
            r27 = r12
            r30 = r14
            r4 = r28
            r28 = r9
            goto L_0x02ad
        L_0x00f8:
            r19 = r3
            r3 = r29
            r2 = r3
        L_0x00fd:
            r29 = r3
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0290 }
            r20 = r7
            r7 = 30
            if (r3 < r7) goto L_0x013c
            r3 = r2
            r7 = 0
            byte[] r21 = r3.q0()     // Catch:{ all -> 0x0133 }
            r3 = 0
            kotlin.io.b.a(r2, r3)     // Catch:{ all -> 0x0126 }
            java.nio.ByteBuffer r3 = java.nio.ByteBuffer.wrap(r21)     // Catch:{ all -> 0x0126 }
            android.graphics.ImageDecoder$Source r3 = android.graphics.ImageDecoder.createSource(r3)     // Catch:{ all -> 0x0126 }
            r21 = r4
            r27 = r12
            r30 = r14
            r4 = r28
            r28 = r9
            goto L_0x0195
        L_0x0126:
            r0 = move-exception
            r21 = r4
            r27 = r12
            r30 = r14
            r4 = r28
            r28 = r9
            goto L_0x02ad
        L_0x0133:
            r0 = move-exception
            r3 = r0
            throw r3     // Catch:{ all -> 0x0136 }
        L_0x0136:
            r0 = move-exception
            r7 = r0
            kotlin.io.b.a(r2, r3)     // Catch:{ all -> 0x0126 }
            throw r7     // Catch:{ all -> 0x0126 }
        L_0x013c:
            java.lang.String r3 = "tmp"
            android.content.Context r7 = r4.c     // Catch:{ all -> 0x0284 }
            if (r7 != 0) goto L_0x0147
        L_0x0145:
            r7 = 0
            goto L_0x0157
        L_0x0147:
            java.io.File r7 = r7.getCacheDir()     // Catch:{ all -> 0x0284 }
            if (r7 != 0) goto L_0x014e
            goto L_0x0145
        L_0x014e:
            r21 = r7
            r22 = 0
            r21.mkdirs()     // Catch:{ all -> 0x0284 }
            kotlin.x r21 = kotlin.x.a     // Catch:{ all -> 0x0284 }
        L_0x0157:
            r21 = r4
            r4 = 0
            java.io.File r3 = java.io.File.createTempFile(r3, r4, r7)     // Catch:{ all -> 0x027a }
            r4 = r28
            r4.element = r3     // Catch:{ all -> 0x0272 }
            r7 = r2
            r22 = 0
            java.io.File r3 = (java.io.File) r3     // Catch:{ all -> 0x0263 }
            r28 = r9
            r27 = r12
            r30 = r14
            r9 = 1
            r12 = 0
            r14 = 0
            okio.b0 r3 = okio.q.g(r3, r12, r9, r14)     // Catch:{ all -> 0x0260 }
            r9 = r3
            r12 = 0
            long r23 = r7.M0(r9)     // Catch:{ all -> 0x0257 }
            java.lang.Long r9 = kotlin.coroutines.jvm.internal.b.d(r23)     // Catch:{ all -> 0x0257 }
            r12 = 0
            kotlin.io.b.a(r3, r12)     // Catch:{ all -> 0x0260 }
            long r23 = r9.longValue()     // Catch:{ all -> 0x0260 }
            kotlin.coroutines.jvm.internal.b.d(r23)     // Catch:{ all -> 0x0260 }
            r3 = 0
            kotlin.io.b.a(r2, r3)     // Catch:{ all -> 0x0254 }
            T r3 = r4.element     // Catch:{ all -> 0x0254 }
            java.io.File r3 = (java.io.File) r3     // Catch:{ all -> 0x0254 }
            android.graphics.ImageDecoder$Source r3 = android.graphics.ImageDecoder.createSource(r3)     // Catch:{ all -> 0x0254 }
        L_0x0195:
            java.lang.String r7 = "if (SDK_INT >= 30) {\n                    // Buffer the source into memory.\n                    ImageDecoder.createSource(ByteBuffer.wrap(bufferedSource.use { it.readByteArray() }))\n                } else {\n                    // Work around https://issuetracker.google.com/issues/139371066 by copying the source to a temp file.\n                    tempFile = File.createTempFile(\"tmp\", null, context?.cacheDir?.apply { mkdirs() })\n                    bufferedSource.use { tempFile.sink().use(it::readAll) }\n                    ImageDecoder.createSource(tempFile)\n                }"
            kotlin.jvm.internal.k.d(r3, r7)     // Catch:{ all -> 0x0254 }
            r7 = r3
            r9 = 0
            coil.decode.j$d r12 = new coil.decode.j$d     // Catch:{ all -> 0x0254 }
            r12.<init>(r4, r10, r8, r11)     // Catch:{ all -> 0x0254 }
            android.graphics.drawable.Drawable r12 = android.graphics.ImageDecoder.decodeDrawable(r7, r12)     // Catch:{ all -> 0x0254 }
            java.lang.String r14 = "crossinline action: ImageDecoder.(info: ImageInfo, source: Source) -> Unit\n): Drawable {\n    return ImageDecoder.decodeDrawable(this) { decoder, info, source ->\n        decoder.action(info, source)\n    }"
            kotlin.jvm.internal.k.d(r12, r14)     // Catch:{ all -> 0x0254 }
            T r2 = r4.element     // Catch:{ all -> 0x02bd }
            java.io.File r2 = (java.io.File) r2     // Catch:{ all -> 0x02bd }
            if (r2 != 0) goto L_0x01b3
        L_0x01b2:
            goto L_0x01bb
        L_0x01b3:
            boolean r2 = r2.delete()     // Catch:{ all -> 0x02bd }
            kotlin.coroutines.jvm.internal.b.a(r2)     // Catch:{ all -> 0x02bd }
            goto L_0x01b2
        L_0x01bb:
            kotlin.o$a r2 = kotlin.o.Companion     // Catch:{ all -> 0x02bd }
            java.lang.Object r2 = kotlin.o.m17constructorimpl(r12)     // Catch:{ all -> 0x02bd }
            r6.resumeWith(r2)     // Catch:{ all -> 0x02bd }
            r13.a()     // Catch:{ Exception -> 0x02d0 }
            java.lang.Object r2 = r5.t()
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            if (r2 != r3) goto L_0x01d8
            kotlin.coroutines.jvm.internal.h.c(r1)
        L_0x01d8:
            if (r2 != r0) goto L_0x01db
            return r0
        L_0x01db:
            r5 = r27
            r3 = r28
            r4 = r11
        L_0x01e0:
            android.graphics.drawable.Drawable r2 = (android.graphics.drawable.Drawable) r2
            boolean r3 = r2 instanceof android.graphics.drawable.AnimatedImageDrawable
            if (r3 == 0) goto L_0x0249
            r3 = r2
            android.graphics.drawable.AnimatedImageDrawable r3 = (android.graphics.drawable.AnimatedImageDrawable) r3
            coil.request.l r5 = r8.i()
            java.lang.Integer r5 = coil.request.h.e(r5)
            if (r5 != 0) goto L_0x01f7
            r5 = -1
            goto L_0x01fb
        L_0x01f7:
            int r5 = r5.intValue()
        L_0x01fb:
            r3.setRepeatCount(r5)
            coil.request.l r3 = r8.i()
            kotlin.jvm.functions.a r3 = coil.request.h.c(r3)
            coil.request.l r5 = r8.i()
            kotlin.jvm.functions.a r5 = coil.request.h.b(r5)
            if (r3 != 0) goto L_0x0212
            if (r5 == 0) goto L_0x023a
        L_0x0212:
            kotlinx.coroutines.d1 r6 = kotlinx.coroutines.d1.a
            kotlinx.coroutines.k2 r6 = kotlinx.coroutines.d1.c()
            kotlinx.coroutines.k2 r6 = r6.W()
            coil.decode.j$c r7 = new coil.decode.j$c
            r9 = 0
            r7.<init>(r2, r3, r5, r9)
            r1.L$0 = r8
            r1.L$1 = r4
            r1.L$2 = r2
            r1.L$3 = r9
            r1.L$4 = r9
            r9 = 2
            r1.label = r9
            java.lang.Object r3 = kotlinx.coroutines.h.g(r6, r7, r1)
            if (r3 != r0) goto L_0x0236
            return r0
        L_0x0236:
            r0 = r2
            r5 = r8
        L_0x0238:
            r2 = r0
            r8 = r5
        L_0x023a:
            coil.drawable.a r0 = new coil.drawable.a
            coil.size.e r3 = r8.k()
            r0.<init>(r2, r3)
            r25 = r2
            r2 = r0
            r0 = r25
            goto L_0x024a
        L_0x0249:
            r0 = r2
        L_0x024a:
            coil.decode.c r3 = new coil.decode.c
            boolean r4 = r4.element
            r3.<init>(r2, r4)
            return r3
        L_0x0254:
            r0 = move-exception
            goto L_0x02ad
        L_0x0257:
            r0 = move-exception
            r9 = r0
            throw r9     // Catch:{ all -> 0x025a }
        L_0x025a:
            r0 = move-exception
            r12 = r0
            kotlin.io.b.a(r3, r9)     // Catch:{ all -> 0x0260 }
            throw r12     // Catch:{ all -> 0x0260 }
        L_0x0260:
            r0 = move-exception
            r3 = r0
            goto L_0x026b
        L_0x0263:
            r0 = move-exception
            r28 = r9
            r27 = r12
            r30 = r14
            r3 = r0
        L_0x026b:
            throw r3     // Catch:{ all -> 0x026c }
        L_0x026c:
            r0 = move-exception
            r7 = r0
            kotlin.io.b.a(r2, r3)     // Catch:{ all -> 0x0254 }
            throw r7     // Catch:{ all -> 0x0254 }
        L_0x0272:
            r0 = move-exception
            r28 = r9
            r27 = r12
            r30 = r14
            goto L_0x02ad
        L_0x027a:
            r0 = move-exception
            r4 = r28
            r28 = r9
            r27 = r12
            r30 = r14
            goto L_0x02ad
        L_0x0284:
            r0 = move-exception
            r21 = r4
            r27 = r12
            r30 = r14
            r4 = r28
            r28 = r9
            goto L_0x02ad
        L_0x0290:
            r0 = move-exception
            r21 = r4
            r20 = r7
            r27 = r12
            r30 = r14
            r4 = r28
            r28 = r9
            goto L_0x02ad
        L_0x029e:
            r0 = move-exception
            r19 = r3
            r21 = r4
            r20 = r7
            r27 = r12
            r30 = r14
            r4 = r28
            r28 = r9
        L_0x02ad:
            T r2 = r4.element     // Catch:{ all -> 0x02bd }
            java.io.File r2 = (java.io.File) r2     // Catch:{ all -> 0x02bd }
            if (r2 != 0) goto L_0x02b4
        L_0x02b3:
            goto L_0x02bc
        L_0x02b4:
            boolean r2 = r2.delete()     // Catch:{ all -> 0x02bd }
            kotlin.coroutines.jvm.internal.b.a(r2)     // Catch:{ all -> 0x02bd }
            goto L_0x02b3
        L_0x02bc:
            throw r0     // Catch:{ all -> 0x02bd }
        L_0x02bd:
            r0 = move-exception
            goto L_0x02cc
        L_0x02bf:
            r0 = move-exception
            r19 = r3
            r21 = r4
            r20 = r7
            r28 = r9
            r27 = r12
            r30 = r14
        L_0x02cc:
            r13.a()     // Catch:{ Exception -> 0x02d0 }
            throw r0     // Catch:{ Exception -> 0x02d0 }
        L_0x02d0:
            r0 = move-exception
            goto L_0x02df
        L_0x02d2:
            r0 = move-exception
            r19 = r3
            r21 = r4
            r20 = r7
            r28 = r9
            r27 = r12
            r30 = r14
        L_0x02df:
            boolean r2 = r0 instanceof java.lang.InterruptedException
            if (r2 != 0) goto L_0x02e9
            boolean r2 = r0 instanceof java.io.InterruptedIOException
            if (r2 == 0) goto L_0x02e8
            goto L_0x02e9
        L_0x02e8:
            throw r0
        L_0x02e9:
            java.util.concurrent.CancellationException r2 = new java.util.concurrent.CancellationException
            java.lang.String r3 = "Blocking call was interrupted due to parent cancellation."
            r2.<init>(r3)
            java.lang.Throwable r2 = r2.initCause(r0)
            java.lang.String r3 = "CancellationException(\"Blocking call was interrupted due to parent cancellation.\").initCause(exception)"
            kotlin.jvm.internal.k.d(r2, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.decode.j.a(coil.bitmap.c, okio.e, coil.size.Size, coil.decode.m, kotlin.coroutines.d):java.lang.Object");
    }

    @f(c = "coil.decode.ImageDecoderDecoder$decode$drawable$1", f = "ImageDecoderDecoder.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ImageDecoderDecoder.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ Drawable $baseDrawable;
        final /* synthetic */ kotlin.jvm.functions.a<x> $onEnd;
        final /* synthetic */ kotlin.jvm.functions.a<x> $onStart;
        int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(Drawable drawable, kotlin.jvm.functions.a<x> aVar, kotlin.jvm.functions.a<x> aVar2, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.$baseDrawable = drawable;
            this.$onStart = aVar;
            this.$onEnd = aVar2;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.$baseDrawable, this.$onStart, this.$onEnd, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    ((AnimatedImageDrawable) this.$baseDrawable).registerAnimationCallback(GifExtensions.a(this.$onStart, this.$onEnd));
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* compiled from: ImageDecoderDecoder.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
