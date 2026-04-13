package org.apache.http.impl.conn;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.conn.b;
import org.apache.http.conn.q;
import org.apache.http.conn.s;
import org.apache.http.k;
import org.apache.http.o;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: AbstractClientConnAdapter */
public abstract class a implements q, f {
    private final b c;
    private volatile s d;
    private volatile boolean f = false;
    private volatile boolean q = false;
    private volatile long x = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;

    protected a(b mgr, s conn) {
        this.c = mgr;
        this.d = conn;
    }

    /* access modifiers changed from: protected */
    public synchronized void i() {
        this.d = null;
        this.x = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
    }

    /* access modifiers changed from: protected */
    public s l() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public b j() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public boolean n() {
        return this.q;
    }

    /* access modifiers changed from: protected */
    public final void a(s wrappedConn) {
        if (n() || wrappedConn == null) {
            throw new ConnectionShutdownException();
        }
    }

    public boolean isOpen() {
        s conn = l();
        if (conn == null) {
            return false;
        }
        return conn.isOpen();
    }

    public boolean l0() {
        s conn;
        if (!n() && (conn = l()) != null) {
            return conn.l0();
        }
        return true;
    }

    public void y(int timeout) {
        s conn = l();
        a(conn);
        conn.y(timeout);
    }

    public void flush() {
        s conn = l();
        a(conn);
        conn.flush();
    }

    public boolean a0(int timeout) {
        s conn = l();
        a(conn);
        return conn.a0(timeout);
    }

    public void G0(org.apache.http.q response) {
        s conn = l();
        a(conn);
        v0();
        conn.G0(response);
    }

    public org.apache.http.q K0() {
        s conn = l();
        a(conn);
        v0();
        return conn.K0();
    }

    public void H(k request) {
        s conn = l();
        a(conn);
        v0();
        conn.H(request);
    }

    public void E0(o request) {
        s conn = l();
        a(conn);
        v0();
        conn.E0(request);
    }

    public InetAddress w() {
        s conn = l();
        a(conn);
        return conn.w();
    }

    public int I0() {
        s conn = l();
        a(conn);
        return conn.I0();
    }

    public void O0(Socket socket) {
        throw new UnsupportedOperationException();
    }

    public Socket q() {
        s conn = l();
        a(conn);
        if (!isOpen()) {
            return null;
        }
        return conn.q();
    }

    public SSLSession S0() {
        s conn = l();
        a(conn);
        if (!isOpen()) {
            return null;
        }
        Socket sock = conn.q();
        if (sock instanceof SSLSocket) {
            return ((SSLSocket) sock).getSession();
        }
        return null;
    }

    public void i0() {
        this.f = true;
    }

    public void v0() {
        this.f = false;
    }

    public boolean m() {
        return this.f;
    }

    public void O(long duration, TimeUnit unit) {
        if (duration > 0) {
            this.x = unit.toMillis(duration);
        } else {
            this.x = -1;
        }
    }

    public synchronized void g() {
        if (!this.q) {
            this.q = true;
            this.c.d(this, this.x, TimeUnit.MILLISECONDS);
        }
    }

    public synchronized void c() {
        if (!this.q) {
            this.q = true;
            v0();
            try {
                shutdown();
            } catch (IOException e) {
            }
            this.c.d(this, this.x, TimeUnit.MILLISECONDS);
        }
    }

    public Object getAttribute(String id) {
        s conn = l();
        a(conn);
        if (conn instanceof f) {
            return ((f) conn).getAttribute(id);
        }
        return null;
    }

    public void setAttribute(String id, Object obj) {
        s conn = l();
        a(conn);
        if (conn instanceof f) {
            ((f) conn).setAttribute(id, obj);
        }
    }
}
