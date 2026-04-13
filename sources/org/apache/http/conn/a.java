package org.apache.http.conn;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.http.entity.g;
import org.apache.http.j;

@Deprecated
/* compiled from: BasicManagedEntity */
public class a extends g implements g, k {
    protected q d;
    protected final boolean f;

    public a(j entity, q conn, boolean reuse) {
        super(entity);
        org.apache.http.util.a.i(conn, "Connection");
        this.d = conn;
        this.f = reuse;
    }

    public boolean isRepeatable() {
        return false;
    }

    public InputStream getContent() {
        return new j(this.c.getContent(), this);
    }

    private void f() {
        q qVar = this.d;
        if (qVar != null) {
            try {
                if (this.f) {
                    org.apache.http.util.g.a(this.c);
                    this.d.i0();
                } else {
                    qVar.v0();
                }
            } finally {
                h();
            }
        }
    }

    public void writeTo(OutputStream outstream) {
        super.writeTo(outstream);
        f();
    }

    public void c() {
        q qVar = this.d;
        if (qVar != null) {
            try {
                qVar.c();
            } finally {
                this.d = null;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public boolean a(InputStream wrapped) {
        try {
            q qVar = this.d;
            if (qVar != null) {
                if (this.f) {
                    wrapped.close();
                    this.d.i0();
                } else {
                    qVar.v0();
                }
            }
            h();
            return false;
        } catch (Throwable th) {
            h();
            throw th;
        }
    }

    public boolean d(InputStream wrapped) {
        boolean valid;
        try {
            q qVar = this.d;
            if (qVar != null) {
                if (this.f) {
                    valid = qVar.isOpen();
                    wrapped.close();
                    this.d.i0();
                } else {
                    qVar.v0();
                }
            }
        } catch (SocketException ex) {
            if (valid) {
                throw ex;
            }
        } catch (Throwable th) {
            h();
            throw th;
        }
        h();
        return false;
    }

    public boolean b(InputStream wrapped) {
        q qVar = this.d;
        if (qVar == null) {
            return false;
        }
        qVar.c();
        return false;
    }

    /* access modifiers changed from: protected */
    public void h() {
        q qVar = this.d;
        if (qVar != null) {
            try {
                qVar.g();
            } finally {
                this.d = null;
            }
        }
    }
}
