package com.bumptech.glide.load.engine.bitmap_recycle;

import com.bumptech.glide.load.engine.bitmap_recycle.m;
import com.bumptech.glide.util.j;
import java.util.Queue;

/* compiled from: BaseKeyPool */
public abstract class d<T extends m> {
    private final Queue<T> a = j.e(20);

    /* access modifiers changed from: package-private */
    public abstract T a();

    d() {
    }

    /* access modifiers changed from: package-private */
    public T b() {
        T result = (m) this.a.poll();
        if (result == null) {
            return a();
        }
        return result;
    }

    public void c(T key) {
        if (this.a.size() < 20) {
            this.a.offer(key);
        }
    }
}
