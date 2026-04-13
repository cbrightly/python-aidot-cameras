package coil.transition;

import coil.request.j;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CrossfadeTransition.kt */
public final class CrossfadeTransition implements b {
    private final int c;
    private final boolean d;

    @f(c = "coil.transition.CrossfadeTransition", f = "CrossfadeTransition.kt", l = {100}, m = "transition")
    /* compiled from: CrossfadeTransition.kt */
    public static final class a extends d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ CrossfadeTransition this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(CrossfadeTransition crossfadeTransition, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = crossfadeTransition;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a((c) null, (j) null, this);
        }
    }

    public CrossfadeTransition(int durationMillis, boolean preferExactIntrinsicSize) {
        this.c = durationMillis;
        this.d = preferExactIntrinsicSize;
        if (!(durationMillis > 0)) {
            throw new IllegalArgumentException("durationMillis must be > 0.".toString());
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ CrossfadeTransition(int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 100 : i, (i2 & 2) != 0 ? false : z);
    }

    public final int b() {
        return this.c;
    }

    public final boolean c() {
        return this.d;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v19, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: kotlin.jvm.internal.z} */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0145, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0124 A[Catch:{ all -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0128 A[Catch:{ all -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x013a A[Catch:{ all -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x013f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0140  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002d  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull coil.transition.c r26, @org.jetbrains.annotations.NotNull coil.request.j r27, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r28) {
        /*
            r25 = this;
            r0 = r28
            boolean r1 = r0 instanceof coil.transition.CrossfadeTransition.a
            if (r1 == 0) goto L_0x0018
            r1 = r0
            coil.transition.CrossfadeTransition$a r1 = (coil.transition.CrossfadeTransition.a) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r25
            goto L_0x0020
        L_0x0018:
            coil.transition.CrossfadeTransition$a r1 = new coil.transition.CrossfadeTransition$a
            r2 = r25
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r1.label
            r5 = 0
            r6 = 0
            switch(r4) {
                case 0: goto L_0x0051;
                case 1: goto L_0x0035;
                default: goto L_0x002d;
            }
        L_0x002d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0035:
            r0 = r6
            r4 = r5
            r0 = 0
            java.lang.Object r5 = r1.L$3
            r4 = r5
            kotlin.jvm.internal.z r4 = (kotlin.jvm.internal.z) r4
            java.lang.Object r5 = r1.L$2
            coil.request.j r5 = (coil.request.j) r5
            java.lang.Object r5 = r1.L$1
            coil.transition.c r5 = (coil.transition.c) r5
            java.lang.Object r5 = r1.L$0
            coil.transition.CrossfadeTransition r5 = (coil.transition.CrossfadeTransition) r5
            kotlin.p.b(r3)     // Catch:{ all -> 0x004e }
            goto L_0x0142
        L_0x004e:
            r0 = move-exception
            goto L_0x0148
        L_0x0051:
            kotlin.p.b(r3)
            r4 = r25
            r7 = r27
            r8 = r26
            boolean r9 = r7 instanceof coil.request.m
            if (r9 == 0) goto L_0x007a
            r9 = r7
            coil.request.m r9 = (coil.request.m) r9
            coil.request.j$a r9 = r9.c()
            coil.decode.b r9 = r9.a()
            coil.decode.b r10 = coil.decode.b.MEMORY_CACHE
            if (r9 != r10) goto L_0x007a
            r0 = r7
            coil.request.m r0 = (coil.request.m) r0
            android.graphics.drawable.Drawable r0 = r0.a()
            r8.a(r0)
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x007a:
            android.view.View r9 = r8.getView()
            r10 = 0
            int r11 = r9.getVisibility()
            r12 = 1
            if (r11 != 0) goto L_0x0088
            r9 = r12
            goto L_0x0089
        L_0x0088:
            r9 = r6
        L_0x0089:
            if (r9 != 0) goto L_0x00a9
            boolean r0 = r7 instanceof coil.request.m
            if (r0 == 0) goto L_0x009b
            r0 = r7
            coil.request.m r0 = (coil.request.m) r0
            android.graphics.drawable.Drawable r0 = r0.a()
            r8.a(r0)
            goto L_0x00a6
        L_0x009b:
            boolean r0 = r7 instanceof coil.request.g
            if (r0 == 0) goto L_0x00a6
            android.graphics.drawable.Drawable r0 = r7.a()
            r8.c(r0)
        L_0x00a6:
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x00a9:
            kotlin.jvm.internal.z r9 = new kotlin.jvm.internal.z
            r9.<init>()
            r10 = 0
            r1.L$0 = r4     // Catch:{ all -> 0x0146 }
            r1.L$1 = r8     // Catch:{ all -> 0x0146 }
            r1.L$2 = r7     // Catch:{ all -> 0x0146 }
            r1.L$3 = r9     // Catch:{ all -> 0x0146 }
            r1.label = r12     // Catch:{ all -> 0x0146 }
            r11 = r1
            r13 = 0
            kotlinx.coroutines.o r14 = new kotlinx.coroutines.o     // Catch:{ all -> 0x0146 }
            kotlin.coroutines.d r15 = kotlin.coroutines.intrinsics.b.c(r11)     // Catch:{ all -> 0x0146 }
            r14.<init>(r15, r12)     // Catch:{ all -> 0x0146 }
            r14.w()     // Catch:{ all -> 0x0146 }
            r15 = r14
            r16 = 0
            coil.drawable.CrossfadeDrawable r24 = new coil.drawable.CrossfadeDrawable     // Catch:{ all -> 0x0146 }
            android.graphics.drawable.Drawable r18 = r8.e()     // Catch:{ all -> 0x0146 }
            android.graphics.drawable.Drawable r19 = r7.a()     // Catch:{ all -> 0x0146 }
            android.view.View r5 = r8.getView()     // Catch:{ all -> 0x0146 }
            boolean r6 = r5 instanceof android.widget.ImageView     // Catch:{ all -> 0x0146 }
            if (r6 == 0) goto L_0x00e0
            android.widget.ImageView r5 = (android.widget.ImageView) r5     // Catch:{ all -> 0x0146 }
            goto L_0x00e1
        L_0x00e0:
            r5 = 0
        L_0x00e1:
            if (r5 != 0) goto L_0x00e5
            r5 = 0
            goto L_0x00e9
        L_0x00e5:
            coil.size.e r5 = coil.util.f.h(r5)     // Catch:{ all -> 0x0146 }
        L_0x00e9:
            if (r5 != 0) goto L_0x00ed
            coil.size.e r5 = coil.size.e.FILL     // Catch:{ all -> 0x0146 }
        L_0x00ed:
            r20 = r5
            int r21 = r4.b()     // Catch:{ all -> 0x0146 }
            boolean r5 = r7 instanceof coil.request.m     // Catch:{ all -> 0x0146 }
            if (r5 == 0) goto L_0x0108
            r5 = r7
            coil.request.m r5 = (coil.request.m) r5     // Catch:{ all -> 0x0146 }
            coil.request.j$a r5 = r5.c()     // Catch:{ all -> 0x0146 }
            boolean r5 = r5.b()     // Catch:{ all -> 0x0146 }
            if (r5 != 0) goto L_0x0105
            goto L_0x0108
        L_0x0105:
            r22 = 0
            goto L_0x010a
        L_0x0108:
            r22 = r12
        L_0x010a:
            boolean r23 = r4.c()     // Catch:{ all -> 0x0146 }
            r17 = r24
            r17.<init>(r18, r19, r20, r21, r22, r23)     // Catch:{ all -> 0x0146 }
            r5 = r24
            r9.element = r5     // Catch:{ all -> 0x0146 }
            coil.transition.CrossfadeTransition$transition$2$1 r6 = new coil.transition.CrossfadeTransition$transition$2$1     // Catch:{ all -> 0x0146 }
            r6.<init>(r5, r15)     // Catch:{ all -> 0x0146 }
            r5.registerAnimationCallback(r6)     // Catch:{ all -> 0x0146 }
            boolean r6 = r7 instanceof coil.request.m     // Catch:{ all -> 0x0146 }
            if (r6 == 0) goto L_0x0128
            r8.a(r5)     // Catch:{ all -> 0x0146 }
            goto L_0x012f
        L_0x0128:
            boolean r6 = r7 instanceof coil.request.g     // Catch:{ all -> 0x0146 }
            if (r6 == 0) goto L_0x012f
            r8.c(r5)     // Catch:{ all -> 0x0146 }
        L_0x012f:
            java.lang.Object r5 = r14.t()     // Catch:{ all -> 0x0146 }
            java.lang.Object r6 = kotlin.coroutines.intrinsics.c.d()     // Catch:{ all -> 0x0146 }
            if (r5 != r6) goto L_0x013d
            kotlin.coroutines.jvm.internal.h.c(r1)     // Catch:{ all -> 0x0146 }
        L_0x013d:
            if (r5 != r0) goto L_0x0140
            return r0
        L_0x0140:
            r4 = r9
            r0 = r10
        L_0x0142:
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x0146:
            r0 = move-exception
            r4 = r9
        L_0x0148:
            T r5 = r4.element
            coil.drawable.CrossfadeDrawable r5 = (coil.drawable.CrossfadeDrawable) r5
            if (r5 != 0) goto L_0x014f
            goto L_0x0152
        L_0x014f:
            r5.stop()
        L_0x0152:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.transition.CrossfadeTransition.a(coil.transition.c, coil.request.j, kotlin.coroutines.d):java.lang.Object");
    }

    public boolean equals(@Nullable Object other) {
        return this == other || ((other instanceof CrossfadeTransition) && this.c == ((CrossfadeTransition) other).c);
    }

    public int hashCode() {
        return this.c;
    }

    @NotNull
    public String toString() {
        return "CrossfadeTransition(durationMillis=" + this.c + ')';
    }

    public CrossfadeTransition() {
        this(0, false, 3, (DefaultConstructorMarker) null);
    }
}
