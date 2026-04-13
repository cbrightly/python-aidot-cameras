package com.airbnb.lottie.model.layer;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.CallSuper;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.e;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.d;
import com.airbnb.lottie.animation.keyframe.h;
import com.airbnb.lottie.animation.keyframe.p;
import com.airbnb.lottie.b0;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.model.content.h;
import com.airbnb.lottie.model.content.n;
import com.airbnb.lottie.model.f;
import com.airbnb.lottie.model.layer.e;
import com.airbnb.lottie.parser.j;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: BaseLayer */
public abstract class b implements e, a.b, f {
    @Nullable
    private Paint A;
    float B;
    @Nullable
    BlurMaskFilter C;
    private final Path a = new Path();
    private final Matrix b = new Matrix();
    private final Matrix c = new Matrix();
    private final Paint d = new com.airbnb.lottie.animation.a(1);
    private final Paint e = new com.airbnb.lottie.animation.a(1, PorterDuff.Mode.DST_IN);
    private final Paint f = new com.airbnb.lottie.animation.a(1, PorterDuff.Mode.DST_OUT);
    private final Paint g;
    private final Paint h;
    private final RectF i;
    private final RectF j;
    private final RectF k;
    private final RectF l;
    private final RectF m;
    private final String n;
    final Matrix o;
    final e0 p;
    final e q;
    @Nullable
    private h r;
    @Nullable
    private d s;
    @Nullable
    private b t;
    @Nullable
    private b u;
    private List<b> v;
    private final List<com.airbnb.lottie.animation.keyframe.a<?, ?>> w;
    final p x;
    private boolean y;
    private boolean z;

    /* access modifiers changed from: package-private */
    public abstract void s(Canvas canvas, Matrix matrix, int i2);

    @Nullable
    static b t(c compositionLayer, e layerModel, e0 drawable, c0 composition) {
        switch (a.a[layerModel.f().ordinal()]) {
            case 1:
                return new g(drawable, layerModel, compositionLayer, composition);
            case 2:
                return new c(drawable, layerModel, composition.o(layerModel.m()), composition);
            case 3:
                return new h(drawable, layerModel);
            case 4:
                return new d(drawable, layerModel);
            case 5:
                return new f(drawable, layerModel);
            case 6:
                return new i(drawable, layerModel);
            default:
                com.airbnb.lottie.utils.d.c("Unknown layer type " + layerModel.f());
                return null;
        }
    }

    b(e0 lottieDrawable, e layerModel) {
        com.airbnb.lottie.animation.a aVar = new com.airbnb.lottie.animation.a(1);
        this.g = aVar;
        this.h = new com.airbnb.lottie.animation.a(PorterDuff.Mode.CLEAR);
        this.i = new RectF();
        this.j = new RectF();
        this.k = new RectF();
        this.l = new RectF();
        this.m = new RectF();
        this.o = new Matrix();
        this.w = new ArrayList();
        this.y = true;
        this.B = 0.0f;
        this.p = lottieDrawable;
        this.q = layerModel;
        this.n = layerModel.i() + "#draw";
        if (layerModel.h() == e.b.INVERT) {
            aVar.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        } else {
            aVar.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }
        p b2 = layerModel.w().b();
        this.x = b2;
        b2.b(this);
        if (layerModel.g() != null && !layerModel.g().isEmpty()) {
            h hVar = new h(layerModel.g());
            this.r = hVar;
            Iterator<com.airbnb.lottie.animation.keyframe.a<n, Path>> it = hVar.a().iterator();
            while (it.hasNext()) {
                ((com.airbnb.lottie.animation.keyframe.a) it.next()).a(this);
            }
            Iterator<com.airbnb.lottie.animation.keyframe.a<Integer, Integer>> it2 = this.r.c().iterator();
            while (it2.hasNext()) {
                BaseKeyframeAnimation<Integer, Integer> animation = (com.airbnb.lottie.animation.keyframe.a) it2.next();
                g(animation);
                animation.a(this);
            }
        }
        N();
    }

    /* access modifiers changed from: package-private */
    public void J(boolean outline) {
        if (outline && this.A == null) {
            this.A = new com.airbnb.lottie.animation.a();
        }
        this.z = outline;
    }

