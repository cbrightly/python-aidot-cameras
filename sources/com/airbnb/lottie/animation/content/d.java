package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.p;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.l;
import com.airbnb.lottie.model.content.c;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.model.f;
import com.airbnb.lottie.model.layer.b;
import com.airbnb.lottie.utils.h;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ContentGroup */
public class d implements e, m, a.b, f {
    private final Paint a;
    private final RectF b;
    private final Matrix c;
    private final Path d;
    private final RectF e;
    private final String f;
    private final boolean g;
    private final List<c> h;
    private final e0 i;
    @Nullable
    private List<m> j;
    @Nullable
    private p k;

    private static List<c> g(e0 drawable, c0 composition, b layer, List<c> contentModels) {
        List<Content> contents = new ArrayList<>(contentModels.size());
        for (int i2 = 0; i2 < contentModels.size(); i2++) {
            c content = contentModels.get(i2).a(drawable, composition, layer);
            if (content != null) {
                contents.add(content);
            }
        }
        return contents;
    }

    @Nullable
    static l i(List<c> contentModels) {
        for (int i2 = 0; i2 < contentModels.size(); i2++) {
            c contentModel = contentModels.get(i2);
            if (contentModel instanceof l) {
                return (l) contentModel;
            }
        }
        return null;
    }

    public d(e0 lottieDrawable, b layer, com.airbnb.lottie.model.content.p shapeGroup, c0 composition) {
        this(lottieDrawable, layer, shapeGroup.c(), shapeGroup.d(), g(lottieDrawable, composition, layer, shapeGroup.b()), i(shapeGroup.b()));
    }

    d(e0 lottieDrawable, b layer, String name, boolean hidden, List<c> contents, @Nullable l transform) {
        this.a = new com.airbnb.lottie.animation.a();
        this.b = new RectF();
        this.c = new Matrix();
        this.d = new Path();
        this.e = new RectF();
        this.f = name;
        this.i = lottieDrawable;
        this.g = hidden;
        this.h = contents;
        if (transform != null) {
            p b2 = transform.b();
            this.k = b2;
            b2.a(layer);
            this.k.b(this);
        }
        List<GreedyContent> greedyContents = new ArrayList<>();
        for (int i2 = contents.size() - 1; i2 >= 0; i2--) {
            c content = contents.get(i2);
            if (content instanceof j) {
                greedyContents.add((j) content);
            }
        }
        for (int i3 = greedyContents.size() - 1; i3 >= 0; i3--) {
            ((j) greedyContents.get(i3)).g(contents.listIterator(contents.size()));
        }
    }

    public void a() {
        this.i.invalidateSelf();
    }

    public String getName() {
        return this.f;
    }

    public void b(List<c> contentsBefore, List<c> list) {
        List<Content> myContentsBefore = new ArrayList<>(contentsBefore.size() + this.h.size());
        myContentsBefore.addAll(contentsBefore);
        for (int i2 = this.h.size() - 1; i2 >= 0; i2--) {
            c content = this.h.get(i2);
            content.b(myContentsBefore, this.h.subList(0, i2));
            myContentsBefore.add(content);
        }
    }

    public List<c> j() {
        return this.h;
    }

    /* access modifiers changed from: package-private */
    public List<m> k() {
        if (this.j == null) {
            this.j = new ArrayList();
            for (int i2 = 0; i2 < this.h.size(); i2++) {
                c content = this.h.get(i2);
                if (content instanceof m) {
                    this.j.add((m) content);
                }
            }
        }
        return this.j;
    }

    /* access modifiers changed from: package-private */
    public Matrix l() {
        p pVar = this.k;
        if (pVar != null) {
            return pVar.f();
        }
        this.c.reset();
        return this.c;
    }

    public Path getPath() {
        this.c.reset();
        p pVar = this.k;
        if (pVar != null) {
            this.c.set(pVar.f());
        }
        this.d.reset();
        if (this.g) {
            return this.d;
        }
        for (int i2 = this.h.size() - 1; i2 >= 0; i2--) {
            c content = this.h.get(i2);
            if (content instanceof m) {
                this.d.addPath(((m) content).getPath(), this.c);
            }
        }
        return this.d;
    }

    public void h(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        int opacity;
        if (!this.g) {
            this.c.set(parentMatrix);
            p pVar = this.k;
            if (pVar != null) {
                this.c.preConcat(pVar.f());
                opacity = (int) ((((((float) (this.k.h() == null ? 100 : this.k.h().h().intValue())) / 100.0f) * ((float) parentAlpha)) / 255.0f) * 255.0f);
            } else {
                opacity = parentAlpha;
            }
            int childAlpha = 255;
            boolean isRenderingWithOffScreen = this.i.O() && m() && opacity != 255;
            if (isRenderingWithOffScreen) {
                this.b.set(0.0f, 0.0f, 0.0f, 0.0f);
                f(this.b, this.c, true);
                this.a.setAlpha(opacity);
                h.m(canvas, this.b, this.a);
            }
            if (!isRenderingWithOffScreen) {
                childAlpha = opacity;
            }
            for (int i2 = this.h.size() - 1; i2 >= 0; i2--) {
                c cVar = this.h.get(i2);
                if (cVar instanceof e) {
                    ((e) cVar).h(canvas, this.c, childAlpha);
                }
            }
            if (isRenderingWithOffScreen) {
                canvas.restore();
            }
        }
    }

    private boolean m() {
        int drawableContentCount = 0;
        for (int i2 = 0; i2 < this.h.size(); i2++) {
            if ((this.h.get(i2) instanceof e) && (drawableContentCount = drawableContentCount + 1) >= 2) {
                return true;
            }
        }
        return false;
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        this.c.set(parentMatrix);
        p pVar = this.k;
        if (pVar != null) {
            this.c.preConcat(pVar.f());
        }
        this.e.set(0.0f, 0.0f, 0.0f, 0.0f);
        for (int i2 = this.h.size() - 1; i2 >= 0; i2--) {
            c content = this.h.get(i2);
            if (content instanceof e) {
                ((e) content).f(this.e, this.c, applyParents);
                outBounds.union(this.e);
            }
        }
    }

    public void e(e keyPath, int depth, List<e> accumulator, e currentPartialKeyPath) {
        if (keyPath.g(getName(), depth) || "__container".equals(getName())) {
            if (!"__container".equals(getName())) {
                currentPartialKeyPath = currentPartialKeyPath.a(getName());
                if (keyPath.c(getName(), depth)) {
                    accumulator.add(currentPartialKeyPath.i(this));
                }
            }
            if (keyPath.h(getName(), depth)) {
                int newDepth = keyPath.e(getName(), depth) + depth;
                for (int i2 = 0; i2 < this.h.size(); i2++) {
                    c content = this.h.get(i2);
                    if (content instanceof f) {
                        ((f) content).e(keyPath, newDepth, accumulator, currentPartialKeyPath);
                    }
                }
            }
        }
    }

    public <T> void d(T property, @Nullable com.airbnb.lottie.value.c<T> callback) {
        p pVar = this.k;
        if (pVar != null) {
            pVar.c(property, callback);
        }
    }
}
