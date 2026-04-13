package com.airbnb.lottie.animation.keyframe;

import android.view.animation.Interpolator;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.b0;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BaseKeyframeAnimation */
public abstract class a<K, A> {
    final List<b> a = new ArrayList(1);
    private boolean b = false;
    private final d<K> c;
    protected float d = 0.0f;
    @Nullable
    protected com.airbnb.lottie.value.c<A> e;
    @Nullable
    private A f = null;
    private float g = -1.0f;
    private float h = -1.0f;

    /* compiled from: BaseKeyframeAnimation */
    public interface b {
        void a();
    }

    /* compiled from: BaseKeyframeAnimation */
    public interface d<T> {
        boolean a(float f);

        com.airbnb.lottie.value.a<T> b();

        boolean c(float f);

        @FloatRange(from = 0.0d, to = 1.0d)
        float d();

        @FloatRange(from = 0.0d, to = 1.0d)
        float e();

        boolean isEmpty();
    }

    /* access modifiers changed from: package-private */
    public abstract A i(com.airbnb.lottie.value.a<K> aVar, float f2);

    a(List<? extends com.airbnb.lottie.value.a<K>> keyframes) {
        this.c = o(keyframes);
    }

    public void l() {
        this.b = true;
    }

    public void a(b listener) {
        this.a.add(listener);
    }

    public void m(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        if (!this.c.isEmpty()) {
            if (progress < g()) {
                progress = g();
            } else if (progress > c()) {
                progress = c();
            }
            if (progress != this.d) {
                this.d = progress;
                if (this.c.c(progress)) {
                    k();
                }
            }
        }
    }

    public void k() {
        for (int i = 0; i < this.a.size(); i++) {
            this.a.get(i).a();
        }
    }

    /* access modifiers changed from: protected */
    public com.airbnb.lottie.value.a<K> b() {
        b0.a("BaseKeyframeAnimation#getCurrentKeyframe");
        Keyframe<K> keyframe = this.c.b();
        b0.b("BaseKeyframeAnimation#getCurrentKeyframe");
        return keyframe;
    }

    /* access modifiers changed from: package-private */
    public float e() {
        if (this.b) {
            return 0.0f;
        }
        Keyframe<K> keyframe = b();
        if (keyframe.i()) {
            return 0.0f;
        }
        return (this.d - keyframe.f()) / (keyframe.c() - keyframe.f());
    }

