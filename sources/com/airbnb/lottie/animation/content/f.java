package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.content.b;
import com.airbnb.lottie.model.content.s;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: EllipseContent */
public class f implements m, a.b, k {
    private final Path a = new Path();
    private final String b;
    private final e0 c;
    private final a<?, PointF> d;
    private final a<?, PointF> e;
    private final b f;
    private final b g = new b();
    private boolean h;

    public f(e0 lottieDrawable, com.airbnb.lottie.model.layer.b layer, b circleShape) {
        this.b = circleShape.b();
        this.c = lottieDrawable;
        a<PointF, PointF> j = circleShape.d().j();
        this.d = j;
        a<PointF, PointF> j2 = circleShape.c().j();
        this.e = j2;
        this.f = circleShape;
        layer.g(j);
        layer.g(j2);
        j.a(this);
        j2.a(this);
    }

    public void a() {
        g();
    }

    private void g() {
        this.h = false;
        this.c.invalidateSelf();
    }

    public void b(List<c> contentsBefore, List<c> list) {
        for (int i = 0; i < contentsBefore.size(); i++) {
            c content = contentsBefore.get(i);
            if ((content instanceof u) && ((u) content).j() == s.a.SIMULTANEOUSLY) {
                u trimPath = (u) content;
                this.g.a(trimPath);
                trimPath.d(this);
            }
        }
    }

    public String getName() {
        return this.b;
    }

    public Path getPath() {
        if (this.h) {
            return this.a;
        }
        this.a.reset();
        if (this.f.e()) {
            this.h = true;
            return this.a;
        }
        PointF size = this.d.h();
        float halfWidth = size.x / 2.0f;
        float halfHeight = size.y / 2.0f;
        float cpW = halfWidth * 0.55228f;
        float cpH = halfHeight * 0.55228f;
        this.a.reset();
        if (this.f.f()) {
            this.a.moveTo(0.0f, -halfHeight);
            this.a.cubicTo(0.0f - cpW, -halfHeight, -halfWidth, 0.0f - cpH, -halfWidth, 0.0f);
            this.a.cubicTo(-halfWidth, cpH + 0.0f, 0.0f - cpW, halfHeight, 0.0f, halfHeight);
            this.a.cubicTo(cpW + 0.0f, halfHeight, halfWidth, cpH + 0.0f, halfWidth, 0.0f);
            this.a.cubicTo(halfWidth, 0.0f - cpH, cpW + 0.0f, -halfHeight, 0.0f, -halfHeight);
        } else {
            this.a.moveTo(0.0f, -halfHeight);
            this.a.cubicTo(cpW + 0.0f, -halfHeight, halfWidth, 0.0f - cpH, halfWidth, 0.0f);
            this.a.cubicTo(halfWidth, cpH + 0.0f, cpW + 0.0f, halfHeight, 0.0f, halfHeight);
            this.a.cubicTo(0.0f - cpW, halfHeight, -halfWidth, cpH + 0.0f, -halfWidth, 0.0f);
            this.a.cubicTo(-halfWidth, 0.0f - cpH, 0.0f - cpW, -halfHeight, 0.0f, -halfHeight);
        }
        PointF position = this.e.h();
        this.a.offset(position.x, position.y);
        this.a.close();
        this.g.b(this.a);
        this.h = true;
        return this.a;
    }

    public void e(e keyPath, int depth, List<e> accumulator, e currentPartialKeyPath) {
        g.k(keyPath, depth, accumulator, currentPartialKeyPath, this);
    }

    public <T> void d(T property, @Nullable c<T> callback) {
        if (property == j0.k) {
            this.d.n(callback);
        } else if (property == j0.n) {
            this.e.n(callback);
        }
    }
}
