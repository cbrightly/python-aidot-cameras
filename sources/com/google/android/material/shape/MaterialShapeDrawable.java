package com.google.android.material.shape;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.util.ObjectsCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.ShapePath;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.BitSet;

public class MaterialShapeDrawable extends Drawable implements TintAwareDrawable, Shapeable {
    public static final int SHADOW_COMPAT_MODE_ALWAYS = 2;
    public static final int SHADOW_COMPAT_MODE_DEFAULT = 0;
    public static final int SHADOW_COMPAT_MODE_NEVER = 1;
    private static final float SHADOW_OFFSET_MULTIPLIER = 0.25f;
    private static final float SHADOW_RADIUS_MULTIPLIER = 0.75f;
    private static final String TAG = MaterialShapeDrawable.class.getSimpleName();
    private static final Paint clearPaint = new Paint(1);
    /* access modifiers changed from: private */
    public final BitSet containsIncompatibleShadowOp;
    /* access modifiers changed from: private */
    public final ShapePath.ShadowCompatOperation[] cornerShadowOperation;
    private MaterialShapeDrawableState drawableState;
    /* access modifiers changed from: private */
    public final ShapePath.ShadowCompatOperation[] edgeShadowOperation;
    private final Paint fillPaint;
    private final RectF insetRectF;
    private final Matrix matrix;
    private final Path path;
    @NonNull
    private final RectF pathBounds;
    /* access modifiers changed from: private */
    public boolean pathDirty;
    private final Path pathInsetByStroke;
    private final ShapeAppearancePathProvider pathProvider;
    @NonNull
    private final ShapeAppearancePathProvider.PathListener pathShadowListener;
    private final RectF rectF;
    private final Region scratchRegion;
    private boolean shadowBitmapDrawingEnable;
    private final ShadowRenderer shadowRenderer;
    private final Paint strokePaint;
    private ShapeAppearanceModel strokeShapeAppearance;
    @Nullable
    private PorterDuffColorFilter strokeTintFilter;
    @Nullable
    private PorterDuffColorFilter tintFilter;
    private final Region transparentRegion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CompatibilityShadowMode {
    }

    @NonNull
    public static MaterialShapeDrawable createWithElevationOverlay(Context context) {
        return createWithElevationOverlay(context, 0.0f);
    }

    @NonNull
    public static MaterialShapeDrawable createWithElevationOverlay(Context context, float elevation) {
        int colorSurface = MaterialColors.getColor(context, R.attr.colorSurface, MaterialShapeDrawable.class.getSimpleName());
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        materialShapeDrawable.initializeElevationOverlay(context);
        materialShapeDrawable.setFillColor(ColorStateList.valueOf(colorSurface));
        materialShapeDrawable.setElevation(elevation);
        return materialShapeDrawable;
    }

    public MaterialShapeDrawable() {
        this(new ShapeAppearanceModel());
    }

    public MaterialShapeDrawable(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        this(ShapeAppearanceModel.builder(context, attrs, defStyleAttr, defStyleRes).build());
    }

    @Deprecated
    public MaterialShapeDrawable(@NonNull ShapePathModel shapePathModel) {
        this((ShapeAppearanceModel) shapePathModel);
    }

    public MaterialShapeDrawable(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this(new MaterialShapeDrawableState(shapeAppearanceModel, (ElevationOverlayProvider) null));
    }

