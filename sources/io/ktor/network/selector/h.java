package io.ktor.network.selector;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LockFreeMPSCQueue.kt */
public final class h<E> {
    private static final AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(h.class, Object.class, "_cur");
    private volatile Object _cur = new i(8);

    public final boolean c() {
        return ((i) this._cur).f();
    }

    public final void b() {
        while (true) {
            i cur = (i) this._cur;
            if (!cur.d()) {
                a.compareAndSet(this, cur, cur.h());
            } else {
                return;
            }
        }
    }

    public final boolean a(@NotNull E element) {
        k.f(element, "element");
        while (true) {
            i cur = (i) this._cur;
            switch (cur.a(element)) {
                case 0:
                    return true;
                case 1:
                    a.compareAndSet(this, cur, cur.h());
                    break;
                case 2:
                    return false;
            }
        }
    }

    @Nullable
    public final E d() {
        while (true) {
            i cur = (i) this._cur;
            Object result = cur.i();
            if (result != i.c) {
                return result;
            }
            a.compareAndSet(this, cur, cur.h());
        }
    }
}
