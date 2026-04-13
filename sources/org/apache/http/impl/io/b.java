package org.apache.http.impl.io;

import org.apache.http.g;
import org.apache.http.io.e;
import org.apache.http.io.i;
import org.apache.http.message.j;
import org.apache.http.message.t;
import org.apache.http.n;
import org.apache.http.params.HttpParams;
import org.apache.http.util.a;
import org.apache.http.util.d;

/* compiled from: AbstractMessageWriter */
public abstract class b<T extends n> implements e<T> {
    protected final i a;
    protected final d b;
    protected final t c;

    /* access modifiers changed from: protected */
    public abstract void b(T t);

    @Deprecated
    public b(i buffer, t formatter, HttpParams params) {
        a.i(buffer, "Session input buffer");
        this.a = buffer;
        this.b = new d(128);
        this.c = formatter != null ? formatter : j.b;
    }

    public b(i buffer, t formatter) {
        this.a = (i) a.i(buffer, "Session input buffer");
        this.c = formatter != null ? formatter : j.b;
        this.b = new d(128);
    }

    public void a(T message) {
        a.i(message, "HTTP message");
        b(message);
        g it = message.i();
        while (it.hasNext()) {
            this.a.b(this.c.b(this.b, it.a()));
        }
        this.b.clear();
        this.a.b(this.b);
    }
}
