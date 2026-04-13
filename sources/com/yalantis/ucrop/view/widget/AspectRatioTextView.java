package com.yalantis.ucrop.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.luck.picture.lib.R;
import com.yalantis.ucrop.model.AspectRatio;
import java.util.Locale;

public class AspectRatioTextView extends AppCompatTextView {
    private final float MARGIN_MULTIPLIER;
    private float mAspectRatio;
    private String mAspectRatioTitle;
    private float mAspectRatioX;
    private float mAspectRatioY;
    private final Rect mCanvasClipBounds;
    private Paint mDotPaint;
    private int mDotSize;

    public AspectRatioTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public AspectRatioTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.MARGIN_MULTIPLIER = 1.5f;
        this.mCanvasClipBounds = new Rect();
        init(context.obtainStyledAttributes(attrs, R.styleable.ucrop_AspectRatioTextView));
    }

    @TargetApi(21)
    public AspectRatioTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        this.MARGIN_MULTIPLIER = 1.5f;
        this.mCanvasClipBounds = new Rect();
        init(context.obtainStyledAttributes(attrs, R.styleable.ucrop_AspectRatioTextView));
    }

    public void setActiveColor(@ColorInt int activeColor) {
        applyActiveColor(activeColor);
        invalidate();
    }

    public void setAspectRatio(@NonNull AspectRatio aspectRatio) {
        this.mAspectRatioTitle = aspectRatio.getAspectRatioTitle();
        this.mAspectRatioX = aspectRatio.getAspectRatioX();
        float aspectRatioY = aspectRatio.getAspectRatioY();
        this.mAspectRatioY = aspectRatioY;
        float f = this.mAspectRatioX;
        if (f == 0.0f || aspectRatioY == 0.0f) {
            this.mAspectRatio = 0.0f;
        } else {
            this.mAspectRatio = f / aspectRatioY;
        }
        setTitle();
    }

    public float getAspectRatio(boolean toggleRatio) {
        if (toggleRatio) {
            toggleAspectRatio();
            setTitle();
        }
        return this.mAspectRatio;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected()) {
            canvas.getClipBounds(this.mCanvasClipBounds);
            Rect rect = this.mCanvasClipBounds;
            float f = ((float) rect.bottom) - (((float) rect.top) / 2.0f);
            int i = this.mDotSize;
            canvas.drawCircle(((float) (rect.right - rect.left)) / 2.0f, f - (((float) i) * 1.5f), ((float) i) / 2.0f, this.mDotPaint);
        }
    }

    private void init(@NonNull TypedArray a) {
        setGravity(1);
        this.mAspectRatioTitle = a.getString(R.styleable.ucrop_AspectRatioTextView_ucrop_artv_ratio_title);
        this.mAspectRatioX = a.getFloat(R.styleable.ucrop_AspectRatioTextView_ucrop_artv_ratio_x, 0.0f);
        float f = a.getFloat(R.styleable.ucrop_AspectRatioTextView_ucrop_artv_ratio_y, 0.0f);
        this.mAspectRatioY = f;
        float f2 = this.mAspectRatioX;
        if (f2 == 0.0f || f == 0.0f) {
            this.mAspectRatio = 0.0f;
        } else {
            this.mAspectRatio = f2 / f;
        }
        this.mDotSize = getContext().getResources().getDimensionPixelSize(R.dimen.ucrop_size_dot_scale_text_view);
        Paint paint = new Paint(1);
        this.mDotPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        setTitle();
        applyActiveColor(getResources().getColor(R.color.ucrop_color_widget_active));
        a.recycle();
    }

    private void applyActiveColor(@ColorInt int activeColor) {
        Paint paint = this.mDotPaint;
        if (paint != null) {
            paint.setColor(activeColor);
        }
        setTextColor(new ColorStateList(new int[][]{new int[]{16842913}, new int[]{0}}, new int[]{activeColor, ContextCompat.getColor(getContext(), R.color.ucrop_color_widget)}));
    }

    private void toggleAspectRatio() {
        if (this.mAspectRatio != 0.0f) {
            float tempRatioW = this.mAspectRatioX;
            float f = this.mAspectRatioY;
            this.mAspectRatioX = f;
            this.mAspectRatioY = tempRatioW;
            this.mAspectRatio = f / tempRatioW;
        }
    }

    private void setTitle() {
        if (!TextUtils.isEmpty(this.mAspectRatioTitle)) {
            setText(this.mAspectRatioTitle);
            return;
        }
        setText(String.format(Locale.US, "%d:%d", new Object[]{Integer.valueOf((int) this.mAspectRatioX), Integer.valueOf((int) this.mAspectRatioY)}));
    }
}
