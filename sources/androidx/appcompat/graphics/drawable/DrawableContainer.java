package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.DrawableCompat;
import com.alibaba.fastjson.asm.Opcodes;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class DrawableContainer extends Drawable implements Drawable.Callback {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_DITHER = true;
    private static final String TAG = "DrawableContainer";
    private int mAlpha = 255;
    private Runnable mAnimationRunnable;
    private BlockInvalidateCallback mBlockInvalidateCallback;
    private int mCurIndex = -1;
    private Drawable mCurrDrawable;
    private DrawableContainerState mDrawableContainerState;
    private long mEnterAnimationEnd;
    private long mExitAnimationEnd;
    private boolean mHasAlpha;
    private Rect mHotspotBounds;
    private Drawable mLastDrawable;
    private boolean mMutated;

    DrawableContainer() {
    }

    public void draw(@NonNull Canvas canvas) {
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mDrawableContainerState.getChangingConfigurations();
    }

    private boolean needsMirroring() {
        if (!isAutoMirrored() || DrawableCompat.getLayoutDirection(this) != 1) {
            return false;
        }
        return true;
    }

    public boolean getPadding(@NonNull Rect padding) {
        boolean result;
        Rect r = this.mDrawableContainerState.getConstantPadding();
        if (r != null) {
            padding.set(r);
            result = (((r.left | r.top) | r.bottom) | r.right) != 0;
        } else {
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                result = drawable.getPadding(padding);
            } else {
                result = super.getPadding(padding);
            }
        }
        if (needsMirroring()) {
            int left = padding.left;
            padding.left = padding.right;
            padding.right = left;
        }
        return result;
    }

    @RequiresApi(21)
    public void getOutline(@NonNull Outline outline) {
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            Api21Impl.getOutline(drawable, outline);
        }
    }

    public void setAlpha(int alpha) {
        if (!this.mHasAlpha || this.mAlpha != alpha) {
            this.mHasAlpha = true;
            this.mAlpha = alpha;
            Drawable drawable = this.mCurrDrawable;
            if (drawable == null) {
                return;
            }
            if (this.mEnterAnimationEnd == 0) {
                drawable.setAlpha(alpha);
            } else {
                animate(false);
            }
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public void setDither(boolean dither) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState.mDither != dither) {
            drawableContainerState.mDither = dither;
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                drawable.setDither(dither);
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        drawableContainerState.mHasColorFilter = true;
        if (drawableContainerState.mColorFilter != colorFilter) {
            drawableContainerState.mColorFilter = colorFilter;
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    public void setTintList(ColorStateList tint) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        drawableContainerState.mHasTintList = true;
        if (drawableContainerState.mTintList != tint) {
            drawableContainerState.mTintList = tint;
            DrawableCompat.setTintList(this.mCurrDrawable, tint);
        }
    }

    public void setTintMode(@NonNull PorterDuff.Mode tintMode) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        drawableContainerState.mHasTintMode = true;
        if (drawableContainerState.mTintMode != tintMode) {
            drawableContainerState.mTintMode = tintMode;
            DrawableCompat.setTintMode(this.mCurrDrawable, tintMode);
        }
    }

    public void setEnterFadeDuration(int ms) {
        this.mDrawableContainerState.mEnterFadeDuration = ms;
    }

    public void setExitFadeDuration(int ms) {
        this.mDrawableContainerState.mExitFadeDuration = ms;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            drawable.setBounds(bounds);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(bounds);
        }
    }

    public boolean isStateful() {
        return this.mDrawableContainerState.isStateful();
    }

    public void setAutoMirrored(boolean mirrored) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState.mAutoMirrored != mirrored) {
            drawableContainerState.mAutoMirrored = mirrored;
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                DrawableCompat.setAutoMirrored(drawable, mirrored);
            }
        }
    }

    public boolean isAutoMirrored() {
        return this.mDrawableContainerState.mAutoMirrored;
    }

    public void jumpToCurrentState() {
        boolean changed = false;
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
            this.mLastDrawable = null;
            changed = true;
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
            if (this.mHasAlpha) {
                this.mCurrDrawable.setAlpha(this.mAlpha);
            }
        }
        if (this.mExitAnimationEnd != 0) {
            this.mExitAnimationEnd = 0;
            changed = true;
        }
        if (this.mEnterAnimationEnd != 0) {
            this.mEnterAnimationEnd = 0;
            changed = true;
        }
        if (changed) {
            invalidateSelf();
        }
    }

    public void setHotspot(float x, float y) {
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspot(drawable, x, y);
        }
    }

    public void setHotspotBounds(int left, int top, int right, int bottom) {
        Rect rect = this.mHotspotBounds;
        if (rect == null) {
            this.mHotspotBounds = new Rect(left, top, right, bottom);
        } else {
            rect.set(left, top, right, bottom);
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspotBounds(drawable, left, top, right, bottom);
        }
    }

    public void getHotspotBounds(@NonNull Rect outRect) {
        Rect rect = this.mHotspotBounds;
        if (rect != null) {
            outRect.set(rect);
        } else {
            super.getHotspotBounds(outRect);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            return drawable.setState(state);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.setState(state);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            return drawable.setLevel(level);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.setLevel(level);
        }
        return false;
    }

    public boolean onLayoutDirectionChanged(int layoutDirection) {
        return this.mDrawableContainerState.setLayoutDirection(layoutDirection, getCurrentIndex());
    }

    public int getIntrinsicWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantWidth();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return -1;
    }

    public int getIntrinsicHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantHeight();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return -1;
    }

    public int getMinimumWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumWidth();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getMinimumWidth();
        }
        return 0;
    }

    public int getMinimumHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumHeight();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getMinimumHeight();
        }
        return 0;
    }

    public void invalidateDrawable(@NonNull Drawable who) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState != null) {
            drawableContainerState.invalidateCache();
        }
        if (who == this.mCurrDrawable && getCallback() != null) {
            getCallback().invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
        if (who == this.mCurrDrawable && getCallback() != null) {
            getCallback().scheduleDrawable(this, what, when);
        }
    }

    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
        if (who == this.mCurrDrawable && getCallback() != null) {
            getCallback().unscheduleDrawable(this, what);
        }
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = super.setVisible(visible, restart);
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            drawable.setVisible(visible, restart);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.setVisible(visible, restart);
        }
        return changed;
    }

    public int getOpacity() {
        Drawable drawable = this.mCurrDrawable;
        if (drawable == null || !drawable.isVisible()) {
            return -2;
        }
        return this.mDrawableContainerState.getOpacity();
    }

    /* access modifiers changed from: package-private */
    public void setCurrentIndex(int index) {
        selectDrawable(index);
    }

    /* access modifiers changed from: package-private */
    public int getCurrentIndex() {
        return this.mCurIndex;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0073  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean selectDrawable(int r10) {
        /*
            r9 = this;
            int r0 = r9.mCurIndex
            r1 = 0
            if (r10 != r0) goto L_0x0006
            return r1
        L_0x0006:
            long r2 = android.os.SystemClock.uptimeMillis()
            androidx.appcompat.graphics.drawable.DrawableContainer$DrawableContainerState r0 = r9.mDrawableContainerState
            int r0 = r0.mExitFadeDuration
            r4 = 0
            r5 = 0
            if (r0 <= 0) goto L_0x002e
            android.graphics.drawable.Drawable r0 = r9.mLastDrawable
            if (r0 == 0) goto L_0x001a
            r0.setVisible(r1, r1)
        L_0x001a:
            android.graphics.drawable.Drawable r0 = r9.mCurrDrawable
            if (r0 == 0) goto L_0x0029
            r9.mLastDrawable = r0
            androidx.appcompat.graphics.drawable.DrawableContainer$DrawableContainerState r0 = r9.mDrawableContainerState
            int r0 = r0.mExitFadeDuration
            long r0 = (long) r0
            long r0 = r0 + r2
            r9.mExitAnimationEnd = r0
            goto L_0x0035
        L_0x0029:
            r9.mLastDrawable = r4
            r9.mExitAnimationEnd = r5
            goto L_0x0035
        L_0x002e:
            android.graphics.drawable.Drawable r0 = r9.mCurrDrawable
            if (r0 == 0) goto L_0x0035
            r0.setVisible(r1, r1)
        L_0x0035:
            if (r10 < 0) goto L_0x0055
            androidx.appcompat.graphics.drawable.DrawableContainer$DrawableContainerState r0 = r9.mDrawableContainerState
            int r1 = r0.mNumChildren
            if (r10 >= r1) goto L_0x0055
            android.graphics.drawable.Drawable r0 = r0.getChild(r10)
            r9.mCurrDrawable = r0
            r9.mCurIndex = r10
            if (r0 == 0) goto L_0x0054
            androidx.appcompat.graphics.drawable.DrawableContainer$DrawableContainerState r1 = r9.mDrawableContainerState
            int r1 = r1.mEnterFadeDuration
            if (r1 <= 0) goto L_0x0051
            long r7 = (long) r1
            long r7 = r7 + r2
            r9.mEnterAnimationEnd = r7
        L_0x0051:
            r9.initializeDrawableForDisplay(r0)
        L_0x0054:
            goto L_0x005a
        L_0x0055:
            r9.mCurrDrawable = r4
            r0 = -1
            r9.mCurIndex = r0
        L_0x005a:
            long r0 = r9.mEnterAnimationEnd
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            r1 = 1
            if (r0 != 0) goto L_0x0067
            long r7 = r9.mExitAnimationEnd
            int r0 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x0079
        L_0x0067:
            java.lang.Runnable r0 = r9.mAnimationRunnable
            if (r0 != 0) goto L_0x0073
            androidx.appcompat.graphics.drawable.DrawableContainer$1 r0 = new androidx.appcompat.graphics.drawable.DrawableContainer$1
            r0.<init>()
            r9.mAnimationRunnable = r0
            goto L_0x0076
        L_0x0073:
            r9.unscheduleSelf(r0)
        L_0x0076:
            r9.animate(r1)
        L_0x0079:
            r9.invalidateSelf()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.graphics.drawable.DrawableContainer.selectDrawable(int):boolean");
    }

    private void initializeDrawableForDisplay(Drawable d) {
        if (this.mBlockInvalidateCallback == null) {
            this.mBlockInvalidateCallback = new BlockInvalidateCallback();
        }
        d.setCallback(this.mBlockInvalidateCallback.wrap(d.getCallback()));
        try {
            if (this.mDrawableContainerState.mEnterFadeDuration <= 0 && this.mHasAlpha) {
                d.setAlpha(this.mAlpha);
            }
            DrawableContainerState drawableContainerState = this.mDrawableContainerState;
            if (drawableContainerState.mHasColorFilter) {
                d.setColorFilter(drawableContainerState.mColorFilter);
            } else {
                if (drawableContainerState.mHasTintList) {
                    DrawableCompat.setTintList(d, drawableContainerState.mTintList);
                }
                DrawableContainerState drawableContainerState2 = this.mDrawableContainerState;
                if (drawableContainerState2.mHasTintMode) {
                    DrawableCompat.setTintMode(d, drawableContainerState2.mTintMode);
                }
            }
            d.setVisible(isVisible(), true);
            d.setDither(this.mDrawableContainerState.mDither);
            d.setState(getState());
            d.setLevel(getLevel());
            d.setBounds(getBounds());
            int i = Build.VERSION.SDK_INT;
            if (i >= 23) {
                DrawableCompat.setLayoutDirection(d, DrawableCompat.getLayoutDirection(this));
            }
            if (i >= 19) {
                DrawableCompat.setAutoMirrored(d, this.mDrawableContainerState.mAutoMirrored);
            }
            Rect hotspotBounds = this.mHotspotBounds;
            if (i >= 21 && hotspotBounds != null) {
                DrawableCompat.setHotspotBounds(d, hotspotBounds.left, hotspotBounds.top, hotspotBounds.right, hotspotBounds.bottom);
            }
        } finally {
            d.setCallback(this.mBlockInvalidateCallback.unwrap());
        }
    }

    /* access modifiers changed from: package-private */
    public void animate(boolean schedule) {
        this.mHasAlpha = true;
        long now = SystemClock.uptimeMillis();
        boolean animating = false;
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            long j = this.mEnterAnimationEnd;
            if (j != 0) {
                if (j <= now) {
                    drawable.setAlpha(this.mAlpha);
                    this.mEnterAnimationEnd = 0;
                } else {
                    drawable.setAlpha(((255 - (((int) ((j - now) * 255)) / this.mDrawableContainerState.mEnterFadeDuration)) * this.mAlpha) / 255);
                    animating = true;
                }
            }
        } else {
            this.mEnterAnimationEnd = 0;
        }
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            long j2 = this.mExitAnimationEnd;
            if (j2 != 0) {
                if (j2 <= now) {
                    drawable2.setVisible(false, false);
                    this.mLastDrawable = null;
                    this.mExitAnimationEnd = 0;
                } else {
                    drawable2.setAlpha((this.mAlpha * (((int) ((j2 - now) * 255)) / this.mDrawableContainerState.mExitFadeDuration)) / 255);
                    animating = true;
                }
            }
        } else {
            this.mExitAnimationEnd = 0;
        }
        if (schedule && animating) {
            scheduleSelf(this.mAnimationRunnable, 16 + now);
        }
    }

    @NonNull
    public Drawable getCurrent() {
        return this.mCurrDrawable;
    }

    /* access modifiers changed from: package-private */
    public final void updateDensity(Resources res) {
        this.mDrawableContainerState.updateDensity(res);
    }

    @RequiresApi(21)
    public void applyTheme(@NonNull Resources.Theme theme) {
        this.mDrawableContainerState.applyTheme(theme);
    }

    @RequiresApi(21)
    public boolean canApplyTheme() {
        return this.mDrawableContainerState.canApplyTheme();
    }

    public final Drawable.ConstantState getConstantState() {
        if (!this.mDrawableContainerState.canConstantState()) {
            return null;
        }
        this.mDrawableContainerState.mChangingConfigurations = getChangingConfigurations();
        return this.mDrawableContainerState;
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            DrawableContainerState clone = cloneConstantState();
            clone.mutate();
            setConstantState(clone);
            this.mMutated = true;
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public DrawableContainerState cloneConstantState() {
        return this.mDrawableContainerState;
    }

    /* access modifiers changed from: package-private */
    public void clearMutated() {
        this.mDrawableContainerState.clearMutated();
        this.mMutated = false;
    }

    public static abstract class DrawableContainerState extends Drawable.ConstantState {
        boolean mAutoMirrored;
        boolean mCanConstantState;
        int mChangingConfigurations;
        boolean mCheckedConstantSize;
        boolean mCheckedConstantState;
        boolean mCheckedOpacity;
        boolean mCheckedPadding;
        boolean mCheckedStateful;
        int mChildrenChangingConfigurations;
        ColorFilter mColorFilter;
        int mConstantHeight;
        int mConstantMinimumHeight;
        int mConstantMinimumWidth;
        Rect mConstantPadding;
        boolean mConstantSize = false;
        int mConstantWidth;
        int mDensity;
        boolean mDither = true;
        SparseArray<Drawable.ConstantState> mDrawableFutures;
        Drawable[] mDrawables;
        int mEnterFadeDuration = 0;
        int mExitFadeDuration = 0;
        boolean mHasColorFilter;
        boolean mHasTintList;
        boolean mHasTintMode;
        int mLayoutDirection;
        boolean mMutated;
        int mNumChildren;
        int mOpacity;
        final DrawableContainer mOwner;
        Resources mSourceRes;
        boolean mStateful;
        ColorStateList mTintList;
        PorterDuff.Mode mTintMode;
        boolean mVariablePadding = false;

        DrawableContainerState(DrawableContainerState orig, DrawableContainer owner, Resources res) {
            this.mOwner = owner;
            Rect rect = null;
            this.mSourceRes = res != null ? res : orig != null ? orig.mSourceRes : null;
            int resolveDensity = DrawableContainer.resolveDensity(res, orig != null ? orig.mDensity : 0);
            this.mDensity = resolveDensity;
            if (orig != null) {
                this.mChangingConfigurations = orig.mChangingConfigurations;
                this.mChildrenChangingConfigurations = orig.mChildrenChangingConfigurations;
                this.mCheckedConstantState = true;
                this.mCanConstantState = true;
                this.mVariablePadding = orig.mVariablePadding;
                this.mConstantSize = orig.mConstantSize;
                this.mDither = orig.mDither;
                this.mMutated = orig.mMutated;
                this.mLayoutDirection = orig.mLayoutDirection;
                this.mEnterFadeDuration = orig.mEnterFadeDuration;
                this.mExitFadeDuration = orig.mExitFadeDuration;
                this.mAutoMirrored = orig.mAutoMirrored;
                this.mColorFilter = orig.mColorFilter;
                this.mHasColorFilter = orig.mHasColorFilter;
                this.mTintList = orig.mTintList;
                this.mTintMode = orig.mTintMode;
                this.mHasTintList = orig.mHasTintList;
                this.mHasTintMode = orig.mHasTintMode;
                if (orig.mDensity == resolveDensity) {
                    if (orig.mCheckedPadding) {
                        this.mConstantPadding = orig.mConstantPadding != null ? new Rect(orig.mConstantPadding) : rect;
                        this.mCheckedPadding = true;
                    }
                    if (orig.mCheckedConstantSize) {
                        this.mConstantWidth = orig.mConstantWidth;
                        this.mConstantHeight = orig.mConstantHeight;
                        this.mConstantMinimumWidth = orig.mConstantMinimumWidth;
                        this.mConstantMinimumHeight = orig.mConstantMinimumHeight;
                        this.mCheckedConstantSize = true;
                    }
                }
                if (orig.mCheckedOpacity) {
                    this.mOpacity = orig.mOpacity;
                    this.mCheckedOpacity = true;
                }
                if (orig.mCheckedStateful) {
                    this.mStateful = orig.mStateful;
                    this.mCheckedStateful = true;
                }
                Drawable[] origDr = orig.mDrawables;
                this.mDrawables = new Drawable[origDr.length];
                this.mNumChildren = orig.mNumChildren;
                SparseArray<Drawable.ConstantState> origDf = orig.mDrawableFutures;
                if (origDf != null) {
                    this.mDrawableFutures = origDf.clone();
                } else {
                    this.mDrawableFutures = new SparseArray<>(this.mNumChildren);
                }
                int count = this.mNumChildren;
                for (int i = 0; i < count; i++) {
                    if (origDr[i] != null) {
                        Drawable.ConstantState cs = origDr[i].getConstantState();
                        if (cs != null) {
                            this.mDrawableFutures.put(i, cs);
                        } else {
                            this.mDrawables[i] = origDr[i];
                        }
                    }
                }
                return;
            }
            this.mDrawables = new Drawable[10];
            this.mNumChildren = 0;
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations | this.mChildrenChangingConfigurations;
        }

        public final int addChild(Drawable dr) {
            int pos = this.mNumChildren;
            if (pos >= this.mDrawables.length) {
                growArray(pos, pos + 10);
            }
            dr.mutate();
            dr.setVisible(false, true);
            dr.setCallback(this.mOwner);
            this.mDrawables[pos] = dr;
            this.mNumChildren++;
            this.mChildrenChangingConfigurations |= dr.getChangingConfigurations();
            invalidateCache();
            this.mConstantPadding = null;
            this.mCheckedPadding = false;
            this.mCheckedConstantSize = false;
            this.mCheckedConstantState = false;
            return pos;
        }

        /* access modifiers changed from: package-private */
        public void invalidateCache() {
            this.mCheckedOpacity = false;
            this.mCheckedStateful = false;
        }

        /* access modifiers changed from: package-private */
        public final int getCapacity() {
            return this.mDrawables.length;
        }

        private void createAllFutures() {
            SparseArray<Drawable.ConstantState> sparseArray = this.mDrawableFutures;
            if (sparseArray != null) {
                int futureCount = sparseArray.size();
                for (int keyIndex = 0; keyIndex < futureCount; keyIndex++) {
                    this.mDrawables[this.mDrawableFutures.keyAt(keyIndex)] = prepareDrawable(this.mDrawableFutures.valueAt(keyIndex).newDrawable(this.mSourceRes));
                }
                this.mDrawableFutures = null;
            }
        }

        private Drawable prepareDrawable(Drawable child) {
            if (Build.VERSION.SDK_INT >= 23) {
                DrawableCompat.setLayoutDirection(child, this.mLayoutDirection);
            }
            Drawable child2 = child.mutate();
            child2.setCallback(this.mOwner);
            return child2;
        }

        public final int getChildCount() {
            return this.mNumChildren;
        }

        public final Drawable getChild(int index) {
            int keyIndex;
            Drawable result = this.mDrawables[index];
            if (result != null) {
                return result;
            }
            SparseArray<Drawable.ConstantState> sparseArray = this.mDrawableFutures;
            if (sparseArray == null || (keyIndex = sparseArray.indexOfKey(index)) < 0) {
                return null;
            }
            Drawable prepared = prepareDrawable(this.mDrawableFutures.valueAt(keyIndex).newDrawable(this.mSourceRes));
            this.mDrawables[index] = prepared;
            this.mDrawableFutures.removeAt(keyIndex);
            if (this.mDrawableFutures.size() == 0) {
                this.mDrawableFutures = null;
            }
            return prepared;
        }

        /* access modifiers changed from: package-private */
        public final boolean setLayoutDirection(int layoutDirection, int currentIndex) {
            boolean changed = false;
            int count = this.mNumChildren;
            Drawable[] drawables = this.mDrawables;
            for (int i = 0; i < count; i++) {
                if (drawables[i] != null) {
                    boolean childChanged = false;
                    if (Build.VERSION.SDK_INT >= 23) {
                        childChanged = DrawableCompat.setLayoutDirection(drawables[i], layoutDirection);
                    }
                    if (i == currentIndex) {
                        changed = childChanged;
                    }
                }
            }
            this.mLayoutDirection = layoutDirection;
            return changed;
        }

        /* access modifiers changed from: package-private */
        public final void updateDensity(Resources res) {
            if (res != null) {
                this.mSourceRes = res;
                int targetDensity = DrawableContainer.resolveDensity(res, this.mDensity);
                int sourceDensity = this.mDensity;
                this.mDensity = targetDensity;
                if (sourceDensity != targetDensity) {
                    this.mCheckedConstantSize = false;
                    this.mCheckedPadding = false;
                }
            }
        }

        /* access modifiers changed from: package-private */
        @RequiresApi(21)
        public final void applyTheme(Resources.Theme theme) {
            if (theme != null) {
                createAllFutures();
                int count = this.mNumChildren;
                Drawable[] drawables = this.mDrawables;
                for (int i = 0; i < count; i++) {
                    if (drawables[i] != null && DrawableCompat.canApplyTheme(drawables[i])) {
                        DrawableCompat.applyTheme(drawables[i], theme);
                        this.mChildrenChangingConfigurations |= drawables[i].getChangingConfigurations();
                    }
                }
                updateDensity(Api21Impl.getResources(theme));
            }
        }

        @RequiresApi(21)
        public boolean canApplyTheme() {
            int count = this.mNumChildren;
            Drawable[] drawables = this.mDrawables;
            for (int i = 0; i < count; i++) {
                Drawable d = drawables[i];
                if (d == null) {
                    Drawable.ConstantState future = this.mDrawableFutures.get(i);
                    if (future != null && Api21Impl.canApplyTheme(future)) {
                        return true;
                    }
                } else if (DrawableCompat.canApplyTheme(d)) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void mutate() {
            int count = this.mNumChildren;
            Drawable[] drawables = this.mDrawables;
            for (int i = 0; i < count; i++) {
                if (drawables[i] != null) {
                    drawables[i].mutate();
                }
            }
            this.mMutated = true;
        }

        /* access modifiers changed from: package-private */
        public final void clearMutated() {
            this.mMutated = false;
        }

        public final void setVariablePadding(boolean variable) {
            this.mVariablePadding = variable;
        }

        public final Rect getConstantPadding() {
            if (this.mVariablePadding) {
                return null;
            }
            Rect r = this.mConstantPadding;
            if (r != null || this.mCheckedPadding) {
                return r;
            }
            createAllFutures();
            Rect r2 = null;
            Rect t = new Rect();
            int count = this.mNumChildren;
            Drawable[] drawables = this.mDrawables;
            for (int i = 0; i < count; i++) {
                if (drawables[i].getPadding(t)) {
                    if (r2 == null) {
                        r2 = new Rect(0, 0, 0, 0);
                    }
                    int i2 = t.left;
                    if (i2 > r2.left) {
                        r2.left = i2;
                    }
                    int i3 = t.top;
                    if (i3 > r2.top) {
                        r2.top = i3;
                    }
                    int i4 = t.right;
                    if (i4 > r2.right) {
                        r2.right = i4;
                    }
                    int i5 = t.bottom;
                    if (i5 > r2.bottom) {
                        r2.bottom = i5;
                    }
                }
            }
            this.mCheckedPadding = true;
            this.mConstantPadding = r2;
            return r2;
        }

        public final void setConstantSize(boolean constant) {
            this.mConstantSize = constant;
        }

        public final boolean isConstantSize() {
            return this.mConstantSize;
        }

        public final int getConstantWidth() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize();
            }
            return this.mConstantWidth;
        }

        public final int getConstantHeight() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize();
            }
            return this.mConstantHeight;
        }

        public final int getConstantMinimumWidth() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize();
            }
            return this.mConstantMinimumWidth;
        }

        public final int getConstantMinimumHeight() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize();
            }
            return this.mConstantMinimumHeight;
        }

        /* access modifiers changed from: protected */
        public void computeConstantSize() {
            this.mCheckedConstantSize = true;
            createAllFutures();
            int count = this.mNumChildren;
            Drawable[] drawables = this.mDrawables;
            this.mConstantHeight = -1;
            this.mConstantWidth = -1;
            this.mConstantMinimumHeight = 0;
            this.mConstantMinimumWidth = 0;
            for (int i = 0; i < count; i++) {
                Drawable dr = drawables[i];
                int s = dr.getIntrinsicWidth();
                if (s > this.mConstantWidth) {
                    this.mConstantWidth = s;
                }
                int s2 = dr.getIntrinsicHeight();
                if (s2 > this.mConstantHeight) {
                    this.mConstantHeight = s2;
                }
                int s3 = dr.getMinimumWidth();
                if (s3 > this.mConstantMinimumWidth) {
                    this.mConstantMinimumWidth = s3;
                }
                int s4 = dr.getMinimumHeight();
                if (s4 > this.mConstantMinimumHeight) {
                    this.mConstantMinimumHeight = s4;
                }
            }
        }

        public final void setEnterFadeDuration(int duration) {
            this.mEnterFadeDuration = duration;
        }

        public final int getEnterFadeDuration() {
            return this.mEnterFadeDuration;
        }

        public final void setExitFadeDuration(int duration) {
            this.mExitFadeDuration = duration;
        }

        public final int getExitFadeDuration() {
            return this.mExitFadeDuration;
        }

        public final int getOpacity() {
            if (this.mCheckedOpacity) {
                return this.mOpacity;
            }
            createAllFutures();
            int count = this.mNumChildren;
            Drawable[] drawables = this.mDrawables;
            int op = count > 0 ? drawables[0].getOpacity() : -2;
            for (int i = 1; i < count; i++) {
                op = Drawable.resolveOpacity(op, drawables[i].getOpacity());
            }
            this.mOpacity = op;
            this.mCheckedOpacity = true;
            return op;
        }

        public final boolean isStateful() {
            if (this.mCheckedStateful) {
                return this.mStateful;
            }
            createAllFutures();
            int count = this.mNumChildren;
            Drawable[] drawables = this.mDrawables;
            boolean isStateful = false;
            int i = 0;
            while (true) {
                if (i >= count) {
                    break;
                } else if (drawables[i].isStateful()) {
                    isStateful = true;
                    break;
                } else {
                    i++;
                }
            }
            this.mStateful = isStateful;
            this.mCheckedStateful = true;
            return isStateful;
        }

        public void growArray(int oldSize, int newSize) {
            Drawable[] newDrawables = new Drawable[newSize];
            Drawable[] drawableArr = this.mDrawables;
            if (drawableArr != null) {
                System.arraycopy(drawableArr, 0, newDrawables, 0, oldSize);
            }
            this.mDrawables = newDrawables;
        }

        public boolean canConstantState() {
            if (this.mCheckedConstantState) {
                return this.mCanConstantState;
            }
            createAllFutures();
            this.mCheckedConstantState = true;
            int count = this.mNumChildren;
            Drawable[] drawables = this.mDrawables;
            for (int i = 0; i < count; i++) {
                if (drawables[i].getConstantState() == null) {
                    this.mCanConstantState = false;
                    return false;
                }
            }
            this.mCanConstantState = true;
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public void setConstantState(DrawableContainerState state) {
        this.mDrawableContainerState = state;
        int i = this.mCurIndex;
        if (i >= 0) {
            Drawable child = state.getChild(i);
            this.mCurrDrawable = child;
            if (child != null) {
                initializeDrawableForDisplay(child);
            }
        }
        this.mLastDrawable = null;
    }

    public static class BlockInvalidateCallback implements Drawable.Callback {
        private Drawable.Callback mCallback;

        BlockInvalidateCallback() {
        }

        public BlockInvalidateCallback wrap(Drawable.Callback callback) {
            this.mCallback = callback;
            return this;
        }

        public Drawable.Callback unwrap() {
            Drawable.Callback callback = this.mCallback;
            this.mCallback = null;
            return callback;
        }

        public void invalidateDrawable(@NonNull Drawable who) {
        }

        public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
            Drawable.Callback callback = this.mCallback;
            if (callback != null) {
                callback.scheduleDrawable(who, what, when);
            }
        }

        public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
            Drawable.Callback callback = this.mCallback;
            if (callback != null) {
                callback.unscheduleDrawable(who, what);
            }
        }
    }

    static int resolveDensity(@Nullable Resources r, int parentDensity) {
        int densityDpi = r == null ? parentDensity : r.getDisplayMetrics().densityDpi;
        return densityDpi == 0 ? Opcodes.IF_ICMPNE : densityDpi;
    }

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        public static boolean canApplyTheme(Drawable.ConstantState constantState) {
            return constantState.canApplyTheme();
        }

        public static Resources getResources(Resources.Theme theme) {
            return theme.getResources();
        }

        public static void getOutline(Drawable drawable, Outline outline) {
            drawable.getOutline(outline);
        }
    }
}
