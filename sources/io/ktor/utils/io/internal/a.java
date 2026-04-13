package io.ktor.utils.io.internal;

import com.leedarson.bean.Constants;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.TypeCastException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CancellableReusableContinuation.kt */
public final class a<T> implements d<T> {
    private static final AtomicReferenceFieldUpdater c;
    private static final AtomicReferenceFieldUpdater d;
    private volatile Object jobCancellationHandler = null;
    private volatile Object state = null;

    static {
        Class<Object> cls = Object.class;
        Class<a> cls2 = a.class;
        c = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, Constants.ACTION_STATE);
        d = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "jobCancellationHandler");
    }

    public final void d(@NotNull T value) {
        k.f(value, "value");
        o.a aVar = o.Companion;
        resumeWith(o.m17constructorimpl(value));
        C0288a aVar2 = (C0288a) d.getAndSet(this, (Object) null);
        if (aVar2 != null) {
            aVar2.a();
        }
    }

    public final void g(@NotNull Throwable cause) {
        k.f(cause, "cause");
        o.a aVar = o.Companion;
        resumeWith(o.m17constructorimpl(p.a(cause)));
        C0288a aVar2 = (C0288a) d.getAndSet(this, (Object) null);
        if (aVar2 != null) {
            aVar2.a();
        }
    }

    @NotNull
    public final Object i(@NotNull d<? super T> actual) {
        k.f(actual, "actual");
        while (true) {
            Object before = this.state;
            if (before == null) {
                if (c.compareAndSet(this, (Object) null, actual)) {
                    k(actual.getContext());
                    return c.d();
                }
            } else if (c.compareAndSet(this, before, (Object) null)) {
                if (!(before instanceof Throwable)) {
                    return before;
                }
                throw ((Throwable) before);
            }
        }
    }

    private final void k(g context) {
        Object cur$iv;
        z1 newJob = (z1) context.get(z1.g);
        C0288a aVar = (C0288a) this.jobCancellationHandler;
        if ((aVar != null ? aVar.b() : null) != newJob) {
            if (newJob == null) {
                C0288a aVar2 = (C0288a) d.getAndSet(this, (Object) null);
                if (aVar2 != null) {
                    aVar2.a();
                    return;
                }
                return;
            }
            C0288a newHandler = new C0288a(this, newJob);
            do {
                cur$iv = this.jobCancellationHandler;
                C0288a relation = (C0288a) cur$iv;
                if (relation != null && relation.b() == newJob) {
                    newHandler.a();
                    return;
                }
            } while (!d.compareAndSet(this, cur$iv, newHandler));
            C0288a oldJob = (C0288a) cur$iv;
            if (oldJob != null) {
                oldJob.a();
            }
        }
    }

    /* access modifiers changed from: private */
    public final void j(a<T>.defpackage.a relation) {
        d.compareAndSet(this, relation, (Object) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000b, code lost:
        r0 = r0.getContext();
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public kotlin.coroutines.g getContext() {
        /*
            r2 = this;
            java.lang.Object r0 = r2.state
            boolean r1 = r0 instanceof kotlin.coroutines.d
            if (r1 != 0) goto L_0x0007
            r0 = 0
        L_0x0007:
            kotlin.coroutines.d r0 = (kotlin.coroutines.d) r0
            if (r0 == 0) goto L_0x0012
            kotlin.coroutines.g r0 = r0.getContext()
            if (r0 == 0) goto L_0x0012
            goto L_0x0014
        L_0x0012:
            kotlin.coroutines.h r0 = kotlin.coroutines.h.INSTANCE
        L_0x0014:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.internal.a.getContext():kotlin.coroutines.g");
    }

    public void resumeWith(@NotNull Object result) {
        Object cur$iv;
        Object upd$iv;
        do {
            cur$iv = this.state;
            Object before = cur$iv;
            if (before == null) {
                upd$iv = o.m20exceptionOrNullimpl(result);
                if (upd$iv == null) {
                    p.b(result);
                    upd$iv = result;
                }
            } else if (before instanceof d) {
                upd$iv = null;
            } else {
                return;
            }
        } while (!c.compareAndSet(this, cur$iv, upd$iv));
        Object before2 = cur$iv;
        if (before2 instanceof d) {
            ((d) before2).resumeWith(result);
        }
    }

    /* access modifiers changed from: private */
    public final void l(z1 job, Throwable exception) {
        Object cur$iv;
        do {
            cur$iv = this.state;
            Object it = cur$iv;
            if (!(it instanceof d) || ((z1) ((d) it).getContext().get(z1.g)) != job) {
                return;
            }
        } while (!c.compareAndSet(this, cur$iv, (Object) null));
        if (cur$iv != null) {
            o.a aVar = o.Companion;
            ((d) cur$iv).resumeWith(o.m17constructorimpl(p.a(exception)));
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<T>");
    }

    /* renamed from: io.ktor.utils.io.internal.a$a  reason: collision with other inner class name */
    /* compiled from: CancellableReusableContinuation.kt */
    public final class C0288a implements l<Throwable, x> {
        private f1 c;
        @NotNull
        private final z1 d;
        final /* synthetic */ a f;

        public C0288a(@NotNull a $outer, z1 job) {
            k.f(job, "job");
            this.f = $outer;
            this.d = job;
            f1 h = z1.a.d(job, true, false, this, 2, (Object) null);
            if (job.isActive()) {
                this.c = h;
            }
        }

        @NotNull
        public final z1 b() {
            return this.d;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            c((Throwable) obj);
            return x.a;
        }

        public void c(@Nullable Throwable cause) {
            this.f.j(this);
            a();
            if (cause != null) {
                this.f.l(this.d, cause);
            }
        }

        public final void a() {
            f1 it = this.c;
            if (it != null) {
                this.c = null;
                it.dispose();
            }
        }
    }
}
