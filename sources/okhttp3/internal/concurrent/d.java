package okhttp3.internal.concurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import kotlin.jvm.internal.k;
import kotlin.x;
import okhttp3.internal.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TaskQueue.kt */
public final class d {
    private boolean a;
    @Nullable
    private a b;
    @NotNull
    private final List<a> c = new ArrayList();
    private boolean d;
    @NotNull
    private final e e;
    @NotNull
    private final String f;

    public d(@NotNull e taskRunner, @NotNull String name) {
        k.f(taskRunner, "taskRunner");
        k.f(name, "name");
        this.e = taskRunner;
        this.f = name;
    }

    @NotNull
    public final e h() {
        return this.e;
    }

    @NotNull
    public final String f() {
        return this.f;
    }

    public final boolean g() {
        return this.a;
    }

    @Nullable
    public final a c() {
        return this.b;
    }

    public final void l(@Nullable a aVar) {
        this.b = aVar;
    }

    @NotNull
    public final List<a> e() {
        return this.c;
    }

    public final boolean d() {
        return this.d;
    }

    public final void m(boolean z) {
        this.d = z;
    }

    public static /* synthetic */ void j(d dVar, a aVar, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = 0;
        }
        dVar.i(aVar, j);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void i(@org.jetbrains.annotations.NotNull okhttp3.internal.concurrent.a r8, long r9) {
        /*
            r7 = this;
            java.lang.String r0 = "task"
            kotlin.jvm.internal.k.f(r8, r0)
            okhttp3.internal.concurrent.e r0 = r7.e
            monitor-enter(r0)
            r1 = 0
            boolean r2 = r7.a     // Catch:{ all -> 0x005e }
            if (r2 == 0) goto L_0x004e
            boolean r2 = r8.a()     // Catch:{ all -> 0x005e }
            if (r2 == 0) goto L_0x002f
            r2 = r7
            r3 = r8
            r4 = 0
            okhttp3.internal.concurrent.e$b r5 = okhttp3.internal.concurrent.e.c     // Catch:{ all -> 0x005e }
            java.util.logging.Logger r5 = r5.a()     // Catch:{ all -> 0x005e }
            java.util.logging.Level r6 = java.util.logging.Level.FINE     // Catch:{ all -> 0x005e }
            boolean r5 = r5.isLoggable(r6)     // Catch:{ all -> 0x005e }
            if (r5 == 0) goto L_0x002c
            r5 = 0
            java.lang.String r6 = "schedule canceled (queue is shutdown)"
            okhttp3.internal.concurrent.b.c(r3, r2, r6)     // Catch:{ all -> 0x005e }
        L_0x002c:
            monitor-exit(r0)
            return
        L_0x002f:
            r2 = r7
            r3 = r8
            r4 = 0
            okhttp3.internal.concurrent.e$b r5 = okhttp3.internal.concurrent.e.c     // Catch:{ all -> 0x005e }
            java.util.logging.Logger r5 = r5.a()     // Catch:{ all -> 0x005e }
            java.util.logging.Level r6 = java.util.logging.Level.FINE     // Catch:{ all -> 0x005e }
            boolean r5 = r5.isLoggable(r6)     // Catch:{ all -> 0x005e }
            if (r5 == 0) goto L_0x0047
            r5 = 0
            java.lang.String r6 = "schedule failed (queue is shutdown)"
            okhttp3.internal.concurrent.b.c(r3, r2, r6)     // Catch:{ all -> 0x005e }
        L_0x0047:
            java.util.concurrent.RejectedExecutionException r2 = new java.util.concurrent.RejectedExecutionException     // Catch:{ all -> 0x005e }
            r2.<init>()     // Catch:{ all -> 0x005e }
            throw r2     // Catch:{ all -> 0x005e }
        L_0x004e:
            r2 = 0
            boolean r2 = r7.k(r8, r9, r2)     // Catch:{ all -> 0x005e }
            if (r2 == 0) goto L_0x005a
            okhttp3.internal.concurrent.e r2 = r7.e     // Catch:{ all -> 0x005e }
            r2.h(r7)     // Catch:{ all -> 0x005e }
        L_0x005a:
            kotlin.x r1 = kotlin.x.a     // Catch:{ all -> 0x005e }
            monitor-exit(r0)
            return
        L_0x005e:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.concurrent.d.i(okhttp3.internal.concurrent.a, long):void");
    }

    public final boolean k(@NotNull a task, long delayNanos, boolean recurrence) {
        String str;
        a aVar = task;
        k.f(aVar, "task");
        aVar.e(this);
        long now = this.e.g().nanoTime();
        long executeNanoTime = now + delayNanos;
        int existingIndex = this.c.indexOf(aVar);
        if (existingIndex != -1) {
            if (task.c() <= executeNanoTime) {
                if (e.c.a().isLoggable(Level.FINE)) {
                    b.c(aVar, this, "already scheduled");
                }
                return false;
            }
            this.c.remove(existingIndex);
        }
        aVar.g(executeNanoTime);
        if (e.c.a().isLoggable(Level.FINE)) {
            if (recurrence) {
                str = "run again after " + b.b(executeNanoTime - now);
            } else {
                str = "scheduled after " + b.b(executeNanoTime - now);
            }
            b.c(aVar, this, str);
        }
        int index$iv = 0;
        Iterator<a> it = this.c.iterator();
        while (true) {
            if (!it.hasNext()) {
                index$iv = -1;
                break;
            }
            if ((it.next().c() - now > delayNanos ? 1 : null) != null) {
                break;
            }
            index$iv++;
        }
        int insertAt = index$iv;
        if (insertAt == -1) {
            insertAt = this.c.size();
        }
        this.c.add(insertAt, aVar);
        if (insertAt == 0) {
            return true;
        }
        return false;
    }

    public final void a() {
        if (!b.h || !Thread.holdsLock(this)) {
            synchronized (this.e) {
                if (b()) {
                    this.e.h(this);
                }
                x xVar = x.a;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST NOT hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    public final void n() {
        if (!b.h || !Thread.holdsLock(this)) {
            synchronized (this.e) {
                this.a = true;
                if (b()) {
                    this.e.h(this);
                }
                x xVar = x.a;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST NOT hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    public final boolean b() {
        a aVar = this.b;
        if (aVar != null) {
            if (aVar == null) {
                k.n();
            }
            if (aVar.a()) {
                this.d = true;
            }
        }
        boolean tasksCanceled = false;
        int size = this.c.size() - 1;
        while (size >= 0) {
            int i = size;
            if (this.c.get(i).a()) {
                a task$iv = this.c.get(i);
                if (e.c.a().isLoggable(Level.FINE)) {
                    b.c(task$iv, this, "canceled");
                }
                tasksCanceled = true;
                this.c.remove(i);
            }
            size = i - 1;
        }
        return tasksCanceled;
    }

    @NotNull
    public String toString() {
        return this.f;
    }
}
