package org.apache.httpcore.message;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import org.apache.httpcore.HeaderElement;
import org.apache.httpcore.NameValuePair;
import org.apache.httpcore.f;
import org.apache.httpcore.u;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.d;

/* compiled from: BasicHeaderValueParser */
public class e implements r {
    @Deprecated
    public static final e a = new e();
    public static final e b = new e();
    private static final BitSet c = v.a(61, 59, 44);
    private static final BitSet d = v.a(59, 44);
    private final v e = v.a;

    public static f[] d(String value, r parser) {
        a.g(value, "Value");
        d buffer = new d(value.length());
        buffer.append(value);
        return (parser != null ? parser : b).a(buffer, new u(0, value.length()));
    }

    public f[] a(d buffer, u cursor) {
        a.g(buffer, "Char array buffer");
        a.g(cursor, "Parser cursor");
        List<HeaderElement> elements = new ArrayList<>();
        while (!cursor.a()) {
            f element = e(buffer, cursor);
            if (!element.getName().isEmpty() || element.getValue() != null) {
                elements.add(element);
            }
        }
        return (f[]) elements.toArray(new f[elements.size()]);
    }

    public f e(d buffer, u cursor) {
        a.g(buffer, "Char array buffer");
        a.g(cursor, "Parser cursor");
        u nvp = f(buffer, cursor);
        u[] params = null;
        if (!cursor.a() && buffer.charAt(cursor.b() - 1) != ',') {
            params = g(buffer, cursor);
        }
        return b(nvp.getName(), nvp.getValue(), params);
    }

    /* access modifiers changed from: protected */
    public f b(String name, String value, u[] params) {
        return new c(name, value, params);
    }

    public u[] g(d buffer, u cursor) {
        a.g(buffer, "Char array buffer");
        a.g(cursor, "Parser cursor");
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

    public u f(d buffer, u cursor) {
        a.g(buffer, "Char array buffer");
        a.g(cursor, "Parser cursor");
        String name = this.e.f(buffer, cursor, c);
        if (cursor.a()) {
            return new l(name, (String) null);
        }
        int delim = buffer.charAt(cursor.b());
        cursor.d(cursor.b() + 1);
        if (delim != 61) {
            return c(name, (String) null);
        }
        String value = this.e.g(buffer, cursor, d);
        if (!cursor.a()) {
            cursor.d(cursor.b() + 1);
        }
        return c(name, value);
    }

    /* access modifiers changed from: protected */
    public u c(String name, String value) {
        return new l(name, value);
    }
}
