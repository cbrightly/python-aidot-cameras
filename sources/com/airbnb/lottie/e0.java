package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.h;
import com.airbnb.lottie.parser.v;
import com.airbnb.lottie.utils.d;
import com.airbnb.lottie.utils.e;
import com.airbnb.lottie.utils.g;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: LottieDrawable */
public class e0 extends Drawable implements Drawable.Callback, Animatable {
    @Nullable
    q0 A4;
    private boolean B4;
    private boolean C4;
    private boolean D4;
    /* access modifiers changed from: private */
    @Nullable
    public com.airbnb.lottie.model.layer.c E4;
    private int F4;
    private boolean G4;
    private boolean H4;
    private boolean I4;
    private o0 J4;
    private boolean K4;
    private final Matrix L4;
    private Bitmap M4;
    private Canvas N4;
    private Rect O4;
    private RectF P4;
    private Paint Q4;
    private Rect R4;
    private Rect S4;
    private RectF T4;
    private RectF U4;
    private Matrix V4;
    private Matrix W4;
    private boolean X4;
    @Nullable
    private com.airbnb.lottie.manager.b a1;
    @Nullable
    private a0 a2;
    private c0 c;
    /* access modifiers changed from: private */
    public final e d;
    private boolean f = true;
    private final ValueAnimator.AnimatorUpdateListener p0;
    @Nullable
    private String p1;
    @Nullable
    private com.airbnb.lottie.manager.a p2;
    @Nullable
    private Map<String, Typeface> p3;
    @Nullable
    String p4;
    private boolean q = false;
    private boolean x = false;
    private c y = c.NONE;
    private final ArrayList<b> z = new ArrayList<>();
    @Nullable
    z z4;

    /* compiled from: LottieDrawable */
    public interface b {
        void a(c0 c0Var);
    }

    /* compiled from: LottieDrawable */
    public enum c {
        NONE,
        PLAY,
        RESUME
    }

