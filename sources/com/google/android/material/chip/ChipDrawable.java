package com.google.android.material.chip;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.annotation.AnimatorRes;
import androidx.annotation.AttrRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.XmlRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.internal.view.SupportMenu;
import androidx.core.text.BidiFormatter;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.canvas.CanvasCompat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.lang.ref.WeakReference;
import java.util.Arrays;

public class ChipDrawable extends MaterialShapeDrawable implements TintAwareDrawable, Drawable.Callback, TextDrawableHelper.TextDrawableDelegate {
    private static final boolean DEBUG = false;
    private static final int[] DEFAULT_STATE = {16842910};
    private static final int MAX_CHIP_ICON_HEIGHT = 24;
    private static final String NAMESPACE_APP = "http://schemas.android.com/apk/res-auto";
    private static final ShapeDrawable closeIconRippleMask = new ShapeDrawable(new OvalShape());
    private int alpha = 255;
    private boolean checkable;
    @Nullable
    private Drawable checkedIcon;
    @Nullable
    private ColorStateList checkedIconTint;
    private boolean checkedIconVisible;
    @Nullable
    private ColorStateList chipBackgroundColor;
    private float chipCornerRadius = -1.0f;
    private float chipEndPadding;
    @Nullable
    private Drawable chipIcon;
    private float chipIconSize;
    @Nullable
    private ColorStateList chipIconTint;
    private boolean chipIconVisible;
    private float chipMinHeight;
    private final Paint chipPaint = new Paint(1);
    private float chipStartPadding;
    @Nullable
    private ColorStateList chipStrokeColor;
    private float chipStrokeWidth;
    @Nullable
    private ColorStateList chipSurfaceColor;
    @Nullable
    private Drawable closeIcon;
    @Nullable
    private CharSequence closeIconContentDescription;
    private float closeIconEndPadding;
    @Nullable
    private Drawable closeIconRipple;
    private float closeIconSize;
    private float closeIconStartPadding;
    private int[] closeIconStateSet;
    @Nullable
    private ColorStateList closeIconTint;
    private boolean closeIconVisible;
    @Nullable
    private ColorFilter colorFilter;
    @Nullable
    private ColorStateList compatRippleColor;
    @NonNull
    private final Context context;
    private boolean currentChecked;
    @ColorInt
    private int currentChipBackgroundColor;
    @ColorInt
    private int currentChipStrokeColor;
    @ColorInt
    private int currentChipSurfaceColor;
    @ColorInt
    private int currentCompatRippleColor;
    @ColorInt
    private int currentCompositeSurfaceBackgroundColor;
    @ColorInt
    private int currentTextColor;
    @ColorInt
    private int currentTint;
    @Nullable
    private final Paint debugPaint;
    @NonNull
    private WeakReference<Delegate> delegate = new WeakReference<>((Object) null);
    private final Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private boolean hasChipIconTint;
    @Nullable
    private MotionSpec hideMotionSpec;
    private float iconEndPadding;
    private float iconStartPadding;
    private boolean isShapeThemingEnabled;
    private int maxWidth;
    private final PointF pointF = new PointF();
    private final RectF rectF = new RectF();
    @Nullable
    private ColorStateList rippleColor;
    private final Path shapePath = new Path();
    private boolean shouldDrawText;
    @Nullable
    private MotionSpec showMotionSpec;
    @Nullable
    private CharSequence text;
    @NonNull
    private final TextDrawableHelper textDrawableHelper;
    private float textEndPadding;
    private float textStartPadding;
    @Nullable
    private ColorStateList tint;
    @Nullable
    private PorterDuffColorFilter tintFilter;
    @Nullable
    private PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
    private TextUtils.TruncateAt truncateAt;
    private boolean useCompatRipple;

    public interface Delegate {
        void onChipDrawableSizeChange();
    }

    @NonNull
    public static ChipDrawable createFromAttributes(@NonNull Context context2, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        ChipDrawable chip2 = new ChipDrawable(context2, attrs, defStyleAttr, defStyleRes);
        chip2.loadFromAttributes(attrs, defStyleAttr, defStyleRes);
        return chip2;
    }

    @NonNull
    public static ChipDrawable createFromResource(@NonNull Context context2, @XmlRes int id) {
        AttributeSet attrs = DrawableUtils.parseDrawableXml(context2, id, "chip");
        int style = attrs.getStyleAttribute();
        if (style == 0) {
            style = R.style.Widget_MaterialComponents_Chip_Entry;
        }
        return createFromAttributes(context2, attrs, R.attr.chipStandaloneStyle, style);
    }

