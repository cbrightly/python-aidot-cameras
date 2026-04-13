package io.reactivex.internal.schedulers;

import io.reactivex.r;
import java.util.concurrent.ThreadFactory;

/* compiled from: NewThreadScheduler */
public final class g extends r {
    private static final i b = new i("RxNewThreadScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.newthread-priority", 5).intValue())));
    final ThreadFactory c;

    public g() {
        this(b);
    }

    public g(ThreadFactory threadFactory) {
        this.c = threadFactory;
    }

    public r.c a() {
        return new h(this.c);
    }
}
