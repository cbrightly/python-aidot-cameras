package org.apache.http.message;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.e;
import org.apache.http.u;
import org.apache.http.util.a;
import org.apache.http.util.d;

/* compiled from: BasicHeaderValueParser */
public class g implements s {
    @Deprecated
    public static final g a = new g();
    public static final g b = new g();
    private static final BitSet c = w.a(61, 59, 44);
    private static final BitSet d = w.a(59, 44);
    private final w e = w.a;

    public static e[] e(String value, s parser) {
        a.i(value, "Value");
        d buffer = new d(value.length());
        buffer.append(value);
        return (parser != null ? parser : b).b(buffer, new v(0, value.length()));
    }

    public e[] b(d buffer, v cursor) {
        a.i(buffer, "Char array buffer");
        a.i(cursor, "Parser cursor");
        List<HeaderElement> elements = new ArrayList<>();
        while (!cursor.a()) {
            e element = a(buffer, cursor);
            if (element.getName().length() != 0 || element.getValue() != null) {
                elements.add(element);
            }
        }
        return (e[]) elements.toArray(new e[elements.size()]);
    }

    public e a(d buffer, v cursor) {
        a.i(buffer, "Char array buffer");
        a.i(cursor, "Parser cursor");
        u nvp = f(buffer, cursor);
        u[] params = null;
        if (!cursor.a() && buffer.charAt(cursor.b() - 1) != ',') {
            params = g(buffer, cursor);
        }
        return c(nvp.getName(), nvp.getValue(), params);
    }

    /* access modifiers changed from: protected */
    public e c(String name, String value, u[] params) {
        return new c(name, value, params);
    }

    public u[] g(d buffer, v cursor) {
        a.i(buffer, "Char array buffer");
        a.i(cursor, "Parser cursor");
        this.e.h(buffer, cursor);
        List<NameValuePair> params = new ArrayList<>();
        while (!cursor.a()) {
            params.add(f(buffer, cursor));
            if (buffer.charAt(cursor.b() - 1) == ',') {
                break;
            }
        }
        return (u[]) params.toArray(new u[params.size()]);
    }

    public u f(d buffer, v cursor) {
        a.i(buffer, "Char array buffer");
        a.i(cursor, "Parser cursor");
        String name = this.e.f(buffer, cursor, c);
        if (cursor.a()) {
            return new m(name, (String) null);
        }
        int delim = buffer.charAt(cursor.b());
        cursor.d(cursor.b() + 1);
        if (delim != 61) {
            return d(name, (String) null);
        }
        String value = this.e.g(buffer, cursor, d);
        if (!cursor.a()) {
            cursor.d(cursor.b() + 1);
        }
        return d(name, value);
    }

    /* access modifiers changed from: protected */
    public u d(String name, String value) {
        return new m(name, value);
    }
}
