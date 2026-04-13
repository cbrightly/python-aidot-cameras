package com.airbnb.lottie.utils;

import android.view.Choreographer;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.airbnb.lottie.b0;
import com.airbnb.lottie.c0;

/* compiled from: LottieValueAnimator */
public class e extends a implements Choreographer.FrameCallback {
    private int a1 = 0;
    private float a2 = 2.14748365E9f;
    private float p0 = 0.0f;
    private float p1 = -2.14748365E9f;
    @Nullable
    private c0 p2;
    @VisibleForTesting
    protected boolean p3 = false;
    private boolean p4 = false;
    private float q = 1.0f;
    private boolean x = false;
    private long y = 0;
    private float z = 0.0f;

    public Object getAnimatedValue() {
        return Float.valueOf(k());
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float k() {
        c0 c0Var = this.p2;
        if (c0Var == null) {
            return 0.0f;
        }
        return (this.p0 - c0Var.p()) / (this.p2.f() - this.p2.p());
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getAnimatedFraction() {
        if (this.p2 == null) {
            return 0.0f;
        }
        if (q()) {
            return (n() - this.p0) / (n() - o());
        }
        return (this.p0 - o()) / (n() - o());
    }

    public long getDuration() {
        c0 c0Var = this.p2;
        if (c0Var == null) {
            return 0;
        }
        return (long) c0Var.d();
    }

    public float l() {
        return this.p0;
    }

    public boolean isRunning() {
        return this.p3;
    }

    public void G(boolean useCompositionFrameRate) {
        this.p4 = useCompositionFrameRate;
    }

    public void doFrame(long frameTimeNanos) {
        t();
        if (this.p2 != null && isRunning()) {
            b0.a("LottieValueAnimator#doFrame");
            long j = this.y;
            long timeSinceFrame = 0;
            if (j != 0) {
                timeSinceFrame = frameTimeNanos - j;
            }
            float dFrames = ((float) timeSinceFrame) / m();
            float newFrameRaw = this.z + (q() ? -dFrames : dFrames);
            boolean ended = !g.d(newFrameRaw, o(), n());
            float previousFrameRaw = this.z;
            float b = g.b(newFrameRaw, o(), n());
            this.z = b;
            if (this.p4) {
                b = (float) Math.floor((double) b);
            }
            this.p0 = b;
            this.y = frameTimeNanos;
            if (!this.p4 || this.z != previousFrameRaw) {
                h();
            }
            if (ended) {
                if (getRepeatCount() == -1 || this.a1 < getRepeatCount()) {
                    d();
                    this.a1++;
                    if (getRepeatMode() == 2) {
                        this.x = !this.x;
                        y();
                    } else {
                        float n = q() ? n() : o();
                        this.z = n;
                        this.p0 = n;
                    }
                    this.y = frameTimeNanos;
                } else {
                    float o = this.q < 0.0f ? o() : n();
                    this.z = o;
                    this.p0 = o;
                    u();
                    b(q());
                }
            }
            H();
            b0.b("LottieValueAnimator#doFrame");
        }
    }

    private float m() {
        c0 c0Var = this.p2;
        if (c0Var == null) {
            return Float.MAX_VALUE;
        }
        return (1.0E9f / c0Var.i()) / Math.abs(this.q);
    }

    public void i() {
        this.p2 = null;
        this.p1 = -2.14748365E9f;
        this.a2 = 2.14748365E9f;
    }

    public void z(c0 composition) {
        boolean keepMinAndMaxFrames = this.p2 == null;
        this.p2 = composition;
        if (keepMinAndMaxFrames) {
            C(Math.max(this.p1, composition.p()), Math.min(this.a2, composition.f()));
        } else {
            C((float) ((int) composition.p()), (float) ((int) composition.f()));
        }
        float frame = this.p0;
        this.p0 = 0.0f;
        this.z = 0.0f;
        A((float) ((int) frame));
        h();
    }

    public void A(float frame) {
        if (this.z != frame) {
            float b = g.b(frame, o(), n());
            this.z = b;
            if (this.p4) {
                b = (float) Math.floor((double) b);
            }
            this.p0 = b;
            this.y = 0;
            h();
        }
    }

    public void D(int minFrame) {
        C((float) minFrame, (float) ((int) this.a2));
    }

    public void B(float maxFrame) {
        C(this.p1, maxFrame);
    }

    public void C(float minFrame, float maxFrame) {
        if (minFrame <= maxFrame) {
            c0 c0Var = this.p2;
            float compositionMinFrame = c0Var == null ? -3.4028235E38f : c0Var.p();
            c0 c0Var2 = this.p2;
            float compositionMaxFrame = c0Var2 == null ? Float.MAX_VALUE : c0Var2.f();
            float newMinFrame = g.b(minFrame, compositionMinFrame, compositionMaxFrame);
            float newMaxFrame = g.b(maxFrame, compositionMinFrame, compositionMaxFrame);
            if (newMinFrame != this.p1 || newMaxFrame != this.a2) {
                this.p1 = newMinFrame;
                this.a2 = newMaxFrame;
                A((float) ((int) g.b(this.p0, newMinFrame, newMaxFrame)));
                return;
            }
            return;
        }
        throw new IllegalArgumentException(String.format("minFrame (%s) must be <= maxFrame (%s)", new Object[]{Float.valueOf(minFrame), Float.valueOf(maxFrame)}));
    }

    public void y() {
        F(-p());
    }

    public void F(float speed) {
        this.q = speed;
    }

    public float p() {
        return this.q;
    }

    public void setRepeatMode(int value) {
        super.setRepeatMode(value);
        if (value != 2 && this.x) {
            this.x = false;
            y();
        }
    }

    @MainThread
    public void s() {
        this.p3 = true;
        f(q());
        A((float) ((int) (q() ? n() : o())));
        this.y = 0;
        this.a1 = 0;
        t();
    }

    @MainThread
    public void j() {
        u();
        b(q());
    }

    @MainThread
    public void r() {
        u();
        c();
    }

    @MainThread
    public void w() {
        this.p3 = true;
        t();
        this.y = 0;
        if (q() && l() == o()) {
            A(n());
        } else if (!q() && l() == n()) {
            A(o());
        }
        e();
    }

    @MainThread
    public void cancel() {
        a();
        u();
    }

    private boolean q() {
        return p() < 0.0f;
    }

    public float o() {
        c0 c0Var = this.p2;
        if (c0Var == null) {
            return 0.0f;
        }
        float f = this.p1;
        return f == -2.14748365E9f ? c0Var.p() : f;
    }

    public float n() {
        c0 c0Var = this.p2;
        if (c0Var == null) {
            return 0.0f;
        }
        float f = this.a2;
        return f == 2.14748365E9f ? c0Var.f() : f;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        super.a();
        b(q());
    }

    /* access modifiers changed from: protected */
    public void t() {
        if (isRunning()) {
            v(false);
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void u() {
        v(true);
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void v(boolean stopRunning) {
        Choreographer.getInstance().removeFrameCallback(this);
        if (stopRunning) {
            this.p3 = false;
        }
    }

    private void H() {
        if (this.p2 != null) {
            float f = this.p0;
            if (f < this.p1 || f > this.a2) {
                throw new IllegalStateException(String.format("Frame must be [%f,%f]. It is %f", new Object[]{Float.valueOf(this.p1), Float.valueOf(this.a2), Float.valueOf(this.p0)}));
            }
        }
    }
}
