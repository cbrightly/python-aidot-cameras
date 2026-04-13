package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.a;
import org.apache.http.conn.r;
import org.apache.http.conn.routing.b;
import org.apache.http.h;
import org.apache.http.pool.c;

/* compiled from: CPoolEntry */
public class f extends c<b, r> {
    private final a i;
    private volatile boolean j;

    public f(a log, String id, b route, r conn, long timeToLive, TimeUnit tunit) {
        super(id, route, conn, timeToLive, tunit);
        this.i = log;
    }

    public void n() {
        this.j = true;
    }

    public boolean m() {
        return this.j;
    }

    public void l() {
        ((h) b()).close();
    }

    public void o() {
        ((h) b()).shutdown();
    }

    public boolean i(long now) {
        boolean expired = super.i(now);
        if (expired && this.i.isDebugEnabled()) {
            a aVar = this.i;
            aVar.debug("Connection " + this + " expired @ " + new Date(c()));
        }
        return expired;
    }

    public boolean h() {
        return !((h) b()).isOpen();
    }

    public void a() {
        try {
            l();
        } catch (IOException ex) {
            this.i.debug("I/O error closing connection", ex);
        }
    }
}
