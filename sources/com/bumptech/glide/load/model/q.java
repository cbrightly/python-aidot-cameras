package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools;
import com.bumptech.glide.g;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.n;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: MultiModelLoader */
public class q<Model, Data> implements n<Model, Data> {
    private final List<n<Model, Data>> a;
    private final Pools.Pool<List<Throwable>> b;

    q(@NonNull List<n<Model, Data>> modelLoaders, @NonNull Pools.Pool<List<Throwable>> exceptionListPool) {
        this.a = modelLoaders;
        this.b = exceptionListPool;
    }

    public n.a<Data> b(@NonNull Model model, int width, int height, @NonNull i options) {
        ModelLoader.LoadData<Data> loadData;
        f sourceKey = null;
        int size = this.a.size();
        List<DataFetcher<Data>> fetchers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ModelLoader<Model, Data> modelLoader = (n) this.a.get(i);
            if (modelLoader.a(model) && (loadData = modelLoader.b(model, width, height, options)) != null) {
                sourceKey = loadData.a;
                fetchers.add(loadData.c);
            }
        }
        if (fetchers.isEmpty() != 0 || sourceKey == null) {
            return null;
        }
        return new n.a<>(sourceKey, new a(fetchers, this.b));
    }

    public boolean a(@NonNull Model model) {
        Iterator<n<Model, Data>> it = this.a.iterator();
        while (it.hasNext()) {
            if (((n) it.next()).a(model)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "MultiModelLoader{modelLoaders=" + Arrays.toString(this.a.toArray()) + '}';
    }

    /* compiled from: MultiModelLoader */
    public static class a<Data> implements d<Data>, d.a<Data> {
        private final List<d<Data>> c;
        private final Pools.Pool<List<Throwable>> d;
        private int f = 0;
        private g q;
        private d.a<? super Data> x;
        @Nullable
        private List<Throwable> y;
        private boolean z;

        a(@NonNull List<d<Data>> fetchers, @NonNull Pools.Pool<List<Throwable>> throwableListPool) {
            this.d = throwableListPool;
            com.bumptech.glide.util.i.c(fetchers);
            this.c = fetchers;
        }

        public void d(@NonNull g priority, @NonNull d.a<? super Data> callback) {
            this.q = priority;
            this.x = callback;
            this.y = this.d.acquire();
            this.c.get(this.f).d(priority, this);
            if (this.z) {
                cancel();
            }
        }

        public void b() {
            List<Throwable> list = this.y;
            if (list != null) {
                this.d.release(list);
            }
            this.y = null;
            Iterator<d<Data>> it = this.c.iterator();
            while (it.hasNext()) {
                ((d) it.next()).b();
            }
        }

        public void cancel() {
            this.z = true;
            Iterator<d<Data>> it = this.c.iterator();
            while (it.hasNext()) {
                ((d) it.next()).cancel();
            }
        }

        @NonNull
        public Class<Data> a() {
            return this.c.get(0).a();
        }

        @NonNull
        public com.bumptech.glide.load.a getDataSource() {
            return this.c.get(0).getDataSource();
        }

        public void e(@Nullable Data data) {
            if (data != null) {
                this.x.e(data);
            } else {
                f();
            }
        }

        public void c(@NonNull Exception e) {
            ((List) com.bumptech.glide.util.i.d(this.y)).add(e);
            f();
        }

        private void f() {
            if (!this.z) {
                if (this.f < this.c.size() - 1) {
                    this.f++;
                    d(this.q, this.x);
                    return;
                }
                com.bumptech.glide.util.i.d(this.y);
                this.x.c(new GlideException("Fetch failed", (List<Throwable>) new ArrayList(this.y)));
            }
        }
    }
}
