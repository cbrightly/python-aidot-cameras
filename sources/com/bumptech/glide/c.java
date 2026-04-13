package com.bumptech.glide;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.bumptech.glide.b;
import com.bumptech.glide.e;
import com.bumptech.glide.load.engine.bitmap_recycle.k;
import com.bumptech.glide.load.engine.cache.a;
import com.bumptech.glide.load.engine.cache.g;
import com.bumptech.glide.load.engine.cache.h;
import com.bumptech.glide.load.engine.cache.i;
import com.bumptech.glide.load.engine.j;
import com.bumptech.glide.manager.o;
import com.bumptech.glide.request.f;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: GlideBuilder */
public final class c {
    private final Map<Class<?>, j<?, ?>> a = new ArrayMap();
    private final e.a b = new e.a();
    private j c;
    private com.bumptech.glide.load.engine.bitmap_recycle.e d;
    private com.bumptech.glide.load.engine.bitmap_recycle.b e;
    private h f;
    private com.bumptech.glide.load.engine.executor.a g;
    private com.bumptech.glide.load.engine.executor.a h;
    private a.C0027a i;
    private i j;
    private com.bumptech.glide.manager.d k;
    private int l = 4;
    private b.a m = new a();
    @Nullable
    private o.b n;
    private com.bumptech.glide.load.engine.executor.a o;
    private boolean p;
    @Nullable
    private List<com.bumptech.glide.request.e<Object>> q;

    /* renamed from: com.bumptech.glide.c$c  reason: collision with other inner class name */
    /* compiled from: GlideBuilder */
    public static final class C0020c {
    }

    /* compiled from: GlideBuilder */
    public class a implements b.a {
        a() {
        }

        @NonNull
        public f build() {
            return new f();
        }
    }

    /* access modifiers changed from: package-private */
    public void b(@Nullable o.b factory) {
        this.n = factory;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public b a(@NonNull Context context) {
        Context context2 = context;
        if (this.g == null) {
            this.g = com.bumptech.glide.load.engine.executor.a.g();
        }
        if (this.h == null) {
            this.h = com.bumptech.glide.load.engine.executor.a.e();
        }
        if (this.o == null) {
            this.o = com.bumptech.glide.load.engine.executor.a.c();
        }
        if (this.j == null) {
            this.j = new i.a(context2).a();
        }
        if (this.k == null) {
            this.k = new com.bumptech.glide.manager.e();
        }
        if (this.d == null) {
            int size = this.j.b();
            if (size > 0) {
                this.d = new k((long) size);
            } else {
                this.d = new com.bumptech.glide.load.engine.bitmap_recycle.f();
            }
        }
        if (this.e == null) {
            this.e = new com.bumptech.glide.load.engine.bitmap_recycle.j(this.j.a());
        }
        if (this.f == null) {
            this.f = new g((long) this.j.d());
        }
        if (this.i == null) {
            this.i = new com.bumptech.glide.load.engine.cache.f(context2);
        }
        if (this.c == null) {
            this.c = new j(this.f, this.i, this.h, this.g, com.bumptech.glide.load.engine.executor.a.h(), this.o, this.p);
        }
        List<com.bumptech.glide.request.e<Object>> list = this.q;
        if (list == null) {
            this.q = Collections.emptyList();
        } else {
            this.q = Collections.unmodifiableList(list);
        }
        e experiments = this.b.b();
        return new b(context, this.c, this.f, this.d, this.e, new o(this.n, experiments), this.k, this.l, this.m, this.a, this.q, experiments);
    }

    /* compiled from: GlideBuilder */
    public static final class d {
        private d() {
        }
    }

    /* compiled from: GlideBuilder */
    public static final class b {
        b() {
        }
    }
}
