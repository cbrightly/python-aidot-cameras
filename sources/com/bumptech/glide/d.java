package com.bumptech.glide;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.ImageView;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.b;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.engine.j;
import com.bumptech.glide.request.e;
import com.bumptech.glide.request.target.f;
import com.bumptech.glide.request.target.k;
import java.util.List;
import java.util.Map;

/* compiled from: GlideContext */
public class d extends ContextWrapper {
    @VisibleForTesting
    static final j<?, ?> a = new a();
    private final b b;
    private final Registry c;
    private final f d;
    private final b.a e;
    private final List<e<Object>> f;
    private final Map<Class<?>, j<?, ?>> g;
    private final j h;
    private final e i;
    private final int j;
    @GuardedBy("this")
    @Nullable
    private com.bumptech.glide.request.f k;

    public d(@NonNull Context context, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b arrayPool, @NonNull Registry registry, @NonNull f imageViewTargetFactory, @NonNull b.a defaultRequestOptionsFactory, @NonNull Map<Class<?>, j<?, ?>> defaultTransitionOptions, @NonNull List<e<Object>> defaultRequestListeners, @NonNull j engine, @NonNull e experiments, int logLevel) {
        super(context.getApplicationContext());
        this.b = arrayPool;
        this.c = registry;
        this.d = imageViewTargetFactory;
        this.e = defaultRequestOptionsFactory;
        this.f = defaultRequestListeners;
        this.g = defaultTransitionOptions;
        this.h = engine;
        this.i = experiments;
        this.j = logLevel;
    }

    public List<e<Object>> c() {
        return this.f;
    }

    public synchronized com.bumptech.glide.request.f d() {
        if (this.k == null) {
            this.k = (com.bumptech.glide.request.f) this.e.build().S();
        }
        return this.k;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: com.bumptech.glide.j<?, T>} */
    /* JADX WARNING: Multi-variable type inference failed */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> com.bumptech.glide.j<?, T> e(@androidx.annotation.NonNull java.lang.Class<T> r5) {
        /*
            r4 = this;
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.j<?, ?>> r0 = r4.g
            java.lang.Object r0 = r0.get(r5)
            com.bumptech.glide.j r0 = (com.bumptech.glide.j) r0
            if (r0 != 0) goto L_0x0034
            java.util.Map<java.lang.Class<?>, com.bumptech.glide.j<?, ?>> r1 = r4.g
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0014:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0034
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            java.lang.Class r3 = (java.lang.Class) r3
            boolean r3 = r3.isAssignableFrom(r5)
            if (r3 == 0) goto L_0x0033
            java.lang.Object r3 = r2.getValue()
            r0 = r3
            com.bumptech.glide.j r0 = (com.bumptech.glide.j) r0
        L_0x0033:
            goto L_0x0014
        L_0x0034:
            if (r0 != 0) goto L_0x0038
            com.bumptech.glide.j<?, ?> r0 = a
        L_0x0038:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.d.e(java.lang.Class):com.bumptech.glide.j");
    }

    @NonNull
    public <X> k<ImageView, X> a(@NonNull ImageView imageView, @NonNull Class<X> transcodeClass) {
        return this.d.a(imageView, transcodeClass);
    }

    @NonNull
    public j f() {
        return this.h;
    }

    @NonNull
    public Registry i() {
        return this.c;
    }

    public int h() {
        return this.j;
    }

    @NonNull
    public com.bumptech.glide.load.engine.bitmap_recycle.b b() {
        return this.b;
    }

    public e g() {
        return this.i;
    }
}
