package kotlinx.coroutines.sync;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.internal.a0;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import kotlinx.coroutines.p;
import kotlinx.coroutines.q;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\b\u0000\u0018\u00002\u00020\u00112\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00110 :\u0006$%&'()B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0007\u0010\bJ\u001d\u0010\n\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H@ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001d\u0010\f\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H@ø\u0001\u0000¢\u0006\u0004\b\f\u0010\u000bJT\u0010\u0014\u001a\u00020\t\"\u0004\b\u0000\u0010\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\"\u0010\u0013\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0010H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0017\u001a\u00020\u0016H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J\u0019\u0010\u0019\u001a\u00020\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0004\b\u0019\u0010\bJ\u0019\u0010\u001a\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u00018VX\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001f\u001a\u00020\u00018@X\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001dR\"\u0010#\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00110 8VX\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"\u0002\u0004\n\u0002\b\u0019¨\u0006*"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl;", "", "locked", "<init>", "(Z)V", "", "owner", "holdsLock", "(Ljava/lang/Object;)Z", "", "lock", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lockSuspend", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/sync/Mutex;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectClause2", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "", "toString", "()Ljava/lang/String;", "tryLock", "unlock", "(Ljava/lang/Object;)V", "isLocked", "()Z", "isLockedEmptyQueueState$kotlinx_coroutines_core", "isLockedEmptyQueueState", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnLock", "()Lkotlinx/coroutines/selects/SelectClause2;", "onLock", "LockCont", "LockSelect", "LockWaiter", "LockedQueue", "TryLockDesc", "UnlockOp", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Mutex.kt */
public final class c implements b {
    static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(c.class, Object.class, "_state");
    @NotNull
    volatile /* synthetic */ Object _state;

    public c(boolean locked) {
        this._state = locked ? d.e : d.f;
    }

    public boolean d(@Nullable Object owner) {
        while (true) {
            Object state = this._state;
            boolean z = true;
            if (state instanceof a) {
                if (((a) state).a != d.d) {
                    return false;
                }
                if (a.compareAndSet(this, state, owner == null ? d.e : new a(owner))) {
                    return true;
                }
            } else if (state instanceof C0452c) {
                if (((C0452c) state).q == owner) {
                    z = false;
                }
                if (z) {
                    return false;
                }
                throw new IllegalStateException(k.l("Already locked by ", owner).toString());
            } else if (state instanceof a0) {
                ((a0) state).c(this);
            } else {
                throw new IllegalStateException(k.l("Illegal state ", state).toString());
            }
        }
    }

    @Nullable
    public Object a(@Nullable Object owner, @NotNull kotlin.coroutines.d<? super x> $completion) {
        if (d(owner)) {
            return x.a;
        }
        Object c = c(owner, $completion);
        return c == kotlin.coroutines.intrinsics.c.d() ? c : x.a;
    }

