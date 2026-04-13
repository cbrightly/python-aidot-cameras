package com.caverock.androidsvg;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Base64;
import android.util.Log;
import com.alibaba.fastjson.asm.Opcodes;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGAndroidRenderer;
import com.caverock.androidsvg.a;
import com.caverock.androidsvg.e;
import com.caverock.androidsvg.g;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: SVGAndroidRenderer */
public class h {
    private static HashSet<String> a = null;
    /* access modifiers changed from: private */
    public Canvas b;
    private float c;
    private g d;
    /* access modifiers changed from: private */
    public C0053h e;
    private Stack<C0053h> f;
    private Stack<g.j0> g;
    private Stack<Matrix> h;
    private a.q i = null;

    /* renamed from: com.caverock.androidsvg.h$h  reason: collision with other inner class name */
    /* compiled from: SVGAndroidRenderer */
    public class C0053h {
        g.e0 a;
        boolean b;
        boolean c;
        Paint d;
        Paint e;
        g.b f;
        g.b g;
        boolean h;

        C0053h() {
            Paint paint = new Paint();
            this.d = paint;
            paint.setFlags(Opcodes.INSTANCEOF);
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 14) {
                this.d.setHinting(0);
            }
            this.d.setStyle(Paint.Style.FILL);
            this.d.setTypeface(Typeface.DEFAULT);
            Paint paint2 = new Paint();
            this.e = paint2;
            paint2.setFlags(Opcodes.INSTANCEOF);
            if (i2 >= 14) {
                this.e.setHinting(0);
            }
            this.e.setStyle(Paint.Style.STROKE);
            this.e.setTypeface(Typeface.DEFAULT);
            this.a = g.e0.a();
        }

