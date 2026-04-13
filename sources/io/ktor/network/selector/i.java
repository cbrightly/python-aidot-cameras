package io.ktor.network.selector;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LockFreeMPSCQueue.kt */
public final class i<E> {
    private static final AtomicReferenceFieldUpdater a;
    private static final AtomicLongFieldUpdater b;
    @NotNull
    public static final Object c = new a();
    public static final b d = new b((DefaultConstructorMarker) null);
    private volatile Object _next = null;
    private volatile long _state = 0;
    private final int e;
    private final AtomicReferenceArray<Object> f;
    private final int g;

    public i(int capacity) {
        this.g = capacity;
        int i = capacity - 1;
        this.e = i;
        this.f = new AtomicReferenceArray<>(capacity);
        boolean z = false;
        if (i <= 1073741823) {
            if (!((i & capacity) == 0 ? true : z)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final boolean f() {
        b bVar = d;
        long $this$withState$iv = this._state;
        if (((int) ((1073741823 & $this$withState$iv) >> 0)) == ((int) ((1152921503533105152L & $this$withState$iv) >> 30))) {
            return true;
        }
        return false;
    }

    public final boolean d() {
        long cur$iv;
        long state;
        do {
            cur$iv = this._state;
            state = cur$iv;
            if ((state & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & state) != 0) {
                return false;
            }
        } while (!b.compareAndSet(this, cur$iv, state | 2305843009213693952L));
        return true;
    }

    public final int a(@NotNull E element) {
        long state;
        int newTail;
        int tail;
        i e2;
        E e3 = element;
        k.f(e3, "element");
        do {
            state = this._state;
            if ((3458764513820540928L & state) != 0) {
                return d.e(state);
            }
            b bVar = d;
            long $this$withState$iv = state;
            int head$iv = (int) (($this$withState$iv & 1073741823) >> 0);
            int tail$iv = (int) (($this$withState$iv & 1152921503533105152L) >> 30);
            int tail2 = tail$iv;
            int i = this.e;
            if (((tail2 + 2) & i) == (head$iv & i)) {
                return 1;
            }
            newTail = (tail2 + 1) & 1073741823;
            tail = tail2;
            int i2 = newTail;
            int i3 = head$iv;
            int i4 = tail$iv;
        } while (!b.compareAndSet(this, state, d.g(state, newTail)));
        this.f.set(this.e & tail, e3);
        i cur = this;
        while ((cur._state & 1152921504606846976L) != 0 && (e2 = cur.h().e(tail, e3)) != null) {
            cur = e2;
        }
        return 0;
    }

    private final i<E> e(int index, E element) {
        Object old = this.f.get(this.e & index);
        if (!(old instanceof c) || ((c) old).a != index) {
            return null;
        }
        this.f.set(this.e & index, element);
        return this;
    }

    @Nullable
    public final Object i() {
        Object obj;
        long state = this._state;
        if ((1152921504606846976L & state) != 0) {
            return c;
        }
        long $this$withState$iv = state;
        b bVar = d;
        int head$iv = (int) ((1073741823 & $this$withState$iv) >> 0);
        int tail$iv = (int) ((1152921503533105152L & $this$withState$iv) >> 30);
        int head = head$iv;
        int i = this.e;
        if ((tail$iv & i) == (head & i) || (obj = this.f.get(i & head)) == null) {
            return null;
        }
        Object element = obj;
        if (element instanceof c) {
            return null;
        }
        int newHead = 1073741823 & (head + 1);
        int newHead2 = newHead;
        Object element2 = element;
        int $i$f$loop = head;
        int i2 = head$iv;
        int i3 = tail$iv;
        if (b.compareAndSet(this, state, d.f(state, newHead))) {
            this.f.set(this.e & $i$f$loop, (Object) null);
            return element2;
        }
        i cur = this;
        while (true) {
            i j = cur.j($i$f$loop, newHead2);
            if (j == null) {
                return element2;
            }
            cur = j;
        }
    }

    private final i<E> j(int oldHead, int newHead) {
        i $this$loop$iv = this;
        while (true) {
            long state = $this$loop$iv._state;
            long $this$withState$iv = state;
            b bVar = d;
            boolean z = false;
            int head$iv = (int) ((1073741823 & $this$withState$iv) >> 0);
            int tail$iv = (int) ((1152921503533105152L & $this$withState$iv) >> 30);
            int head = head$iv;
            int i = tail$iv;
            if (head == oldHead) {
                z = true;
            }
            if (!z) {
                throw new IllegalStateException("This queue can have only one consumer".toString());
            } else if ((state & 1152921504606846976L) != 0) {
                return h();
            } else {
                i $this$loop$iv2 = $this$loop$iv;
                int head2 = head;
                int i2 = head$iv;
                int i3 = tail$iv;
                if (b.compareAndSet(this, state, d.f(state, newHead))) {
                    this.f.set(head2 & this.e, (Object) null);
                    return null;
                }
                $this$loop$iv = $this$loop$iv2;
            }
        }
    }

    @NotNull
    public final i<E> h() {
        return c(g());
    }

    private final long g() {
        long cur$iv;
        long upd$iv;
        do {
            cur$iv = this._state;
            long state = cur$iv;
            if ((state & 1152921504606846976L) != 0) {
                return state;
            }
            upd$iv = state | 1152921504606846976L;
        } while (!b.compareAndSet(this, cur$iv, upd$iv));
        return upd$iv;
    }

    private final i<E> c(long state) {
        while (true) {
            i next = (i) this._next;
            if (next != null) {
                return next;
            }
            a.compareAndSet(this, (Object) null, b(state));
        }
    }

    private final i<E> b(long state) {
        i next = new i(this.g * 2);
        b this_$iv = d;
        long $this$withState$iv = state;
        int tail = (int) ((1152921503533105152L & $this$withState$iv) >> 30);
        int index = (int) ((1073741823 & $this$withState$iv) >> 0);
        while (true) {
            int i = this.e;
            if ((index & i) != (tail & i)) {
                AtomicReferenceArray<Object> atomicReferenceArray = next.f;
                int i2 = next.e & index;
                Object obj = this.f.get(i & index);
                if (obj == null) {
                    obj = new c(index);
                }
                atomicReferenceArray.set(i2, obj);
                index++;
            } else {
                b bVar = this_$iv;
                long j = $this$withState$iv;
                next._state = d.h(state, 1152921504606846976L);
                return next;
            }
        }
    }

    /* compiled from: LockFreeMPSCQueue.kt */
    public static final class c {
        public final int a;

        public c(int index) {
            this.a = index;
        }
    }

    /* compiled from: LockFreeMPSCQueue.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final long h(long $this$wo, long other) {
            return (~other) & $this$wo;
        }

        /* access modifiers changed from: private */
        public final long f(long $this$updateHead, int newHead) {
            return h($this$updateHead, 1073741823) | (((long) newHead) << 0);
        }

        /* access modifiers changed from: private */
        public final long g(long $this$updateTail, int newTail) {
            return h($this$updateTail, 1152921503533105152L) | (((long) newTail) << 30);
        }

        /* access modifiers changed from: private */
        public final int e(long $this$addFailReason) {
            return (2305843009213693952L & $this$addFailReason) != 0 ? 2 : 1;
        }
    }

    /* compiled from: LockFreeMPSCQueue.kt */
    public static final class a {
        a() {
        }

        @NotNull
        public String toString() {
            return "REMOVE_FROZEN";
        }
    }

    static {
        Class<i> cls = i.class;
        a = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_next");
        b = AtomicLongFieldUpdater.newUpdater(cls, "_state");
    }
}
