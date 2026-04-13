package io.ktor.http.cio.internals;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.leedarson.bean.Constants;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.TypeCastException;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.c2;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.t;
import kotlinx.coroutines.v;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WeakTimeoutQueue.kt */
public final class g {
    private final io.ktor.util.internal.a a;
    private final long b;
    private final kotlin.jvm.functions.a<Long> c;
    private volatile boolean cancelled;

    public g(long timeoutMillis, @NotNull kotlin.jvm.functions.a<Long> clock) {
        k.f(clock, "clock");
        this.b = timeoutMillis;
        this.c = clock;
        this.a = new io.ktor.util.internal.a();
    }

    /* compiled from: WeakTimeoutQueue.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Long> {
        public static final a INSTANCE = new a();

        a() {
            super(0);
        }

        public final long invoke() {
            return System.currentTimeMillis();
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ g(long j, kotlin.jvm.functions.a aVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, (i & 2) != 0 ? a.INSTANCE : aVar);
    }

    @NotNull
    public final d d(@NotNull z1 job) {
        k.f(job, "job");
        long now = this.c.invoke().longValue();
        io.ktor.util.internal.a head = this.a;
        if (!this.cancelled) {
            c cancellable = new c(this.b + now, job);
            head.a(cancellable);
            c(now, head, this.cancelled);
            if (!this.cancelled) {
                return cancellable;
            }
            cancellable.q();
            throw new CancellationException();
        }
        throw new CancellationException();
    }

    public final void a() {
        this.cancelled = true;
        b();
    }

    public final void b() {
        c(this.c.invoke().longValue(), this.a, this.cancelled);
    }

    @Nullable
    public final <T> Object e(@NotNull p<? super o0, ? super kotlin.coroutines.d<? super T>, ? extends Object> block, @NotNull kotlin.coroutines.d<? super T> $completion) {
        Object result;
        kotlin.coroutines.d continuation = kotlin.coroutines.intrinsics.b.c($completion);
        e wrapped = new e(continuation.getContext(), continuation, (z1) null, 4, (DefaultConstructorMarker) null);
        d handle = d(wrapped);
        wrapped.t(handle);
        try {
            if (wrapped.isCancelled()) {
                result = kotlin.coroutines.intrinsics.c.d();
            } else if (block != null) {
                result = ((p) e0.e(block, 2)).invoke(wrapped, wrapped);
            } else {
                throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
            }
        } catch (Throwable t) {
            if (!wrapped.k()) {
                result = kotlin.coroutines.intrinsics.c.d();
            } else {
                handle.dispose();
                throw t;
            }
        }
        if (result == kotlin.coroutines.intrinsics.c.d()) {
            result = kotlin.coroutines.intrinsics.c.d();
        } else if (wrapped.k()) {
            handle.dispose();
        } else {
            result = kotlin.coroutines.intrinsics.c.d();
        }
        if (result == kotlin.coroutines.intrinsics.c.d()) {
            h.c($completion);
        }
        return result;
    }

    private final void c(long now, io.ktor.util.internal.a head, boolean cancelled2) {
        while (true) {
            Object h = head.h();
            if (!(h instanceof b)) {
                h = null;
            }
            b p = (b) h;
            if (p == null) {
                return;
            }
            if (!cancelled2 && p.r() > now) {
                return;
            }
            if (p.t() && p.n()) {
                p.q();
            }
        }
    }

    /* compiled from: WeakTimeoutQueue.kt */
    public interface d extends kotlin.jvm.functions.l<Throwable, x>, f1 {

        /* compiled from: WeakTimeoutQueue.kt */
        public static final class a {
            public static void a(d $this, @Nullable Throwable cause) {
                $this.dispose();
            }
        }
    }

    /* compiled from: WeakTimeoutQueue.kt */
    public static abstract class b extends io.ktor.util.internal.c implements d {
        private final long q;

        public abstract void q();

        public b(long deadline) {
            this.q = deadline;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            s((Throwable) obj);
            return x.a;
        }

        public void s(@Nullable Throwable cause) {
            d.a.a(this, cause);
        }

        public final long r() {
            return this.q;
        }

        public boolean t() {
            return !l();
        }

        public void dispose() {
            n();
        }
    }

    /* compiled from: WeakTimeoutQueue.kt */
    public static final class c extends b {
        private final z1 x;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(long deadline, @NotNull z1 job) {
            super(deadline);
            k.f(job, "job");
            this.x = job;
        }

        public boolean t() {
            return super.t() && this.x.isActive();
        }

        public void q() {
            z1.a.a(this.x, (CancellationException) null, 1, (Object) null);
        }
    }

    /* compiled from: WeakTimeoutQueue.kt */
    public static final class e<T> implements kotlin.coroutines.d<T>, z1, o0 {
        private static final AtomicReferenceFieldUpdater c = AtomicReferenceFieldUpdater.newUpdater(e.class, Object.class, Constants.ACTION_STATE);
        @NotNull
        private final kotlin.coroutines.g d;
        @NotNull
        private final z1 f;
        private volatile Object state;

        public boolean I() {
            return this.f.I();
        }

