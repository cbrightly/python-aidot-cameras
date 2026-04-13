package org.apache.httpcore.impl.io;

import org.apache.httpcore.io.h;
import org.apache.httpcore.message.s;
import org.apache.httpcore.p;

/* compiled from: DefaultHttpResponseWriter */
public class i extends b<p> {
    public i(h buffer, s formatter) {
        super(buffer, formatter);
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public void b(p message) {
        this.c.b(this.b, message.j());
        this.a.b(this.b);
    }
}
