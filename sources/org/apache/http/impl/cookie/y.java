package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.e;
import org.apache.http.message.c;
import org.apache.http.message.m;
import org.apache.http.message.v;
import org.apache.http.message.w;
import org.apache.http.u;
import org.apache.http.util.a;
import org.apache.http.util.d;

/* compiled from: NetscapeDraftHeaderParser */
public class y {
    public static final y a = new y();
    private static final BitSet b = w.a(61, 59);
    private static final BitSet c = w.a(59);
    private final w d = w.a;

    public e a(d buffer, v cursor) {
        a.i(buffer, "Char array buffer");
        a.i(cursor, "Parser cursor");
        u nvp = b(buffer, cursor);
        List<NameValuePair> params = new ArrayList<>();
        while (!cursor.a()) {
            params.add(b(buffer, cursor));
        }
        return new c(nvp.getName(), nvp.getValue(), (u[]) params.toArray(new u[params.size()]));
    }

    private u b(d buffer, v cursor) {
        String name = this.d.f(buffer, cursor, b);
        if (cursor.a()) {
            return new m(name, (String) null);
        }
        int delim = buffer.charAt(cursor.b());
        cursor.d(cursor.b() + 1);
        if (delim != 61) {
            return new m(name, (String) null);
        }
        String value = this.d.f(buffer, cursor, c);
        if (!cursor.a()) {
            cursor.d(cursor.b() + 1);
        }
        return new m(name, value);
    }
}