    private ChipDrawable(@NonNull Context context2, AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context2, attrs, defStyleAttr, defStyleRes);
        initializeElevationOverlay(context2);
        this.context = context2;
        TextDrawableHelper textDrawableHelper2 = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper2;
        this.text = "";
        textDrawableHelper2.getTextPaint().density = context2.getResources().getDisplayMetrics().density;
        this.debugPaint = null;
        int[] iArr = DEFAULT_STATE;
        setState(iArr);
        setCloseIconState(iArr);
        this.shouldDrawText = true;
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            closeIconRippleMask.setTint(-1);
        }
    }

    private void loadFromAttributes(@Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(this.context, attrs, R.styleable.Chip, defStyleAttr, defStyleRes, new int[0]);
        this.isShapeThemingEnabled = a.hasValue(R.styleable.Chip_shapeAppearance);
        setChipSurfaceColor(MaterialResources.getColorStateList(this.context, a, R.styleable.Chip_chipSurfaceColor));
        setChipBackgroundColor(MaterialResources.getColorStateList(this.context, a, R.styleable.Chip_chipBackgroundColor));
        setChipMinHeight(a.getDimension(R.styleable.Chip_chipMinHeight, 0.0f));
        int i = R.styleable.Chip_chipCornerRadius;
        if (a.hasValue(i)) {
            setChipCornerRadius(a.getDimension(i, 0.0f));
        }
        setChipStrokeColor(MaterialResources.getColorStateList(this.context, a, R.styleable.Chip_chipStrokeColor));
        setChipStrokeWidth(a.getDimension(R.styleable.Chip_chipStrokeWidth, 0.0f));
        setRippleColor(MaterialResources.getColorStateList(this.context, a, R.styleable.Chip_rippleColor));
        setText(a.getText(R.styleable.Chip_android_text));
        TextAppearance textAppearance = MaterialResources.getTextAppearance(this.context, a, R.styleable.Chip_android_textAppearance);
        textAppearance.textSize = a.getDimension(R.styleable.Chip_android_textSize, textAppearance.textSize);
        setTextAppearance(textAppearance);
        switch (a.getInt(R.styleable.Chip_android_ellipsize, 0)) {
            case 1:
                setEllipsize(TextUtils.TruncateAt.START);
                break;
            case 2:
                setEllipsize(TextUtils.TruncateAt.MIDDLE);
                break;
            case 3:
                setEllipsize(TextUtils.TruncateAt.END);
                break;
        }
        setChipIconVisible(a.getBoolean(R.styleable.Chip_chipIconVisible, false));
        if (!(attrs == null || attrs.getAttributeValue(NAMESPACE_APP, "chipIconEnabled") == null || attrs.getAttributeValue(NAMESPACE_APP, "chipIconVisible") != null)) {
            setChipIconVisible(a.getBoolean(R.styleable.Chip_chipIconEnabled, false));
        }
        setChipIcon(MaterialResources.getDrawable(this.context, a, R.styleable.Chip_chipIcon));
        int i2 = R.styleable.Chip_chipIconTint;
        if (a.hasValue(i2)) {
            setChipIconTint(MaterialResources.getColorStateList(this.context, a, i2));
        }
        setChipIconSize(a.getDimension(R.styleable.Chip_chipIconSize, -1.0f));
        setCloseIconVisible(a.getBoolean(R.styleable.Chip_closeIconVisible, false));
        if (!(attrs == null || attrs.getAttributeValue(NAMESPACE_APP, "closeIconEnabled") == null || attrs.getAttributeValue(NAMESPACE_APP, "closeIconVisible") != null)) {
            setCloseIconVisible(a.getBoolean(R.styleable.Chip_closeIconEnabled, false));
        }
        setCloseIcon(MaterialResources.getDrawable(this.context, a, R.styleable.Chip_closeIcon));
        setCloseIconTint(MaterialResources.getColorStateList(this.context, a, R.styleable.Chip_closeIconTint));
        setCloseIconSize(a.getDimension(R.styleable.Chip_closeIconSize, 0.0f));
        setCheckable(a.getBoolean(R.styleable.Chip_android_checkable, false));
        setCheckedIconVisible(a.getBoolean(R.styleable.Chip_checkedIconVisible, false));
        if (!(attrs == null || attrs.getAttributeValue(NAMESPACE_APP, "checkedIconEnabled") == null || attrs.getAttributeValue(NAMESPACE_APP, "checkedIconVisible") != null)) {
            setCheckedIconVisible(a.getBoolean(R.styleable.Chip_checkedIconEnabled, false));
        }
        setCheckedIcon(MaterialResources.getDrawable(this.context, a, R.styleable.Chip_checkedIcon));
        int i3 = R.styleable.Chip_checkedIconTint;
        if (a.hasValue(i3)) {
            setCheckedIconTint(MaterialResources.getColorStateList(this.context, a, i3));
        }
        setShowMotionSpec(MotionSpec.createFromAttribute(this.context, a, R.styleable.Chip_showMotionSpec));
        setHideMotionSpec(MotionSpec.createFromAttribute(this.context, a, R.styleable.Chip_hideMotionSpec));
        setChipStartPadding(a.getDimension(R.styleable.Chip_chipStartPadding, 0.0f));
        setIconStartPadding(a.getDimension(R.styleable.Chip_iconStartPadding, 0.0f));
        setIconEndPadding(a.getDimension(R.styleable.Chip_iconEndPadding, 0.0f));
        setTextStartPadding(a.getDimension(R.styleable.Chip_textStartPadding, 0.0f));
        setTextEndPadding(a.getDimension(R.styleable.Chip_textEndPadding, 0.0f));
        setCloseIconStartPadding(a.getDimension(R.styleable.Chip_closeIconStartPadding, 0.0f));
        setCloseIconEndPadding(a.getDimension(R.styleable.Chip_closeIconEndPadding, 0.0f));
        setChipEndPadding(a.getDimension(R.styleable.Chip_chipEndPadding, 0.0f));
        setMaxWidth(a.getDimensionPixelSize(R.styleable.Chip_android_maxWidth, Integer.MAX_VALUE));
        a.recycle();
    }

    public void setUseCompatRipple(boolean useCompatRipple2) {
        if (this.useCompatRipple != useCompatRipple2) {
            this.useCompatRipple = useCompatRipple2;
            updateCompatRippleColor();
            onStateChange(getState());
        }
    }

    public boolean getUseCompatRipple() {
        return this.useCompatRipple;
    }

    public void setDelegate(@Nullable Delegate delegate2) {
        this.delegate = new WeakReference<>(delegate2);
    }

    /* access modifiers changed from: protected */
    public void onSizeChange() {
        Delegate delegate2 = (Delegate) this.delegate.get();
        if (delegate2 != null) {
            delegate2.onChipDrawableSizeChange();
        }
    }

    public void getChipTouchBounds(@NonNull RectF bounds) {
        calculateChipTouchBounds(getBounds(), bounds);
    }

    public void getCloseIconTouchBounds(@NonNull RectF bounds) {
        calculateCloseIconTouchBounds(getBounds(), bounds);
    }

    public int getIntrinsicWidth() {
        return Math.min(Math.round(this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding + this.textDrawableHelper.getTextWidth(getText().toString()) + this.textEndPadding + calculateCloseIconWidth() + this.chipEndPadding), this.maxWidth);
    }

    public int getIntrinsicHeight() {
        return (int) this.chipMinHeight;
    }

    private boolean showsChipIcon() {
        return this.chipIconVisible && this.chipIcon != null;
    }

    private boolean showsCheckedIcon() {
        return this.checkedIconVisible && this.checkedIcon != null && this.currentChecked;
    }

    private boolean showsCloseIcon() {
        return this.closeIconVisible && this.closeIcon != null;
    }

    private boolean canShowCheckedIcon() {
        return this.checkedIconVisible && this.checkedIcon != null && this.checkable;
    }

    /* access modifiers changed from: package-private */
    public float calculateChipIconWidth() {
        if (showsChipIcon() || showsCheckedIcon()) {
            return this.iconStartPadding + getCurrentChipIconWidth() + this.iconEndPadding;
        }
        return 0.0f;
    }

    private float getCurrentChipIconWidth() {
        Drawable iconDrawable = this.currentChecked ? this.checkedIcon : this.chipIcon;
        float f = this.chipIconSize;
        if (f > 0.0f || iconDrawable == null) {
            return f;
        }
        return (float) iconDrawable.getIntrinsicWidth();
    }

    private float getCurrentChipIconHeight() {
        Drawable icon = this.currentChecked ? this.checkedIcon : this.chipIcon;
        float maxChipIconHeight = this.chipIconSize;
        if (maxChipIconHeight > 0.0f || icon == null) {
            return maxChipIconHeight;
        }
        float maxChipIconHeight2 = (float) Math.ceil((double) ViewUtils.dpToPx(this.context, 24));
        if (((float) icon.getIntrinsicHeight()) <= maxChipIconHeight2) {
            return (float) icon.getIntrinsicHeight();
        }
        return maxChipIconHeight2;
    }

    /* access modifiers changed from: package-private */
    public float calculateCloseIconWidth() {
        if (showsCloseIcon()) {
            return this.closeIconStartPadding + this.closeIconSize + this.closeIconEndPadding;
        }
        return 0.0f;
    }

    /* access modifiers changed from: package-private */
    public boolean isShapeThemingEnabled() {
        return this.isShapeThemingEnabled;
    }

    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (!bounds.isEmpty() && getAlpha() != 0) {
            int saveCount = 0;
            int i = this.alpha;
            if (i < 255) {
                saveCount = CanvasCompat.saveLayerAlpha(canvas, (float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, i);
            }
            drawChipSurface(canvas, bounds);
            drawChipBackground(canvas, bounds);
            if (this.isShapeThemingEnabled) {
                super.draw(canvas);
            }
            drawChipStroke(canvas, bounds);
            drawCompatRipple(canvas, bounds);
            drawChipIcon(canvas, bounds);
            drawCheckedIcon(canvas, bounds);
            if (this.shouldDrawText) {
                drawText(canvas, bounds);
            }
            drawCloseIcon(canvas, bounds);
            drawDebug(canvas, bounds);
            if (this.alpha < 255) {
                canvas.restoreToCount(saveCount);
            }
        }
    }

    private void drawChipSurface(@NonNull Canvas canvas, @NonNull Rect bounds) {
        if (!this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipSurfaceColor);
            this.chipPaint.setStyle(Paint.Style.FILL);
            this.rectF.set(bounds);
            canvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
    }

    private void drawChipBackground(@NonNull Canvas canvas, @NonNull Rect bounds) {
        if (!this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipBackgroundColor);
            this.chipPaint.setStyle(Paint.Style.FILL);
            this.chipPaint.setColorFilter(getTintColorFilter());
            this.rectF.set(bounds);
            canvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
    }

    private void drawChipStroke(@NonNull Canvas canvas, @NonNull Rect bounds) {
        if (this.chipStrokeWidth > 0.0f && !this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipStrokeColor);
            this.chipPaint.setStyle(Paint.Style.STROKE);
            if (!this.isShapeThemingEnabled) {
                this.chipPaint.setColorFilter(getTintColorFilter());
            }
            RectF rectF2 = this.rectF;
            float f = this.chipStrokeWidth;
            rectF2.set(((float) bounds.left) + (f / 2.0f), ((float) bounds.top) + (f / 2.0f), ((float) bounds.right) - (f / 2.0f), ((float) bounds.bottom) - (f / 2.0f));
            float strokeCornerRadius = this.chipCornerRadius - (this.chipStrokeWidth / 2.0f);
            canvas.drawRoundRect(this.rectF, strokeCornerRadius, strokeCornerRadius, this.chipPaint);
        }
    }

    private void drawCompatRipple(@NonNull Canvas canvas, @NonNull Rect bounds) {
        this.chipPaint.setColor(this.currentCompatRippleColor);
        this.chipPaint.setStyle(Paint.Style.FILL);
        this.rectF.set(bounds);
        if (!this.isShapeThemingEnabled) {
            canvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
            return;
        }
        calculatePathForSize(new RectF(bounds), this.shapePath);
        super.drawShape(canvas, this.chipPaint, this.shapePath, getBoundsAsRectF());
    }

    private void drawChipIcon(@NonNull Canvas canvas, @NonNull Rect bounds) {
        if (showsChipIcon()) {
            calculateChipIconBounds(bounds, this.rectF);
            RectF rectF2 = this.rectF;
            float tx = rectF2.left;
            float ty = rectF2.top;
            canvas.translate(tx, ty);
            this.chipIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.chipIcon.draw(canvas);
            canvas.translate(-tx, -ty);
        }
    }

    private void drawCheckedIcon(@NonNull Canvas canvas, @NonNull Rect bounds) {
        if (showsCheckedIcon()) {
            calculateChipIconBounds(bounds, this.rectF);
            RectF rectF2 = this.rectF;
            float tx = rectF2.left;
            float ty = rectF2.top;
            canvas.translate(tx, ty);
            this.checkedIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.checkedIcon.draw(canvas);
            canvas.translate(-tx, -ty);
        }
    }

    private void drawText(@NonNull Canvas canvas, @NonNull Rect bounds) {
        if (this.text != null) {
            Paint.Align align = calculateTextOriginAndAlignment(bounds, this.pointF);
            calculateTextBounds(bounds, this.rectF);
            if (this.textDrawableHelper.getTextAppearance() != null) {
                this.textDrawableHelper.getTextPaint().drawableState = getState();
                this.textDrawableHelper.updateTextPaintDrawState(this.context);
            }
            this.textDrawableHelper.getTextPaint().setTextAlign(align);
            boolean clip = Math.round(this.textDrawableHelper.getTextWidth(getText().toString())) > Math.round(this.rectF.width());
            int saveCount = 0;
            if (clip) {
                saveCount = canvas.save();
                canvas.clipRect(this.rectF);
            }
            CharSequence finalText = this.text;
            if (clip && this.truncateAt != null) {
                finalText = TextUtils.ellipsize(this.text, this.textDrawableHelper.getTextPaint(), this.rectF.width(), this.truncateAt);
            }
            int length = finalText.length();
            PointF pointF2 = this.pointF;
            canvas.drawText(finalText, 0, length, pointF2.x, pointF2.y, this.textDrawableHelper.getTextPaint());
            if (clip) {
                canvas.restoreToCount(saveCount);
            }
        }
    }

    private void drawCloseIcon(@NonNull Canvas canvas, @NonNull Rect bounds) {
        if (showsCloseIcon()) {
            calculateCloseIconBounds(bounds, this.rectF);
            RectF rectF2 = this.rectF;
            float tx = rectF2.left;
            float ty = rectF2.top;
            canvas.translate(tx, ty);
            this.closeIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
                this.closeIconRipple.setBounds(this.closeIcon.getBounds());
                this.closeIconRipple.jumpToCurrentState();
                this.closeIconRipple.draw(canvas);
            } else {
                this.closeIcon.draw(canvas);
            }
            canvas.translate(-tx, -ty);
        }
    }

    private void drawDebug(@NonNull Canvas canvas, @NonNull Rect bounds) {
        Paint paint = this.debugPaint;
        if (paint != null) {
            paint.setColor(ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, NeedPermissionEvent.PER_IPC_SPEAK_PERM));
            canvas.drawRect(bounds, this.debugPaint);
            if (showsChipIcon() || showsCheckedIcon()) {
                calculateChipIconBounds(bounds, this.rectF);
                canvas.drawRect(this.rectF, this.debugPaint);
            }
            if (this.text != null) {
                canvas.drawLine((float) bounds.left, bounds.exactCenterY(), (float) bounds.right, bounds.exactCenterY(), this.debugPaint);
            }
            if (showsCloseIcon()) {
                calculateCloseIconBounds(bounds, this.rectF);
                canvas.drawRect(this.rectF, this.debugPaint);
            }
            this.debugPaint.setColor(ColorUtils.setAlphaComponent(SupportMenu.CATEGORY_MASK, NeedPermissionEvent.PER_IPC_SPEAK_PERM));
            calculateChipTouchBounds(bounds, this.rectF);
            canvas.drawRect(this.rectF, this.debugPaint);
            this.debugPaint.setColor(ColorUtils.setAlphaComponent(-16711936, NeedPermissionEvent.PER_IPC_SPEAK_PERM));
            calculateCloseIconTouchBounds(bounds, this.rectF);
            canvas.drawRect(this.rectF, this.debugPaint);
        }
    }

    private void calculateChipIconBounds(@NonNull Rect bounds, @NonNull RectF outBounds) {
        outBounds.setEmpty();
        if (showsChipIcon() || showsCheckedIcon()) {
            float offsetFromStart = this.chipStartPadding + this.iconStartPadding;
            float chipWidth = getCurrentChipIconWidth();
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                float f = ((float) bounds.left) + offsetFromStart;
                outBounds.left = f;
                outBounds.right = f + chipWidth;
            } else {
                float f2 = ((float) bounds.right) - offsetFromStart;
                outBounds.right = f2;
                outBounds.left = f2 - chipWidth;
            }
            float chipHeight = getCurrentChipIconHeight();
            float exactCenterY = bounds.exactCenterY() - (chipHeight / 2.0f);
            outBounds.top = exactCenterY;
            outBounds.bottom = exactCenterY + chipHeight;
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Paint.Align calculateTextOriginAndAlignment(@NonNull Rect bounds, @NonNull PointF pointF2) {
        pointF2.set(0.0f, 0.0f);
        Paint.Align align = Paint.Align.LEFT;
        if (this.text != null) {
            float offsetFromStart = this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                pointF2.x = ((float) bounds.left) + offsetFromStart;
                align = Paint.Align.LEFT;
            } else {
                pointF2.x = ((float) bounds.right) - offsetFromStart;
                align = Paint.Align.RIGHT;
            }
            pointF2.y = ((float) bounds.centerY()) - calculateTextCenterFromBaseline();
        }
        return align;
    }

    private float calculateTextCenterFromBaseline() {
        this.textDrawableHelper.getTextPaint().getFontMetrics(this.fontMetrics);
        Paint.FontMetrics fontMetrics2 = this.fontMetrics;
        return (fontMetrics2.descent + fontMetrics2.ascent) / 2.0f;
    }

    private void calculateTextBounds(@NonNull Rect bounds, @NonNull RectF outBounds) {
        outBounds.setEmpty();
        if (this.text != null) {
            float offsetFromStart = this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding;
            float offsetFromEnd = this.chipEndPadding + calculateCloseIconWidth() + this.textEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                outBounds.left = ((float) bounds.left) + offsetFromStart;
                outBounds.right = ((float) bounds.right) - offsetFromEnd;
            } else {
                outBounds.left = ((float) bounds.left) + offsetFromEnd;
                outBounds.right = ((float) bounds.right) - offsetFromStart;
            }
            outBounds.top = (float) bounds.top;
            outBounds.bottom = (float) bounds.bottom;
        }
    }

    private void calculateCloseIconBounds(@NonNull Rect bounds, @NonNull RectF outBounds) {
        outBounds.setEmpty();
        if (showsCloseIcon()) {
            float offsetFromEnd = this.chipEndPadding + this.closeIconEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                float f = ((float) bounds.right) - offsetFromEnd;
                outBounds.right = f;
                outBounds.left = f - this.closeIconSize;
            } else {
                float f2 = ((float) bounds.left) + offsetFromEnd;
                outBounds.left = f2;
                outBounds.right = f2 + this.closeIconSize;
            }
            float exactCenterY = bounds.exactCenterY();
            float f3 = this.closeIconSize;
            float f4 = exactCenterY - (f3 / 2.0f);
            outBounds.top = f4;
            outBounds.bottom = f4 + f3;
        }
    }

    private void calculateChipTouchBounds(@NonNull Rect bounds, @NonNull RectF outBounds) {
        outBounds.set(bounds);
        if (showsCloseIcon()) {
            float offsetFromEnd = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                outBounds.right = ((float) bounds.right) - offsetFromEnd;
            } else {
                outBounds.left = ((float) bounds.left) + offsetFromEnd;
            }
        }
    }

    private void calculateCloseIconTouchBounds(@NonNull Rect bounds, @NonNull RectF outBounds) {
        outBounds.setEmpty();
        if (showsCloseIcon()) {
            float offsetFromEnd = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                float f = (float) bounds.right;
                outBounds.right = f;
                outBounds.left = f - offsetFromEnd;
            } else {
                int i = bounds.left;
                outBounds.left = (float) i;
                outBounds.right = ((float) i) + offsetFromEnd;
            }
            outBounds.top = (float) bounds.top;
            outBounds.bottom = (float) bounds.bottom;
        }
    }

    public boolean isStateful() {
        return isStateful(this.chipSurfaceColor) || isStateful(this.chipBackgroundColor) || isStateful(this.chipStrokeColor) || (this.useCompatRipple && isStateful(this.compatRippleColor)) || isStateful(this.textDrawableHelper.getTextAppearance()) || canShowCheckedIcon() || isStateful(this.chipIcon) || isStateful(this.checkedIcon) || isStateful(this.tint);
    }

    public boolean isCloseIconStateful() {
        return isStateful(this.closeIcon);
    }

    public boolean setCloseIconState(@NonNull int[] stateSet) {
        if (Arrays.equals(this.closeIconStateSet, stateSet)) {
            return false;
        }
        this.closeIconStateSet = stateSet;
        if (showsCloseIcon()) {
            return onStateChange(getState(), stateSet);
        }
        return false;
    }

    @NonNull
    public int[] getCloseIconState() {
        return this.closeIconStateSet;
    }

    public void onTextSizeChange() {
        onSizeChange();
        invalidateSelf();
    }

    public boolean onStateChange(@NonNull int[] state) {
        if (this.isShapeThemingEnabled) {
            super.onStateChange(state);
        }
        return onStateChange(state, getCloseIconState());
    }

    private boolean onStateChange(@NonNull int[] chipState, @NonNull int[] closeIconState) {
        int[] iArr = chipState;
        int[] iArr2 = closeIconState;
        boolean invalidate = super.onStateChange(chipState);
        boolean sizeChanged = false;
        ColorStateList colorStateList = this.chipSurfaceColor;
        int newChipSurfaceColor = compositeElevationOverlayIfNeeded(colorStateList != null ? colorStateList.getColorForState(iArr, this.currentChipSurfaceColor) : 0);
        if (this.currentChipSurfaceColor != newChipSurfaceColor) {
            this.currentChipSurfaceColor = newChipSurfaceColor;
            invalidate = true;
        }
        ColorStateList colorStateList2 = this.chipBackgroundColor;
        int newChipBackgroundColor = compositeElevationOverlayIfNeeded(colorStateList2 != null ? colorStateList2.getColorForState(iArr, this.currentChipBackgroundColor) : 0);
        if (this.currentChipBackgroundColor != newChipBackgroundColor) {
            this.currentChipBackgroundColor = newChipBackgroundColor;
            invalidate = true;
        }
        int newCompositeSurfaceBackgroundColor = MaterialColors.layer(newChipSurfaceColor, newChipBackgroundColor);
        boolean newChecked = true;
        if ((this.currentCompositeSurfaceBackgroundColor != newCompositeSurfaceBackgroundColor) || (getFillColor() == null)) {
            this.currentCompositeSurfaceBackgroundColor = newCompositeSurfaceBackgroundColor;
            setFillColor(ColorStateList.valueOf(newCompositeSurfaceBackgroundColor));
            invalidate = true;
        }
        ColorStateList colorStateList3 = this.chipStrokeColor;
        int newChipStrokeColor = colorStateList3 != null ? colorStateList3.getColorForState(iArr, this.currentChipStrokeColor) : 0;
        if (this.currentChipStrokeColor != newChipStrokeColor) {
            this.currentChipStrokeColor = newChipStrokeColor;
            invalidate = true;
        }
        int newCompatRippleColor = (this.compatRippleColor == null || !RippleUtils.shouldDrawRippleCompat(chipState)) ? 0 : this.compatRippleColor.getColorForState(iArr, this.currentCompatRippleColor);
        if (this.currentCompatRippleColor != newCompatRippleColor) {
            this.currentCompatRippleColor = newCompatRippleColor;
            if (this.useCompatRipple) {
                invalidate = true;
            }
        }
        int newTextColor = (this.textDrawableHelper.getTextAppearance() == null || this.textDrawableHelper.getTextAppearance().textColor == null) ? 0 : this.textDrawableHelper.getTextAppearance().textColor.getColorForState(iArr, this.currentTextColor);
        if (this.currentTextColor != newTextColor) {
            this.currentTextColor = newTextColor;
            invalidate = true;
        }
        if (!hasState(getState(), 16842912) || !this.checkable) {
            newChecked = false;
        }
        if (!(this.currentChecked == newChecked || this.checkedIcon == null)) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.currentChecked = newChecked;
            invalidate = true;
            if (oldChipIconWidth != calculateChipIconWidth()) {
                sizeChanged = true;
            }
        }
        ColorStateList colorStateList4 = this.tint;
        int newTint = colorStateList4 != null ? colorStateList4.getColorForState(iArr, this.currentTint) : 0;
        if (this.currentTint != newTint) {
            this.currentTint = newTint;
            this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, this.tintMode);
            invalidate = true;
        }
        if (isStateful(this.chipIcon)) {
            invalidate |= this.chipIcon.setState(iArr);
        }
        if (isStateful(this.checkedIcon)) {
            invalidate |= this.checkedIcon.setState(iArr);
        }
        if (isStateful(this.closeIcon)) {
            int[] closeIconMergedState = new int[(iArr.length + iArr2.length)];
            int i = newChipSurfaceColor;
            System.arraycopy(iArr, 0, closeIconMergedState, 0, iArr.length);
            System.arraycopy(iArr2, 0, closeIconMergedState, iArr.length, iArr2.length);
            invalidate |= this.closeIcon.setState(closeIconMergedState);
        }
        if (RippleUtils.USE_FRAMEWORK_RIPPLE && isStateful(this.closeIconRipple)) {
            invalidate |= this.closeIconRipple.setState(iArr2);
        }
        if (invalidate) {
            invalidateSelf();
        }
        if (sizeChanged) {
            onSizeChange();
        }
        return invalidate;
    }

    private static boolean isStateful(@Nullable ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    private static boolean isStateful(@Nullable Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0002, code lost:
        r0 = r1.textColor;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isStateful(@androidx.annotation.Nullable com.google.android.material.resources.TextAppearance r1) {
        /*
            if (r1 == 0) goto L_0x000e
            android.content.res.ColorStateList r0 = r1.textColor
            if (r0 == 0) goto L_0x000e
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x000e
            r0 = 1
            goto L_0x000f
        L_0x000e:
            r0 = 0
        L_0x000f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.ChipDrawable.isStateful(com.google.android.material.resources.TextAppearance):boolean");
    }

    public boolean onLayoutDirectionChanged(int layoutDirection) {
        boolean invalidate = super.onLayoutDirectionChanged(layoutDirection);
        if (showsChipIcon()) {
            invalidate |= DrawableCompat.setLayoutDirection(this.chipIcon, layoutDirection);
        }
        if (showsCheckedIcon()) {
            invalidate |= DrawableCompat.setLayoutDirection(this.checkedIcon, layoutDirection);
        }
        if (showsCloseIcon()) {
            invalidate |= DrawableCompat.setLayoutDirection(this.closeIcon, layoutDirection);
        }
        if (!invalidate) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        boolean invalidate = super.onLevelChange(level);
        if (showsChipIcon()) {
            invalidate |= this.chipIcon.setLevel(level);
        }
        if (showsCheckedIcon()) {
            invalidate |= this.checkedIcon.setLevel(level);
        }
        if (showsCloseIcon()) {
            invalidate |= this.closeIcon.setLevel(level);
        }
        if (invalidate) {
            invalidateSelf();
        }
        return invalidate;
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean invalidate = super.setVisible(visible, restart);
        if (showsChipIcon()) {
            invalidate |= this.chipIcon.setVisible(visible, restart);
        }
        if (showsCheckedIcon()) {
            invalidate |= this.checkedIcon.setVisible(visible, restart);
        }
        if (showsCloseIcon()) {
            invalidate |= this.closeIcon.setVisible(visible, restart);
        }
        if (invalidate) {
            invalidateSelf();
        }
        return invalidate;
    }

    public void setAlpha(int alpha2) {
        if (this.alpha != alpha2) {
            this.alpha = alpha2;
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter2) {
        if (this.colorFilter != colorFilter2) {
            this.colorFilter = colorFilter2;
            invalidateSelf();
        }
    }

    @Nullable
    public ColorFilter getColorFilter() {
        return this.colorFilter;
    }

    public void setTintList(@Nullable ColorStateList tint2) {
        if (this.tint != tint2) {
            this.tint = tint2;
            onStateChange(getState());
        }
    }

    public void setTintMode(@NonNull PorterDuff.Mode tintMode2) {
        if (this.tintMode != tintMode2) {
            this.tintMode = tintMode2;
            this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, tintMode2);
            invalidateSelf();
        }
    }

    public int getOpacity() {
        return -3;
    }

    @TargetApi(21)
    public void getOutline(@NonNull Outline outline) {
        if (this.isShapeThemingEnabled) {
            super.getOutline(outline);
            return;
        }
        Rect bounds = getBounds();
        if (!bounds.isEmpty()) {
            outline.setRoundRect(bounds, this.chipCornerRadius);
        } else {
            outline.setRoundRect(0, 0, getIntrinsicWidth(), getIntrinsicHeight(), this.chipCornerRadius);
        }
        outline.setAlpha(((float) getAlpha()) / 255.0f);
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

    private void unapplyChildDrawable(@Nullable Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback) null);
        }
    }

    private void applyChildDrawable(@Nullable Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(this);
            DrawableCompat.setLayoutDirection(drawable, DrawableCompat.getLayoutDirection(this));
            drawable.setLevel(getLevel());
            drawable.setVisible(isVisible(), false);
            if (drawable == this.closeIcon) {
                if (drawable.isStateful()) {
                    drawable.setState(getCloseIconState());
                }
                DrawableCompat.setTintList(drawable, this.closeIconTint);
                return;
            }
            if (drawable.isStateful()) {
                drawable.setState(getState());
            }
            Drawable drawable2 = this.chipIcon;
            if (drawable == drawable2 && this.hasChipIconTint) {
                DrawableCompat.setTintList(drawable2, this.chipIconTint);
            }
        }
    }

    @Nullable
    private ColorFilter getTintColorFilter() {
        ColorFilter colorFilter2 = this.colorFilter;
        return colorFilter2 != null ? colorFilter2 : this.tintFilter;
    }

    private void updateCompatRippleColor() {
        this.compatRippleColor = this.useCompatRipple ? RippleUtils.sanitizeRippleDrawableColor(this.rippleColor) : null;
    }

    private void setChipSurfaceColor(@Nullable ColorStateList chipSurfaceColor2) {
        if (this.chipSurfaceColor != chipSurfaceColor2) {
            this.chipSurfaceColor = chipSurfaceColor2;
            onStateChange(getState());
        }
    }

    private static boolean hasState(@Nullable int[] stateSet, @AttrRes int state) {
        if (stateSet == null) {
            return false;
        }
        for (int s : stateSet) {
            if (s == state) {
                return true;
            }
        }
        return false;
    }

    public void setTextSize(@Dimension float size) {
        TextAppearance textAppearance = getTextAppearance();
        if (textAppearance != null) {
            textAppearance.textSize = size;
            this.textDrawableHelper.getTextPaint().setTextSize(size);
            onTextSizeChange();
        }
    }

    @Nullable
    public ColorStateList getChipBackgroundColor() {
        return this.chipBackgroundColor;
    }

    public void setChipBackgroundColorResource(@ColorRes int id) {
        setChipBackgroundColor(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setChipBackgroundColor(@Nullable ColorStateList chipBackgroundColor2) {
        if (this.chipBackgroundColor != chipBackgroundColor2) {
            this.chipBackgroundColor = chipBackgroundColor2;
            onStateChange(getState());
        }
    }

    public float getChipMinHeight() {
        return this.chipMinHeight;
    }

    public void setChipMinHeightResource(@DimenRes int id) {
        setChipMinHeight(this.context.getResources().getDimension(id));
    }

    public void setChipMinHeight(float chipMinHeight2) {
        if (this.chipMinHeight != chipMinHeight2) {
            this.chipMinHeight = chipMinHeight2;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getChipCornerRadius() {
        return this.isShapeThemingEnabled ? getTopLeftCornerResolvedSize() : this.chipCornerRadius;
    }

    @Deprecated
    public void setChipCornerRadiusResource(@DimenRes int id) {
        setChipCornerRadius(this.context.getResources().getDimension(id));
    }

    @Deprecated
    public void setChipCornerRadius(float chipCornerRadius2) {
        if (this.chipCornerRadius != chipCornerRadius2) {
            this.chipCornerRadius = chipCornerRadius2;
            setShapeAppearanceModel(getShapeAppearanceModel().withCornerSize(chipCornerRadius2));
        }
    }

    @Nullable
    public ColorStateList getChipStrokeColor() {
        return this.chipStrokeColor;
    }

    public void setChipStrokeColorResource(@ColorRes int id) {
        setChipStrokeColor(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setChipStrokeColor(@Nullable ColorStateList chipStrokeColor2) {
        if (this.chipStrokeColor != chipStrokeColor2) {
            this.chipStrokeColor = chipStrokeColor2;
            if (this.isShapeThemingEnabled) {
                setStrokeColor(chipStrokeColor2);
            }
            onStateChange(getState());
        }
    }

    public float getChipStrokeWidth() {
        return this.chipStrokeWidth;
    }

    public void setChipStrokeWidthResource(@DimenRes int id) {
        setChipStrokeWidth(this.context.getResources().getDimension(id));
    }

    public void setChipStrokeWidth(float chipStrokeWidth2) {
        if (this.chipStrokeWidth != chipStrokeWidth2) {
            this.chipStrokeWidth = chipStrokeWidth2;
            this.chipPaint.setStrokeWidth(chipStrokeWidth2);
            if (this.isShapeThemingEnabled) {
                super.setStrokeWidth(chipStrokeWidth2);
            }
            invalidateSelf();
        }
    }

    @Nullable
    public ColorStateList getRippleColor() {
        return this.rippleColor;
    }

    public void setRippleColorResource(@ColorRes int id) {
        setRippleColor(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setRippleColor(@Nullable ColorStateList rippleColor2) {
        if (this.rippleColor != rippleColor2) {
            this.rippleColor = rippleColor2;
            updateCompatRippleColor();
            onStateChange(getState());
        }
    }

    @Nullable
    public CharSequence getText() {
        return this.text;
    }

    public void setTextResource(@StringRes int id) {
        setText(this.context.getResources().getString(id));
    }

    public void setText(@Nullable CharSequence text2) {
        if (text2 == null) {
            text2 = "";
        }
        if (!TextUtils.equals(this.text, text2)) {
            this.text = text2;
            this.textDrawableHelper.setTextWidthDirty(true);
            invalidateSelf();
            onSizeChange();
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

    public TextUtils.TruncateAt getEllipsize() {
        return this.truncateAt;
    }

    public void setEllipsize(@Nullable TextUtils.TruncateAt truncateAt2) {
        this.truncateAt = truncateAt2;
    }

    public boolean isChipIconVisible() {
        return this.chipIconVisible;
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return isChipIconVisible();
    }

    public void setChipIconVisible(@BoolRes int id) {
        setChipIconVisible(this.context.getResources().getBoolean(id));
    }

    public void setChipIconVisible(boolean chipIconVisible2) {
        if (this.chipIconVisible != chipIconVisible2) {
            boolean oldShowsChipIcon = showsChipIcon();
            this.chipIconVisible = chipIconVisible2;
            boolean newShowsChipIcon = showsChipIcon();
            if (oldShowsChipIcon != newShowsChipIcon) {
                if (newShowsChipIcon) {
                    applyChildDrawable(this.chipIcon);
                } else {
                    unapplyChildDrawable(this.chipIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Deprecated
    public void setChipIconEnabledResource(@BoolRes int id) {
        setChipIconVisible(id);
    }

    @Deprecated
    public void setChipIconEnabled(boolean chipIconEnabled) {
        setChipIconVisible(chipIconEnabled);
    }

    @Nullable
    public Drawable getChipIcon() {
        Drawable drawable = this.chipIcon;
        if (drawable != null) {
            return DrawableCompat.unwrap(drawable);
        }
        return null;
    }

    public void setChipIconResource(@DrawableRes int id) {
        setChipIcon(AppCompatResources.getDrawable(this.context, id));
    }

    public void setChipIcon(@Nullable Drawable chipIcon2) {
        Drawable oldChipIcon = getChipIcon();
        if (oldChipIcon != chipIcon2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.chipIcon = chipIcon2 != null ? DrawableCompat.wrap(chipIcon2).mutate() : null;
            float newChipIconWidth = calculateChipIconWidth();
            unapplyChildDrawable(oldChipIcon);
            if (showsChipIcon()) {
                applyChildDrawable(this.chipIcon);
            }
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    @Nullable
    public ColorStateList getChipIconTint() {
        return this.chipIconTint;
    }

    public void setChipIconTintResource(@ColorRes int id) {
        setChipIconTint(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setChipIconTint(@Nullable ColorStateList chipIconTint2) {
        this.hasChipIconTint = true;
        if (this.chipIconTint != chipIconTint2) {
            this.chipIconTint = chipIconTint2;
            if (showsChipIcon()) {
                DrawableCompat.setTintList(this.chipIcon, chipIconTint2);
            }
            onStateChange(getState());
        }
    }

    public float getChipIconSize() {
        return this.chipIconSize;
    }

    public void setChipIconSizeResource(@DimenRes int id) {
        setChipIconSize(this.context.getResources().getDimension(id));
    }

    public void setChipIconSize(float chipIconSize2) {
        if (this.chipIconSize != chipIconSize2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.chipIconSize = chipIconSize2;
            float newChipIconWidth = calculateChipIconWidth();
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public boolean isCloseIconVisible() {
        return this.closeIconVisible;
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return isCloseIconVisible();
    }

    public void setCloseIconVisible(@BoolRes int id) {
        setCloseIconVisible(this.context.getResources().getBoolean(id));
    }

    public void setCloseIconVisible(boolean closeIconVisible2) {
        if (this.closeIconVisible != closeIconVisible2) {
            boolean oldShowsCloseIcon = showsCloseIcon();
            this.closeIconVisible = closeIconVisible2;
            boolean newShowsCloseIcon = showsCloseIcon();
            if (oldShowsCloseIcon != newShowsCloseIcon) {
                if (newShowsCloseIcon) {
                    applyChildDrawable(this.closeIcon);
                } else {
                    unapplyChildDrawable(this.closeIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Deprecated
    public void setCloseIconEnabledResource(@BoolRes int id) {
        setCloseIconVisible(id);
    }

    @Deprecated
    public void setCloseIconEnabled(boolean closeIconEnabled) {
        setCloseIconVisible(closeIconEnabled);
    }

    @Nullable
    public Drawable getCloseIcon() {
        Drawable drawable = this.closeIcon;
        if (drawable != null) {
            return DrawableCompat.unwrap(drawable);
        }
        return null;
    }

    public void setCloseIconResource(@DrawableRes int id) {
        setCloseIcon(AppCompatResources.getDrawable(this.context, id));
    }

    public void setCloseIcon(@Nullable Drawable closeIcon2) {
        Drawable oldCloseIcon = getCloseIcon();
        if (oldCloseIcon != closeIcon2) {
            float oldCloseIconWidth = calculateCloseIconWidth();
            this.closeIcon = closeIcon2 != null ? DrawableCompat.wrap(closeIcon2).mutate() : null;
            if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
                updateFrameworkCloseIconRipple();
            }
            float newCloseIconWidth = calculateCloseIconWidth();
            unapplyChildDrawable(oldCloseIcon);
            if (showsCloseIcon()) {
                applyChildDrawable(this.closeIcon);
            }
            invalidateSelf();
            if (oldCloseIconWidth != newCloseIconWidth) {
                onSizeChange();
            }
        }
    }

    @TargetApi(21)
    private void updateFrameworkCloseIconRipple() {
        this.closeIconRipple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(getRippleColor()), this.closeIcon, closeIconRippleMask);
    }

    @Nullable
    public ColorStateList getCloseIconTint() {
        return this.closeIconTint;
    }

    public void setCloseIconTintResource(@ColorRes int id) {
        setCloseIconTint(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setCloseIconTint(@Nullable ColorStateList closeIconTint2) {
        if (this.closeIconTint != closeIconTint2) {
            this.closeIconTint = closeIconTint2;
            if (showsCloseIcon()) {
                DrawableCompat.setTintList(this.closeIcon, closeIconTint2);
            }
            onStateChange(getState());
        }
    }

    public float getCloseIconSize() {
        return this.closeIconSize;
    }

    public void setCloseIconSizeResource(@DimenRes int id) {
        setCloseIconSize(this.context.getResources().getDimension(id));
    }

    public void setCloseIconSize(float closeIconSize2) {
        if (this.closeIconSize != closeIconSize2) {
            this.closeIconSize = closeIconSize2;
            invalidateSelf();
            if (showsCloseIcon()) {
                onSizeChange();
            }
        }
    }

    public void setCloseIconContentDescription(@Nullable CharSequence closeIconContentDescription2) {
        if (this.closeIconContentDescription != closeIconContentDescription2) {
            this.closeIconContentDescription = BidiFormatter.getInstance().unicodeWrap(closeIconContentDescription2);
            invalidateSelf();
        }
    }

    @Nullable
    public CharSequence getCloseIconContentDescription() {
        return this.closeIconContentDescription;
    }

    public boolean isCheckable() {
        return this.checkable;
    }

    public void setCheckableResource(@BoolRes int id) {
        setCheckable(this.context.getResources().getBoolean(id));
    }

    public void setCheckable(boolean checkable2) {
        if (this.checkable != checkable2) {
            this.checkable = checkable2;
            float oldChipIconWidth = calculateChipIconWidth();
            if (!checkable2 && this.currentChecked) {
                this.currentChecked = false;
            }
            float newChipIconWidth = calculateChipIconWidth();
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public boolean isCheckedIconVisible() {
        return this.checkedIconVisible;
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return isCheckedIconVisible();
    }

    public void setCheckedIconVisible(@BoolRes int id) {
        setCheckedIconVisible(this.context.getResources().getBoolean(id));
    }

    public void setCheckedIconVisible(boolean checkedIconVisible2) {
        if (this.checkedIconVisible != checkedIconVisible2) {
            boolean oldShowsCheckedIcon = showsCheckedIcon();
            this.checkedIconVisible = checkedIconVisible2;
            boolean newShowsCheckedIcon = showsCheckedIcon();
            if (oldShowsCheckedIcon != newShowsCheckedIcon) {
                if (newShowsCheckedIcon) {
                    applyChildDrawable(this.checkedIcon);
                } else {
                    unapplyChildDrawable(this.checkedIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Deprecated
    public void setCheckedIconEnabledResource(@BoolRes int id) {
        setCheckedIconVisible(this.context.getResources().getBoolean(id));
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean checkedIconEnabled) {
        setCheckedIconVisible(checkedIconEnabled);
    }

    @Nullable
    public Drawable getCheckedIcon() {
        return this.checkedIcon;
    }

    public void setCheckedIconResource(@DrawableRes int id) {
        setCheckedIcon(AppCompatResources.getDrawable(this.context, id));
    }

    public void setCheckedIcon(@Nullable Drawable checkedIcon2) {
        if (this.checkedIcon != checkedIcon2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.checkedIcon = checkedIcon2;
            float newChipIconWidth = calculateChipIconWidth();
            unapplyChildDrawable(this.checkedIcon);
            applyChildDrawable(this.checkedIcon);
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    @Nullable
    public ColorStateList getCheckedIconTint() {
        return this.checkedIconTint;
    }

    public void setCheckedIconTintResource(@ColorRes int id) {
        setCheckedIconTint(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setCheckedIconTint(@Nullable ColorStateList checkedIconTint2) {
        if (this.checkedIconTint != checkedIconTint2) {
            this.checkedIconTint = checkedIconTint2;
            if (canShowCheckedIcon()) {
                DrawableCompat.setTintList(this.checkedIcon, checkedIconTint2);
            }
            onStateChange(getState());
        }
    }

    @Nullable
    public MotionSpec getShowMotionSpec() {
        return this.showMotionSpec;
    }

    public void setShowMotionSpecResource(@AnimatorRes int id) {
        setShowMotionSpec(MotionSpec.createFromResource(this.context, id));
    }

    public void setShowMotionSpec(@Nullable MotionSpec showMotionSpec2) {
        this.showMotionSpec = showMotionSpec2;
    }

    @Nullable
    public MotionSpec getHideMotionSpec() {
        return this.hideMotionSpec;
    }

    public void setHideMotionSpecResource(@AnimatorRes int id) {
        setHideMotionSpec(MotionSpec.createFromResource(this.context, id));
    }

    public void setHideMotionSpec(@Nullable MotionSpec hideMotionSpec2) {
        this.hideMotionSpec = hideMotionSpec2;
    }

    public float getChipStartPadding() {
        return this.chipStartPadding;
    }

    public void setChipStartPaddingResource(@DimenRes int id) {
        setChipStartPadding(this.context.getResources().getDimension(id));
    }

    public void setChipStartPadding(float chipStartPadding2) {
        if (this.chipStartPadding != chipStartPadding2) {
            this.chipStartPadding = chipStartPadding2;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getIconStartPadding() {
        return this.iconStartPadding;
    }

    public void setIconStartPaddingResource(@DimenRes int id) {
        setIconStartPadding(this.context.getResources().getDimension(id));
    }

    public void setIconStartPadding(float iconStartPadding2) {
        if (this.iconStartPadding != iconStartPadding2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.iconStartPadding = iconStartPadding2;
            float newChipIconWidth = calculateChipIconWidth();
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public float getIconEndPadding() {
        return this.iconEndPadding;
    }

    public void setIconEndPaddingResource(@DimenRes int id) {
        setIconEndPadding(this.context.getResources().getDimension(id));
    }

    public void setIconEndPadding(float iconEndPadding2) {
        if (this.iconEndPadding != iconEndPadding2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.iconEndPadding = iconEndPadding2;
            float newChipIconWidth = calculateChipIconWidth();
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public float getTextStartPadding() {
        return this.textStartPadding;
    }

    public void setTextStartPaddingResource(@DimenRes int id) {
        setTextStartPadding(this.context.getResources().getDimension(id));
    }

    public void setTextStartPadding(float textStartPadding2) {
        if (this.textStartPadding != textStartPadding2) {
            this.textStartPadding = textStartPadding2;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getTextEndPadding() {
        return this.textEndPadding;
    }

    public void setTextEndPaddingResource(@DimenRes int id) {
        setTextEndPadding(this.context.getResources().getDimension(id));
    }

    public void setTextEndPadding(float textEndPadding2) {
        if (this.textEndPadding != textEndPadding2) {
            this.textEndPadding = textEndPadding2;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getCloseIconStartPadding() {
        return this.closeIconStartPadding;
    }

    public void setCloseIconStartPaddingResource(@DimenRes int id) {
        setCloseIconStartPadding(this.context.getResources().getDimension(id));
    }

    public void setCloseIconStartPadding(float closeIconStartPadding2) {
        if (this.closeIconStartPadding != closeIconStartPadding2) {
            this.closeIconStartPadding = closeIconStartPadding2;
            invalidateSelf();
            if (showsCloseIcon()) {
                onSizeChange();
            }
        }
    }

    public float getCloseIconEndPadding() {
        return this.closeIconEndPadding;
    }

    public void setCloseIconEndPaddingResource(@DimenRes int id) {
        setCloseIconEndPadding(this.context.getResources().getDimension(id));
    }

    public void setCloseIconEndPadding(float closeIconEndPadding2) {
        if (this.closeIconEndPadding != closeIconEndPadding2) {
            this.closeIconEndPadding = closeIconEndPadding2;
            invalidateSelf();
            if (showsCloseIcon()) {
                onSizeChange();
            }
        }
    }

    public float getChipEndPadding() {
        return this.chipEndPadding;
    }

    public void setChipEndPaddingResource(@DimenRes int id) {
        setChipEndPadding(this.context.getResources().getDimension(id));
    }

    public void setChipEndPadding(float chipEndPadding2) {
        if (this.chipEndPadding != chipEndPadding2) {
            this.chipEndPadding = chipEndPadding2;
            invalidateSelf();
            onSizeChange();
        }
    }

    @Px
    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setMaxWidth(@Px int maxWidth2) {
        this.maxWidth = maxWidth2;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldDrawText() {
        return this.shouldDrawText;
    }

    /* access modifiers changed from: package-private */
    public void setShouldDrawText(boolean shouldDrawText2) {
        this.shouldDrawText = shouldDrawText2;
    }
}
