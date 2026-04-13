package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.c;
import org.apache.http.e;
import org.apache.http.f;
import org.apache.http.g;
import org.apache.http.util.a;

/* compiled from: BasicHeaderElementIterator */
public class d implements f {
    private final g c;
    private final s d;
    private e f;
    private org.apache.http.util.d q;
    private v x;

    public d(g headerIterator, s parser) {
        this.f = null;
        this.q = null;
        this.x = null;
        this.c = (g) a.i(headerIterator, "Header iterator");
        this.d = (s) a.i(parser, "Parser");
    }

    public d(g headerIterator) {
        this(headerIterator, g.b);
    }

    private void b() {
        this.x = null;
        this.q = null;
        while (this.c.hasNext()) {
            org.apache.http.d h = this.c.a();
            if (h instanceof c) {
                org.apache.http.util.d buffer = ((c) h).getBuffer();
                this.q = buffer;
                v vVar = new v(0, buffer.length());
                this.x = vVar;
                vVar.d(((c) h).getValuePos());
                return;
            }
            String value = h.getValue();
            if (value != null) {
                org.apache.http.util.d dVar = new org.apache.http.util.d(value.length());
                this.q = dVar;
                dVar.append(value);
                this.x = new v(0, this.q.length());
                return;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
            r3 = this;
        L_0x0000:
            org.apache.http.g r0 = r3.c
            boolean r0 = r0.hasNext()
            if (r0 != 0) goto L_0x000e
            org.apache.http.message.v r0 = r3.x
            if (r0 == 0) goto L_0x000d
            goto L_0x000e
        L_0x000d:
            return
        L_0x000e:
            org.apache.http.message.v r0 = r3.x
            if (r0 == 0) goto L_0x0018
            boolean r0 = r0.a()
            if (r0 == 0) goto L_0x001b
        L_0x0018:
            r3.b()
        L_0x001b:
            org.apache.http.message.v r0 = r3.x
            if (r0 == 0) goto L_0x0000
        L_0x001f:
            org.apache.http.message.v r0 = r3.x
            boolean r0 = r0.a()
            if (r0 != 0) goto L_0x0046
            org.apache.http.message.s r0 = r3.d
            org.apache.http.util.d r1 = r3.q
            org.apache.http.message.v r2 = r3.x
            org.apache.http.e r0 = r0.a(r1, r2)
            java.lang.String r1 = r0.getName()
            int r1 = r1.length()
            if (r1 != 0) goto L_0x0043
            java.lang.String r1 = r0.getValue()
            if (r1 == 0) goto L_0x0042
            goto L_0x0043
        L_0x0042:
            goto L_0x001f
        L_0x0043:
            r3.f = r0
            return
        L_0x0046:
            org.apache.http.message.v r0 = r3.x
            boolean r0 = r0.a()
            if (r0 == 0) goto L_0x0054
            r0 = 0
            r3.x = r0
            r3.q = r0
            goto L_0x0000
        L_0x0054:
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.message.d.c():void");
    }

    public boolean hasNext() {
        if (this.f == null) {
            c();
        }
        return this.f != null;
    }

    public e nextElement() {
        if (this.f == null) {
            c();
        }
        if (this.f != null) {
            e element = this.f;
            this.f = null;
            return element;
        }
        throw new NoSuchElementException("No more header elements available");
    }

    public final Object next() {
        return nextElement();
    }

    public void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }
}
