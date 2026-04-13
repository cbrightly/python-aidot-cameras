package okhttp3.internal.connection;

import androidx.core.app.NotificationCompat;
import com.google.android.libraries.places.api.model.PlaceTypes;
import java.lang.ref.Reference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import okhttp3.f0;
import okhttp3.internal.concurrent.d;
import okhttp3.internal.concurrent.e;
import okhttp3.internal.connection.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealConnectionPool.kt */
public final class h {
    public static final a a = new a((DefaultConstructorMarker) null);
    private final long b;
    private final d c;
    private final b d = new b(this, okhttp3.internal.b.i + " ConnectionPool");
    private final ConcurrentLinkedQueue<f> e = new ConcurrentLinkedQueue<>();
    private final int f;

    public h(@NotNull e taskRunner, int maxIdleConnections, long keepAliveDuration, @NotNull TimeUnit timeUnit) {
        k.f(taskRunner, "taskRunner");
        k.f(timeUnit, "timeUnit");
        this.f = maxIdleConnections;
        this.b = timeUnit.toNanos(keepAliveDuration);
        this.c = taskRunner.i();
        if (!(keepAliveDuration > 0)) {
            throw new IllegalArgumentException(("keepAliveDuration <= 0: " + keepAliveDuration).toString());
        }
    }

    /* compiled from: RealConnectionPool.kt */
    public static final class b extends okhttp3.internal.concurrent.a {
        final /* synthetic */ h e;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(h $outer, String $super_call_param$1) {
            super($super_call_param$1, false, 2, (DefaultConstructorMarker) null);
            this.e = $outer;
        }

        public long f() {
            return this.e.b(System.nanoTime());
        }
    }

    public final boolean a(@NotNull okhttp3.a address, @NotNull e call, @Nullable List<f0> routes, boolean requireMultiplexed) {
        k.f(address, PlaceTypes.ADDRESS);
        k.f(call, NotificationCompat.CATEGORY_CALL);
        Iterator<f> it = this.e.iterator();
        while (it.hasNext()) {
            f connection = it.next();
            k.b(connection, "connection");
            synchronized (connection) {
                if (requireMultiplexed) {
                    if (!connection.w()) {
                        x xVar = x.a;
                    }
                }
                if (connection.u(address, routes)) {
                    call.c(connection);
                    return true;
                }
                x xVar2 = x.a;
            }
        }
        return false;
    }