    private MaterialShapeDrawable(@NonNull MaterialShapeDrawableState drawableState2) {
        this.cornerShadowOperation = new ShapePath.ShadowCompatOperation[4];
        this.edgeShadowOperation = new ShapePath.ShadowCompatOperation[4];
        this.containsIncompatibleShadowOp = new BitSet(8);
        this.matrix = new Matrix();
        this.path = new Path();
        this.pathInsetByStroke = new Path();
        this.rectF = new RectF();
        this.insetRectF = new RectF();
        this.transparentRegion = new Region();
        this.scratchRegion = new Region();
        Paint paint = new Paint(1);
        this.fillPaint = paint;
        Paint paint2 = new Paint(1);
        this.strokePaint = paint2;
        this.shadowRenderer = new ShadowRenderer();
        this.pathProvider = Looper.getMainLooper().getThread() == Thread.currentThread() ? ShapeAppearancePathProvider.getInstance() : new ShapeAppearancePathProvider();
        this.pathBounds = new RectF();
        this.shadowBitmapDrawingEnable = true;
        this.drawableState = drawableState2;
        paint2.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        Paint paint3 = clearPaint;
        paint3.setColor(-1);
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        updateTintFilter();
        updateColorsForState(getState());
        this.pathShadowListener = new ShapeAppearancePathProvider.PathListener() {
            public void onCornerPathCreated(@NonNull ShapePath cornerPath, Matrix transform, int count) {
                MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(count, cornerPath.containsIncompatibleShadowOp());
                MaterialShapeDrawable.this.cornerShadowOperation[count] = cornerPath.createShadowCompatOperation(transform);
            }

            public void onEdgePathCreated(@NonNull ShapePath edgePath, Matrix transform, int count) {
                MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(count + 4, edgePath.containsIncompatibleShadowOp());
                MaterialShapeDrawable.this.edgeShadowOperation[count] = edgePath.createShadowCompatOperation(transform);
            }
        };
    }

    @Nullable
    public Drawable.ConstantState getConstantState() {
        return this.drawableState;
    }

    @NonNull
    public Drawable mutate() {
        this.drawableState = new MaterialShapeDrawableState(this.drawableState);
        return this;
    }

