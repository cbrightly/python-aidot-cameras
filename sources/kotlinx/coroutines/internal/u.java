package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.l;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0016\b\u0000\u0018\u0000 /*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001:\u0002/0B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\n\u001a\u00020\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0000j\b\u0012\u0004\u0012\u00028\u0000`\u000e2\u0006\u0010\r\u001a\u00020\fH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J'\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0000j\b\u0012\u0004\u0012\u00028\u0000`\u000e2\u0006\u0010\r\u001a\u00020\fH\u0002¢\u0006\u0004\b\u0011\u0010\u0010J\r\u0010\u0012\u001a\u00020\u0005¢\u0006\u0004\b\u0012\u0010\u0013J3\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u000e2\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\u0015\u0010\u0016J\r\u0010\u0017\u001a\u00020\u0005¢\u0006\u0004\b\u0017\u0010\u0013J-\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00010\u001b\"\u0004\b\u0001\u0010\u00182\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0019¢\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\fH\u0002¢\u0006\u0004\b\u001e\u0010\u001fJ\u0013\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\u0004\b \u0010!J\u000f\u0010\"\u001a\u0004\u0018\u00010\u0001¢\u0006\u0004\b\"\u0010#J3\u0010&\u001a\u0016\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u000e2\u0006\u0010$\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u0003H\u0002¢\u0006\u0004\b&\u0010'R\u0014\u0010\u0004\u001a\u00020\u00038\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010(R\u0011\u0010)\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b)\u0010\u0013R\u0014\u0010*\u001a\u00020\u00038\u0002X\u0004¢\u0006\u0006\n\u0004\b*\u0010(R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010+R\u0011\u0010.\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b,\u0010-¨\u00061"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "", "E", "", "capacity", "", "singleConsumer", "<init>", "(IZ)V", "element", "addLast", "(Ljava/lang/Object;)I", "", "state", "Lkotlinx/coroutines/internal/Core;", "allocateNextCopy", "(J)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "allocateOrGetNextCopy", "close", "()Z", "index", "fillPlaceholder", "(ILjava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "isClosed", "R", "Lkotlin/Function1;", "transform", "", "map", "(Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "markFrozen", "()J", "next", "()Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "removeFirstOrNull", "()Ljava/lang/Object;", "oldHead", "newHead", "removeSlowPath", "(II)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "I", "isEmpty", "mask", "Z", "getSize", "()I", "size", "Companion", "Placeholder", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: LockFreeTaskQueue.kt */
public final class u<E> {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    private static final /* synthetic */ AtomicReferenceFieldUpdater b;
    private static final /* synthetic */ AtomicLongFieldUpdater c;
    @NotNull
    public static final f0 d = new f0("REMOVE_FROZEN");
    @NotNull
    private volatile /* synthetic */ Object _next = null;
    @NotNull
    private volatile /* synthetic */ long _state = 0;
    private final int e;
    private final boolean f;
    private final int g;
    @NotNull
    private /* synthetic */ AtomicReferenceArray h;

