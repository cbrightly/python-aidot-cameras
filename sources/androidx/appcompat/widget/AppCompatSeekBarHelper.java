package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

public class AppCompatSeekBarHelper extends AppCompatProgressBarHelper {
    private boolean mHasTickMarkTint = false;
    private boolean mHasTickMarkTintMode = false;
    private Drawable mTickMark;
    private ColorStateList mTickMarkTintList = null;
    private PorterDuff.Mode mTickMarkTintMode = null;
    private final SeekBar mView;

    AppCompatSeekBarHelper(SeekBar view) {
        super(view);
        this.mView = view;
    }

    /* access modifiers changed from: package-private */
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        super.loadFromAttributes(attrs, defStyleAttr);
        Context context = this.mView.getContext();
        int[] iArr = R.styleable.AppCompatSeekBar;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, iArr, defStyleAttr, 0);
        SeekBar seekBar = this.mView;
        ViewCompat.saveAttributeDataForStyleable(seekBar, seekBar.getContext(), iArr, attrs, a.getWrappedTypeArray(), defStyleAttr, 0);
        Drawable drawable = a.getDrawableIfKnown(R.styleable.AppCompatSeekBar_android_thumb);
        if (drawable != null) {
            this.mView.setThumb(drawable);
        }
        setTickMark(a.getDrawable(R.styleable.AppCompatSeekBar_tickMark));
        int i = R.styleable.AppCompatSeekBar_tickMarkTintMode;
        if (a.hasValue(i)) {
            this.mTickMarkTintMode = DrawableUtils.parseTintMode(a.getInt(i, -1), this.mTickMarkTintMode);
            this.mHasTickMarkTintMode = true;
        }
        int i2 = R.styleable.AppCompatSeekBar_tickMarkTint;
        if (a.hasValue(i2)) {
            this.mTickMarkTintList = a.getColorStateList(i2);
            this.mHasTickMarkTint = true;
        }
        a.recycle();
        applyTickMarkTint();
    }

    /* access modifiers changed from: package-private */
    public void setTickMark(@Nullable Drawable tickMark) {
        Drawable drawable = this.mTickMark;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback) null);
        }
        this.mTickMark = tickMark;
        if (tickMark != null) {
            tickMark.setCallback(this.mView);
            DrawableCompat.setLayoutDirection(tickMark, ViewCompat.getLayoutDirection(this.mView));
            if (tickMark.isStateful()) {
                tickMark.setState(this.mView.getDrawableState());
            }
            applyTickMarkTint();
        }
        this.mView.invalidate();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Drawable getTickMark() {
        return this.mTickMark;
    }

    /* access modifiers changed from: package-private */
    public void setTickMarkTintList(@Nullable ColorStateList tint) {
        this.mTickMarkTintList = tint;
        this.mHasTickMarkTint = true;
        applyTickMarkTint();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ColorStateList getTickMarkTintList() {
        return this.mTickMarkTintList;
    }

    /* access modifiers changed from: package-private */
    public void setTickMarkTintMode(@Nullable PorterDuff.Mode tintMode) {
        this.mTickMarkTintMode = tintMode;
        this.mHasTickMarkTintMode = true;
        applyTickMarkTint();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public PorterDuff.Mode getTickMarkTintMode() {
        return this.mTickMarkTintMode;
    }

    private void applyTickMarkTint() {
        Drawable drawable = this.mTickMark;
        if (drawable == null) {
            return;
        }
        if (this.mHasTickMarkTint || this.mHasTickMarkTintMode) {
            Drawable wrap = DrawableCompat.wrap(drawable.mutate());
            this.mTickMark = wrap;
            if (this.mHasTickMarkTint) {
                DrawableCompat.setTintList(wrap, this.mTickMarkTintList);
            }
            if (this.mHasTickMarkTintMode) {
                DrawableCompat.setTintMode(this.mTickMark, this.mTickMarkTintMode);
            }
            if (this.mTickMark.isStateful()) {
                this.mTickMark.setState(this.mView.getDrawableState());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void jumpDrawablesToCurrentState() {
        Drawable drawable = this.mTickMark;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    /* access modifiers changed from: package-private */
    public void drawableStateChanged() {
        Drawable tickMark = this.mTickMark;
        if (tickMark != null && tickMark.isStateful() && tickMark.setState(this.mView.getDrawableState())) {
            this.mView.invalidateDrawable(tickMark);
        }
    }

    /* access modifiers changed from: package-private */
    public void drawTickMarks(Canvas canvas) {
        if (this.mTickMark != null) {
            int count = this.mView.getMax();
            int halfH = 1;
            if (count > 1) {
                int w = this.mTickMark.getIntrinsicWidth();
                int h = this.mTickMark.getIntrinsicHeight();
                int halfW = w >= 0 ? w / 2 : 1;
                if (h >= 0) {
                    halfH = h / 2;
                }
                this.mTickMark.setBounds(-halfW, -halfH, halfW, halfH);
                float spacing = ((float) ((this.mView.getWidth() - this.mView.getPaddingLeft()) - this.mView.getPaddingRight())) / ((float) count);
                int saveCount = canvas.save();
                canvas.translate((float) this.mView.getPaddingLeft(), (float) (this.mView.getHeight() / 2));
                for (int i = 0; i <= count; i++) {
                    this.mTickMark.draw(canvas);
                    canvas.translate(spacing, 0.0f);
                }
                canvas.restoreToCount(saveCount);
            }
        }
    }
}