    /* access modifiers changed from: protected */
    public float d() {
        Keyframe<K> keyframe = b();
        if (keyframe == null || keyframe.i()) {
            return 0.0f;
        }
        return keyframe.d.getInterpolation(e());
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    private float g() {
        if (this.g == -1.0f) {
            this.g = this.c.d();
        }
        return this.g;
    }

    /* access modifiers changed from: package-private */
    @FloatRange(from = 0.0d, to = 1.0d)
    public float c() {
        if (this.h == -1.0f) {
            this.h = this.c.e();
        }
        return this.h;
    }

    public A h() {
        A value;
        float linearProgress = e();
        if (this.e == null && this.c.a(linearProgress)) {
            return this.f;
        }
        Keyframe<K> keyframe = b();
        Interpolator interpolator = keyframe.e;
        if (interpolator == null || keyframe.f == null) {
            value = i(keyframe, d());
        } else {
            value = j(keyframe, linearProgress, interpolator.getInterpolation(linearProgress), keyframe.f.getInterpolation(linearProgress));
        }
        this.f = value;
        return value;
    }

    public float f() {
        return this.d;
    }

    public void n(@Nullable com.airbnb.lottie.value.c<A> valueCallback) {
        com.airbnb.lottie.value.c<A> cVar = this.e;
        if (cVar != null) {
            cVar.c((a<?, ?>) null);
        }
        this.e = valueCallback;
        if (valueCallback != null) {
            valueCallback.c(this);
        }
    }

    /* access modifiers changed from: protected */
    public A j(com.airbnb.lottie.value.a<K> aVar, float linearKeyframeProgress, float xKeyframeProgress, float yKeyframeProgress) {
        throw new UnsupportedOperationException("This animation does not support split dimensions!");
    }

    private static <T> d<T> o(List<? extends com.airbnb.lottie.value.a<T>> keyframes) {
        if (keyframes.isEmpty()) {
            return new c();
        }
        if (keyframes.size() == 1) {
            return new f(keyframes);
        }
        return new e(keyframes);
    }

    /* compiled from: BaseKeyframeAnimation */
    public static final class c<T> implements d<T> {
        private c() {
        }

        public boolean isEmpty() {
            return true;
        }

        public boolean c(float progress) {
            return false;
        }

        public com.airbnb.lottie.value.a<T> b() {
            throw new IllegalStateException("not implemented");
        }

        public float d() {
            return 0.0f;
        }

        public float e() {
            return 1.0f;
        }

        public boolean a(float progress) {
            throw new IllegalStateException("not implemented");
        }
    }

    /* compiled from: BaseKeyframeAnimation */
    public static final class f<T> implements d<T> {
        @NonNull
        private final com.airbnb.lottie.value.a<T> a;
        private float b = -1.0f;

        f(List<? extends com.airbnb.lottie.value.a<T>> keyframes) {
            this.a = (com.airbnb.lottie.value.a) keyframes.get(0);
        }

        public boolean isEmpty() {
            return false;
        }

        public boolean c(float progress) {
            return !this.a.i();
        }

        public com.airbnb.lottie.value.a<T> b() {
            return this.a;
        }

        public float d() {
            return this.a.f();
        }

        public float e() {
            return this.a.c();
        }

        public boolean a(float progress) {
            if (this.b == progress) {
                return true;
            }
            this.b = progress;
            return false;
        }
    }

    /* compiled from: BaseKeyframeAnimation */
    public static final class e<T> implements d<T> {
        private final List<? extends com.airbnb.lottie.value.a<T>> a;
        @NonNull
        private com.airbnb.lottie.value.a<T> b;
        private com.airbnb.lottie.value.a<T> c = null;
        private float d = -1.0f;

        e(List<? extends com.airbnb.lottie.value.a<T>> keyframes) {
            this.a = keyframes;
            this.b = f(0.0f);
        }

        public boolean isEmpty() {
            return false;
        }

        public boolean c(float progress) {
            if (this.b.a(progress)) {
                return !this.b.i();
            }
            this.b = f(progress);
            return true;
        }

        private com.airbnb.lottie.value.a<T> f(float progress) {
            List<? extends com.airbnb.lottie.value.a<T>> list = this.a;
            Keyframe<T> keyframe = (com.airbnb.lottie.value.a) list.get(list.size() - 1);
            if (progress >= keyframe.f()) {
                return keyframe;
            }
            for (int i = this.a.size() - 2; i >= 1; i--) {
                Keyframe<T> keyframe2 = (com.airbnb.lottie.value.a) this.a.get(i);
                if (this.b != keyframe2 && keyframe2.a(progress)) {
                    return keyframe2;
                }
            }
            return (com.airbnb.lottie.value.a) this.a.get(0);
        }

        @NonNull
        public com.airbnb.lottie.value.a<T> b() {
            return this.b;
        }

        public float d() {
            return ((com.airbnb.lottie.value.a) this.a.get(0)).f();
        }

        public float e() {
            List<? extends com.airbnb.lottie.value.a<T>> list = this.a;
            return ((com.airbnb.lottie.value.a) list.get(list.size() - 1)).c();
        }

        public boolean a(float progress) {
            com.airbnb.lottie.value.a<T> aVar = this.c;
            com.airbnb.lottie.value.a<T> aVar2 = this.b;
            if (aVar == aVar2 && this.d == progress) {
                return true;
            }
            this.c = aVar2;
            this.d = progress;
            return false;
        }
    }
}
