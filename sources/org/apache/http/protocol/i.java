package org.apache.http.protocol;

import java.util.LinkedList;
import java.util.List;
import org.apache.http.p;
import org.apache.http.s;

/* compiled from: HttpProcessorBuilder */
public class i {
    private c<p> a;
    private c<s> b;

    public static i j() {
        return new i();
    }

    i() {
    }

    private c<p> k() {
        if (this.a == null) {
            this.a = new c<>();
        }
        return this.a;
    }

    private c<s> l() {
        if (this.b == null) {
            this.b = new c<>();
        }
        return this.b;
    }

    public i e(p e) {
        if (e == null) {
            return this;
        }
        k().b(e);
        return this;
    }

    public i g(p e) {
        if (e == null) {
            return this;
        }
        k().c(e);
        return this;
    }

    public i a(p e) {
        return g(e);
    }

    public i d(p... e) {
        if (e == null) {
            return this;
        }
        k().a(e);
        return this;
    }

    public i c(p... e) {
        return d(e);
    }

    public i f(s e) {
        if (e == null) {
            return this;
        }
        l().b(e);
        return this;
    }

    public i h(s e) {
        if (e == null) {
            return this;
        }
        l().c(e);
        return this;
    }

    public i b(s e) {
        return h(e);
    }

    public h i() {
        c<p> cVar = this.a;
        LinkedList<s> linkedList = null;
        LinkedList<p> d = cVar != null ? cVar.d() : null;
        c<s> cVar2 = this.b;
        if (cVar2 != null) {
            linkedList = cVar2.d();
        }
        return new k((List<p>) d, (List<s>) linkedList);
    }
}