    public void a() {
        C();
    }

    /* access modifiers changed from: package-private */
    public e x() {
        return this.q;
    }

    /* access modifiers changed from: package-private */
    public void I(@Nullable b matteLayer) {
        this.t = matteLayer;
    }

    /* access modifiers changed from: package-private */
    public boolean z() {
        return this.t != null;
    }

    /* access modifiers changed from: package-private */
    public void K(@Nullable b parentLayer) {
        this.u = parentLayer;
    }

    private void N() {
        boolean z2 = true;
        if (!this.q.e().isEmpty()) {
            d dVar = new d(this.q.e());
            this.s = dVar;
            dVar.l();
            this.s.a(new a(this));
            if (((Float) this.s.h()).floatValue() != 1.0f) {
                z2 = false;
            }
            M(z2);
            g(this.s);
            return;
        }
        M(true);
    }

    /* access modifiers changed from: private */
    /* renamed from: D */
    public /* synthetic */ void E() {
        M(this.s.p() == 1.0f);
    }

    private void C() {
        this.p.invalidateSelf();
    }

    public void g(@Nullable com.airbnb.lottie.animation.keyframe.a<?, ?> newAnimation) {
        if (newAnimation != null) {
            this.w.add(newAnimation);
        }
    }

    public void G(com.airbnb.lottie.animation.keyframe.a<?, ?> animation) {
        this.w.remove(animation);
    }

