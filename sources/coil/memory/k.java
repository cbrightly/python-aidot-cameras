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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TargetDelegate.kt */
public final class k extends s {
    @NotNull
    private final coil.target.b a;
    @NotNull
    private final e b;
    @NotNull
    private final c c;
    @Nullable
    private final m d;

    @f(c = "coil.memory.InvalidatableTargetDelegate", f = "TargetDelegate.kt", l = {225}, m = "error")
    /* compiled from: TargetDelegate.kt */
    public static final class a extends d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(k kVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.b((g) null, this);
        }
    }

    @f(c = "coil.memory.InvalidatableTargetDelegate", f = "TargetDelegate.kt", l = {228}, m = "success")
    /* compiled from: TargetDelegate.kt */
    public static final class b extends d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(k kVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.f((coil.request.m) null, this);
        }
    }

    @NotNull
    public coil.target.b d() {
        return this.a;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public k(@NotNull coil.target.b target, @NotNull e referenceCounter, @NotNull c eventListener, @Nullable m logger) {
        super((DefaultConstructorMarker) null);
        kotlin.jvm.internal.k.e(target, TypedValues.AttributesType.S_TARGET);
        kotlin.jvm.internal.k.e(referenceCounter, "referenceCounter");
        kotlin.jvm.internal.k.e(eventListener, "eventListener");
        this.a = target;
        this.b = referenceCounter;
        this.c = eventListener;
        this.d = logger;
    }

    public void e(@Nullable Drawable placeholder, @Nullable Bitmap cached) {
        e $this$setValid$iv = this.b;
        if (cached != null) {
            $this$setValid$iv.a(cached, false);
        }
        d().b(placeholder);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object f(@org.jetbrains.annotations.NotNull coil.request.m r17, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r18) {
        /*
            r16 = this;
            r0 = r18
            boolean r1 = r0 instanceof coil.memory.k.b
            if (r1 == 0) goto L_0x0018
            r1 = r0
            coil.memory.k$b r1 = (coil.memory.k.b) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r16
            goto L_0x0020
        L_0x0018:
            coil.memory.k$b r1 = new coil.memory.k$b
            r2 = r16
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            switch(r4) {
                case 0: goto L_0x0047;
                case 1: goto L_0x0036;
                default: goto L_0x002c;
            }
        L_0x002c:
            r18 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0036:
            r3 = r5
            r3 = 0
            java.lang.Object r4 = r0.L$1
            coil.c r4 = (coil.c) r4
            java.lang.Object r5 = r0.L$0
            coil.request.m r5 = (coil.request.m) r5
            kotlin.p.b(r1)
            r18 = r1
            goto L_0x0101
        L_0x0047:
            kotlin.p.b(r1)
            r4 = r16
            r6 = r17
            coil.bitmap.e r7 = r4.b
            r8 = r6
            r9 = 0
            android.graphics.drawable.Drawable r10 = r8.a()
            boolean r11 = r10 instanceof android.graphics.drawable.BitmapDrawable
            if (r11 == 0) goto L_0x005d
            android.graphics.drawable.BitmapDrawable r10 = (android.graphics.drawable.BitmapDrawable) r10
            goto L_0x005e
        L_0x005d:
            r10 = 0
        L_0x005e:
            if (r10 != 0) goto L_0x0062
            r10 = 0
            goto L_0x0066
        L_0x0062:
            android.graphics.Bitmap r10 = r10.getBitmap()
        L_0x0066:
            r8 = r10
            r9 = 0
            r10 = 0
            if (r8 == 0) goto L_0x006e
            r7.a(r8, r5)
        L_0x006e:
            coil.target.b r5 = r4.d()
            coil.c r7 = r4.c
            coil.util.m r8 = r4.d
            r9 = 0
            coil.request.i r10 = r6.b()
            coil.transition.b r10 = r10.K()
            coil.transition.b r11 = coil.transition.b.b
            if (r10 != r11) goto L_0x008f
            android.graphics.drawable.Drawable r3 = r6.a()
            r5.a(r3)
            r18 = r1
            goto L_0x0109
        L_0x008f:
            boolean r11 = r5 instanceof coil.transition.c
            if (r11 != 0) goto L_0x00e4
            coil.request.i r3 = r6.b()
            coil.request.e r3 = r3.p()
            coil.transition.b r3 = r3.l()
            if (r3 == 0) goto L_0x00da
            if (r8 != 0) goto L_0x00a6
            r18 = r1
            goto L_0x00dc
        L_0x00a6:
            java.lang.String r3 = "TargetDelegate"
            r11 = 3
            r13 = r8
            r14 = 0
            int r15 = r13.b()
            if (r15 > r11) goto L_0x00d7
            r15 = 0
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r18 = r1
            java.lang.String r1 = "Ignoring '"
            r12.append(r1)
            r12.append(r10)
            java.lang.String r1 = "' as '"
            r12.append(r1)
            r12.append(r5)
            java.lang.String r1 = "' does not implement coil.transition.TransitionTarget."
            r12.append(r1)
            java.lang.String r1 = r12.toString()
            r12 = 0
            r13.a(r3, r11, r1, r12)
            goto L_0x00d9
        L_0x00d7:
            r18 = r1
        L_0x00d9:
            goto L_0x00dc
        L_0x00da:
            r18 = r1
        L_0x00dc:
            android.graphics.drawable.Drawable r1 = r6.a()
            r5.a(r1)
            goto L_0x0109
        L_0x00e4:
            r18 = r1
            coil.request.i r1 = r6.b()
            r7.p(r1)
            r1 = r5
            coil.transition.c r1 = (coil.transition.c) r1
            r0.L$0 = r6
            r0.L$1 = r7
            r11 = 1
            r0.label = r11
            java.lang.Object r1 = r10.a(r1, r6, r0)
            if (r1 != r3) goto L_0x00fe
            return r3
        L_0x00fe:
            r5 = r6
            r4 = r7
            r3 = r9
        L_0x0101:
            coil.request.i r1 = r5.b()
            r4.g(r1)
        L_0x0109:
            kotlin.x r1 = kotlin.x.a
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.memory.k.f(coil.request.m, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object b(@org.jetbrains.annotations.NotNull coil.request.g r17, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r18) {
        /*
            r16 = this;
            r0 = r18
            boolean r1 = r0 instanceof coil.memory.k.a
            if (r1 == 0) goto L_0x0018
            r1 = r0
            coil.memory.k$a r1 = (coil.memory.k.a) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r16
            goto L_0x0020
        L_0x0018:
            coil.memory.k$a r1 = new coil.memory.k$a
            r2 = r16
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L_0x0046;
                case 1: goto L_0x0035;
                default: goto L_0x002b;
            }
        L_0x002b:
            r18 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0035:
            r3 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$1
            coil.c r4 = (coil.c) r4
            java.lang.Object r5 = r0.L$0
            coil.request.g r5 = (coil.request.g) r5
            kotlin.p.b(r1)
            r18 = r1
            goto L_0x00de
        L_0x0046:
            kotlin.p.b(r1)
            r4 = r16
            r5 = r17
            coil.target.b r6 = r4.d()
            coil.c r7 = r4.c
            coil.util.m r8 = r4.d
            r9 = 0
            coil.request.i r10 = r5.b()
            coil.transition.b r10 = r10.K()
            coil.transition.b r11 = coil.transition.b.b
            if (r10 != r11) goto L_0x006d
            android.graphics.drawable.Drawable r3 = r5.a()
            r6.c(r3)
            r18 = r1
            goto L_0x00e6
        L_0x006d:
            boolean r11 = r6 instanceof coil.transition.c
            if (r11 != 0) goto L_0x00c2
            coil.request.i r3 = r5.b()
            coil.request.e r3 = r3.p()
            coil.transition.b r3 = r3.l()
            if (r3 == 0) goto L_0x00b8
            if (r8 != 0) goto L_0x0084
            r18 = r1
            goto L_0x00ba
        L_0x0084:
            java.lang.String r3 = "TargetDelegate"
            r11 = 3
            r12 = r8
            r13 = 0
            int r14 = r12.b()
            if (r14 > r11) goto L_0x00b5
            r14 = 0
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r18 = r1
            java.lang.String r1 = "Ignoring '"
            r15.append(r1)
            r15.append(r10)
            java.lang.String r1 = "' as '"
            r15.append(r1)
            r15.append(r6)
            java.lang.String r1 = "' does not implement coil.transition.TransitionTarget."
            r15.append(r1)
            java.lang.String r1 = r15.toString()
            r14 = 0
            r12.a(r3, r11, r1, r14)
            goto L_0x00b7
        L_0x00b5:
            r18 = r1
        L_0x00b7:
            goto L_0x00ba
        L_0x00b8:
            r18 = r1
        L_0x00ba:
            android.graphics.drawable.Drawable r1 = r5.a()
            r6.c(r1)
            goto L_0x00e6
        L_0x00c2:
            r18 = r1
            coil.request.i r1 = r5.b()
            r7.p(r1)
            r1 = r6
            coil.transition.c r1 = (coil.transition.c) r1
            r0.L$0 = r5
            r0.L$1 = r7
            r11 = 1
            r0.label = r11
            java.lang.Object r1 = r10.a(r1, r5, r0)
            if (r1 != r3) goto L_0x00dc
            return r3
        L_0x00dc:
            r4 = r7
            r3 = r9
        L_0x00de:
            coil.request.i r1 = r5.b()
            r4.g(r1)
        L_0x00e6:
            kotlin.x r1 = kotlin.x.a
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.memory.k.b(coil.request.g, kotlin.coroutines.d):java.lang.Object");
    }
}
