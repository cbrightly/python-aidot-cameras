package io.ktor.util.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LockFreeLinkedList.kt */
public class c {
    static final AtomicReferenceFieldUpdater c;
    static final AtomicReferenceFieldUpdater d;
    private static final AtomicReferenceFieldUpdater f;
    volatile Object _next = this;
    volatile Object _prev = this;
    private volatile Object _removedRef = null;

    static {
        Class<Object> cls = Object.class;
        Class<c> cls2 = c.class;
        c = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_next");
        d = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_prev");
        f = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_removedRef");
    }

    private final e p() {
        e eVar = (e) this._removedRef;
        if (eVar != null) {
            return eVar;
        }
        e it = new e(this);
        f.lazySet(this, it);
        return it;
    }

    public final boolean l() {
        return h() instanceof e;
    }

    @NotNull
    public final Object h() {
        while (true) {
            Object next = this._next;
            if (!(next instanceof d)) {
                return next;
            }
            ((d) next).a(this);
        }
    }

    @NotNull
    public final c i() {
        return b.a(h());
    }

    @NotNull
    public final Object j() {
        while (true) {
            Object prev = this._prev;
            if (prev instanceof e) {
                return prev;
            }
            if (prev != null) {
                c cVar = (c) prev;
                if (((c) prev).h() == this) {
                    return prev;
                }
                d((c) prev, (d) null);
            } else {
                throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Node /* = io.ktor.util.internal.LockFreeLinkedListNode */");
            }
        }
    }

    public final void a(@NotNull c node) {
        Object j;
        k.f(node, "node");
        do {
            j = j();
            if (j == null) {
                throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Node /* = io.ktor.util.internal.LockFreeLinkedListNode */");
            }
        } while (!((c) j).b(node, this));
    }

    public final boolean b(@NotNull c node, @NotNull c next) {
        k.f(node, "node");
        k.f(next, "next");
        d.lazySet(node, this);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = c;
        atomicReferenceFieldUpdater.lazySet(node, next);
        if (!atomicReferenceFieldUpdater.compareAndSet(this, next, node)) {
            return false;
        }
        node.f(next);
        return true;
    }

    public boolean n() {
        Object next;
        do {
            next = h();
            if ((next instanceof e) || next == this) {
                return false;
            }
            if (next != null) {
            } else {
                throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Node /* = io.ktor.util.internal.LockFreeLinkedListNode */");
            }
        } while (!c.compareAndSet(this, next, ((c) next).p()));
        g((c) next);
        return true;
    }

    private final void f(c next) {
        Object nextPrev;
        c $this$loop$iv = next;
        do {
            nextPrev = $this$loop$iv._prev;
            if ((nextPrev instanceof e) || h() != next) {
                return;
            }
        } while (!d.compareAndSet(next, nextPrev, this));
        if (!(h() instanceof e)) {
            return;
        }
        if (nextPrev != null) {
            next.d((c) nextPrev, (d) null);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Node /* = io.ktor.util.internal.LockFreeLinkedListNode */");
    }

    private final void g(c next) {
        k();
        next.d(b.a(this._prev), (d) null);
    }

    private final c m() {
        Object prev;
        c cVar;
        do {
            prev = this._prev;
            if (prev instanceof e) {
                return ((e) prev).a;
            }
            if (prev == this) {
                cVar = e();
            } else if (prev != null) {
                cVar = (c) prev;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Node /* = io.ktor.util.internal.LockFreeLinkedListNode */");
            }
        } while (!d.compareAndSet(this, prev, cVar.p()));
        return (c) prev;
    }

    private final c e() {
        boolean z;
        c cur = this;
        while (!(cur instanceof a)) {
            cur = cur.i();
            if (cur != this) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (!z) {
                throw new IllegalStateException("Cannot loop to this while looking for list head".toString());
            }
        }
        return cur;
    }

    public final void k() {
        c last = null;
        c prev = m();
        Object obj = this._next;
        if (obj != null) {
            c next = ((e) obj).a;
            while (true) {
                Object nextNext = next.h();
                if (nextNext instanceof e) {
                    next.m();
                    next = ((e) nextNext).a;
                } else {
                    Object prevNext = prev.h();
                    if (prevNext instanceof e) {
                        if (last != null) {
                            prev.m();
                            c.compareAndSet(last, prev, ((e) prevNext).a);
                            prev = last;
                            last = null;
                        } else {
                            prev = b.a(prev._prev);
                        }
                    } else if (prevNext != this) {
                        last = prev;
                        if (prevNext != null) {
                            prev = (c) prevNext;
                            if (prev == next) {
                                return;
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Node /* = io.ktor.util.internal.LockFreeLinkedListNode */");
                        }
                    } else if (c.compareAndSet(prev, this, next)) {
                        return;
                    }
                }
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Removed");
        }
    }

    private final c d(c _prev2, d op) {
        c prev = _prev2;
        c last = null;
        while (true) {
            Object prevNext = prev._next;
            if (prevNext == op) {
                return prev;
            }
            if (prevNext instanceof d) {
                ((d) prevNext).a(prev);
            } else if (!(prevNext instanceof e)) {
                Object oldPrev = this._prev;
                if (oldPrev instanceof e) {
                    return null;
                }
                if (prevNext != this) {
                    last = prev;
                    if (prevNext != null) {
                        prev = (c) prevNext;
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type io.ktor.util.internal.Node /* = io.ktor.util.internal.LockFreeLinkedListNode */");
                    }
                } else if (oldPrev == prev) {
                    return null;
                } else {
                    if (d.compareAndSet(this, oldPrev, prev) && !(prev._prev instanceof e)) {
                        return null;
                    }
                }
            } else if (last != null) {
                prev.m();
                c.compareAndSet(last, prev, ((e) prevNext).a);
                prev = last;
                last = null;
            } else {
                prev = b.a(prev._prev);
            }
        }
    }

    @NotNull
    public String toString() {
        return getClass().getSimpleName() + '@' + Integer.toHexString(System.identityHashCode(this));
    }
}
