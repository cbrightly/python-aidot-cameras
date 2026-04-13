package org.apache.httpcore.protocol;

import java.util.LinkedList;
import org.apache.httpcore.o;
import org.apache.httpcore.r;

/* compiled from: HttpProcessorBuilder */
public class i {
    private b<o> a;
    private b<r> b;

    public static i h() {
        return new i();
    }

    i() {
    }

    private b<o> i() {
        if (this.a == null) {
            this.a = new b<>();
        }
        return this.a;
    }

    private b<r> j() {
        if (this.b == null) {
            this.b = new b<>();
        }
        return this.b;
    }

    public i c(o e) {
        if (e == null) {
            return this;
        }
        i().b(e);
        return this;
    }

    public i e(o e) {
        if (e == null) {
            return this;
        }
        i().c(e);
        return this;
    }

    public i d(r e) {
        if (e == null) {
            return this;
        }
        j().b(e);
        return this;
    }

    public i f(r e) {
        if (e == null) {
            return this;
        }
        j().c(e);
        return this;
    }

    public i b(r... e) {
        if (e == null) {
            return this;
        }
        j().a(e);
        return this;
    }

    public i a(r... e) {
        return b(e);
    }

    public h g() {
        b<o> bVar = this.a;
        LinkedList<r> linkedList = null;
        LinkedList<o> d = bVar != null ? bVar.d() : null;
        b<r> bVar2 = this.b;
        if (bVar2 != null) {
            linkedList = bVar2.d();
        }
        return new m(d, linkedList);
    }
}
