package io.reactivex.internal.disposables;

import io.reactivex.disposables.b;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.plugins.a;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: DisposableHelper */
public enum c implements b {
    DISPOSED;

    public static boolean isDisposed(b d) {
        return d == DISPOSED;
    }

    public static boolean set(AtomicReference<b> field, b d) {
        b current;
        do {
            current = field.get();
            if (current == DISPOSED) {
                if (d == null) {
                    return false;
                }
                d.dispose();
                return false;
            }
        } while (!field.compareAndSet(current, d));
        if (current == null) {
            return true;
        }
        current.dispose();
        return true;
    }

    public static boolean setOnce(AtomicReference<b> field, b d) {
        io.reactivex.internal.functions.b.e(d, "d is null");
        if (field.compareAndSet((Object) null, d)) {
            return true;
        }
        d.dispose();
        if (field.get() == DISPOSED) {
            return false;
        }
        reportDisposableSet();
        return false;
    }

    public static boolean replace(AtomicReference<b> field, b d) {
        b current;
        do {
            current = field.get();
            if (current == DISPOSED) {
                if (d == null) {
                    return false;
                }
                d.dispose();
                return false;
            }
        } while (!field.compareAndSet(current, d));
        return true;
    }

    public static boolean dispose(AtomicReference<b> field) {
        b current;
        b current2 = field.get();
        b d = DISPOSED;
        if (current2 == d || (current = field.getAndSet(d)) == d) {
            return false;
        }
        if (current == null) {
            return true;
        }
        current.dispose();
        return true;
    }

    public static boolean validate(b current, b next) {
        if (next == null) {
            a.q(new NullPointerException("next is null"));
            return false;
        } else if (current == null) {
            return true;
        } else {
            next.dispose();
            reportDisposableSet();
            return false;
        }
    }

    public static void reportDisposableSet() {
        a.q(new ProtocolViolationException("Disposable already set!"));
    }

    public static boolean trySet(AtomicReference<b> field, b d) {
        if (field.compareAndSet((Object) null, d)) {
            return true;
        }
        if (field.get() != DISPOSED) {
            return false;
        }
        d.dispose();
        return false;
    }

    public void dispose() {
    }

    public boolean isDisposed() {
        return true;
    }
}
