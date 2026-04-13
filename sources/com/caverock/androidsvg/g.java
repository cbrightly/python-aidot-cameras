package com.caverock.androidsvg;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import androidx.core.view.ViewCompat;
import com.caverock.androidsvg.a;
import com.luck.picture.lib.config.PictureMimeType;
import com.meituan.robust.Constants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import meshsdk.BaseResp;

/* compiled from: SVG */
public class g {
    private static i a;
    private static boolean b = true;
    private f0 c = null;
    private String d = "";
    private String e = "";
    private float f = 96.0f;
    private a.r g = new a.r();
    private Map<String, l0> h = new HashMap();

    /* compiled from: SVG */
    public interface b1 {
    }

    /* compiled from: SVG */
    public enum d1 {
        px,
        em,
        ex,
        in,
        cm,
        mm,
        pt,
        pc,
        percent
    }

    /* compiled from: SVG */
    public interface g0 {
        Set<String> a();

        String b();

        void c(Set<String> set);

        void e(Set<String> set);

        void f(Set<String> set);

        Set<String> getRequiredFeatures();

        void h(Set<String> set);

        void i(String str);

        Set<String> k();

        Set<String> l();
    }

    /* compiled from: SVG */
    public interface j0 {
        void g(n0 n0Var);

        List<n0> getChildren();
    }

    /* compiled from: SVG */
    public enum k {
        pad,
        reflect,
        repeat
    }

    /* compiled from: SVG */
    public interface n {
        void j(Matrix matrix);
    }

    /* compiled from: SVG */
    public interface t {
    }

    /* compiled from: SVG */
    public interface x {
        void a(float f, float f2, float f3, float f4);

        void b(float f, float f2);

        void c(float f, float f2, float f3, float f4, float f5, float f6);

        void close();

        void d(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5);

        void e(float f, float f2);
    }

    /* compiled from: SVG */
    public interface x0 {
        b1 d();
    }

    g() {
    }

    public static g l(InputStream is) {
        return new j().z(is, b);
    }

    public static g o(String svg) {
        return new j().z(new ByteArrayInputStream(svg.getBytes()), b);
    }

    public static g m(Context context, int resourceId) {
        return n(context.getResources(), resourceId);
    }

    public static g n(Resources resources, int resourceId) {
        j parser = new j();
        InputStream is = resources.openRawResource(resourceId);
        try {
            return parser.z(is, b);
        } finally {
            try {
                is.close();
            } catch (IOException e2) {
            }
        }
    }

    public Picture u(f renderOptions) {
        d1 d1Var;
        p pVar;
        b viewBox = (renderOptions == null || !renderOptions.f()) ? this.c.p : renderOptions.d;
        if (renderOptions == null || !renderOptions.g()) {
            f0 f0Var = this.c;
            p pVar2 = f0Var.s;
            if (pVar2 != null && pVar2.d != (d1Var = d1.percent) && (pVar = f0Var.t) != null && pVar.d != d1Var) {
                return t((int) Math.ceil((double) pVar2.b(this.f)), (int) Math.ceil((double) this.c.t.b(this.f)), renderOptions);
            } else if (pVar2 == null || viewBox == null) {
                p pVar3 = f0Var.t;
                if (pVar3 == null || viewBox == null) {
                    return t(512, 512, renderOptions);
                }
                float h2 = pVar3.b(this.f);
                return t((int) Math.ceil((double) ((viewBox.c * h2) / viewBox.d)), (int) Math.ceil((double) h2), renderOptions);
            } else {
                float w2 = pVar2.b(this.f);
                return t((int) Math.ceil((double) w2), (int) Math.ceil((double) ((viewBox.d * w2) / viewBox.c)), renderOptions);
            }
        } else {
            return t((int) Math.ceil((double) renderOptions.f.b()), (int) Math.ceil((double) renderOptions.f.c()), renderOptions);
        }
    }

    public Picture t(int widthInPixels, int heightInPixels, f renderOptions) {
        f fVar;
        Picture picture = new Picture();
        Canvas canvas = picture.beginRecording(widthInPixels, heightInPixels);
        if (renderOptions == null || renderOptions.f == null) {
            if (renderOptions != null) {
                fVar = new f(renderOptions);
            }
            renderOptions = fVar;
            renderOptions.h(0.0f, 0.0f, (float) widthInPixels, (float) heightInPixels);
        }
        new h(canvas, this.f).O0(this, renderOptions);
        picture.endRecording();
        return picture;
    }

