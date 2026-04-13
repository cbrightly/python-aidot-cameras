package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.m;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.content.q;
import com.airbnb.lottie.model.content.s;
import com.airbnb.lottie.model.layer.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ShapeContent */
public class r implements m, a.b {
    private final Path a = new Path();
    private final String b;
    private final boolean c;
    private final e0 d;
    private final m e;
    private boolean f;
    private final b g = new b();

    public r(e0 lottieDrawable, b layer, q shape) {
        this.b = shape.b();
        this.c = shape.d();
        this.d = lottieDrawable;
        m a2 = shape.c().j();
        this.e = a2;
        layer.g(a2);
        a2.a(this);
    }

    public void a() {
        d();
    }

    private void d() {
        this.f = false;
        this.d.invalidateSelf();
    }

    public void b(List<c> contentsBefore, List<c> list) {
        List<ShapeModifierContent> shapeModifierContents = null;
        for (int i = 0; i < contentsBefore.size(); i++) {
            c content = contentsBefore.get(i);
            if ((content instanceof u) && ((u) content).j() == s.a.SIMULTANEOUSLY) {
                u trimPath = (u) content;
                this.g.a(trimPath);
                trimPath.d(this);
            } else if (content instanceof s) {
                if (shapeModifierContents == null) {
                    shapeModifierContents = new ArrayList<>();
                }
                shapeModifierContents.add((s) content);
            }
        }
        this.e.q(shapeModifierContents);
    }

    public Path getPath() {
        if (this.f) {
            return this.a;
        }
        this.a.reset();
        if (this.c) {
            this.f = true;
            return this.a;
        }
        Path shapeAnimationPath = (Path) this.e.h();
        if (shapeAnimationPath == null) {
            return this.a;
        }
        this.a.set(shapeAnimationPath);
        this.a.setFillType(Path.FillType.EVEN_ODD);
        this.g.b(this.a);
        this.f = true;
        return this.a;
    }
}
