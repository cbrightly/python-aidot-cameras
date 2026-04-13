package org.apache.httpcore.impl.io;

import org.apache.httpcore.io.d;
import org.apache.httpcore.io.e;
import org.apache.httpcore.io.h;
import org.apache.httpcore.message.i;
import org.apache.httpcore.message.s;
import org.apache.httpcore.p;

/* compiled from: DefaultHttpResponseWriterFactory */
public class j implements e<p> {
    public static final j a = new j();
    private final s b;

    public j(s lineFormatter) {
        this.b = lineFormatter != null ? lineFormatter : i.b;
    }

    public j() {
        this((s) null);
    }

    public d<p> a(h buffer) {
        return new i(buffer, this.b);
    }
}
