package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.engine.e;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.n;
import java.util.Collections;
import java.util.List;

/* compiled from: SourceGenerator */
public class x implements e, e.a {
    private final f<?> c;
    private final e.a d;
    private int f;
    private b q;
    private Object x;
    private volatile n.a<?> y;
    private c z;

    x(f<?> helper, e.a cb) {
        this.c = helper;
        this.d = cb;
    }

    public boolean b() {
        if (this.x != null) {
            Object data = this.x;
            this.x = null;
            d(data);
        }
        b bVar = this.q;
        if (bVar != null && bVar.b()) {
            return true;
        }
        this.q = null;
        this.y = null;
        boolean started = false;
        while (!started && f()) {
            List<n.a<?>> g = this.c.g();
            int i = this.f;
            this.f = i + 1;
            this.y = g.get(i);
            if (this.y != null && (this.c.e().c(this.y.c.getDataSource()) || this.c.t(this.y.c.a()))) {
                started = true;
                j(this.y);
            }
        }
        return started;
    }

    private void j(n.a<?> toStart) {
        this.y.c.d(this.c.l(), new a(toStart));
    }

    /* compiled from: SourceGenerator */
    public class a implements d.a<Object> {
        final /* synthetic */ n.a c;

        a(n.a aVar) {
            this.c = aVar;
        }

        public void e(@Nullable Object data) {
            if (x.this.g(this.c)) {
                x.this.h(this.c, data);
            }
        }

        public void c(@NonNull Exception e) {
            if (x.this.g(this.c)) {
                x.this.i(this.c, e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean g(n.a<?> requestLoadData) {
        ModelLoader.LoadData<?> currentLoadData = this.y;
        return currentLoadData != null && currentLoadData == requestLoadData;
    }

    private boolean f() {
        return this.f < this.c.g().size();
    }

    /* JADX INFO: finally extract failed */
    private void d(Object dataToCache) {
        long startTime = com.bumptech.glide.util.e.b();
        try {
            Encoder<Object> encoder = this.c.p(dataToCache);
            DataCacheWriter<Object> writer = new d<>(encoder, dataToCache, this.c.k());
            this.z = new c(this.y.a, this.c.o());
            this.c.d().a(this.z, writer);
            if (Log.isLoggable("SourceGenerator", 2)) {
                Log.v("SourceGenerator", "Finished encoding source to cache, key: " + this.z + ", data: " + dataToCache + ", encoder: " + encoder + ", duration: " + com.bumptech.glide.util.e.a(startTime));
            }
            this.y.c.b();
            this.q = new b(Collections.singletonList(this.y.a), this.c, this);
        } catch (Throwable th) {
            this.y.c.b();
            throw th;
        }
    }

    public void cancel() {
        ModelLoader.LoadData<?> local = this.y;
        if (local != null) {
            local.c.cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public void h(n.a<?> loadData, Object data) {
        i diskCacheStrategy = this.c.e();
        if (data == null || !diskCacheStrategy.c(loadData.c.getDataSource())) {
            e.a aVar = this.d;
            f fVar = loadData.a;
            d<Data> dVar = loadData.c;
            aVar.e(fVar, data, dVar, dVar.getDataSource(), this.z);
            return;
        }
        this.x = data;
        this.d.c();
    }

    /* access modifiers changed from: package-private */
    public void i(n.a<?> loadData, @NonNull Exception e) {
        e.a aVar = this.d;
        c cVar = this.z;
        d<Data> dVar = loadData.c;
        aVar.a(cVar, e, dVar, dVar.getDataSource());
    }

    public void c() {
        throw new UnsupportedOperationException();
    }

    public void e(f sourceKey, Object data, d<?> fetcher, com.bumptech.glide.load.a dataSource, f attemptedKey) {
        this.d.e(sourceKey, data, fetcher, this.y.c.getDataSource(), sourceKey);
    }

    public void a(f sourceKey, Exception e, d<?> fetcher, com.bumptech.glide.load.a dataSource) {
        this.d.a(sourceKey, e, fetcher, this.y.c.getDataSource());
    }
}
