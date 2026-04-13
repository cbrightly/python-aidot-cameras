package com.yanzhenjie.andserver.server;

import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.e;
import com.yanzhenjie.andserver.server.a.c;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import org.apache.httpcore.protocol.j;

/* compiled from: BasicServer */
public abstract class a<T extends c> implements e {
    protected final InetAddress a;
    protected final int b;
    protected final int c;
    protected final ServerSocketFactory d;
    protected final SSLContext e;
    protected final com.yanzhenjie.andserver.d f;
    protected final e.b g;
    /* access modifiers changed from: private */
    public org.apache.httpcore.impl.bootstrap.a h;
    protected boolean i;

    /* access modifiers changed from: protected */
    public abstract j d();

    a(T builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
    }

    public boolean isRunning() {
        return this.i;
    }

    public void a() {
        if (!this.i) {
            com.yanzhenjie.andserver.util.c.b().a(new C0232a());
        }
    }

    /* renamed from: com.yanzhenjie.andserver.server.a$a  reason: collision with other inner class name */
    /* compiled from: BasicServer */
    public class C0232a implements Runnable {
        C0232a() {
        }

        public void run() {
            try {
                org.apache.httpcore.impl.bootstrap.a unused = a.this.h = org.apache.httpcore.impl.bootstrap.d.a().h(a.this.d).i(org.apache.httpcore.config.c.b().e(true).g(true).i(true).h(a.this.c).b(8192).c(8192).d(8192).f(0).a()).f(a.this.a).e(a.this.b).j(a.this.e).k(new d(a.this.f)).g(com.yanzhenjie.andserver.a.a).c(org.slf4j.e.ANY_MARKER, a.this.d()).d(org.apache.httpcore.c.a).b();
                a.this.h.c();
                a.this.i = true;
                com.yanzhenjie.andserver.util.c.b().c(new C0233a());
                Runtime.getRuntime().addShutdownHook(new b());
            } catch (Exception e) {
                com.yanzhenjie.andserver.util.c.b().c(new c(e));
            }
        }

        /* renamed from: com.yanzhenjie.andserver.server.a$a$a  reason: collision with other inner class name */
        /* compiled from: BasicServer */
        public class C0233a implements Runnable {
            C0233a() {
            }

            public void run() {
                e.b bVar = a.this.g;
                if (bVar != null) {
                    bVar.onStarted();
                }
            }
        }

        /* renamed from: com.yanzhenjie.andserver.server.a$a$b */
        /* compiled from: BasicServer */
        public class b extends Thread {
            b() {
            }

            public void run() {
                a.this.h.b(3, TimeUnit.SECONDS);
            }
        }

        /* renamed from: com.yanzhenjie.andserver.server.a$a$c */
        /* compiled from: BasicServer */
        public class c implements Runnable {
            final /* synthetic */ Exception c;

            c(Exception exc) {
                this.c = exc;
            }

            public void run() {
                e.b bVar = a.this.g;
                if (bVar != null) {
                    bVar.onException(this.c);
                }
            }
        }
    }

    public void shutdown() {
        if (this.i) {
            com.yanzhenjie.andserver.util.c.b().a(new b());
        }
    }

    /* compiled from: BasicServer */
    public class b implements Runnable {
        b() {
        }

        public void run() {
            if (a.this.h != null) {
                a.this.h.b(3, TimeUnit.SECONDS);
                a.this.i = false;
                com.yanzhenjie.andserver.util.c.b().c(new C0234a());
            }
        }

        /* renamed from: com.yanzhenjie.andserver.server.a$b$a  reason: collision with other inner class name */
        /* compiled from: BasicServer */
        public class C0234a implements Runnable {
            C0234a() {
            }

            public void run() {
                e.b bVar = a.this.g;
                if (bVar != null) {
                    bVar.a();
                }
            }
        }
    }

    /* compiled from: BasicServer */
    public static final class d implements org.apache.httpcore.impl.bootstrap.c {
        private final com.yanzhenjie.andserver.d a;

        public d(@NonNull com.yanzhenjie.andserver.d initializer) {
            this.a = initializer;
        }

        public void a(SSLServerSocket socket) {
            this.a.a(socket);
        }
    }

    /* compiled from: BasicServer */
    public static abstract class c<T extends c, S extends a> {
        InetAddress a;
        int b;
        int c;
        ServerSocketFactory d;
        SSLContext e;
        com.yanzhenjie.andserver.d f;
        e.b g;

        c() {
        }

        public T g(int port) {
            this.b = port;
            return this;
        }

        public T j(int timeout, TimeUnit timeUnit) {
            this.c = (int) Math.min(timeUnit.toMillis((long) timeout), 2147483647L);
            return this;
        }

        public T h(SSLContext sslContext) {
            this.e = sslContext;
            return this;
        }

        public T i(com.yanzhenjie.andserver.d initializer) {
            this.f = initializer;
            return this;
        }

        public T f(e.b listener) {
            this.g = listener;
            return this;
        }
    }
}
