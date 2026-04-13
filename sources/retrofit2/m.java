package retrofit2;

import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.e0;
import okhttp3.f;
import okhttp3.x;
import okio.k;
import okio.p;

/* compiled from: OkHttpCall */
public final class m<T> implements d<T> {
    private final r c;
    private final Object[] d;
    private final e.a f;
    @GuardedBy("this")
    private boolean p0;
    private final h<e0, T> q;
    private volatile boolean x;
    @GuardedBy("this")
    @Nullable
    private e y;
    @GuardedBy("this")
    @Nullable
    private Throwable z;

    m(r requestFactory, Object[] args, e.a callFactory, h<e0, T> responseConverter) {
        this.c = requestFactory;
        this.d = args;
        this.f = callFactory;
        this.q = responseConverter;
    }

    /* renamed from: a */
    public m<T> clone() {
        return new m<>(this.c, this.d, this.f, this.q);
    }

    public synchronized b0 g() {
        try {
        } catch (IOException e) {
            throw new RuntimeException("Unable to create request.", e);
        }
        return c().g();
    }

    @GuardedBy("this")
    private e c() {
        e call = this.y;
        if (call != null) {
            return call;
        }
        Throwable th = this.z;
        if (th == null) {
            try {
                e b2 = b();
                this.y = b2;
                return b2;
            } catch (IOException | Error | RuntimeException e) {
                x.s(e);
                this.z = e;
                throw e;
            }
        } else if (th instanceof IOException) {
            throw ((IOException) th);
        } else if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else {
            throw ((Error) th);
        }
    }

    public void T(f<T> callback) {
        e call;
        Throwable failure;
        Objects.requireNonNull(callback, "callback == null");
        synchronized (this) {
            if (!this.p0) {
                this.p0 = true;
                call = this.y;
                failure = this.z;
                if (call == null && failure == null) {
                    try {
                        e b2 = b();
                        this.y = b2;
                        call = b2;
                    } catch (Throwable t) {
                        x.s(t);
                        this.z = t;
                        failure = t;
                    }
                }
            } else {
                throw new IllegalStateException("Already executed.");
            }
        }
        if (failure != null) {
            callback.a(this, failure);
            return;
        }
        if (this.x) {
            call.cancel();
        }
        call.o0(new a(callback));
    }

    /* compiled from: OkHttpCall */
    public class a implements f {
        final /* synthetic */ f c;

        a(f fVar) {
            this.c = fVar;
        }

        public void onResponse(e call, d0 rawResponse) {
            try {
                try {
                    this.c.b(m.this, m.this.d(rawResponse));
                } catch (Throwable t) {
                    x.s(t);
                    t.printStackTrace();
                }
            } catch (Throwable e) {
                x.s(e);
                a(e);
            }
        }

        public void onFailure(e call, IOException e) {
            a(e);
        }

        private void a(Throwable e) {
            try {
                this.c.a(m.this, e);
            } catch (Throwable t) {
                x.s(t);
                t.printStackTrace();
            }
        }
    }

    public s<T> execute() {
        e call;
        synchronized (this) {
            if (!this.p0) {
                this.p0 = true;
                call = c();
            } else {
                throw new IllegalStateException("Already executed.");
            }
        }
        if (this.x) {
            call.cancel();
        }
        return d(call.execute());
    }

    private e b() {
        e call = this.f.a(this.c.a(this.d));
        if (call != null) {
            return call;
        }
        throw new NullPointerException("Call.Factory returned null.");
    }

    /* access modifiers changed from: package-private */
    public s<T> d(d0 rawResponse) {
        e0 rawBody = rawResponse.a();
        d0 rawResponse2 = rawResponse.v().b(new c(rawBody.contentType(), rawBody.contentLength())).c();
        int code = rawResponse2.j();
        if (code < 200 || code >= 300) {
            try {
                return s.c(x.a(rawBody), rawResponse2);
            } finally {
                rawBody.close();
            }
        } else if (code == 204 || code == 205) {
            rawBody.close();
            return s.g(null, rawResponse2);
        } else {
            b catchingBody = new b(rawBody);
            try {
                return s.g(this.q.convert(catchingBody), rawResponse2);
            } catch (RuntimeException e) {
                catchingBody.a();
                throw e;
            }
        }
    }

    public void cancel() {
        e call;
        this.x = true;
        synchronized (this) {
            call = this.y;
        }
        if (call != null) {
            call.cancel();
        }
    }

    public boolean isCanceled() {
        boolean z2 = true;
        if (this.x) {
            return true;
        }
        synchronized (this) {
            e eVar = this.y;
            if (eVar == null || !eVar.isCanceled()) {
                z2 = false;
            }
        }
        return z2;
    }

    /* compiled from: OkHttpCall */
    public static final class c extends e0 {
        @Nullable
        private final x c;
        private final long d;

        c(@Nullable x contentType, long contentLength) {
            this.c = contentType;
            this.d = contentLength;
        }

        public x contentType() {
            return this.c;
        }

        public long contentLength() {
            return this.d;
        }

        public okio.e source() {
            throw new IllegalStateException("Cannot read raw response body of a converted body.");
        }
    }

    /* compiled from: OkHttpCall */
    public static final class b extends e0 {
        private final e0 c;
        private final okio.e d;
        @Nullable
        IOException f;

        b(e0 delegate) {
            this.c = delegate;
            this.d = p.d(new a(delegate.source()));
        }

        /* compiled from: OkHttpCall */
        public class a extends k {
            a(okio.e0 arg0) {
                super(arg0);
            }

            public long read(okio.c sink, long byteCount) {
                try {
                    return super.read(sink, byteCount);
                } catch (IOException e) {
                    b.this.f = e;
                    throw e;
                }
            }
        }

        public x contentType() {
            return this.c.contentType();
        }

        public long contentLength() {
            return this.c.contentLength();
        }

        public okio.e source() {
            return this.d;
        }

        public void close() {
            this.c.close();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            IOException iOException = this.f;
            if (iOException != null) {
                throw iOException;
            }
        }
    }
}
