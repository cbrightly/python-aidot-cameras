package org.apache.http.message;

import java.util.Locale;
import org.apache.http.j;
import org.apache.http.q;
import org.apache.http.t;
import org.apache.http.util.a;
import org.apache.http.v;
import org.apache.http.w;
import org.apache.http.y;

/* compiled from: BasicHttpResponse */
public class i extends a implements q {
    private Locale a1;
    private y f;
    private final w p0;
    private v q;
    private int x;
    private String y;
    private j z;

    public i(y statusline, w catalog, Locale locale) {
        this.f = (y) a.i(statusline, "Status line");
        this.q = statusline.getProtocolVersion();
        this.x = statusline.getStatusCode();
        this.y = statusline.getReasonPhrase();
        this.p0 = catalog;
        this.a1 = locale;
    }

    public i(y statusline) {
        this.f = (y) a.i(statusline, "Status line");
        this.q = statusline.getProtocolVersion();
        this.x = statusline.getStatusCode();
        this.y = statusline.getReasonPhrase();
        this.p0 = null;
        this.a1 = null;
    }

    public v getProtocolVersion() {
        return this.q;
    }

    public y j() {
        if (this.f == null) {
            v vVar = this.q;
            if (vVar == null) {
                vVar = t.HTTP_1_1;
            }
            int i = this.x;
            String str = this.y;
            if (str == null) {
                str = f(i);
            }
            this.f = new o(vVar, i, str);
        }
        return this.f;
    }

    public j a() {
        return this.z;
    }

    public void l(j entity) {
        this.z = entity;
    }

    /* access modifiers changed from: protected */
    public String f(int code) {
        w wVar = this.p0;
        if (wVar == null) {
            return null;
        }
        Locale locale = this.a1;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return wVar.a(code, locale);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(j());
        sb.append(' ');
        sb.append(this.c);
        if (this.z != null) {
            sb.append(' ');
            sb.append(this.z);
        }
        return sb.toString();
    }
}
