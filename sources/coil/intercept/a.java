package coil.intercept;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.VisibleForTesting;
import coil.bitmap.e;
import coil.decode.f;
import coil.fetch.g;
import coil.intercept.b;
import coil.memory.MemoryCache;
import coil.memory.m;
import coil.memory.o;
import coil.memory.q;
import coil.memory.r;
import coil.request.i;
import coil.size.OriginalSize;
import coil.size.PixelSize;
import coil.size.Size;
import coil.util.h;
import coil.util.n;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EngineInterceptor.kt */
public final class a implements b {
    @NotNull
    public static final C0006a a = new C0006a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public final coil.b b;
    /* access modifiers changed from: private */
    @NotNull
    public final coil.bitmap.c c;
    /* access modifiers changed from: private */
    @NotNull
    public final e d;
    @NotNull
    private final r e;
    @NotNull
    private final m f;
    /* access modifiers changed from: private */
    @NotNull
    public final q g;
    /* access modifiers changed from: private */
    @NotNull
    public final n h;
    /* access modifiers changed from: private */
    @NotNull
    public final f i;
    /* access modifiers changed from: private */
    @Nullable
    public final coil.util.m j;

    @kotlin.coroutines.jvm.internal.f(c = "coil.intercept.EngineInterceptor", f = "EngineInterceptor.kt", l = {103}, m = "intercept")
    /* compiled from: EngineInterceptor.kt */
    public static final class b extends d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(a aVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a((b.a) null, this);
        }
    }

    public a(@NotNull coil.b registry, @NotNull coil.bitmap.c bitmapPool, @NotNull e referenceCounter, @NotNull r strongMemoryCache, @NotNull m memoryCacheService, @NotNull q requestService, @NotNull n systemCallbacks, @NotNull f drawableDecoder, @Nullable coil.util.m logger) {
        k.e(registry, "registry");
        k.e(bitmapPool, "bitmapPool");
        k.e(referenceCounter, "referenceCounter");
        k.e(strongMemoryCache, "strongMemoryCache");
        k.e(memoryCacheService, "memoryCacheService");
        k.e(requestService, "requestService");
        k.e(systemCallbacks, "systemCallbacks");
        k.e(drawableDecoder, "drawableDecoder");
        this.b = registry;
        this.c = bitmapPool;
        this.d = referenceCounter;
        this.e = strongMemoryCache;
        this.f = memoryCacheService;
        this.g = requestService;
        this.h = systemCallbacks;
        this.i = drawableDecoder;
        this.j = logger;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: coil.intercept.b$a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: coil.intercept.a} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x015b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull coil.intercept.b.a r28, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super coil.request.j> r29) {
        /*
            r27 = this;
            r0 = r29
            boolean r1 = r0 instanceof coil.intercept.a.b
            if (r1 == 0) goto L_0x0018
            r1 = r0
            coil.intercept.a$b r1 = (coil.intercept.a.b) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r27
            goto L_0x0020
        L_0x0018:
            coil.intercept.a$b r1 = new coil.intercept.a$b
            r2 = r27
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r1.label
            switch(r4) {
                case 0: goto L_0x004a;
                case 1: goto L_0x0033;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0033:
            java.lang.Object r0 = r1.L$1
            r4 = r0
            coil.intercept.b$a r4 = (coil.intercept.b.a) r4
            java.lang.Object r0 = r1.L$0
            r5 = r0
            coil.intercept.a r5 = (coil.intercept.a) r5
            kotlin.p.b(r3)     // Catch:{ all -> 0x0045 }
            r0 = r3
            r18 = r0
            goto L_0x0138
        L_0x0045:
            r0 = move-exception
            r18 = r3
            goto L_0x014c
        L_0x004a:
            kotlin.p.b(r3)
            r5 = r27
            r4 = r28
            boolean r6 = r4 instanceof coil.intercept.c     // Catch:{ all -> 0x0149 }
            if (r6 == 0) goto L_0x0139
            coil.request.i r6 = r4.getRequest()     // Catch:{ all -> 0x0149 }
            r15 = r6
            android.content.Context r6 = r15.l()     // Catch:{ all -> 0x0149 }
            r17 = r6
            java.lang.Object r6 = r15.m()     // Catch:{ all -> 0x0149 }
            r14 = r6
            coil.size.Size r6 = r4.getSize()     // Catch:{ all -> 0x0149 }
            r13 = r6
            r6 = r4
            coil.intercept.c r6 = (coil.intercept.c) r6     // Catch:{ all -> 0x0149 }
            coil.c r6 = r6.e()     // Catch:{ all -> 0x0149 }
            r12 = r6
            r12.h(r15, r14)     // Catch:{ all -> 0x0149 }
            coil.b r6 = r5.b     // Catch:{ all -> 0x0149 }
            java.lang.Object r6 = coil.util.d.a(r6, r14)     // Catch:{ all -> 0x0149 }
            r11 = r6
            r12.e(r15, r11)     // Catch:{ all -> 0x0149 }
            coil.fetch.g r6 = coil.util.h.a(r15, r11)     // Catch:{ all -> 0x0149 }
            if (r6 != 0) goto L_0x008c
            coil.b r6 = r5.b     // Catch:{ all -> 0x0045 }
            coil.fetch.g r6 = coil.util.d.c(r6, r11)     // Catch:{ all -> 0x0045 }
        L_0x008c:
            r10 = r6
            coil.memory.MemoryCache$Key r6 = r15.y()     // Catch:{ all -> 0x0149 }
            if (r6 != 0) goto L_0x0097
            coil.memory.MemoryCache$Key r6 = r5.l(r15, r11, r10, r13)     // Catch:{ all -> 0x0045 }
        L_0x0097:
            r9 = r6
            coil.request.c r6 = r15.z()     // Catch:{ all -> 0x0149 }
            boolean r6 = r6.getReadEnabled()     // Catch:{ all -> 0x0149 }
            if (r6 == 0) goto L_0x00a9
            coil.memory.m r6 = r5.f     // Catch:{ all -> 0x0045 }
            coil.memory.o$a r6 = r6.a(r9)     // Catch:{ all -> 0x0045 }
            goto L_0x00aa
        L_0x00a9:
            r6 = 0
        L_0x00aa:
            r8 = r6
            if (r8 == 0) goto L_0x00f9
            boolean r6 = r5.n(r9, r8, r15, r13)     // Catch:{ all -> 0x0149 }
            if (r6 == 0) goto L_0x00f9
            coil.request.m r0 = new coil.request.m     // Catch:{ all -> 0x0149 }
            android.graphics.Bitmap r6 = r8.b()     // Catch:{ all -> 0x0149 }
            r16 = 0
            android.content.res.Resources r7 = r17.getResources()     // Catch:{ all -> 0x0149 }
            r29 = r6
            java.lang.String r2 = "context.resources"
            kotlin.jvm.internal.k.d(r7, r2)     // Catch:{ all -> 0x0149 }
            r2 = r7
            r7 = 0
            r18 = r3
            android.graphics.drawable.BitmapDrawable r3 = new android.graphics.drawable.BitmapDrawable     // Catch:{ all -> 0x0147 }
            r19 = r6
            r6 = r29
            r3.<init>(r2, r6)     // Catch:{ all -> 0x0147 }
            coil.request.j$a r2 = new coil.request.j$a     // Catch:{ all -> 0x0147 }
            boolean r6 = r8.a()     // Catch:{ all -> 0x0147 }
            coil.decode.b r7 = coil.decode.b.MEMORY_CACHE     // Catch:{ all -> 0x0147 }
            r16 = r4
            coil.intercept.c r16 = (coil.intercept.c) r16     // Catch:{ all -> 0x0147 }
            android.graphics.Bitmap r16 = r16.d()     // Catch:{ all -> 0x0147 }
            if (r16 == 0) goto L_0x00ec
            r29 = r8
            r8 = 1
            goto L_0x00f2
        L_0x00ec:
            r16 = 0
            r29 = r8
            r8 = r16
        L_0x00f2:
            r2.<init>(r9, r6, r7, r8)     // Catch:{ all -> 0x0147 }
            r0.<init>(r3, r15, r2)     // Catch:{ all -> 0x0147 }
            return r0
        L_0x00f9:
            r18 = r3
            r29 = r8
            kotlinx.coroutines.i0 r2 = r15.r()     // Catch:{ all -> 0x0147 }
            coil.intercept.a$c r3 = new coil.intercept.a$c     // Catch:{ all -> 0x0147 }
            r16 = 0
            r6 = r3
            r8 = 1
            r7 = r5
            r19 = r29
            r29 = r0
            r0 = r8
            r8 = r15
            r20 = r9
            r9 = r19
            r21 = r10
            r10 = r11
            r22 = r11
            r11 = r21
            r23 = r12
            r12 = r4
            r24 = r13
            r25 = r14
            r14 = r23
            r26 = r15
            r15 = r20
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x0147 }
            r1.L$0 = r5     // Catch:{ all -> 0x0147 }
            r1.L$1 = r4     // Catch:{ all -> 0x0147 }
            r1.label = r0     // Catch:{ all -> 0x0147 }
            java.lang.Object r0 = kotlinx.coroutines.h.g(r2, r3, r1)     // Catch:{ all -> 0x0147 }
            r2 = r29
            if (r0 != r2) goto L_0x0138
            return r2
        L_0x0138:
            return r0
        L_0x0139:
            r18 = r3
            java.lang.String r0 = "Check failed."
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0147 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0147 }
            r2.<init>(r0)     // Catch:{ all -> 0x0147 }
            throw r2     // Catch:{ all -> 0x0147 }
        L_0x0147:
            r0 = move-exception
            goto L_0x014c
        L_0x0149:
            r0 = move-exception
            r18 = r3
        L_0x014c:
            boolean r2 = r0 instanceof java.util.concurrent.CancellationException
            if (r2 != 0) goto L_0x015b
            coil.memory.q r2 = r5.g
            coil.request.i r3 = r4.getRequest()
            coil.request.g r2 = r2.a(r3, r0)
            return r2
        L_0x015b:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.intercept.a.a(coil.intercept.b$a, kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "coil.intercept.EngineInterceptor$intercept$2", f = "EngineInterceptor.kt", l = {405, 428, 487}, m = "invokeSuspend")
    /* compiled from: EngineInterceptor.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super coil.request.m>, Object> {
        final /* synthetic */ b.a $chain;
        final /* synthetic */ coil.c $eventListener;
        final /* synthetic */ g<Object> $fetcher;
        final /* synthetic */ Object $mappedData;
        final /* synthetic */ MemoryCache.Key $memoryCacheKey;
        final /* synthetic */ i $request;
        final /* synthetic */ Size $size;
        final /* synthetic */ o.a $value;
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(a aVar, i iVar, o.a aVar2, Object obj, g<Object> gVar, b.a aVar3, Size size, coil.c cVar, MemoryCache.Key key, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$request = iVar;
            this.$value = aVar2;
            this.$mappedData = obj;
            this.$fetcher = gVar;
            this.$chain = aVar3;
            this.$size = size;
            this.$eventListener = cVar;
            this.$memoryCacheKey = key;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, this.$request, this.$value, this.$mappedData, this.$fetcher, this.$chain, this.$size, this.$eventListener, this.$memoryCacheKey, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super coil.request.m> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v21, resolved type: coil.fetch.e} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v16, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v29, resolved type: coil.decode.m} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v17, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v22, resolved type: coil.c} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v18, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v46, resolved type: coil.size.Size} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v19, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v19, resolved type: coil.request.i} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v24, resolved type: coil.intercept.a} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v24, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v50, resolved type: coil.fetch.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v25, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v33, resolved type: coil.decode.m} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v26, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v33, resolved type: coil.c} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v27, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v26, resolved type: coil.size.Size} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v28, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v22, resolved type: coil.request.i} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v29, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v28, resolved type: coil.intercept.a} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v32, resolved type: java.util.List<coil.transform.d>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v54, resolved type: android.graphics.drawable.BitmapDrawable} */
        /* JADX WARNING: type inference failed for: r4v2, types: [android.graphics.drawable.BitmapDrawable] */
        /* JADX WARNING: type inference failed for: r4v6 */
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:100:0x0407, code lost:
            r16 = true;
            r0 = null;
            r7 = r29;
            r18 = r17;
            r1 = r22;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:101:0x0413, code lost:
            r2 = r5;
            kotlin.jvm.internal.k.d(r2, "output");
            r11.m(r3, r2);
            r8 = r3.l().getResources();
            kotlin.jvm.internal.k.d(r8, "context.resources");
            r17 = coil.fetch.e.e(r18, new android.graphics.drawable.BitmapDrawable(r8, r2), false, (coil.decode.b) null, 6, (java.lang.Object) null);
            r3 = r0;
            r2 = r7;
            r7 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:102:0x0446, code lost:
            r29 = r2;
            r16 = true;
            r1 = coil.intercept.a.d(r18);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:103:0x044e, code lost:
            if (r1 != null) goto L_0x0452;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:104:0x0450, code lost:
            r7 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:106:0x045a, code lost:
            if (r1.b() > 4) goto L_0x0474;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:107:0x045c, code lost:
            r7 = null;
            r1.a("EngineInterceptor", 4, kotlin.jvm.internal.k.l("allowConversionToBitmap=false, skipping transformations for type ", r17.f().getClass().getCanonicalName()), (java.lang.Throwable) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:108:0x0474, code lost:
            r7 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:109:0x0475, code lost:
            r1 = kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:110:0x0478, code lost:
            r3 = r7;
            r2 = r29;
            r1 = r22;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:111:0x047d, code lost:
            r0 = r17;
            r4 = r0.f();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:112:0x0485, code lost:
            if ((r4 instanceof android.graphics.drawable.BitmapDrawable) == false) goto L_0x048a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:113:0x0487, code lost:
            r4 = (android.graphics.drawable.BitmapDrawable) r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:114:0x048a, code lost:
            r4 = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:115:0x048b, code lost:
            if (r4 != 0) goto L_0x048e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x048e, code lost:
            r4 = r4.getBitmap();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x0492, code lost:
            if (r4 != null) goto L_0x0495;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:0x0495, code lost:
            r4.prepareToDraw();
            r4 = kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x049a, code lost:
            r2 = r0.a();
            r4 = r0.b();
            r0 = r0.c();
            coil.intercept.a.j(r1.this$0, r2);
            r5 = coil.intercept.a.k(r1.this$0, r1.$request, r1.$memoryCacheKey, r2, r4);
            r6 = r1.$request;
            r8 = r1.$memoryCacheKey;
            r9 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x04cb, code lost:
            if (kotlin.coroutines.jvm.internal.b.a(r5).booleanValue() == false) goto L_0x04ce;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:121:0x04cd, code lost:
            r7 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x04d8, code lost:
            if (((coil.intercept.c) r1.$chain).d() == null) goto L_0x04dd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:124:0x04da, code lost:
            r8 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:125:0x04dd, code lost:
            r8 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:127:0x04e8, code lost:
            return new coil.request.m(r2, r6, new coil.request.j.a(r7, r4, r0, r8));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:129:0x04ee, code lost:
            throw new kotlin.NoWhenBranchMatchedException();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x014d, code lost:
            r10 = (coil.fetch.f) r10;
            r6.j(r3, r12, r7, r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0155, code lost:
            if ((r10 instanceof coil.fetch.m) == false) goto L_0x021b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            kotlinx.coroutines.c2.i(r1.getContext());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x015f, code lost:
            if (r4 != 0) goto L_0x0179;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0165, code lost:
            if (r3.I() != null) goto L_0x0179;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x016f, code lost:
            if (r3.z().getWriteEnabled() != false) goto L_0x0179;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0171, code lost:
            r13 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0173, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0174, code lost:
            r8 = r9;
            r4 = r10;
            r10 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0179, code lost:
            r13 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x017b, code lost:
            if (r13 == false) goto L_0x0182;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x017d, code lost:
            r14 = coil.decode.g.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x017f, code lost:
            r29 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
            r14 = r3.n();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0186, code lost:
            if (r14 != null) goto L_0x01a7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x019c, code lost:
            r29 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
            r14 = coil.util.d.b(coil.intercept.a.f(r11), r3.m(), ((coil.fetch.m) r10).c(), ((coil.fetch.m) r10).b());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x01a7, code lost:
            r29 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x01a9, code lost:
            r2 = r14;
            r6.n(r3, r2, r7);
            r19 = coil.intercept.a.b(r11);
            r20 = ((coil.fetch.m) r10).c();
            r1.L$0 = r11;
            r1.L$1 = r3;
            r1.L$2 = r5;
            r1.L$3 = r6;
            r1.L$4 = r7;
            r1.L$5 = r10;
            r1.L$6 = r2;
            r1.label = 2;
            r8 = r2.a(r19, r20, r5, r7, r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x01d5, code lost:
            if (r8 != r0) goto L_0x01d8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x01d7, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x01d8, code lost:
            r4 = r10;
            r10 = r2;
            r2 = r29;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
            r8 = (coil.decode.c) r8;
            r6.i(r3, r10, r7, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x01e1, code lost:
            r14 = r4;
            r12 = r7;
            r13 = r9;
            r4 = new coil.fetch.e(r8.a(), r8.b(), ((coil.fetch.m) r4).a());
            r15 = r11;
            r10 = r5;
            r11 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x01ff, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0200, code lost:
            r8 = r9;
            r10 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0203, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0204, code lost:
            r2 = r29;
            r8 = r9;
            r4 = r10;
            r10 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x020a, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x020b, code lost:
            r29 = r2;
            r8 = r9;
            r4 = r10;
            r10 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x021b, code lost:
            r29 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x021f, code lost:
            if ((r10 instanceof coil.fetch.e) == 0) goto L_0x04e9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x0221, code lost:
            r4 = (coil.fetch.e) r10;
            r12 = r7;
            r13 = r9;
            r14 = r10;
            r15 = r11;
            r2 = r29;
            r10 = r5;
            r11 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x022d, code lost:
            r17 = r4;
            kotlinx.coroutines.c2.i(r1.getContext());
            r18 = r15;
            r9 = r3.J();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x0242, code lost:
            if (r9.isEmpty() == false) goto L_0x024b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x0244, code lost:
            r7 = null;
            r16 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x0253, code lost:
            if ((r17.f() instanceof android.graphics.drawable.BitmapDrawable) == false) goto L_0x02dd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x0255, code lost:
            r8 = ((android.graphics.drawable.BitmapDrawable) r17.f()).getBitmap();
            r4 = coil.memory.q.b;
            kotlin.jvm.internal.k.d(r8, "resultBitmap");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:0x026f, code lost:
            if (kotlin.collections.l.r(r4, coil.util.c.c(r8)) == false) goto L_0x027a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:0x0271, code lost:
            r21 = r0;
            r22 = r1;
            r29 = r2;
            r0 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:0x027a, code lost:
            r4 = coil.intercept.a.d(r18);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:0x027e, code lost:
            if (r4 != null) goto L_0x0285;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x0280, code lost:
            r21 = r0;
            r22 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:0x0285, code lost:
            r21 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:0x0290, code lost:
            if (r4.b() > 4) goto L_0x02b7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:0x0292, code lost:
            r0 = new java.lang.StringBuilder();
            r22 = r1;
            r0.append("Converting bitmap with config ");
            r0.append(coil.util.c.c(r8));
            r0.append(" to apply transformations: ");
            r0.append(r9);
            r4.a("EngineInterceptor", 4, r0.toString(), (java.lang.Throwable) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:75:0x02b7, code lost:
            r22 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:76:0x02b9, code lost:
            r0 = kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:0x02bc, code lost:
            r20 = r8;
            r0 = r9;
            r8 = coil.intercept.a.c(r18).a(r17.f(), r12.d(), r10, r12.k(), r12.a());
            r29 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x02dd, code lost:
            r21 = r0;
            r22 = r1;
            r0 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x02e6, code lost:
            if (r3.g() == false) goto L_0x0446;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:0x02e8, code lost:
            r1 = coil.intercept.a.d(r18);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:81:0x02ec, code lost:
            if (r1 != null) goto L_0x02f1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:82:0x02ee, code lost:
            r29 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:84:0x02f9, code lost:
            if (r1.b() > 4) goto L_0x0326;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:85:0x02fb, code lost:
            r9 = new java.lang.StringBuilder();
            r29 = r2;
            r9.append("Converting drawable of type ");
            r9.append(r17.f().getClass().getCanonicalName());
            r9.append(" to apply transformations: ");
            r9.append(r0);
            r1.a("EngineInterceptor", 4, r9.toString(), (java.lang.Throwable) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:86:0x0326, code lost:
            r29 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:87:0x0328, code lost:
            r1 = kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:88:0x032b, code lost:
            r8 = coil.intercept.a.c(r18).a(r17.f(), r12.d(), r10, r12.k(), r12.a());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:0x0344, code lost:
            r1 = r8;
            kotlin.jvm.internal.k.d(r1, "input");
            r11.k(r3, r1);
            r2 = r0;
            r5 = r1;
            r6 = r2.size() - 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:90:0x0356, code lost:
            if (r6 < 0) goto L_0x0407;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:91:0x0358, code lost:
            r7 = r29;
            r20 = r0;
            r9 = r13;
            r0 = r21;
            r8 = 0;
            r13 = r6;
            r6 = 0;
            r19 = r15;
            r15 = r17;
            r17 = r14;
            r14 = r1;
            r1 = r3;
            r3 = r18;
            r18 = r12;
            r12 = r5;
            r5 = r10;
            r10 = r2;
            r2 = r22;
            r27 = r11;
            r11 = false;
            r4 = r27;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:92:0x0379, code lost:
            r29 = r8;
            r16 = true;
            r8 = r8 + 1;
            r21 = r6;
            r6 = r29;
            r23 = r6;
            r24 = r7;
            r7 = coil.intercept.a.b(r3);
            r25 = r9;
            r26 = r11;
            r11 = r12;
            kotlin.jvm.internal.k.d(r11, "bitmap");
            r2.L$0 = r1;
            r2.L$1 = r5;
            r2.L$2 = r4;
            r2.L$3 = r3;
            r2.L$4 = r15;
            r2.L$5 = r10;
            r2.L$6 = null;
            r2.I$0 = r8;
            r2.I$1 = r13;
            r2.label = 3;
            r9 = r10.get(r6).a(r7, r11, r5, r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:93:0x03bc, code lost:
            if (r9 != r0) goto L_0x03bf;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:94:0x03be, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:95:0x03bf, code lost:
            r6 = r21;
            r7 = r24;
            r11 = r26;
            r21 = r20;
            r20 = r19;
            r19 = r18;
            r18 = r17;
            r17 = r14;
            r14 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:96:0x03d2, code lost:
            r22 = (android.graphics.Bitmap) r9;
            kotlinx.coroutines.c2.i(r2.getContext());
            r12 = (android.graphics.Bitmap) r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:97:0x03e2, code lost:
            if (r14 <= r13) goto L_0x03f4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:98:0x03e4, code lost:
            r3 = r1;
            r1 = r2;
            r2 = r10;
            r5 = r12;
            r18 = r15;
            r0 = r22;
            r13 = r25;
            r27 = r11;
            r11 = r4;
            r4 = r27;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:0x03f4, code lost:
            r8 = r17;
            r17 = r18;
            r18 = r19;
            r19 = r20;
            r20 = r21;
            r9 = r25;
            r27 = r14;
            r14 = r8;
            r8 = r27;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r29) {
            /*
                r28 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                r1 = r28
                int r2 = r1.label
                r4 = 0
                r5 = 0
                switch(r2) {
                    case 0: goto L_0x00cc;
                    case 1: goto L_0x008f;
                    case 2: goto L_0x0053;
                    case 3: goto L_0x0015;
                    default: goto L_0x000d;
                }
            L_0x000d:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0015:
                r2 = r28
                r6 = r4
                r7 = r4
                r8 = r4
                r9 = r29
                r10 = r5
                r11 = r4
                r12 = r5
                r7 = 0
                r6 = 0
                r11 = 0
                r8 = 0
                int r13 = r2.I$1
                int r14 = r2.I$0
                java.lang.Object r15 = r2.L$5
                r10 = r15
                java.util.List r10 = (java.util.List) r10
                java.lang.Object r15 = r2.L$4
                coil.fetch.e r15 = (coil.fetch.e) r15
                java.lang.Object r3 = r2.L$3
                coil.intercept.a r3 = (coil.intercept.a) r3
                java.lang.Object r4 = r2.L$2
                coil.c r4 = (coil.c) r4
                java.lang.Object r5 = r2.L$1
                coil.size.Size r5 = (coil.size.Size) r5
                java.lang.Object r1 = r2.L$0
                coil.request.i r1 = (coil.request.i) r1
                kotlin.p.b(r9)
                r25 = r9
                r16 = 1
                r17 = 0
                r18 = 0
                r19 = 0
                r20 = 0
                r21 = 0
                goto L_0x03d2
            L_0x0053:
                r1 = r28
                r2 = 0
                r3 = r2
                r4 = r2
                r5 = r2
                r6 = r2
                r7 = r2
                r8 = 0
                r9 = r8
                r8 = r29
                r10 = r2
                r11 = r2
                r2 = 0
                java.lang.Object r9 = r1.L$6
                coil.decode.e r9 = (coil.decode.e) r9
                java.lang.Object r11 = r1.L$5
                r4 = r11
                coil.fetch.f r4 = (coil.fetch.f) r4
                java.lang.Object r11 = r1.L$4
                r7 = r11
                coil.decode.m r7 = (coil.decode.m) r7
                java.lang.Object r11 = r1.L$3
                r6 = r11
                coil.c r6 = (coil.c) r6
                java.lang.Object r11 = r1.L$2
                r5 = r11
                coil.size.Size r5 = (coil.size.Size) r5
                java.lang.Object r11 = r1.L$1
                r3 = r11
                coil.request.i r3 = (coil.request.i) r3
                java.lang.Object r11 = r1.L$0
                r10 = r11
                coil.intercept.a r10 = (coil.intercept.a) r10
                kotlin.p.b(r8)     // Catch:{ all -> 0x008c }
                r11 = r10
                r10 = r9
                r9 = r8
                goto L_0x01dc
            L_0x008c:
                r0 = move-exception
                goto L_0x0210
            L_0x008f:
                r1 = r28
                r2 = 0
                r3 = r2
                r4 = r2
                r5 = r2
                r6 = r2
                r8 = 0
                r7 = r8
                r9 = r29
                r10 = r2
                r11 = r8
                r12 = r2
                r2 = 0
                int r7 = r1.I$0
                java.lang.Object r11 = r1.L$5
                r6 = r11
                coil.decode.m r6 = (coil.decode.m) r6
                java.lang.Object r11 = r1.L$4
                r5 = r11
                coil.c r5 = (coil.c) r5
                java.lang.Object r11 = r1.L$3
                r4 = r11
                coil.size.Size r4 = (coil.size.Size) r4
                java.lang.Object r11 = r1.L$2
                r3 = r11
                coil.request.i r3 = (coil.request.i) r3
                java.lang.Object r11 = r1.L$1
                coil.fetch.g r11 = (coil.fetch.g) r11
                java.lang.Object r12 = r1.L$0
                r10 = r12
                coil.intercept.a r10 = (coil.intercept.a) r10
                kotlin.p.b(r9)
                r12 = r11
                r11 = r10
                r10 = r9
                r27 = r5
                r5 = r4
                r4 = r7
                r7 = r6
                r6 = r27
                goto L_0x014d
            L_0x00cc:
                r8 = r4
                kotlin.p.b(r29)
                r1 = r28
                r2 = r29
                coil.intercept.a r3 = r1.this$0
                coil.request.i r4 = r1.$request
                java.lang.Object r4 = r4.m()
                r3.m(r4)
                coil.memory.o$a r3 = r1.$value
                if (r3 == 0) goto L_0x00f2
                coil.intercept.a r3 = r1.this$0
                coil.bitmap.e r3 = r3.d
                coil.memory.o$a r4 = r1.$value
                android.graphics.Bitmap r4 = r4.b()
                r3.b(r4)
            L_0x00f2:
                coil.intercept.a r3 = r1.this$0
                java.lang.Object r4 = r1.$mappedData
                coil.fetch.g<java.lang.Object> r11 = r1.$fetcher
                coil.request.i r5 = r1.$request
                coil.intercept.b$a r6 = r1.$chain
                coil.intercept.c r6 = (coil.intercept.c) r6
                int r7 = r6.h()
                coil.size.Size r6 = r1.$size
                coil.c r9 = r1.$eventListener
                r10 = 0
                coil.memory.q r12 = r3.g
                coil.util.n r13 = r3.h
                boolean r13 = r13.b()
                coil.decode.m r12 = r12.e(r5, r6, r13)
                r9.f(r5, r11, r12)
                coil.bitmap.c r19 = r3.c
                r1.L$0 = r3
                r1.L$1 = r11
                r1.L$2 = r5
                r1.L$3 = r6
                r1.L$4 = r9
                r1.L$5 = r12
                r1.I$0 = r7
                r13 = 1
                r1.label = r13
                r18 = r11
                r20 = r4
                r21 = r6
                r22 = r12
                r23 = r1
                java.lang.Object r4 = r18.b(r19, r20, r21, r22, r23)
                if (r4 != r0) goto L_0x0140
                return r0
            L_0x0140:
                r27 = r9
                r9 = r2
                r2 = r10
                r10 = r4
                r4 = r7
                r7 = r12
                r12 = r11
                r11 = r3
                r3 = r5
                r5 = r6
                r6 = r27
            L_0x014d:
                coil.fetch.f r10 = (coil.fetch.f) r10
                r6.j(r3, r12, r7, r10)
                boolean r13 = r10 instanceof coil.fetch.m
                if (r13 == 0) goto L_0x021b
                kotlin.coroutines.g r13 = r1.getContext()     // Catch:{ all -> 0x020a }
                kotlinx.coroutines.c2.i(r13)     // Catch:{ all -> 0x020a }
                if (r4 != 0) goto L_0x0179
                coil.target.b r13 = r3.I()     // Catch:{ all -> 0x0173 }
                if (r13 != 0) goto L_0x0179
                coil.request.c r13 = r3.z()     // Catch:{ all -> 0x0173 }
                boolean r13 = r13.getWriteEnabled()     // Catch:{ all -> 0x0173 }
                if (r13 != 0) goto L_0x0179
                r13 = 1
                goto L_0x017a
            L_0x0173:
                r0 = move-exception
                r8 = r9
                r4 = r10
                r10 = r11
                goto L_0x0210
            L_0x0179:
                r13 = r8
            L_0x017a:
                if (r13 == 0) goto L_0x0182
                coil.decode.g r14 = coil.decode.g.a     // Catch:{ all -> 0x0173 }
                r29 = r2
                goto L_0x01a9
            L_0x0182:
                coil.decode.e r14 = r3.n()     // Catch:{ all -> 0x020a }
                if (r14 != 0) goto L_0x01a7
                coil.b r14 = r11.b     // Catch:{ all -> 0x020a }
                java.lang.Object r15 = r3.m()     // Catch:{ all -> 0x020a }
                r17 = r10
                coil.fetch.m r17 = (coil.fetch.m) r17     // Catch:{ all -> 0x020a }
                okio.e r8 = r17.c()     // Catch:{ all -> 0x020a }
                r17 = r10
                coil.fetch.m r17 = (coil.fetch.m) r17     // Catch:{ all -> 0x020a }
                r29 = r2
                java.lang.String r2 = r17.b()     // Catch:{ all -> 0x0203 }
                coil.decode.e r14 = coil.util.d.b(r14, r15, r8, r2)     // Catch:{ all -> 0x0203 }
                goto L_0x01a9
            L_0x01a7:
                r29 = r2
            L_0x01a9:
                r2 = r14
                r6.n(r3, r2, r7)     // Catch:{ all -> 0x0203 }
                coil.bitmap.c r19 = r11.c     // Catch:{ all -> 0x0203 }
                r8 = r10
                coil.fetch.m r8 = (coil.fetch.m) r8     // Catch:{ all -> 0x0203 }
                okio.e r20 = r8.c()     // Catch:{ all -> 0x0203 }
                r1.L$0 = r11     // Catch:{ all -> 0x0203 }
                r1.L$1 = r3     // Catch:{ all -> 0x0203 }
                r1.L$2 = r5     // Catch:{ all -> 0x0203 }
                r1.L$3 = r6     // Catch:{ all -> 0x0203 }
                r1.L$4 = r7     // Catch:{ all -> 0x0203 }
                r1.L$5 = r10     // Catch:{ all -> 0x0203 }
                r1.L$6 = r2     // Catch:{ all -> 0x0203 }
                r8 = 2
                r1.label = r8     // Catch:{ all -> 0x0203 }
                r18 = r2
                r21 = r5
                r22 = r7
                r23 = r1
                java.lang.Object r8 = r18.a(r19, r20, r21, r22, r23)     // Catch:{ all -> 0x0203 }
                if (r8 != r0) goto L_0x01d8
                return r0
            L_0x01d8:
                r4 = r10
                r10 = r2
                r2 = r29
            L_0x01dc:
                coil.decode.c r8 = (coil.decode.c) r8     // Catch:{ all -> 0x01ff }
                r6.i(r3, r10, r7, r8)     // Catch:{ all -> 0x01ff }
                coil.fetch.e r10 = new coil.fetch.e
                android.graphics.drawable.Drawable r12 = r8.a()
                boolean r13 = r8.b()
                r14 = r4
                coil.fetch.m r14 = (coil.fetch.m) r14
                coil.decode.b r14 = r14.a()
                r10.<init>(r12, r13, r14)
                r14 = r4
                r12 = r7
                r13 = r9
                r4 = r10
                r15 = r11
                r10 = r5
                r11 = r6
                goto L_0x022d
            L_0x01ff:
                r0 = move-exception
                r8 = r9
                r10 = r11
                goto L_0x0210
            L_0x0203:
                r0 = move-exception
                r2 = r29
                r8 = r9
                r4 = r10
                r10 = r11
                goto L_0x0210
            L_0x020a:
                r0 = move-exception
                r29 = r2
                r8 = r9
                r4 = r10
                r10 = r11
            L_0x0210:
                r9 = r4
                coil.fetch.m r9 = (coil.fetch.m) r9
                okio.e r9 = r9.c()
                coil.util.f.a(r9)
                throw r0
            L_0x021b:
                r29 = r2
                boolean r2 = r10 instanceof coil.fetch.e
                if (r2 == 0) goto L_0x04e9
                r2 = r10
                coil.fetch.e r2 = (coil.fetch.e) r2
                r4 = r2
                r12 = r7
                r13 = r9
                r14 = r10
                r15 = r11
                r2 = r29
                r10 = r5
                r11 = r6
            L_0x022d:
                r17 = r4
                kotlin.coroutines.g r4 = r1.getContext()
                kotlinx.coroutines.c2.i(r4)
                r18 = r15
                r19 = 0
                java.util.List r9 = r3.J()
                boolean r4 = r9.isEmpty()
                if (r4 == 0) goto L_0x024b
                r0 = 0
                r3 = r0
                r7 = r0
                r16 = 1
                goto L_0x047d
            L_0x024b:
                android.graphics.drawable.Drawable r4 = r17.f()
                boolean r4 = r4 instanceof android.graphics.drawable.BitmapDrawable
                java.lang.String r5 = " to apply transformations: "
                if (r4 == 0) goto L_0x02dd
                android.graphics.drawable.Drawable r4 = r17.f()
                android.graphics.drawable.BitmapDrawable r4 = (android.graphics.drawable.BitmapDrawable) r4
                android.graphics.Bitmap r8 = r4.getBitmap()
                android.graphics.Bitmap$Config[] r4 = coil.memory.q.b
                java.lang.String r6 = "resultBitmap"
                kotlin.jvm.internal.k.d(r8, r6)
                android.graphics.Bitmap$Config r6 = coil.util.c.c(r8)
                boolean r4 = kotlin.collections.l.r(r4, r6)
                if (r4 == 0) goto L_0x027a
                r21 = r0
                r22 = r1
                r29 = r2
                r0 = r9
                goto L_0x0344
            L_0x027a:
                coil.util.m r4 = r18.j
                if (r4 != 0) goto L_0x0285
                r21 = r0
                r22 = r1
                goto L_0x02bc
            L_0x0285:
                java.lang.String r6 = "EngineInterceptor"
                r7 = 4
                r20 = 0
                r21 = r0
                int r0 = r4.b()
                if (r0 > r7) goto L_0x02b7
                r0 = 0
                r29 = r0
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                r22 = r1
                java.lang.String r1 = "Converting bitmap with config "
                r0.append(r1)
                android.graphics.Bitmap$Config r1 = coil.util.c.c(r8)
                r0.append(r1)
                r0.append(r5)
                r0.append(r9)
                java.lang.String r0 = r0.toString()
                r1 = 0
                r4.a(r6, r7, r0, r1)
                goto L_0x02b9
            L_0x02b7:
                r22 = r1
            L_0x02b9:
                kotlin.x r0 = kotlin.x.a
            L_0x02bc:
                coil.decode.f r4 = r18.i
                android.graphics.drawable.Drawable r5 = r17.f()
                android.graphics.Bitmap$Config r6 = r12.d()
                coil.size.e r0 = r12.k()
                boolean r1 = r12.a()
                r7 = r10
                r20 = r8
                r8 = r0
                r0 = r9
                r9 = r1
                android.graphics.Bitmap r8 = r4.a(r5, r6, r7, r8, r9)
                r29 = r2
                goto L_0x0344
            L_0x02dd:
                r21 = r0
                r22 = r1
                r0 = r9
                boolean r1 = r3.g()
                if (r1 == 0) goto L_0x0446
                coil.util.m r1 = r18.j
                if (r1 != 0) goto L_0x02f1
                r29 = r2
                goto L_0x032b
            L_0x02f1:
                java.lang.String r4 = "EngineInterceptor"
                r6 = 4
                r7 = 0
                int r8 = r1.b()
                if (r8 > r6) goto L_0x0326
                r8 = 0
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                r29 = r2
                java.lang.String r2 = "Converting drawable of type "
                r9.append(r2)
                android.graphics.drawable.Drawable r2 = r17.f()
                java.lang.Class r2 = r2.getClass()
                java.lang.String r2 = r2.getCanonicalName()
                r9.append(r2)
                r9.append(r5)
                r9.append(r0)
                java.lang.String r2 = r9.toString()
                r5 = 0
                r1.a(r4, r6, r2, r5)
                goto L_0x0328
            L_0x0326:
                r29 = r2
            L_0x0328:
                kotlin.x r1 = kotlin.x.a
            L_0x032b:
                coil.decode.f r4 = r18.i
                android.graphics.drawable.Drawable r5 = r17.f()
                android.graphics.Bitmap$Config r6 = r12.d()
                coil.size.e r8 = r12.k()
                boolean r9 = r12.a()
                r7 = r10
                android.graphics.Bitmap r8 = r4.a(r5, r6, r7, r8, r9)
            L_0x0344:
                r1 = r8
                java.lang.String r2 = "input"
                kotlin.jvm.internal.k.d(r1, r2)
                r11.k(r3, r1)
                r2 = r0
                r4 = 0
                r5 = r1
                int r6 = r2.size()
                int r6 = r6 + -1
                if (r6 < 0) goto L_0x0407
                r7 = r29
                r20 = r0
                r9 = r13
                r0 = r21
                r8 = 0
                r13 = r6
                r6 = r19
                r19 = r15
                r15 = r17
                r17 = r14
                r14 = r1
                r1 = r3
                r3 = r18
                r18 = r12
                r12 = r5
                r5 = r10
                r10 = r2
                r2 = r22
                r27 = r11
                r11 = r4
                r4 = r27
            L_0x0379:
                r29 = r8
                r16 = 1
                int r8 = r8 + 1
                r21 = r6
                r6 = r29
                java.lang.Object r22 = r10.get(r6)
                r23 = r6
                r6 = r22
                coil.transform.d r6 = (coil.transform.d) r6
                r29 = r12
                r22 = 0
                r24 = r7
                coil.bitmap.c r7 = r3.c
                r25 = r9
                java.lang.String r9 = "bitmap"
                r26 = r11
                r11 = r29
                kotlin.jvm.internal.k.d(r11, r9)
                r2.L$0 = r1
                r2.L$1 = r5
                r2.L$2 = r4
                r2.L$3 = r3
                r2.L$4 = r15
                r2.L$5 = r10
                r9 = 0
                r2.L$6 = r9
                r2.I$0 = r8
                r2.I$1 = r13
                r9 = 3
                r2.label = r9
                java.lang.Object r9 = r6.a(r7, r11, r5, r2)
                if (r9 != r0) goto L_0x03bf
                return r0
            L_0x03bf:
                r6 = r21
                r7 = r24
                r11 = r26
                r21 = r20
                r20 = r19
                r19 = r18
                r18 = r17
                r17 = r14
                r14 = r8
                r8 = r22
            L_0x03d2:
                r22 = r9
                android.graphics.Bitmap r22 = (android.graphics.Bitmap) r22
                r23 = 0
                kotlin.coroutines.g r24 = r2.getContext()
                kotlinx.coroutines.c2.i(r24)
                r12 = r9
                android.graphics.Bitmap r12 = (android.graphics.Bitmap) r12
                if (r14 <= r13) goto L_0x03f4
                r3 = r1
                r1 = r2
                r2 = r10
                r5 = r12
                r18 = r15
                r0 = r22
                r13 = r25
                r27 = r11
                r11 = r4
                r4 = r27
                goto L_0x0413
            L_0x03f4:
                r8 = r17
                r17 = r18
                r18 = r19
                r19 = r20
                r20 = r21
                r9 = r25
                r27 = r14
                r14 = r8
                r8 = r27
                goto L_0x0379
            L_0x0407:
                r16 = 1
                r6 = 0
                r0 = r6
                r7 = r29
                r18 = r17
                r6 = r19
                r1 = r22
            L_0x0413:
                r2 = r5
                java.lang.String r4 = "output"
                kotlin.jvm.internal.k.d(r2, r4)
                r11.m(r3, r2)
                android.content.Context r3 = r3.l()
                r4 = r2
                r5 = 0
                android.content.res.Resources r8 = r3.getResources()
                r9 = r4
                java.lang.String r10 = "context.resources"
                kotlin.jvm.internal.k.d(r8, r10)
                r10 = 0
                android.graphics.drawable.BitmapDrawable r11 = new android.graphics.drawable.BitmapDrawable
                r11.<init>(r8, r9)
                r20 = 0
                r21 = 0
                r22 = 6
                r23 = 0
                r19 = r11
                coil.fetch.e r17 = coil.fetch.e.e(r18, r19, r20, r21, r22, r23)
                r3 = r0
                r2 = r7
                r7 = 0
                goto L_0x047d
            L_0x0446:
                r29 = r2
                r16 = 1
                coil.util.m r1 = r18.j
                if (r1 != 0) goto L_0x0452
                r7 = 0
                goto L_0x0478
            L_0x0452:
                java.lang.String r2 = "EngineInterceptor"
                r4 = 4
                r5 = 0
                int r6 = r1.b()
                if (r6 > r4) goto L_0x0474
                r6 = 0
                android.graphics.drawable.Drawable r7 = r17.f()
                java.lang.Class r7 = r7.getClass()
                java.lang.String r7 = r7.getCanonicalName()
                java.lang.String r8 = "allowConversionToBitmap=false, skipping transformations for type "
                java.lang.String r6 = kotlin.jvm.internal.k.l(r8, r7)
                r7 = 0
                r1.a(r2, r4, r6, r7)
                goto L_0x0475
            L_0x0474:
                r7 = 0
            L_0x0475:
                kotlin.x r1 = kotlin.x.a
            L_0x0478:
                r3 = r7
                r2 = r29
                r1 = r22
            L_0x047d:
                r0 = r17
                android.graphics.drawable.Drawable r4 = r0.f()
                boolean r5 = r4 instanceof android.graphics.drawable.BitmapDrawable
                if (r5 == 0) goto L_0x048a
                android.graphics.drawable.BitmapDrawable r4 = (android.graphics.drawable.BitmapDrawable) r4
                goto L_0x048b
            L_0x048a:
                r4 = r7
            L_0x048b:
                if (r4 != 0) goto L_0x048e
            L_0x048d:
                goto L_0x049a
            L_0x048e:
                android.graphics.Bitmap r4 = r4.getBitmap()
                if (r4 != 0) goto L_0x0495
                goto L_0x048d
            L_0x0495:
                r4.prepareToDraw()
                kotlin.x r4 = kotlin.x.a
            L_0x049a:
                android.graphics.drawable.Drawable r2 = r0.a()
                boolean r4 = r0.b()
                coil.decode.b r0 = r0.c()
                coil.intercept.a r5 = r1.this$0
                r5.p(r2)
                coil.intercept.a r5 = r1.this$0
                coil.request.i r6 = r1.$request
                coil.memory.MemoryCache$Key r8 = r1.$memoryCacheKey
                boolean r5 = r5.q(r6, r8, r2, r4)
                coil.request.i r6 = r1.$request
                coil.memory.MemoryCache$Key r8 = r1.$memoryCacheKey
                r9 = r8
                r10 = 0
                java.lang.Boolean r10 = kotlin.coroutines.jvm.internal.b.a(r5)
                boolean r10 = r10.booleanValue()
                if (r10 == 0) goto L_0x04ce
                r7 = r8
            L_0x04ce:
                coil.intercept.b$a r8 = r1.$chain
                coil.intercept.c r8 = (coil.intercept.c) r8
                android.graphics.Bitmap r8 = r8.d()
                if (r8 == 0) goto L_0x04dd
                r8 = r16
                goto L_0x04de
            L_0x04dd:
                r8 = 0
            L_0x04de:
                coil.request.j$a r10 = new coil.request.j$a
                r10.<init>(r7, r4, r0, r8)
                coil.request.m r7 = new coil.request.m
                r7.<init>(r2, r6, r10)
                return r7
            L_0x04e9:
                kotlin.NoWhenBranchMatchedException r0 = new kotlin.NoWhenBranchMatchedException
                r0.<init>()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: coil.intercept.a.c.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @VisibleForTesting
    @Nullable
    public final MemoryCache.Key l(@NotNull i request, @NotNull Object data, @NotNull g<Object> fetcher, @NotNull Size size) {
        Object obj = data;
        g<Object> gVar = fetcher;
        Size size2 = size;
        k.e(request, Progress.REQUEST);
        k.e(obj, "data");
        k.e(gVar, "fetcher");
        k.e(size2, "size");
        String base = gVar.c(obj);
        if (base == null) {
            return null;
        }
        if (request.J().isEmpty()) {
            MemoryCache.Key.a aVar = MemoryCache.Key.c;
            return new MemoryCache.Key.Complex(base, kotlin.collections.q.g(), (Size) null, request.B().b());
        }
        MemoryCache.Key.a aVar2 = MemoryCache.Key.c;
        List J = request.J();
        coil.request.l parameters$iv = request.B();
        List $this$mapIndices$iv$iv = J;
        ArrayList destination$iv$iv = new ArrayList($this$mapIndices$iv$iv.size());
        int i2 = 0;
        int size3 = $this$mapIndices$iv$iv.size() - 1;
        if (size3 >= 0) {
            do {
                int i$iv$iv = i2;
                i2++;
                destination$iv$iv.add($this$mapIndices$iv$iv.get(i$iv$iv).key());
            } while (i2 <= size3);
        }
        return new MemoryCache.Key.Complex(base, destination$iv$iv, size2, parameters$iv.b());
    }

    @VisibleForTesting
    public final boolean n(@Nullable MemoryCache.Key cacheKey, @NotNull o.a cacheValue, @NotNull i request, @NotNull Size size) {
        k.e(cacheValue, "cacheValue");
        k.e(request, Progress.REQUEST);
        k.e(size, "size");
        if (!o(cacheKey, cacheValue, request, size)) {
            return false;
        }
        if (this.g.b(request, coil.util.c.c(cacheValue.b()))) {
            return true;
        }
        coil.util.m $this$log$iv = this.j;
        if ($this$log$iv != null && $this$log$iv.b() <= 3) {
            $this$log$iv.a("EngineInterceptor", 3, request.m() + ": Cached bitmap is hardware-backed, which is incompatible with the request.", (Throwable) null);
        }
        return false;
    }

    private final boolean o(MemoryCache.Key cacheKey, o.a cacheValue, i request, Size size) {
        int cachedHeight;
        int cachedWidth;
        MemoryCache.Key key = cacheKey;
        Size cachedSize = size;
        if (cachedSize instanceof OriginalSize) {
            if (cacheValue.a()) {
                coil.util.m $this$log$iv = this.j;
                if ($this$log$iv != null && $this$log$iv.b() <= 3) {
                    $this$log$iv.a("EngineInterceptor", 3, request.m() + ": Requested original size, but cached image is sampled.", (Throwable) null);
                }
                return false;
            }
        } else if (cachedSize instanceof PixelSize) {
            MemoryCache.Key.Complex complex = key instanceof MemoryCache.Key.Complex ? (MemoryCache.Key.Complex) key : null;
            Size cachedSize2 = complex == null ? null : complex.a();
            if (cachedSize2 instanceof PixelSize) {
                cachedWidth = ((PixelSize) cachedSize2).d();
                cachedHeight = ((PixelSize) cachedSize2).c();
            } else {
                if (k.a(cachedSize2, OriginalSize.c) || cachedSize2 == null) {
                    Bitmap bitmap = cacheValue.b();
                    cachedWidth = bitmap.getWidth();
                    cachedHeight = bitmap.getHeight();
                } else {
                    throw new NoWhenBranchMatchedException();
                }
            }
            if (Math.abs(cachedWidth - ((PixelSize) cachedSize).d()) <= 1 && Math.abs(cachedHeight - ((PixelSize) cachedSize).c()) <= 1) {
                return true;
            }
            coil.decode.d dVar = coil.decode.d.a;
            double multiple = coil.decode.d.d(cachedWidth, cachedHeight, ((PixelSize) cachedSize).d(), ((PixelSize) cachedSize).c(), request.G());
            if (!(multiple == 1.0d) && !h.b(request)) {
                coil.util.m $this$log$iv2 = this.j;
                if ($this$log$iv2 == null || $this$log$iv2.b() > 3) {
                    return false;
                }
                $this$log$iv2.a("EngineInterceptor", 3, request.m() + ": Cached image's request size (" + cachedWidth + ", " + cachedHeight + ") does not exactly match the requested size (" + ((PixelSize) cachedSize).d() + ", " + ((PixelSize) cachedSize).c() + ", " + request.G() + ").", (Throwable) null);
                return false;
            } else if (multiple > 1.0d && cacheValue.a()) {
                coil.util.m $this$log$iv3 = this.j;
                if ($this$log$iv3 == null || $this$log$iv3.b() > 3) {
                    return false;
                }
                $this$log$iv3.a("EngineInterceptor", 3, request.m() + ": Cached image's request size (" + cachedWidth + ", " + cachedHeight + ") is smaller than the requested size (" + ((PixelSize) cachedSize).d() + ", " + ((PixelSize) cachedSize).c() + ", " + request.G() + ").", (Throwable) null);
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final void m(Object data) {
        if (data instanceof BitmapDrawable) {
            e $this$setValid$iv = this.d;
            Bitmap bitmap$iv = ((BitmapDrawable) data).getBitmap();
            if (bitmap$iv != null) {
                $this$setValid$iv.a(bitmap$iv, false);
            }
        } else if (data instanceof Bitmap) {
            this.d.a((Bitmap) data, false);
        }
    }

    /* access modifiers changed from: private */
    public final void p(Drawable drawable) {
        Bitmap bitmap = null;
        BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
        if (bitmapDrawable != null) {
            bitmap = bitmapDrawable.getBitmap();
        }
        Bitmap bitmap2 = bitmap;
        if (bitmap2 != null) {
            this.d.a(bitmap2, true);
            this.d.c(bitmap2);
        }
    }

    /* access modifiers changed from: private */
    public final boolean q(i request, MemoryCache.Key key, Drawable drawable, boolean isSampled) {
        if (request.z().getWriteEnabled() && key != null) {
            Bitmap bitmap = null;
            BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
            if (bitmapDrawable != null) {
                bitmap = bitmapDrawable.getBitmap();
            }
            Bitmap bitmap2 = bitmap;
            if (bitmap2 != null) {
                this.e.c(key, bitmap2, isSampled);
                return true;
            }
        }
        return false;
    }

    /* renamed from: coil.intercept.a$a  reason: collision with other inner class name */
    /* compiled from: EngineInterceptor.kt */
    public static final class C0006a {
        public /* synthetic */ C0006a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0006a() {
        }
    }
}
