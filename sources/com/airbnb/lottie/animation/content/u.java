package com.airbnb.lottie.animation.content;

import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.model.content.s;
import com.airbnb.lottie.model.layer.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TrimPathContent */
public class u implements c, a.b {
    private final String a;
    private final boolean b;
    private final List<a.b> c = new ArrayList();
    private final s.a d;
    private final a<?, Float> e;
    private final a<?, Float> f;
    private final a<?, Float> g;

    public u(b layer, s trimPath) {
        this.a = trimPath.c();
        this.b = trimPath.g();
        this.d = trimPath.f();
        a<Float, Float> j = trimPath.e().j();
        this.e = j;
        a<Float, Float> j2 = trimPath.b().j();
        this.f = j2;
        a<Float, Float> j3 = trimPath.d().j();
        this.g = j3;
        layer.g(j);
        layer.g(j2);
        layer.g(j3);
        j.a(this);
        j2.a(this);
        j3.a(this);
    }

    public void a() {
        for (int i = 0; i < this.c.size(); i++) {
            this.c.get(i).a();
        }
    }

    public void b(List<c> list, List<c> list2) {
    }

    /* access modifiers changed from: package-private */
    public void d(a.b listener) {
        this.c.add(listener);
    }

    /* access modifiers changed from: package-private */
    public s.a j() {
        return this.d;
    }

    public a<?, Float> i() {
        return this.e;
    }

    public a<?, Float> e() {
        return this.f;
    }

    public a<?, Float> g() {
        return this.g;
    }

    public boolean k() {
        return this.b;
    }
}
