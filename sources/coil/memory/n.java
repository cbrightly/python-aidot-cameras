package coil.memory;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import coil.bitmap.e;
import coil.c;
import coil.request.g;
import coil.util.m;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TargetDelegate.kt */
public final class n extends s {
    @NotNull
    private final coil.target.a<?> a;
    /* access modifiers changed from: private */
    @NotNull
    public final e b;
    @NotNull
    private final c c;
    @Nullable
    private final m d;

    @f(c = "coil.memory.PoolableTargetDelegate", f = "TargetDelegate.kt", l = {227, 240}, m = "error")
    /* compiled from: TargetDelegate.kt */
    public static final class a extends d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ n this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(n nVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = nVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.b((g) null, this);
        }
    }

    @f(c = "coil.memory.PoolableTargetDelegate", f = "TargetDelegate.kt", l = {228, 251}, m = "success")
    /* compiled from: TargetDelegate.kt */
    public static final class b extends d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ n this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(n nVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = nVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.f((coil.request.m) null, this);
        }
    }

    @NotNull
    /* renamed from: l */
    public coil.target.a<?> d() {
        return this.a;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public n(@NotNull coil.target.a<?> target, @NotNull e referenceCounter, @NotNull c eventListener, @Nullable m logger) {
        super((DefaultConstructorMarker) null);
        k.e(target, TypedValues.AttributesType.S_TARGET);
        k.e(referenceCounter, "referenceCounter");
        k.e(eventListener, "eventListener");
        this.a = target;
        this.b = referenceCounter;
        this.c = eventListener;
        this.d = logger;
    }

    public void e(@Nullable Drawable placeholder, @Nullable Bitmap cached) {
        if (this.b instanceof coil.bitmap.g) {
            d().b(placeholder);
            return;
        }
        m(cached);
        d().b(placeholder);
        k(cached);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: android.graphics.Bitmap} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: coil.memory.n} */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x013c, code lost:
        r8.g(r9.b());
        r14 = r5;
        r8 = r7;
        r5 = r3;
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01f4, code lost:
        r8.g(r9.b());
        r2 = r5;
        r8 = r7;
        r5 = r3;
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01ff, code lost:
        h(r5, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0206, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002d  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object f(@org.jetbrains.annotations.NotNull coil.request.m r24, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r25) {
        /*
            r23 = this;
            r0 = r25
            boolean r1 = r0 instanceof coil.memory.n.b
            if (r1 == 0) goto L_0x0018
            r1 = r0
            coil.memory.n$b r1 = (coil.memory.n.b) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r23
            goto L_0x0020
        L_0x0018:
            coil.memory.n$b r1 = new coil.memory.n$b
            r2 = r23
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            r6 = 0
            switch(r4) {
                case 0: goto L_0x006b;
                case 1: goto L_0x0056;
                case 2: goto L_0x0037;
                default: goto L_0x002d;
            }
        L_0x002d:
            r16 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0037:
            r3 = r6
            r4 = r5
            r7 = r5
            r7 = 0
            r5 = 0
            r4 = 0
            java.lang.Object r8 = r0.L$3
            coil.c r8 = (coil.c) r8
            java.lang.Object r9 = r0.L$2
            r6 = r9
            android.graphics.Bitmap r6 = (android.graphics.Bitmap) r6
            java.lang.Object r9 = r0.L$1
            r3 = r9
            coil.memory.n r3 = (coil.memory.n) r3
            java.lang.Object r9 = r0.L$0
            coil.request.m r9 = (coil.request.m) r9
            kotlin.p.b(r1)
            r16 = r1
            goto L_0x01f4
        L_0x0056:
            r3 = r6
            r4 = r5
            r7 = r5
            r7 = 0
            r5 = 0
            r4 = 0
            java.lang.Object r8 = r0.L$1
            coil.c r8 = (coil.c) r8
            java.lang.Object r9 = r0.L$0
            coil.request.m r9 = (coil.request.m) r9
            kotlin.p.b(r1)
            r16 = r1
            goto L_0x013c
        L_0x006b:
            kotlin.p.b(r1)
            r4 = r23
            r9 = r24
            r5 = r9
            r7 = 0
            android.graphics.drawable.Drawable r8 = r5.a()
            boolean r10 = r8 instanceof android.graphics.drawable.BitmapDrawable
            if (r10 == 0) goto L_0x007f
            android.graphics.drawable.BitmapDrawable r8 = (android.graphics.drawable.BitmapDrawable) r8
            goto L_0x0080
        L_0x007f:
            r8 = r6
        L_0x0080:
            if (r8 != 0) goto L_0x0085
            r5 = r4
            r8 = r6
            goto L_0x008a
        L_0x0085:
            android.graphics.Bitmap r8 = r8.getBitmap()
            r5 = r4
        L_0x008a:
            r7 = r8
            r8 = 0
            coil.bitmap.e r10 = r5.b
            boolean r10 = r10 instanceof coil.bitmap.g
            java.lang.String r11 = "' does not implement coil.transition.TransitionTarget."
            java.lang.String r12 = "' as '"
            java.lang.String r13 = "Ignoring '"
            if (r10 == 0) goto L_0x014a
            coil.target.a r10 = r5.d()
            r14 = 0
            r15 = r10
            coil.c r6 = r4.c
            r16 = r1
            coil.util.m r1 = r4.d
            r24 = r0
            r17 = 0
            coil.request.i r18 = r9.b()
            coil.transition.b r2 = r18.K()
            r18 = r8
            coil.transition.b r8 = coil.transition.b.b
            if (r2 != r8) goto L_0x00c0
            android.graphics.drawable.Drawable r3 = r9.a()
            r15.a(r3)
            goto L_0x0115
        L_0x00c0:
            boolean r8 = r15 instanceof coil.transition.c
            if (r8 != 0) goto L_0x0118
            coil.request.i r3 = r9.b()
            coil.request.e r3 = r3.p()
            coil.transition.b r3 = r3.l()
            if (r3 == 0) goto L_0x010b
            if (r1 != 0) goto L_0x00d7
            r21 = r1
            goto L_0x010d
        L_0x00d7:
            java.lang.String r3 = "TargetDelegate"
            r8 = 3
            r19 = r1
            r20 = 0
            r21 = r1
            int r1 = r19.b()
            if (r1 > r8) goto L_0x0108
            r1 = 0
            r22 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r13)
            r1.append(r2)
            r1.append(r12)
            r1.append(r15)
            r1.append(r11)
            java.lang.String r1 = r1.toString()
            r11 = r19
            r12 = 0
            r11.a(r3, r8, r1, r12)
            goto L_0x010a
        L_0x0108:
            r11 = r19
        L_0x010a:
            goto L_0x010d
        L_0x010b:
            r21 = r1
        L_0x010d:
            android.graphics.drawable.Drawable r1 = r9.a()
            r15.a(r1)
        L_0x0115:
            r8 = r18
            goto L_0x0147
        L_0x0118:
            r21 = r1
            coil.request.i r1 = r9.b()
            r6.p(r1)
            r1 = r15
            coil.transition.c r1 = (coil.transition.c) r1
            r0.L$0 = r9
            r0.L$1 = r6
            r8 = 1
            r0.label = r8
            r8 = r24
            java.lang.Object r1 = r2.a(r1, r9, r8)
            if (r1 != r3) goto L_0x0134
            return r3
        L_0x0134:
            r3 = r5
            r8 = r6
            r6 = r7
            r5 = r14
            r4 = r17
            r7 = r18
        L_0x013c:
            coil.request.i r1 = r9.b()
            r8.g(r1)
            r14 = r5
            r8 = r7
            r5 = r3
            r7 = r6
        L_0x0147:
            goto L_0x0203
        L_0x014a:
            r16 = r1
            r18 = r8
            r5.m(r7)
            coil.target.a r1 = r5.d()
            r2 = 0
            r6 = r1
            coil.c r8 = r4.c
            coil.util.m r4 = r4.d
            r10 = r0
            r14 = 0
            coil.request.i r15 = r9.b()
            coil.transition.b r15 = r15.K()
            r24 = r1
            coil.transition.b r1 = coil.transition.b.b
            if (r15 != r1) goto L_0x0175
            android.graphics.drawable.Drawable r1 = r9.a()
            r6.a(r1)
            r20 = r2
            goto L_0x01ca
        L_0x0175:
            boolean r1 = r6 instanceof coil.transition.c
            if (r1 != 0) goto L_0x01cf
            coil.request.i r1 = r9.b()
            coil.request.e r1 = r1.p()
            coil.transition.b r1 = r1.l()
            if (r1 == 0) goto L_0x01c0
            if (r4 != 0) goto L_0x018c
            r20 = r2
            goto L_0x01c2
        L_0x018c:
            java.lang.String r1 = "TargetDelegate"
            r3 = 3
            r17 = r4
            r19 = 0
            r20 = r2
            int r2 = r17.b()
            if (r2 > r3) goto L_0x01bd
            r2 = 0
            r21 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r13)
            r2.append(r15)
            r2.append(r12)
            r2.append(r6)
            r2.append(r11)
            java.lang.String r2 = r2.toString()
            r11 = r17
            r12 = 0
            r11.a(r1, r3, r2, r12)
            goto L_0x01bf
        L_0x01bd:
            r11 = r17
        L_0x01bf:
            goto L_0x01c2
        L_0x01c0:
            r20 = r2
        L_0x01c2:
            android.graphics.drawable.Drawable r1 = r9.a()
            r6.a(r1)
        L_0x01ca:
            r8 = r18
            r2 = r20
            goto L_0x01ff
        L_0x01cf:
            r20 = r2
            coil.request.i r1 = r9.b()
            r8.p(r1)
            r1 = r6
            coil.transition.c r1 = (coil.transition.c) r1
            r0.L$0 = r9
            r0.L$1 = r5
            r0.L$2 = r7
            r0.L$3 = r8
            r2 = 2
            r0.label = r2
            java.lang.Object r1 = r15.a(r1, r9, r10)
            if (r1 != r3) goto L_0x01ed
            return r3
        L_0x01ed:
            r3 = r5
            r6 = r7
            r4 = r14
            r7 = r18
            r5 = r20
        L_0x01f4:
            coil.request.i r1 = r9.b()
            r8.g(r1)
            r2 = r5
            r8 = r7
            r5 = r3
            r7 = r6
        L_0x01ff:
            r5.k(r7)
        L_0x0203:
            kotlin.x r1 = kotlin.x.a
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.memory.n.f(coil.request.m, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: coil.memory.n} */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0125, code lost:
        r9.g(r10.b());
        r7 = r4;
        r14 = r5;
        r5 = r6;
        r4 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01db, code lost:
        r8.g(r9.b());
        r2 = r5;
        r5 = r6;
        r8 = r7;
        r7 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01e6, code lost:
        h(r7, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01ed, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002d  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object b(@org.jetbrains.annotations.NotNull coil.request.g r24, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r25) {
        /*
            r23 = this;
            r0 = r25
            boolean r1 = r0 instanceof coil.memory.n.a
            if (r1 == 0) goto L_0x0018
            r1 = r0
            coil.memory.n$a r1 = (coil.memory.n.a) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r23
            goto L_0x0020
        L_0x0018:
            coil.memory.n$a r1 = new coil.memory.n$a
            r2 = r23
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            r6 = 0
            switch(r4) {
                case 0: goto L_0x0069;
                case 1: goto L_0x0052;
                case 2: goto L_0x0037;
                default: goto L_0x002d;
            }
        L_0x002d:
            r16 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0037:
            r3 = r6
            r4 = r5
            r7 = r5
            r7 = 0
            r5 = 0
            r4 = 0
            java.lang.Object r8 = r0.L$2
            coil.c r8 = (coil.c) r8
            r6 = 0
            java.lang.Object r9 = r0.L$1
            r3 = r9
            coil.memory.n r3 = (coil.memory.n) r3
            java.lang.Object r9 = r0.L$0
            coil.request.g r9 = (coil.request.g) r9
            kotlin.p.b(r1)
            r16 = r1
            goto L_0x01db
        L_0x0052:
            r3 = r23
            r4 = r6
            r7 = r5
            r8 = r5
            r8 = 0
            r5 = 0
            r7 = 0
            java.lang.Object r9 = r0.L$1
            coil.c r9 = (coil.c) r9
            java.lang.Object r10 = r0.L$0
            coil.request.g r10 = (coil.request.g) r10
            kotlin.p.b(r1)
            r16 = r1
            goto L_0x0125
        L_0x0069:
            kotlin.p.b(r1)
            r4 = r23
            r9 = r24
            r5 = 0
            r7 = r4
            r8 = 0
            coil.bitmap.e r10 = r7.b
            boolean r10 = r10 instanceof coil.bitmap.g
            java.lang.String r11 = "' does not implement coil.transition.TransitionTarget."
            java.lang.String r12 = "' as '"
            java.lang.String r13 = "Ignoring '"
            if (r10 == 0) goto L_0x0133
            coil.target.a r10 = r7.d()
            r14 = 0
            r15 = r10
            coil.c r6 = r4.c
            r16 = r1
            coil.util.m r1 = r4.d
            r24 = r0
            r17 = 0
            coil.request.i r18 = r9.b()
            coil.transition.b r2 = r18.K()
            r18 = r8
            coil.transition.b r8 = coil.transition.b.b
            if (r2 != r8) goto L_0x00a7
            android.graphics.drawable.Drawable r3 = r9.a()
            r15.c(r3)
            goto L_0x00fc
        L_0x00a7:
            boolean r8 = r15 instanceof coil.transition.c
            if (r8 != 0) goto L_0x00ff
            coil.request.i r3 = r9.b()
            coil.request.e r3 = r3.p()
            coil.transition.b r3 = r3.l()
            if (r3 == 0) goto L_0x00f2
            if (r1 != 0) goto L_0x00be
            r21 = r1
            goto L_0x00f4
        L_0x00be:
            java.lang.String r3 = "TargetDelegate"
            r8 = 3
            r19 = r1
            r20 = 0
            r21 = r1
            int r1 = r19.b()
            if (r1 > r8) goto L_0x00ef
            r1 = 0
            r22 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r13)
            r1.append(r2)
            r1.append(r12)
            r1.append(r15)
            r1.append(r11)
            java.lang.String r1 = r1.toString()
            r11 = r19
            r12 = 0
            r11.a(r3, r8, r1, r12)
            goto L_0x00f1
        L_0x00ef:
            r11 = r19
        L_0x00f1:
            goto L_0x00f4
        L_0x00f2:
            r21 = r1
        L_0x00f4:
            android.graphics.drawable.Drawable r1 = r9.a()
            r15.c(r1)
        L_0x00fc:
            r8 = r18
            goto L_0x0130
        L_0x00ff:
            r21 = r1
            coil.request.i r1 = r9.b()
            r6.p(r1)
            r1 = r15
            coil.transition.c r1 = (coil.transition.c) r1
            r0.L$0 = r9
            r0.L$1 = r6
            r8 = 1
            r0.label = r8
            r8 = r24
            java.lang.Object r1 = r2.a(r1, r9, r8)
            if (r1 != r3) goto L_0x011b
            return r3
        L_0x011b:
            r3 = r4
            r4 = r7
            r10 = r9
            r7 = r17
            r8 = r18
            r9 = r6
            r6 = r5
            r5 = r14
        L_0x0125:
            coil.request.i r1 = r10.b()
            r9.g(r1)
            r7 = r4
            r14 = r5
            r5 = r6
            r4 = r3
        L_0x0130:
            goto L_0x01ea
        L_0x0133:
            r16 = r1
            r18 = r8
            r7.m(r5)
            coil.target.a r1 = r7.d()
            r2 = 0
            r6 = r1
            coil.c r8 = r4.c
            coil.util.m r10 = r4.d
            r14 = r0
            r15 = 0
            coil.request.i r17 = r9.b()
            r24 = r1
            coil.transition.b r1 = r17.K()
            r17 = r2
            coil.transition.b r2 = coil.transition.b.b
            if (r1 != r2) goto L_0x015e
            android.graphics.drawable.Drawable r2 = r9.a()
            r6.c(r2)
            goto L_0x01b3
        L_0x015e:
            boolean r2 = r6 instanceof coil.transition.c
            if (r2 != 0) goto L_0x01b8
            coil.request.i r2 = r9.b()
            coil.request.e r2 = r2.p()
            coil.transition.b r2 = r2.l()
            if (r2 == 0) goto L_0x01a9
            if (r10 != 0) goto L_0x0175
            r21 = r4
            goto L_0x01ab
        L_0x0175:
            java.lang.String r2 = "TargetDelegate"
            r3 = 3
            r19 = r10
            r20 = 0
            r21 = r4
            int r4 = r19.b()
            if (r4 > r3) goto L_0x01a6
            r4 = 0
            r22 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r13)
            r4.append(r1)
            r4.append(r12)
            r4.append(r6)
            r4.append(r11)
            java.lang.String r4 = r4.toString()
            r11 = r19
            r12 = 0
            r11.a(r2, r3, r4, r12)
            goto L_0x01a8
        L_0x01a6:
            r11 = r19
        L_0x01a8:
            goto L_0x01ab
        L_0x01a9:
            r21 = r4
        L_0x01ab:
            android.graphics.drawable.Drawable r2 = r9.a()
            r6.c(r2)
        L_0x01b3:
            r2 = r17
            r8 = r18
            goto L_0x01e6
        L_0x01b8:
            r21 = r4
            coil.request.i r2 = r9.b()
            r8.p(r2)
            r2 = r6
            coil.transition.c r2 = (coil.transition.c) r2
            r0.L$0 = r9
            r0.L$1 = r7
            r0.L$2 = r8
            r4 = 2
            r0.label = r4
            java.lang.Object r1 = r1.a(r2, r9, r14)
            if (r1 != r3) goto L_0x01d4
            return r3
        L_0x01d4:
            r6 = r5
            r3 = r7
            r4 = r15
            r5 = r17
            r7 = r18
        L_0x01db:
            coil.request.i r1 = r9.b()
            r8.g(r1)
            r2 = r5
            r5 = r6
            r8 = r7
            r7 = r3
        L_0x01e6:
            r7.k(r5)
        L_0x01ea:
            kotlin.x r1 = kotlin.x.a
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.memory.n.b(coil.request.g, kotlin.coroutines.d):java.lang.Object");
    }

    public void a() {
        if (this.b instanceof coil.bitmap.g) {
            d().d();
            return;
        }
        m((Bitmap) null);
        d().d();
        k((Bitmap) null);
    }

    /* access modifiers changed from: private */
    public final void m(Bitmap bitmap) {
        if (bitmap != null) {
            this.b.c(bitmap);
        }
    }

    /* access modifiers changed from: private */
    public final void k(Bitmap bitmap) {
        Bitmap previous = coil.util.f.g(d().getView()).d(this, bitmap);
        if (previous != null) {
            this.b.b(previous);
        }
    }
}
