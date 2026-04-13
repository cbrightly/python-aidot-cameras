package com.airbnb.lottie.animation.keyframe;

import android.graphics.Matrix;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.animatable.l;
import com.airbnb.lottie.model.layer.b;
import com.airbnb.lottie.value.ScaleXY;
import com.airbnb.lottie.value.c;
import com.airbnb.lottie.value.d;
import java.util.Collections;

/* compiled from: TransformKeyframeAnimation */
public class p {
    private final Matrix a = new Matrix();
    private final Matrix b;
    private final Matrix c;
    private final Matrix d;
    private final float[] e;
    @Nullable
    private a<PointF, PointF> f;
    @Nullable
    private a<?, PointF> g;
    @Nullable
    private a<d, d> h;
    @Nullable
    private a<Float, Float> i;
    @Nullable
    private a<Integer, Integer> j;
    @Nullable
    private d k;
    @Nullable
    private d l;
    @Nullable
    private a<?, Float> m;
    @Nullable
    private a<?, Float> n;

    public p(l animatableTransform) {
        this.f = animatableTransform.c() == null ? null : animatableTransform.c().j();
        this.g = animatableTransform.f() == null ? null : animatableTransform.f().j();
        this.h = animatableTransform.h() == null ? null : animatableTransform.h().j();
        this.i = animatableTransform.g() == null ? null : animatableTransform.g().j();
        d dVar = animatableTransform.i() == null ? null : (d) animatableTransform.i().j();
        this.k = dVar;
        if (dVar != null) {
            this.b = new Matrix();
            this.c = new Matrix();
            this.d = new Matrix();
            this.e = new float[9];
        } else {
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
        }
        this.l = animatableTransform.j() == null ? null : (d) animatableTransform.j().j();
        if (animatableTransform.e() != null) {
            this.j = animatableTransform.e().j();
        }
        if (animatableTransform.k() != null) {
            this.m = animatableTransform.k().j();
        } else {
            this.m = null;
        }
        if (animatableTransform.d() != null) {
            this.n = animatableTransform.d().j();
        } else {
            this.n = null;
        }
    }

    public void a(b layer) {
        layer.g(this.j);
        layer.g(this.m);
        layer.g(this.n);
        layer.g(this.f);
        layer.g(this.g);
        layer.g(this.h);
        layer.g(this.i);
        layer.g(this.k);
        layer.g(this.l);
    }

    public void b(a.b listener) {
        a<Integer, Integer> aVar = this.j;
        if (aVar != null) {
            aVar.a(listener);
        }
        a<?, Float> aVar2 = this.m;
        if (aVar2 != null) {
            aVar2.a(listener);
        }
        a<?, Float> aVar3 = this.n;
        if (aVar3 != null) {
            aVar3.a(listener);
        }
        a<PointF, PointF> aVar4 = this.f;
        if (aVar4 != null) {
            aVar4.a(listener);
        }
        a<?, PointF> aVar5 = this.g;
        if (aVar5 != null) {
            aVar5.a(listener);
        }
        a<d, d> aVar6 = this.h;
        if (aVar6 != null) {
            aVar6.a(listener);
        }
        a<Float, Float> aVar7 = this.i;
        if (aVar7 != null) {
            aVar7.a(listener);
        }
        d dVar = this.k;
        if (dVar != null) {
            dVar.a(listener);
        }
        d dVar2 = this.l;
        if (dVar2 != null) {
            dVar2.a(listener);
        }
    }

    public void j(float progress) {
        a<Integer, Integer> aVar = this.j;
        if (aVar != null) {
            aVar.m(progress);
        }
        a<?, Float> aVar2 = this.m;
        if (aVar2 != null) {
            aVar2.m(progress);
        }
        a<?, Float> aVar3 = this.n;
        if (aVar3 != null) {
            aVar3.m(progress);
        }
        a<PointF, PointF> aVar4 = this.f;
        if (aVar4 != null) {
            aVar4.m(progress);
        }
        a<?, PointF> aVar5 = this.g;
        if (aVar5 != null) {
            aVar5.m(progress);
        }
        a<d, d> aVar6 = this.h;
        if (aVar6 != null) {
            aVar6.m(progress);
        }
        a<Float, Float> aVar7 = this.i;
        if (aVar7 != null) {
            aVar7.m(progress);
        }
        d dVar = this.k;
        if (dVar != null) {
            dVar.m(progress);
        }
        d dVar2 = this.l;
        if (dVar2 != null) {
            dVar2.m(progress);
        }
    }

    @Nullable
    public a<?, Integer> h() {
        return this.j;
    }

    @Nullable
    public a<?, Float> i() {
        return this.m;
    }

    @Nullable
    public a<?, Float> e() {
        return this.n;
    }

