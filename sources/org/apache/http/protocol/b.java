package org.apache.http.protocol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.q;
import org.apache.http.s;

@Deprecated
/* compiled from: BasicHttpProcessor */
public final class b implements h, Cloneable {
    protected final List<p> c = new ArrayList();
    protected final List<s> d = new ArrayList();

    public void h(p itcp) {
        if (itcp != null) {
            this.c.add(itcp);
        }
    }

    public void i(p itcp, int index) {
        if (itcp != null) {
            this.c.add(index, itcp);
        }
    }

    public void k(s itcp, int index) {
        if (itcp != null) {
            this.d.add(index, itcp);
        }
    }

    public void s(Class<? extends p> clazz) {
        Iterator<p> it = this.c.iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(clazz)) {
                it.remove();
            }
        }
    }

    public void t(Class<? extends s> clazz) {
        Iterator<s> it = this.d.iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(clazz)) {
                it.remove();
            }
        }
    }

    public final void c(p interceptor) {
        h(interceptor);
    }

    public final void d(p interceptor, int index) {
        i(interceptor, index);
    }

    public int p() {
        return this.c.size();
    }

    public p o(int index) {
        if (index < 0 || index >= this.c.size()) {
            return null;
        }
        return this.c.get(index);
    }

    public void l() {
        this.c.clear();
    }

    public void j(s itcp) {
        if (itcp != null) {
            this.d.add(itcp);
        }
    }

    public final void e(s interceptor) {
        j(interceptor);
    }

    public final void f(s interceptor, int index) {
        k(interceptor, index);
    }

    public int r() {
        return this.d.size();
    }

    public s q(int index) {
        if (index < 0 || index >= this.d.size()) {
            return null;
        }
        return this.d.get(index);
    }

    public void m() {
        this.d.clear();
    }

    public void b(o request, f context) {
        for (p interceptor : this.c) {
            interceptor.b(request, context);
        }
    }

    public void a(q response, f context) {
        for (s interceptor : this.d) {
            interceptor.a(response, context);
        }
    }

    /* access modifiers changed from: protected */
    public void n(b target) {
        target.c.clear();
        target.c.addAll(this.c);
        target.d.clear();
        target.d.addAll(this.d);
    }

    public Object clone() {
        b clone = (b) super.clone();
        n(clone);
        return clone;
    }
}
