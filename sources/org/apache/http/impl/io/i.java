package org.apache.http.impl.io;

import org.apache.http.message.t;
import org.apache.http.o;

/* compiled from: DefaultHttpRequestWriter */
public class i extends b<o> {
    public i(org.apache.http.io.i buffer, t formatter) {
        super(buffer, formatter);
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public void b(o message) {
        this.c.a(this.b, message.r());
        this.a.b(this.b);
    }
}
