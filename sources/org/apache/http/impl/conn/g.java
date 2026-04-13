package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSession;
import org.apache.http.conn.r;
import org.apache.http.h;
import org.apache.http.k;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.q;

/* compiled from: CPoolProxy */
public class g implements r, f {
    private volatile f c;

    g(f entry) {
        this.c = entry;
    }

    /* access modifiers changed from: package-private */
    public f i() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public f a() {
        f local = this.c;
        this.c = null;
        return local;
    }

    /* access modifiers changed from: package-private */
    public r g() {
        f local = this.c;
        if (local == null) {
            return null;
        }
        return (r) local.b();
    }

    /* access modifiers changed from: package-private */
    public r m() {
        r conn = g();
        if (conn != null) {
            return conn;
        }
        throw new ConnectionShutdownException();
    }

    public void close() {
        f local = this.c;
        if (local != null) {
            local.l();
        }
    }

    public void shutdown() {
        f local = this.c;
        if (local != null) {
            local.o();
        }
    }

    public boolean isOpen() {
        f local = this.c;
        if (local != null) {
            return !local.h();
        }
        return false;
    }

    public boolean l0() {
        h conn = g();
        if (conn != null) {
            return conn.l0();
        }
        return true;
    }

    public void y(int timeout) {
        m().y(timeout);
    }

    public void O0(Socket socket) {
        m().O0(socket);
    }

    public Socket q() {
        return m().q();
    }

    public SSLSession S0() {
        return m().S0();
    }

    public boolean a0(int timeout) {
        return m().a0(timeout);
    }

    public void E0(o request) {
        m().E0(request);
    }

    public void H(k request) {
        m().H(request);
    }

    public q K0() {
        return m().K0();
    }

    public void G0(q response) {
        m().G0(response);
    }

    public void flush() {
        m().flush();
    }

    public InetAddress w() {
        return m().w();
    }

    public int I0() {
        return m().I0();
    }

    public Object getAttribute(String id) {
        r conn = m();
        if (conn instanceof f) {
            return ((f) conn).getAttribute(id);
        }
        return null;
    }

    public void setAttribute(String id, Object obj) {
        r conn = m();
        if (conn instanceof f) {
            ((f) conn).setAttribute(id, obj);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CPoolProxy{");
        r conn = g();
        if (conn != null) {
            sb.append(conn);
        } else {
            sb.append("detached");
        }
        sb.append('}');
        return sb.toString();
    }

    public static h n(f poolEntry) {
        return new g(poolEntry);
    }

    private static g l(h conn) {
        if (g.class.isInstance(conn)) {
            return g.class.cast(conn);
        }
        throw new IllegalStateException("Unexpected connection proxy class: " + conn.getClass());
    }

    public static f j(h proxy) {
        f entry = l(proxy).i();
        if (entry != null) {
            return entry;
        }
        throw new ConnectionShutdownException();
    }

    public static f c(h conn) {
        return l(conn).a();
    }
}
