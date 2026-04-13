package org.apache.httpcore.message;

import java.util.Locale;
import org.apache.httpcore.j;
import org.apache.httpcore.p;
import org.apache.httpcore.t;
import org.apache.httpcore.util.a;
import org.apache.httpcore.v;
import org.apache.httpcore.w;
import org.apache.httpcore.y;

/* compiled from: BasicHttpResponse */
public class h extends a implements p {
    private y c;
    private v d;
    private int e;
    private String f;
    private j g;
    private final w h;
    private Locale i;

    public h(y statusline, w catalog, Locale locale) {
        this.c = (y) a.g(statusline, "Status line");
        this.d = statusline.getProtocolVersion();
        this.e = statusline.getStatusCode();
        this.f = statusline.getReasonPhrase();
        this.h = catalog;
        this.i = locale;
    }

    public v getProtocolVersion() {
        return this.d;
    }

    public y j() {
        if (this.c == null) {
            v vVar = this.d;
            if (vVar == null) {
                vVar = t.HTTP_1_1;
            }
            int i2 = this.e;
            String str = this.f;
            if (str == null) {
                str = g(i2);
            }
            this.c = new n(vVar, i2, str);
        }
        return this.c;
    }

    public j a() {
        return this.g;
    }

    public void e(int code) {
        a.e(code, "Status code");
        this.c = null;
        this.e = code;
        this.f = null;
    }

    public void b(j entity) {
        this.g = entity;
    }

    /* access modifiers changed from: protected */
    public String g(int code) {
        w wVar = this.h;
        if (wVar == null) {
            return null;
        }
        Locale locale = this.i;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return wVar.a(code, locale);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(j());
        sb.append(' ');
        sb.append(this.a);
        if (this.g != null) {
            sb.append(' ');
            sb.append(this.g);
        }
        return sb.toString();
    }
}
