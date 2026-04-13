package com.airbnb.lottie;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.utils.d;
import com.airbnb.lottie.utils.h;
import com.airbnb.lottie.value.c;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LottieAnimationView extends AppCompatImageView {
    private static final String c = LottieAnimationView.class.getSimpleName();
    /* access modifiers changed from: private */
    public static final g0<Throwable> d = a.a;
    @Nullable
    private c0 A4;
    @RawRes
    private int a1;
    private boolean a2 = false;
    private final g0<c0> f = new y(this);
    private String p0;
    private boolean p1 = false;
    private boolean p2 = true;
    private final Set<b> p3 = new HashSet();
    private final Set<i0> p4 = new HashSet();
    private final g0<Throwable> q = new a();
    /* access modifiers changed from: private */
    @Nullable
    public g0<Throwable> x;
    /* access modifiers changed from: private */
    @DrawableRes
    public int y = 0;
    private final e0 z = new e0();
    @Nullable
    private l0<c0> z4;

    public enum b {
        SET_ANIMATION,
        SET_PROGRESS,
        SET_REPEAT_MODE,
        SET_REPEAT_COUNT,
        SET_IMAGE_ASSETS,
        PLAY_OPTION
    }

    static /* synthetic */ void s(Throwable throwable) {
        if (h.k(throwable)) {
            d.d("Unable to load composition.", throwable);
            return;
        }
        throw new IllegalStateException("Unable to parse composition", throwable);
    }

    public class a implements g0<Throwable> {
        a() {
        }

        /* renamed from: a */
        public void onResult(Throwable result) {
            if (LottieAnimationView.this.y != 0) {
                LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                lottieAnimationView.setImageResource(lottieAnimationView.y);
            }
            (LottieAnimationView.this.x == null ? LottieAnimationView.d : LottieAnimationView.this.x).onResult(result);
        }
    }

    public LottieAnimationView(Context context) {
        super(context);
        m((AttributeSet) null, R$attr.lottieAnimationViewStyle);
    }

    public LottieAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m(attrs, R$attr.lottieAnimationViewStyle);
    }

    public LottieAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        m(attrs, defStyleAttr);
    }

    private void m(@Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        String url;
        boolean z2 = false;
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R$styleable.LottieAnimationView, defStyleAttr, 0);
        this.p2 = ta.getBoolean(R$styleable.LottieAnimationView_lottie_cacheComposition, true);
        int i = R$styleable.LottieAnimationView_lottie_rawRes;
        boolean hasRawRes = ta.hasValue(i);
        int i2 = R$styleable.LottieAnimationView_lottie_fileName;
        boolean hasFileName = ta.hasValue(i2);
        int i3 = R$styleable.LottieAnimationView_lottie_url;
        boolean hasUrl = ta.hasValue(i3);
        if (!hasRawRes || !hasFileName) {
            if (hasRawRes) {
                int rawResId = ta.getResourceId(i, 0);
                if (rawResId != 0) {
                    setAnimation(rawResId);
                }
            } else if (hasFileName) {
                String fileName = ta.getString(i2);
                if (fileName != null) {
                    setAnimation(fileName);
                }
            } else if (hasUrl && (url = ta.getString(i3)) != null) {
                setAnimationFromUrl(url);
            }
            setFallbackResource(ta.getResourceId(R$styleable.LottieAnimationView_lottie_fallbackRes, 0));
            if (ta.getBoolean(R$styleable.LottieAnimationView_lottie_autoPlay, false)) {
                this.a2 = true;
            }
            if (ta.getBoolean(R$styleable.LottieAnimationView_lottie_loop, false)) {
                this.z.S0(-1);
            }
            int i4 = R$styleable.LottieAnimationView_lottie_repeatMode;
            if (ta.hasValue(i4)) {
                setRepeatMode(ta.getInt(i4, 1));
            }
            int i5 = R$styleable.LottieAnimationView_lottie_repeatCount;
            if (ta.hasValue(i5)) {
                setRepeatCount(ta.getInt(i5, -1));
            }
            int i6 = R$styleable.LottieAnimationView_lottie_speed;
            if (ta.hasValue(i6)) {
                setSpeed(ta.getFloat(i6, 1.0f));
            }
            int i7 = R$styleable.LottieAnimationView_lottie_clipToCompositionBounds;
            if (ta.hasValue(i7)) {
                setClipToCompositionBounds(ta.getBoolean(i7, true));
            }
            int i8 = R$styleable.LottieAnimationView_lottie_defaultFontFileExtension;
            if (ta.hasValue(i8)) {
                setDefaultFontFileExtension(ta.getString(i8));
            }
            setImageAssetsFolder(ta.getString(R$styleable.LottieAnimationView_lottie_imageAssetsFolder));
            int i9 = R$styleable.LottieAnimationView_lottie_progress;
            y(ta.getFloat(i9, 0.0f), ta.hasValue(i9));
            j(ta.getBoolean(R$styleable.LottieAnimationView_lottie_enableMergePathsForKitKatAndAbove, false));
            int i10 = R$styleable.LottieAnimationView_lottie_colorFilter;
            if (ta.hasValue(i10)) {
                p0 filter = new p0(AppCompatResources.getColorStateList(getContext(), ta.getResourceId(i10, -1)).getDefaultColor());
                f(new e("**"), j0.K, new c<>(filter));
            }
            int colorRes = R$styleable.LottieAnimationView_lottie_renderMode;
            if (ta.hasValue(colorRes)) {
                o0 o0Var = o0.AUTOMATIC;
                int renderModeOrdinal = ta.getInt(colorRes, o0Var.ordinal());
                if (renderModeOrdinal >= o0.values().length) {
                    renderModeOrdinal = o0Var.ordinal();
                }
                setRenderMode(o0.values()[renderModeOrdinal]);
            }
            setIgnoreDisabledSystemAnimations(ta.getBoolean(R$styleable.LottieAnimationView_lottie_ignoreDisabledSystemAnimations, false));
            int i11 = R$styleable.LottieAnimationView_lottie_useCompositionFrameRate;
            if (ta.hasValue(i11)) {
                setUseCompositionFrameRate(ta.getBoolean(i11, false));
            }
            ta.recycle();
            e0 e0Var = this.z;
            if (h.f(getContext()) != 0.0f) {
                z2 = true;
            }
            e0Var.W0(Boolean.valueOf(z2));
            return;
        }
        throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use only one at once.");
    }

    public void setImageResource(int resId) {
        h();
        super.setImageResource(resId);
    }

    public void setImageDrawable(Drawable drawable) {
        h();
        super.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap bm) {
        h();
        super.setImageBitmap(bm);
    }

    public void unscheduleDrawable(Drawable who) {
        e0 e0Var;
        if (!this.p1 && who == (e0Var = this.z) && e0Var.M()) {
            t();
        } else if (!this.p1 && (who instanceof e0) && ((e0) who).M()) {
            ((e0) who).p0();
        }
        super.unscheduleDrawable(who);
    }

    public void invalidate() {
        super.invalidate();
        Drawable d2 = getDrawable();
        if ((d2 instanceof e0) && ((e0) d2).F() == o0.SOFTWARE) {
            this.z.invalidateSelf();
        }
    }

    public void invalidateDrawable(@NonNull Drawable dr) {
        Drawable drawable = getDrawable();
        e0 e0Var = this.z;
        if (drawable == e0Var) {
            super.invalidateDrawable(e0Var);
        } else {
            super.invalidateDrawable(dr);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.c = this.p0;
        ss.d = this.a1;
        ss.f = this.z.E();
        ss.q = this.z.N();
        ss.x = this.z.y();
        ss.y = this.z.H();
        ss.z = this.z.G();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        int i;
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.p0 = ss.c;
        Set<b> set = this.p3;
        b bVar = b.SET_ANIMATION;
        if (!set.contains(bVar) && !TextUtils.isEmpty(this.p0)) {
            setAnimation(this.p0);
        }
        this.a1 = ss.d;
        if (!this.p3.contains(bVar) && (i = this.a1) != 0) {
            setAnimation(i);
        }
        if (!this.p3.contains(b.SET_PROGRESS)) {
            y(ss.f, false);
        }
        if (!this.p3.contains(b.PLAY_OPTION) && ss.q) {
            u();
        }
        if (!this.p3.contains(b.SET_IMAGE_ASSETS)) {
            setImageAssetsFolder(ss.x);
        }
        if (!this.p3.contains(b.SET_REPEAT_MODE)) {
            setRepeatMode(ss.y);
        }
        if (!this.p3.contains(b.SET_REPEAT_COUNT)) {
            setRepeatCount(ss.z);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode() && this.a2) {
            this.z.q0();
        }
    }

    public void setIgnoreDisabledSystemAnimations(boolean ignore) {
        this.z.C0(ignore);
    }

    public void setUseCompositionFrameRate(boolean useCompositionFrameRate) {
        this.z.Y0(useCompositionFrameRate);
    }

    public void j(boolean enable) {
        this.z.m(enable);
    }

    public void setClipToCompositionBounds(boolean clipToCompositionBounds) {
        this.z.w0(clipToCompositionBounds);
    }

    public boolean getClipToCompositionBounds() {
        return this.z.s();
    }

    public void setCacheComposition(boolean cacheComposition) {
        this.p2 = cacheComposition;
    }

    public void setOutlineMasksAndMattes(boolean outline) {
        this.z.O0(outline);
    }

    public void setAnimation(@RawRes int rawRes) {
        this.a1 = rawRes;
        this.p0 = null;
        setCompositionTask(l(rawRes));
    }

    private l0<c0> l(@RawRes int rawRes) {
        if (isInEditMode()) {
            return new l0<>(new c(this, rawRes), true);
        }
        return this.p2 ? d0.l(getContext(), rawRes) : d0.m(getContext(), rawRes, (String) null);
    }

    /* access modifiers changed from: private */
    /* renamed from: q */
    public /* synthetic */ k0 r(int rawRes) {
        return this.p2 ? d0.n(getContext(), rawRes) : d0.o(getContext(), rawRes, (String) null);
    }

    public void setAnimation(String assetName) {
        this.p0 = assetName;
        this.a1 = 0;
        setCompositionTask(k(assetName));
    }

    private l0<c0> k(String assetName) {
        if (isInEditMode()) {
            return new l0<>(new b(this, assetName), true);
        }
        return this.p2 ? d0.c(getContext(), assetName) : d0.d(getContext(), assetName, (String) null);
    }

    /* access modifiers changed from: private */
    /* renamed from: o */
    public /* synthetic */ k0 p(String assetName) {
        return this.p2 ? d0.e(getContext(), assetName) : d0.f(getContext(), assetName, (String) null);
    }

    @Deprecated
    public void setAnimationFromJson(String jsonString) {
        w(jsonString, (String) null);
    }

    public void w(String jsonString, @Nullable String cacheKey) {
        v(new ByteArrayInputStream(jsonString.getBytes()), cacheKey);
    }

    public void v(InputStream stream, @Nullable String cacheKey) {
        setCompositionTask(d0.g(stream, cacheKey));
    }

    public void setAnimationFromUrl(String url) {
        setCompositionTask(this.p2 ? d0.p(getContext(), url) : d0.q(getContext(), url, (String) null));
    }

    public void setFailureListener(@Nullable g0<Throwable> failureListener) {
        this.x = failureListener;
    }

    public void setFallbackResource(@DrawableRes int fallbackResource) {
        this.y = fallbackResource;
    }

    private void setCompositionTask(l0<c0> compositionTask) {
        this.p3.add(b.SET_ANIMATION);
        i();
        h();
        this.z4 = compositionTask.c(this.f).b(this.q);
    }

    private void h() {
        l0<c0> l0Var = this.z4;
        if (l0Var != null) {
            l0Var.j(this.f);
            this.z4.i(this.q);
        }
    }

    public void setComposition(@NonNull c0 composition) {
        if (b0.a) {
            String str = c;
            Log.v(str, "Set Composition \n" + composition);
        }
        this.z.setCallback(this);
        this.A4 = composition;
        this.p1 = true;
        boolean isNewComposition = this.z.x0(composition);
        this.p1 = false;
        if (getDrawable() != this.z || isNewComposition) {
            if (!isNewComposition) {
                x();
            }
            onVisibilityChanged(this, getVisibility());
            requestLayout();
            for (i0 lottieOnCompositionLoadedListener : this.p4) {
                lottieOnCompositionLoadedListener.a(composition);
            }
        }
    }

    @Nullable
    public c0 getComposition() {
        return this.A4;
    }

    @MainThread
    public void u() {
        this.p3.add(b.PLAY_OPTION);
        this.z.q0();
    }

    public void setMinFrame(int startFrame) {
        this.z.L0(startFrame);
    }

    public float getMinFrame() {
        return this.z.C();
    }

    public void setMinProgress(float startProgress) {
        this.z.N0(startProgress);
    }

    public void setMaxFrame(int endFrame) {
        this.z.G0(endFrame);
    }

    public float getMaxFrame() {
        return this.z.B();
    }

    public void setMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) float endProgress) {
        this.z.I0(endProgress);
    }

    public void setMinFrame(String markerName) {
        this.z.M0(markerName);
    }

    public void setMaxFrame(String markerName) {
        this.z.H0(markerName);
    }

    public void setMinAndMaxFrame(String markerName) {
        this.z.K0(markerName);
    }

    public void setSpeed(float speed) {
        this.z.V0(speed);
    }

    public float getSpeed() {
        return this.z.I();
    }

    public void d(Animator.AnimatorListener listener) {
        this.z.c(listener);
    }

    public void setRepeatMode(int mode) {
        this.p3.add(b.SET_REPEAT_MODE);
        this.z.T0(mode);
    }

    public int getRepeatMode() {
        return this.z.H();
    }

    public void setRepeatCount(int count) {
        this.p3.add(b.SET_REPEAT_COUNT);
        this.z.S0(count);
    }

    public int getRepeatCount() {
        return this.z.G();
    }

    public boolean n() {
        return this.z.M();
    }

    public void setImageAssetsFolder(String imageAssetsFolder) {
        this.z.E0(imageAssetsFolder);
    }

    @Nullable
    public String getImageAssetsFolder() {
        return this.z.y();
    }

    public void setMaintainOriginalImageBounds(boolean maintainOriginalImageBounds) {
        this.z.F0(maintainOriginalImageBounds);
    }

    public boolean getMaintainOriginalImageBounds() {
        return this.z.A();
    }

    public void setImageAssetDelegate(a0 assetDelegate) {
        this.z.D0(assetDelegate);
    }

    public void setDefaultFontFileExtension(String extension) {
        this.z.y0(extension);
    }

    public void setFontAssetDelegate(z assetDelegate) {
        this.z.z0(assetDelegate);
    }

    public void setFontMap(@Nullable Map<String, Typeface> fontMap) {
        this.z.A0(fontMap);
    }

    public void setTextDelegate(q0 textDelegate) {
        this.z.X0(textDelegate);
    }

    public <T> void f(e keyPath, T property, c<T> callback) {
        this.z.d(keyPath, property, callback);
    }

    @MainThread
    public void g() {
        this.p3.add(b.PLAY_OPTION);
        this.z.g();
    }

    @MainThread
    public void t() {
        this.a2 = false;
        this.z.p0();
    }

    public void setFrame(int frame) {
        this.z.B0(frame);
    }

    public int getFrame() {
        return this.z.w();
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        y(progress, true);
    }

    private void y(@FloatRange(from = 0.0d, to = 1.0d) float progress, boolean fromUser) {
        if (fromUser) {
            this.p3.add(b.SET_PROGRESS);
        }
        this.z.Q0(progress);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.z.E();
    }

    public long getDuration() {
        c0 c0Var = this.A4;
        if (c0Var != null) {
            return (long) c0Var.d();
        }
        return 0;
    }

    public void setPerformanceTrackingEnabled(boolean enabled) {
        this.z.P0(enabled);
    }

    @Nullable
    public n0 getPerformanceTracker() {
        return this.z.D();
    }

    private void i() {
        this.A4 = null;
        this.z.h();
    }

    public void setSafeMode(boolean safeMode) {
        this.z.U0(safeMode);
    }

    public void setRenderMode(o0 renderMode) {
        this.z.R0(renderMode);
    }

    public o0 getRenderMode() {
        return this.z.F();
    }

    public void setApplyingOpacityToLayersEnabled(boolean isApplyingOpacityToLayersEnabled) {
        this.z.v0(isApplyingOpacityToLayersEnabled);
    }

    public boolean e(@NonNull i0 lottieOnCompositionLoadedListener) {
        c0 composition = this.A4;
        if (composition != null) {
            lottieOnCompositionLoadedListener.a(composition);
        }
        return this.p4.add(lottieOnCompositionLoadedListener);
    }

    private void x() {
        boolean wasAnimating = n();
        setImageDrawable((Drawable) null);
        setImageDrawable(this.z);
        if (wasAnimating) {
            this.z.t0();
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        String c;
        int d;
        float f;
        boolean q;
        String x;
        int y;
        int z;

        /* synthetic */ SavedState(Parcel x0, a x1) {
            this(x0);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.c = in.readString();
            this.f = in.readFloat();
            this.q = in.readInt() != 1 ? false : true;
            this.x = in.readString();
            this.y = in.readInt();
            this.z = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.c);
            out.writeFloat(this.f);
            out.writeInt(this.q ? 1 : 0);
            out.writeString(this.x);
            out.writeInt(this.y);
            out.writeInt(this.z);
        }

        public class a implements Parcelable.Creator<SavedState> {
            a() {
            }

            /* renamed from: a */
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, (a) null);
            }

            /* renamed from: b */
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }
    }
}
