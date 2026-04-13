package com.bumptech.glide;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.manager.c;
import com.bumptech.glide.manager.d;
import com.bumptech.glide.manager.k;
import com.bumptech.glide.manager.l;
import com.bumptech.glide.manager.p;
import com.bumptech.glide.manager.q;
import com.bumptech.glide.manager.r;
import com.bumptech.glide.request.e;
import com.bumptech.glide.request.f;
import com.bumptech.glide.util.j;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: RequestManager */
public class i implements ComponentCallbacks2, l {
    private static final f c = ((f) f.w0(Bitmap.class).S());
    private static final f d = ((f) f.w0(GifDrawable.class).S());
    private static final f f = ((f) ((f) f.x0(com.bumptech.glide.load.engine.i.c).e0(g.LOW)).m0(true));
    @GuardedBy("this")
    private final r a1;
    private final c a2;
    @GuardedBy("this")
    private final p p0;
    private final Runnable p1;
    private final CopyOnWriteArrayList<e<Object>> p2;
    @GuardedBy("this")
    private f p3;
    private boolean p4;
    protected final b q;
    protected final Context x;
    final k y;
    @GuardedBy("this")
    private final q z;

    /* compiled from: RequestManager */
    public class a implements Runnable {
        a() {
        }

        public void run() {
            i iVar = i.this;
            iVar.y.b(iVar);
        }
    }

    public i(@NonNull b glide, @NonNull k lifecycle, @NonNull p treeNode, @NonNull Context context) {
        this(glide, lifecycle, treeNode, new q(), glide.g(), context);
    }

    i(b glide, k lifecycle, p treeNode, q requestTracker, d factory, Context context) {
        this.a1 = new r();
        a aVar = new a();
        this.p1 = aVar;
        this.q = glide;
        this.y = lifecycle;
        this.p0 = treeNode;
        this.z = requestTracker;
        this.x = context;
        c a3 = factory.a(context.getApplicationContext(), new b(requestTracker));
        this.a2 = a3;
        if (j.q()) {
            j.u(aVar);
        } else {
            lifecycle.b(this);
        }
        lifecycle.b(a3);
        this.p2 = new CopyOnWriteArrayList<>(glide.i().c());
        v(glide.i().d());
        glide.o(this);
    }

    /* access modifiers changed from: protected */
    public synchronized void v(@NonNull f toSet) {
        this.p3 = (f) ((f) toSet.clone()).b();
    }

    public synchronized void t() {
        this.z.d();
    }

    public synchronized void r() {
        this.z.c();
    }

    public synchronized void s() {
        r();
        for (i requestManager : this.p0.a()) {
            requestManager.r();
        }
    }

    public synchronized void u() {
        this.z.f();
    }

    public synchronized void onStart() {
        u();
        this.a1.onStart();
    }

    public synchronized void onStop() {
        t();
        this.a1.onStop();
    }

    public synchronized void onDestroy() {
        this.a1.onDestroy();
        Iterator<com.bumptech.glide.request.target.j<?>> it = this.a1.i().iterator();
        while (it.hasNext()) {
            l((com.bumptech.glide.request.target.j) it.next());
        }
        this.a1.h();
        this.z.b();
        this.y.a(this);
        this.y.a(this.a2);
        j.v(this.p1);
        this.q.s(this);
    }

    @CheckResult
    @NonNull
    public h<Bitmap> i() {
        return h(Bitmap.class).a(c);
    }

    @CheckResult
    @NonNull
    public h<GifDrawable> k() {
        return h(GifDrawable.class).a(d);
    }

    @CheckResult
    @NonNull
    public h<Drawable> j() {
        return h(Drawable.class);
    }

    @CheckResult
    @NonNull
    public h<Drawable> q(@Nullable String string) {
        return j().M0(string);
    }

    @CheckResult
    @NonNull
    public h<Drawable> p(@RawRes @DrawableRes @Nullable Integer resourceId) {
        return j().K0(resourceId);
    }

    @CheckResult
    @NonNull
    public <ResourceType> h<ResourceType> h(@NonNull Class<ResourceType> resourceClass) {
        return new h<>(this.q, this, resourceClass, this.x);
    }

    public void l(@Nullable com.bumptech.glide.request.target.j<?> target) {
        if (target != null) {
            y(target);
        }
    }

    private void y(@NonNull com.bumptech.glide.request.target.j<?> target) {
        boolean isOwnedByUs = x(target);
        com.bumptech.glide.request.c request = target.getRequest();
        if (!isOwnedByUs && !this.q.p(target) && request != null) {
            target.e((com.bumptech.glide.request.c) null);
            request.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean x(@NonNull com.bumptech.glide.request.target.j<?> target) {
        com.bumptech.glide.request.c request = target.getRequest();
        if (request == null) {
            return true;
        }
        if (!this.z.a(request)) {
            return false;
        }
        this.a1.k(target);
        target.e((com.bumptech.glide.request.c) null);
        return true;
    }

    /* access modifiers changed from: package-private */
    public synchronized void w(@NonNull com.bumptech.glide.request.target.j<?> target, @NonNull com.bumptech.glide.request.c request) {
        this.a1.j(target);
        this.z.g(request);
    }

    /* access modifiers changed from: package-private */
    public List<e<Object>> m() {
        return this.p2;
    }

    /* access modifiers changed from: package-private */
    public synchronized f n() {
        return this.p3;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <T> j<?, T> o(Class<T> transcodeClass) {
        return this.q.i().e(transcodeClass);
    }

    public synchronized String toString() {
        return super.toString() + "{tracker=" + this.z + ", treeNode=" + this.p0 + "}";
    }

    public void onTrimMemory(int level) {
        if (level == 60 && this.p4) {
            s();
        }
    }

    public void onLowMemory() {
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    /* compiled from: RequestManager */
    public class b implements c.a {
        @GuardedBy("RequestManager.this")
        private final q a;

        b(@NonNull q requestTracker) {
            this.a = requestTracker;
        }

        public void a(boolean isConnected) {
            if (isConnected) {
                synchronized (i.this) {
                    this.a.e();
                }
            }
        }
    }
}
