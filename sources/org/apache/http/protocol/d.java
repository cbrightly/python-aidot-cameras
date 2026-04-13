package org.apache.http.protocol;

import org.apache.http.util.a;

@Deprecated
/* compiled from: DefaultedHttpContext */
public final class d implements f {
    private final f c;
    private final f d;

    public d(f local, f defaults) {
        this.c = (f) a.i(local, "HTTP context");
        this.d = defaults;
    }

    public Object getAttribute(String id) {
        Object obj = this.c.getAttribute(id);
        if (obj == null) {
            return this.d.getAttribute(id);
        }
        return obj;
    }

    public void setAttribute(String id, Object obj) {
        this.c.setAttribute(id, obj);
    }

    public String toString() {
        return "[local: " + this.c + "defaults: " + this.d + "]";
    }
}