    public void r(Canvas canvas) {
        s(canvas, (f) null);
    }

    public void s(Canvas canvas, f renderOptions) {
        if (renderOptions == null) {
            renderOptions = new f();
        }
        if (!renderOptions.g()) {
            renderOptions.h(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
        }
        new h(canvas, this.f).O0(this, renderOptions);
    }

    public float h() {
        if (this.c != null) {
            return e(this.f).c;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public void z(String value) {
        f0 f0Var = this.c;
        if (f0Var != null) {
            f0Var.s = j.o0(value);
            return;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public float f() {
        if (this.c != null) {
            return e(this.f).d;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public void x(String value) {
        f0 f0Var = this.c;
        if (f0Var != null) {
            f0Var.t = j.o0(value);
            return;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public void y(float minX, float minY, float width, float height) {
        f0 f0Var = this.c;
        if (f0Var != null) {
            f0Var.p = new b(minX, minY, width, height);
            return;
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    public RectF g() {
        f0 f0Var = this.c;
        if (f0Var != null) {
            b bVar = f0Var.p;
            if (bVar == null) {
                return null;
            }
            return bVar.d();
        }
        throw new IllegalArgumentException("SVG document is empty");
    }

    /* access modifiers changed from: package-private */
    public f0 p() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public void A(f0 rootElement) {
        this.c = rootElement;
    }

    /* access modifiers changed from: package-private */
    public n0 v(String iri) {
        if (iri == null) {
            return null;
        }
        String iri2 = c(iri);
        if (iri2.length() <= 1 || !iri2.startsWith("#")) {
            return null;
        }
        return j(iri2.substring(1));
    }

    private String c(String str) {
        if (str.startsWith("\"") && str.endsWith("\"")) {
            str = str.substring(1, str.length() - 1).replace("\\\"", "\"");
        } else if (str.startsWith("'") && str.endsWith("'")) {
            str = str.substring(1, str.length() - 1).replace("\\'", "'");
        }
        return str.replace("\\\n", "").replace("\\A", "\n");
    }

    private b e(float dpi) {
        d1 d1Var;
        d1 d1Var2;
        d1 d1Var3;
        d1 d1Var4;
        float hOut;
        d1 d1Var5;
        f0 f0Var = this.c;
        p w2 = f0Var.s;
        p h2 = f0Var.t;
        if (w2 == null || w2.i() || (d1Var = w2.d) == (d1Var2 = d1.percent) || d1Var == (d1Var3 = d1.em) || d1Var == (d1Var4 = d1.ex)) {
            return new b(-1.0f, -1.0f, -1.0f, -1.0f);
        }
        float wOut = w2.b(dpi);
        if (h2 == null) {
            b bVar = this.c.p;
            if (bVar != null) {
                hOut = (bVar.d * wOut) / bVar.c;
            } else {
                hOut = wOut;
            }
        } else if (h2.i() || (d1Var5 = h2.d) == d1Var2 || d1Var5 == d1Var3 || d1Var5 == d1Var4) {
            return new b(-1.0f, -1.0f, -1.0f, -1.0f);
        } else {
            hOut = h2.b(dpi);
        }
        return new b(0.0f, 0.0f, wOut, hOut);
    }

    /* access modifiers changed from: package-private */
    public void a(a.r ruleset) {
        this.g.b(ruleset);
    }

    /* access modifiers changed from: package-private */
    public List<a.p> d() {
        return this.g.c();
    }

    /* access modifiers changed from: package-private */
    public boolean q() {
        return !this.g.d();
    }

    /* access modifiers changed from: package-private */
    public void b() {
        this.g.e(a.u.RenderOptions);
    }

    /* compiled from: SVG */
    public static class b {
        float a;
        float b;
        float c;
        float d;

        b(float minX, float minY, float width, float height) {
            this.a = minX;
            this.b = minY;
            this.c = width;
            this.d = height;
        }

        b(b copy) {
            this.a = copy.a;
            this.b = copy.b;
            this.c = copy.c;
            this.d = copy.d;
        }

        static b a(float minX, float minY, float maxX, float maxY) {
            return new b(minX, minY, maxX - minX, maxY - minY);
        }

        /* access modifiers changed from: package-private */
        public RectF d() {
            return new RectF(this.a, this.b, b(), c());
        }

        /* access modifiers changed from: package-private */
        public float b() {
            return this.a + this.c;
        }

        /* access modifiers changed from: package-private */
        public float c() {
            return this.b + this.d;
        }

        /* access modifiers changed from: package-private */
        public void e(b other) {
            float f = other.a;
            if (f < this.a) {
                this.a = f;
            }
            float f2 = other.b;
            if (f2 < this.b) {
                this.b = f2;
            }
            if (other.b() > b()) {
                this.c = other.b() - this.a;
            }
            if (other.c() > c()) {
                this.d = other.c() - this.b;
            }
        }

        public String toString() {
            return Constants.ARRAY_TYPE + this.a + " " + this.b + " " + this.c + " " + this.d + "]";
        }
    }

    /* compiled from: SVG */
    public static class e0 implements Cloneable {
        p A4;
        Integer B4;
        b C4;
        C0051g D4;
        h E4;
        f F4;
        Boolean G4;
        c H4;
        String I4;
        String J4;
        String K4;
        Boolean L4;
        Boolean M4;
        o0 N4;
        Float O4;
        String P4;
        a Q4;
        String R4;
        o0 S4;
        Float T4;
        o0 U4;
        Float V4;
        i W4;
        e X4;
        d a1;
        p[] a2;
        long c = 0;
        o0 d;
        a f;
        c p0;
        Float p1;
        p p2;
        Float p3;
        f p4;
        Float q;
        o0 x;
        Float y;
        p z;
        List<String> z4;

        /* compiled from: SVG */
        public enum a {
            NonZero,
            EvenOdd
        }

        /* compiled from: SVG */
        public enum b {
            Normal,
            Italic,
            Oblique
        }

        /* compiled from: SVG */
        public enum c {
            Butt,
            Round,
            Square
        }

        /* compiled from: SVG */
        public enum d {
            Miter,
            Round,
            Bevel
        }

        /* compiled from: SVG */
        public enum e {
            auto,
            optimizeQuality,
            optimizeSpeed
        }

        /* compiled from: SVG */
        public enum f {
            Start,
            Middle,
            End
        }

        /* renamed from: com.caverock.androidsvg.g$e0$g  reason: collision with other inner class name */
        /* compiled from: SVG */
        public enum C0051g {
            None,
            Underline,
            Overline,
            LineThrough,
            Blink
        }

        /* compiled from: SVG */
        public enum h {
            LTR,
            RTL
        }

        /* compiled from: SVG */
        public enum i {
            None,
            NonScalingStroke
        }

        e0() {
        }

        static e0 a() {
            e0 def = new e0();
            def.c = -1;
            f fVar = f.c;
            def.d = fVar;
            a aVar = a.NonZero;
            def.f = aVar;
            Float valueOf = Float.valueOf(1.0f);
            def.q = valueOf;
            def.x = null;
            def.y = valueOf;
            def.z = new p(1.0f);
            def.p0 = c.Butt;
            def.a1 = d.Miter;
            def.p1 = Float.valueOf(4.0f);
            def.a2 = null;
            def.p2 = new p(0.0f);
            def.p3 = valueOf;
            def.p4 = fVar;
            def.z4 = null;
            def.A4 = new p(12.0f, d1.pt);
            def.B4 = Integer.valueOf(BaseResp.ERR_MSG_SEND_FAIL);
            def.C4 = b.Normal;
            def.D4 = C0051g.None;
            def.E4 = h.LTR;
            def.F4 = f.Start;
            def.G4 = true;
            def.H4 = null;
            def.I4 = null;
            def.J4 = null;
            def.K4 = null;
            Boolean bool = Boolean.TRUE;
            def.L4 = bool;
            def.M4 = bool;
            def.N4 = fVar;
            def.O4 = valueOf;
            def.P4 = null;
            def.Q4 = aVar;
            def.R4 = null;
            def.S4 = null;
            def.T4 = valueOf;
            def.U4 = null;
            def.V4 = valueOf;
            def.W4 = i.None;
            def.X4 = e.auto;
            return def;
        }

        /* access modifiers changed from: package-private */
        public void b(boolean isRootSVG) {
            Boolean bool = Boolean.TRUE;
            this.L4 = bool;
            if (!isRootSVG) {
                bool = Boolean.FALSE;
            }
            this.G4 = bool;
            this.H4 = null;
            this.P4 = null;
            this.p3 = Float.valueOf(1.0f);
            this.N4 = f.c;
            this.O4 = Float.valueOf(1.0f);
            this.R4 = null;
            this.S4 = null;
            this.T4 = Float.valueOf(1.0f);
            this.U4 = null;
            this.V4 = Float.valueOf(1.0f);
            this.W4 = i.None;
        }

        /* access modifiers changed from: protected */
        public Object clone() {
            e0 obj = (e0) super.clone();
            p[] pVarArr = this.a2;
            if (pVarArr != null) {
                obj.a2 = (p[]) pVarArr.clone();
            }
            return obj;
        }
    }

    /* compiled from: SVG */
    public static abstract class o0 implements Cloneable {
        o0() {
        }
    }

    /* compiled from: SVG */
    public static class f extends o0 {
        static final f c = new f(ViewCompat.MEASURED_STATE_MASK);
        static final f d = new f(0);
        int f;

        f(int val) {
            this.f = val;
        }

        public String toString() {
            return String.format("#%08x", new Object[]{Integer.valueOf(this.f)});
        }
    }

    /* renamed from: com.caverock.androidsvg.g$g  reason: collision with other inner class name */
    /* compiled from: SVG */
    public static class C0052g extends o0 {
        private static C0052g c = new C0052g();

        private C0052g() {
        }

        static C0052g a() {
            return c;
        }
    }

    /* compiled from: SVG */
    public static class u extends o0 {
        String c;
        o0 d;

        u(String href, o0 fallback) {
            this.c = href;
            this.d = fallback;
        }

        public String toString() {
            return this.c + " " + this.d;
        }
    }

    /* compiled from: SVG */
    public static class p implements Cloneable {
        float c;
        d1 d;

        p(float value, d1 unit) {
            this.c = value;
            this.d = unit;
        }

        p(float value) {
            this.c = value;
            this.d = d1.px;
        }

        /* access modifiers changed from: package-private */
        public float a() {
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public float e(h renderer) {
            switch (a.a[this.d.ordinal()]) {
                case 1:
                    return this.c;
                case 2:
                    return this.c * renderer.Y();
                case 3:
                    return this.c * renderer.Z();
                case 4:
                    return this.c * renderer.b0();
                case 5:
                    return (this.c * renderer.b0()) / 2.54f;
                case 6:
                    return (this.c * renderer.b0()) / 25.4f;
                case 7:
                    return (this.c * renderer.b0()) / 72.0f;
                case 8:
                    return (this.c * renderer.b0()) / 6.0f;
                case 9:
                    b viewPortUser = renderer.a0();
                    if (viewPortUser == null) {
                        return this.c;
                    }
                    return (this.c * viewPortUser.c) / 100.0f;
                default:
                    return this.c;
            }
        }

        /* access modifiers changed from: package-private */
        public float f(h renderer) {
            if (this.d != d1.percent) {
                return e(renderer);
            }
            b viewPortUser = renderer.a0();
            if (viewPortUser == null) {
                return this.c;
            }
            return (this.c * viewPortUser.d) / 100.0f;
        }

        /* access modifiers changed from: package-private */
        public float c(h renderer) {
            if (this.d != d1.percent) {
                return e(renderer);
            }
            b viewPortUser = renderer.a0();
            if (viewPortUser == null) {
                return this.c;
            }
            float w = viewPortUser.c;
            float h = viewPortUser.d;
            if (w == h) {
                return (this.c * w) / 100.0f;
            }
            return (this.c * ((float) (Math.sqrt((double) ((w * w) + (h * h))) / 1.414213562373095d))) / 100.0f;
        }

        /* access modifiers changed from: package-private */
        public float d(h renderer, float max) {
            if (this.d == d1.percent) {
                return (this.c * max) / 100.0f;
            }
            return e(renderer);
        }

        /* access modifiers changed from: package-private */
        public float b(float dpi) {
            switch (a.a[this.d.ordinal()]) {
                case 1:
                    return this.c;
                case 4:
                    return this.c * dpi;
                case 5:
                    return (this.c * dpi) / 2.54f;
                case 6:
                    return (this.c * dpi) / 25.4f;
                case 7:
                    return (this.c * dpi) / 72.0f;
                case 8:
                    return (this.c * dpi) / 6.0f;
                default:
                    return this.c;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean i() {
            return this.c == 0.0f;
        }

        /* access modifiers changed from: package-private */
        public boolean h() {
            return this.c < 0.0f;
        }

        public String toString() {
            return String.valueOf(this.c) + this.d;
        }
    }

    /* compiled from: SVG */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[d1.values().length];
            a = iArr;
            try {
                iArr[d1.px.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[d1.em.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[d1.ex.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[d1.in.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[d1.cm.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[d1.mm.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[d1.pt.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[d1.pc.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[d1.percent.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    /* compiled from: SVG */
    public static class c {
        p a;
        p b;
        p c;
        p d;

        c(p top, p right, p bottom, p left) {
            this.a = top;
            this.b = right;
            this.c = bottom;
            this.d = left;
        }
    }

    /* compiled from: SVG */
    public static class n0 {
        g a;
        j0 b;

        n0() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "";
        }
    }

    /* compiled from: SVG */
    public static abstract class l0 extends n0 {
        String c = null;
        Boolean d = null;
        e0 e = null;
        e0 f = null;
        List<String> g = null;

        l0() {
        }

        public String toString() {
            return m();
        }
    }

    /* compiled from: SVG */
    public static abstract class k0 extends l0 {
        b h = null;

        k0() {
        }
    }

    /* compiled from: SVG */
    public static abstract class i0 extends k0 implements g0 {
        Set<String> i = null;
        String j = null;
        Set<String> k = null;
        Set<String> l = null;
        Set<String> m = null;

        i0() {
        }

        public void e(Set<String> features) {
            this.i = features;
        }

        public Set<String> getRequiredFeatures() {
            return this.i;
        }

        public void i(String extensions) {
            this.j = extensions;
        }

        public String b() {
            return this.j;
        }

        public void f(Set<String> languages) {
            this.k = languages;
        }

        public Set<String> a() {
            return this.k;
        }

        public void h(Set<String> mimeTypes) {
            this.l = mimeTypes;
        }

        public Set<String> k() {
            return this.l;
        }

        public void c(Set<String> fontNames) {
            this.m = fontNames;
        }

        public Set<String> l() {
            return this.m;
        }
    }

    /* compiled from: SVG */
    public static abstract class h0 extends k0 implements j0, g0 {
        List<n0> i = new ArrayList();
        Set<String> j = null;
        String k = null;
        Set<String> l = null;
        Set<String> m = null;
        Set<String> n = null;

        h0() {
        }

        public List<n0> getChildren() {
            return this.i;
        }

        public void g(n0 elem) {
            this.i.add(elem);
        }

        public void e(Set<String> features) {
            this.j = features;
        }

        public Set<String> getRequiredFeatures() {
            return this.j;
        }

        public void i(String extensions) {
            this.k = extensions;
        }

        public String b() {
            return this.k;
        }

        public void f(Set<String> languages) {
            this.l = languages;
        }

        public Set<String> a() {
            return null;
        }

        public void h(Set<String> mimeTypes) {
            this.m = mimeTypes;
        }

        public Set<String> k() {
            return this.m;
        }

        public void c(Set<String> fontNames) {
            this.n = fontNames;
        }

        public Set<String> l() {
            return this.n;
        }
    }

    /* compiled from: SVG */
    public static abstract class p0 extends h0 {
        e o = null;

        p0() {
        }
    }

    /* compiled from: SVG */
    public static abstract class r0 extends p0 {
        b p;

        r0() {
        }
    }

    /* compiled from: SVG */
    public static class f0 extends r0 {
        p q;
        p r;
        p s;
        p t;
        public String u;

        f0() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "svg";
        }
    }

    /* compiled from: SVG */
    public static class m extends h0 implements n {
        Matrix o;

        m() {
        }

        public void j(Matrix transform) {
            this.o = transform;
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "group";
        }
    }

    /* compiled from: SVG */
    public static class h extends m implements t {
        h() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "defs";
        }
    }

    /* compiled from: SVG */
    public static abstract class l extends i0 implements n {
        Matrix n;

        l() {
        }

        public void j(Matrix transform) {
            this.n = transform;
        }
    }

    /* compiled from: SVG */
    public static class e1 extends m {
        String p;
        p q;
        p r;
        p s;
        p t;

        e1() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "use";
        }
    }

    /* compiled from: SVG */
    public static class v extends l {
        w o;
        Float p;

        v() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "path";
        }
    }

    /* compiled from: SVG */
    public static class b0 extends l {
        p o;
        p p;
        p q;
        p r;
        p s;
        p t;

        b0() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "rect";
        }
    }

    /* compiled from: SVG */
    public static class d extends l {
        p o;
        p p;
        p q;

        d() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "circle";
        }
    }

    /* compiled from: SVG */
    public static class i extends l {
        p o;
        p p;
        p q;
        p r;

        i() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "ellipse";
        }
    }

    /* compiled from: SVG */
    public static class q extends l {
        p o;
        p p;
        p q;
        p r;

        q() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "line";
        }
    }

    /* compiled from: SVG */
    public static class z extends l {
        float[] o;

        z() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "polyline";
        }
    }

    /* compiled from: SVG */
    public static class a0 extends z {
        a0() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "polygon";
        }
    }

    /* compiled from: SVG */
    public static abstract class y0 extends h0 {
        y0() {
        }

        public void g(n0 elem) {
            if (elem instanceof x0) {
                this.i.add(elem);
                return;
            }
            throw new SVGParseException("Text content elements cannot contain " + elem + " elements.");
        }
    }

    /* compiled from: SVG */
    public static abstract class a1 extends y0 {
        List<p> o;
        List<p> p;
        List<p> q;
        List<p> r;

        a1() {
        }
    }

    /* compiled from: SVG */
    public static class w0 extends a1 implements b1, n {
        Matrix s;

        w0() {
        }

        public void j(Matrix transform) {
            this.s = transform;
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "text";
        }
    }

    /* compiled from: SVG */
    public static class v0 extends a1 implements x0 {
        private b1 s;

        v0() {
        }

        public void n(b1 obj) {
            this.s = obj;
        }

        public b1 d() {
            return this.s;
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "tspan";
        }
    }

    /* compiled from: SVG */
    public static class c1 extends n0 implements x0 {
        String c;
        private b1 d;

        c1(String text) {
            this.c = text;
        }

        public String toString() {
            return "TextChild: '" + this.c + "'";
        }

        public b1 d() {
            return this.d;
        }
    }

    /* compiled from: SVG */
    public static class u0 extends y0 implements x0 {
        String o;
        private b1 p;

        u0() {
        }

        public void n(b1 obj) {
            this.p = obj;
        }

        public b1 d() {
            return this.p;
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "tref";
        }
    }

    /* compiled from: SVG */
    public static class z0 extends y0 implements x0 {
        String o;
        p p;
        private b1 q;

        z0() {
        }

        public void n(b1 obj) {
            this.q = obj;
        }

        public b1 d() {
            return this.q;
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "textPath";
        }
    }

    /* compiled from: SVG */
    public static class s0 extends m {
        s0() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "switch";
        }
    }

    /* compiled from: SVG */
    public static class t0 extends r0 implements t {
        t0() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "symbol";
        }
    }

    /* compiled from: SVG */
    public static class r extends r0 implements t {
        boolean q;
        p r;
        p s;
        p t;
        p u;
        Float v;

        r() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "marker";
        }
    }

    /* compiled from: SVG */
    public static abstract class j extends l0 implements j0 {
        List<n0> h = new ArrayList();
        Boolean i;
        Matrix j;
        k k;
        String l;

        j() {
        }

        public List<n0> getChildren() {
            return this.h;
        }

        public void g(n0 elem) {
            if (elem instanceof d0) {
                this.h.add(elem);
                return;
            }
            throw new SVGParseException("Gradient elements cannot contain " + elem + " elements.");
        }
    }

    /* compiled from: SVG */
    public static class d0 extends l0 implements j0 {
        Float h;

        d0() {
        }

        public List<n0> getChildren() {
            return Collections.emptyList();
        }

        public void g(n0 elem) {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "stop";
        }
    }

    /* compiled from: SVG */
    public static class m0 extends j {
        p m;
        p n;
        p o;
        p p;

        m0() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "linearGradient";
        }
    }

    /* compiled from: SVG */
    public static class q0 extends j {
        p m;
        p n;
        p o;
        p p;
        p q;

        q0() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "radialGradient";
        }
    }

    /* compiled from: SVG */
    public static class e extends m implements t {
        Boolean p;

        e() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "clipPath";
        }
    }

    /* compiled from: SVG */
    public static class y extends r0 implements t {
        Boolean q;
        Boolean r;
        Matrix s;
        p t;
        p u;
        p v;
        p w;
        String x;

        y() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "pattern";
        }
    }

    /* compiled from: SVG */
    public static class o extends p0 implements n {
        String p;
        p q;
        p r;
        p s;
        p t;
        Matrix u;

        o() {
        }

        public void j(Matrix transform) {
            this.u = transform;
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return PictureMimeType.MIME_TYPE_PREFIX_IMAGE;
        }
    }

    /* compiled from: SVG */
    public static class f1 extends r0 implements t {
        f1() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "view";
        }
    }

    /* compiled from: SVG */
    public static class s extends h0 implements t {
        Boolean o;
        Boolean p;
        p q;
        p r;
        p s;
        p t;

        s() {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "mask";
        }
    }

    /* compiled from: SVG */
    public static class c0 extends l0 implements j0 {
        c0() {
        }

        public List<n0> getChildren() {
            return Collections.emptyList();
        }

        public void g(n0 elem) {
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return "solidColor";
        }
    }

    /* access modifiers changed from: package-private */
    public void B(String title) {
        this.d = title;
    }

    /* access modifiers changed from: package-private */
    public void w(String desc) {
        this.e = desc;
    }

    static i k() {
        return a;
    }

    /* compiled from: SVG */
    public static class w implements x {
        private byte[] a = new byte[8];
        private int b = 0;
        private float[] c = new float[16];
        private int d = 0;

        w() {
        }

        /* access modifiers changed from: package-private */
        public boolean i() {
            return this.b == 0;
        }

        private void f(byte value) {
            int i = this.b;
            byte[] bArr = this.a;
            if (i == bArr.length) {
                byte[] newCommands = new byte[(bArr.length * 2)];
                System.arraycopy(bArr, 0, newCommands, 0, bArr.length);
                this.a = newCommands;
            }
            byte[] newCommands2 = this.a;
            int i2 = this.b;
            this.b = i2 + 1;
            newCommands2[i2] = value;
        }

        private void g(int num) {
            float[] fArr = this.c;
            if (fArr.length < this.d + num) {
                float[] newCoords = new float[(fArr.length * 2)];
                System.arraycopy(fArr, 0, newCoords, 0, fArr.length);
                this.c = newCoords;
            }
        }

        public void b(float x, float y) {
            f((byte) 0);
            g(2);
            float[] fArr = this.c;
            int i = this.d;
            int i2 = i + 1;
            this.d = i2;
            fArr[i] = x;
            this.d = i2 + 1;
            fArr[i2] = y;
        }

        public void e(float x, float y) {
            f((byte) 1);
            g(2);
            float[] fArr = this.c;
            int i = this.d;
            int i2 = i + 1;
            this.d = i2;
            fArr[i] = x;
            this.d = i2 + 1;
            fArr[i2] = y;
        }

        public void c(float x1, float y1, float x2, float y2, float x3, float y3) {
            f((byte) 2);
            g(6);
            float[] fArr = this.c;
            int i = this.d;
            int i2 = i + 1;
            this.d = i2;
            fArr[i] = x1;
            int i3 = i2 + 1;
            this.d = i3;
            fArr[i2] = y1;
            int i4 = i3 + 1;
            this.d = i4;
            fArr[i3] = x2;
            int i5 = i4 + 1;
            this.d = i5;
            fArr[i4] = y2;
            int i6 = i5 + 1;
            this.d = i6;
            fArr[i5] = x3;
            this.d = i6 + 1;
            fArr[i6] = y3;
        }

        public void a(float x1, float y1, float x2, float y2) {
            f((byte) 3);
            g(4);
            float[] fArr = this.c;
            int i = this.d;
            int i2 = i + 1;
            this.d = i2;
            fArr[i] = x1;
            int i3 = i2 + 1;
            this.d = i3;
            fArr[i2] = y1;
            int i4 = i3 + 1;
            this.d = i4;
            fArr[i3] = x2;
            this.d = i4 + 1;
            fArr[i4] = y2;
        }

        public void d(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y) {
            f((byte) ((largeArcFlag ? 2 : 0) | 4 | sweepFlag));
            g(5);
            float[] fArr = this.c;
            int i = this.d;
            int i2 = i + 1;
            this.d = i2;
            fArr[i] = rx;
            int i3 = i2 + 1;
            this.d = i3;
            fArr[i2] = ry;
            int i4 = i3 + 1;
            this.d = i4;
            fArr[i3] = xAxisRotation;
            int i5 = i4 + 1;
            this.d = i5;
            fArr[i4] = x;
            this.d = i5 + 1;
            fArr[i5] = y;
        }

        public void close() {
            f((byte) 8);
        }

        /* access modifiers changed from: package-private */
        public void h(x handler) {
            int coordsPos = 0;
            for (int commandPos = 0; commandPos < this.b; commandPos++) {
                byte command = this.a[commandPos];
                switch (command) {
                    case 0:
                        float[] fArr = this.c;
                        int coordsPos2 = coordsPos + 1;
                        handler.b(fArr[coordsPos], fArr[coordsPos2]);
                        coordsPos = coordsPos2 + 1;
                        break;
                    case 1:
                        float[] fArr2 = this.c;
                        int coordsPos3 = coordsPos + 1;
                        handler.e(fArr2[coordsPos], fArr2[coordsPos3]);
                        coordsPos = coordsPos3 + 1;
                        break;
                    case 2:
                        float[] fArr3 = this.c;
                        int coordsPos4 = coordsPos + 1;
                        float f = fArr3[coordsPos];
                        int coordsPos5 = coordsPos4 + 1;
                        float f2 = fArr3[coordsPos4];
                        int coordsPos6 = coordsPos5 + 1;
                        float f3 = fArr3[coordsPos5];
                        int coordsPos7 = coordsPos6 + 1;
                        float f4 = fArr3[coordsPos6];
                        int coordsPos8 = coordsPos7 + 1;
                        float f5 = fArr3[coordsPos7];
                        coordsPos = coordsPos8 + 1;
                        handler.c(f, f2, f3, f4, f5, fArr3[coordsPos8]);
                        break;
                    case 3:
                        float[] fArr4 = this.c;
                        int coordsPos9 = coordsPos + 1;
                        int coordsPos10 = coordsPos9 + 1;
                        int coordsPos11 = coordsPos10 + 1;
                        handler.a(fArr4[coordsPos], fArr4[coordsPos9], fArr4[coordsPos10], fArr4[coordsPos11]);
                        coordsPos = coordsPos11 + 1;
                        break;
                    case 8:
                        handler.close();
                        break;
                    default:
                        boolean largeArcFlag = (command & 2) != 0;
                        boolean sweepFlag = (command & 1) != 0;
                        float[] fArr5 = this.c;
                        int coordsPos12 = coordsPos + 1;
                        float f6 = fArr5[coordsPos];
                        int coordsPos13 = coordsPos12 + 1;
                        float f7 = fArr5[coordsPos12];
                        int coordsPos14 = coordsPos13 + 1;
                        float f8 = fArr5[coordsPos13];
                        int coordsPos15 = coordsPos14 + 1;
                        handler.d(f6, f7, f8, largeArcFlag, sweepFlag, fArr5[coordsPos14], fArr5[coordsPos15]);
                        coordsPos = coordsPos15 + 1;
                        break;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public l0 j(String id) {
        if (id == null || id.length() == 0) {
            return null;
        }
        if (id.equals(this.c.c)) {
            return this.c;
        }
        if (this.h.containsKey(id)) {
            return this.h.get(id);
        }
        l0 result = i(this.c, id);
        this.h.put(id, result);
        return result;
    }

    private l0 i(j0 obj, String id) {
        l0 found;
        l0 elem = (l0) obj;
        if (id.equals(elem.c)) {
            return elem;
        }
        for (n0 child : obj.getChildren()) {
            if (child instanceof l0) {
                l0 childElem = (l0) child;
                if (id.equals(childElem.c)) {
                    return childElem;
                }
                if ((child instanceof j0) && (found = i((j0) child, id)) != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