    public Matrix f() {
        PointF anchorPointValue;
        float rotationValue;
        PointF positionValue;
        this.a.reset();
        BaseKeyframeAnimation<?, PointF> position = this.g;
        if (!(position == null || (positionValue = position.h()) == null)) {
            float f2 = positionValue.x;
            if (!(f2 == 0.0f && positionValue.y == 0.0f)) {
                this.a.preTranslate(f2, positionValue.y);
            }
        }
        BaseKeyframeAnimation<Float, Float> rotation = this.i;
        if (rotation != null) {
            if (rotation instanceof q) {
                rotationValue = rotation.h().floatValue();
            } else {
                rotationValue = ((d) rotation).p();
            }
            if (rotationValue != 0.0f) {
                this.a.preRotate(rotationValue);
            }
        }
        d skew = this.k;
        if (skew != null) {
            d dVar = this.l;
            float mCos = dVar == null ? 0.0f : (float) Math.cos(Math.toRadians((double) ((-dVar.p()) + 90.0f)));
            d dVar2 = this.l;
            float mSin = dVar2 == null ? 1.0f : (float) Math.sin(Math.toRadians((double) ((-dVar2.p()) + 90.0f)));
            d();
            float[] fArr = this.e;
            fArr[0] = mCos;
            fArr[1] = mSin;
            fArr[3] = -mSin;
            fArr[4] = mCos;
            fArr[8] = 1.0f;
            this.b.setValues(fArr);
            d();
            float[] fArr2 = this.e;
            fArr2[0] = 1.0f;
            fArr2[3] = (float) Math.tan(Math.toRadians((double) skew.p()));
            fArr2[4] = 1.0f;
            fArr2[8] = 1.0f;
            this.c.setValues(fArr2);
            d();
            float[] fArr3 = this.e;
            fArr3[0] = mCos;
            fArr3[1] = -mSin;
            fArr3[3] = mSin;
            fArr3[4] = mCos;
            fArr3[8] = 1.0f;
            this.d.setValues(fArr3);
            this.c.preConcat(this.b);
            this.d.preConcat(this.c);
            this.a.preConcat(this.d);
        }
        BaseKeyframeAnimation<ScaleXY, ScaleXY> scale = this.h;
        if (scale != null) {
            d scaleTransform = scale.h();
            if (!(scaleTransform.b() == 1.0f && scaleTransform.c() == 1.0f)) {
                this.a.preScale(scaleTransform.b(), scaleTransform.c());
            }
        }
        BaseKeyframeAnimation<PointF, PointF> anchorPoint = this.f;
        if (!(anchorPoint == null || (((anchorPointValue = anchorPoint.h()) == null || anchorPointValue.x == 0.0f) && anchorPointValue.y == 0.0f))) {
            this.a.preTranslate(-anchorPointValue.x, -anchorPointValue.y);
        }
        return this.a;
    }

    private void d() {
        for (int i2 = 0; i2 < 9; i2++) {
            this.e[i2] = 0.0f;
        }
    }

    public Matrix g(float amount) {
        a<?, PointF> aVar = this.g;
        PointF anchorPoint = null;
        PointF position = aVar == null ? null : aVar.h();
        a<d, d> aVar2 = this.h;
        d scale = aVar2 == null ? null : aVar2.h();
        this.a.reset();
        if (position != null) {
            this.a.preTranslate(position.x * amount, position.y * amount);
        }
        if (scale != null) {
            this.a.preScale((float) Math.pow((double) scale.b(), (double) amount), (float) Math.pow((double) scale.c(), (double) amount));
        }
        a<Float, Float> aVar3 = this.i;
        if (aVar3 != null) {
            float rotation = aVar3.h().floatValue();
            a<PointF, PointF> aVar4 = this.f;
            if (aVar4 != null) {
                anchorPoint = aVar4.h();
            }
            Matrix matrix = this.a;
            float f2 = rotation * amount;
            float f3 = 0.0f;
            float f4 = anchorPoint == null ? 0.0f : anchorPoint.x;
            if (anchorPoint != null) {
                f3 = anchorPoint.y;
            }
            matrix.preRotate(f2, f4, f3);
        }
        return this.a;
    }

    public <T> boolean c(T property, @Nullable c<T> callback) {
        if (property == j0.f) {
            a<PointF, PointF> aVar = this.f;
            if (aVar == null) {
                this.f = new q(callback, new PointF());
                return true;
            }
            aVar.n(callback);
            return true;
        } else if (property == j0.g) {
            a<?, PointF> aVar2 = this.g;
            if (aVar2 == null) {
                this.g = new q(callback, new PointF());
                return true;
            }
            aVar2.n(callback);
            return true;
        } else {
            if (property == j0.h) {
                a<?, PointF> aVar3 = this.g;
                if (aVar3 instanceof n) {
                    ((n) aVar3).r(callback);
                    return true;
                }
            }
            if (property == j0.i) {
                a<?, PointF> aVar4 = this.g;
                if (aVar4 instanceof n) {
                    ((n) aVar4).s(callback);
                    return true;
                }
            }
            if (property == j0.o) {
                a<d, d> aVar5 = this.h;
                if (aVar5 == null) {
                    this.h = new q(callback, new d());
                    return true;
                }
                aVar5.n(callback);
                return true;
            } else if (property == j0.p) {
                a<Float, Float> aVar6 = this.i;
                if (aVar6 == null) {
                    this.i = new q(callback, Float.valueOf(0.0f));
                    return true;
                }
                aVar6.n(callback);
                return true;
            } else if (property == j0.c) {
                a<Integer, Integer> aVar7 = this.j;
                if (aVar7 == null) {
                    this.j = new q(callback, 100);
                    return true;
                }
                aVar7.n(callback);
                return true;
            } else if (property == j0.C) {
                a<?, Float> aVar8 = this.m;
                if (aVar8 == null) {
                    this.m = new q(callback, Float.valueOf(100.0f));
                    return true;
                }
                aVar8.n(callback);
                return true;
            } else if (property == j0.D) {
                a<?, Float> aVar9 = this.n;
                if (aVar9 == null) {
                    this.n = new q(callback, Float.valueOf(100.0f));
                    return true;
                }
                aVar9.n(callback);
                return true;
            } else if (property == j0.q) {
                if (this.k == null) {
                    this.k = new d(Collections.singletonList(new com.airbnb.lottie.value.a(Float.valueOf(0.0f))));
                }
                this.k.n(callback);
                return true;
            } else if (property != j0.r) {
                return false;
            } else {
                if (this.l == null) {
                    this.l = new d(Collections.singletonList(new com.airbnb.lottie.value.a(Float.valueOf(0.0f))));
                }
                this.l.n(callback);
                return true;
            }
        }
    }
}