    @CallSuper
    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        this.i.set(0.0f, 0.0f, 0.0f, 0.0f);
        q();
        this.o.set(parentMatrix);
        if (applyParents) {
            List<b> list = this.v;
            if (list != null) {
                for (int i2 = list.size() - 1; i2 >= 0; i2--) {
                    this.o.preConcat(this.v.get(i2).x.f());
                }
            } else {
                b bVar = this.u;
                if (bVar != null) {
                    this.o.preConcat(bVar.x.f());
                }
            }
        }
        this.o.preConcat(this.x.f());
    }

    public void h(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        Paint paint;
        Integer opacityValue;
        b0.a(this.n);
        if (!this.y || this.q.x()) {
            b0.b(this.n);
            return;
        }
        q();
        b0.a("Layer#parentMatrix");
        this.b.reset();
        this.b.set(parentMatrix);
        for (int i2 = this.v.size() - 1; i2 >= 0; i2--) {
            this.b.preConcat(this.v.get(i2).x.f());
        }
        b0.b("Layer#parentMatrix");
        int opacity = 100;
        BaseKeyframeAnimation<?, Integer> opacityAnimation = this.x.h();
        if (!(opacityAnimation == null || (opacityValue = opacityAnimation.h()) == null)) {
            opacity = opacityValue.intValue();
        }
        int alpha = (int) ((((((float) parentAlpha) / 255.0f) * ((float) opacity)) / 100.0f) * 255.0f);
        if (z() || y()) {
            b0.a("Layer#computeBounds");
            f(this.i, this.b, false);
            B(this.i, parentMatrix);
            this.b.preConcat(this.x.f());
            A(this.i, this.b);
            this.j.set(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
            canvas.getMatrix(this.c);
            if (!this.c.isIdentity()) {
                Matrix matrix = this.c;
                matrix.invert(matrix);
                this.c.mapRect(this.j);
            }
            if (!this.i.intersect(this.j)) {
                this.i.set(0.0f, 0.0f, 0.0f, 0.0f);
            }
            b0.b("Layer#computeBounds");
            if (this.i.width() >= 1.0f && this.i.height() >= 1.0f) {
                b0.a("Layer#saveLayer");
                this.d.setAlpha(255);
                com.airbnb.lottie.utils.h.m(canvas, this.i, this.d);
                b0.b("Layer#saveLayer");
                r(canvas);
                b0.a("Layer#drawLayer");
                s(canvas, this.b, alpha);
                b0.b("Layer#drawLayer");
                if (y()) {
                    n(canvas, this.b);
                }
                if (z()) {
                    b0.a("Layer#drawMatte");
                    b0.a("Layer#saveLayer");
                    com.airbnb.lottie.utils.h.n(canvas, this.i, this.g, 19);
                    b0.b("Layer#saveLayer");
                    r(canvas);
                    this.t.h(canvas, parentMatrix, alpha);
                    b0.a("Layer#restoreLayer");
                    canvas.restore();
                    b0.b("Layer#restoreLayer");
                    b0.b("Layer#drawMatte");
                }
                b0.a("Layer#restoreLayer");
                canvas.restore();
                b0.b("Layer#restoreLayer");
            }
            if (this.z && (paint = this.A) != null) {
                paint.setStyle(Paint.Style.STROKE);
                this.A.setColor(-251901);
                this.A.setStrokeWidth(4.0f);
                canvas.drawRect(this.i, this.A);
                this.A.setStyle(Paint.Style.FILL);
                this.A.setColor(1357638635);
                canvas.drawRect(this.i, this.A);
            }
            F(b0.b(this.n));
            return;
        }
        this.b.preConcat(this.x.f());
        b0.a("Layer#drawLayer");
        s(canvas, this.b, alpha);
        b0.b("Layer#drawLayer");
        F(b0.b(this.n));
    }

    private void F(float ms) {
        this.p.t().n().a(this.q.i(), ms);
    }

    private void r(Canvas canvas) {
        b0.a("Layer#clearLayer");
        RectF rectF = this.i;
        canvas.drawRect(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f, this.h);
        b0.b("Layer#clearLayer");
    }

    private void A(RectF rect, Matrix matrix) {
        this.k.set(0.0f, 0.0f, 0.0f, 0.0f);
        if (y()) {
            int size = this.r.b().size();
            for (int i2 = 0; i2 < size; i2++) {
                com.airbnb.lottie.model.content.h mask = this.r.b().get(i2);
                Path maskPath = (Path) ((com.airbnb.lottie.animation.keyframe.a) this.r.a().get(i2)).h();
                if (maskPath != null) {
                    this.a.set(maskPath);
                    this.a.transform(matrix);
                    switch (a.b[mask.a().ordinal()]) {
                        case 1:
                            return;
                        case 2:
                            return;
                        case 3:
                        case 4:
                            if (mask.d()) {
                                return;
                            }
                            break;
                    }
                    this.a.computeBounds(this.m, false);
                    if (i2 == 0) {
                        this.k.set(this.m);
                    } else {
                        RectF rectF = this.k;
                        rectF.set(Math.min(rectF.left, this.m.left), Math.min(this.k.top, this.m.top), Math.max(this.k.right, this.m.right), Math.max(this.k.bottom, this.m.bottom));
                    }
                }
            }
            if (!rect.intersect(this.k)) {
                rect.set(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
    }

    /* compiled from: BaseLayer */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[h.a.values().length];
            b = iArr;
            try {
                iArr[h.a.MASK_MODE_NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[h.a.MASK_MODE_SUBTRACT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b[h.a.MASK_MODE_INTERSECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                b[h.a.MASK_MODE_ADD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr2 = new int[e.a.values().length];
            a = iArr2;
            try {
                iArr2[e.a.SHAPE.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[e.a.PRE_COMP.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[e.a.SOLID.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[e.a.IMAGE.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[e.a.NULL.ordinal()] = 5;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[e.a.TEXT.ordinal()] = 6;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[e.a.UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    private void B(RectF rect, Matrix matrix) {
        if (z() && this.q.h() != e.b.INVERT) {
            this.l.set(0.0f, 0.0f, 0.0f, 0.0f);
            this.t.f(this.l, matrix, true);
            if (!rect.intersect(this.l)) {
                rect.set(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
    }

    private void n(Canvas canvas, Matrix matrix) {
        b0.a("Layer#saveLayer");
        com.airbnb.lottie.utils.h.n(canvas, this.i, this.e, 19);
        if (Build.VERSION.SDK_INT < 28) {
            r(canvas);
        }
        b0.b("Layer#saveLayer");
        for (int i2 = 0; i2 < this.r.b().size(); i2++) {
            com.airbnb.lottie.model.content.h mask = this.r.b().get(i2);
            BaseKeyframeAnimation<ShapeData, Path> maskAnimation = (com.airbnb.lottie.animation.keyframe.a) this.r.a().get(i2);
            BaseKeyframeAnimation<Integer, Integer> opacityAnimation = (com.airbnb.lottie.animation.keyframe.a) this.r.c().get(i2);
            switch (a.b[mask.a().ordinal()]) {
                case 1:
                    if (!p()) {
                        break;
                    } else {
                        this.d.setAlpha(255);
                        canvas.drawRect(this.i, this.d);
                        break;
                    }
                case 2:
                    if (i2 == 0) {
                        this.d.setColor(ViewCompat.MEASURED_STATE_MASK);
                        this.d.setAlpha(255);
                        canvas.drawRect(this.i, this.d);
                    }
                    if (!mask.d()) {
                        o(canvas, matrix, maskAnimation);
                        break;
                    } else {
                        m(canvas, matrix, maskAnimation, opacityAnimation);
                        break;
                    }
                case 3:
                    if (!mask.d()) {
                        j(canvas, matrix, maskAnimation, opacityAnimation);
                        break;
                    } else {
                        l(canvas, matrix, maskAnimation, opacityAnimation);
                        break;
                    }
                case 4:
                    if (!mask.d()) {
                        i(canvas, matrix, maskAnimation, opacityAnimation);
                        break;
                    } else {
                        k(canvas, matrix, maskAnimation, opacityAnimation);
                        break;
                    }
            }
        }
        b0.a("Layer#restoreLayer");
        canvas.restore();
        b0.b("Layer#restoreLayer");
    }

    private boolean p() {
        if (this.r.a().isEmpty()) {
            return false;
        }
        for (int i2 = 0; i2 < this.r.b().size(); i2++) {
            if (this.r.b().get(i2).a() != h.a.MASK_MODE_NONE) {
                return false;
            }
        }
        return true;
    }

    private void i(Canvas canvas, Matrix matrix, com.airbnb.lottie.animation.keyframe.a<n, Path> maskAnimation, com.airbnb.lottie.animation.keyframe.a<Integer, Integer> opacityAnimation) {
        this.a.set(maskAnimation.h());
        this.a.transform(matrix);
        this.d.setAlpha((int) (((float) opacityAnimation.h().intValue()) * 2.55f));
        canvas.drawPath(this.a, this.d);
    }

    private void k(Canvas canvas, Matrix matrix, com.airbnb.lottie.animation.keyframe.a<n, Path> maskAnimation, com.airbnb.lottie.animation.keyframe.a<Integer, Integer> opacityAnimation) {
        com.airbnb.lottie.utils.h.m(canvas, this.i, this.d);
        canvas.drawRect(this.i, this.d);
        this.a.set(maskAnimation.h());
        this.a.transform(matrix);
        this.d.setAlpha((int) (((float) opacityAnimation.h().intValue()) * 2.55f));
        canvas.drawPath(this.a, this.f);
        canvas.restore();
    }

    private void o(Canvas canvas, Matrix matrix, com.airbnb.lottie.animation.keyframe.a<n, Path> maskAnimation) {
        this.a.set(maskAnimation.h());
        this.a.transform(matrix);
        canvas.drawPath(this.a, this.f);
    }

    private void m(Canvas canvas, Matrix matrix, com.airbnb.lottie.animation.keyframe.a<n, Path> maskAnimation, com.airbnb.lottie.animation.keyframe.a<Integer, Integer> opacityAnimation) {
        com.airbnb.lottie.utils.h.m(canvas, this.i, this.f);
        canvas.drawRect(this.i, this.d);
        this.f.setAlpha((int) (((float) opacityAnimation.h().intValue()) * 2.55f));
        this.a.set(maskAnimation.h());
        this.a.transform(matrix);
        canvas.drawPath(this.a, this.f);
        canvas.restore();
    }

    private void j(Canvas canvas, Matrix matrix, com.airbnb.lottie.animation.keyframe.a<n, Path> maskAnimation, com.airbnb.lottie.animation.keyframe.a<Integer, Integer> opacityAnimation) {
        com.airbnb.lottie.utils.h.m(canvas, this.i, this.e);
        this.a.set(maskAnimation.h());
        this.a.transform(matrix);
        this.d.setAlpha((int) (((float) opacityAnimation.h().intValue()) * 2.55f));
        canvas.drawPath(this.a, this.d);
        canvas.restore();
    }

    private void l(Canvas canvas, Matrix matrix, com.airbnb.lottie.animation.keyframe.a<n, Path> maskAnimation, com.airbnb.lottie.animation.keyframe.a<Integer, Integer> opacityAnimation) {
        com.airbnb.lottie.utils.h.m(canvas, this.i, this.e);
        canvas.drawRect(this.i, this.d);
        this.f.setAlpha((int) (((float) opacityAnimation.h().intValue()) * 2.55f));
        this.a.set(maskAnimation.h());
        this.a.transform(matrix);
        canvas.drawPath(this.a, this.f);
        canvas.restore();
    }

    /* access modifiers changed from: package-private */
    public boolean y() {
        com.airbnb.lottie.animation.keyframe.h hVar = this.r;
        return hVar != null && !hVar.a().isEmpty();
    }

    private void M(boolean visible) {
        if (visible != this.y) {
            this.y = visible;
            C();
        }
    }

    /* access modifiers changed from: package-private */
    public void L(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        this.x.j(progress);
        if (this.r != null) {
            for (int i2 = 0; i2 < this.r.a().size(); i2++) {
                this.r.a().get(i2).m(progress);
            }
        }
        d dVar = this.s;
        if (dVar != null) {
            dVar.m(progress);
        }
        b bVar = this.t;
        if (bVar != null) {
            bVar.L(progress);
        }
        for (int i3 = 0; i3 < this.w.size(); i3++) {
            this.w.get(i3).m(progress);
        }
    }

    private void q() {
        if (this.v == null) {
            if (this.u == null) {
                this.v = Collections.emptyList();
                return;
            }
            this.v = new ArrayList();
            for (b layer = this.u; layer != null; layer = layer.u) {
                this.v.add(layer);
            }
        }
    }

    public String getName() {
        return this.q.i();
    }

    @Nullable
    public com.airbnb.lottie.model.content.a u() {
        return this.q.a();
    }

    public BlurMaskFilter v(float radius) {
        if (this.B == radius) {
            return this.C;
        }
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(radius / 2.0f, BlurMaskFilter.Blur.NORMAL);
        this.C = blurMaskFilter;
        this.B = radius;
        return blurMaskFilter;
    }

    @Nullable
    public j w() {
        return this.q.c();
    }

    public void b(List<c> list, List<c> list2) {
    }

    public void e(com.airbnb.lottie.model.e keyPath, int depth, List<com.airbnb.lottie.model.e> accumulator, com.airbnb.lottie.model.e currentPartialKeyPath) {
        b bVar = this.t;
        if (bVar != null) {
            com.airbnb.lottie.model.e matteCurrentPartialKeyPath = currentPartialKeyPath.a(bVar.getName());
            if (keyPath.c(this.t.getName(), depth)) {
                accumulator.add(matteCurrentPartialKeyPath.i(this.t));
            }
            if (keyPath.h(getName(), depth)) {
                this.t.H(keyPath, keyPath.e(this.t.getName(), depth) + depth, accumulator, matteCurrentPartialKeyPath);
            }
        }
        if (keyPath.g(getName(), depth)) {
            if (!"__container".equals(getName())) {
                currentPartialKeyPath = currentPartialKeyPath.a(getName());
                if (keyPath.c(getName(), depth)) {
                    accumulator.add(currentPartialKeyPath.i(this));
                }
            }
            if (keyPath.h(getName(), depth)) {
                H(keyPath, keyPath.e(getName(), depth) + depth, accumulator, currentPartialKeyPath);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void H(com.airbnb.lottie.model.e keyPath, int depth, List<com.airbnb.lottie.model.e> list, com.airbnb.lottie.model.e currentPartialKeyPath) {
    }

    @CallSuper
    public <T> void d(T property, @Nullable com.airbnb.lottie.value.c<T> callback) {
        this.x.c(property, callback);
    }
}