    public final void e(@NotNull f connection) {
        k.f(connection, "connection");
        Object $this$assertThreadHoldsLock$iv = connection;
        if (!okhttp3.internal.b.h || Thread.holdsLock($this$assertThreadHoldsLock$iv)) {
            this.e.add(connection);
            d.j(this.c, this.d, 0, 2, (Object) null);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST hold lock on ");
        sb.append($this$assertThreadHoldsLock$iv);
        throw new AssertionError(sb.toString());
    }

    public final boolean c(@NotNull f connection) {
        k.f(connection, "connection");
        Object $this$assertThreadHoldsLock$iv = connection;
        if (okhttp3.internal.b.h && !Thread.holdsLock($this$assertThreadHoldsLock$iv)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST hold lock on ");
            sb.append($this$assertThreadHoldsLock$iv);
            throw new AssertionError(sb.toString());
        } else if (connection.r() || this.f == 0) {
            connection.D(true);
            this.e.remove(connection);
            if (!this.e.isEmpty()) {
                return true;
            }
            this.c.a();
            return true;
        } else {
            d.j(this.c, this.d, 0, 2, (Object) null);
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:0x008d, code lost:
        okhttp3.internal.b.k(r5.E());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x009a, code lost:
        if (r13.e.isEmpty() == false) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x009c, code lost:
        r13.c.a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00a1, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long b(long r14) {
        /*
            r13 = this;
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = -9223372036854775808
            java.util.concurrent.ConcurrentLinkedQueue<okhttp3.internal.connection.f> r5 = r13.e
            java.util.Iterator r5 = r5.iterator()
        L_0x000b:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x004a
            java.lang.Object r6 = r5.next()
            okhttp3.internal.connection.f r6 = (okhttp3.internal.connection.f) r6
            java.lang.String r7 = "connection"
            kotlin.jvm.internal.k.b(r6, r7)
            monitor-enter(r6)
            r7 = 0
            int r8 = r13.d(r6, r14)     // Catch:{ all -> 0x0045 }
            if (r8 <= 0) goto L_0x002d
            int r8 = r0 + 1
            java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x002b }
            r0 = r8
            goto L_0x0040
        L_0x002b:
            r0 = move-exception
            goto L_0x0048
        L_0x002d:
            int r1 = r1 + 1
            long r8 = r6.q()     // Catch:{ all -> 0x0045 }
            long r8 = r14 - r8
            int r10 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r10 <= 0) goto L_0x003e
            r3 = r8
            r2 = r6
            kotlin.x r10 = kotlin.x.a     // Catch:{ all -> 0x0045 }
            goto L_0x0040
        L_0x003e:
            kotlin.x r10 = kotlin.x.a     // Catch:{ all -> 0x0045 }
        L_0x0040:
            monitor-exit(r6)
            goto L_0x000b
        L_0x0045:
            r5 = move-exception
            r8 = r0
            r0 = r5
        L_0x0048:
            monitor-exit(r6)
            throw r0
        L_0x004a:
            long r5 = r13.b
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 >= 0) goto L_0x0061
            int r7 = r13.f
            if (r1 <= r7) goto L_0x0057
            goto L_0x0061
        L_0x0057:
            if (r1 <= 0) goto L_0x005b
            long r5 = r5 - r3
            return r5
        L_0x005b:
            if (r0 <= 0) goto L_0x005e
            return r5
        L_0x005e:
            r5 = -1
            return r5
        L_0x0061:
            if (r2 != 0) goto L_0x0066
            kotlin.jvm.internal.k.n()
        L_0x0066:
            r5 = r2
            monitor-enter(r5)
            r6 = 0
            java.util.List r7 = r5.p()     // Catch:{ all -> 0x00a2 }
            boolean r7 = r7.isEmpty()     // Catch:{ all -> 0x00a2 }
            r8 = 1
            r7 = r7 ^ r8
            r9 = 0
            if (r7 == 0) goto L_0x0079
            monitor-exit(r5)
            return r9
        L_0x0079:
            long r11 = r5.q()     // Catch:{ all -> 0x00a2 }
            long r11 = r11 + r3
            int r7 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r7 == 0) goto L_0x0084
            monitor-exit(r5)
            return r9
        L_0x0084:
            r5.D(r8)     // Catch:{ all -> 0x00a2 }
            java.util.concurrent.ConcurrentLinkedQueue<okhttp3.internal.connection.f> r7 = r13.e     // Catch:{ all -> 0x00a2 }
            r7.remove(r2)     // Catch:{ all -> 0x00a2 }
            monitor-exit(r5)
            java.net.Socket r6 = r5.E()
            okhttp3.internal.b.k(r6)
            java.util.concurrent.ConcurrentLinkedQueue<okhttp3.internal.connection.f> r6 = r13.e
            boolean r6 = r6.isEmpty()
            if (r6 == 0) goto L_0x00a1
            okhttp3.internal.concurrent.d r6 = r13.c
            r6.a()
        L_0x00a1:
            return r9
        L_0x00a2:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.h.b(long):long");
    }

    private final int d(f connection, long now) {
        Object $this$assertThreadHoldsLock$iv = connection;
        if (!okhttp3.internal.b.h || Thread.holdsLock($this$assertThreadHoldsLock$iv)) {
            List references = connection.p();
            int i = 0;
            while (i < references.size()) {
                Reference reference = references.get(i);
                if (reference.get() != null) {
                    i++;
                } else {
                    okhttp3.internal.platform.h.c.g().m("A connection to " + connection.a().a().l() + " was leaked. " + "Did you forget to close a response body?", ((e.b) reference).a());
                    references.remove(i);
                    connection.D(true);
                    if (references.isEmpty()) {
                        connection.C(now - this.b);
                        return 0;
                    }
                }
            }
            return references.size();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST hold lock on ");
        sb.append($this$assertThreadHoldsLock$iv);
        throw new AssertionError(sb.toString());
    }

    /* compiled from: RealConnectionPool.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
