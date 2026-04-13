package okhttp3.internal.connection;

import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.TypeCastException;
import kotlin.collections.v;
import kotlin.jvm.internal.k;
import kotlin.x;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.f;
import okhttp3.internal.http.g;
import okhttp3.internal.http.j;
import okhttp3.internal.platform.h;
import okhttp3.p;
import okhttp3.r;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealCall.kt */
public final class e implements okhttp3.e {
    @NotNull
    private final z A4;
    @NotNull
    private final b0 B4;
    private final boolean C4;
    @Nullable
    private c a1;
    private boolean a2;
    private final h c;
    @NotNull
    private final r d;
    /* access modifiers changed from: private */
    public final c f;
    private boolean p0;
    private boolean p1;
    private boolean p2 = true;
    private volatile boolean p3;
    private volatile c p4;
    private final AtomicBoolean q = new AtomicBoolean();
    private Object x;
    private d y;
    @Nullable
    private f z;
    @Nullable
    private volatile f z4;

    public e(@NotNull z client, @NotNull b0 originalRequest, boolean forWebSocket) {
        k.f(client, "client");
        k.f(originalRequest, "originalRequest");
        this.A4 = client;
        this.B4 = originalRequest;
        this.C4 = forWebSocket;
        this.c = client.l().a();
        this.d = client.q().a(this);
        c $this$apply = new c(this);
        $this$apply.g((long) client.h(), TimeUnit.MILLISECONDS);
        this.f = $this$apply;
    }

    @NotNull
    public final z k() {
        return this.A4;
    }

    @NotNull
    public final b0 p() {
        return this.B4;
    }

    public final boolean n() {
        return this.C4;
    }

    @NotNull
    public final r m() {
        return this.d;
    }

    /* compiled from: RealCall.kt */
    public static final class c extends okio.a {
        final /* synthetic */ e m;

        c(e $outer) {
            this.m = $outer;
        }

        /* access modifiers changed from: protected */
        public void A() {
            this.m.cancel();
        }
    }

    @Nullable
    public final f l() {
        return this.z;
    }

    @Nullable
    public final c o() {
        return this.a1;
    }

    public final void y(@Nullable f fVar) {
        this.z4 = fVar;
    }

    @NotNull
    /* renamed from: f */
    public e clone() {
        return new e(this.A4, this.B4, this.C4);
    }

    @NotNull
    public b0 g() {
        return this.B4;
    }

    public void cancel() {
        if (!this.p3) {
            this.p3 = true;
            c cVar = this.p4;
            if (cVar != null) {
                cVar.b();
            }
            f fVar = this.z4;
            if (fVar != null) {
                fVar.f();
            }
            this.d.g(this);
        }
    }

    public boolean isCanceled() {
        return this.p3;
    }

    @NotNull
    public d0 execute() {
        if (this.q.compareAndSet(false, true)) {
            this.f.u();
            e();
            try {
                this.A4.o().b(this);
                return q();
            } finally {
                this.A4.o().g(this);
            }
        } else {
            throw new IllegalStateException("Already Executed".toString());
        }
    }

    public void o0(@NotNull f responseCallback) {
        k.f(responseCallback, "responseCallback");
        if (this.q.compareAndSet(false, true)) {
            e();
            this.A4.o().a(new a(this, responseCallback));
            return;
        }
        throw new IllegalStateException("Already Executed".toString());
    }

    private final void e() {
        this.x = h.c.g().i("response.body().close()");
        this.d.f(this);
    }

    @NotNull
    public final d0 q() {
        List interceptors = new ArrayList();
        v.x(interceptors, this.A4.v());
        interceptors.add(new j(this.A4));
        interceptors.add(new okhttp3.internal.http.a(this.A4.n()));
        interceptors.add(new okhttp3.internal.cache.a(this.A4.f()));
        interceptors.add(a.b);
        if (!this.C4) {
            v.x(interceptors, this.A4.y());
        }
        interceptors.add(new okhttp3.internal.http.b(this.C4));
        try {
            d0 response = new g(this, interceptors, 0, (c) null, this.B4, this.A4.k(), this.A4.H(), this.A4.N()).a(this.B4);
            if (!isCanceled()) {
                t((IOException) null);
                return response;
            }
            okhttp3.internal.b.j(response);
            throw new IOException("Canceled");
        } catch (IOException e) {
            Throwable t = t(e);
            if (t == null) {
                t = new TypeCastException("null cannot be cast to non-null type kotlin.Throwable");
            }
            throw t;
        } catch (Throwable th) {
            if (1 == 0) {
                t((IOException) null);
            }
            throw th;
        }
    }

