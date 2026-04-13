package io.ktor.utils.io.jvm.javaio;

import com.leedarson.bean.Constants;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Blocking.kt */
public abstract class a {
    static final AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(a.class, Object.class, Constants.ACTION_STATE);
    /* access modifiers changed from: private */
    public final d<x> b;
    /* access modifiers changed from: private */
    public final f1 c;
    private int d;
    private int e;
    @Nullable
    private final z1 f;
    volatile int result;
    volatile Object state;

    public a() {
        this((z1) null, 1, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract Object g(@NotNull d<? super x> dVar);

    /* compiled from: Blocking.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(a aVar) {
            super(1);
            this.this$0 = aVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable cause) {
            if (cause != null) {
                d b = this.this$0.b;
                o.a aVar = o.Companion;
                b.resumeWith(o.m17constructorimpl(p.a(cause)));
            }
        }
    }

    public a(@Nullable z1 parent) {
        this.f = parent;
        c cVar = new c(this);
        this.b = cVar;
        this.state = this;
        boolean z = false;
        this.result = 0;
        this.c = parent != null ? parent.t(new b(this)) : null;
        ((kotlin.jvm.functions.l) e0.e(new C0291a(this, (d) null), 1)).invoke(cVar);
        if (!(this.state != this ? true : z)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(z1 z1Var, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : z1Var);
    }

    @Nullable
    public final z1 f() {
        return this.f;
    }

    /* compiled from: Blocking.kt */
    public static final class c implements d<x> {
        @NotNull
        private final g c;
        final /* synthetic */ a d;

        c(a $outer) {
            this.d = $outer;
            this.c = $outer.f() != null ? e.c.plus($outer.f()) : e.c;
        }

        @NotNull
        public g getContext() {
            return this.c;
        }

        public void resumeWith(@NotNull Object result) {
            Object cur$iv;
            z1 f;
            Throwable it;
            Object value = o.m20exceptionOrNullimpl(result);
            if (value == null) {
                value = x.a;
            }
            a $this$getAndUpdate$iv = this.d;
            do {
                cur$iv = $this$getAndUpdate$iv.state;
                Object current = cur$iv;
                if ((current instanceof Thread) || (current instanceof d) || k.a(current, this)) {
                } else {
                    return;
                }
            } while (!a.a.compareAndSet($this$getAndUpdate$iv, cur$iv, value));
            Object before = cur$iv;
            if (before instanceof Thread) {
                LockSupport.unpark((Thread) before);
            } else if ((before instanceof d) && (it = o.m20exceptionOrNullimpl(result)) != null) {
                o.a aVar = o.Companion;
                ((d) before).resumeWith(o.m17constructorimpl(p.a(it)));
            }
            if (o.m22isFailureimpl(result) && !(o.m20exceptionOrNullimpl(result) instanceof CancellationException) && (f = this.d.f()) != null) {
                z1.a.a(f, (CancellationException) null, 1, (Object) null);
            }
            f1 a = this.d.c;
            if (a != null) {
                a.dispose();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final int e() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public final int d() {
        return this.e;
    }

    @f(c = "io.ktor.utils.io.jvm.javaio.BlockingAdapter$block$1", f = "Blocking.kt", l = {171}, m = "invokeSuspend")
    /* renamed from: io.ktor.utils.io.jvm.javaio.a$a  reason: collision with other inner class name */
    /* compiled from: Blocking.kt */
    public static final class C0291a extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.l<d<? super x>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0291a(a aVar, d dVar) {
            super(1, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final d<x> create(@NotNull d<?> dVar) {
            k.f(dVar, "completion");
            return new C0291a(this.this$0, dVar);
        }

        public final Object invoke(Object obj) {
            return ((C0291a) create((d) obj)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b($result);
                    a aVar = this.this$0;
                    this.label = 1;
                    if (aVar.g(this) == d) {
                        return d;
                    }
                    break;
                case 1:
                    p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    public final void i() {
        f1 f1Var = this.c;
        if (f1Var != null) {
            f1Var.dispose();
        }
        d<x> dVar = this.b;
        CancellationException cancellationException = new CancellationException("Stream closed");
        o.a aVar = o.Companion;
        dVar.resumeWith(o.m17constructorimpl(p.a(cancellationException)));
    }

    public final int k(@NotNull byte[] buffer, int offset, int length) {
        k.f(buffer, "buffer");
        this.d = offset;
        this.e = length;
        return j(buffer);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: kotlin.coroutines.d} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int j(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.String r0 = "jobToken"
            kotlin.jvm.internal.k.f(r10, r0)
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            if (r0 != 0) goto L_0x000e
            kotlin.jvm.internal.k.n()
        L_0x000e:
            r1 = 0
            r2 = r9
            r3 = 0
        L_0x0011:
            java.lang.Object r4 = r2.state
            r5 = r4
            r6 = 0
            boolean r7 = r5 instanceof kotlin.coroutines.d
            if (r7 == 0) goto L_0x002b
            if (r5 == 0) goto L_0x0023
            r1 = r5
            kotlin.coroutines.d r1 = (kotlin.coroutines.d) r1
            r7 = r0
            goto L_0x0045
        L_0x0023:
            kotlin.TypeCastException r7 = new kotlin.TypeCastException
            java.lang.String r8 = "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any>"
            r7.<init>(r8)
            throw r7
        L_0x002b:
            boolean r7 = r5 instanceof kotlin.x
            if (r7 == 0) goto L_0x0032
            int r7 = r9.result
            return r7
        L_0x0032:
            boolean r7 = r5 instanceof java.lang.Throwable
            if (r7 != 0) goto L_0x0081
            boolean r7 = r5 instanceof java.lang.Thread
            if (r7 != 0) goto L_0x0079
            boolean r7 = kotlin.jvm.internal.k.a(r5, r9)
            if (r7 != 0) goto L_0x0071
            kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
            r7.<init>()
        L_0x0045:
            r5 = r7
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r6 = a
            boolean r6 = r6.compareAndSet(r2, r4, r5)
            if (r6 == 0) goto L_0x0070
            if (r1 != 0) goto L_0x0054
            kotlin.jvm.internal.k.n()
        L_0x0054:
            kotlin.o$a r2 = kotlin.o.Companion
            java.lang.Object r2 = kotlin.o.m17constructorimpl(r10)
            r1.resumeWith(r2)
            r9.h(r0)
            java.lang.Object r2 = r9.state
            r3 = 0
            boolean r4 = r2 instanceof java.lang.Throwable
            if (r4 != 0) goto L_0x006c
            int r2 = r9.result
            return r2
        L_0x006c:
            r4 = r2
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            throw r4
        L_0x0070:
            goto L_0x0011
        L_0x0071:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "Not yet started"
            r7.<init>(r8)
            throw r7
        L_0x0079:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "There is already thread owning adapter"
            r7.<init>(r8)
            throw r7
        L_0x0081:
            r7 = r5
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.jvm.javaio.a.j(java.lang.Object):int");
    }

    private final void h(Thread thread) {
        if (this.state == thread) {
            io.ktor.utils.io.internal.b eventLoop = io.ktor.utils.io.internal.c.a();
            while (true) {
                long nextEventTimeNanos = eventLoop.a();
                if (this.state == thread) {
                    if (nextEventTimeNanos > 0) {
                        LockSupport.parkNanos(nextEventTimeNanos);
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void c(int rc) {
        this.result = rc;
    }
}
