package io.ktor.util.pipeline;

import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.y;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.e;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PipelineContext.kt */
public final class l<TSubject, TContext> implements d<TSubject, TContext>, f<TSubject>, o0 {
    /* access modifiers changed from: private */
    public int c = -1;
    private final d<x> d = new a(this);
    @NotNull
    private TSubject f;
    /* access modifiers changed from: private */
    public Object q;
    private int x;
    @NotNull
    private final TContext y;
    private final List<q<d<TSubject, TContext>, TSubject, d<? super x>, Object>> z;

    public l(@NotNull TSubject initial, @NotNull TContext context, @NotNull List<? extends q<? super d<TSubject, TContext>, ? super TSubject, ? super d<? super x>, ? extends Object>> blocks) {
        k.f(initial, "initial");
        k.f(context, "context");
        k.f(blocks, "blocks");
        this.y = context;
        this.z = blocks;
        this.f = initial;
    }

    @NotNull
    public TContext getContext() {
        return this.y;
    }

    @NotNull
    public g getCoroutineContext() {
        return this.d.getContext();
    }

    /* compiled from: PipelineContext.kt */
    public static final class a implements d<x>, e {
        final /* synthetic */ l c;

        a(l $outer) {
            this.c = $outer;
        }

        @Nullable
        public e getCallerFrame() {
            d<?> a = a();
            if (!(a instanceof e)) {
                a = null;
            }
            return (e) a;
        }

        @Nullable
        public StackTraceElement getStackTraceElement() {
            return null;
        }

        private final d<?> a() {
            Object rootContinuation;
            if (this.c.c < 0 || (rootContinuation = this.c.q) == null) {
                return null;
            }
            if (rootContinuation instanceof d) {
                l lVar = this.c;
                lVar.c = lVar.c - 1;
                int unused = lVar.c;
                return (d) rootContinuation;
            } else if (!(rootContinuation instanceof ArrayList)) {
                return null;
            } else {
                if (((ArrayList) rootContinuation).isEmpty()) {
                    return k.c;
                }
                return c((List) rootContinuation);
            }
        }

        private final d<?> c(List<? extends d<?>> list) {
            try {
                int index = this.c.c;
                d result = (d) y.V(list, index);
                if (result == null) {
                    return k.c;
                }
                this.c.c = index - 1;
                return result;
            } catch (Throwable th) {
                return k.c;
            }
        }

        @NotNull
        public g getContext() {
            Object cont = this.c.q;
            if (cont == null) {
                throw new IllegalStateException("Not started");
            } else if (cont instanceof d) {
                return ((d) cont).getContext();
            } else {
                if (cont instanceof List) {
                    return ((d) y.d0((List) cont)).getContext();
                }
                throw new IllegalStateException("Unexpected rootContinuation value");
            }
        }

        public void resumeWith(@NotNull Object result) {
            if (o.m22isFailureimpl(result)) {
                l lVar = this.c;
                o.a aVar = o.Companion;
                Throwable r1 = o.m20exceptionOrNullimpl(result);
                if (r1 == null) {
                    k.n();
                }
                lVar.k(o.m17constructorimpl(p.a(r1)));
                return;
            }
            boolean unused = this.c.i(false);
        }
    }

    @NotNull
    public TSubject l() {
        return this.f;
    }

    public void o() {
        this.x = this.z.size();
    }

    @Nullable
    public Object j(@NotNull d<? super TSubject> $completion) {
        Object obj;
        d continuation = $completion;
        if (this.x == this.z.size()) {
            obj = l();
        } else {
            g(continuation);
            if (i(true)) {
                h();
                obj = l();
            } else {
                obj = c.d();
            }
        }
        if (obj == c.d()) {
            h.c($completion);
        }
        return obj;
    }

    @Nullable
    public Object E(@NotNull TSubject subject, @NotNull d<? super TSubject> $completion) {
        this.f = subject;
        return j($completion);
    }

    @Nullable
    public Object a(@NotNull TSubject initial, @NotNull d<? super TSubject> $completion) {
        this.x = 0;
        if (0 == this.z.size()) {
            return initial;
        }
        this.f = initial;
        if (this.q == null) {
            return j($completion);
        }
        throw new IllegalStateException("Already started");
    }

