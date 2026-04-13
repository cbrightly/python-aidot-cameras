package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.f;
import com.bumptech.glide.util.i;

/* compiled from: EngineResource */
public class o<Z> implements t<Z> {
    private final boolean c;
    private final boolean d;
    private final t<Z> f;
    private final a q;
    private final f x;
    private int y;
    private boolean z;

    /* compiled from: EngineResource */
    public interface a {
        void d(f fVar, o<?> oVar);
    }

    o(t<Z> toWrap, boolean isMemoryCacheable, boolean isRecyclable, f key, a listener) {
        this.f = (t) i.d(toWrap);
        this.c = isMemoryCacheable;
        this.d = isRecyclable;
        this.x = key;
        this.q = (a) i.d(listener);
    }

    /* access modifiers changed from: package-private */
    public t<Z> c() {
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return this.c;
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
        if (this.y > 0) {
            throw new IllegalStateException("Cannot recycle a resource while it is still acquired");
        } else if (!this.z) {
            this.z = true;
            if (this.d) {
                this.f.recycle();
            }
        } else {
            throw new IllegalStateException("Cannot recycle a resource that has already been recycled");
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void b() {
        if (!this.z) {
            this.y++;
        } else {
            throw new IllegalStateException("Cannot acquire a recycled resource");
        }
    }

    /* access modifiers changed from: package-private */
    public void e() {
        boolean release = false;
        synchronized (this) {
            int i = this.y;
            if (i > 0) {
                int i2 = i - 1;
                this.y = i2;
                if (i2 == 0) {
                    release = true;
                }
            } else {
                throw new IllegalStateException("Cannot release a recycled or not yet acquired resource");
            }
        }
        if (release) {
            this.q.d(this.x, this);
        }
    }

    public synchronized String toString() {
        return "EngineResource{isMemoryCacheable=" + this.c + ", listener=" + this.q + ", key=" + this.x + ", acquired=" + this.y + ", isRecycled=" + this.z + ", resource=" + this.f + '}';
    }
}
