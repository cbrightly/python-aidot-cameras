package org.apache.http.impl.io;

import org.apache.http.io.e;
import org.apache.http.io.f;
import org.apache.http.io.i;
import org.apache.http.message.t;
import org.apache.http.o;

/* compiled from: DefaultHttpRequestWriterFactory */
public class j implements f<o> {
    public static final j a = new j();
    private final t b;

    public j(t lineFormatter) {
        this.b = lineFormatter != null ? lineFormatter : org.apache.http.message.j.b;
    }

    public j() {
        this((t) null);
    }

    public e<o> a(i buffer) {
        return new i(buffer, this.b);
    }
}