        @Nullable
        public Object J(@NotNull kotlin.coroutines.d<? super x> dVar) {
            return this.f.J(dVar);
        }

        @NotNull
        public t T(@NotNull v vVar) {
            k.f(vVar, "child");
            return this.f.T(vVar);
        }

        public void c(@Nullable CancellationException cancellationException) {
            this.f.c(cancellationException);
        }

        public <R> R fold(R r, @NotNull p<? super R, ? super g.b, ? extends R> pVar) {
            k.f(pVar, "operation");
            return this.f.fold(r, pVar);
        }

        @Nullable
        public <E extends g.b> E get(@NotNull g.c<E> cVar) {
            k.f(cVar, CacheEntity.KEY);
            return this.f.get(cVar);
        }

        @NotNull
        public kotlin.sequences.h<z1> getChildren() {
            return this.f.getChildren();
        }

        @NotNull
        public g.c<?> getKey() {
            return this.f.getKey();
        }

        public boolean isActive() {
            return this.f.isActive();
        }

        public boolean isCancelled() {
            return this.f.isCancelled();
        }

        @NotNull
        public f1 m(boolean z, boolean z2, @NotNull kotlin.jvm.functions.l<? super Throwable, x> lVar) {
            k.f(lVar, "handler");
            return this.f.m(z, z2, lVar);
        }

        @NotNull
        public kotlin.coroutines.g minusKey(@NotNull g.c<?> cVar) {
            k.f(cVar, CacheEntity.KEY);
            return this.f.minusKey(cVar);
        }

        @NotNull
        public CancellationException n() {
            return this.f.n();
        }

        @NotNull
        public kotlin.coroutines.g plus(@NotNull kotlin.coroutines.g gVar) {
            k.f(gVar, "context");
            return this.f.plus(gVar);
        }

        public boolean start() {
            return this.f.start();
        }

        @NotNull
        public f1 t(@NotNull kotlin.jvm.functions.l<? super Throwable, x> lVar) {
            k.f(lVar, "handler");
            return this.f.t(lVar);
        }

        /* compiled from: WeakTimeoutQueue.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<Throwable, x> {
            final /* synthetic */ e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(e eVar) {
                super(1);
                this.this$0 = eVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return x.a;
            }

            public final void invoke(@Nullable Throwable it) {
                if (it != null) {
                    e eVar = this.this$0;
                    o.a aVar = o.Companion;
                    eVar.resumeWith(o.m17constructorimpl(kotlin.p.a(it)));
                    z1.a.a(this.this$0.d(), (CancellationException) null, 1, (Object) null);
                }
            }
        }

        /* compiled from: WeakTimeoutQueue.kt */
        public static final class b extends l implements kotlin.jvm.functions.l<Throwable, x> {
            final /* synthetic */ e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(e eVar) {
                super(1);
                this.this$0 = eVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return x.a;
            }

            public final void invoke(@Nullable Throwable it) {
                e eVar = this.this$0;
                Throwable cancellationException = it != null ? it : new CancellationException();
                o.a aVar = o.Companion;
                eVar.resumeWith(o.m17constructorimpl(kotlin.p.a(cancellationException)));
            }
        }

        public e(@NotNull kotlin.coroutines.g gVar, @NotNull kotlin.coroutines.d<? super T> dVar, @NotNull z1 z1Var) {
            k.f(gVar, "context");
            k.f(dVar, "delegate");
            k.f(z1Var, "job");
            this.f = z1Var;
            this.d = gVar.plus(z1Var);
            this.state = dVar;
            z1 z1Var2 = (z1) gVar.get(z1.g);
            if (z1Var2 != null) {
                z1.a.d(z1Var2, true, false, new a(this), 2, (Object) null);
            }
            z1Var.t(new b(this));
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ e(kotlin.coroutines.g gVar, kotlin.coroutines.d dVar, z1 z1Var, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(gVar, dVar, (i & 4) != 0 ? c2.a((z1) gVar.get(z1.g)) : z1Var);
        }

        @NotNull
        public final z1 d() {
            return this.f;
        }

        @NotNull
        public kotlin.coroutines.g getContext() {
            return this.d;
        }

        @NotNull
        public kotlin.coroutines.g getCoroutineContext() {
            return getContext();
        }

        public void resumeWith(@NotNull Object result) {
            Object cur$iv;
            do {
                cur$iv = this.state;
                if (((kotlin.coroutines.d) cur$iv) == null) {
                    return;
                }
            } while (!c.compareAndSet(this, cur$iv, (Object) null));
            kotlin.coroutines.d it = (kotlin.coroutines.d) cur$iv;
            if (it != null) {
                it.resumeWith(result);
                z1.a.a(this.f, (CancellationException) null, 1, (Object) null);
            }
        }

        public final boolean k() {
            Object cur$iv;
            do {
                cur$iv = this.state;
                if (!(((kotlin.coroutines.d) cur$iv) instanceof kotlin.coroutines.d)) {
                    return false;
                }
            } while (!c.compareAndSet(this, cur$iv, (Object) null));
            z1.a.a(this.f, (CancellationException) null, 1, (Object) null);
            return true;
        }
    }
}
