package org.apache.http.client.methods;

import com.amazonaws.http.HttpHeader;
import org.apache.http.client.utils.a;
import org.apache.http.d;
import org.apache.http.j;
import org.apache.http.k;

/* compiled from: HttpEntityEnclosingRequestBase */
public abstract class f extends m implements k {
    private j p0;

    public j a() {
        return this.p0;
    }

    public void l(j entity) {
        this.p0 = entity;
    }

    public boolean m() {
        d expect = u(HttpHeader.EXPECT);
        return expect != null && "100-continue".equalsIgnoreCase(expect.getValue());
    }

    public Object clone() {
        f clone = (f) super.clone();
        j jVar = this.p0;
        if (jVar != null) {
            clone.p0 = (j) a.a(jVar);
        }
        return clone;
    }
}