    private final Object c(Object owner, kotlin.coroutines.d<? super x> $completion) {
        Object obj = owner;
        o cancellable$iv = q.b(kotlin.coroutines.intrinsics.b.c($completion));
        n cont = cancellable$iv;
        a aVar = new a(obj, cont);
        while (true) {
            Object state = this._state;
            if (state instanceof a) {
                if (((a) state).a != d.d) {
                    a.compareAndSet(this, state, new C0452c(((a) state).a));
                } else {
                    if (a.compareAndSet(this, state, obj == null ? d.e : new a(obj))) {
                        cont.h(x.a, new e(this, obj));
                        break;
                    }
                }
            } else if (state instanceof C0452c) {
                if (((C0452c) state).q != obj) {
                    ((C0452c) state).g(aVar);
                    if (this._state == state || !aVar.z()) {
                        q.c(cont, aVar);
                    } else {
                        aVar = new a(obj, cont);
                    }
                } else {
                    throw new IllegalStateException(k.l("Already locked by ", obj).toString());
                }
            } else if (state instanceof a0) {
                ((a0) state).c(this);
            } else {
                throw new IllegalStateException(k.l("Illegal state ", state).toString());
            }
        }
        q.c(cont, aVar);
        Object t = cancellable$iv.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            h.c($completion);
        }
        return t == kotlin.coroutines.intrinsics.c.d() ? t : x.a;
    }

    @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: Mutex.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ Object $owner;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(c cVar, Object obj) {
            super(1);
            this.this$0 = cVar;
            this.$owner = obj;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((Throwable) p1);
            return x.a;
        }

        public final void invoke(@NotNull Throwable it) {
            this.this$0.b(this.$owner);
        }
    }

    public void b(@Nullable Object owner) {
        while (true) {
            Object state = this._state;
            boolean z = true;
            if (state instanceof a) {
                if (owner == null) {
                    if (((a) state).a == d.d) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException("Mutex is not locked".toString());
                    }
                } else {
                    if (((a) state).a != owner) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException(("Mutex is locked by " + ((a) state).a + " but expected " + owner).toString());
                    }
                }
                if (a.compareAndSet(this, state, d.f)) {
                    return;
                }
            } else if (state instanceof a0) {
                ((a0) state).c(this);
            } else if (state instanceof C0452c) {
                if (owner != null) {
                    if (((C0452c) state).q != owner) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException(("Mutex is locked by " + ((C0452c) state).q + " but expected " + owner).toString());
                    }
                }
                s waiter = ((C0452c) state).u();
                if (waiter == null) {
                    d op = new d((C0452c) state);
                    if (a.compareAndSet(this, state, op) && op.c(this) == null) {
                        return;
                    }
                } else if (((b) waiter).A()) {
                    C0452c cVar = (C0452c) state;
                    Object obj = ((b) waiter).x;
                    if (obj == null) {
                        obj = d.c;
                    }
                    cVar.q = obj;
                    ((b) waiter).y();
                    return;
                }
            } else {
                throw new IllegalStateException(k.l("Illegal state ", state).toString());
            }
        }
    }

    @NotNull
    public String toString() {
        while (true) {
            Object state = this._state;
            if (state instanceof a) {
                return "Mutex[" + ((a) state).a + ']';
            } else if (state instanceof a0) {
                ((a0) state).c(this);
            } else if (state instanceof C0452c) {
                return "Mutex[" + ((C0452c) state).q + ']';
            } else {
                throw new IllegalStateException(k.l("Illegal state ", state).toString());
            }
        }
    }

    @l(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "owner", "", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: kotlinx.coroutines.sync.c$c  reason: collision with other inner class name */
    /* compiled from: Mutex.kt */
    public static final class C0452c extends kotlinx.coroutines.internal.q {
        @NotNull
        public Object q;

        public C0452c(@NotNull Object owner) {
            this.q = owner;
        }

        @NotNull
        public String toString() {
            return "LockedQueue[" + this.q + ']';
        }
    }

    @l(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b¢\u0004\u0018\u00002\u00020\u000f2\u00020\u0010B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H&¢\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\b\u001a\u00020\u0005¢\u0006\u0004\b\b\u0010\u0007J\r\u0010\n\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\tH&¢\u0006\u0004\b\f\u0010\u000bR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00018\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "", "owner", "<init>", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;)V", "", "completeResumeLockWaiter", "()V", "dispose", "", "take", "()Z", "tryResumeLockWaiter", "Ljava/lang/Object;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/DisposableHandle;"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: Mutex.kt */
    public abstract class b extends s implements f1 {
        private static final /* synthetic */ AtomicIntegerFieldUpdater q = AtomicIntegerFieldUpdater.newUpdater(b.class, "isTaken");
        @NotNull
        private volatile /* synthetic */ int isTaken = 0;
        @Nullable
        public final Object x;

        public abstract boolean A();

        public abstract void y();

        public b(@Nullable Object owner) {
            this.x = owner;
        }

        public final boolean z() {
            return q.compareAndSet(this, 0, 1);
        }

        public final void dispose() {
            t();
        }
    }

    @l(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u001d\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0007H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockCont;", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeLockWaiter", "toString", "", "tryResumeLockWaiter", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: Mutex.kt */
    public final class a extends b {
        @NotNull
        private final n<x> z;

        public a(@Nullable Object owner, @NotNull n<? super x> cont) {
            super(owner);
            this.z = cont;
        }

        @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
        /* renamed from: kotlinx.coroutines.sync.c$a$a  reason: collision with other inner class name */
        /* compiled from: Mutex.kt */
        public static final class C0451a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
            final /* synthetic */ c this$0;
            final /* synthetic */ a this$1;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0451a(c cVar, a aVar) {
                super(1);
                this.this$0 = cVar;
                this.this$1 = aVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((Throwable) p1);
                return x.a;
            }

            public final void invoke(@NotNull Throwable it) {
                this.this$0.b(this.this$1.x);
            }
        }

        public boolean A() {
            if (z() && this.z.A(x.a, (Object) null, new C0451a(c.this, this)) != null) {
                return true;
            }
            return false;
        }

        public void y() {
            this.z.G(p.a);
        }

        @NotNull
        public String toString() {
            return "LockCont[" + this.x + ", " + this.z + "] for " + c.this;
        }
    }

    @l(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001a\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00022\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\b\u001a\u00020\u0002H\u0016R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "Lkotlinx/coroutines/sync/MutexImpl;", "queue", "Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "(Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;)V", "complete", "", "affected", "failure", "", "prepare", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: Mutex.kt */
    public static final class d extends kotlinx.coroutines.internal.d<c> {
        @NotNull
        public final C0452c b;

        public d(@NotNull C0452c queue) {
            this.b = queue;
        }

        @Nullable
        /* renamed from: i */
        public Object g(@NotNull c affected) {
            if (this.b.y()) {
                return null;
            }
            return d.b;
        }

        /* renamed from: h */
        public void d(@NotNull c affected, @Nullable Object failure) {
            c.a.compareAndSet(affected, this, failure == null ? d.f : this.b);
        }
    }
}
