package org.apache.http.impl.io;

import org.apache.http.io.i;
import org.apache.http.message.t;
import org.apache.http.o;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: HttpRequestWriter */
public class n extends b<o> {
    public n(i buffer, t formatter, HttpParams params) {
        super(buffer, formatter, params);
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public void b(o message) {
        this.c.a(this.b, message.r());
        this.a.b(this.b);
    }
}