    public final void i(@NotNull b0 request, boolean newExchangeFinder) {
        k.f(request, Progress.REQUEST);
        if (this.a1 == null) {
            synchronized (this) {
                if (!(!this.a2)) {
                    throw new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()".toString());
                } else if (true ^ this.p1) {
                    x xVar = x.a;
                } else {
                    throw new IllegalStateException("Check failed.".toString());
                }
            }
            if (newExchangeFinder) {
                this.y = new d(this.c, h(request.l()), this, this.d);
                return;
            }
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    @NotNull
    public final c r(@NotNull g chain) {
        k.f(chain, "chain");
        synchronized (this) {
            if (!this.p2) {
                throw new IllegalStateException("released".toString());
            } else if (!(!this.a2)) {
                throw new IllegalStateException("Check failed.".toString());
            } else if (!this.p1) {
                x xVar = x.a;
            } else {
                throw new IllegalStateException("Check failed.".toString());
            }
        }
        d exchangeFinder = this.y;
        if (exchangeFinder == null) {
            k.n();
        }
        c result = new c(this, this.d, exchangeFinder, exchangeFinder.a(this.A4, chain));
        this.a1 = result;
        this.p4 = result;
        synchronized (this) {
            this.p1 = true;
            this.a2 = true;
        }
        if (!this.p3) {
            return result;
        }
        throw new IOException("Canceled");
    }

    public final void c(@NotNull f connection) {
        k.f(connection, "connection");
        Object $this$assertThreadHoldsLock$iv = connection;
        if (!okhttp3.internal.b.h || Thread.holdsLock($this$assertThreadHoldsLock$iv)) {
            if (this.z == null) {
                this.z = connection;
                connection.p().add(new b(this, this.x));
                return;
            }
            throw new IllegalStateException("Check failed.".toString());
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

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0021, code lost:
        if (r7.a2 != false) goto L_0x0023;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <E extends java.io.IOException> E s(@org.jetbrains.annotations.NotNull okhttp3.internal.connection.c r8, boolean r9, boolean r10, E r11) {
        /*
            r7 = this;
            java.lang.String r0 = "exchange"
            kotlin.jvm.internal.k.f(r8, r0)
            okhttp3.internal.connection.c r0 = r7.p4
            boolean r0 = kotlin.jvm.internal.k.a(r8, r0)
            r1 = 1
            r0 = r0 ^ r1
            if (r0 == 0) goto L_0x0010
            return r11
        L_0x0010:
            r0 = 0
            r2 = 0
            monitor-enter(r7)
            r3 = 0
            if (r9 == 0) goto L_0x001d
            boolean r4 = r7.p1     // Catch:{ all -> 0x001b }
            if (r4 != 0) goto L_0x0023
            goto L_0x001d
        L_0x001b:
            r1 = move-exception
            goto L_0x005c
        L_0x001d:
            if (r10 == 0) goto L_0x0045
            boolean r4 = r7.a2     // Catch:{ all -> 0x001b }
            if (r4 == 0) goto L_0x0045
        L_0x0023:
            r4 = 0
            if (r9 == 0) goto L_0x0028
            r7.p1 = r4     // Catch:{ all -> 0x001b }
        L_0x0028:
            if (r10 == 0) goto L_0x002c
            r7.a2 = r4     // Catch:{ all -> 0x001b }
        L_0x002c:
            boolean r5 = r7.p1     // Catch:{ all -> 0x001b }
            if (r5 != 0) goto L_0x0036
            boolean r6 = r7.a2     // Catch:{ all -> 0x001b }
            if (r6 != 0) goto L_0x0036
            r6 = r1
            goto L_0x0037
        L_0x0036:
            r6 = r4
        L_0x0037:
            r0 = r6
            if (r5 != 0) goto L_0x0043
            boolean r5 = r7.a2     // Catch:{ all -> 0x001b }
            if (r5 != 0) goto L_0x0043
            boolean r5 = r7.p2     // Catch:{ all -> 0x001b }
            if (r5 != 0) goto L_0x0043
            goto L_0x0044
        L_0x0043:
            r1 = r4
        L_0x0044:
            r2 = r1
        L_0x0045:
            kotlin.x r1 = kotlin.x.a     // Catch:{ all -> 0x001b }
            monitor-exit(r7)
            if (r0 == 0) goto L_0x0054
            r1 = 0
            r7.p4 = r1
            okhttp3.internal.connection.f r1 = r7.z
            if (r1 == 0) goto L_0x0054
            r1.t()
        L_0x0054:
            if (r2 == 0) goto L_0x005b
            java.io.IOException r1 = r7.d(r11)
            return r1
        L_0x005b:
            return r11
        L_0x005c:
            monitor-exit(r7)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.e.s(okhttp3.internal.connection.c, boolean, boolean, java.io.IOException):java.io.IOException");
    }

    @Nullable
    public final IOException t(@Nullable IOException e) {
        boolean callDone = false;
        synchronized (this) {
            if (this.p2) {
                boolean z2 = false;
                this.p2 = false;
                if (!this.p1 && !this.a2) {
                    z2 = true;
                }
                callDone = z2;
            }
            x xVar = x.a;
        }
        if (callDone) {
            return d(e);
        }
        return e;
    }

    private final <E extends IOException> E d(E e) {
        Socket v;
        boolean z2 = okhttp3.internal.b.h;
        if (!z2 || !Thread.holdsLock(this)) {
            f connection = this.z;
            if (connection != null) {
                Object $this$assertThreadDoesntHoldLock$iv = connection;
                if (!z2 || !Thread.holdsLock($this$assertThreadDoesntHoldLock$iv)) {
                    synchronized (connection) {
                        v = v();
                    }
                    Socket socket = v;
                    if (this.z == null) {
                        if (socket != null) {
                            okhttp3.internal.b.k(socket);
                        }
                        this.d.l(this, connection);
                    } else {
                        if (!(socket == null)) {
                            throw new IllegalStateException("Check failed.".toString());
                        }
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Thread ");
                    Thread currentThread = Thread.currentThread();
                    k.b(currentThread, "Thread.currentThread()");
                    sb.append(currentThread.getName());
                    sb.append(" MUST NOT hold lock on ");
                    sb.append($this$assertThreadDoesntHoldLock$iv);
                    throw new AssertionError(sb.toString());
                }
            }
            IOException result = A(e);
            if (e != null) {
                r rVar = this.d;
                if (result == null) {
                    k.n();
                }
                rVar.e(this, result);
            } else {
                this.d.d(this);
            }
            return result;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Thread ");
        Thread currentThread2 = Thread.currentThread();
        k.b(currentThread2, "Thread.currentThread()");
        sb2.append(currentThread2.getName());
        sb2.append(" MUST NOT hold lock on ");
        sb2.append(this);
        throw new AssertionError(sb2.toString());
    }

    @Nullable
    public final Socket v() {
        f connection = this.z;
        if (connection == null) {
            k.n();
        }
        Object $this$assertThreadHoldsLock$iv = connection;
        if (!okhttp3.internal.b.h || Thread.holdsLock($this$assertThreadHoldsLock$iv)) {
            List $this$indexOfFirst$iv = connection.p();
            int index$iv = 0;
            Iterator<Reference<e>> it = $this$indexOfFirst$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    index$iv = -1;
                    break;
                } else if (k.a((e) it.next().get(), this)) {
                    break;
                } else {
                    index$iv++;
                }
            }
            int index = index$iv;
            if (index != -1) {
                $this$indexOfFirst$iv.remove(index);
                this.z = null;
                if ($this$indexOfFirst$iv.isEmpty()) {
                    connection.C(System.nanoTime());
                    if (this.c.c(connection)) {
                        return connection.E();
                    }
                }
                return null;
            }
            throw new IllegalStateException("Check failed.".toString());
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

    private final <E extends IOException> E A(E cause) {
        if (this.p0 || !this.f.v()) {
            return cause;
        }
        InterruptedIOException e = new InterruptedIOException("timeout");
        if (cause != null) {
            e.initCause(cause);
        }
        return e;
    }

    public final void z() {
        if (!this.p0) {
            this.p0 = true;
            this.f.v();
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final void j(boolean closeExchange) {
        c cVar;
        synchronized (this) {
            if (this.p2) {
                x xVar = x.a;
            } else {
                throw new IllegalStateException("released".toString());
            }
        }
        if (closeExchange && (cVar = this.p4) != null) {
            cVar.d();
        }
        this.a1 = null;
    }

    private final okhttp3.a h(okhttp3.v url) {
        okhttp3.g certificatePinner = null;
        SSLSocketFactory sslSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        if (url.k()) {
            sslSocketFactory = this.A4.L();
            hostnameVerifier = this.A4.u();
            certificatePinner = this.A4.j();
        }
        return new okhttp3.a(url.j(), url.p(), this.A4.p(), this.A4.K(), sslSocketFactory, hostnameVerifier, certificatePinner, this.A4.F(), this.A4.D(), this.A4.C(), this.A4.m(), this.A4.G());
    }

    public final boolean w() {
        d dVar = this.y;
        if (dVar == null) {
            k.n();
        }
        return dVar.e();
    }

    /* access modifiers changed from: private */
    public final String B() {
        StringBuilder sb = new StringBuilder();
        sb.append(isCanceled() ? "canceled " : "");
        sb.append(this.C4 ? "web socket" : NotificationCompat.CATEGORY_CALL);
        sb.append(" to ");
        sb.append(u());
        return sb.toString();
    }

    @NotNull
    public final String u() {
        return this.B4.l().r();
    }

    /* compiled from: RealCall.kt */
    public final class a implements Runnable {
        @NotNull
        private volatile AtomicInteger c = new AtomicInteger(0);
        private final f d;
        final /* synthetic */ e f;

        public a(@NotNull e $outer, f responseCallback) {
            k.f(responseCallback, "responseCallback");
            this.f = $outer;
            this.d = responseCallback;
        }

        @NotNull
        public final AtomicInteger c() {
            return this.c;
        }

        public final void e(@NotNull a other) {
            k.f(other, "other");
            this.c = other.c;
        }

        @NotNull
        public final String d() {
            return this.f.p().l().j();
        }

        @NotNull
        public final e b() {
            return this.f;
        }

        public final void a(@NotNull ExecutorService executorService) {
            k.f(executorService, "executorService");
            Object $this$assertThreadDoesntHoldLock$iv = this.f.k().o();
            if (!okhttp3.internal.b.h || !Thread.holdsLock($this$assertThreadDoesntHoldLock$iv)) {
                try {
                    executorService.execute(this);
                } catch (RejectedExecutionException e) {
                    InterruptedIOException ioException = new InterruptedIOException("executor rejected");
                    ioException.initCause(e);
                    this.f.t(ioException);
                    this.d.onFailure(this.f, ioException);
                    this.f.k().o().f(this);
                } catch (Throwable th) {
                    this.f.k().o().f(this);
                    throw th;
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Thread ");
                Thread currentThread = Thread.currentThread();
                k.b(currentThread, "Thread.currentThread()");
                sb.append(currentThread.getName());
                sb.append(" MUST NOT hold lock on ");
                sb.append($this$assertThreadDoesntHoldLock$iv);
                throw new AssertionError(sb.toString());
            }
        }

        public void run() {
            p o;
            Thread currentThread$iv = Thread.currentThread();
            k.b(currentThread$iv, "currentThread");
            String oldName$iv = currentThread$iv.getName();
            currentThread$iv.setName("OkHttp " + this.f.u());
            boolean signalledCallback = false;
            try {
                this.f.f.u();
                signalledCallback = true;
                this.d.onResponse(this.f, this.f.q());
                o = this.f.k().o();
            } catch (IOException e) {
                if (signalledCallback) {
                    h.c.g().k("Callback failure for " + this.f.B(), 4, e);
                } else {
                    this.d.onFailure(this.f, e);
                }
                o = this.f.k().o();
            } catch (Throwable th) {
                currentThread$iv.setName(oldName$iv);
                throw th;
            }
            o.f(this);
            currentThread$iv.setName(oldName$iv);
        }
    }

    /* compiled from: RealCall.kt */
    public static final class b extends WeakReference<e> {
        @Nullable
        private final Object a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull e referent, @Nullable Object callStackTrace) {
            super(referent);
            k.f(referent, "referent");
            this.a = callStackTrace;
        }

        @Nullable
        public final Object a() {
            return this.a;
        }
    }
}
