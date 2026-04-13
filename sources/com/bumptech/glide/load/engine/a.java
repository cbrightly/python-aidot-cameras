package com.bumptech.glide.load.engine;

import android.os.Process;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.load.f;
import com.bumptech.glide.util.i;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* compiled from: ActiveResources */
public final class a {
    private final boolean a;
    private final Executor b;
    @VisibleForTesting
    final Map<f, d> c;
    private final ReferenceQueue<o<?>> d;
    private o.a e;
    private volatile boolean f;
    @Nullable
    private volatile c g;

    @VisibleForTesting
    /* compiled from: ActiveResources */
    public interface c {
        void a();
    }

    a(boolean isActiveResourceRetentionAllowed) {
        this(isActiveResourceRetentionAllowed, Executors.newSingleThreadExecutor(new C0025a()));
    }

    /* renamed from: com.bumptech.glide.load.engine.a$a  reason: collision with other inner class name */
    /* compiled from: ActiveResources */
    public class C0025a implements ThreadFactory {
        C0025a() {
        }

        /* renamed from: com.bumptech.glide.load.engine.a$a$a  reason: collision with other inner class name */
        /* compiled from: ActiveResources */
        public class C0026a implements Runnable {
            final /* synthetic */ Runnable c;

            C0026a(Runnable runnable) {
                this.c = runnable;
            }

            public void run() {
                Process.setThreadPriority(10);
                this.c.run();
            }
        }

        public Thread newThread(@NonNull Runnable r) {
            return new Thread(new C0026a(r), "glide-active-resources");
        }
    }

    @VisibleForTesting
    a(boolean isActiveResourceRetentionAllowed, Executor monitorClearedResourcesExecutor) {
        this.c = new HashMap();
        this.d = new ReferenceQueue<>();
        this.a = isActiveResourceRetentionAllowed;
        this.b = monitorClearedResourcesExecutor;
        monitorClearedResourcesExecutor.execute(new b());
    }

    /* compiled from: ActiveResources */
    public class b implements Runnable {
        b() {
        }

        public void run() {
            a.this.b();
        }
    }

    /* access modifiers changed from: package-private */
    public void f(o.a listener) {
        synchronized (listener) {
            synchronized (this) {
                this.e = listener;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void a(f key, o<?> resource) {
        d removed = this.c.put(key, new d(key, resource, this.d, this.a));
        if (removed != null) {
            removed.a();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void d(f key) {
        d removed = this.c.remove(key);
        if (removed != null) {
            removed.a();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        return r1;
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.bumptech.glide.load.engine.o<?> e(com.bumptech.glide.load.f r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.util.Map<com.bumptech.glide.load.f, com.bumptech.glide.load.engine.a$d> r0 = r2.c     // Catch:{ all -> 0x001b }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x001b }
            com.bumptech.glide.load.engine.a$d r0 = (com.bumptech.glide.load.engine.a.d) r0     // Catch:{ all -> 0x001b }
            if (r0 != 0) goto L_0x000e
            r1 = 0
            monitor-exit(r2)
            return r1
        L_0x000e:
            java.lang.Object r1 = r0.get()     // Catch:{ all -> 0x001b }
            com.bumptech.glide.load.engine.o r1 = (com.bumptech.glide.load.engine.o) r1     // Catch:{ all -> 0x001b }
            if (r1 != 0) goto L_0x0019
            r2.c(r0)     // Catch:{ all -> 0x001b }
        L_0x0019:
            monitor-exit(r2)
            return r1
        L_0x001b:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.a.e(com.bumptech.glide.load.f):com.bumptech.glide.load.engine.o");
    }

    /* access modifiers changed from: package-private */
    public void c(@NonNull d ref) {
        synchronized (this) {
            this.c.remove(ref.a);
            if (ref.b) {
                t<?> tVar = ref.c;
                if (tVar != null) {
                    this.e.d(ref.a, new o(tVar, true, false, ref.a, this.e));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b() {
        while (!this.f) {
            try {
                c((d) this.d.remove());
                c current = this.g;
                if (current != null) {
                    current.a();
                }
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @VisibleForTesting
    /* compiled from: ActiveResources */
    public static final class d extends WeakReference<o<?>> {
        final f a;
        final boolean b;
        @Nullable
        t<?> c;

        d(@NonNull f key, @NonNull o<?> referent, @NonNull ReferenceQueue<? super o<?>> queue, boolean isActiveResourceRetentionAllowed) {
            super(referent, queue);
            t<?> tVar;
            this.a = (f) i.d(key);
            if (!referent.d() || !isActiveResourceRetentionAllowed) {
                tVar = null;
            } else {
                tVar = (t) i.d(referent.c());
            }
            this.c = tVar;
            this.b = referent.d();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.c = null;
            clear();
        }
    }
}