    private static int modulateAlpha(int paintAlpha, int alpha) {
        return (paintAlpha * ((alpha >>> 7) + alpha)) >>> 8;
    }

    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.drawableState.shapeAppearanceModel = shapeAppearanceModel;
        invalidateSelf();
    }

    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.drawableState.shapeAppearanceModel;
    }

    @Deprecated
    public void setShapedViewModel(@NonNull ShapePathModel shapedViewModel) {
        setShapeAppearanceModel(shapedViewModel);
    }

    @Deprecated
    @Nullable
    public ShapePathModel getShapedViewModel() {
        ShapeAppearanceModel shapeAppearance = getShapeAppearanceModel();
        if (shapeAppearance instanceof ShapePathModel) {
            return (ShapePathModel) shapeAppearance;
        }
        return null;
    }

    public void setFillColor(@Nullable ColorStateList fillColor) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.fillColor != fillColor) {
            materialShapeDrawableState.fillColor = fillColor;
            onStateChange(getState());
        }
    }

    @Nullable
    public ColorStateList getFillColor() {
        return this.drawableState.fillColor;
    }

    public void setStrokeColor(@Nullable ColorStateList strokeColor) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.strokeColor != strokeColor) {
            materialShapeDrawableState.strokeColor = strokeColor;
            onStateChange(getState());
        }
    }

    @Nullable
    public ColorStateList getStrokeColor() {
        return this.drawableState.strokeColor;
    }

    public void setTintMode(@Nullable PorterDuff.Mode tintMode) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.tintMode != tintMode) {
            materialShapeDrawableState.tintMode = tintMode;
            updateTintFilter();
            invalidateSelfIgnoreShape();
        }
    }

    public void setTintList(@Nullable ColorStateList tintList) {
        this.drawableState.tintList = tintList;
        updateTintFilter();
        invalidateSelfIgnoreShape();
    }

    @Nullable
    public ColorStateList getTintList() {
        return this.drawableState.tintList;
    }

    @Nullable
    public ColorStateList getStrokeTintList() {
        return this.drawableState.strokeTintList;
    }

    public void setTint(@ColorInt int tintColor) {
        setTintList(ColorStateList.valueOf(tintColor));
    }

    public void setStrokeTint(ColorStateList tintList) {
        this.drawableState.strokeTintList = tintList;
        updateTintFilter();
        invalidateSelfIgnoreShape();
    }

    public void setStrokeTint(@ColorInt int tintColor) {
        setStrokeTint(ColorStateList.valueOf(tintColor));
    }

    public void setStroke(float strokeWidth, @ColorInt int strokeColor) {
        setStrokeWidth(strokeWidth);
        setStrokeColor(ColorStateList.valueOf(strokeColor));
    }

    public void setStroke(float strokeWidth, @Nullable ColorStateList strokeColor) {
        setStrokeWidth(strokeWidth);
        setStrokeColor(strokeColor);
    }

    public float getStrokeWidth() {
        return this.drawableState.strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.drawableState.strokeWidth = strokeWidth;
        invalidateSelf();
    }

    public int getOpacity() {
        return -3;
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.alpha != alpha) {
            materialShapeDrawableState.alpha = alpha;
            invalidateSelfIgnoreShape();
        }
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.drawableState.colorFilter = colorFilter;
        invalidateSelfIgnoreShape();
    }

    public Region getTransparentRegion() {
        this.transparentRegion.set(getBounds());
        calculatePath(getBoundsAsRectF(), this.path);
        this.scratchRegion.setPath(this.path, this.transparentRegion);
        this.transparentRegion.op(this.scratchRegion, Region.Op.DIFFERENCE);
        return this.transparentRegion;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public RectF getBoundsAsRectF() {
        this.rectF.set(getBounds());
        return this.rectF;
    }

    public void setCornerSize(float cornerSize) {
        setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(cornerSize));
    }

    public void setCornerSize(@NonNull CornerSize cornerSize) {
        setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(cornerSize));
    }

    public boolean isPointInTransparentRegion(int x, int y) {
        return getTransparentRegion().contains(x, y);
    }

    public int getShadowCompatibilityMode() {
        return this.drawableState.shadowCompatMode;
    }

    public boolean getPadding(@NonNull Rect padding) {
        Rect rect = this.drawableState.padding;
        if (rect == null) {
            return super.getPadding(padding);
        }
        padding.set(rect);
        return true;
    }

    public void setPadding(int left, int top, int right, int bottom) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.padding == null) {
            materialShapeDrawableState.padding = new Rect();
        }
        this.drawableState.padding.set(left, top, right, bottom);
        invalidateSelf();
    }

    public void setShadowCompatibilityMode(int mode) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.shadowCompatMode != mode) {
            materialShapeDrawableState.shadowCompatMode = mode;
            invalidateSelfIgnoreShape();
        }
    }

    @Deprecated
    public boolean isShadowEnabled() {
        int i = this.drawableState.shadowCompatMode;
        return i == 0 || i == 2;
    }

    @Deprecated
    public void setShadowEnabled(boolean shadowEnabled) {
        setShadowCompatibilityMode(shadowEnabled ^ true ? 1 : 0);
    }

    public boolean isElevationOverlayEnabled() {
        ElevationOverlayProvider elevationOverlayProvider = this.drawableState.elevationOverlayProvider;
        return elevationOverlayProvider != null && elevationOverlayProvider.isThemeElevationOverlayEnabled();
    }

    public boolean isElevationOverlayInitialized() {
        return this.drawableState.elevationOverlayProvider != null;
    }

    public void initializeElevationOverlay(Context context) {
        this.drawableState.elevationOverlayProvider = new ElevationOverlayProvider(context);
        updateZ();
    }

    /* access modifiers changed from: protected */
    @ColorInt
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int compositeElevationOverlayIfNeeded(@ColorInt int backgroundColor) {
        float elevation = getZ() + getParentAbsoluteElevation();
        ElevationOverlayProvider elevationOverlayProvider = this.drawableState.elevationOverlayProvider;
        return elevationOverlayProvider != null ? elevationOverlayProvider.compositeOverlayIfNeeded(backgroundColor, elevation) : backgroundColor;
    }

    public float getInterpolation() {
        return this.drawableState.interpolation;
    }

    public void setInterpolation(float interpolation) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.interpolation != interpolation) {
            materialShapeDrawableState.interpolation = interpolation;
            this.pathDirty = true;
            invalidateSelf();
        }
    }

    public float getParentAbsoluteElevation() {
        return this.drawableState.parentAbsoluteElevation;
    }

    public void setParentAbsoluteElevation(float parentAbsoluteElevation) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.parentAbsoluteElevation != parentAbsoluteElevation) {
            materialShapeDrawableState.parentAbsoluteElevation = parentAbsoluteElevation;
            updateZ();
        }
    }

    public float getElevation() {
        return this.drawableState.elevation;
    }

    public void setElevation(float elevation) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.elevation != elevation) {
            materialShapeDrawableState.elevation = elevation;
            updateZ();
        }
    }

    public float getTranslationZ() {
        return this.drawableState.translationZ;
    }

    public void setTranslationZ(float translationZ) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.translationZ != translationZ) {
            materialShapeDrawableState.translationZ = translationZ;
            updateZ();
        }
    }

    public float getZ() {
        return getElevation() + getTranslationZ();
    }

    public void setZ(float z) {
        setTranslationZ(z - getElevation());
    }

    private void updateZ() {
        float z = getZ();
        this.drawableState.shadowCompatRadius = (int) Math.ceil((double) (0.75f * z));
        this.drawableState.shadowCompatOffset = (int) Math.ceil((double) (SHADOW_OFFSET_MULTIPLIER * z));
        updateTintFilter();
        invalidateSelfIgnoreShape();
    }

    @Deprecated
    public int getShadowElevation() {
        return (int) getElevation();
    }

    @Deprecated
    public void setShadowElevation(int shadowElevation) {
        setElevation((float) shadowElevation);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getShadowVerticalOffset() {
        return this.drawableState.shadowCompatOffset;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setShadowBitmapDrawingEnable(boolean enable) {
        this.shadowBitmapDrawingEnable = enable;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setEdgeIntersectionCheckEnable(boolean enable) {
        this.pathProvider.setEdgeIntersectionCheckEnable(enable);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setShadowVerticalOffset(int shadowOffset) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.shadowCompatOffset != shadowOffset) {
            materialShapeDrawableState.shadowCompatOffset = shadowOffset;
            invalidateSelfIgnoreShape();
        }
    }

    public int getShadowCompatRotation() {
        return this.drawableState.shadowCompatRotation;
    }

    public void setShadowCompatRotation(int shadowRotation) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.shadowCompatRotation != shadowRotation) {
            materialShapeDrawableState.shadowCompatRotation = shadowRotation;
            invalidateSelfIgnoreShape();
        }
    }

    public int getShadowRadius() {
        return this.drawableState.shadowCompatRadius;
    }

    @Deprecated
    public void setShadowRadius(int shadowRadius) {
        this.drawableState.shadowCompatRadius = shadowRadius;
    }

    public boolean requiresCompatShadow() {
        int i = Build.VERSION.SDK_INT;
        return i < 21 || (!isRoundRect() && !this.path.isConvex() && i < 29);
    }

    public float getScale() {
        return this.drawableState.scale;
    }

    public void setScale(float scale) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.scale != scale) {
            materialShapeDrawableState.scale = scale;
            invalidateSelf();
        }
    }

    public void invalidateSelf() {
        this.pathDirty = true;
        super.invalidateSelf();
    }

    private void invalidateSelfIgnoreShape() {
        super.invalidateSelf();
    }

    public void setUseTintColorForShadow(boolean useTintColorForShadow) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.useTintColorForShadow != useTintColorForShadow) {
            materialShapeDrawableState.useTintColorForShadow = useTintColorForShadow;
            invalidateSelf();
        }
    }

    public void setShadowColor(int shadowColor) {
        this.shadowRenderer.setShadowColor(shadowColor);
        this.drawableState.useTintColorForShadow = false;
        invalidateSelfIgnoreShape();
    }

    public Paint.Style getPaintStyle() {
        return this.drawableState.paintStyle;
    }

    public void setPaintStyle(Paint.Style paintStyle) {
        this.drawableState.paintStyle = paintStyle;
        invalidateSelfIgnoreShape();
    }

    private boolean hasCompatShadow() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        int i = materialShapeDrawableState.shadowCompatMode;
        if (i == 1 || materialShapeDrawableState.shadowCompatRadius <= 0 || (i != 2 && !requiresCompatShadow())) {
            return false;
        }
        return true;
    }

    private boolean hasFill() {
        Paint.Style style = this.drawableState.paintStyle;
        return style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.FILL;
    }

    private boolean hasStroke() {
        Paint.Style style = this.drawableState.paintStyle;
        return (style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.STROKE) && this.strokePaint.getStrokeWidth() > 0.0f;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        this.pathDirty = true;
        super.onBoundsChange(bounds);
    }

    public void draw(@NonNull Canvas canvas) {
        this.fillPaint.setColorFilter(this.tintFilter);
        int prevAlpha = this.fillPaint.getAlpha();
        this.fillPaint.setAlpha(modulateAlpha(prevAlpha, this.drawableState.alpha));
        this.strokePaint.setColorFilter(this.strokeTintFilter);
        this.strokePaint.setStrokeWidth(this.drawableState.strokeWidth);
        int prevStrokeAlpha = this.strokePaint.getAlpha();
        this.strokePaint.setAlpha(modulateAlpha(prevStrokeAlpha, this.drawableState.alpha));
        if (this.pathDirty) {
            calculateStrokePath();
            calculatePath(getBoundsAsRectF(), this.path);
            this.pathDirty = false;
        }
        maybeDrawCompatShadow(canvas);
        if (hasFill()) {
            drawFillShape(canvas);
        }
        if (hasStroke()) {
            drawStrokeShape(canvas);
        }
        this.fillPaint.setAlpha(prevAlpha);
        this.strokePaint.setAlpha(prevStrokeAlpha);
    }

    private void maybeDrawCompatShadow(@NonNull Canvas canvas) {
        if (hasCompatShadow()) {
            canvas.save();
            prepareCanvasForShadow(canvas);
            if (!this.shadowBitmapDrawingEnable) {
                drawCompatShadow(canvas);
                canvas.restore();
                return;
            }
            int pathExtraWidth = (int) (this.pathBounds.width() - ((float) getBounds().width()));
            int pathExtraHeight = (int) (this.pathBounds.height() - ((float) getBounds().height()));
            if (pathExtraWidth < 0 || pathExtraHeight < 0) {
                throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
            }
            Bitmap shadowLayer = Bitmap.createBitmap(((int) this.pathBounds.width()) + (this.drawableState.shadowCompatRadius * 2) + pathExtraWidth, ((int) this.pathBounds.height()) + (this.drawableState.shadowCompatRadius * 2) + pathExtraHeight, Bitmap.Config.ARGB_8888);
            Canvas shadowCanvas = new Canvas(shadowLayer);
            float shadowLeft = (float) ((getBounds().left - this.drawableState.shadowCompatRadius) - pathExtraWidth);
            float shadowTop = (float) ((getBounds().top - this.drawableState.shadowCompatRadius) - pathExtraHeight);
            shadowCanvas.translate(-shadowLeft, -shadowTop);
            drawCompatShadow(shadowCanvas);
            canvas.drawBitmap(shadowLayer, shadowLeft, shadowTop, (Paint) null);
            shadowLayer.recycle();
            canvas.restore();
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void drawShape(@NonNull Canvas canvas, @NonNull Paint paint, @NonNull Path path2, @NonNull RectF bounds) {
        drawShape(canvas, paint, path2, this.drawableState.shapeAppearanceModel, bounds);
    }

    private void drawShape(@NonNull Canvas canvas, @NonNull Paint paint, @NonNull Path path2, @NonNull ShapeAppearanceModel shapeAppearanceModel, @NonNull RectF bounds) {
        if (shapeAppearanceModel.isRoundRect(bounds)) {
            float cornerSize = shapeAppearanceModel.getTopRightCornerSize().getCornerSize(bounds) * this.drawableState.interpolation;
            canvas.drawRoundRect(bounds, cornerSize, cornerSize, paint);
            return;
        }
        canvas.drawPath(path2, paint);
    }

    private void drawFillShape(@NonNull Canvas canvas) {
        drawShape(canvas, this.fillPaint, this.path, this.drawableState.shapeAppearanceModel, getBoundsAsRectF());
    }

    private void drawStrokeShape(@NonNull Canvas canvas) {
        drawShape(canvas, this.strokePaint, this.pathInsetByStroke, this.strokeShapeAppearance, getBoundsInsetByStroke());
    }

    private void prepareCanvasForShadow(@NonNull Canvas canvas) {
        int shadowOffsetX = getShadowOffsetX();
        int shadowOffsetY = getShadowOffsetY();
        if (Build.VERSION.SDK_INT < 21 && this.shadowBitmapDrawingEnable) {
            Rect canvasClipBounds = canvas.getClipBounds();
            int i = this.drawableState.shadowCompatRadius;
            canvasClipBounds.inset(-i, -i);
            canvasClipBounds.offset(shadowOffsetX, shadowOffsetY);
            canvas.clipRect(canvasClipBounds, Region.Op.REPLACE);
        }
        canvas.translate((float) shadowOffsetX, (float) shadowOffsetY);
    }

    private void drawCompatShadow(@NonNull Canvas canvas) {
        if (this.containsIncompatibleShadowOp.cardinality() > 0) {
            Log.w(TAG, "Compatibility shadow requested but can't be drawn for all operations in this shape.");
        }
        if (this.drawableState.shadowCompatOffset != 0) {
            canvas.drawPath(this.path, this.shadowRenderer.getShadowPaint());
        }
        for (int index = 0; index < 4; index++) {
            this.cornerShadowOperation[index].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, canvas);
            this.edgeShadowOperation[index].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, canvas);
        }
        if (this.shadowBitmapDrawingEnable != 0) {
            int shadowOffsetX = getShadowOffsetX();
            int shadowOffsetY = getShadowOffsetY();
            canvas.translate((float) (-shadowOffsetX), (float) (-shadowOffsetY));
            canvas.drawPath(this.path, clearPaint);
            canvas.translate((float) shadowOffsetX, (float) shadowOffsetY);
        }
    }

    public int getShadowOffsetX() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        return (int) (((double) materialShapeDrawableState.shadowCompatOffset) * Math.sin(Math.toRadians((double) materialShapeDrawableState.shadowCompatRotation)));
    }

    public int getShadowOffsetY() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        return (int) (((double) materialShapeDrawableState.shadowCompatOffset) * Math.cos(Math.toRadians((double) materialShapeDrawableState.shadowCompatRotation)));
    }

    @Deprecated
    public void getPathForSize(int width, int height, @NonNull Path path2) {
        calculatePathForSize(new RectF(0.0f, 0.0f, (float) width, (float) height), path2);
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void calculatePathForSize(@NonNull RectF bounds, @NonNull Path path2) {
        ShapeAppearancePathProvider shapeAppearancePathProvider = this.pathProvider;
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        ShapeAppearanceModel shapeAppearanceModel = materialShapeDrawableState.shapeAppearanceModel;
        float f = materialShapeDrawableState.interpolation;
        shapeAppearancePathProvider.calculatePath(shapeAppearanceModel, f, bounds, this.pathShadowListener, path2);
    }

    private void calculateStrokePath() {
        final float strokeInsetLength = -getStrokeInsetLength();
        ShapeAppearanceModel withTransformedCornerSizes = getShapeAppearanceModel().withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() {
            @NonNull
            public CornerSize apply(@NonNull CornerSize cornerSize) {
                return cornerSize instanceof RelativeCornerSize ? cornerSize : new AdjustedCornerSize(strokeInsetLength, cornerSize);
            }
        });
        this.strokeShapeAppearance = withTransformedCornerSizes;
        this.pathProvider.calculatePath(withTransformedCornerSizes, this.drawableState.interpolation, getBoundsInsetByStroke(), this.pathInsetByStroke);
    }

    @TargetApi(21)
    public void getOutline(@NonNull Outline outline) {
        if (this.drawableState.shadowCompatMode != 2) {
            if (isRoundRect()) {
                outline.setRoundRect(getBounds(), getTopLeftCornerResolvedSize() * this.drawableState.interpolation);
                return;
            }
            calculatePath(getBoundsAsRectF(), this.path);
            if (this.path.isConvex() || Build.VERSION.SDK_INT >= 29) {
                try {
                    outline.setConvexPath(this.path);
                } catch (IllegalArgumentException e) {
                }
            }
        }
    }

    private void calculatePath(@NonNull RectF bounds, @NonNull Path path2) {
        calculatePathForSize(bounds, path2);
        if (this.drawableState.scale != 1.0f) {
            this.matrix.reset();
            Matrix matrix2 = this.matrix;
            float f = this.drawableState.scale;
            matrix2.setScale(f, f, bounds.width() / 2.0f, bounds.height() / 2.0f);
            path2.transform(this.matrix);
        }
        path2.computeBounds(this.pathBounds, true);
    }

    private boolean updateTintFilter() {
        PorterDuffColorFilter originalTintFilter = this.tintFilter;
        PorterDuffColorFilter originalStrokeTintFilter = this.strokeTintFilter;
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        this.tintFilter = calculateTintFilter(materialShapeDrawableState.tintList, materialShapeDrawableState.tintMode, this.fillPaint, true);
        MaterialShapeDrawableState materialShapeDrawableState2 = this.drawableState;
        this.strokeTintFilter = calculateTintFilter(materialShapeDrawableState2.strokeTintList, materialShapeDrawableState2.tintMode, this.strokePaint, false);
        MaterialShapeDrawableState materialShapeDrawableState3 = this.drawableState;
        if (materialShapeDrawableState3.useTintColorForShadow) {
            this.shadowRenderer.setShadowColor(materialShapeDrawableState3.tintList.getColorForState(getState(), 0));
        }
        if (!ObjectsCompat.equals(originalTintFilter, this.tintFilter) || !ObjectsCompat.equals(originalStrokeTintFilter, this.strokeTintFilter)) {
            return true;
        }
        return false;
    }

    @NonNull
    private PorterDuffColorFilter calculateTintFilter(@Nullable ColorStateList tintList, @Nullable PorterDuff.Mode tintMode, @NonNull Paint paint, boolean requiresElevationOverlay) {
        if (tintList == null || tintMode == null) {
            return calculatePaintColorTintFilter(paint, requiresElevationOverlay);
        }
        return calculateTintColorTintFilter(tintList, tintMode, requiresElevationOverlay);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0002, code lost:
        r0 = r5.getColor();
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.PorterDuffColorFilter calculatePaintColorTintFilter(@androidx.annotation.NonNull android.graphics.Paint r5, boolean r6) {
        /*
            r4 = this;
            if (r6 == 0) goto L_0x0014
            int r0 = r5.getColor()
            int r1 = r4.compositeElevationOverlayIfNeeded(r0)
            if (r1 == r0) goto L_0x0014
            android.graphics.PorterDuffColorFilter r2 = new android.graphics.PorterDuffColorFilter
            android.graphics.PorterDuff$Mode r3 = android.graphics.PorterDuff.Mode.SRC_IN
            r2.<init>(r1, r3)
            return r2
        L_0x0014:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.shape.MaterialShapeDrawable.calculatePaintColorTintFilter(android.graphics.Paint, boolean):android.graphics.PorterDuffColorFilter");
    }

    @NonNull
    private PorterDuffColorFilter calculateTintColorTintFilter(@NonNull ColorStateList tintList, @NonNull PorterDuff.Mode tintMode, boolean requiresElevationOverlay) {
        int tintColor = tintList.getColorForState(getState(), 0);
        if (requiresElevationOverlay) {
            tintColor = compositeElevationOverlayIfNeeded(tintColor);
        }
        return new PorterDuffColorFilter(tintColor, tintMode);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        r0 = r1.drawableState.strokeColor;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        r0 = r1.drawableState.fillColor;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r1.drawableState.tintList;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        r0 = r1.drawableState.strokeTintList;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStateful() {
        /*
            r1 = this;
            boolean r0 = super.isStateful()
            if (r0 != 0) goto L_0x0039
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r1.drawableState
            android.content.res.ColorStateList r0 = r0.tintList
            if (r0 == 0) goto L_0x0012
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x0039
        L_0x0012:
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r1.drawableState
            android.content.res.ColorStateList r0 = r0.strokeTintList
            if (r0 == 0) goto L_0x001e
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x0039
        L_0x001e:
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r1.drawableState
            android.content.res.ColorStateList r0 = r0.strokeColor
            if (r0 == 0) goto L_0x002a
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x0039
        L_0x002a:
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r1.drawableState
            android.content.res.ColorStateList r0 = r0.fillColor
            if (r0 == 0) goto L_0x0037
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x0037
            goto L_0x0039
        L_0x0037:
            r0 = 0
            goto L_0x003a
        L_0x0039:
            r0 = 1
        L_0x003a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.shape.MaterialShapeDrawable.isStateful():boolean");
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        boolean invalidateSelf = updateColorsForState(state) || updateTintFilter();
        if (invalidateSelf) {
            invalidateSelf();
        }
        return invalidateSelf;
    }

    private boolean updateColorsForState(int[] state) {
        int previousStrokeColor;
        int newStrokeColor;
        int previousFillColor;
        int newFillColor;
        boolean invalidateSelf = false;
        if (!(this.drawableState.fillColor == null || (previousFillColor = this.fillPaint.getColor()) == (newFillColor = this.drawableState.fillColor.getColorForState(state, previousFillColor)))) {
            this.fillPaint.setColor(newFillColor);
            invalidateSelf = true;
        }
        if (this.drawableState.strokeColor == null || (previousStrokeColor = this.strokePaint.getColor()) == (newStrokeColor = this.drawableState.strokeColor.getColorForState(state, previousStrokeColor))) {
            return invalidateSelf;
        }
        this.strokePaint.setColor(newStrokeColor);
        return true;
    }

    private float getStrokeInsetLength() {
        if (hasStroke()) {
            return this.strokePaint.getStrokeWidth() / 2.0f;
        }
        return 0.0f;
    }

    @NonNull
    private RectF getBoundsInsetByStroke() {
        this.insetRectF.set(getBoundsAsRectF());
        float inset = getStrokeInsetLength();
        this.insetRectF.inset(inset, inset);
        return this.insetRectF;
    }

    public float getTopLeftCornerResolvedSize() {
        return this.drawableState.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public float getTopRightCornerResolvedSize() {
        return this.drawableState.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public float getBottomLeftCornerResolvedSize() {
        return this.drawableState.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public float getBottomRightCornerResolvedSize() {
        return this.drawableState.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(getBoundsAsRectF());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isRoundRect() {
        return this.drawableState.shapeAppearanceModel.isRoundRect(getBoundsAsRectF());
    }

    public static final class MaterialShapeDrawableState extends Drawable.ConstantState {
        public int alpha = 255;
        @Nullable
        public ColorFilter colorFilter;
        public float elevation = 0.0f;
        @Nullable
        public ElevationOverlayProvider elevationOverlayProvider;
        @Nullable
        public ColorStateList fillColor = null;
        public float interpolation = 1.0f;
        @Nullable
        public Rect padding = null;
        public Paint.Style paintStyle = Paint.Style.FILL_AND_STROKE;
        public float parentAbsoluteElevation = 0.0f;
        public float scale = 1.0f;
        public int shadowCompatMode = 0;
        public int shadowCompatOffset = 0;
        public int shadowCompatRadius = 0;
        public int shadowCompatRotation = 0;
        @NonNull
        public ShapeAppearanceModel shapeAppearanceModel;
        @Nullable
        public ColorStateList strokeColor = null;
        @Nullable
        public ColorStateList strokeTintList = null;
        public float strokeWidth;
        @Nullable
        public ColorStateList tintList = null;
        @Nullable
        public PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
        public float translationZ = 0.0f;
        public boolean useTintColorForShadow = false;

        public MaterialShapeDrawableState(ShapeAppearanceModel shapeAppearanceModel2, ElevationOverlayProvider elevationOverlayProvider2) {
            this.shapeAppearanceModel = shapeAppearanceModel2;
            this.elevationOverlayProvider = elevationOverlayProvider2;
        }

        public MaterialShapeDrawableState(@NonNull MaterialShapeDrawableState orig) {
            this.shapeAppearanceModel = orig.shapeAppearanceModel;
            this.elevationOverlayProvider = orig.elevationOverlayProvider;
            this.strokeWidth = orig.strokeWidth;
            this.colorFilter = orig.colorFilter;
            this.fillColor = orig.fillColor;
            this.strokeColor = orig.strokeColor;
            this.tintMode = orig.tintMode;
            this.tintList = orig.tintList;
            this.alpha = orig.alpha;
            this.scale = orig.scale;
            this.shadowCompatOffset = orig.shadowCompatOffset;
            this.shadowCompatMode = orig.shadowCompatMode;
            this.useTintColorForShadow = orig.useTintColorForShadow;
            this.interpolation = orig.interpolation;
            this.parentAbsoluteElevation = orig.parentAbsoluteElevation;
            this.elevation = orig.elevation;
            this.translationZ = orig.translationZ;
            this.shadowCompatRadius = orig.shadowCompatRadius;
            this.shadowCompatRotation = orig.shadowCompatRotation;
            this.strokeTintList = orig.strokeTintList;
            this.paintStyle = orig.paintStyle;
            if (orig.padding != null) {
                this.padding = new Rect(orig.padding);
            }
        }

        @NonNull
        public Drawable newDrawable() {
            MaterialShapeDrawable msd = new MaterialShapeDrawable(this);
            boolean unused = msd.pathDirty = true;
            return msd;
        }

        public int getChangingConfigurations() {
            return 0;
        }
    }
}
