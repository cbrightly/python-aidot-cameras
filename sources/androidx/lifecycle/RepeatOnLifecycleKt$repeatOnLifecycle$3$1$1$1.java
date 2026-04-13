package androidx.lifecycle;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.z;
import kotlin.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.sync.b;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroidx/lifecycle/LifecycleOwner;", "event", "Landroidx/lifecycle/Lifecycle$Event;", "onStateChanged"}, k = 3, mv = {1, 6, 0}, xi = 48)
/* compiled from: RepeatOnLifecycle.kt */
public final class RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1 implements LifecycleEventObserver {
    final /* synthetic */ o0 $$this$coroutineScope;
    final /* synthetic */ p<o0, d<? super x>, Object> $block;
    final /* synthetic */ Lifecycle.Event $cancelWorkEvent;
    final /* synthetic */ n<x> $cont;
    final /* synthetic */ z<z1> $launchedJob;
    final /* synthetic */ b $mutex;
    final /* synthetic */ Lifecycle.Event $startWorkEvent;

    RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1(Lifecycle.Event event, z<z1> zVar, o0 o0Var, Lifecycle.Event event2, n<? super x> nVar, b bVar, p<? super o0, ? super d<? super x>, ? extends Object> pVar) {
        this.$startWorkEvent = event;
        this.$launchedJob = zVar;
        this.$$this$coroutineScope = o0Var;
        this.$cancelWorkEvent = event2;
        this.$cont = nVar;
        this.$mutex = bVar;
        this.$block = pVar;
    }

    public final void onStateChanged(@NotNull LifecycleOwner lifecycleOwner, @NotNull Lifecycle.Event event) {
        k.e(lifecycleOwner, "<anonymous parameter 0>");
        k.e(event, NotificationCompat.CATEGORY_EVENT);
        if (event == this.$startWorkEvent) {
            z<z1> zVar = this.$launchedJob;
            o0 o0Var = this.$$this$coroutineScope;
            final b bVar = this.$mutex;
            final p<o0, d<? super x>, Object> pVar = this.$block;
            zVar.element = j.d(o0Var, (g) null, (q0) null, new AnonymousClass1((d<? super AnonymousClass1>) null), 3, (Object) null);
            return;
        }
        if (event == this.$cancelWorkEvent) {
            z1 z1Var = (z1) this.$launchedJob.element;
            if (z1Var != null) {
                z1.a.a(z1Var, (CancellationException) null, 1, (Object) null);
            }
            this.$launchedJob.element = null;
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            n<x> nVar = this.$cont;
            o.a aVar = o.Companion;
            nVar.resumeWith(o.m17constructorimpl(x.a));
        }
    }

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1", f = "RepeatOnLifecycle.kt", l = {171, 110}, m = "invokeSuspend")
    /* renamed from: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1  reason: invalid class name */
    /* compiled from: RepeatOnLifecycle.kt */
    public static final class AnonymousClass1 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
        Object L$0;
        Object L$1;
        int label;

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new AnonymousClass1(bVar, pVar, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((AnonymousClass1) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
            /*
                r10 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r10.label
                switch(r1) {
                    case 0: goto L_0x002e;
                    case 1: goto L_0x001f;
                    case 2: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r0)
                throw r11
            L_0x0011:
                r0 = r10
                r1 = 0
                r2 = 0
                r3 = 0
                java.lang.Object r4 = r0.L$0
                kotlinx.coroutines.sync.b r4 = (kotlinx.coroutines.sync.b) r4
                kotlin.p.b(r11)     // Catch:{ all -> 0x001d }
                goto L_0x0065
            L_0x001d:
                r2 = move-exception
                goto L_0x0073
            L_0x001f:
                r1 = r10
                r2 = 0
                java.lang.Object r3 = r1.L$1
                kotlin.jvm.functions.p r3 = (kotlin.jvm.functions.p) r3
                r4 = 0
                java.lang.Object r5 = r1.L$0
                kotlinx.coroutines.sync.b r5 = (kotlinx.coroutines.sync.b) r5
                kotlin.p.b(r11)
                goto L_0x004a
            L_0x002e:
                kotlin.p.b(r11)
                r1 = r10
                kotlinx.coroutines.sync.b r2 = r5
                kotlin.jvm.functions.p<kotlinx.coroutines.o0, kotlin.coroutines.d<? super kotlin.x>, java.lang.Object> r3 = r6
                r4 = 0
                r5 = 0
                r1.L$0 = r2
                r1.L$1 = r3
                r6 = 1
                r1.label = r6
                java.lang.Object r6 = r2.a(r4, r1)
                if (r6 != r0) goto L_0x0047
                return r0
            L_0x0047:
                r9 = r5
                r5 = r2
                r2 = r9
            L_0x004a:
                r6 = 0
                androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 r7 = new androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1     // Catch:{ all -> 0x006c }
                r8 = 0
                r7.<init>(r3, r8)     // Catch:{ all -> 0x006c }
                r1.L$0 = r5     // Catch:{ all -> 0x006c }
                r1.L$1 = r8     // Catch:{ all -> 0x006c }
                r3 = 2
                r1.label = r3     // Catch:{ all -> 0x006c }
                java.lang.Object r3 = kotlinx.coroutines.p0.e(r7, r1)     // Catch:{ all -> 0x006c }
                if (r3 != r0) goto L_0x0060
                return r0
            L_0x0060:
                r0 = r1
                r1 = r2
                r3 = r4
                r4 = r5
                r2 = r6
            L_0x0065:
                kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x001d }
                r4.b(r3)
                return r2
            L_0x006c:
                r0 = move-exception
                r3 = r4
                r4 = r5
                r9 = r2
                r2 = r0
                r0 = r1
                r1 = r9
            L_0x0073:
                r4.b(r3)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
