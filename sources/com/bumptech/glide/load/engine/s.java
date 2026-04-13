package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.b;

/* compiled from: LockedResource */
public final class s<Z> implements t<Z>, FactoryPools.e {
    private static final Pools.Pool<s<?>> c = FactoryPools.d(20, new a());
    private final b d = b.a();
    private t<Z> f;
    private boolean q;
    private boolean x;

    /* compiled from: LockedResource */
    public class a implements FactoryPools.d<s<?>> {
        a() {
        }

        /* renamed from: a */
        public s<?> create() {
            return new s<>();
        }
    }

    @NonNull
    static <Z> s<Z> c(t<Z> resource) {
        LockedResource<Z> result = (s) i.d(c.acquire());
        result.b(resource);
        return result;
    }

    s() {
    }

    private void b(t<Z> toWrap) {
        this.x = false;
        this.q = true;
        this.f = toWrap;
    }

    private void e() {
        this.f = null;
        c.release(this);
    }

    /* access modifiers changed from: package-private */
    public synchronized void f() {
        this.d.c();
        if (this.q) {
            this.q = false;
            if (this.x) {
                recycle();
            }
        } else {
            throw new IllegalStateException("Already unlocked");
        }
    }

    @NonNull
    public Class<Z> a() {
        return this.f.a();
    }

    @NonNull
    public Z get() {
        return this.f.get();
    }

    public int getSize() {
        return this.f.getSize();
    }

    public synchronized void recycle() {
        this.d.c();
        this.x = true;
        if (!this.q) {
            this.f.recycle();
            e();
        }
    }

    @NonNull
    public b d() {
        return this.d;
    }
}
