package com.caverock.androidsvg;

import com.caverock.androidsvg.a;
import com.caverock.androidsvg.g;

/* compiled from: RenderOptions */
public class f {
    a.r a = null;
    e b = null;
    String c = null;
    g.b d = null;
    String e = null;
    g.b f = null;

    public f() {
    }

    public f(f other) {
        if (other != null) {
            this.a = other.a;
            this.b = other.b;
            this.d = other.d;
            this.e = other.e;
            this.f = other.f;
        }
    }

    public f a(String css) {
        this.a = new a(a.u.RenderOptions).d(css);
        return this;
    }

    public boolean b() {
        a.r rVar = this.a;
        return rVar != null && rVar.f() > 0;
    }

    public boolean c() {
        return this.b != null;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean f() {
        return this.d != null;
    }

    public f h(float minX, float minY, float width, float height) {
        this.f = new g.b(minX, minY, width, height);
        return this;
    }

    public boolean g() {
        return this.f != null;
    }

    public boolean d() {
        return this.c != null;
    }
}
