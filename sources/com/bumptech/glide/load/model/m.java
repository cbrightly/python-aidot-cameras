package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.j;
import java.util.Queue;

/* compiled from: ModelCache */
public class m<A, B> {
    private final f<b<A>, B> a;

    public m(long size) {
        this.a = new a(size);
    }

    /* compiled from: ModelCache */
    public class a extends f<b<A>, B> {
        a(long size) {
            super(size);
        }

        /* access modifiers changed from: protected */
        /* renamed from: n */
        public void j(@NonNull b<A> key, @Nullable B b) {
            key.c();
        }
    }

    @Nullable
    public B a(A model, int width, int height) {
        ModelCache.ModelKey<A> key = b.a(model, width, height);
        B result = this.a.g(key);
        key.c();
        return result;
    }

    public void b(A model, int width, int height, B value) {
        this.a.k(b.a(model, width, height), value);
    }

    @VisibleForTesting
    /* compiled from: ModelCache */
    public static final class b<A> {
        private static final Queue<b<?>> a = j.e(0);
        private int b;
        private int c;
        private A d;

        static <A> b<A> a(A model, int width, int height) {
            ModelCache.ModelKey<A> modelKey;
            Queue<b<?>> queue = a;
            synchronized (queue) {
                modelKey = (b) queue.poll();
            }
            if (modelKey == null) {
                modelKey = new b<>();
            }
            modelKey.b(model, width, height);
            return modelKey;
        }

        private b() {
        }

        private void b(A model, int width, int height) {
            this.d = model;
            this.c = width;
            this.b = height;
        }

        public void c() {
            Queue<b<?>> queue = a;
            synchronized (queue) {
                queue.offer(this);
            }
        }

        public boolean equals(Object o) {
            if (!(o instanceof b)) {
                return false;
            }
            ModelCache.ModelKey<A> other = (b) o;
            if (this.c == other.c && this.b == other.b && this.d.equals(other.d)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((this.b * 31) + this.c) * 31) + this.d.hashCode();
        }
    }
}
