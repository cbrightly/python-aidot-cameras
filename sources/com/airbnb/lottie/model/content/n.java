package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import androidx.annotation.FloatRange;
import com.airbnb.lottie.model.a;
import com.airbnb.lottie.utils.d;
import com.airbnb.lottie.utils.g;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ShapeData */
public class n {
    private final List<a> a;
    private PointF b;
    private boolean c;

    public n(PointF initialPoint, boolean closed, List<a> curves) {
        this.b = initialPoint;
        this.c = closed;
        this.a = new ArrayList(curves);
    }

    public n() {
        this.a = new ArrayList();
    }

    public void f(float x, float y) {
        if (this.b == null) {
            this.b = new PointF();
        }
        this.b.set(x, y);
    }

    public PointF b() {
        return this.b;
    }

    public void e(boolean closed) {
        this.c = closed;
    }

    public boolean d() {
        return this.c;
    }

    public List<a> a() {
        return this.a;
    }

    public void c(n shapeData1, n shapeData2, @FloatRange(from = 0.0d, to = 1.0d) float percentage) {
        float f = percentage;
        if (this.b == null) {
            this.b = new PointF();
        }
        this.c = shapeData1.d() || shapeData2.d();
        if (shapeData1.a().size() != shapeData2.a().size()) {
            d.c("Curves must have the same number of control points. Shape 1: " + shapeData1.a().size() + "\tShape 2: " + shapeData2.a().size());
        }
        int points = Math.min(shapeData1.a().size(), shapeData2.a().size());
        if (this.a.size() < points) {
            for (int i = this.a.size(); i < points; i++) {
                this.a.add(new a());
            }
        } else if (this.a.size() > points) {
            for (int i2 = this.a.size() - 1; i2 >= points; i2--) {
                List<a> list = this.a;
                list.remove(list.size() - 1);
            }
        }
        PointF initialPoint1 = shapeData1.b();
        PointF initialPoint2 = shapeData2.b();
        f(g.i(initialPoint1.x, initialPoint2.x, f), g.i(initialPoint1.y, initialPoint2.y, f));
        int i3 = this.a.size() - 1;
        while (i3 >= 0) {
            a curve1 = shapeData1.a().get(i3);
            a curve2 = shapeData2.a().get(i3);
            PointF cp11 = curve1.a();
            PointF cp21 = curve1.b();
            PointF vertex1 = curve1.c();
            PointF cp12 = curve2.a();
            PointF cp22 = curve2.b();
            PointF vertex2 = curve2.c();
            a aVar = curve1;
            this.a.get(i3).d(g.i(cp11.x, cp12.x, f), g.i(cp11.y, cp12.y, f));
            this.a.get(i3).e(g.i(cp21.x, cp22.x, f), g.i(cp21.y, cp22.y, f));
            this.a.get(i3).f(g.i(vertex1.x, vertex2.x, f), g.i(vertex1.y, vertex2.y, f));
            i3--;
            points = points;
        }
    }

    public String toString() {
        return "ShapeData{numCurves=" + this.a.size() + "closed=" + this.c + '}';
    }
}
