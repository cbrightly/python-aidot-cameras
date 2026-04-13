package io.reactivex.internal.util;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.plugins.a;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: BackpressureHelper */
public final class c {
    public static long c(long a, long b) {
        long u = a + b;
        if (u < 0) {
            return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
        return u;
    }

    public static long a(AtomicLong requested, long n) {
        long r;
        do {
            r = requested.get();
            if (r == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            }
        } while (!requested.compareAndSet(r, c(r, n)));
        return r;
    }

    public static long b(AtomicLong requested, long n) {
        long r;
        do {
            r = requested.get();
            if (r == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (r == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            }
        } while (!requested.compareAndSet(r, c(r, n)));
        return r;
    }

    public static long d(AtomicLong requested, long n) {
        long current;
        long update;
        do {
            current = requested.get();
            if (current == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            }
            update = current - n;
            if (update < 0) {
                a.q(new IllegalStateException("More produced than requested: " + update));
                update = 0;
            }
        } while (!requested.compareAndSet(current, update));
        return update;
    }

    public static long e(AtomicLong requested, long n) {
        long current;
        long update;
        do {
            current = requested.get();
            if (current == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (current == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            }
            update = current - n;
            if (update < 0) {
                a.q(new IllegalStateException("More produced than requested: " + update));
                update = 0;
            }
        } while (!requested.compareAndSet(current, update));
        return update;
    }
}
