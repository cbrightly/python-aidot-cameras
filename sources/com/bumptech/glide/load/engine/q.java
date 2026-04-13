package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.f;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Jobs */
public final class q {
    private final Map<f, k<?>> a = new HashMap();
    private final Map<f, k<?>> b = new HashMap();

    q() {
    }

    /* access modifiers changed from: package-private */
    public k<?> a(f key, boolean onlyRetrieveFromCache) {
        return b(onlyRetrieveFromCache).get(key);
    }

    /* access modifiers changed from: package-private */
    public void c(f key, k<?> job) {
        b(job.p()).put(key, job);
    }

    /* access modifiers changed from: package-private */
    public void d(f key, k<?> expected) {
        Map<f, k<?>> b2 = b(expected.p());
        if (expected.equals(b2.get(key))) {
            b2.remove(key);
        }
    }

    private Map<f, k<?>> b(boolean onlyRetrieveFromCache) {
        return onlyRetrieveFromCache ? this.b : this.a;
    }
}