    /* compiled from: LottieDrawable */
    public class a implements ValueAnimator.AnimatorUpdateListener {
        a() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (e0.this.E4 != null) {
                e0.this.E4.L(e0.this.d.k());
            }
        }
    }

    public e0() {
        e eVar = new e();
        this.d = eVar;
        a aVar = new a();
        this.p0 = aVar;
        this.C4 = false;
        this.D4 = true;
        this.F4 = 255;
        this.J4 = o0.AUTOMATIC;
        this.K4 = false;
        this.L4 = new Matrix();
        this.X4 = false;
        eVar.addUpdateListener(aVar);
    }

    public boolean n() {
        return this.B4;
    }

    public void m(boolean enable) {
        if (this.B4 != enable) {
            if (Build.VERSION.SDK_INT < 19) {
                d.c("Merge paths are not supported pre-Kit Kat.");
                return;
            }
            this.B4 = enable;
            if (this.c != null) {
                f();
            }
        }
    }

    public void w0(boolean clipToCompositionBounds) {
        if (clipToCompositionBounds != this.D4) {
            this.D4 = clipToCompositionBounds;
            com.airbnb.lottie.model.layer.c compositionLayer = this.E4;
            if (compositionLayer != null) {
                compositionLayer.O(clipToCompositionBounds);
            }
            invalidateSelf();
        }
    }

    public boolean s() {
        return this.D4;
    }

    public void E0(@Nullable String imageAssetsFolder) {
        this.p1 = imageAssetsFolder;
    }

    @Nullable
    public String y() {
        return this.p1;
    }

    public void F0(boolean maintainOriginalImageBounds) {
        this.C4 = maintainOriginalImageBounds;
    }

    public boolean A() {
        return this.C4;
    }

    public boolean x0(c0 composition) {
        if (this.c == composition) {
            return false;
        }
        this.X4 = true;
        h();
        this.c = composition;
        f();
        this.d.z(composition);
        Q0(this.d.getAnimatedFraction());
        Iterator<LottieDrawable.LazyCompositionTask> it = new ArrayList(this.z).iterator();
        while (it.hasNext()) {
            b t = (b) it.next();
            if (t != null) {
                t.a(composition);
            }
            it.remove();
        }
        this.z.clear();
        composition.v(this.G4);
        i();
        Drawable.Callback callback = getCallback();
        if (callback instanceof ImageView) {
            ((ImageView) callback).setImageDrawable((Drawable) null);
            ((ImageView) callback).setImageDrawable(this);
        }
        return true;
    }

    public void R0(o0 renderMode) {
        this.J4 = renderMode;
        i();
    }

    public o0 F() {
        return this.K4 ? o0.SOFTWARE : o0.HARDWARE;
    }

    private void i() {
        c0 composition = this.c;
        if (composition != null) {
            this.K4 = this.J4.useSoftwareRendering(Build.VERSION.SDK_INT, composition.q(), composition.m());
        }
    }

    public void P0(boolean enabled) {
        this.G4 = enabled;
        c0 c0Var = this.c;
        if (c0Var != null) {
            c0Var.v(enabled);
        }
    }

    public void O0(boolean outline) {
        if (this.H4 != outline) {
            this.H4 = outline;
            com.airbnb.lottie.model.layer.c cVar = this.E4;
            if (cVar != null) {
                cVar.J(outline);
            }
        }
    }

    @Nullable
    public n0 D() {
        c0 c0Var = this.c;
        if (c0Var != null) {
            return c0Var.n();
        }
        return null;
    }

    public void v0(boolean isApplyingOpacityToLayersEnabled) {
        this.I4 = isApplyingOpacityToLayersEnabled;
    }

    public boolean O() {
        return this.I4;
    }

    private void f() {
        c0 composition = this.c;
        if (composition != null) {
            com.airbnb.lottie.model.layer.c cVar = new com.airbnb.lottie.model.layer.c(this, v.a(composition), composition.k(), composition);
            this.E4 = cVar;
            if (this.H4) {
                cVar.J(true);
            }
            this.E4.O(this.D4);
        }
    }

    public void h() {
        if (this.d.isRunning()) {
            this.d.cancel();
            if (!isVisible()) {
                this.y = c.NONE;
            }
        }
        this.c = null;
        this.E4 = null;
        this.a1 = null;
        this.d.i();
        invalidateSelf();
    }

    public void U0(boolean safeMode) {
        this.x = safeMode;
    }

    public void invalidateSelf() {
        if (!this.X4) {
            this.X4 = true;
            Drawable.Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        this.F4 = alpha;
        invalidateSelf();
    }

    public int getAlpha() {
        return this.F4;
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        d.c("Use addColorFilter instead.");
    }

    public int getOpacity() {
        return -3;
    }

    public void draw(@NonNull Canvas canvas) {
        b0.a("Drawable#draw");
        if (this.x) {
            try {
                if (this.K4) {
                    r0(canvas, this.E4);
                } else {
                    l(canvas);
                }
            } catch (Throwable e) {
                d.b("Lottie crashed in draw!", e);
            }
        } else if (this.K4) {
            r0(canvas, this.E4);
        } else {
            l(canvas);
        }
        this.X4 = false;
        b0.b("Drawable#draw");
    }

    @MainThread
    public void start() {
        Drawable.Callback callback = getCallback();
        if (!(callback instanceof View) || !((View) callback).isInEditMode()) {
            q0();
        }
    }

    @MainThread
    public void stop() {
        o();
    }

    public boolean isRunning() {
        return M();
    }

    @MainThread
    public void q0() {
        if (this.E4 == null) {
            this.z.add(new m(this));
            return;
        }
        i();
        if (e() || G() == 0) {
            if (isVisible()) {
                this.d.s();
                this.y = c.NONE;
            } else {
                this.y = c.PLAY;
            }
        }
        if (!e()) {
            B0((int) (I() < 0.0f ? C() : B()));
            this.d.j();
            if (!isVisible()) {
                this.y = c.NONE;
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: R */
    public /* synthetic */ void S(c0 c2) {
        q0();
    }

    @MainThread
    public void o() {
        this.z.clear();
        this.d.j();
        if (!isVisible()) {
            this.y = c.NONE;
        }
    }

    @MainThread
    public void t0() {
        if (this.E4 == null) {
            this.z.add(new k(this));
            return;
        }
        i();
        if (e() || G() == 0) {
            if (isVisible()) {
                this.d.w();
                this.y = c.NONE;
            } else {
                this.y = c.RESUME;
            }
        }
        if (!e()) {
            B0((int) (I() < 0.0f ? C() : B()));
            this.d.j();
            if (!isVisible()) {
                this.y = c.NONE;
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: T */
    public /* synthetic */ void U(c0 c2) {
        t0();
    }

    public void L0(int minFrame) {
        if (this.c == null) {
            this.z.add(new r(this, minFrame));
        } else {
            this.d.D(minFrame);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: h0 */
    public /* synthetic */ void i0(int minFrame, c0 c2) {
        L0(minFrame);
    }

    public float C() {
        return this.d.o();
    }

    public void N0(float minProgress) {
        c0 c0Var = this.c;
        if (c0Var == null) {
            this.z.add(new n(this, minProgress));
        } else {
            L0((int) g.i(c0Var.p(), this.c.f(), minProgress));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: l0 */
    public /* synthetic */ void m0(float minProgress, c0 c2) {
        N0(minProgress);
    }

    public void G0(int maxFrame) {
        if (this.c == null) {
            this.z.add(new v(this, maxFrame));
        } else {
            this.d.B(((float) maxFrame) + 0.99f);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: X */
    public /* synthetic */ void Y(int maxFrame, c0 c2) {
        G0(maxFrame);
    }

    public float B() {
        return this.d.n();
    }

    public void I0(@FloatRange(from = 0.0d, to = 1.0d) float maxProgress) {
        c0 c0Var = this.c;
        if (c0Var == null) {
            this.z.add(new l(this, maxProgress));
        } else {
            this.d.B(g.i(c0Var.p(), this.c.f(), maxProgress));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b0 */
    public /* synthetic */ void c0(float maxProgress, c0 c2) {
        I0(maxProgress);
    }

    public void M0(String markerName) {
        c0 c0Var = this.c;
        if (c0Var == null) {
            this.z.add(new p(this, markerName));
            return;
        }
        h marker = c0Var.l(markerName);
        if (marker != null) {
            L0((int) marker.b);
            return;
        }
        throw new IllegalArgumentException("Cannot find marker with name " + markerName + ".");
    }

    /* access modifiers changed from: private */
    /* renamed from: j0 */
    public /* synthetic */ void k0(String markerName, c0 c2) {
        M0(markerName);
    }

    public void H0(String markerName) {
        c0 c0Var = this.c;
        if (c0Var == null) {
            this.z.add(new s(this, markerName));
            return;
        }
        h marker = c0Var.l(markerName);
        if (marker != null) {
            G0((int) (marker.b + marker.c));
            return;
        }
        throw new IllegalArgumentException("Cannot find marker with name " + markerName + ".");
    }

    /* access modifiers changed from: private */
    /* renamed from: Z */
    public /* synthetic */ void a0(String markerName, c0 c2) {
        H0(markerName);
    }

    public void K0(String markerName) {
        c0 c0Var = this.c;
        if (c0Var == null) {
            this.z.add(new w(this, markerName));
            return;
        }
        h marker = c0Var.l(markerName);
        if (marker != null) {
            int startFrame = (int) marker.b;
            J0(startFrame, ((int) marker.c) + startFrame);
            return;
        }
        throw new IllegalArgumentException("Cannot find marker with name " + markerName + ".");
    }

    /* access modifiers changed from: private */
    /* renamed from: f0 */
    public /* synthetic */ void g0(String markerName, c0 c2) {
        K0(markerName);
    }

    public void J0(int minFrame, int maxFrame) {
        if (this.c == null) {
            this.z.add(new q(this, minFrame, maxFrame));
        } else {
            this.d.C((float) minFrame, ((float) maxFrame) + 0.99f);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d0 */
    public /* synthetic */ void e0(int minFrame, int maxFrame, c0 c2) {
        J0(minFrame, maxFrame);
    }

    public void V0(float speed) {
        this.d.F(speed);
    }

    public float I() {
        return this.d.p();
    }

    public void c(Animator.AnimatorListener listener) {
        this.d.addListener(listener);
    }

    public void B0(int frame) {
        if (this.c == null) {
            this.z.add(new u(this, frame));
        } else {
            this.d.A((float) frame);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: V */
    public /* synthetic */ void W(int frame, c0 c2) {
        B0(frame);
    }

    public int w() {
        return (int) this.d.l();
    }

    public void Q0(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        if (this.c == null) {
            this.z.add(new o(this, progress));
            return;
        }
        b0.a("Drawable#setProgress");
        this.d.A(this.c.h(progress));
        b0.b("Drawable#setProgress");
    }

    /* access modifiers changed from: private */
    /* renamed from: n0 */
    public /* synthetic */ void o0(float progress, c0 c2) {
        Q0(progress);
    }

    public void T0(int mode) {
        this.d.setRepeatMode(mode);
    }

    @SuppressLint({"WrongConstant"})
    public int H() {
        return this.d.getRepeatMode();
    }

    public void S0(int count) {
        this.d.setRepeatCount(count);
    }

    public int G() {
        return this.d.getRepeatCount();
    }

    public boolean M() {
        e eVar = this.d;
        if (eVar == null) {
            return false;
        }
        return eVar.isRunning();
    }

    /* access modifiers changed from: package-private */
    public boolean N() {
        if (isVisible()) {
            return this.d.isRunning();
        }
        c cVar = this.y;
        return cVar == c.PLAY || cVar == c.RESUME;
    }

    private boolean e() {
        return this.f || this.q;
    }

    public void W0(Boolean areEnabled) {
        this.f = areEnabled.booleanValue();
    }

    public void C0(boolean ignore) {
        this.q = ignore;
    }

    public void Y0(boolean useCompositionFrameRate) {
        this.d.G(useCompositionFrameRate);
    }

    public void D0(a0 assetDelegate) {
        this.a2 = assetDelegate;
        com.airbnb.lottie.manager.b bVar = this.a1;
        if (bVar != null) {
            bVar.d(assetDelegate);
        }
    }

    public void z0(z assetDelegate) {
        com.airbnb.lottie.manager.a aVar = this.p2;
        if (aVar != null) {
            aVar.d(assetDelegate);
        }
    }

    public void A0(@Nullable Map<String, Typeface> fontMap) {
        if (fontMap != this.p3) {
            this.p3 = fontMap;
            invalidateSelf();
        }
    }

    public void X0(q0 textDelegate) {
    }

    @Nullable
    public q0 J() {
        return this.A4;
    }

    public boolean Z0() {
        return this.p3 == null && this.A4 == null && this.c.c().size() > 0;
    }

    public c0 t() {
        return this.c;
    }

    public void g() {
        this.z.clear();
        this.d.cancel();
        if (!isVisible()) {
            this.y = c.NONE;
        }
    }

    public void p0() {
        this.z.clear();
        this.d.r();
        if (!isVisible()) {
            this.y = c.NONE;
        }
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float E() {
        return this.d.k();
    }

    public int getIntrinsicWidth() {
        c0 c0Var = this.c;
        if (c0Var == null) {
            return -1;
        }
        return c0Var.b().width();
    }

    public int getIntrinsicHeight() {
        c0 c0Var = this.c;
        if (c0Var == null) {
            return -1;
        }
        return c0Var.b().height();
    }

    public List<com.airbnb.lottie.model.e> s0(com.airbnb.lottie.model.e keyPath) {
        if (this.E4 == null) {
            d.c("Cannot resolve KeyPath. Composition is not set yet.");
            return Collections.emptyList();
        }
        List<KeyPath> keyPaths = new ArrayList<>();
        this.E4.e(keyPath, 0, keyPaths, new com.airbnb.lottie.model.e(new String[0]));
        return keyPaths;
    }

    public <T> void d(com.airbnb.lottie.model.e keyPath, T property, @Nullable com.airbnb.lottie.value.c<T> callback) {
        int invalidate;
        com.airbnb.lottie.model.layer.c cVar = this.E4;
        if (cVar == null) {
            this.z.add(new t(this, keyPath, property, callback));
            return;
        }
        if (keyPath == com.airbnb.lottie.model.e.a) {
            cVar.d(property, callback);
            invalidate = 1;
        } else if (keyPath.d() != null) {
            keyPath.d().d(property, callback);
            invalidate = 1;
        } else {
            List<com.airbnb.lottie.model.e> s0 = s0(keyPath);
            for (int i = 0; i < s0.size(); i++) {
                s0.get(i).d().d(property, callback);
            }
            invalidate = s0.isEmpty() ^ 1;
        }
        if (invalidate != 0) {
            invalidateSelf();
            if (property == j0.E) {
                Q0(E());
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: P */
    public /* synthetic */ void Q(com.airbnb.lottie.model.e keyPath, Object property, com.airbnb.lottie.value.c callback, c0 c2) {
        d(keyPath, property, callback);
    }

    @Nullable
    public Bitmap r(String id) {
        com.airbnb.lottie.manager.b assetManager = x();
        if (assetManager != null) {
            return assetManager.a(id);
        }
        return null;
    }

    @Nullable
    public f0 z(String id) {
        c0 composition = this.c;
        if (composition == null) {
            return null;
        }
        return composition.j().get(id);
    }

    private com.airbnb.lottie.manager.b x() {
        com.airbnb.lottie.manager.b bVar = this.a1;
        if (bVar != null && !bVar.b(u())) {
            this.a1 = null;
        }
        if (this.a1 == null) {
            this.a1 = new com.airbnb.lottie.manager.b(getCallback(), this.p1, this.a2, this.c.j());
        }
        return this.a1;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Typeface K(com.airbnb.lottie.model.c font) {
        Map<String, Typeface> fontMap = this.p3;
        if (fontMap != null) {
            String key = font.a();
            if (fontMap.containsKey(key)) {
                return fontMap.get(key);
            }
            String key2 = font.b();
            if (fontMap.containsKey(key2)) {
                return fontMap.get(key2);
            }
            String key3 = font.a() + "-" + font.c();
            if (fontMap.containsKey(key3)) {
                return fontMap.get(key3);
            }
        }
        com.airbnb.lottie.manager.a assetManager = v();
        if (assetManager != null) {
            return assetManager.b(font);
        }
        return null;
    }

    private com.airbnb.lottie.manager.a v() {
        if (getCallback() == null) {
            return null;
        }
        if (this.p2 == null) {
            com.airbnb.lottie.manager.a aVar = new com.airbnb.lottie.manager.a(getCallback(), this.z4);
            this.p2 = aVar;
            if (this.p4 != null) {
                aVar.c(this.p4);
            }
        }
        return this.p2;
    }

    public void y0(String extension) {
        this.p4 = extension;
        com.airbnb.lottie.manager.a fam = v();
        if (fam != null) {
            fam.c(extension);
        }
    }

    @Nullable
    private Context u() {
        Drawable.Callback callback = getCallback();
        if (callback != null && (callback instanceof View)) {
            return ((View) callback).getContext();
        }
        return null;
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean wasNotVisibleAlready = !isVisible();
        boolean ret = super.setVisible(visible, restart);
        if (visible) {
            c cVar = this.y;
            if (cVar == c.PLAY) {
                q0();
            } else if (cVar == c.RESUME) {
                t0();
            }
        } else if (this.d.isRunning()) {
            p0();
            this.y = c.RESUME;
        } else if (!wasNotVisibleAlready) {
            this.y = c.NONE;
        }
        return ret;
    }

    public void invalidateDrawable(@NonNull Drawable who) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    private void l(Canvas canvas) {
        com.airbnb.lottie.model.layer.c compositionLayer = this.E4;
        c0 composition = this.c;
        if (compositionLayer != null && composition != null) {
            this.L4.reset();
            Rect bounds = getBounds();
            if (!bounds.isEmpty()) {
                this.L4.preScale(((float) bounds.width()) / ((float) composition.b().width()), ((float) bounds.height()) / ((float) composition.b().height()));
                this.L4.preTranslate((float) bounds.left, (float) bounds.top);
            }
            compositionLayer.h(canvas, this.L4, this.F4);
        }
    }

    private void r0(Canvas originalCanvas, com.airbnb.lottie.model.layer.c compositionLayer) {
        if (this.c != null && compositionLayer != null) {
            q();
            originalCanvas.getMatrix(this.V4);
            originalCanvas.getClipBounds(this.O4);
            j(this.O4, this.P4);
            this.V4.mapRect(this.P4);
            k(this.P4, this.O4);
            if (this.D4) {
                this.U4.set(0.0f, 0.0f, (float) getIntrinsicWidth(), (float) getIntrinsicHeight());
            } else {
                compositionLayer.f(this.U4, (Matrix) null, false);
            }
            this.V4.mapRect(this.U4);
            Rect bounds = getBounds();
            float scaleX = ((float) bounds.width()) / ((float) getIntrinsicWidth());
            float scaleY = ((float) bounds.height()) / ((float) getIntrinsicHeight());
            u0(this.U4, scaleX, scaleY);
            if (!L()) {
                RectF rectF = this.U4;
                Rect rect = this.O4;
                rectF.intersect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
            }
            int renderWidth = (int) Math.ceil((double) this.U4.width());
            int renderHeight = (int) Math.ceil((double) this.U4.height());
            if (renderWidth != 0 && renderHeight != 0) {
                p(renderWidth, renderHeight);
                if (this.X4) {
                    this.L4.set(this.V4);
                    this.L4.preScale(scaleX, scaleY);
                    Matrix matrix = this.L4;
                    RectF rectF2 = this.U4;
                    matrix.postTranslate(-rectF2.left, -rectF2.top);
                    this.M4.eraseColor(0);
                    compositionLayer.h(this.N4, this.L4, this.F4);
                    this.V4.invert(this.W4);
                    this.W4.mapRect(this.T4, this.U4);
                    k(this.T4, this.S4);
                }
                this.R4.set(0, 0, renderWidth, renderHeight);
                originalCanvas.drawBitmap(this.M4, this.R4, this.S4, this.Q4);
            }
        }
    }

    private void q() {
        if (this.N4 == null) {
            this.N4 = new Canvas();
            this.U4 = new RectF();
            this.V4 = new Matrix();
            this.W4 = new Matrix();
            this.O4 = new Rect();
            this.P4 = new RectF();
            this.Q4 = new com.airbnb.lottie.animation.a();
            this.R4 = new Rect();
            this.S4 = new Rect();
            this.T4 = new RectF();
        }
    }

    private void p(int renderWidth, int renderHeight) {
        Bitmap bitmap = this.M4;
        if (bitmap == null || bitmap.getWidth() < renderWidth || this.M4.getHeight() < renderHeight) {
            Bitmap createBitmap = Bitmap.createBitmap(renderWidth, renderHeight, Bitmap.Config.ARGB_8888);
            this.M4 = createBitmap;
            this.N4.setBitmap(createBitmap);
            this.X4 = true;
        } else if (this.M4.getWidth() > renderWidth || this.M4.getHeight() > renderHeight) {
            Bitmap createBitmap2 = Bitmap.createBitmap(this.M4, 0, 0, renderWidth, renderHeight);
            this.M4 = createBitmap2;
            this.N4.setBitmap(createBitmap2);
            this.X4 = true;
        }
    }

    private void k(RectF src, Rect dst) {
        dst.set((int) Math.floor((double) src.left), (int) Math.floor((double) src.top), (int) Math.ceil((double) src.right), (int) Math.ceil((double) src.bottom));
    }

    private void j(Rect src, RectF dst) {
        dst.set((float) src.left, (float) src.top, (float) src.right, (float) src.bottom);
    }

    private void u0(RectF rect, float scaleX, float scaleY) {
        rect.set(rect.left * scaleX, rect.top * scaleY, rect.right * scaleX, rect.bottom * scaleY);
    }

    private boolean L() {
        Drawable.Callback callback = getCallback();
        if (!(callback instanceof View)) {
            return false;
        }
        ViewParent parent = ((View) callback).getParent();
        if (Build.VERSION.SDK_INT < 18 || !(parent instanceof ViewGroup)) {
            return false;
        }
        return !((ViewGroup) parent).getClipChildren();
    }
}
