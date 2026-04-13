package org.apache.httpcore.impl.io;

import org.apache.httpcore.g;
import org.apache.httpcore.io.d;
import org.apache.httpcore.io.h;
import org.apache.httpcore.l;
import org.apache.httpcore.message.i;
import org.apache.httpcore.message.s;
import org.apache.httpcore.util.a;

/* compiled from: AbstractMessageWriter */
public abstract class b<T extends l> implements d<T> {
    protected final h a;
    protected final org.apache.httpcore.util.d b;
    protected final s c;

    /* access modifiers changed from: protected */
    public abstract void b(T t);

    public b(h buffer, s formatter) {
        this.a = (h) a.g(buffer, "Session input buffer");
        this.c = formatter != null ? formatter : i.b;
        this.b = new org.apache.httpcore.util.d(128);
    }

    public void a(T message) {
        a.g(message, "HTTP message");
        b(message);
        g it = message.i();
        while (it.hasNext()) {
            this.a.b(this.c.a(this.b, it.a()));
        }
        this.b.clear();
        this.a.b(this.b);
    }
}
