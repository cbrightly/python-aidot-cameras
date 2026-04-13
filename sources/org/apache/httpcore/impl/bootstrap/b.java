package org.apache.httpcore.impl.bootstrap;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.httpcore.config.c;
import org.apache.httpcore.i;
import org.apache.httpcore.protocol.l;
import org.apache.httpcore.s;

/* compiled from: RequestListener */
public class b implements Runnable {
    private final c c;
    private final ServerSocket d;
    private final l f;
    private final i<? extends s> q;
    private final org.apache.httpcore.c x;
    private final ExecutorService y;
    private final AtomicBoolean z = new AtomicBoolean(false);

    public b(c socketConfig, ServerSocket serversocket, l httpService, i<? extends s> connectionFactory, org.apache.httpcore.c exceptionLogger, ExecutorService executorService) {
        this.c = socketConfig;
        this.d = serversocket;
        this.q = connectionFactory;
        this.f = httpService;
        this.x = exceptionLogger;
        this.y = executorService;
    }

    public void run() {
        while (!a() && !Thread.interrupted()) {
            try {
                Socket socket = this.d.accept();
                socket.setSoTimeout(this.c.h());
                socket.setKeepAlive(this.c.i());
                socket.setTcpNoDelay(this.c.k());
                if (this.c.d() > 0) {
                    socket.setReceiveBufferSize(this.c.d());
                }
                if (this.c.e() > 0) {
                    socket.setSendBufferSize(this.c.e());
                }
                if (this.c.f() >= 0) {
                    socket.setSoLinger(true, this.c.f());
                }
                this.y.execute(new f(this.f, (s) this.q.a(socket), this.x));
            } catch (Exception ex) {
                this.x.a(ex);
                return;
            }
        }
    }

    public boolean a() {
        return this.z.get();
    }

    public void b() {
        if (this.z.compareAndSet(false, true)) {
            this.d.close();
        }
    }
}
