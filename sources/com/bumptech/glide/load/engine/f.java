package com.bumptech.glide.load.engine;

import com.bumptech.glide.d;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.cache.a;
import com.bumptech.glide.load.engine.g;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.l;
import com.bumptech.glide.load.m;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.n;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: DecodeHelper */
public final class f<Transcode> {
    private final List<n.a<?>> a = new ArrayList();
    private final List<com.bumptech.glide.load.f> b = new ArrayList();
    private d c;
    private Object d;
    private int e;
    private int f;
    private Class<?> g;
    private g.e h;
    private i i;
    private Map<Class<?>, m<?>> j;
    private Class<Transcode> k;
    private boolean l;
    private boolean m;
    private com.bumptech.glide.load.f n;
    private com.bumptech.glide.g o;
    private i p;
    private boolean q;
    private boolean r;

    f() {
    }

    /* access modifiers changed from: package-private */
    public <R> void u(d glideContext, Object model, com.bumptech.glide.load.f signature, int width, int height, i diskCacheStrategy, Class<?> resourceClass, Class<R> transcodeClass, com.bumptech.glide.g priority, i options, Map<Class<?>, m<?>> transformations, boolean isTransformationRequired, boolean isScaleOnlyOrNoTransform, g.e diskCacheProvider) {
        this.c = glideContext;
        this.d = model;
        this.n = signature;
        this.e = width;
        this.f = height;
        this.p = diskCacheStrategy;
        this.g = resourceClass;
        this.h = diskCacheProvider;
        this.k = transcodeClass;
        this.o = priority;
        this.i = options;
        this.j = transformations;
        this.q = isTransformationRequired;
        this.r = isScaleOnlyOrNoTransform;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.c = null;
        this.d = null;
        this.n = null;
        this.g = null;
        this.k = null;
        this.i = null;
        this.o = null;
        this.j = null;
        this.p = null;
        this.a.clear();
        this.l = false;
        this.b.clear();
        this.m = false;
    }

    /* access modifiers changed from: package-private */
    public a d() {
        return this.h.a();
    }

    /* access modifiers changed from: package-private */
    public i e() {
        return this.p;
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.g l() {
        return this.o;
    }

    /* access modifiers changed from: package-private */
    public i k() {
        return this.i;
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.load.f o() {
        return this.n;
    }

    /* access modifiers changed from: package-private */
    public int s() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public int f() {
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public b b() {
        return this.c.b();
    }

    /* access modifiers changed from: package-private */
    public Class<?> q() {
        return this.k;
    }

    /* access modifiers changed from: package-private */
    public Class<?> i() {
        return this.d.getClass();
    }

    /* access modifiers changed from: package-private */
    public List<Class<?>> m() {
        return this.c.i().j(this.d.getClass(), this.g, this.k);
    }

    /* access modifiers changed from: package-private */
    public boolean t(Class<?> dataClass) {
        return h(dataClass) != null;
    }

    /* access modifiers changed from: package-private */
    public <Data> r<Data, ?, Transcode> h(Class<Data> dataClass) {
        return this.c.i().h(dataClass, this.g, this.k);
    }

    /* access modifiers changed from: package-private */
    public boolean w() {
        return this.r;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: com.bumptech.glide.load.m<Z>} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <Z> com.bumptech.glide.load.m<Z> r(java.lang.Class<Z> r5) {
        /*
            r4 = this;
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.m<?>> r0 = r4.j
            java.lang.Object r0 = r0.get(r5)
            com.bumptech.glide.load.m r0 = (com.bumptech.glide.load.m) r0
            if (r0 != 0) goto L_0x0035
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.m<?>> r1 = r4.j
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0014:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0035
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            java.lang.Class r3 = (java.lang.Class) r3
            boolean r3 = r3.isAssignableFrom(r5)
            if (r3 == 0) goto L_0x0034
            java.lang.Object r1 = r2.getValue()
            r0 = r1
            com.bumptech.glide.load.m r0 = (com.bumptech.glide.load.m) r0
            goto L_0x0035
        L_0x0034:
            goto L_0x0014
        L_0x0035:
            if (r0 != 0) goto L_0x0065
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.m<?>> r1 = r4.j
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x0060
            boolean r1 = r4.q
            if (r1 != 0) goto L_0x0044
            goto L_0x0060
        L_0x0044:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Missing transformation for "
            r2.append(r3)
            r2.append(r5)
            java.lang.String r3 = ". If you wish to ignore unknown resource types, use the optional transformation methods."
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0060:
            com.bumptech.glide.load.resource.c r1 = com.bumptech.glide.load.resource.c.a()
            return r1
        L_0x0065:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.f.r(java.lang.Class):com.bumptech.glide.load.m");
    }

    /* access modifiers changed from: package-private */
    public boolean v(t<?> resource) {
        return this.c.i().n(resource);
    }

    /* access modifiers changed from: package-private */
    public <Z> l<Z> n(t<Z> resource) {
        return this.c.i().k(resource);
    }

    /* access modifiers changed from: package-private */
    public List<n<File, ?>> j(File file) {
        return this.c.i().i(file);
    }

    /* access modifiers changed from: package-private */
    public boolean x(com.bumptech.glide.load.f key) {
        List<n.a<?>> g2 = g();
        int size = g2.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((n.a) g2.get(i2)).a.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public List<n.a<?>> g() {
        if (!this.l) {
            this.l = true;
            this.a.clear();
            List<ModelLoader<Object, ?>> modelLoaders = this.c.i().i(this.d);
            int size = modelLoaders.size();
            for (int i2 = 0; i2 < size; i2++) {
                ModelLoader.LoadData<?> current = ((n) modelLoaders.get(i2)).b(this.d, this.e, this.f, this.i);
                if (current != null) {
                    this.a.add(current);
                }
            }
        }
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public List<com.bumptech.glide.load.f> c() {
        if (!this.m) {
            this.m = true;
            this.b.clear();
            List<n.a<?>> g2 = g();
            int size = g2.size();
            for (int i2 = 0; i2 < size; i2++) {
                ModelLoader.LoadData<?> data = (n.a) g2.get(i2);
                if (!this.b.contains(data.a)) {
                    this.b.add(data.a);
                }
                for (int j2 = 0; j2 < data.b.size(); j2++) {
                    if (!this.b.contains(data.b.get(j2))) {
                        this.b.add(data.b.get(j2));
                    }
                }
            }
        }
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public <X> com.bumptech.glide.load.d<X> p(X data) {
        return this.c.i().m(data);
    }
}