    public u(int capacity, boolean singleConsumer) {
        this.e = capacity;
        this.f = singleConsumer;
        int i = capacity - 1;
        this.g = i;
        this.h = new AtomicReferenceArray(capacity);
        boolean z = false;
        if (i <= 1073741823) {
            if (!((i & capacity) == 0 ? true : z)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final boolean g() {
        a aVar = a;
        long $this$withState$iv = this._state;
        if (((int) ((1073741823 & $this$withState$iv) >> 0)) == ((int) ((1152921503533105152L & $this$withState$iv) >> 30))) {
            return true;
        }
        return false;
    }

    public final int f() {
        a aVar = a;
        long $this$withState$iv = this._state;
        return (((int) ((1152921503533105152L & $this$withState$iv) >> 30)) - ((int) ((1073741823 & $this$withState$iv) >> 0))) & 1073741823;
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
        } while (!c.compareAndSet(this, cur$iv, state | 2305843009213693952L));
        return true;
    }

    public final int a(@NotNull E element) {
        u e2;
        E e3 = element;
        while (true) {
            long state = this._state;
            if ((3458764513820540928L & state) != 0) {
                return a.a(state);
            }
            a aVar = a;
            long $this$withState$iv = state;
            int head$iv = (int) (($this$withState$iv & 1073741823) >> 0);
            int tail$iv = (int) (($this$withState$iv & 1152921503533105152L) >> 30);
            int head = head$iv;
            int tail = tail$iv;
            int mask = this.g;
            if (((tail + 2) & mask) == (head & mask)) {
                return 1;
            }
            if (this.f || this.h.get(tail & mask) == null) {
                int tail2 = tail;
                int mask2 = mask;
                int i = head$iv;
                int i2 = tail$iv;
                if (c.compareAndSet(this, state, a.c(state, (tail + 1) & 1073741823))) {
                    this.h.set(tail2 & mask2, e3);
                    u cur = this;
                    while ((cur._state & 1152921504606846976L) != 0 && (e2 = cur.i().e(tail2, e3)) != null) {
                        cur = e2;
                    }
                    return 0;
                }
            } else {
                int i3 = this.e;
                if (i3 < 1024 || (1073741823 & (tail - head)) > (i3 >> 1)) {
                    return 1;
                }
            }
        }
        return 1;
    }

    private final u<E> e(int index, E element) {
        Object old = this.h.get(this.g & index);
        if (!(old instanceof b) || ((b) old).a != index) {
            return null;
        }
        this.h.set(this.g & index, element);
        return this;
    }

    @Nullable
    public final Object j() {
        int $i$f$loop;
        u $this$loop$iv = this;
        int $i$f$loop2 = 0;
        while (true) {
            long state = $this$loop$iv._state;
            if ((1152921504606846976L & state) != 0) {
                return d;
            }
            a aVar = a;
            long $this$withState$iv = state;
            int head$iv = (int) ((1073741823 & $this$withState$iv) >> 0);
            int tail$iv = (int) ((1152921503533105152L & $this$withState$iv) >> 30);
            int head = head$iv;
            int i = this.g;
            u $this$loop$iv2 = $this$loop$iv;
            if ((tail$iv & i) == (head & i)) {
                return null;
            }
            Object element = this.h.get(i & head);
            if (element == null) {
                if (this.f) {
                    return null;
                }
                $i$f$loop = $i$f$loop2;
            } else if (element instanceof b) {
                return null;
            } else {
                int newHead = 1073741823 & (head + 1);
                int newHead2 = newHead;
                Object element2 = element;
                $i$f$loop = $i$f$loop2;
                int $i$f$loop3 = head;
                int i2 = head$iv;
                int i3 = tail$iv;
                if (c.compareAndSet(this, state, a.b(state, newHead))) {
                    this.h.set(this.g & $i$f$loop3, (Object) null);
                    return element2;
                } else if (this.f) {
                    u cur = this;
                    while (true) {
                        u k = cur.k($i$f$loop3, newHead2);
                        if (k == null) {
                            return element2;
                        }
                        cur = k;
                    }
                }
            }
            $this$loop$iv = $this$loop$iv2;
            $i$f$loop2 = $i$f$loop;
        }
    }

    private final u<E> k(int oldHead, int newHead) {
        u $this$loop$iv = this;
        while (true) {
            long state = $this$loop$iv._state;
            a aVar = a;
            long $this$withState$iv = state;
            boolean z = false;
            int head$iv = (int) ((1073741823 & $this$withState$iv) >> 0);
            int tail$iv = (int) ((1152921503533105152L & $this$withState$iv) >> 30);
            int head = head$iv;
            int i = tail$iv;
            if (s0.a()) {
                if (head == oldHead) {
                    z = true;
                }
                if (!z) {
                    throw new AssertionError();
                }
            } else {
                int i2 = oldHead;
            }
            if ((state & 1152921504606846976L) != 0) {
                return i();
            }
            u $this$loop$iv2 = $this$loop$iv;
            int head2 = head;
            int i3 = head$iv;
            int i4 = tail$iv;
            if (c.compareAndSet(this, state, a.b(state, newHead))) {
                this.h.set(head2 & this.g, (Object) null);
                return null;
            }
            $this$loop$iv = $this$loop$iv2;
        }
    }

    @NotNull
    public final u<E> i() {
        return c(h());
    }

    private final long h() {
        long cur$iv;
        long upd$iv;
        do {
            cur$iv = this._state;
            long state = cur$iv;
            if ((state & 1152921504606846976L) != 0) {
                return state;
            }
            upd$iv = state | 1152921504606846976L;
        } while (!c.compareAndSet(this, cur$iv, upd$iv));
        return upd$iv;
    }

    private final u<E> c(long state) {
        while (true) {
            u next = (u) this._next;
            if (next != null) {
                return next;
            }
            b.compareAndSet(this, (Object) null, b(state));
        }
    }

    private final u<E> b(long state) {
        u next = new u(this.e * 2, this.f);
        a this_$iv = a;
        long $this$withState$iv = state;
        int tail = (int) ((1152921503533105152L & $this$withState$iv) >> 30);
        int index = (int) ((1073741823 & $this$withState$iv) >> 0);
        while (true) {
            int i = this.g;
            if ((index & i) != (tail & i)) {
                Object value = this.h.get(i & index);
                if (value == null) {
                    value = new b(index);
                }
                next.h.set(next.g & index, value);
                index++;
            } else {
                a aVar = this_$iv;
                long j = $this$withState$iv;
                next._state = a.d(state, 1152921504606846976L);
                return next;
            }
        }
    }

    @l(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore$Placeholder;", "", "index", "", "(I)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: LockFreeTaskQueue.kt */
    public static final class b {
        public final int a;

        public b(int index) {
            this.a = index;
        }
    }

    @l(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\n\u0010\u0016\u001a\u00020\u0004*\u00020\tJ\u0012\u0010\u0017\u001a\u00020\t*\u00020\t2\u0006\u0010\u0018\u001a\u00020\u0004J\u0012\u0010\u0019\u001a\u00020\t*\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0004JP\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0001\u0010\u001c*\u00020\t26\u0010\u001d\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b(!\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u001c0\u001eH\b¢\u0006\u0002\u0010#J\u0015\u0010$\u001a\u00020\t*\u00020\t2\u0006\u0010%\u001a\u00020\tH\u0004R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u00138\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore$Companion;", "", "()V", "ADD_CLOSED", "", "ADD_FROZEN", "ADD_SUCCESS", "CAPACITY_BITS", "CLOSED_MASK", "", "CLOSED_SHIFT", "FROZEN_MASK", "FROZEN_SHIFT", "HEAD_MASK", "HEAD_SHIFT", "INITIAL_CAPACITY", "MAX_CAPACITY_MASK", "MIN_ADD_SPIN_CAPACITY", "REMOVE_FROZEN", "Lkotlinx/coroutines/internal/Symbol;", "TAIL_MASK", "TAIL_SHIFT", "addFailReason", "updateHead", "newHead", "updateTail", "newTail", "withState", "T", "block", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "head", "tail", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "wo", "other", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: LockFreeTaskQueue.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        public final long d(long $this$wo, long other) {
            return (~other) & $this$wo;
        }

        public final long b(long $this$updateHead, int newHead) {
            return d($this$updateHead, 1073741823) | (((long) newHead) << 0);
        }

        public final long c(long $this$updateTail, int newTail) {
            return d($this$updateTail, 1152921503533105152L) | (((long) newTail) << 30);
        }

        public final int a(long $this$addFailReason) {
            return (2305843009213693952L & $this$addFailReason) != 0 ? 2 : 1;
        }
    }

    static {
        Class<u> cls = u.class;
        b = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_next");
        c = AtomicLongFieldUpdater.newUpdater(cls, "_state");
    }
}
