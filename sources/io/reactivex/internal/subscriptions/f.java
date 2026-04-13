package io.reactivex.internal.subscriptions;

import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.internal.functions.b;
import io.reactivex.plugins.a;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.c;

/* compiled from: SubscriptionHelper */
public enum f implements c {
    CANCELLED;

    public void request(long n) {
    }

    public void cancel() {
    }

    public static boolean validate(c current, c next) {
        if (next == null) {
            a.q(new NullPointerException("next is null"));
            return false;
        } else if (current == null) {
            return true;
        } else {
            next.cancel();
            reportSubscriptionSet();
            return false;
        }
    }

    public static void reportSubscriptionSet() {
        a.q(new ProtocolViolationException("Subscription already set!"));
    }

    public static boolean validate(long n) {
        if (n > 0) {
            return true;
        }
        a.q(new IllegalArgumentException("n > 0 required but it was " + n));
        return false;
    }

    public static void reportMoreProduced(long n) {
        a.q(new ProtocolViolationException("More produced than requested: " + n));
    }

    public static boolean set(AtomicReference<c> field, c s) {
        c current;
        do {
            current = field.get();
            if (current == CANCELLED) {
                if (s == null) {
                    return false;
                }
                s.cancel();
                return false;
            }
        } while (!field.compareAndSet(current, s));
        if (current == null) {
            return true;
        }
        current.cancel();
        return true;
    }

    public static boolean setOnce(AtomicReference<c> field, c s) {
        b.e(s, "s is null");
        if (field.compareAndSet((Object) null, s)) {
            return true;
        }
        s.cancel();
        if (field.get() == CANCELLED) {
            return false;
        }
        reportSubscriptionSet();
        return false;
    }

    public static boolean replace(AtomicReference<c> field, c s) {
        c current;
        do {
            current = field.get();
            if (current == CANCELLED) {
                if (s == null) {
                    return false;
                }
                s.cancel();
                return false;
            }
        } while (!field.compareAndSet(current, s));
        return true;
    }

    public static boolean cancel(AtomicReference<c> field) {
        c current;
        c current2 = field.get();
        f fVar = CANCELLED;
        if (current2 == fVar || (current = field.getAndSet(fVar)) == fVar) {
            return false;
        }
        if (current == null) {
            return true;
        }
        current.cancel();
        return true;
    }

    public static boolean deferredSetOnce(AtomicReference<c> field, AtomicLong requested, c s) {
        if (!setOnce(field, s)) {
            return false;
        }
        long r = requested.getAndSet(0);
        if (r == 0) {
            return true;
        }
        s.request(r);
        return true;
    }

    public static void deferredRequest(AtomicReference<c> field, AtomicLong requested, long n) {
        c s = field.get();
        if (s != null) {
            s.request(n);
        } else if (validate(n)) {
            io.reactivex.internal.util.c.a(requested, n);
            c s2 = field.get();
            if (s2 != null) {
                long r = requested.getAndSet(0);
                if (r != 0) {
                    s2.request(r);
                }
            }
        }
    }

    public static boolean setOnce(AtomicReference<c> field, c s, long request) {
        if (!setOnce(field, s)) {
            return false;
        }
        s.request(request);
        return true;
    }
}
