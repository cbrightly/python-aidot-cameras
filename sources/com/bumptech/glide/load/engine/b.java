package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.engine.e;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.n;
import java.io.File;
import java.util.List;

/* compiled from: DataCacheGenerator */
public class b implements e, d.a<Object> {
    private File a1;
    private final List<f> c;
    private final f<?> d;
    private final e.a f;
    private volatile n.a<?> p0;
    private int q;
    private f x;
    private List<n<File, ?>> y;
    private int z;

    b(f<?> helper, e.a cb) {
        this(helper.c(), helper, cb);
    }

    b(List<f> cacheKeys, f<?> helper, e.a cb) {
        this.q = -1;
        this.c = cacheKeys;
        this.d = helper;
        this.f = cb;
    }

    public boolean b() {
        while (true) {
            if (this.y == null || !a()) {
                int i = this.q + 1;
                this.q = i;
                if (i >= this.c.size()) {
                    return false;
                }
                f sourceId = this.c.get(this.q);
                File b = this.d.d().b(new c(sourceId, this.d.o()));
                this.a1 = b;
                if (b != null) {
                    this.x = sourceId;
                    this.y = this.d.j(b);
                    this.z = 0;
                }
            } else {
                this.p0 = null;
                boolean started = false;
                while (!started && a()) {
                    List<n<File, ?>> list = this.y;
                    int i2 = this.z;
                    this.z = i2 + 1;
                    this.p0 = ((n) list.get(i2)).b(this.a1, this.d.s(), this.d.f(), this.d.k());
                    if (this.p0 != null && this.d.t(this.p0.c.a())) {
                        started = true;
                        this.p0.c.d(this.d.l(), this);
                    }
                }
                return started;
            }
        }
    }

    private boolean a() {
        return this.z < this.y.size();
    }

    public void cancel() {
        ModelLoader.LoadData<?> local = this.p0;
        if (local != null) {
            local.c.cancel();
        }
    }

    public void e(Object data) {
        this.f.e(this.x, data, this.p0.c, a.DATA_DISK_CACHE, this.x);
    }

    public void c(@NonNull Exception e) {
        this.f.a(this.x, e, this.p0.c, a.DATA_DISK_CACHE);
    }
}
