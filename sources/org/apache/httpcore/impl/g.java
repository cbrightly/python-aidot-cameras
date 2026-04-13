package org.apache.httpcore.impl;

import java.util.Locale;
import org.apache.httpcore.message.h;
import org.apache.httpcore.message.n;
import org.apache.httpcore.p;
import org.apache.httpcore.protocol.d;
import org.apache.httpcore.q;
import org.apache.httpcore.util.a;
import org.apache.httpcore.v;
import org.apache.httpcore.w;

/* compiled from: DefaultHttpResponseFactory */
public class g implements q {
    public static final g a = new g();
    protected final w b;

    public g(w catalog) {
        this.b = (w) a.g(catalog, "Reason phrase catalog");
    }

    public g() {
        this(h.a);
    }

    public p a(v ver, int status, d context) {
        a.g(ver, "HTTP version");
        Locale loc = b(context);
        return new h(new n(ver, status, this.b.a(status, loc)), this.b, loc);
    }

    /* access modifiers changed from: protected */
    public Locale b(d context) {
        return Locale.getDefault();
    }
}