    /* access modifiers changed from: private */
    public final boolean i(boolean direct) {
        while (true) {
            int index = this.x;
            if (index != this.z.size()) {
                this.x = index + 1;
                q next = this.z.get(index);
                try {
                    Object arg$iv = l();
                    d continuation$iv = this.d;
                    q $this$startCoroutineUninterceptedOrReturn3$iv = next;
                    if ($this$startCoroutineUninterceptedOrReturn3$iv == null) {
                        throw new TypeCastException("null cannot be cast to non-null type (R, A, kotlin.coroutines.Continuation<kotlin.Unit>) -> kotlin.Any?");
                    } else if (((q) e0.e($this$startCoroutineUninterceptedOrReturn3$iv, 3)).invoke(this, arg$iv, continuation$iv) == c.d()) {
                        return false;
                    }
                } catch (Throwable cause) {
                    o.a aVar = o.Companion;
                    k(o.m17constructorimpl(p.a(cause)));
                    return false;
                }
            } else if (direct) {
                return true;
            } else {
                o.a aVar2 = o.Companion;
                k(o.m17constructorimpl(l()));
                return false;
            }
        }
    }

    /* access modifiers changed from: private */
    public final void k(Object result) {
        Object obj;
        Object rootContinuation = this.q;
        if (rootContinuation != null) {
            if (rootContinuation instanceof d) {
                this.q = null;
                this.c = -1;
                obj = rootContinuation;
            } else if (!(rootContinuation instanceof ArrayList)) {
                m(rootContinuation);
                throw null;
            } else if (!((ArrayList) rootContinuation).isEmpty()) {
                this.c = kotlin.collections.q.i((List) rootContinuation) - 1;
                obj = ((ArrayList) rootContinuation).remove(kotlin.collections.q.i((List) rootContinuation));
            } else {
                throw new IllegalStateException("No more continuations to resume");
            }
            if (obj != null) {
                d next = (d) obj;
                if (!o.m22isFailureimpl(result)) {
                    next.resumeWith(result);
                    return;
                }
                Throwable r2 = o.m20exceptionOrNullimpl(result);
                if (r2 == null) {
                    k.n();
                }
                Throwable exception = i.a(r2, next);
                o.a aVar = o.Companion;
                next.resumeWith(o.m17constructorimpl(p.a(exception)));
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<TSubject>");
        }
        throw new IllegalStateException("No more continuations to resume");
    }

    private final void h() {
        Object rootContinuation = this.q;
        if (rootContinuation == null) {
            throw new IllegalStateException("No more continuations to resume");
        } else if (rootContinuation instanceof d) {
            this.c = -1;
            this.q = null;
        } else if (!(rootContinuation instanceof ArrayList)) {
            m(rootContinuation);
            throw null;
        } else if (!((ArrayList) rootContinuation).isEmpty()) {
            ((ArrayList) rootContinuation).remove(kotlin.collections.q.i((List) rootContinuation));
            this.c = kotlin.collections.q.i((List) rootContinuation);
        } else {
            throw new IllegalStateException("No more continuations to resume");
        }
    }

    private final void g(d<? super TSubject> continuation) {
        Object rootContinuation = this.q;
        if (rootContinuation == null) {
            this.c = 0;
            this.q = continuation;
        } else if (rootContinuation instanceof d) {
            ArrayList arrayList = new ArrayList(this.z.size());
            ArrayList $this$apply = arrayList;
            $this$apply.add(rootContinuation);
            $this$apply.add(continuation);
            this.c = 1;
            this.q = arrayList;
        } else if (rootContinuation instanceof ArrayList) {
            ArrayList arrayList2 = (ArrayList) rootContinuation;
            ((ArrayList) rootContinuation).add(continuation);
            this.c = kotlin.collections.q.i((List) rootContinuation);
        } else {
            m(rootContinuation);
            throw null;
        }
    }

    private final Void m(Object rootContinuation) {
        throw new IllegalStateException("Unexpected rootContinuation content: " + rootContinuation);
    }
}
