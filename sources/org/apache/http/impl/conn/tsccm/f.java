package org.apache.http.impl.conn.tsccm;

import java.util.LinkedList;
import java.util.Queue;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.conn.routing.b;

@Deprecated
/* compiled from: RouteSpecificPool */
public class f {
    private final a a = h.n(getClass());
    protected final b b;
    protected final int c;
    protected final org.apache.http.conn.params.a d;
    protected final LinkedList<b> e;
    protected final Queue<h> f;
    protected int g;

    public f(b route, org.apache.http.conn.params.a connPerRoute) {
        this.b = route;
        this.d = connPerRoute;
        this.c = connPerRoute.getMaxForRoute(route);
        this.e = new LinkedList<>();
        this.f = new LinkedList();
        this.g = 0;
    }

    public final b h() {
        return this.b;
    }

    public final int g() {
        return this.c;
    }

    public boolean j() {
        return this.g < 1 && this.f.isEmpty();
    }

    public int f() {
        return this.d.getMaxForRoute(this.b) - this.g;
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.impl.conn.tsccm.b a(java.lang.Object r6) {
        /*
            r5 = this;
            java.util.LinkedList<org.apache.http.impl.conn.tsccm.b> r0 = r5.e
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0034
            java.util.LinkedList<org.apache.http.impl.conn.tsccm.b> r0 = r5.e
            int r1 = r0.size()
            java.util.ListIterator r0 = r0.listIterator(r1)
        L_0x0012:
            boolean r1 = r0.hasPrevious()
            if (r1 == 0) goto L_0x0034
            java.lang.Object r1 = r0.previous()
            org.apache.http.impl.conn.tsccm.b r1 = (org.apache.http.impl.conn.tsccm.b) r1
            java.lang.Object r2 = r1.a()
            if (r2 == 0) goto L_0x0030
            java.lang.Object r2 = r1.a()
            boolean r2 = org.apache.http.util.h.a(r6, r2)
            if (r2 == 0) goto L_0x002f
            goto L_0x0030
        L_0x002f:
            goto L_0x0012
        L_0x0030:
            r0.remove()
            return r1
        L_0x0034:
            int r0 = r5.f()
            if (r0 != 0) goto L_0x005e
            java.util.LinkedList<org.apache.http.impl.conn.tsccm.b> r0 = r5.e
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x005e
            java.util.LinkedList<org.apache.http.impl.conn.tsccm.b> r0 = r5.e
            java.lang.Object r0 = r0.remove()
            org.apache.http.impl.conn.tsccm.b r0 = (org.apache.http.impl.conn.tsccm.b) r0
            r0.e()
            org.apache.http.conn.s r1 = r0.h()
            r1.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x005d
        L_0x0055:
            r2 = move-exception
            org.apache.commons.logging.a r3 = r5.a
            java.lang.String r4 = "I/O error closing connection"
            r3.debug(r4, r2)
        L_0x005d:
            return r0
        L_0x005e:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.tsccm.f.a(java.lang.Object):org.apache.http.impl.conn.tsccm.b");
    }

    public void e(b entry) {
        int i = this.g;
        if (i < 1) {
            throw new IllegalStateException("No entry created for this pool. " + this.b);
        } else if (i > this.e.size()) {
            this.e.add(entry);
        } else {
            throw new IllegalStateException("No entry allocated from this pool. " + this.b);
        }
    }

    public void b(b entry) {
        org.apache.http.util.a.a(this.b.equals(entry.i()), "Entry not planned for this pool");
        this.g++;
    }

    public boolean c(b entry) {
        boolean found = this.e.remove(entry);
        if (found) {
            this.g--;
        }
        return found;
    }

    public void d() {
        org.apache.http.util.b.a(this.g > 0, "There is no entry that could be dropped");
        this.g--;
    }

    public void l(h wt) {
        org.apache.http.util.a.i(wt, "Waiting thread");
        this.f.add(wt);
    }

    public boolean i() {
        return !this.f.isEmpty();
    }

    public h k() {
        return this.f.peek();
    }

    public void m(h wt) {
        if (wt != null) {
            this.f.remove(wt);
        }
    }
}
