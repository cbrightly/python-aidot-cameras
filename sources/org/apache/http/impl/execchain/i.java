package org.apache.http.impl.execchain;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.http.conn.k;
import org.apache.http.entity.g;
import org.apache.http.j;
import org.apache.http.q;

/* compiled from: ResponseEntityProxy */
public class i extends g implements k {
    private final c d;

    public static void h(q response, c connHolder) {
        j entity = response.a();
        if (entity != null && entity.isStreaming() && connHolder != null) {
            response.l(new i(entity, connHolder));
        }
    }

    i(j entity, c connHolder) {
        super(entity);
        this.d = connHolder;
    }

    private void f() {
        c cVar = this.d;
        if (cVar != null) {
            cVar.close();
        }
    }

    private void c() {
        c cVar = this.d;
        if (cVar != null) {
            cVar.c();
        }
    }

    public void g() {
        c cVar = this.d;
        if (cVar != null) {
            cVar.g();
        }
    }

    public boolean isRepeatable() {
        return false;
    }

    public InputStream getContent() {
        return new org.apache.http.conn.j(this.c.getContent(), this);
    }

    public void writeTo(OutputStream outstream) {
        if (outstream != null) {
            try {
                this.c.writeTo(outstream);
            } catch (IOException ex) {
                c();
                throw ex;
            } catch (RuntimeException ex2) {
                c();
                throw ex2;
            } catch (Throwable th) {
                f();
                throw th;
            }
        }
        g();
        f();
    }

    public boolean a(InputStream wrapped) {
        if (wrapped != null) {
            try {
                wrapped.close();
            } catch (IOException ex) {
                c();
                throw ex;
            } catch (RuntimeException ex2) {
                c();
                throw ex2;
            } catch (Throwable th) {
                f();
                throw th;
            }
        }
        g();
        f();
        return false;
    }

    public boolean d(InputStream wrapped) {
        boolean open;
        try {
            c cVar = this.d;
            open = cVar != null && !cVar.a();
            if (wrapped != null) {
                wrapped.close();
            }
            g();
        } catch (SocketException ex) {
            if (open) {
                throw ex;
            }
        } catch (IOException ex2) {
            c();
            throw ex2;
        } catch (RuntimeException ex3) {
            try {
                c();
                throw ex3;
            } catch (Throwable th) {
                f();
                throw th;
            }
        }
        f();
        return false;
    }

    public boolean b(InputStream wrapped) {
        f();
        return false;
    }

    public String toString() {
        return "ResponseEntityProxy{" + this.c + '}';
    }
}