        C0053h(C0053h copy) {
            this.b = copy.b;
            this.c = copy.c;
            this.d = new Paint(copy.d);
            this.e = new Paint(copy.e);
            g.b bVar = copy.f;
            if (bVar != null) {
                this.f = new g.b(bVar);
            }
            g.b bVar2 = copy.g;
            if (bVar2 != null) {
                this.g = new g.b(bVar2);
            }
            this.h = copy.h;
            try {
                this.a = (g.e0) copy.a.clone();
            } catch (CloneNotSupportedException e2) {
                Log.e("SVGAndroidRenderer", "Unexpected clone error", e2);
                this.a = g.e0.a();
            }
        }
    }

    private void V0() {
        this.e = new C0053h();
        this.f = new Stack<>();
        d1(this.e, g.e0.a());
        C0053h hVar = this.e;
        hVar.f = null;
        hVar.h = false;
        this.f.push(new C0053h(hVar));
        this.h = new Stack<>();
        this.g = new Stack<>();
    }

    h(Canvas canvas, float defaultDPI) {
        this.b = canvas;
        this.c = defaultDPI;
    }

    /* access modifiers changed from: package-private */
    public float b0() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public float Y() {
        return this.e.d.getTextSize();
    }

    /* access modifiers changed from: package-private */
    public float Z() {
        return this.e.d.getTextSize() / 2.0f;
    }

    /* access modifiers changed from: package-private */
    public g.b a0() {
        C0053h hVar = this.e;
        g.b bVar = hVar.g;
        if (bVar != null) {
            return bVar;
        }
        return hVar.f;
    }

    /* access modifiers changed from: package-private */
    public void O0(g document, f renderOptions) {
        e preserveAspectRatio;
        g.b viewBox;
        if (renderOptions != null) {
            this.d = document;
            g.f0 rootObj = document.p();
            if (rootObj == null) {
                h1("Nothing to render. Document is empty.", new Object[0]);
                return;
            }
            if (renderOptions.e()) {
                g.n0 obj = this.d.j(renderOptions.e);
                if (obj == null || !(obj instanceof g.f1)) {
                    Log.w("SVGAndroidRenderer", String.format("View element with id \"%s\" not found.", new Object[]{renderOptions.e}));
                    return;
                }
                g.f1 view = (g.f1) obj;
                if (view.p == null) {
                    Log.w("SVGAndroidRenderer", String.format("View element with id \"%s\" is missing a viewBox attribute.", new Object[]{renderOptions.e}));
                    return;
                } else {
                    viewBox = view.p;
                    preserveAspectRatio = view.o;
                }
            } else {
                viewBox = renderOptions.f() ? renderOptions.d : rootObj.p;
                preserveAspectRatio = renderOptions.c() ? renderOptions.b : rootObj.o;
            }
            if (renderOptions.b()) {
                document.a(renderOptions.a);
            }
            if (renderOptions.d()) {
                a.q qVar = new a.q();
                this.i = qVar;
                qVar.a = document.j(renderOptions.c);
            }
            V0();
            A(rootObj);
            a1();
            g.b viewPort = new g.b(renderOptions.f);
            g.p pVar = rootObj.s;
            if (pVar != null) {
                viewPort.c = pVar.d(this, viewPort.c);
            }
            g.p pVar2 = rootObj.t;
            if (pVar2 != null) {
                viewPort.d = pVar2.d(this, viewPort.d);
            }
            H0(rootObj, viewPort, viewBox, preserveAspectRatio);
            Z0();
            if (renderOptions.b()) {
                document.b();
                return;
            }
            return;
        }
        throw new NullPointerException("renderOptions shouldn't be null");
    }

    private void I0(g.n0 obj) {
        if (!(obj instanceof g.t)) {
            a1();
            A(obj);
            if (obj instanceof g.f0) {
                F0((g.f0) obj);
            } else if (obj instanceof g.e1) {
                M0((g.e1) obj);
            } else if (obj instanceof g.s0) {
                J0((g.s0) obj);
            } else if (obj instanceof g.m) {
                y0((g.m) obj);
            } else if (obj instanceof g.o) {
                z0((g.o) obj);
            } else if (obj instanceof g.v) {
                B0((g.v) obj);
            } else if (obj instanceof g.b0) {
                E0((g.b0) obj);
            } else if (obj instanceof g.d) {
                w0((g.d) obj);
            } else if (obj instanceof g.i) {
                x0((g.i) obj);
            } else if (obj instanceof g.q) {
                A0((g.q) obj);
            } else if (obj instanceof g.a0) {
                D0((g.a0) obj);
            } else if (obj instanceof g.z) {
                C0((g.z) obj);
            } else if (obj instanceof g.w0) {
                L0((g.w0) obj);
            }
            Z0();
        }
    }

    private void N0(g.j0 obj, boolean isContainer) {
        if (isContainer) {
            q0(obj);
        }
        for (g.n0 child : obj.getChildren()) {
            I0(child);
        }
        if (isContainer) {
            p0();
        }
    }

    private void a1() {
        this.b.save();
        this.f.push(this.e);
        this.e = new C0053h(this.e);
    }

    private void Z0() {
        this.b.restore();
        this.e = this.f.pop();
    }

    private void q0(g.j0 obj) {
        this.g.push(obj);
        this.h.push(this.b.getMatrix());
    }

    private void p0() {
        this.g.pop();
        this.h.pop();
    }

    private void e1(C0053h state, g.l0 obj) {
        state.a.b(obj.b == null);
        g.e0 e0Var = obj.e;
        if (e0Var != null) {
            d1(state, e0Var);
        }
        if (this.d.q()) {
            for (a.p rule : this.d.d()) {
                if (a.l(this.i, rule.a, obj)) {
                    d1(state, rule.b);
                }
            }
        }
        g.e0 e0Var2 = obj.f;
        if (e0Var2 != null) {
            d1(state, e0Var2);
        }
    }

    private void A(g.n0 obj) {
        Boolean bool;
        if ((obj instanceof g.l0) && (bool = ((g.l0) obj).d) != null) {
            this.e.h = bool.booleanValue();
        }
    }

    private void J(g.k0 obj, Path path) {
        g.o0 o0Var = this.e.a.d;
        if (o0Var instanceof g.u) {
            g.n0 ref = this.d.v(((g.u) o0Var).c);
            if (ref instanceof g.y) {
                T(obj, path, (g.y) ref);
                return;
            }
        }
        this.b.drawPath(path, this.e.d);
    }

    private void K(Path path) {
        C0053h hVar = this.e;
        if (hVar.a.W4 == g.e0.i.NonScalingStroke) {
            Matrix currentMatrix = this.b.getMatrix();
            Path transformedPath = new Path();
            path.transform(currentMatrix, transformedPath);
            this.b.setMatrix(new Matrix());
            Shader shader = this.e.e.getShader();
            Matrix currentShaderMatrix = new Matrix();
            if (shader != null) {
                shader.getLocalMatrix(currentShaderMatrix);
                Matrix newShaderMatrix = new Matrix(currentShaderMatrix);
                newShaderMatrix.postConcat(currentMatrix);
                shader.setLocalMatrix(newShaderMatrix);
            }
            this.b.drawPath(transformedPath, this.e.e);
            this.b.setMatrix(currentMatrix);
            if (shader != null) {
                shader.setLocalMatrix(currentShaderMatrix);
                return;
            }
            return;
        }
        this.b.drawPath(path, hVar.e);
    }

    /* access modifiers changed from: private */
    public static void h1(String format, Object... args) {
        Log.w("SVGAndroidRenderer", String.format(format, args));
    }

    /* access modifiers changed from: private */
    public static void N(String format, Object... args) {
        Log.e("SVGAndroidRenderer", String.format(format, args));
    }

    /* access modifiers changed from: private */
    public static void G(String format, Object... args) {
    }

    private void F0(g.f0 obj) {
        H0(obj, n0(obj.q, obj.r, obj.s, obj.t), obj.p, obj.o);
    }

    private void G0(g.f0 obj, g.b viewPort) {
        H0(obj, viewPort, obj.p, obj.o);
    }

    private void H0(g.f0 obj, g.b viewPort, g.b viewBox, e positioning) {
        G("Svg render", new Object[0]);
        if (viewPort.c != 0.0f && viewPort.d != 0.0f) {
            if (positioning == null) {
                e eVar = obj.o;
                if (eVar == null) {
                    eVar = e.c;
                }
                positioning = eVar;
            }
            e1(this.e, obj);
            if (I()) {
                C0053h hVar = this.e;
                hVar.f = viewPort;
                if (!hVar.a.G4.booleanValue()) {
                    g.b bVar = this.e.f;
                    W0(bVar.a, bVar.b, bVar.c, bVar.d);
                }
                v(obj, this.e.f);
                if (viewBox != null) {
                    this.b.concat(t(this.e.f, viewBox, positioning));
                    this.e.g = obj.p;
                } else {
                    Canvas canvas = this.b;
                    g.b bVar2 = this.e.f;
                    canvas.translate(bVar2.a, bVar2.b);
                }
                boolean compositing = u0();
                f1();
                N0(obj, true);
                if (compositing) {
                    r0(obj);
                }
                c1(obj);
            }
        }
    }

    private g.b n0(g.p x, g.p y, g.p width, g.p height) {
        float _y = 0.0f;
        float _x = x != null ? x.e(this) : 0.0f;
        if (y != null) {
            _y = y.f(this);
        }
        g.b viewPortUser = a0();
        return new g.b(_x, _y, width != null ? width.e(this) : viewPortUser.c, height != null ? height.f(this) : viewPortUser.d);
    }

    private void y0(g.m obj) {
        G("Group render", new Object[0]);
        e1(this.e, obj);
        if (I()) {
            Matrix matrix = obj.o;
            if (matrix != null) {
                this.b.concat(matrix);
            }
            u(obj);
            boolean compositing = u0();
            N0(obj, true);
            if (compositing) {
                r0(obj);
            }
            c1(obj);
        }
    }

    private void c1(g.k0 obj) {
        if (obj.b != null && obj.h != null) {
            Matrix m = new Matrix();
            if (this.h.peek().invert(m)) {
                g.b bVar = obj.h;
                g.b bVar2 = obj.h;
                g.b bVar3 = obj.h;
                float[] pts = {bVar.a, bVar.b, bVar.b(), bVar2.b, bVar2.b(), obj.h.c(), bVar3.a, bVar3.c()};
                m.preConcat(this.b.getMatrix());
                m.mapPoints(pts);
                RectF rect = new RectF(pts[0], pts[1], pts[0], pts[1]);
                for (int i2 = 2; i2 <= 6; i2 += 2) {
                    if (pts[i2] < rect.left) {
                        rect.left = pts[i2];
                    }
                    if (pts[i2] > rect.right) {
                        rect.right = pts[i2];
                    }
                    if (pts[i2 + 1] < rect.top) {
                        rect.top = pts[i2 + 1];
                    }
                    if (pts[i2 + 1] > rect.bottom) {
                        rect.bottom = pts[i2 + 1];
                    }
                }
                g.k0 parent = (g.k0) this.g.peek();
                g.b bVar4 = parent.h;
                if (bVar4 == null) {
                    parent.h = g.b.a(rect.left, rect.top, rect.right, rect.bottom);
                } else {
                    bVar4.e(g.b.a(rect.left, rect.top, rect.right, rect.bottom));
                }
            }
        }
    }

    private boolean u0() {
        g.n0 ref;
        if (!U0()) {
            return false;
        }
        this.b.saveLayerAlpha((RectF) null, C(this.e.a.p3.floatValue()), 31);
        this.f.push(this.e);
        C0053h hVar = new C0053h(this.e);
        this.e = hVar;
        String str = hVar.a.R4;
        if (str == null || ((ref = this.d.v(str)) != null && (ref instanceof g.s))) {
            return true;
        }
        N("Mask reference '%s' not found", this.e.a.R4);
        this.e.a.R4 = null;
        return true;
    }

    private void r0(g.k0 obj) {
        s0(obj, obj.h);
    }

    private void s0(g.k0 obj, g.b originalObjBBox) {
        if (this.e.a.R4 != null) {
            Paint maskPaintCombined = new Paint();
            maskPaintCombined.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            this.b.saveLayer((RectF) null, maskPaintCombined, 31);
            Paint maskPaint1 = new Paint();
            maskPaint1.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2127f, 0.7151f, 0.0722f, 0.0f, 0.0f})));
            this.b.saveLayer((RectF) null, maskPaint1, 31);
            g.n0 ref = this.d.v(this.e.a.R4);
            R0((g.s) ref, obj, originalObjBBox);
            this.b.restore();
            Paint maskPaint2 = new Paint();
            maskPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            this.b.saveLayer((RectF) null, maskPaint2, 31);
            R0((g.s) ref, obj, originalObjBBox);
            this.b.restore();
            this.b.restore();
        }
        Z0();
    }

    private boolean U0() {
        return this.e.a.p3.floatValue() < 1.0f || this.e.a.R4 != null;
    }

    private void J0(g.s0 obj) {
        G("Switch render", new Object[0]);
        e1(this.e, obj);
        if (I()) {
            Matrix matrix = obj.o;
            if (matrix != null) {
                this.b.concat(matrix);
            }
            u(obj);
            boolean compositing = u0();
            S0(obj);
            if (compositing) {
                r0(obj);
            }
            c1(obj);
        }
    }

    private void S0(g.s0 obj) {
        Set<String> syslang;
        String deviceLanguage = Locale.getDefault().getLanguage();
        i fileResolver = g.k();
        for (g.n0 child : obj.getChildren()) {
            if (child instanceof g.g0) {
                g.g0 condObj = (g.g0) child;
                if (condObj.b() == null && ((syslang = condObj.a()) == null || (!syslang.isEmpty() && syslang.contains(deviceLanguage)))) {
                    Set<String> reqfeat = condObj.getRequiredFeatures();
                    if (reqfeat != null) {
                        if (a == null) {
                            d0();
                        }
                        if (reqfeat.isEmpty()) {
                            continue;
                        } else if (!a.containsAll(reqfeat)) {
                            continue;
                        }
                    }
                    Set<String> reqfmts = condObj.k();
                    if (reqfmts != null) {
                        if (!reqfmts.isEmpty() && fileResolver != null) {
                            Iterator<String> it = reqfmts.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (!fileResolver.a(it.next())) {
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    Set<String> reqfonts = condObj.l();
                    if (reqfonts != null) {
                        if (!reqfonts.isEmpty() && fileResolver != null) {
                            for (String fontName : reqfonts) {
                                if (fileResolver.c(fontName, this.e.a.B4.intValue(), String.valueOf(this.e.a.C4)) == null) {
                                }
                            }
                        }
                    }
                    I0(child);
                    return;
                }
            }
        }
    }

    private static synchronized void d0() {
        synchronized (h.class) {
            HashSet<String> hashSet = new HashSet<>();
            a = hashSet;
            hashSet.add("Structure");
            a.add("BasicStructure");
            a.add("ConditionalProcessing");
            a.add("Image");
            a.add("Style");
            a.add("ViewportAttribute");
            a.add("Shape");
            a.add("BasicText");
            a.add("PaintAttribute");
            a.add("BasicPaintAttribute");
            a.add("OpacityAttribute");
            a.add("BasicGraphicsAttribute");
            a.add("Marker");
            a.add("Gradient");
            a.add("Pattern");
            a.add("Clip");
            a.add("BasicClip");
            a.add("Mask");
            a.add("View");
        }
    }

    private void M0(g.e1 obj) {
        G("Use render", new Object[0]);
        g.p pVar = obj.s;
        if (pVar == null || !pVar.i()) {
            g.p pVar2 = obj.t;
            if (pVar2 == null || !pVar2.i()) {
                e1(this.e, obj);
                if (I()) {
                    g.n0 ref = obj.a.v(obj.p);
                    if (ref == null) {
                        N("Use reference '%s' not found", obj.p);
                        return;
                    }
                    Matrix matrix = obj.o;
                    if (matrix != null) {
                        this.b.concat(matrix);
                    }
                    g.p pVar3 = obj.q;
                    float _y = 0.0f;
                    float _x = pVar3 != null ? pVar3.e(this) : 0.0f;
                    g.p pVar4 = obj.r;
                    if (pVar4 != null) {
                        _y = pVar4.f(this);
                    }
                    this.b.translate(_x, _y);
                    u(obj);
                    boolean compositing = u0();
                    q0(obj);
                    if (ref instanceof g.f0) {
                        g.b viewPort = n0((g.p) null, (g.p) null, obj.s, obj.t);
                        a1();
                        G0((g.f0) ref, viewPort);
                        Z0();
                    } else if (ref instanceof g.t0) {
                        g.p _w = obj.s;
                        if (_w == null) {
                            _w = new g.p(100.0f, g.d1.percent);
                        }
                        g.p _h = obj.t;
                        if (_h == null) {
                            _h = new g.p(100.0f, g.d1.percent);
                        }
                        g.b viewPort2 = n0((g.p) null, (g.p) null, _w, _h);
                        a1();
                        K0((g.t0) ref, viewPort2);
                        Z0();
                    } else {
                        I0(ref);
                    }
                    p0();
                    if (compositing) {
                        r0(obj);
                    }
                    c1(obj);
                }
            }
        }
    }

    private void B0(g.v obj) {
        G("Path render", new Object[0]);
        if (obj.o != null) {
            e1(this.e, obj);
            if (I() && g1()) {
                C0053h hVar = this.e;
                if (hVar.c || hVar.b) {
                    Matrix matrix = obj.n;
                    if (matrix != null) {
                        this.b.concat(matrix);
                    }
                    Path path = new d(obj.o).f();
                    if (obj.h == null) {
                        obj.h = r(path);
                    }
                    c1(obj);
                    x(obj);
                    u(obj);
                    boolean compositing = u0();
                    if (this.e.b) {
                        path.setFillType(c0());
                        J(obj, path);
                    }
                    if (this.e.c) {
                        K(path);
                    }
                    Q0(obj);
                    if (compositing) {
                        r0(obj);
                    }
                }
            }
        }
    }

    private g.b r(Path path) {
        RectF pathBounds = new RectF();
        path.computeBounds(pathBounds, true);
        return new g.b(pathBounds.left, pathBounds.top, pathBounds.width(), pathBounds.height());
    }

    private void E0(g.b0 obj) {
        G("Rect render", new Object[0]);
        g.p pVar = obj.q;
        if (pVar != null && obj.r != null && !pVar.i() && !obj.r.i()) {
            e1(this.e, obj);
            if (I() && g1()) {
                Matrix matrix = obj.n;
                if (matrix != null) {
                    this.b.concat(matrix);
                }
                Path path = k0(obj);
                c1(obj);
                x(obj);
                u(obj);
                boolean compositing = u0();
                if (this.e.b) {
                    J(obj, path);
                }
                if (this.e.c) {
                    K(path);
                }
                if (compositing) {
                    r0(obj);
                }
            }
        }
    }

    private void w0(g.d obj) {
        G("Circle render", new Object[0]);
        g.p pVar = obj.q;
        if (pVar != null && !pVar.i()) {
            e1(this.e, obj);
            if (I() && g1()) {
                Matrix matrix = obj.n;
                if (matrix != null) {
                    this.b.concat(matrix);
                }
                Path path = g0(obj);
                c1(obj);
                x(obj);
                u(obj);
                boolean compositing = u0();
                if (this.e.b) {
                    J(obj, path);
                }
                if (this.e.c) {
                    K(path);
                }
                if (compositing) {
                    r0(obj);
                }
            }
        }
    }

    private void x0(g.i obj) {
        G("Ellipse render", new Object[0]);
        g.p pVar = obj.q;
        if (pVar != null && obj.r != null && !pVar.i() && !obj.r.i()) {
            e1(this.e, obj);
            if (I() && g1()) {
                Matrix matrix = obj.n;
                if (matrix != null) {
                    this.b.concat(matrix);
                }
                Path path = h0(obj);
                c1(obj);
                x(obj);
                u(obj);
                boolean compositing = u0();
                if (this.e.b) {
                    J(obj, path);
                }
                if (this.e.c) {
                    K(path);
                }
                if (compositing) {
                    r0(obj);
                }
            }
        }
    }

    private void A0(g.q obj) {
        G("Line render", new Object[0]);
        e1(this.e, obj);
        if (I() && g1() && this.e.c) {
            Matrix matrix = obj.n;
            if (matrix != null) {
                this.b.concat(matrix);
            }
            Path path = i0(obj);
            c1(obj);
            x(obj);
            u(obj);
            boolean compositing = u0();
            K(path);
            Q0(obj);
            if (compositing) {
                r0(obj);
            }
        }
    }

    private List<c> p(g.q obj) {
        g.p pVar = obj.o;
        float f2 = 0.0f;
        float _x1 = pVar != null ? pVar.e(this) : 0.0f;
        g.p pVar2 = obj.p;
        float _y1 = pVar2 != null ? pVar2.f(this) : 0.0f;
        g.p pVar3 = obj.q;
        float _x2 = pVar3 != null ? pVar3.e(this) : 0.0f;
        g.p pVar4 = obj.r;
        if (pVar4 != null) {
            f2 = pVar4.f(this);
        }
        float _y2 = f2;
        List<SVGAndroidRenderer.MarkerVector> markers = new ArrayList<>(2);
        markers.add(new c(_x1, _y1, _x2 - _x1, _y2 - _y1));
        markers.add(new c(_x2, _y2, _x2 - _x1, _y2 - _y1));
        return markers;
    }

    private void C0(g.z obj) {
        G("PolyLine render", new Object[0]);
        e1(this.e, obj);
        if (I() && g1()) {
            C0053h hVar = this.e;
            if (hVar.c || hVar.b) {
                Matrix matrix = obj.n;
                if (matrix != null) {
                    this.b.concat(matrix);
                }
                if (obj.o.length >= 2) {
                    Path path = j0(obj);
                    c1(obj);
                    path.setFillType(c0());
                    x(obj);
                    u(obj);
                    boolean compositing = u0();
                    if (this.e.b) {
                        J(obj, path);
                    }
                    if (this.e.c) {
                        K(path);
                    }
                    Q0(obj);
                    if (compositing) {
                        r0(obj);
                    }
                }
            }
        }
    }

    private List<c> q(g.z obj) {
        g.z zVar = obj;
        int numPoints = zVar.o.length;
        if (numPoints < 2) {
            return null;
        }
        List<SVGAndroidRenderer.MarkerVector> markers = new ArrayList<>();
        float[] fArr = zVar.o;
        c lastPos = new c(fArr[0], fArr[1], 0.0f, 0.0f);
        float x = 0.0f;
        float y = 0.0f;
        for (int i2 = 2; i2 < numPoints; i2 += 2) {
            float[] fArr2 = zVar.o;
            x = fArr2[i2];
            y = fArr2[i2 + 1];
            lastPos.a(x, y);
            markers.add(lastPos);
            lastPos = new c(x, y, x - lastPos.a, y - lastPos.b);
        }
        if ((zVar instanceof g.a0) != 0) {
            float[] fArr3 = zVar.o;
            if (!(x == fArr3[0] || y == fArr3[1])) {
                float x2 = fArr3[0];
                float y2 = fArr3[1];
                lastPos.a(x2, y2);
                markers.add(lastPos);
                c cVar = new c(x2, y2, x2 - lastPos.a, y2 - lastPos.b);
                cVar.b((c) markers.get(0));
                markers.add(cVar);
                markers.set(0, cVar);
            }
        } else {
            markers.add(lastPos);
        }
        return markers;
    }

    private void D0(g.a0 obj) {
        G("Polygon render", new Object[0]);
        e1(this.e, obj);
        if (I() && g1()) {
            C0053h hVar = this.e;
            if (hVar.c || hVar.b) {
                Matrix matrix = obj.n;
                if (matrix != null) {
                    this.b.concat(matrix);
                }
                if (obj.o.length >= 2) {
                    Path path = j0(obj);
                    c1(obj);
                    x(obj);
                    u(obj);
                    boolean compositing = u0();
                    if (this.e.b) {
                        J(obj, path);
                    }
                    if (this.e.c) {
                        K(path);
                    }
                    Q0(obj);
                    if (compositing) {
                        r0(obj);
                    }
                }
            }
        }
    }

    private void L0(g.w0 obj) {
        G("Text render", new Object[0]);
        e1(this.e, obj);
        if (I()) {
            Matrix matrix = obj.s;
            if (matrix != null) {
                this.b.concat(matrix);
            }
            List<g.p> list = obj.o;
            float f2 = 0.0f;
            float x = (list == null || list.size() == 0) ? 0.0f : obj.o.get(0).e(this);
            List<g.p> list2 = obj.p;
            float y = (list2 == null || list2.size() == 0) ? 0.0f : obj.p.get(0).f(this);
            List<g.p> list3 = obj.q;
            float dx = (list3 == null || list3.size() == 0) ? 0.0f : obj.q.get(0).e(this);
            List<g.p> list4 = obj.r;
            if (!(list4 == null || list4.size() == 0)) {
                f2 = obj.r.get(0).f(this);
            }
            float dy = f2;
            g.e0.f anchor = W();
            if (anchor != g.e0.f.Start) {
                float textWidth = s(obj);
                if (anchor == g.e0.f.Middle) {
                    x -= textWidth / 2.0f;
                } else {
                    x -= textWidth;
                }
            }
            if (obj.h == null) {
                i proc = new i(x, y);
                M(obj, proc);
                RectF rectF = proc.d;
                obj.h = new g.b(rectF.left, rectF.top, rectF.width(), proc.d.height());
            }
            c1(obj);
            x(obj);
            u(obj);
            boolean compositing = u0();
            M(obj, new f(x + dx, y + dy));
            if (compositing) {
                r0(obj);
            }
        }
    }

    private g.e0.f W() {
        g.e0.f fVar;
        g.e0 e0Var = this.e.a;
        if (e0Var.E4 == g.e0.h.LTR || (fVar = e0Var.F4) == g.e0.f.Middle) {
            return e0Var.F4;
        }
        g.e0.f fVar2 = g.e0.f.Start;
        return fVar == fVar2 ? g.e0.f.End : fVar2;
    }

    /* compiled from: SVGAndroidRenderer */
    public class f extends j {
        float b;
        float c;

        f(float x, float y) {
            super(h.this, (a) null);
            this.b = x;
            this.c = y;
        }

        public void b(String text) {
            h.G("TextSequence render", new Object[0]);
            if (h.this.g1()) {
                if (h.this.e.b) {
                    h.this.b.drawText(text, this.b, this.c, h.this.e.d);
                }
                if (h.this.e.c) {
                    h.this.b.drawText(text, this.b, this.c, h.this.e.e);
                }
            }
            this.b += h.this.e.d.measureText(text);
        }
    }

    /* compiled from: SVGAndroidRenderer */
    public abstract class j {
        public abstract void b(String str);

        private j() {
        }

        /* synthetic */ j(h x0, a x1) {
            this();
        }

        public boolean a(g.y0 obj) {
            return true;
        }
    }

    private void M(g.y0 obj, j textprocessor) {
        if (I()) {
            Iterator<g.n0> it = obj.i.iterator();
            boolean isFirstChild = true;
            while (it.hasNext()) {
                g.n0 child = it.next();
                if (child instanceof g.c1) {
                    textprocessor.b(b1(((g.c1) child).c, isFirstChild, !it.hasNext()));
                } else {
                    t0(child, textprocessor);
                }
                isFirstChild = false;
            }
        }
    }

    private void t0(g.n0 obj, j textprocessor) {
        g.e0.f anchor;
        if (textprocessor.a((g.y0) obj)) {
            if (obj instanceof g.z0) {
                a1();
                T0((g.z0) obj);
                Z0();
                return;
            }
            boolean specifiedX = true;
            if (obj instanceof g.v0) {
                G("TSpan render", new Object[0]);
                a1();
                g.v0 tspan = (g.v0) obj;
                e1(this.e, tspan);
                if (I()) {
                    float x = 0.0f;
                    float y = 0.0f;
                    float dx = 0.0f;
                    float dy = 0.0f;
                    List<g.p> list = tspan.o;
                    if (list == null || list.size() <= 0) {
                        specifiedX = false;
                    }
                    if (textprocessor instanceof f) {
                        x = !specifiedX ? ((f) textprocessor).b : tspan.o.get(0).e(this);
                        List<g.p> list2 = tspan.p;
                        y = (list2 == null || list2.size() == 0) ? ((f) textprocessor).c : tspan.p.get(0).f(this);
                        List<g.p> list3 = tspan.q;
                        float f2 = 0.0f;
                        dx = (list3 == null || list3.size() == 0) ? 0.0f : tspan.q.get(0).e(this);
                        List<g.p> list4 = tspan.r;
                        if (!(list4 == null || list4.size() == 0)) {
                            f2 = tspan.r.get(0).f(this);
                        }
                        dy = f2;
                    }
                    if (specifiedX && (anchor = W()) != g.e0.f.Start) {
                        float textWidth = s(tspan);
                        if (anchor == g.e0.f.Middle) {
                            x -= textWidth / 2.0f;
                        } else {
                            x -= textWidth;
                        }
                    }
                    x((g.k0) tspan.d());
                    if (textprocessor instanceof f) {
                        ((f) textprocessor).b = x + dx;
                        ((f) textprocessor).c = y + dy;
                    }
                    boolean compositing = u0();
                    M(tspan, textprocessor);
                    if (compositing) {
                        r0(tspan);
                    }
                }
                Z0();
            } else if (obj instanceof g.u0) {
                a1();
                g.u0 tref = (g.u0) obj;
                e1(this.e, tref);
                if (I()) {
                    x((g.k0) tref.d());
                    g.n0 ref = obj.a.v(tref.o);
                    if (ref == null || !(ref instanceof g.y0)) {
                        N("Tref reference '%s' not found", tref.o);
                    } else {
                        StringBuilder str = new StringBuilder();
                        O((g.y0) ref, str);
                        if (str.length() > 0) {
                            textprocessor.b(str.toString());
                        }
                    }
                }
                Z0();
            }
        }
    }

    private void T0(g.z0 obj) {
        G("TextPath render", new Object[0]);
        e1(this.e, obj);
        if (I() && g1()) {
            g.n0 ref = obj.a.v(obj.o);
            if (ref == null) {
                N("TextPath reference '%s' not found", obj.o);
                return;
            }
            g.v pathObj = (g.v) ref;
            Path path = new d(pathObj.o).f();
            Matrix matrix = pathObj.n;
            if (matrix != null) {
                path.transform(matrix);
            }
            PathMeasure measure = new PathMeasure(path, false);
            g.p pVar = obj.p;
            float startOffset = pVar != null ? pVar.d(this, measure.getLength()) : 0.0f;
            g.e0.f anchor = W();
            if (anchor != g.e0.f.Start) {
                float textWidth = s(obj);
                if (anchor == g.e0.f.Middle) {
                    startOffset -= textWidth / 2.0f;
                } else {
                    startOffset -= textWidth;
                }
            }
            x((g.k0) obj.d());
            boolean compositing = u0();
            M(obj, new e(path, startOffset, 0.0f));
            if (compositing) {
                r0(obj);
            }
        }
    }

    /* compiled from: SVGAndroidRenderer */
    public class e extends f {
        private Path e;

        e(Path path, float x, float y) {
            super(x, y);
            this.e = path;
        }

        public void b(String text) {
            if (h.this.g1()) {
                if (h.this.e.b) {
                    h.this.b.drawTextOnPath(text, this.e, this.b, this.c, h.this.e.d);
                }
                if (h.this.e.c) {
                    h.this.b.drawTextOnPath(text, this.e, this.b, this.c, h.this.e.e);
                }
            }
            this.b += h.this.e.d.measureText(text);
        }
    }

    private float s(g.y0 parentTextObj) {
        k proc = new k(this, (a) null);
        M(parentTextObj, proc);
        return proc.b;
    }

    /* compiled from: SVGAndroidRenderer */
    public class k extends j {
        float b;

        private k() {
            super(h.this, (a) null);
            this.b = 0.0f;
        }

        /* synthetic */ k(h x0, a x1) {
            this();
        }

        public void b(String text) {
            this.b += h.this.e.d.measureText(text);
        }
    }

    /* compiled from: SVGAndroidRenderer */
    public class i extends j {
        float b;
        float c;
        RectF d = new RectF();

        i(float x, float y) {
            super(h.this, (a) null);
            this.b = x;
            this.c = y;
        }

        public boolean a(g.y0 obj) {
            if (!(obj instanceof g.z0)) {
                return true;
            }
            g.z0 tpath = (g.z0) obj;
            g.n0 ref = obj.a.v(tpath.o);
            if (ref == null) {
                h.N("TextPath path reference '%s' not found", tpath.o);
                return false;
            }
            g.v pathObj = (g.v) ref;
            Path path = new d(pathObj.o).f();
            Matrix matrix = pathObj.n;
            if (matrix != null) {
                path.transform(matrix);
            }
            RectF pathBounds = new RectF();
            path.computeBounds(pathBounds, true);
            this.d.union(pathBounds);
            return false;
        }

        public void b(String text) {
            if (h.this.g1()) {
                Rect rect = new Rect();
                h.this.e.d.getTextBounds(text, 0, text.length(), rect);
                RectF textbounds = new RectF(rect);
                textbounds.offset(this.b, this.c);
                this.d.union(textbounds);
            }
            this.b += h.this.e.d.measureText(text);
        }
    }

    private void O(g.y0 parent, StringBuilder str) {
        Iterator<g.n0> it = parent.i.iterator();
        boolean isFirstChild = true;
        while (it.hasNext()) {
            g.n0 child = it.next();
            if (child instanceof g.y0) {
                O((g.y0) child, str);
            } else if (child instanceof g.c1) {
                str.append(b1(((g.c1) child).c, isFirstChild, !it.hasNext()));
            }
            isFirstChild = false;
        }
    }

    private String b1(String text, boolean isFirstChild, boolean isLastChild) {
        if (this.e.h) {
            return text.replaceAll("[\\n\\t]", " ");
        }
        String text2 = text.replaceAll("\\n", "").replaceAll("\\t", " ");
        if (isFirstChild) {
            text2 = text2.replaceAll("^\\s+", "");
        }
        if (isLastChild) {
            text2 = text2.replaceAll("\\s+$", "");
        }
        return text2.replaceAll("\\s{2,}", " ");
    }

    private void K0(g.t0 obj, g.b viewPort) {
        G("Symbol render", new Object[0]);
        if (viewPort.c != 0.0f && viewPort.d != 0.0f) {
            e positioning = obj.o;
            if (positioning == null) {
                positioning = e.c;
            }
            e1(this.e, obj);
            C0053h hVar = this.e;
            hVar.f = viewPort;
            if (!hVar.a.G4.booleanValue()) {
                g.b bVar = this.e.f;
                W0(bVar.a, bVar.b, bVar.c, bVar.d);
            }
            g.b bVar2 = obj.p;
            if (bVar2 != null) {
                this.b.concat(t(this.e.f, bVar2, positioning));
                this.e.g = obj.p;
            } else {
                Canvas canvas = this.b;
                g.b bVar3 = this.e.f;
                canvas.translate(bVar3.a, bVar3.b);
            }
            boolean compositing = u0();
            N0(obj, true);
            if (compositing) {
                r0(obj);
            }
            c1(obj);
        }
    }

    private void z0(g.o obj) {
        g.p pVar;
        String str;
        int i2 = 0;
        G("Image render", new Object[0]);
        g.p pVar2 = obj.s;
        if (pVar2 != null && !pVar2.i() && (pVar = obj.t) != null && !pVar.i() && (str = obj.p) != null) {
            e positioning = obj.o;
            if (positioning == null) {
                positioning = e.c;
            }
            Bitmap image = y(str);
            if (image == null) {
                i fileResolver = g.k();
                if (fileResolver != null) {
                    image = fileResolver.d(obj.p);
                } else {
                    return;
                }
            }
            if (image == null) {
                N("Could not locate image '%s'", obj.p);
                return;
            }
            g.b imageNaturalSize = new g.b(0.0f, 0.0f, (float) image.getWidth(), (float) image.getHeight());
            e1(this.e, obj);
            if (I() && g1()) {
                Matrix matrix = obj.u;
                if (matrix != null) {
                    this.b.concat(matrix);
                }
                g.p pVar3 = obj.q;
                float _x = pVar3 != null ? pVar3.e(this) : 0.0f;
                g.p pVar4 = obj.r;
                this.e.f = new g.b(_x, pVar4 != null ? pVar4.f(this) : 0.0f, obj.s.e(this), obj.t.e(this));
                if (!this.e.a.G4.booleanValue()) {
                    g.b bVar = this.e.f;
                    W0(bVar.a, bVar.b, bVar.c, bVar.d);
                }
                obj.h = this.e.f;
                c1(obj);
                u(obj);
                boolean compositing = u0();
                f1();
                this.b.save();
                this.b.concat(t(this.e.f, imageNaturalSize, positioning));
                if (this.e.a.X4 != g.e0.e.optimizeSpeed) {
                    i2 = 2;
                }
                this.b.drawBitmap(image, 0.0f, 0.0f, new Paint(i2));
                this.b.restore();
                if (compositing) {
                    r0(obj);
                }
            }
        }
    }

    private Bitmap y(String url) {
        int comma;
        if (!url.startsWith("data:") || url.length() < 14 || (comma = url.indexOf(44)) < 12 || !";base64".equals(url.substring(comma - 7, comma))) {
            return null;
        }
        try {
            byte[] imageData = Base64.decode(url.substring(comma + 1), 0);
            return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        } catch (Exception e2) {
            Log.e("SVGAndroidRenderer", "Could not decode bad Data URL", e2);
            return null;
        }
    }

    private boolean I() {
        Boolean bool = this.e.a.L4;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean g1() {
        Boolean bool = this.e.a.M4;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    private Matrix t(g.b viewPort, g.b viewBox, e positioning) {
        Matrix m = new Matrix();
        if (positioning == null || positioning.a() == null) {
            return m;
        }
        float xScale = viewPort.c / viewBox.c;
        float yScale = viewPort.d / viewBox.d;
        float xOffset = -viewBox.a;
        float yOffset = -viewBox.b;
        if (positioning.equals(e.b)) {
            m.preTranslate(viewPort.a, viewPort.b);
            m.preScale(xScale, yScale);
            m.preTranslate(xOffset, yOffset);
            return m;
        }
        float scale = positioning.b() == e.b.slice ? Math.max(xScale, yScale) : Math.min(xScale, yScale);
        float imageW = viewPort.c / scale;
        float imageH = viewPort.d / scale;
        int[] iArr = a.a;
        switch (iArr[positioning.a().ordinal()]) {
            case 1:
            case 2:
            case 3:
                xOffset -= (viewBox.c - imageW) / 2.0f;
                break;
            case 4:
            case 5:
            case 6:
                xOffset -= viewBox.c - imageW;
                break;
        }
        switch (iArr[positioning.a().ordinal()]) {
            case 2:
            case 5:
            case 7:
                yOffset -= (viewBox.d - imageH) / 2.0f;
                break;
            case 3:
            case 6:
            case 8:
                yOffset -= viewBox.d - imageH;
                break;
        }
        m.preTranslate(viewPort.a, viewPort.b);
        m.preScale(scale, scale);
        m.preTranslate(xOffset, yOffset);
        return m;
    }

    private boolean e0(g.e0 style, long flag) {
        return (style.c & flag) != 0;
    }

    private void d1(C0053h state, g.e0 style) {
        if (e0(style, 4096)) {
            state.a.p4 = style.p4;
        }
        if (e0(style, 2048)) {
            state.a.p3 = style.p3;
        }
        boolean z = false;
        if (e0(style, 1)) {
            state.a.d = style.d;
            g.o0 o0Var = style.d;
            state.b = (o0Var == null || o0Var == g.f.d) ? false : true;
        }
        if (e0(style, 4)) {
            state.a.q = style.q;
        }
        if (e0(style, 6149)) {
            X0(state, true, state.a.d);
        }
        if (e0(style, 2)) {
            state.a.f = style.f;
        }
        if (e0(style, 8)) {
            state.a.x = style.x;
            g.o0 o0Var2 = style.x;
            state.c = (o0Var2 == null || o0Var2 == g.f.d) ? false : true;
        }
        if (e0(style, 16)) {
            state.a.y = style.y;
        }
        if (e0(style, 6168)) {
            X0(state, false, state.a.x);
        }
        if (e0(style, IjkMediaMeta.AV_CH_LOW_FREQUENCY_2)) {
            state.a.W4 = style.W4;
        }
        if (e0(style, 32)) {
            g.e0 e0Var = state.a;
            g.p pVar = style.z;
            e0Var.z = pVar;
            state.e.setStrokeWidth(pVar.c(this));
        }
        if (e0(style, 64)) {
            state.a.p0 = style.p0;
            switch (a.b[style.p0.ordinal()]) {
                case 1:
                    state.e.setStrokeCap(Paint.Cap.BUTT);
                    break;
                case 2:
                    state.e.setStrokeCap(Paint.Cap.ROUND);
                    break;
                case 3:
                    state.e.setStrokeCap(Paint.Cap.SQUARE);
                    break;
            }
        }
        if (e0(style, 128)) {
            state.a.a1 = style.a1;
            switch (a.c[style.a1.ordinal()]) {
                case 1:
                    state.e.setStrokeJoin(Paint.Join.MITER);
                    break;
                case 2:
                    state.e.setStrokeJoin(Paint.Join.ROUND);
                    break;
                case 3:
                    state.e.setStrokeJoin(Paint.Join.BEVEL);
                    break;
            }
        }
        if (e0(style, 256)) {
            state.a.p1 = style.p1;
            state.e.setStrokeMiter(style.p1.floatValue());
        }
        if (e0(style, 512)) {
            state.a.a2 = style.a2;
        }
        if (e0(style, 1024)) {
            state.a.p2 = style.p2;
        }
        if (e0(style, 1536)) {
            g.p[] pVarArr = state.a.a2;
            if (pVarArr == null) {
                state.e.setPathEffect((PathEffect) null);
            } else {
                float intervalSum = 0.0f;
                int n = pVarArr.length;
                int arrayLen = n % 2 == 0 ? n : n * 2;
                float[] intervals = new float[arrayLen];
                for (int i2 = 0; i2 < arrayLen; i2++) {
                    intervals[i2] = state.a.a2[i2 % n].c(this);
                    intervalSum += intervals[i2];
                }
                if (intervalSum == 0.0f) {
                    state.e.setPathEffect((PathEffect) null);
                } else {
                    float offset = state.a.p2.c(this);
                    if (offset < 0.0f) {
                        offset = intervalSum + (offset % intervalSum);
                    }
                    state.e.setPathEffect(new DashPathEffect(intervals, offset));
                }
            }
        }
        if (e0(style, 16384)) {
            float currentFontSize = Y();
            state.a.A4 = style.A4;
            state.d.setTextSize(style.A4.d(this, currentFontSize));
            state.e.setTextSize(style.A4.d(this, currentFontSize));
        }
        if (e0(style, 8192)) {
            state.a.z4 = style.z4;
        }
        if (e0(style, 32768)) {
            if (style.B4.intValue() == -1 && state.a.B4.intValue() > 100) {
                g.e0 e0Var2 = state.a;
                e0Var2.B4 = Integer.valueOf(e0Var2.B4.intValue() - 100);
            } else if (style.B4.intValue() != 1 || state.a.B4.intValue() >= 900) {
                state.a.B4 = style.B4;
            } else {
                g.e0 e0Var3 = state.a;
                e0Var3.B4 = Integer.valueOf(e0Var3.B4.intValue() + 100);
            }
        }
        if (e0(style, 65536)) {
            state.a.C4 = style.C4;
        }
        if (e0(style, 106496)) {
            Typeface font = null;
            if (!(state.a.z4 == null || this.d == null)) {
                i fileResolver = g.k();
                for (String fontName : state.a.z4) {
                    g.e0 e0Var4 = state.a;
                    font = z(fontName, e0Var4.B4, e0Var4.C4);
                    if (font == null && fileResolver != null) {
                        font = fileResolver.c(fontName, state.a.B4.intValue(), String.valueOf(state.a.C4));
                        continue;
                    }
                    if (font != null) {
                    }
                }
            }
            if (font == null) {
                g.e0 e0Var5 = state.a;
                font = z("serif", e0Var5.B4, e0Var5.C4);
            }
            state.d.setTypeface(font);
            state.e.setTypeface(font);
        }
        if (e0(style, 131072)) {
            state.a.D4 = style.D4;
            Paint paint = state.d;
            g.e0.C0051g gVar = style.D4;
            g.e0.C0051g gVar2 = g.e0.C0051g.LineThrough;
            paint.setStrikeThruText(gVar == gVar2);
            Paint paint2 = state.d;
            g.e0.C0051g gVar3 = style.D4;
            g.e0.C0051g gVar4 = g.e0.C0051g.Underline;
            paint2.setUnderlineText(gVar3 == gVar4);
            if (Build.VERSION.SDK_INT >= 17) {
                state.e.setStrikeThruText(style.D4 == gVar2);
                Paint paint3 = state.e;
                if (style.D4 == gVar4) {
                    z = true;
                }
                paint3.setUnderlineText(z);
            }
        }
        if (e0(style, 68719476736L)) {
            state.a.E4 = style.E4;
        }
        if (e0(style, PlaybackStateCompat.ACTION_SET_REPEAT_MODE)) {
            state.a.F4 = style.F4;
        }
        if (e0(style, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED)) {
            state.a.G4 = style.G4;
        }
        if (e0(style, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE)) {
            state.a.I4 = style.I4;
        }
        if (e0(style, 4194304)) {
            state.a.J4 = style.J4;
        }
        if (e0(style, 8388608)) {
            state.a.K4 = style.K4;
        }
        if (e0(style, 16777216)) {
            state.a.L4 = style.L4;
        }
        if (e0(style, 33554432)) {
            state.a.M4 = style.M4;
        }
        if (e0(style, 1048576)) {
            state.a.H4 = style.H4;
        }
        if (e0(style, 268435456)) {
            state.a.P4 = style.P4;
        }
        if (e0(style, IjkMediaMeta.AV_CH_STEREO_LEFT)) {
            state.a.Q4 = style.Q4;
        }
        if (e0(style, IjkMediaMeta.AV_CH_STEREO_RIGHT)) {
            state.a.R4 = style.R4;
        }
        if (e0(style, 67108864)) {
            state.a.N4 = style.N4;
        }
        if (e0(style, 134217728)) {
            state.a.O4 = style.O4;
        }
        if (e0(style, IjkMediaMeta.AV_CH_SURROUND_DIRECT_LEFT)) {
            state.a.U4 = style.U4;
        }
        if (e0(style, IjkMediaMeta.AV_CH_SURROUND_DIRECT_RIGHT)) {
            state.a.V4 = style.V4;
        }
        if (e0(style, 137438953472L)) {
            state.a.X4 = style.X4;
        }
    }

    /* compiled from: SVGAndroidRenderer */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;
        static final /* synthetic */ int[] c;

        static {
            int[] iArr = new int[g.e0.d.values().length];
            c = iArr;
            try {
                iArr[g.e0.d.Miter.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                c[g.e0.d.Round.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                c[g.e0.d.Bevel.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[g.e0.c.values().length];
            b = iArr2;
            try {
                iArr2[g.e0.c.Butt.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                b[g.e0.c.Round.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                b[g.e0.c.Square.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            int[] iArr3 = new int[e.a.values().length];
            a = iArr3;
            try {
                iArr3[e.a.xMidYMin.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[e.a.xMidYMid.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[e.a.xMidYMax.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[e.a.xMaxYMin.ordinal()] = 4;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[e.a.xMaxYMid.ordinal()] = 5;
            } catch (NoSuchFieldError e11) {
            }
            try {
                a[e.a.xMaxYMax.ordinal()] = 6;
            } catch (NoSuchFieldError e12) {
            }
            try {
                a[e.a.xMinYMid.ordinal()] = 7;
            } catch (NoSuchFieldError e13) {
            }
            try {
                a[e.a.xMinYMax.ordinal()] = 8;
            } catch (NoSuchFieldError e14) {
            }
        }
    }

    private void X0(C0053h state, boolean isFill, g.o0 paint) {
        int col;
        g.e0 e0Var = state.a;
        float paintOpacity = (isFill ? e0Var.q : e0Var.y).floatValue();
        if (paint instanceof g.f) {
            col = ((g.f) paint).f;
        } else if ((paint instanceof g.C0052g) != 0) {
            col = state.a.p4.f;
        } else {
            return;
        }
        int col2 = F(col, paintOpacity);
        if (isFill) {
            state.d.setColor(col2);
        } else {
            state.e.setColor(col2);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        if (r10.equals("sans-serif") != false) goto L_0x005a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.Typeface z(java.lang.String r10, java.lang.Integer r11, com.caverock.androidsvg.g.e0.b r12) {
        /*
            r9 = this;
            r0 = 0
            com.caverock.androidsvg.g$e0$b r1 = com.caverock.androidsvg.g.e0.b.Italic
            r2 = 1
            r3 = 0
            if (r12 != r1) goto L_0x0009
            r1 = r2
            goto L_0x000a
        L_0x0009:
            r1 = r3
        L_0x000a:
            int r4 = r11.intValue()
            r5 = 500(0x1f4, float:7.0E-43)
            r6 = 3
            r7 = 2
            if (r4 <= r5) goto L_0x001a
            if (r1 == 0) goto L_0x0018
            r4 = r6
            goto L_0x001f
        L_0x0018:
            r4 = r2
            goto L_0x001f
        L_0x001a:
            if (r1 == 0) goto L_0x001e
            r4 = r7
            goto L_0x001f
        L_0x001e:
            r4 = r3
        L_0x001f:
            r5 = -1
            int r8 = r10.hashCode()
            switch(r8) {
                case -1536685117: goto L_0x0050;
                case -1431958525: goto L_0x0046;
                case -1081737434: goto L_0x003c;
                case 109326717: goto L_0x0032;
                case 1126973893: goto L_0x0028;
                default: goto L_0x0027;
            }
        L_0x0027:
            goto L_0x0059
        L_0x0028:
            java.lang.String r2 = "cursive"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0027
            r2 = r6
            goto L_0x005a
        L_0x0032:
            java.lang.String r2 = "serif"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0027
            r2 = r3
            goto L_0x005a
        L_0x003c:
            java.lang.String r2 = "fantasy"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0027
            r2 = 4
            goto L_0x005a
        L_0x0046:
            java.lang.String r2 = "monospace"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0027
            r2 = r7
            goto L_0x005a
        L_0x0050:
            java.lang.String r3 = "sans-serif"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0027
            goto L_0x005a
        L_0x0059:
            r2 = r5
        L_0x005a:
            switch(r2) {
                case 0: goto L_0x007a;
                case 1: goto L_0x0073;
                case 2: goto L_0x006c;
                case 3: goto L_0x0065;
                case 4: goto L_0x005e;
                default: goto L_0x005d;
            }
        L_0x005d:
            goto L_0x0080
        L_0x005e:
            android.graphics.Typeface r2 = android.graphics.Typeface.SANS_SERIF
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r2, r4)
            goto L_0x0080
        L_0x0065:
            android.graphics.Typeface r2 = android.graphics.Typeface.SANS_SERIF
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r2, r4)
            goto L_0x0080
        L_0x006c:
            android.graphics.Typeface r2 = android.graphics.Typeface.MONOSPACE
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r2, r4)
            goto L_0x0080
        L_0x0073:
            android.graphics.Typeface r2 = android.graphics.Typeface.SANS_SERIF
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r2, r4)
            goto L_0x0080
        L_0x007a:
            android.graphics.Typeface r2 = android.graphics.Typeface.SERIF
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r2, r4)
        L_0x0080:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.h.z(java.lang.String, java.lang.Integer, com.caverock.androidsvg.g$e0$b):android.graphics.Typeface");
    }

    private static int C(float val) {
        int i2 = (int) (256.0f * val);
        if (i2 < 0) {
            return 0;
        }
        if (i2 > 255) {
            return 255;
        }
        return i2;
    }

    private static int F(int colour, float opacity) {
        int alpha = 255;
        int alpha2 = Math.round(((float) ((colour >> 24) & 255)) * opacity);
        if (alpha2 < 0) {
            alpha = 0;
        } else if (alpha2 <= 255) {
            alpha = alpha2;
        }
        return (alpha << 24) | (16777215 & colour);
    }

    private Path.FillType c0() {
        g.e0.a aVar = this.e.a.f;
        if (aVar == null || aVar != g.e0.a.EvenOdd) {
            return Path.FillType.WINDING;
        }
        return Path.FillType.EVEN_ODD;
    }

    private void W0(float minX, float minY, float width, float height) {
        float left = minX;
        float top = minY;
        float right = minX + width;
        float bottom = minY + height;
        g.c cVar = this.e.a.H4;
        if (cVar != null) {
            left += cVar.d.e(this);
            top += this.e.a.H4.a.f(this);
            right -= this.e.a.H4.b.e(this);
            bottom -= this.e.a.H4.c.f(this);
        }
        this.b.clipRect(left, top, right, bottom);
    }

    private void f1() {
        int col;
        g.e0 e0Var = this.e.a;
        g.o0 o0Var = e0Var.U4;
        if (o0Var instanceof g.f) {
            col = ((g.f) o0Var).f;
        } else if ((o0Var instanceof g.C0052g) != 0) {
            col = e0Var.p4.f;
        } else {
            return;
        }
        Float f2 = e0Var.V4;
        if (f2 != null) {
            col = F(col, f2.floatValue());
        }
        this.b.drawColor(col);
    }

    /* compiled from: SVGAndroidRenderer */
    public class d implements g.x {
        Path a = new Path();
        float b;
        float c;

        d(g.w pathDef) {
            if (pathDef != null) {
                pathDef.h(this);
            }
        }

        /* access modifiers changed from: package-private */
        public Path f() {
            return this.a;
        }

        public void b(float x, float y) {
            this.a.moveTo(x, y);
            this.b = x;
            this.c = y;
        }

        public void e(float x, float y) {
            this.a.lineTo(x, y);
            this.b = x;
            this.c = y;
        }

        public void c(float x1, float y1, float x2, float y2, float x3, float y3) {
            this.a.cubicTo(x1, y1, x2, y2, x3, y3);
            this.b = x3;
            this.c = y3;
        }

        public void a(float x1, float y1, float x2, float y2) {
            this.a.quadTo(x1, y1, x2, y2);
            this.b = x2;
            this.c = y2;
        }

        public void d(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y) {
            h.m(this.b, this.c, rx, ry, xAxisRotation, largeArcFlag, sweepFlag, x, y, this);
            this.b = x;
            this.c = y;
        }

        public void close() {
            this.a.close();
        }
    }

    /* access modifiers changed from: private */
    public static void m(float lastX, float lastY, float rx, float ry, float angle, boolean largeArcFlag, boolean sweepFlag, float x, float y, g.x pather) {
        float f2;
        float f3;
        float f4 = angle;
        boolean z = sweepFlag;
        float rx2 = x;
        float f5 = y;
        if (lastX != rx2 || lastY != f5) {
            if (rx == 0.0f) {
                f3 = rx2;
                f2 = f5;
            } else if (ry == 0.0f) {
                f3 = rx2;
                f2 = f5;
            } else {
                float rx3 = Math.abs(rx);
                float ry2 = Math.abs(ry);
                double angleRad = Math.toRadians(((double) f4) % 360.0d);
                double cosAngle = Math.cos(angleRad);
                double sinAngle = Math.sin(angleRad);
                double dx2 = ((double) (lastX - rx2)) / 2.0d;
                double d2 = angleRad;
                double dy2 = ((double) (lastY - f5)) / 2.0d;
                double x1 = (cosAngle * dx2) + (sinAngle * dy2);
                double y1 = ((-sinAngle) * dx2) + (cosAngle * dy2);
                double d3 = dy2;
                double rx_sq = (double) (rx3 * rx3);
                double d4 = dx2;
                double ry_sq = (double) (ry2 * ry2);
                double x1_sq = x1 * x1;
                double y1_sq = y1 * y1;
                double radiiCheck = (x1_sq / rx_sq) + (y1_sq / ry_sq);
                if (radiiCheck > 0.99999d) {
                    double radiiScale = Math.sqrt(radiiCheck) * 1.00001d;
                    double d5 = rx_sq;
                    float rx4 = (float) (((double) rx3) * radiiScale);
                    ry2 = (float) (((double) ry2) * radiiScale);
                    rx_sq = (double) (rx4 * rx4);
                    ry_sq = (double) (ry2 * ry2);
                    rx3 = rx4;
                } else {
                    double d6 = rx_sq;
                }
                double sign = -1.0d;
                double sq = (((rx_sq * ry_sq) - (rx_sq * y1_sq)) - (ry_sq * x1_sq)) / ((rx_sq * y1_sq) + (ry_sq * x1_sq));
                double coef = Math.sqrt(sq < 0.0d ? 0.0d : sq) * (largeArcFlag == z ? -1.0d : 1.0d);
                double d7 = rx_sq;
                double d8 = ry_sq;
                double cx1 = ((((double) rx3) * y1) / ((double) ry2)) * coef;
                double cy1 = (-((((double) ry2) * x1) / ((double) rx3))) * coef;
                double sx2 = ((double) (lastX + x)) / 2.0d;
                double y12 = y1;
                double sy2 = ((double) (lastY + y)) / 2.0d;
                float rx5 = rx3;
                double cx = sx2 + ((cosAngle * cx1) - (sinAngle * cy1));
                double d9 = cosAngle;
                double cosAngle2 = sy2 + (sinAngle * cx1) + (cosAngle * cy1);
                double d10 = sy2;
                double d11 = sinAngle;
                float rx6 = rx5;
                double ux = (x1 - cx1) / ((double) rx6);
                double d12 = sx2;
                float ry3 = ry2;
                double uy = (y12 - cy1) / ((double) ry3);
                double d13 = cx1;
                double vx = ((-x1) - cx1) / ((double) rx6);
                double y13 = y12;
                double d14 = x1;
                double d15 = cy1;
                double vy = ((-y13) - cy1) / ((double) ry3);
                double angleStart = Math.acos(ux / Math.sqrt((ux * ux) + (uy * uy))) * (uy < 0.0d ? -1.0d : 1.0d);
                double n = Math.sqrt(((ux * ux) + (uy * uy)) * ((vx * vx) + (vy * vy)));
                double p = (ux * vx) + (uy * vy);
                if ((ux * vy) - (uy * vx) >= 0.0d) {
                    sign = 1.0d;
                }
                double angleExtent = B(p / n) * sign;
                if (!sweepFlag && angleExtent > 0.0d) {
                    angleExtent -= 6.283185307179586d;
                } else if (sweepFlag && angleExtent < 0.0d) {
                    angleExtent += 6.283185307179586d;
                }
                double angleExtent2 = angleExtent % 6.283185307179586d;
                double angleExtent3 = y13;
                double d16 = uy;
                float[] bezierPoints = n(angleStart % 6.283185307179586d, angleExtent2);
                Matrix m = new Matrix();
                m.postScale(rx6, ry3);
                double d17 = angleExtent2;
                m.postRotate(angle);
                m.postTranslate((float) cx, (float) cosAngle2);
                m.mapPoints(bezierPoints);
                bezierPoints[bezierPoints.length - 2] = x;
                double d18 = cx;
                bezierPoints[bezierPoints.length - 1] = y;
                for (int i2 = 0; i2 < bezierPoints.length; i2 += 6) {
                    pather.c(bezierPoints[i2], bezierPoints[i2 + 1], bezierPoints[i2 + 2], bezierPoints[i2 + 3], bezierPoints[i2 + 4], bezierPoints[i2 + 5]);
                }
                return;
            }
            pather.e(f3, f2);
        }
    }

    private static double B(double val) {
        if (val < -1.0d) {
            return 3.141592653589793d;
        }
        if (val > 1.0d) {
            return 0.0d;
        }
        return Math.acos(val);
    }

    private static float[] n(double angleStart, double angleExtent) {
        int numSegments = (int) Math.ceil((Math.abs(angleExtent) * 2.0d) / 3.141592653589793d);
        double angleIncrement = angleExtent / ((double) numSegments);
        double controlLength = (Math.sin(angleIncrement / 2.0d) * 1.3333333333333333d) / (Math.cos(angleIncrement / 2.0d) + 1.0d);
        float[] coords = new float[(numSegments * 6)];
        int pos = 0;
        int i2 = 0;
        while (i2 < numSegments) {
            double angle = angleStart + (((double) i2) * angleIncrement);
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);
            int pos2 = pos + 1;
            double angleIncrement2 = angleIncrement;
            int i3 = i2;
            coords[pos] = (float) (dx - (controlLength * dy));
            int pos3 = pos2 + 1;
            coords[pos2] = (float) ((controlLength * dx) + dy);
            double angle2 = angle + angleIncrement2;
            double dx2 = Math.cos(angle2);
            double dx3 = Math.sin(angle2);
            int pos4 = pos3 + 1;
            coords[pos3] = (float) ((controlLength * dx3) + dx2);
            int pos5 = pos4 + 1;
            coords[pos4] = (float) (dx3 - (controlLength * dx2));
            int pos6 = pos5 + 1;
            coords[pos5] = (float) dx2;
            pos = pos6 + 1;
            coords[pos6] = (float) dx3;
            i2 = i3 + 1;
            angleIncrement = angleIncrement2;
        }
        return coords;
    }

    /* compiled from: SVGAndroidRenderer */
    public class c {
        float a;
        float b;
        float c = 0.0f;
        float d = 0.0f;
        boolean e = false;

        c(float x, float y, float dx, float dy) {
            this.a = x;
            this.b = y;
            double len = Math.sqrt((double) ((dx * dx) + (dy * dy)));
            if (len != 0.0d) {
                this.c = (float) (((double) dx) / len);
                this.d = (float) (((double) dy) / len);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(float x, float y) {
            float dx = x - this.a;
            float dy = y - this.b;
            double len = Math.sqrt((double) ((dx * dx) + (dy * dy)));
            if (len != 0.0d) {
                dx = (float) (((double) dx) / len);
                dy = (float) (((double) dy) / len);
            }
            float f2 = this.c;
            if (dx == (-f2) && dy == (-this.d)) {
                this.e = true;
                this.c = -dy;
                this.d = dx;
                return;
            }
            this.c = f2 + dx;
            this.d += dy;
        }

        /* access modifiers changed from: package-private */
        public void b(c v2) {
            float f2 = v2.c;
            float f3 = this.c;
            if (f2 == (-f3)) {
                float f4 = v2.d;
                if (f4 == (-this.d)) {
                    this.e = true;
                    this.c = -f4;
                    this.d = v2.c;
                    return;
                }
            }
            this.c = f3 + f2;
            this.d += v2.d;
        }

        public String toString() {
            return "(" + this.a + "," + this.b + " " + this.c + "," + this.d + ")";
        }
    }

    /* compiled from: SVGAndroidRenderer */
    public class b implements g.x {
        private List<c> a = new ArrayList();
        private float b;
        private float c;
        private c d = null;
        private boolean e = false;
        private boolean f = true;
        private int g = -1;
        private boolean h;

        b(g.w pathDef) {
            if (pathDef != null) {
                pathDef.h(this);
                if (this.h) {
                    this.d.b(this.a.get(this.g));
                    this.a.set(this.g, this.d);
                    this.h = false;
                }
                c cVar = this.d;
                if (cVar != null) {
                    this.a.add(cVar);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public List<c> f() {
            return this.a;
        }

        public void b(float x, float y) {
            if (this.h) {
                this.d.b(this.a.get(this.g));
                this.a.set(this.g, this.d);
                this.h = false;
            }
            c cVar = this.d;
            if (cVar != null) {
                this.a.add(cVar);
            }
            this.b = x;
            this.c = y;
            this.d = new c(x, y, 0.0f, 0.0f);
            this.g = this.a.size();
        }

        public void e(float x, float y) {
            this.d.a(x, y);
            this.a.add(this.d);
            h hVar = h.this;
            c cVar = this.d;
            this.d = new c(x, y, x - cVar.a, y - cVar.b);
            this.h = false;
        }

        public void c(float x1, float y1, float x2, float y2, float x3, float y3) {
            if (this.f || this.e) {
                this.d.a(x1, y1);
                this.a.add(this.d);
                this.e = false;
            }
            this.d = new c(x3, y3, x3 - x2, y3 - y2);
            this.h = false;
        }

        public void a(float x1, float y1, float x2, float y2) {
            this.d.a(x1, y1);
            this.a.add(this.d);
            this.d = new c(x2, y2, x2 - x1, y2 - y1);
            this.h = false;
        }

        public void d(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y) {
            this.e = true;
            this.f = false;
            c cVar = this.d;
            h.m(cVar.a, cVar.b, rx, ry, xAxisRotation, largeArcFlag, sweepFlag, x, y, this);
            this.f = true;
            this.h = false;
        }

        public void close() {
            this.a.add(this.d);
            e(this.b, this.c);
            this.h = true;
        }
    }

    private void Q0(g.l obj) {
        List<c> list;
        int markerCount;
        g.e0 e0Var = this.e.a;
        String str = e0Var.I4;
        if (str != null || e0Var.J4 != null || e0Var.K4 != null) {
            g.r _markerStart = null;
            g.r _markerMid = null;
            g.r _markerEnd = null;
            if (str != null) {
                g.n0 ref = obj.a.v(str);
                if (ref != null) {
                    _markerStart = (g.r) ref;
                } else {
                    N("Marker reference '%s' not found", this.e.a.I4);
                }
            }
            String str2 = this.e.a.J4;
            if (str2 != null) {
                g.n0 ref2 = obj.a.v(str2);
                if (ref2 != null) {
                    _markerMid = (g.r) ref2;
                } else {
                    N("Marker reference '%s' not found", this.e.a.J4);
                }
            }
            String str3 = this.e.a.K4;
            if (str3 != null) {
                g.n0 ref3 = obj.a.v(str3);
                if (ref3 != null) {
                    _markerEnd = (g.r) ref3;
                } else {
                    N("Marker reference '%s' not found", this.e.a.K4);
                }
            }
            if (obj instanceof g.v) {
                list = new b(((g.v) obj).o).f();
            } else if (obj instanceof g.q) {
                list = p((g.q) obj);
            } else {
                list = q((g.z) obj);
            }
            if (list != null && (markerCount = list.size()) != 0) {
                g.e0 e0Var2 = this.e.a;
                e0Var2.K4 = null;
                e0Var2.J4 = null;
                e0Var2.I4 = null;
                if (_markerStart != null) {
                    P0(_markerStart, list.get(0));
                }
                if (_markerMid != null && list.size() > 2) {
                    c lastPos = list.get(0);
                    c thisPos = list.get(1);
                    for (int i2 = 1; i2 < markerCount - 1; i2++) {
                        c nextPos = list.get(i2 + 1);
                        if (thisPos.e) {
                            thisPos = v0(lastPos, thisPos, nextPos);
                        }
                        P0(_markerMid, thisPos);
                        lastPos = thisPos;
                        thisPos = nextPos;
                    }
                }
                if (_markerEnd != null) {
                    P0(_markerEnd, list.get(markerCount - 1));
                }
            }
        }
    }

    private c v0(c lastPos, c thisPos, c nextPos) {
        float dot = L(thisPos.c, thisPos.d, thisPos.a - lastPos.a, thisPos.b - lastPos.b);
        if (dot == 0.0f) {
            dot = L(thisPos.c, thisPos.d, nextPos.a - thisPos.a, nextPos.b - thisPos.b);
        }
        if (dot > 0.0f) {
            return thisPos;
        }
        if (dot == 0.0f && (thisPos.c > 0.0f || thisPos.d >= 0.0f)) {
            return thisPos;
        }
        thisPos.c = -thisPos.c;
        thisPos.d = -thisPos.d;
        return thisPos;
    }

    private float L(float x1, float y1, float x2, float y2) {
        return (x1 * x2) + (y1 * y2);
    }

    private void P0(g.r marker, c pos) {
        float yOffset;
        g.r rVar = marker;
        c cVar = pos;
        float angle = 0.0f;
        a1();
        Float f2 = rVar.v;
        if (f2 != null) {
            if (Float.isNaN(f2.floatValue())) {
                float f3 = cVar.c;
                if (!(f3 == 0.0f && cVar.d == 0.0f)) {
                    angle = (float) Math.toDegrees(Math.atan2((double) cVar.d, (double) f3));
                }
            } else {
                angle = rVar.v.floatValue();
            }
        }
        float unitsScale = rVar.q ? 1.0f : this.e.a.z.b(this.c);
        this.e = U(marker);
        Matrix m = new Matrix();
        m.preTranslate(cVar.a, cVar.b);
        m.preRotate(angle);
        m.preScale(unitsScale, unitsScale);
        g.p pVar = rVar.r;
        float _refX = pVar != null ? pVar.e(this) : 0.0f;
        g.p pVar2 = rVar.s;
        float _refY = pVar2 != null ? pVar2.f(this) : 0.0f;
        g.p pVar3 = rVar.t;
        float _markerHeight = 3.0f;
        float _markerWidth = pVar3 != null ? pVar3.e(this) : 3.0f;
        g.p pVar4 = rVar.u;
        if (pVar4 != null) {
            _markerHeight = pVar4.f(this);
        }
        g.b bVar = rVar.p;
        if (bVar != null) {
            float xScale = _markerWidth / bVar.c;
            float yScale = _markerHeight / bVar.d;
            e positioning = rVar.o;
            if (positioning == null) {
                positioning = e.c;
            }
            if (!positioning.equals(e.b)) {
                float aspectScale = positioning.b() == e.b.slice ? Math.max(xScale, yScale) : Math.min(xScale, yScale);
                yScale = aspectScale;
                xScale = aspectScale;
            }
            m.preTranslate((-_refX) * xScale, (-_refY) * yScale);
            this.b.concat(m);
            g.b bVar2 = rVar.p;
            float imageW = bVar2.c * xScale;
            float imageH = bVar2.d * yScale;
            float xOffset = 0.0f;
            int[] iArr = a.a;
            switch (iArr[positioning.a().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    xOffset = 0.0f - ((_markerWidth - imageW) / 2.0f);
                    break;
                case 4:
                case 5:
                case 6:
                    xOffset = 0.0f - (_markerWidth - imageW);
                    break;
            }
            switch (iArr[positioning.a().ordinal()]) {
                case 2:
                case 5:
                case 7:
                    yOffset = 0.0f - ((_markerHeight - imageH) / 2.0f);
                    break;
                case 3:
                case 6:
                case 8:
                    yOffset = 0.0f - (_markerHeight - imageH);
                    break;
                default:
                    yOffset = 0.0f;
                    break;
            }
            float yOffset2 = angle;
            if (!this.e.a.G4.booleanValue()) {
                W0(xOffset, yOffset, _markerWidth, _markerHeight);
            }
            m.reset();
            m.preScale(xScale, yScale);
            this.b.concat(m);
        } else {
            m.preTranslate(-_refX, -_refY);
            this.b.concat(m);
            if (!this.e.a.G4.booleanValue()) {
                W0(0.0f, 0.0f, _markerWidth, _markerHeight);
            }
        }
        boolean compositing = u0();
        N0(rVar, false);
        if (compositing) {
            r0(marker);
        }
        Z0();
    }

    private C0053h U(g.n0 obj) {
        C0053h newState = new C0053h();
        d1(newState, g.e0.a());
        return V(obj, newState);
    }

    private C0053h V(g.n0 obj, C0053h newState) {
        List<SVG.SvgElementBase> ancestors = new ArrayList<>();
        while (true) {
            if (obj instanceof g.l0) {
                ancestors.add(0, (g.l0) obj);
            }
            g.j0 j0Var = obj.b;
            if (j0Var == null) {
                break;
            }
            obj = (g.n0) j0Var;
        }
        Iterator<SVG.SvgElementBase> it = ancestors.iterator();
        while (it.hasNext()) {
            e1(newState, (g.l0) it.next());
        }
        C0053h hVar = this.e;
        newState.g = hVar.g;
        newState.f = hVar.f;
        return newState;
    }

    private void x(g.k0 obj) {
        g.o0 o0Var = this.e.a.d;
        if (o0Var instanceof g.u) {
            H(true, obj.h, (g.u) o0Var);
        }
        g.o0 o0Var2 = this.e.a.x;
        if (o0Var2 instanceof g.u) {
            H(false, obj.h, (g.u) o0Var2);
        }
    }

    private void H(boolean isFill, g.b boundingBox, g.u paintref) {
        g.n0 ref = this.d.v(paintref.c);
        if (ref == null) {
            Object[] objArr = new Object[2];
            objArr[0] = isFill ? "Fill" : "Stroke";
            objArr[1] = paintref.c;
            N("%s reference '%s' not found", objArr);
            g.o0 o0Var = paintref.d;
            if (o0Var != null) {
                X0(this.e, isFill, o0Var);
            } else if (isFill) {
                this.e.b = false;
            } else {
                this.e.c = false;
            }
        } else if (ref instanceof g.m0) {
            f0(isFill, boundingBox, (g.m0) ref);
        } else if (ref instanceof g.q0) {
            m0(isFill, boundingBox, (g.q0) ref);
        } else if (ref instanceof g.c0) {
            Y0(isFill, (g.c0) ref);
        }
    }

    private void f0(boolean isFill, g.b boundingBox, g.m0 gradient) {
        float _x2;
        float _y1;
        float _x1;
        float _x12;
        g.b bVar = boundingBox;
        g.m0 m0Var = gradient;
        String str = m0Var.l;
        if (str != null) {
            P(m0Var, str);
        }
        Boolean bool = m0Var.i;
        boolean userUnits = bool != null && bool.booleanValue();
        C0053h hVar = this.e;
        Paint paint = isFill ? hVar.d : hVar.e;
        if (userUnits) {
            g.b viewPortUser = a0();
            g.p pVar = m0Var.m;
            _x1 = pVar != null ? pVar.e(this) : 0.0f;
            g.p pVar2 = m0Var.n;
            _y1 = pVar2 != null ? pVar2.f(this) : 0.0f;
            g.p pVar3 = m0Var.o;
            _x2 = pVar3 != null ? pVar3.e(this) : viewPortUser.c;
            g.p pVar4 = m0Var.p;
            _x12 = pVar4 != null ? pVar4.f(this) : 0.0f;
        } else {
            g.p pVar5 = m0Var.m;
            float _x13 = pVar5 != null ? pVar5.d(this, 1.0f) : 0.0f;
            g.p pVar6 = m0Var.n;
            _y1 = pVar6 != null ? pVar6.d(this, 1.0f) : 0.0f;
            g.p pVar7 = m0Var.o;
            _x2 = pVar7 != null ? pVar7.d(this, 1.0f) : 1.0f;
            g.p pVar8 = m0Var.p;
            _x1 = _x13;
            _x12 = pVar8 != null ? pVar8.d(this, 1.0f) : 0.0f;
        }
        a1();
        this.e = U(m0Var);
        Matrix m = new Matrix();
        if (!userUnits) {
            m.preTranslate(bVar.a, bVar.b);
            m.preScale(bVar.c, bVar.d);
        }
        Matrix matrix = m0Var.j;
        if (matrix != null) {
            m.preConcat(matrix);
        }
        int numStops = m0Var.h.size();
        if (numStops == 0) {
            Z0();
            if (isFill) {
                this.e.b = false;
            } else {
                this.e.c = false;
            }
        } else {
            int[] colours = new int[numStops];
            float[] positions = new float[numStops];
            Iterator<g.n0> it = m0Var.h.iterator();
            int i2 = 0;
            float lastOffset = -1.0f;
            while (it.hasNext() != 0) {
                g.d0 stop = (g.d0) it.next();
                Float f2 = stop.h;
                float offset = f2 != null ? f2.floatValue() : 0.0f;
                if (i2 == 0 || offset >= lastOffset) {
                    positions[i2] = offset;
                    lastOffset = offset;
                } else {
                    positions[i2] = lastOffset;
                }
                a1();
                float f3 = offset;
                e1(this.e, stop);
                g.e0 e0Var = this.e.a;
                boolean userUnits2 = userUnits;
                g.f col = (g.f) e0Var.N4;
                if (col == null) {
                    col = g.f.c;
                }
                g.d0 d0Var = stop;
                colours[i2] = F(col.f, e0Var.O4.floatValue());
                i2++;
                Z0();
                g.b bVar2 = boundingBox;
                userUnits = userUnits2;
            }
            if ((_x1 == _x2 && _y1 == _x12) || numStops == 1) {
                Z0();
                paint.setColor(colours[numStops - 1]);
                return;
            }
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            g.k kVar = m0Var.k;
            if (kVar != null) {
                if (kVar == g.k.reflect) {
                    tileMode = Shader.TileMode.MIRROR;
                } else if (kVar == g.k.repeat) {
                    tileMode = Shader.TileMode.REPEAT;
                }
            }
            Z0();
            int i3 = numStops;
            LinearGradient linearGradient = new LinearGradient(_x1, _y1, _x2, _x12, colours, positions, tileMode);
            linearGradient.setLocalMatrix(m);
            paint.setShader(linearGradient);
            paint.setAlpha(C(this.e.a.q.floatValue()));
        }
    }

    private void m0(boolean isFill, g.b boundingBox, g.q0 gradient) {
        float _cy;
        float _cx;
        float _cx2;
        int numStops;
        g.b bVar = boundingBox;
        g.q0 q0Var = gradient;
        String str = q0Var.l;
        if (str != null) {
            P(q0Var, str);
        }
        Boolean bool = q0Var.i;
        boolean userUnits = bool != null && bool.booleanValue();
        C0053h hVar = this.e;
        Paint paint = isFill ? hVar.d : hVar.e;
        if (userUnits) {
            g.p fiftyPercent = new g.p(50.0f, g.d1.percent);
            g.p pVar = q0Var.m;
            _cx = pVar != null ? pVar.e(this) : fiftyPercent.e(this);
            g.p pVar2 = q0Var.n;
            _cy = pVar2 != null ? pVar2.f(this) : fiftyPercent.f(this);
            g.p pVar3 = q0Var.o;
            _cx2 = pVar3 != null ? pVar3.c(this) : fiftyPercent.c(this);
        } else {
            g.p pVar4 = q0Var.m;
            float f2 = 0.5f;
            float _cx3 = pVar4 != null ? pVar4.d(this, 1.0f) : 0.5f;
            g.p pVar5 = q0Var.n;
            float _cy2 = pVar5 != null ? pVar5.d(this, 1.0f) : 0.5f;
            g.p pVar6 = q0Var.o;
            if (pVar6 != null) {
                f2 = pVar6.d(this, 1.0f);
            }
            float f3 = f2;
            _cy = _cy2;
            float f4 = f3;
            _cx = _cx3;
            _cx2 = f4;
        }
        a1();
        this.e = U(q0Var);
        Matrix m = new Matrix();
        if (!userUnits) {
            m.preTranslate(bVar.a, bVar.b);
            m.preScale(bVar.c, bVar.d);
        }
        Matrix matrix = q0Var.j;
        if (matrix != null) {
            m.preConcat(matrix);
        }
        int numStops2 = q0Var.h.size();
        if (numStops2 == 0) {
            Z0();
            if (isFill) {
                this.e.b = false;
            } else {
                this.e.c = false;
            }
        } else {
            int[] colours = new int[numStops2];
            float[] positions = new float[numStops2];
            Iterator<g.n0> it = q0Var.h.iterator();
            int i2 = 0;
            float lastOffset = -1.0f;
            while (it.hasNext() != 0) {
                g.d0 stop = (g.d0) it.next();
                Float f5 = stop.h;
                float offset = f5 != null ? f5.floatValue() : 0.0f;
                if (i2 == 0 || offset >= lastOffset) {
                    positions[i2] = offset;
                    lastOffset = offset;
                } else {
                    positions[i2] = lastOffset;
                }
                a1();
                e1(this.e, stop);
                g.e0 e0Var = this.e.a;
                boolean userUnits2 = userUnits;
                g.f col = (g.f) e0Var.N4;
                if (col == null) {
                    col = g.f.c;
                }
                g.d0 d0Var = stop;
                colours[i2] = F(col.f, e0Var.O4.floatValue());
                i2++;
                Z0();
                g.b bVar2 = boundingBox;
                userUnits = userUnits2;
            }
            if (_cx2 == 0.0f) {
                numStops = numStops2;
            } else if (numStops2 == 1) {
                float[] fArr = positions;
                numStops = numStops2;
            } else {
                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                g.k kVar = q0Var.k;
                if (kVar != null) {
                    if (kVar == g.k.reflect) {
                        tileMode = Shader.TileMode.MIRROR;
                    } else if (kVar == g.k.repeat) {
                        tileMode = Shader.TileMode.REPEAT;
                    }
                }
                Z0();
                int i3 = numStops2;
                RadialGradient radialGradient = new RadialGradient(_cx, _cy, _cx2, colours, positions, tileMode);
                radialGradient.setLocalMatrix(m);
                paint.setShader(radialGradient);
                paint.setAlpha(C(this.e.a.q.floatValue()));
                return;
            }
            Z0();
            paint.setColor(colours[numStops - 1]);
        }
    }

    private void P(g.j gradient, String href) {
        g.n0 ref = gradient.a.v(href);
        if (ref == null) {
            h1("Gradient reference '%s' not found", href);
        } else if (!(ref instanceof g.j)) {
            N("Gradient href attributes must point to other gradient elements", new Object[0]);
        } else if (ref == gradient) {
            N("Circular reference in gradient href attribute '%s'", href);
        } else {
            g.j grRef = (g.j) ref;
            if (gradient.i == null) {
                gradient.i = grRef.i;
            }
            if (gradient.j == null) {
                gradient.j = grRef.j;
            }
            if (gradient.k == null) {
                gradient.k = grRef.k;
            }
            if (gradient.h.isEmpty()) {
                gradient.h = grRef.h;
            }
            try {
                if (gradient instanceof g.m0) {
                    Q((g.m0) gradient, (g.m0) ref);
                } else {
                    R((g.q0) gradient, (g.q0) ref);
                }
            } catch (ClassCastException e2) {
            }
            String str = grRef.l;
            if (str != null) {
                P(gradient, str);
            }
        }
    }

    private void Q(g.m0 gradient, g.m0 grRef) {
        if (gradient.m == null) {
            gradient.m = grRef.m;
        }
        if (gradient.n == null) {
            gradient.n = grRef.n;
        }
        if (gradient.o == null) {
            gradient.o = grRef.o;
        }
        if (gradient.p == null) {
            gradient.p = grRef.p;
        }
    }

    private void R(g.q0 gradient, g.q0 grRef) {
        if (gradient.m == null) {
            gradient.m = grRef.m;
        }
        if (gradient.n == null) {
            gradient.n = grRef.n;
        }
        if (gradient.o == null) {
            gradient.o = grRef.o;
        }
        if (gradient.p == null) {
            gradient.p = grRef.p;
        }
        if (gradient.q == null) {
            gradient.q = grRef.q;
        }
    }

    private void Y0(boolean isFill, g.c0 ref) {
        boolean z = true;
        if (isFill) {
            if (e0(ref.e, IjkMediaMeta.AV_CH_WIDE_LEFT)) {
                C0053h hVar = this.e;
                g.e0 e0Var = hVar.a;
                g.o0 o0Var = ref.e.S4;
                e0Var.d = o0Var;
                if (o0Var == null) {
                    z = false;
                }
                hVar.b = z;
            }
            if (e0(ref.e, IjkMediaMeta.AV_CH_WIDE_RIGHT)) {
                this.e.a.q = ref.e.T4;
            }
            if (e0(ref.e, 6442450944L)) {
                C0053h hVar2 = this.e;
                X0(hVar2, isFill, hVar2.a.d);
                return;
            }
            return;
        }
        if (e0(ref.e, IjkMediaMeta.AV_CH_WIDE_LEFT)) {
            C0053h hVar3 = this.e;
            g.e0 e0Var2 = hVar3.a;
            g.o0 o0Var2 = ref.e.S4;
            e0Var2.x = o0Var2;
            if (o0Var2 == null) {
                z = false;
            }
            hVar3.c = z;
        }
        if (e0(ref.e, IjkMediaMeta.AV_CH_WIDE_RIGHT)) {
            this.e.a.y = ref.e.T4;
        }
        if (e0(ref.e, 6442450944L)) {
            C0053h hVar4 = this.e;
            X0(hVar4, isFill, hVar4.a.x);
        }
    }

    private void u(g.k0 obj) {
        v(obj, obj.h);
    }

    private void v(g.k0 obj, g.b boundingBox) {
        if (this.e.a.P4 != null) {
            if (Build.VERSION.SDK_INT >= 19) {
                Path combinedPath = o(obj, boundingBox);
                if (combinedPath != null) {
                    this.b.clipPath(combinedPath);
                    return;
                }
                return;
            }
            w(obj, boundingBox);
        }
    }

    @TargetApi(19)
    private Path o(g.k0 obj, g.b boundingBox) {
        Path part;
        g.n0 ref = obj.a.v(this.e.a.P4);
        boolean userUnits = false;
        if (ref == null) {
            N("ClipPath reference '%s' not found", this.e.a.P4);
            return null;
        }
        g.e clipPath = (g.e) ref;
        this.f.push(this.e);
        this.e = U(clipPath);
        Boolean bool = clipPath.p;
        if (bool == null || bool.booleanValue()) {
            userUnits = true;
        }
        Matrix m = new Matrix();
        if (!userUnits) {
            m.preTranslate(boundingBox.a, boundingBox.b);
            m.preScale(boundingBox.c, boundingBox.d);
        }
        Matrix matrix = clipPath.o;
        if (matrix != null) {
            m.preConcat(matrix);
        }
        Path combinedPath = new Path();
        for (g.n0 child : clipPath.i) {
            if ((child instanceof g.k0) && (part = o0((g.k0) child, true)) != null) {
                combinedPath.op(part, Path.Op.UNION);
            }
        }
        if (this.e.a.P4 != null) {
            if (clipPath.h == null) {
                clipPath.h = r(combinedPath);
            }
            Path clipClipPath = o(clipPath, clipPath.h);
            if (clipClipPath != null) {
                combinedPath.op(clipClipPath, Path.Op.INTERSECT);
            }
        }
        combinedPath.transform(m);
        this.e = this.f.pop();
        return combinedPath;
    }

    @TargetApi(19)
    private Path o0(g.k0 obj, boolean allowUse) {
        Path childsClipPath;
        this.f.push(this.e);
        C0053h hVar = new C0053h(this.e);
        this.e = hVar;
        e1(hVar, obj);
        if (!I() || !g1()) {
            this.e = this.f.pop();
            return null;
        }
        Path path = null;
        if (obj instanceof g.e1) {
            if (!allowUse) {
                N("<use> elements inside a <clipPath> cannot reference another <use>", new Object[0]);
            }
            g.e1 useElement = (g.e1) obj;
            g.n0 ref = obj.a.v(useElement.p);
            if (ref == null) {
                N("Use reference '%s' not found", useElement.p);
                this.e = this.f.pop();
                return null;
            } else if (!(ref instanceof g.k0)) {
                this.e = this.f.pop();
                return null;
            } else {
                path = o0((g.k0) ref, false);
                if (path == null) {
                    return null;
                }
                if (useElement.h == null) {
                    useElement.h = r(path);
                }
                Matrix matrix = useElement.o;
                if (matrix != null) {
                    path.transform(matrix);
                }
            }
        } else if (obj instanceof g.l) {
            g.l elem = (g.l) obj;
            if (obj instanceof g.v) {
                path = new d(((g.v) obj).o).f();
                if (obj.h == null) {
                    obj.h = r(path);
                }
            } else if (obj instanceof g.b0) {
                path = k0((g.b0) obj);
            } else if (obj instanceof g.d) {
                path = g0((g.d) obj);
            } else if (obj instanceof g.i) {
                path = h0((g.i) obj);
            } else if (obj instanceof g.z) {
                path = j0((g.z) obj);
            }
            if (path == null) {
                return null;
            }
            if (elem.h == null) {
                elem.h = r(path);
            }
            Matrix matrix2 = elem.n;
            if (matrix2 != null) {
                path.transform(matrix2);
            }
            path.setFillType(X());
        } else if (obj instanceof g.w0) {
            g.w0 textElem = (g.w0) obj;
            path = l0(textElem);
            if (path == null) {
                return null;
            }
            Matrix matrix3 = textElem.s;
            if (matrix3 != null) {
                path.transform(matrix3);
            }
            path.setFillType(X());
        } else {
            N("Invalid %s element found in clipPath definition", obj.m());
            return null;
        }
        if (!(this.e.a.P4 == null || (childsClipPath = o(obj, obj.h)) == null)) {
            path.op(childsClipPath, Path.Op.INTERSECT);
        }
        this.e = this.f.pop();
        return path;
    }

    private void w(g.k0 obj, g.b boundingBox) {
        g.n0 ref = obj.a.v(this.e.a.P4);
        if (ref == null) {
            N("ClipPath reference '%s' not found", this.e.a.P4);
            return;
        }
        g.e clipPath = (g.e) ref;
        if (clipPath.i.isEmpty()) {
            this.b.clipRect(0, 0, 0, 0);
            return;
        }
        Boolean bool = clipPath.p;
        boolean userUnits = bool == null || bool.booleanValue();
        if (!(obj instanceof g.m) || userUnits) {
            E();
            if (!userUnits) {
                Matrix m = new Matrix();
                m.preTranslate(boundingBox.a, boundingBox.b);
                m.preScale(boundingBox.c, boundingBox.d);
                this.b.concat(m);
            }
            Matrix m2 = clipPath.o;
            if (m2 != null) {
                this.b.concat(m2);
            }
            this.e = U(clipPath);
            u(clipPath);
            Path combinedPath = new Path();
            for (g.n0 child : clipPath.i) {
                j(child, true, combinedPath, new Matrix());
            }
            this.b.clipPath(combinedPath);
            D();
            return;
        }
        h1("<clipPath clipPathUnits=\"objectBoundingBox\"> is not supported when referenced from container elements (like %s)", obj.m());
    }

    private void j(g.n0 obj, boolean allowUse, Path combinedPath, Matrix combinedPathMatrix) {
        if (I()) {
            E();
            if (obj instanceof g.e1) {
                if (allowUse) {
                    l((g.e1) obj, combinedPath, combinedPathMatrix);
                } else {
                    N("<use> elements inside a <clipPath> cannot reference another <use>", new Object[0]);
                }
            } else if (obj instanceof g.v) {
                i((g.v) obj, combinedPath, combinedPathMatrix);
            } else if (obj instanceof g.w0) {
                k((g.w0) obj, combinedPath, combinedPathMatrix);
            } else if (obj instanceof g.l) {
                h((g.l) obj, combinedPath, combinedPathMatrix);
            } else {
                N("Invalid %s element found in clipPath definition", obj.toString());
            }
            D();
        }
    }

    private void E() {
        b.a(this.b, b.a);
        this.f.push(this.e);
        this.e = new C0053h(this.e);
    }

    private void D() {
        this.b.restore();
        this.e = this.f.pop();
    }

    private Path.FillType X() {
        g.e0.a aVar = this.e.a.Q4;
        if (aVar == null || aVar != g.e0.a.EvenOdd) {
            return Path.FillType.WINDING;
        }
        return Path.FillType.EVEN_ODD;
    }

    private void i(g.v obj, Path combinedPath, Matrix combinedPathMatrix) {
        e1(this.e, obj);
        if (I() && g1()) {
            Matrix matrix = obj.n;
            if (matrix != null) {
                combinedPathMatrix.preConcat(matrix);
            }
            Path path = new d(obj.o).f();
            if (obj.h == null) {
                obj.h = r(path);
            }
            u(obj);
            combinedPath.setFillType(X());
            combinedPath.addPath(path, combinedPathMatrix);
        }
    }

    private void h(g.l obj, Path combinedPath, Matrix combinedPathMatrix) {
        Path path;
        e1(this.e, obj);
        if (I() && g1()) {
            Matrix matrix = obj.n;
            if (matrix != null) {
                combinedPathMatrix.preConcat(matrix);
            }
            if (obj instanceof g.b0) {
                path = k0((g.b0) obj);
            } else if (obj instanceof g.d) {
                path = g0((g.d) obj);
            } else if (obj instanceof g.i) {
                path = h0((g.i) obj);
            } else if (obj instanceof g.z) {
                path = j0((g.z) obj);
            } else {
                return;
            }
            u(obj);
            combinedPath.setFillType(X());
            combinedPath.addPath(path, combinedPathMatrix);
        }
    }

    private void l(g.e1 obj, Path combinedPath, Matrix combinedPathMatrix) {
        e1(this.e, obj);
        if (I() && g1()) {
            Matrix matrix = obj.o;
            if (matrix != null) {
                combinedPathMatrix.preConcat(matrix);
            }
            g.n0 ref = obj.a.v(obj.p);
            if (ref == null) {
                N("Use reference '%s' not found", obj.p);
                return;
            }
            u(obj);
            j(ref, false, combinedPath, combinedPathMatrix);
        }
    }

    private void k(g.w0 obj, Path combinedPath, Matrix combinedPathMatrix) {
        e1(this.e, obj);
        if (I()) {
            Matrix matrix = obj.s;
            if (matrix != null) {
                combinedPathMatrix.preConcat(matrix);
            }
            List<g.p> list = obj.o;
            float f2 = 0.0f;
            float x = (list == null || list.size() == 0) ? 0.0f : obj.o.get(0).e(this);
            List<g.p> list2 = obj.p;
            float y = (list2 == null || list2.size() == 0) ? 0.0f : obj.p.get(0).f(this);
            List<g.p> list3 = obj.q;
            float dx = (list3 == null || list3.size() == 0) ? 0.0f : obj.q.get(0).e(this);
            List<g.p> list4 = obj.r;
            if (!(list4 == null || list4.size() == 0)) {
                f2 = obj.r.get(0).f(this);
            }
            float dy = f2;
            if (this.e.a.F4 != g.e0.f.Start) {
                float textWidth = s(obj);
                if (this.e.a.F4 == g.e0.f.Middle) {
                    x -= textWidth / 2.0f;
                } else {
                    x -= textWidth;
                }
            }
            if (obj.h == null) {
                i proc = new i(x, y);
                M(obj, proc);
                RectF rectF = proc.d;
                obj.h = new g.b(rectF.left, rectF.top, rectF.width(), proc.d.height());
            }
            u(obj);
            Path textAsPath = new Path();
            M(obj, new g(x + dx, y + dy, textAsPath));
            combinedPath.setFillType(X());
            combinedPath.addPath(textAsPath, combinedPathMatrix);
        }
    }

    /* compiled from: SVGAndroidRenderer */
    public class g extends j {
        float b;
        float c;
        Path d;

        g(float x, float y, Path textAsPath) {
            super(h.this, (a) null);
            this.b = x;
            this.c = y;
            this.d = textAsPath;
        }

        public boolean a(g.y0 obj) {
            if (!(obj instanceof g.z0)) {
                return true;
            }
            h.h1("Using <textPath> elements in a clip path is not supported.", new Object[0]);
            return false;
        }

        public void b(String text) {
            if (h.this.g1()) {
                Path spanPath = new Path();
                h.this.e.d.getTextPath(text, 0, text.length(), this.b, this.c, spanPath);
                this.d.addPath(spanPath);
            }
            this.b += h.this.e.d.measureText(text);
        }
    }

    private Path i0(g.q obj) {
        g.p pVar = obj.o;
        float y2 = 0.0f;
        float x1 = pVar == null ? 0.0f : pVar.e(this);
        g.p pVar2 = obj.p;
        float y1 = pVar2 == null ? 0.0f : pVar2.f(this);
        g.p pVar3 = obj.q;
        float x2 = pVar3 == null ? 0.0f : pVar3.e(this);
        g.p pVar4 = obj.r;
        if (pVar4 != null) {
            y2 = pVar4.f(this);
        }
        if (obj.h == null) {
            obj.h = new g.b(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        }
        Path p = new Path();
        p.moveTo(x1, y1);
        p.lineTo(x2, y2);
        return p;
    }

    private Path k0(g.b0 obj) {
        float ry;
        float rx;
        Path p;
        float w;
        float bottom;
        g.b0 b0Var = obj;
        g.p pVar = b0Var.s;
        if (pVar == null && b0Var.t == null) {
            rx = 0.0f;
            ry = 0.0f;
        } else if (pVar == null) {
            rx = b0Var.t.f(this);
            ry = rx;
        } else if (b0Var.t == null) {
            rx = pVar.e(this);
            ry = rx;
        } else {
            rx = pVar.e(this);
            ry = b0Var.t.f(this);
        }
        float rx2 = Math.min(rx, b0Var.q.e(this) / 2.0f);
        float ry2 = Math.min(ry, b0Var.r.f(this) / 2.0f);
        g.p pVar2 = b0Var.o;
        float x = pVar2 != null ? pVar2.e(this) : 0.0f;
        g.p pVar3 = b0Var.p;
        float y = pVar3 != null ? pVar3.f(this) : 0.0f;
        float w2 = b0Var.q.e(this);
        float h2 = b0Var.r.f(this);
        if (b0Var.h == null) {
            b0Var.h = new g.b(x, y, w2, h2);
        }
        float right = x + w2;
        float bottom2 = y + h2;
        Path p2 = new Path();
        if (rx2 == 0.0f) {
            p = p2;
            bottom = bottom2;
            float f2 = h2;
            float f3 = w2;
            w = right;
        } else if (ry2 == 0.0f) {
            p = p2;
            bottom = bottom2;
            float f4 = h2;
            float f5 = w2;
            w = right;
        } else {
            float cpx = rx2 * 0.5522848f;
            float cpy = 0.5522848f * ry2;
            p2.moveTo(x, y + ry2);
            p = p2;
            float bottom3 = bottom2;
            float f6 = w2;
            float w3 = right;
            p2.cubicTo(x, (y + ry2) - cpy, (x + rx2) - cpx, y, x + rx2, y);
            p.lineTo(w3 - rx2, y);
            float f7 = h2;
            p.cubicTo((w3 - rx2) + cpx, y, w3, (y + ry2) - cpy, w3, y + ry2);
            p.lineTo(w3, bottom3 - ry2);
            p.cubicTo(w3, (bottom3 - ry2) + cpy, (w3 - rx2) + cpx, bottom3, w3 - rx2, bottom3);
            p.lineTo(x + rx2, bottom3);
            p.cubicTo((x + rx2) - cpx, bottom3, x, (bottom3 - ry2) + cpy, x, bottom3 - ry2);
            p.lineTo(x, y + ry2);
            p.close();
            return p;
        }
        p.moveTo(x, y);
        p.lineTo(w, y);
        p.lineTo(w, bottom);
        p.lineTo(x, bottom);
        p.lineTo(x, y);
        p.close();
        return p;
    }

    private Path g0(g.d obj) {
        g.d dVar = obj;
        g.p pVar = dVar.o;
        float cy = 0.0f;
        float cx = pVar != null ? pVar.e(this) : 0.0f;
        g.p pVar2 = dVar.p;
        if (pVar2 != null) {
            cy = pVar2.f(this);
        }
        float r = dVar.q.c(this);
        float left = cx - r;
        float top = cy - r;
        float right = cx + r;
        float bottom = cy + r;
        if (dVar.h == null) {
            dVar.h = new g.b(left, top, r * 2.0f, 2.0f * r);
        }
        float cp = r * 0.5522848f;
        Path path = new Path();
        Path p = path;
        p.moveTo(cx, top);
        Path p2 = p;
        path.cubicTo(cx + cp, top, right, cy - cp, right, cy);
        Path path2 = p2;
        path2.cubicTo(right, cy + cp, cx + cp, bottom, cx, bottom);
        path2.cubicTo(cx - cp, bottom, left, cy + cp, left, cy);
        path2.cubicTo(left, cy - cp, cx - cp, top, cx, top);
        p2.close();
        return p2;
    }

    private Path h0(g.i obj) {
        g.i iVar = obj;
        g.p pVar = iVar.o;
        float cy = 0.0f;
        float cx = pVar != null ? pVar.e(this) : 0.0f;
        g.p pVar2 = iVar.p;
        if (pVar2 != null) {
            cy = pVar2.f(this);
        }
        float rx = iVar.q.e(this);
        float ry = iVar.r.f(this);
        float left = cx - rx;
        float top = cy - ry;
        float right = cx + rx;
        float bottom = cy + ry;
        if (iVar.h == null) {
            iVar.h = new g.b(left, top, rx * 2.0f, 2.0f * ry);
        }
        float cpx = rx * 0.5522848f;
        float cpy = ry * 0.5522848f;
        Path path = new Path();
        Path p = path;
        p.moveTo(cx, top);
        Path p2 = p;
        path.cubicTo(cx + cpx, top, right, cy - cpy, right, cy);
        Path path2 = p2;
        path2.cubicTo(right, cy + cpy, cx + cpx, bottom, cx, bottom);
        path2.cubicTo(cx - cpx, bottom, left, cy + cpy, left, cy);
        path2.cubicTo(left, cy - cpy, cx - cpx, top, cx, top);
        p2.close();
        return p2;
    }

    private Path j0(g.z obj) {
        Path path = new Path();
        float[] fArr = obj.o;
        path.moveTo(fArr[0], fArr[1]);
        int i2 = 2;
        while (true) {
            float[] fArr2 = obj.o;
            if (i2 >= fArr2.length) {
                break;
            }
            path.lineTo(fArr2[i2], fArr2[i2 + 1]);
            i2 += 2;
        }
        if ((obj instanceof g.a0) != 0) {
            path.close();
        }
        if (obj.h == null) {
            obj.h = r(path);
        }
        return path;
    }

    private Path l0(g.w0 obj) {
        List<g.p> list = obj.o;
        float f2 = 0.0f;
        float x = (list == null || list.size() == 0) ? 0.0f : obj.o.get(0).e(this);
        List<g.p> list2 = obj.p;
        float y = (list2 == null || list2.size() == 0) ? 0.0f : obj.p.get(0).f(this);
        List<g.p> list3 = obj.q;
        float dx = (list3 == null || list3.size() == 0) ? 0.0f : obj.q.get(0).e(this);
        List<g.p> list4 = obj.r;
        if (!(list4 == null || list4.size() == 0)) {
            f2 = obj.r.get(0).f(this);
        }
        float dy = f2;
        if (this.e.a.F4 != g.e0.f.Start) {
            float textWidth = s(obj);
            if (this.e.a.F4 == g.e0.f.Middle) {
                x -= textWidth / 2.0f;
            } else {
                x -= textWidth;
            }
        }
        if (obj.h == null) {
            i proc = new i(x, y);
            M(obj, proc);
            RectF rectF = proc.d;
            obj.h = new g.b(rectF.left, rectF.top, rectF.width(), proc.d.height());
        }
        Path textAsPath = new Path();
        M(obj, new g(x + dx, y + dy, textAsPath));
        return textAsPath;
    }

    private void T(g.k0 obj, Path path, g.y pattern) {
        float w;
        float w2;
        float y;
        float x;
        boolean z;
        float y2;
        float x2;
        float bottom;
        g.k0 k0Var = obj;
        g.y yVar = pattern;
        Boolean bool = yVar.q;
        boolean patternUnitsAreUser = bool != null && bool.booleanValue();
        String str = yVar.x;
        if (str != null) {
            S(yVar, str);
        }
        if (patternUnitsAreUser) {
            g.p pVar = yVar.t;
            x = pVar != null ? pVar.e(this) : 0.0f;
            g.p pVar2 = yVar.u;
            y = pVar2 != null ? pVar2.f(this) : 0.0f;
            g.p pVar3 = yVar.v;
            w2 = pVar3 != null ? pVar3.e(this) : 0.0f;
            g.p pVar4 = yVar.w;
            w = pVar4 != null ? pVar4.f(this) : 0.0f;
        } else {
            g.p pVar5 = yVar.t;
            float x3 = pVar5 != null ? pVar5.d(this, 1.0f) : 0.0f;
            g.p pVar6 = yVar.u;
            float y3 = pVar6 != null ? pVar6.d(this, 1.0f) : 0.0f;
            g.p pVar7 = yVar.v;
            float w3 = pVar7 != null ? pVar7.d(this, 1.0f) : 0.0f;
            g.p pVar8 = yVar.w;
            float h2 = pVar8 != null ? pVar8.d(this, 1.0f) : 0.0f;
            g.b bVar = k0Var.h;
            float f2 = bVar.a;
            float f3 = bVar.c;
            x = f2 + (x3 * f3);
            float f4 = bVar.b;
            float f5 = bVar.d;
            float f6 = w3 * f3;
            w = h2 * f5;
            y = f4 + (y3 * f5);
            w2 = f6;
        }
        if (w2 == 0.0f) {
            Path path2 = path;
            boolean z2 = patternUnitsAreUser;
            float f7 = x;
            float f8 = y;
        } else if (w == 0.0f) {
            Path path3 = path;
            boolean z3 = patternUnitsAreUser;
            float f9 = x;
            float f10 = y;
        } else {
            e positioning = yVar.o;
            if (positioning == null) {
                positioning = e.c;
            }
            a1();
            this.b.clipPath(path);
            C0053h baseState = new C0053h();
            d1(baseState, g.e0.a());
            baseState.a.G4 = false;
            this.e = V(yVar, baseState);
            g.b patternArea = k0Var.h;
            Matrix matrix = yVar.s;
            if (matrix != null) {
                this.b.concat(matrix);
                Matrix inverse = new Matrix();
                if (yVar.s.invert(inverse)) {
                    g.b bVar2 = k0Var.h;
                    boolean z4 = patternUnitsAreUser;
                    g.b bVar3 = k0Var.h;
                    g.b bVar4 = k0Var.h;
                    float[] pts = {bVar2.a, bVar2.b, bVar2.b(), bVar3.b, bVar3.b(), k0Var.h.c(), bVar4.a, bVar4.c()};
                    inverse.mapPoints(pts);
                    Matrix matrix2 = inverse;
                    z = true;
                    C0053h hVar = baseState;
                    RectF rect = new RectF(pts[0], pts[1], pts[0], pts[1]);
                    for (int i2 = 2; i2 <= 6; i2 += 2) {
                        if (pts[i2] < rect.left) {
                            rect.left = pts[i2];
                        }
                        if (pts[i2] > rect.right) {
                            rect.right = pts[i2];
                        }
                        if (pts[i2 + 1] < rect.top) {
                            rect.top = pts[i2 + 1];
                        }
                        if (pts[i2 + 1] > rect.bottom) {
                            rect.bottom = pts[i2 + 1];
                        }
                    }
                    float f11 = rect.left;
                    float f12 = rect.top;
                    float[] fArr = pts;
                    patternArea = new g.b(f11, f12, rect.right - f11, rect.bottom - f12);
                } else {
                    Matrix matrix3 = inverse;
                    C0053h hVar2 = baseState;
                    z = true;
                }
            } else {
                C0053h hVar3 = baseState;
                z = true;
            }
            float stepX = (((float) Math.floor((double) ((patternArea.a - x) / w2))) * w2) + x;
            float stepY = (((float) Math.floor((double) ((patternArea.b - y) / w))) * w) + y;
            float right = patternArea.b();
            float bottom2 = patternArea.c();
            g.b stepViewBox = new g.b(0.0f, 0.0f, w2, w);
            boolean compositing = u0();
            float f13 = stepY;
            float f14 = stepY;
            while (stepY < bottom2) {
                float f15 = stepX;
                float originX = stepX;
                while (stepX < right) {
                    stepViewBox.a = stepX;
                    stepViewBox.b = stepY;
                    a1();
                    float right2 = right;
                    if (!this.e.a.G4.booleanValue()) {
                        bottom = bottom2;
                        x2 = x;
                        y2 = y;
                        W0(stepViewBox.a, stepViewBox.b, stepViewBox.c, stepViewBox.d);
                    } else {
                        bottom = bottom2;
                        x2 = x;
                        y2 = y;
                    }
                    g.b bVar5 = yVar.p;
                    if (bVar5 != null) {
                        this.b.concat(t(stepViewBox, bVar5, positioning));
                    } else {
                        Boolean bool2 = yVar.r;
                        boolean patternContentUnitsAreUser = (bool2 == null || bool2.booleanValue()) ? z : false;
                        this.b.translate(stepX, stepY);
                        if (!patternContentUnitsAreUser) {
                            Canvas canvas = this.b;
                            g.b bVar6 = k0Var.h;
                            canvas.scale(bVar6.c, bVar6.d);
                        }
                    }
                    for (g.n0 child : yVar.i) {
                        I0(child);
                    }
                    Z0();
                    stepX += w2;
                    right = right2;
                    bottom2 = bottom;
                    x = x2;
                    y = y2;
                }
                float f16 = bottom2;
                float f17 = x;
                float f18 = y;
                stepY += w;
                stepX = originX;
            }
            float originX2 = stepX;
            float f19 = right;
            float f20 = bottom2;
            float f21 = x;
            float f22 = y;
            if (compositing) {
                r0(yVar);
            }
            Z0();
        }
    }

    private void S(g.y pattern, String href) {
        g.n0 ref = pattern.a.v(href);
        if (ref == null) {
            h1("Pattern reference '%s' not found", href);
        } else if (!(ref instanceof g.y)) {
            N("Pattern href attributes must point to other pattern elements", new Object[0]);
        } else if (ref == pattern) {
            N("Circular reference in pattern href attribute '%s'", href);
        } else {
            g.y pRef = (g.y) ref;
            if (pattern.q == null) {
                pattern.q = pRef.q;
            }
            if (pattern.r == null) {
                pattern.r = pRef.r;
            }
            if (pattern.s == null) {
                pattern.s = pRef.s;
            }
            if (pattern.t == null) {
                pattern.t = pRef.t;
            }
            if (pattern.u == null) {
                pattern.u = pRef.u;
            }
            if (pattern.v == null) {
                pattern.v = pRef.v;
            }
            if (pattern.w == null) {
                pattern.w = pRef.w;
            }
            if (pattern.i.isEmpty()) {
                pattern.i = pRef.i;
            }
            if (pattern.p == null) {
                pattern.p = pRef.p;
            }
            if (pattern.o == null) {
                pattern.o = pRef.o;
            }
            String str = pRef.x;
            if (str != null) {
                S(pattern, str);
            }
        }
    }

    private void R0(g.s mask, g.k0 obj, g.b originalObjBBox) {
        float h2;
        float w;
        G("Mask render", new Object[0]);
        Boolean bool = mask.o;
        boolean maskContentUnitsAreUser = true;
        if (bool != null && bool.booleanValue()) {
            g.p pVar = mask.s;
            w = pVar != null ? pVar.e(this) : originalObjBBox.c;
            g.p pVar2 = mask.t;
            h2 = pVar2 != null ? pVar2.f(this) : originalObjBBox.d;
        } else {
            g.p pVar3 = mask.s;
            float h3 = 1.2f;
            float w2 = pVar3 != null ? pVar3.d(this, 1.0f) : 1.2f;
            g.p pVar4 = mask.t;
            if (pVar4 != null) {
                h3 = pVar4.d(this, 1.0f);
            }
            w = w2 * originalObjBBox.c;
            h2 = h3 * originalObjBBox.d;
        }
        if (w != 0.0f && h2 != 0.0f) {
            a1();
            C0053h U = U(mask);
            this.e = U;
            U.a.p3 = Float.valueOf(1.0f);
            boolean compositing = u0();
            this.b.save();
            Boolean bool2 = mask.p;
            if (bool2 != null && !bool2.booleanValue()) {
                maskContentUnitsAreUser = false;
            }
            if (!maskContentUnitsAreUser) {
                this.b.translate(originalObjBBox.a, originalObjBBox.b);
                this.b.scale(originalObjBBox.c, originalObjBBox.d);
            }
            N0(mask, false);
            this.b.restore();
            if (compositing) {
                s0(obj, originalObjBBox);
            }
            Z0();
        }
    }
}
