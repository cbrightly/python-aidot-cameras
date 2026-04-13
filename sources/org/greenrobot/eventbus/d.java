package org.greenrobot.eventbus;

import android.os.Looper;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.greenrobot.eventbus.f;
import org.greenrobot.eventbus.g;
import org.greenrobot.eventbus.meta.b;

/* compiled from: EventBusBuilder */
public class d {
    private static final ExecutorService a = Executors.newCachedThreadPool();
    boolean b = true;
    boolean c = true;
    boolean d = true;
    boolean e = true;
    boolean f;
    boolean g = true;
    boolean h;
    boolean i;
    ExecutorService j = a;
    List<b> k;
    f l;
    g m;

    d() {
    }

    /* access modifiers changed from: package-private */
    public f b() {
        f fVar = this.l;
        if (fVar != null) {
            return fVar;
        }
        return (!f.a.c() || a() == null) ? new f.b() : new f.a("EventBus");
    }

    /* access modifiers changed from: package-private */
    public g c() {
        Object looperOrNull;
        g gVar = this.m;
        if (gVar != null) {
            return gVar;
        }
        if (!f.a.c() || (looperOrNull = a()) == null) {
            return null;
        }
        return new g.a((Looper) looperOrNull);
    }

    /* access modifiers changed from: package-private */
    public Object a() {
        try {
            return Looper.getMainLooper();
        } catch (RuntimeException e2) {
            return null;
        }
    }
}
