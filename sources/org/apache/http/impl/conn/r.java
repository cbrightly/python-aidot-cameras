package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.a;
import org.apache.http.conn.routing.b;
import org.apache.http.conn.routing.f;
import org.apache.http.conn.s;
import org.apache.http.pool.c;

@Deprecated
/* compiled from: HttpPoolEntry */
public class r extends c<b, s> {
    private final a i;
    private final f j;

    public r(a log, String id, b route, s conn, long timeToLive, TimeUnit tunit) {
        super(id, route, conn, timeToLive, tunit);
        this.i = log;
        this.j = new f(route);
    }

    public boolean i(long now) {
        boolean expired = super.i(now);
        if (expired && this.i.isDebugEnabled()) {
            a aVar = this.i;
            aVar.debug("Connection " + this + " expired @ " + new Date(c()));
        }
        return expired;
    }

    /* access modifiers changed from: package-private */
    public f n() {
        return this.j;
    }

    /* access modifiers changed from: package-private */
    public b m() {
        return (b) e();
    }

    /* access modifiers changed from: package-private */
    public b l() {
        return this.j.m();
    }

    public boolean h() {
        return !((s) b()).isOpen();
    }

    public void a() {
        try {
            ((s) b()).close();
        } catch (IOException ex) {
            this.i.debug("I/O error closing connection", ex);
        }
    }
}
