package com.google.android.material.tooltip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.core.graphics.ColorUtils;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.MarkerEdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.OffsetEdgeTreatment;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TooltipDrawable extends MaterialShapeDrawable implements TextDrawableHelper.TextDrawableDelegate {
    @StyleRes
    private static final int DEFAULT_STYLE = R.style.Widget_MaterialComponents_Tooltip;
    @AttrRes
    private static final int DEFAULT_THEME_ATTR = R.attr.tooltipStyle;
    private int arrowSize;
    @NonNull
    private final View.OnLayoutChangeListener attachedViewLayoutChangeListener;
    @NonNull
    private final Context context;
    @NonNull
    private final Rect displayFrame;
    @Nullable
    private final Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private float labelOpacity;
    private int layoutMargin;
    private int locationOnScreenX;
    private int minHeight;
    private int minWidth;
    private int padding;
    @Nullable
    private CharSequence text;
    @NonNull
    private final TextDrawableHelper textDrawableHelper;
    private final float tooltipPivotX;
    private float tooltipPivotY;
    private float tooltipScaleX;
    private float tooltipScaleY;

    @NonNull
    public static TooltipDrawable createFromAttributes(@NonNull Context context2, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        TooltipDrawable tooltip = new TooltipDrawable(context2, attrs, defStyleAttr, defStyleRes);
        tooltip.loadFromAttributes(attrs, defStyleAttr, defStyleRes);
        return tooltip;
    }

    @NonNull
    public static TooltipDrawable createFromAttributes(@NonNull Context context2, @Nullable AttributeSet attrs) {
        return createFromAttributes(context2, attrs, DEFAULT_THEME_ATTR, DEFAULT_STYLE);
    }

    @NonNull
    public static TooltipDrawable create(@NonNull Context context2) {
        return createFromAttributes(context2, (AttributeSet) null, DEFAULT_THEME_ATTR, DEFAULT_STYLE);
    }

    private TooltipDrawable(@NonNull Context context2, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context2, attrs, defStyleAttr, defStyleRes);
        TextDrawableHelper textDrawableHelper2 = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper2;
        this.attachedViewLayoutChangeListener = new View.OnLayoutChangeListener() {
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                TooltipDrawable.this.updateLocationOnScreen(v);
            }
        };
        this.displayFrame = new Rect();
        this.tooltipScaleX = 1.0f;
        this.tooltipScaleY = 1.0f;
        this.tooltipPivotX = 0.5f;
        this.tooltipPivotY = 0.5f;
        this.labelOpacity = 1.0f;
        this.context = context2;
        textDrawableHelper2.getTextPaint().density = context2.getResources().getDisplayMetrics().density;
        textDrawableHelper2.getTextPaint().setTextAlign(Paint.Align.CENTER);
    }

    private void loadFromAttributes(@Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(this.context, attrs, R.styleable.Tooltip, defStyleAttr, defStyleRes, new int[0]);
        this.arrowSize = this.context.getResources().getDimensionPixelSize(R.dimen.mtrl_tooltip_arrowSize);
        setShapeAppearanceModel(getShapeAppearanceModel().toBuilder().setBottomEdge(createMarkerEdge()).build());
        setText(a.getText(R.styleable.Tooltip_android_text));
        setTextAppearance(MaterialResources.getTextAppearance(this.context, a, R.styleable.Tooltip_android_textAppearance));
        setFillColor(ColorStateList.valueOf(a.getColor(R.styleable.Tooltip_backgroundTint, MaterialColors.layer(ColorUtils.setAlphaComponent(MaterialColors.getColor(this.context, 16842801, TooltipDrawable.class.getCanonicalName()), 229), ColorUtils.setAlphaComponent(MaterialColors.getColor(this.context, R.attr.colorOnBackground, TooltipDrawable.class.getCanonicalName()), Opcodes.IFEQ)))));
        setStrokeColor(ColorStateList.valueOf(MaterialColors.getColor(this.context, R.attr.colorSurface, TooltipDrawable.class.getCanonicalName())));
        this.padding = a.getDimensionPixelSize(R.styleable.Tooltip_android_padding, 0);
        this.minWidth = a.getDimensionPixelSize(R.styleable.Tooltip_android_minWidth, 0);
        this.minHeight = a.getDimensionPixelSize(R.styleable.Tooltip_android_minHeight, 0);
        this.layoutMargin = a.getDimensionPixelSize(R.styleable.Tooltip_android_layout_margin, 0);
        a.recycle();
    }

    @Nullable
    public CharSequence getText() {
        return this.text;
    }

    public void setTextResource(@StringRes int id) {
        setText(this.context.getResources().getString(id));
    }

    public void setText(@Nullable CharSequence text2) {
        if (!TextUtils.equals(this.text, text2)) {
            this.text = text2;
            this.textDrawableHelper.setTextWidthDirty(true);
            invalidateSelf();
        }
    }

    @Nullable
    public TextAppearance getTextAppearance() {
        return this.textDrawableHelper.getTextAppearance();
    }

    public void setTextAppearanceResource(@StyleRes int id) {
        setTextAppearance(new TextAppearance(this.context, id));
    }

    public void setTextAppearance(@Nullable TextAppearance textAppearance) {
        this.textDrawableHelper.setTextAppearance(textAppearance, this.context);
    }

    public int getMinWidth() {
        return this.minWidth;
    }

    public void setMinWidth(@Px int minWidth2) {
        this.minWidth = minWidth2;
        invalidateSelf();
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public void setMinHeight(@Px int minHeight2) {
        this.minHeight = minHeight2;
        invalidateSelf();
    }

    public int getTextPadding() {
        return this.padding;
    }

    public void setTextPadding(@Px int padding2) {
        this.padding = padding2;
        invalidateSelf();
    }

    public int getLayoutMargin() {
        return this.layoutMargin;
    }

    public void setLayoutMargin(@Px int layoutMargin2) {
        this.layoutMargin = layoutMargin2;
        invalidateSelf();
    }

    public void setRevealFraction(@FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        this.tooltipPivotY = 1.2f;
        this.tooltipScaleX = fraction;
        this.tooltipScaleY = fraction;
        this.labelOpacity = AnimationUtils.lerp(0.0f, 1.0f, 0.19f, 1.0f, fraction);
        invalidateSelf();
    }

    public void setRelativeToView(@Nullable View view) {
        if (view != null) {
            updateLocationOnScreen(view);
            view.addOnLayoutChangeListener(this.attachedViewLayoutChangeListener);
        }
    }

    public void detachView(@Nullable View view) {
        if (view != null) {
            view.removeOnLayoutChangeListener(this.attachedViewLayoutChangeListener);
        }
    }

    public int getIntrinsicWidth() {
        return (int) Math.max(((float) (this.padding * 2)) + getTextWidth(), (float) this.minWidth);
    }

    public int getIntrinsicHeight() {
        return (int) Math.max(this.textDrawableHelper.getTextPaint().getTextSize(), (float) this.minHeight);
    }

    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.scale(this.tooltipScaleX, this.tooltipScaleY, ((float) getBounds().left) + (((float) getBounds().width()) * 0.5f), ((float) getBounds().top) + (((float) getBounds().height()) * this.tooltipPivotY));
        canvas.translate(calculatePointerOffset(), (float) (-((((double) this.arrowSize) * Math.sqrt(2.0d)) - ((double) this.arrowSize))));
        super.draw(canvas);
        drawText(canvas);
        canvas.restore();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        setShapeAppearanceModel(getShapeAppearanceModel().toBuilder().setBottomEdge(createMarkerEdge()).build());
    }

    public boolean onStateChange(int[] state) {
        return super.onStateChange(state);
    }

    public void onTextSizeChange() {
        invalidateSelf();
    }

    /* access modifiers changed from: private */
    public void updateLocationOnScreen(@NonNull View v) {
        int[] locationOnScreen = new int[2];
        v.getLocationOnScreen(locationOnScreen);
        this.locationOnScreenX = locationOnScreen[0];
        v.getWindowVisibleDisplayFrame(this.displayFrame);
    }

    private float calculatePointerOffset() {
        if (((this.displayFrame.right - getBounds().right) - this.locationOnScreenX) - this.layoutMargin < 0) {
            return (float) (((this.displayFrame.right - getBounds().right) - this.locationOnScreenX) - this.layoutMargin);
        }
        if (((this.displayFrame.left - getBounds().left) - this.locationOnScreenX) + this.layoutMargin > 0) {
            return (float) (((this.displayFrame.left - getBounds().left) - this.locationOnScreenX) + this.layoutMargin);
        }
        return 0.0f;
    }

    private EdgeTreatment createMarkerEdge() {
        float maxArrowOffset = ((float) (((double) getBounds().width()) - (((double) this.arrowSize) * Math.sqrt(2.0d)))) / 2.0f;
        return new OffsetEdgeTreatment(new MarkerEdgeTreatment((float) this.arrowSize), Math.min(Math.max(-calculatePointerOffset(), -maxArrowOffset), maxArrowOffset));
    }

    private void drawText(@NonNull Canvas canvas) {
        if (this.text != null) {
            Rect bounds = getBounds();
            int y = (int) calculateTextOriginAndAlignment(bounds);
            if (this.textDrawableHelper.getTextAppearance() != null) {
                this.textDrawableHelper.getTextPaint().drawableState = getState();
                this.textDrawableHelper.updateTextPaintDrawState(this.context);
                this.textDrawableHelper.getTextPaint().setAlpha((int) (this.labelOpacity * 255.0f));
            }
            CharSequence charSequence = this.text;
            canvas.drawText(charSequence, 0, charSequence.length(), (float) bounds.centerX(), (float) y, this.textDrawableHelper.getTextPaint());
        }
    }

    private float getTextWidth() {
        CharSequence charSequence = this.text;
        if (charSequence == null) {
            return 0.0f;
        }
        return this.textDrawableHelper.getTextWidth(charSequence.toString());
    }

    private float calculateTextOriginAndAlignment(@NonNull Rect bounds) {
        return ((float) bounds.centerY()) - calculateTextCenterFromBaseline();
    }

    private float calculateTextCenterFromBaseline() {
        this.textDrawableHelper.getTextPaint().getFontMetrics(this.fontMetrics);
        Paint.FontMetrics fontMetrics2 = this.fontMetrics;
        return (fontMetrics2.descent + fontMetrics2.ascent) / 2.0f;
    }
}
