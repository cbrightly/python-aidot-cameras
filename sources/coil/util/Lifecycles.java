package coil.util;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: coil.util.-Lifecycles  reason: invalid class name */
/* compiled from: Lifecycles.kt */
public final class Lifecycles {

    @f(c = "coil.util.-Lifecycles", f = "Lifecycles.kt", l = {44}, m = "observeStarted")
    /* renamed from: coil.util.-Lifecycles$a */
    /* compiled from: Lifecycles.kt */
    public static final class a extends d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return Lifecycles.a((Lifecycle) null, this);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: kotlin.jvm.internal.z} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: androidx.lifecycle.Lifecycle} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    @androidx.annotation.MainThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object a(@org.jetbrains.annotations.NotNull androidx.lifecycle.Lifecycle r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r11) {
        /*
            boolean r0 = r11 instanceof coil.util.Lifecycles.a
            if (r0 == 0) goto L_0x0013
            r0 = r11
            coil.util.-Lifecycles$a r0 = (coil.util.Lifecycles.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            coil.util.-Lifecycles$a r0 = new coil.util.-Lifecycles$a
            r0.<init>(r11)
        L_0x0018:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r11.label
            switch(r2) {
                case 0: goto L_0x003d;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002c:
            r1 = 0
            r2 = 0
            r1 = 0
            java.lang.Object r3 = r11.L$1
            r2 = r3
            kotlin.jvm.internal.z r2 = (kotlin.jvm.internal.z) r2
            java.lang.Object r3 = r11.L$0
            r10 = r3
            androidx.lifecycle.Lifecycle r10 = (androidx.lifecycle.Lifecycle) r10
            kotlin.p.b(r0)     // Catch:{ all -> 0x0092 }
            goto L_0x0082
        L_0x003d:
            kotlin.p.b(r0)
            kotlin.jvm.internal.z r2 = new kotlin.jvm.internal.z
            r2.<init>()
            r3 = 0
            r11.L$0 = r10     // Catch:{ all -> 0x0092 }
            r11.L$1 = r2     // Catch:{ all -> 0x0092 }
            r4 = 1
            r11.label = r4     // Catch:{ all -> 0x0092 }
            r5 = r11
            r6 = 0
            kotlinx.coroutines.o r7 = new kotlinx.coroutines.o     // Catch:{ all -> 0x0092 }
            kotlin.coroutines.d r8 = kotlin.coroutines.intrinsics.b.c(r5)     // Catch:{ all -> 0x0092 }
            r7.<init>(r8, r4)     // Catch:{ all -> 0x0092 }
            r4 = r7
            r4.w()     // Catch:{ all -> 0x0092 }
            r7 = r4
            r8 = 0
            coil.util.-Lifecycles$observeStarted$2$1 r9 = new coil.util.-Lifecycles$observeStarted$2$1     // Catch:{ all -> 0x0092 }
            r9.<init>(r7)     // Catch:{ all -> 0x0092 }
            r2.element = r9     // Catch:{ all -> 0x0092 }
            kotlin.jvm.internal.k.c(r9)     // Catch:{ all -> 0x0092 }
            T r9 = r2.element     // Catch:{ all -> 0x0092 }
            androidx.lifecycle.LifecycleObserver r9 = (androidx.lifecycle.LifecycleObserver) r9     // Catch:{ all -> 0x0092 }
            r10.addObserver(r9)     // Catch:{ all -> 0x0092 }
            java.lang.Object r7 = r4.t()     // Catch:{ all -> 0x0092 }
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()     // Catch:{ all -> 0x0092 }
            if (r7 != r4) goto L_0x007e
            kotlin.coroutines.jvm.internal.h.c(r11)     // Catch:{ all -> 0x0092 }
        L_0x007e:
            if (r7 != r1) goto L_0x0081
            return r1
        L_0x0081:
            r1 = r3
        L_0x0082:
            T r1 = r2.element
            androidx.lifecycle.LifecycleObserver r1 = (androidx.lifecycle.LifecycleObserver) r1
            if (r1 != 0) goto L_0x008a
            goto L_0x008e
        L_0x008a:
            r3 = 0
            r10.removeObserver(r1)
        L_0x008e:
            kotlin.x r1 = kotlin.x.a
            return r1
        L_0x0092:
            r1 = move-exception
            T r3 = r2.element
            androidx.lifecycle.LifecycleObserver r3 = (androidx.lifecycle.LifecycleObserver) r3
            if (r3 != 0) goto L_0x009a
            goto L_0x009e
        L_0x009a:
            r4 = 0
            r10.removeObserver(r3)
        L_0x009e:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.util.Lifecycles.a(androidx.lifecycle.Lifecycle, kotlin.coroutines.d):java.lang.Object");
    }
}
