package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.h;
import kotlinx.coroutines.k2;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@f(c = "androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3", f = "RepeatOnLifecycle.kt", l = {84}, m = "invokeSuspend")
/* compiled from: RepeatOnLifecycle.kt */
public final class RepeatOnLifecycleKt$repeatOnLifecycle$3 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
    final /* synthetic */ p<o0, d<? super x>, Object> $block;
    final /* synthetic */ Lifecycle.State $state;
    final /* synthetic */ Lifecycle $this_repeatOnLifecycle;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    RepeatOnLifecycleKt$repeatOnLifecycle$3(Lifecycle lifecycle, Lifecycle.State state, p<? super o0, ? super d<? super x>, ? extends Object> pVar, d<? super RepeatOnLifecycleKt$repeatOnLifecycle$3> dVar) {
        super(2, dVar);
        this.$this_repeatOnLifecycle = lifecycle;
        this.$state = state;
        this.$block = pVar;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        RepeatOnLifecycleKt$repeatOnLifecycle$3 repeatOnLifecycleKt$repeatOnLifecycle$3 = new RepeatOnLifecycleKt$repeatOnLifecycle$3(this.$this_repeatOnLifecycle, this.$state, this.$block, dVar);
        repeatOnLifecycleKt$repeatOnLifecycle$3.L$0 = obj;
        return repeatOnLifecycleKt$repeatOnLifecycle$3;
    }

    @Nullable
    public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
        return ((RepeatOnLifecycleKt$repeatOnLifecycle$3) create(o0Var, dVar)).invokeSuspend(x.a);
    }

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1", f = "RepeatOnLifecycle.kt", l = {166}, m = "invokeSuspend")
    /* renamed from: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1  reason: invalid class name */
    /* compiled from: RepeatOnLifecycle.kt */
    public static final class AnonymousClass1 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new AnonymousClass1(lifecycle, state, o0Var, pVar, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((AnonymousClass1) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:22:0x00c2, code lost:
            r0 = (kotlinx.coroutines.z1) r7.element;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00c7, code lost:
            if (r0 == null) goto L_0x00cd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x00c9, code lost:
            kotlinx.coroutines.z1.a.a(r0, (java.util.concurrent.CancellationException) null, 1, (java.lang.Object) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00cd, code lost:
            r0 = (androidx.lifecycle.LifecycleEventObserver) r6.element;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00d1, code lost:
            if (r0 == null) goto L_0x00db;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00d3, code lost:
            r4.removeObserver(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00de, code lost:
            return kotlin.x.a;
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r23) {
            /*
                r22 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                r1 = r22
                int r2 = r1.label
                r3 = 1
                r4 = 0
                switch(r2) {
                    case 0: goto L_0x003a;
                    case 1: goto L_0x0015;
                    default: goto L_0x000d;
                }
            L_0x000d:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r2)
                throw r0
            L_0x0015:
                r2 = r22
                r5 = r23
                r0 = 0
                java.lang.Object r6 = r2.L$5
                kotlin.jvm.functions.p r6 = (kotlin.jvm.functions.p) r6
                java.lang.Object r6 = r2.L$4
                kotlinx.coroutines.o0 r6 = (kotlinx.coroutines.o0) r6
                java.lang.Object r6 = r2.L$3
                androidx.lifecycle.Lifecycle r6 = (androidx.lifecycle.Lifecycle) r6
                java.lang.Object r6 = r2.L$2
                androidx.lifecycle.Lifecycle$State r6 = (androidx.lifecycle.Lifecycle.State) r6
                java.lang.Object r6 = r2.L$1
                kotlin.jvm.internal.z r6 = (kotlin.jvm.internal.z) r6
                java.lang.Object r7 = r2.L$0
                kotlin.jvm.internal.z r7 = (kotlin.jvm.internal.z) r7
                kotlin.p.b(r5)     // Catch:{ all -> 0x0037 }
                goto L_0x00c2
            L_0x0037:
                r0 = move-exception
                goto L_0x00ea
            L_0x003a:
                kotlin.p.b(r23)
                r2 = r22
                r5 = r23
                androidx.lifecycle.Lifecycle r6 = r4
                androidx.lifecycle.Lifecycle$State r6 = r6.getCurrentState()
                androidx.lifecycle.Lifecycle$State r7 = androidx.lifecycle.Lifecycle.State.DESTROYED
                if (r6 != r7) goto L_0x004e
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x004e:
                kotlin.jvm.internal.z r6 = new kotlin.jvm.internal.z
                r6.<init>()
                kotlin.jvm.internal.z r7 = new kotlin.jvm.internal.z
                r7.<init>()
                r15 = r7
                androidx.lifecycle.Lifecycle$State r7 = r5     // Catch:{ all -> 0x00e7 }
                androidx.lifecycle.Lifecycle r14 = r4     // Catch:{ all -> 0x00e7 }
                kotlinx.coroutines.o0 r10 = r6     // Catch:{ all -> 0x00e7 }
                kotlin.jvm.functions.p<kotlinx.coroutines.o0, kotlin.coroutines.d<? super kotlin.x>, java.lang.Object> r9 = r7     // Catch:{ all -> 0x00e7 }
                r16 = 0
                r2.L$0 = r6     // Catch:{ all -> 0x00e7 }
                r2.L$1 = r15     // Catch:{ all -> 0x00e7 }
                r2.L$2 = r7     // Catch:{ all -> 0x00e7 }
                r2.L$3 = r14     // Catch:{ all -> 0x00e7 }
                r2.L$4 = r10     // Catch:{ all -> 0x00e7 }
                r2.L$5 = r9     // Catch:{ all -> 0x00e7 }
                r2.label = r3     // Catch:{ all -> 0x00e7 }
                r17 = r2
                r18 = 0
                kotlinx.coroutines.o r8 = new kotlinx.coroutines.o     // Catch:{ all -> 0x00e7 }
                kotlin.coroutines.d r11 = kotlin.coroutines.intrinsics.b.c(r17)     // Catch:{ all -> 0x00e7 }
                r8.<init>(r11, r3)     // Catch:{ all -> 0x00e7 }
                r19 = r8
                r19.w()     // Catch:{ all -> 0x00e7 }
                r12 = r19
                r20 = 0
                androidx.lifecycle.Lifecycle$Event r8 = androidx.lifecycle.Lifecycle.Event.upTo(r7)     // Catch:{ all -> 0x00e7 }
                androidx.lifecycle.Lifecycle$Event r11 = androidx.lifecycle.Lifecycle.Event.downFrom(r7)     // Catch:{ all -> 0x00e7 }
                r7 = 0
                kotlinx.coroutines.sync.b r13 = kotlinx.coroutines.sync.d.b(r7, r3, r4)     // Catch:{ all -> 0x00e7 }
                androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1 r7 = new androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1     // Catch:{ all -> 0x00e7 }
                r23 = r7
                r21 = r9
                r9 = r6
                r3 = r14
                r14 = r21
                r7.<init>(r8, r9, r10, r11, r12, r13, r14)     // Catch:{ all -> 0x00e7 }
                r7 = r23
                r15.element = r7     // Catch:{ all -> 0x00e7 }
                if (r7 == 0) goto L_0x00df
                androidx.lifecycle.LifecycleEventObserver r7 = (androidx.lifecycle.LifecycleEventObserver) r7     // Catch:{ all -> 0x00e7 }
                r3.addObserver(r7)     // Catch:{ all -> 0x00e7 }
                java.lang.Object r3 = r19.t()     // Catch:{ all -> 0x00e7 }
                java.lang.Object r7 = kotlin.coroutines.intrinsics.c.d()     // Catch:{ all -> 0x00e7 }
                if (r3 != r7) goto L_0x00bb
                kotlin.coroutines.jvm.internal.h.c(r2)     // Catch:{ all -> 0x00e7 }
            L_0x00bb:
                if (r3 != r0) goto L_0x00be
                return r0
            L_0x00be:
                r7 = r6
                r6 = r15
                r0 = r16
            L_0x00c2:
                T r0 = r7.element
                kotlinx.coroutines.z1 r0 = (kotlinx.coroutines.z1) r0
                if (r0 == 0) goto L_0x00cd
                r3 = 1
                kotlinx.coroutines.z1.a.a(r0, r4, r3, r4)
            L_0x00cd:
                T r0 = r6.element
                androidx.lifecycle.LifecycleEventObserver r0 = (androidx.lifecycle.LifecycleEventObserver) r0
                if (r0 == 0) goto L_0x00db
                androidx.lifecycle.Lifecycle r3 = r4
                r4 = 0
                r3.removeObserver(r0)
            L_0x00db:
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x00df:
                java.lang.NullPointerException r0 = new java.lang.NullPointerException     // Catch:{ all -> 0x00e7 }
                java.lang.String r3 = "null cannot be cast to non-null type androidx.lifecycle.LifecycleEventObserver"
                r0.<init>(r3)     // Catch:{ all -> 0x00e7 }
                throw r0     // Catch:{ all -> 0x00e7 }
            L_0x00e7:
                r0 = move-exception
                r7 = r6
                r6 = r15
            L_0x00ea:
                T r3 = r7.element
                kotlinx.coroutines.z1 r3 = (kotlinx.coroutines.z1) r3
                if (r3 == 0) goto L_0x00f4
                r7 = 1
                kotlinx.coroutines.z1.a.a(r3, r4, r7, r4)
            L_0x00f4:
                T r3 = r6.element
                androidx.lifecycle.LifecycleEventObserver r3 = (androidx.lifecycle.LifecycleEventObserver) r3
                if (r3 == 0) goto L_0x0102
                androidx.lifecycle.Lifecycle r4 = r4
                r6 = 0
                r4.removeObserver(r3)
            L_0x0102:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        Object d = c.d();
        switch (this.label) {
            case 0:
                kotlin.p.b($result);
                k2 W = d1.c().W();
                final Lifecycle lifecycle = this.$this_repeatOnLifecycle;
                final Lifecycle.State state = this.$state;
                final p<o0, d<? super x>, Object> pVar = this.$block;
                final o0 o0Var = (o0) this.L$0;
                AnonymousClass1 r3 = new AnonymousClass1((d<? super AnonymousClass1>) null);
                this.label = 1;
                if (h.g(W, r3, this) != d) {
                    break;
                } else {
                    return d;
                }
            case 1:
                kotlin.p.b($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return x.a;
    }
}
