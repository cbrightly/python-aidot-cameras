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

/* compiled from: ResourceCacheGenerator */
public class u implements e, d.a<Object> {
    private File a1;
    private final e.a c;
    private final f<?> d;
    private int f;
    private volatile n.a<?> p0;
    private v p1;
    private int q = -1;
    private f x;
    private List<n<File, ?>> y;
    private int z;

    u(f<?> helper, e.a cb) {
        this.d = helper;
        this.c = cb;
    }

    public boolean b() {
        List<f> c2 = this.d.c();
        boolean z2 = false;
        if (c2.isEmpty()) {
            return false;
        }
        List<Class<?>> resourceClasses = this.d.m();
        if (!resourceClasses.isEmpty()) {
            while (true) {
                if (this.y == null || !a()) {
                    int i = this.q + 1;
                    this.q = i;
                    if (i >= resourceClasses.size()) {
                        int i2 = this.f + 1;
                        this.f = i2;
                        if (i2 >= c2.size()) {
                            return z2;
                        }
                        this.q = z2 ? 1 : 0;
                    }
                    f sourceId = c2.get(this.f);
                    Class<?> resourceClass = resourceClasses.get(this.q);
                    f fVar = sourceId;
                    v vVar = r5;
                    v vVar2 = new v(this.d.b(), fVar, this.d.o(), this.d.s(), this.d.f(), this.d.r(resourceClass), resourceClass, this.d.k());
                    this.p1 = vVar;
                    File b = this.d.d().b(this.p1);
                    this.a1 = b;
                    if (b != null) {
                        this.x = sourceId;
                        this.y = this.d.j(b);
                        z2 = false;
                        this.z = 0;
                    } else {
                        z2 = false;
                    }
                } else {
                    this.p0 = null;
                    boolean started = false;
                    while (!started && a()) {
                        List<n<File, ?>> list = this.y;
                        int i3 = this.z;
                        this.z = i3 + 1;
                        this.p0 = ((n) list.get(i3)).b(this.a1, this.d.s(), this.d.f(), this.d.k());
                        if (this.p0 != null && this.d.t(this.p0.c.a())) {
                            started = true;
                            this.p0.c.d(this.d.l(), this);
                        }
                    }
                    return started;
                }
            }
        } else if (File.class.equals(this.d.q())) {
            return false;
        } else {
            throw new IllegalStateException("Failed to find any load path from " + this.d.i() + " to " + this.d.q());
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
        this.c.e(this.x, data, this.p0.c, a.RESOURCE_DISK_CACHE, this.p1);
    }

    public void c(@NonNull Exception e) {
        this.c.a(this.p1, e, this.p0.c, a.RESOURCE_DISK_CACHE);
    }
}
