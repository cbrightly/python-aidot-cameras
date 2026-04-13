package com.airbnb.lottie.model.layer;

import androidx.annotation.Nullable;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.j;
import com.airbnb.lottie.model.animatable.k;
import com.airbnb.lottie.model.animatable.l;
import com.airbnb.lottie.model.content.c;
import com.airbnb.lottie.model.content.h;
import java.util.List;
import java.util.Locale;

/* compiled from: Layer */
public class e {
    private final List<c> a;
    private final c0 b;
    private final String c;
    private final long d;
    private final a e;
    private final long f;
    @Nullable
    private final String g;
    private final List<h> h;
    private final l i;
    private final int j;
    private final int k;
    private final int l;
    private final float m;
    private final float n;
    private final float o;
    private final float p;
    @Nullable
    private final j q;
    @Nullable
    private final k r;
    @Nullable
    private final com.airbnb.lottie.model.animatable.b s;
    private final List<com.airbnb.lottie.value.a<Float>> t;
    private final b u;
    private final boolean v;
    @Nullable
    private final com.airbnb.lottie.model.content.a w;
    @Nullable
    private final com.airbnb.lottie.parser.j x;

    /* compiled from: Layer */
    public enum a {
        PRE_COMP,
        SOLID,
        IMAGE,
        NULL,
        SHAPE,
        TEXT,
        UNKNOWN
    }

    /* compiled from: Layer */
    public enum b {
        NONE,
        ADD,
        INVERT,
        LUMA,
        LUMA_INVERTED,
        UNKNOWN
    }

    public e(List<c> shapes, c0 composition, String layerName, long layerId, a layerType, long parentId, @Nullable String refId, List<h> masks, l transform, int solidWidth, int solidHeight, int solidColor, float timeStretch, float startFrame, float preCompWidth, float preCompHeight, @Nullable j text, @Nullable k textProperties, List<com.airbnb.lottie.value.a<Float>> inOutKeyframes, b matteType, @Nullable com.airbnb.lottie.model.animatable.b timeRemapping, boolean hidden, @Nullable com.airbnb.lottie.model.content.a blurEffect, @Nullable com.airbnb.lottie.parser.j dropShadowEffect) {
        this.a = shapes;
        this.b = composition;
        this.c = layerName;
        this.d = layerId;
        this.e = layerType;
        this.f = parentId;
        this.g = refId;
        this.h = masks;
        this.i = transform;
        this.j = solidWidth;
        this.k = solidHeight;
        this.l = solidColor;
        this.m = timeStretch;
        this.n = startFrame;
        this.o = preCompWidth;
        this.p = preCompHeight;
        this.q = text;
        this.r = textProperties;
        this.t = inOutKeyframes;
        this.u = matteType;
        this.s = timeRemapping;
        this.v = hidden;
        this.w = blurEffect;
        this.x = dropShadowEffect;
    }

    /* access modifiers changed from: package-private */
    public c0 b() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public float v() {
        return this.m;
    }

    /* access modifiers changed from: package-private */
    public float r() {
        return this.n / this.b.e();
    }

    /* access modifiers changed from: package-private */
    public List<com.airbnb.lottie.value.a<Float>> e() {
        return this.t;
    }

    public long d() {
        return this.d;
    }

    public String i() {
        return this.c;
    }

    @Nullable
    public String m() {
        return this.g;
    }

    /* access modifiers changed from: package-private */
    public float l() {
        return this.o;
    }

    /* access modifiers changed from: package-private */
    public float k() {
        return this.p;
    }

    /* access modifiers changed from: package-private */
    public List<h> g() {
        return this.h;
    }

    public a f() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public b h() {
        return this.u;
    }

    /* access modifiers changed from: package-private */
    public long j() {
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public List<c> n() {
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public l w() {
        return this.i;
    }

    /* access modifiers changed from: package-private */
    public int o() {
        return this.l;
    }

    /* access modifiers changed from: package-private */
    public int p() {
        return this.k;
    }

    /* access modifiers changed from: package-private */
    public int q() {
        return this.j;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public j s() {
        return this.q;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public k t() {
        return this.r;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public com.airbnb.lottie.model.animatable.b u() {
        return this.s;
    }

    public String toString() {
        return y("");
    }

    public boolean x() {
        return this.v;
    }

    @Nullable
    public com.airbnb.lottie.model.content.a a() {
        return this.w;
    }

    @Nullable
    public com.airbnb.lottie.parser.j c() {
        return this.x;
    }

    public String y(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(i());
        sb.append("\n");
        e parent = this.b.t(j());
        if (parent != null) {
            sb.append("\t\tParents: ");
            sb.append(parent.i());
            e parent2 = this.b.t(parent.j());
            while (parent2 != null) {
                sb.append("->");
                sb.append(parent2.i());
                parent2 = this.b.t(parent2.j());
            }
            sb.append(prefix);
            sb.append("\n");
        }
        if (!g().isEmpty()) {
            sb.append(prefix);
            sb.append("\tMasks: ");
            sb.append(g().size());
            sb.append("\n");
        }
        if (!(q() == 0 || p() == 0)) {
            sb.append(prefix);
            sb.append("\tBackground: ");
            sb.append(String.format(Locale.US, "%dx%d %X\n", new Object[]{Integer.valueOf(q()), Integer.valueOf(p()), Integer.valueOf(o())}));
        }
        if (!this.a.isEmpty()) {
            sb.append(prefix);
            sb.append("\tShapes:\n");
            for (Object shape : this.a) {
                sb.append(prefix);
                sb.append("\t\t");
                sb.append(shape);
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
