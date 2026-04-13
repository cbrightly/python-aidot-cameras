package org.apache.http.impl.client;

import com.amazonaws.http.HttpHeader;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.d;
import org.apache.http.entity.g;
import org.apache.http.j;
import org.apache.http.k;

@Deprecated
/* compiled from: EntityEnclosingRequestWrapper */
public class w extends f0 implements k {
    /* access modifiers changed from: private */
    public boolean a1;
    private j p0;

    public w(k request) {
        super(request);
        l(request.a());
    }

    public j a() {
        return this.p0;
    }

    public void l(j entity) {
        this.p0 = entity != null ? new a(entity) : null;
        this.a1 = false;
    }

    public boolean m() {
        d expect = u(HttpHeader.EXPECT);
        return expect != null && "100-continue".equalsIgnoreCase(expect.getValue());
    }

    public boolean q() {
        j jVar = this.p0;
        return jVar == null || jVar.isRepeatable() || !this.a1;
    }

    /* compiled from: EntityEnclosingRequestWrapper */
    public class a extends g {
        a(j entity) {
            super(entity);
        }

        public InputStream getContent() {
            boolean unused = w.this.a1 = true;
            return super.getContent();
        }

        public void writeTo(OutputStream outstream) {
            boolean unused = w.this.a1 = true;
            super.writeTo(outstream);
        }
    }
}
