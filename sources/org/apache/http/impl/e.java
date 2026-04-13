package org.apache.http.impl;

import java.util.Locale;
import org.apache.http.message.i;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.apache.http.r;
import org.apache.http.util.a;
import org.apache.http.w;
import org.apache.http.y;

/* compiled from: DefaultHttpResponseFactory */
public class e implements r {
    public static final e a = new e();
    protected final w b;

    public e(w catalog) {
        this.b = (w) a.i(catalog, "Reason phrase catalog");
    }

    public e() {
        this(f.a);
    }

    public q a(y statusline, f context) {
        a.i(statusline, "Status line");
        return new i(statusline, this.b, b(context));
    }

    /* access modifiers changed from: protected */
    public Locale b(f context) {
        return Locale.getDefault();
    }
}
