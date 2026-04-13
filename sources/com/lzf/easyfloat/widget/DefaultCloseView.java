package com.lzf.easyfloat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.lzf.easyfloat.R;
import com.lzf.easyfloat.interfaces.OnTouchRangeListener;
import com.lzf.easyfloat.utils.DisplayUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultCloseView.kt */
public final class DefaultCloseView extends BaseSwitchView {
    private float height;
    private boolean inRange;
    private int inRangeColor;
    @Nullable
    private OnTouchRangeListener listener;
    private int normalColor;
    private Paint paint;
    @NotNull
    private Path path;
    @NotNull
    private RectF rectF;
    @NotNull
    private Region region;
    private int shapeType;
    @NotNull
    private final Region totalRegion;
    private float width;
    private float zoomSize;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DefaultCloseView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 6, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DefaultCloseView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    public void _$_clearFindViewByIdCache() {
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DefaultCloseView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DefaultCloseView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        k.e(context, "context");
        this.normalColor = Color.parseColor("#99000000");
        this.inRangeColor = Color.parseColor("#99FF0000");
        this.path = new Path();
        this.rectF = new RectF();
        this.region = new Region();
        this.totalRegion = new Region();
        this.zoomSize = (float) DisplayUtils.INSTANCE.dp2px(context, 4.0f);
        if (attrs != null) {
            initAttrs(attrs);
        }
        initPaint();
        setWillNotDraw(false);
    }

    private final void initAttrs(AttributeSet attrs) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DefaultCloseView, 0, 0);
        TypedArray $this$initAttrs_u24lambda_u2d1 = obtainStyledAttributes;
        this.normalColor = $this$initAttrs_u24lambda_u2d1.getColor(R.styleable.DefaultCloseView_normalColor, this.normalColor);
        this.inRangeColor = $this$initAttrs_u24lambda_u2d1.getColor(R.styleable.DefaultCloseView_inRangeColor, this.inRangeColor);
        this.shapeType = $this$initAttrs_u24lambda_u2d1.getInt(R.styleable.DefaultCloseView_closeShapeType, this.shapeType);
        this.zoomSize = $this$initAttrs_u24lambda_u2d1.getDimension(R.styleable.DefaultCloseView_zoomSize, this.zoomSize);
        obtainStyledAttributes.recycle();
    }

    private final void initPaint() {
        Paint paint2 = new Paint();
        Paint $this$initPaint_u24lambda_u2d2 = paint2;
        $this$initPaint_u24lambda_u2d2.setColor(this.normalColor);
        $this$initPaint_u24lambda_u2d2.setStrokeWidth(10.0f);
        $this$initPaint_u24lambda_u2d2.setStyle(Paint.Style.FILL);
        $this$initPaint_u24lambda_u2d2.setAntiAlias(true);
        x xVar = x.a;
        this.paint = paint2;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = (float) w;
        this.height = (float) h;
    }

    /* access modifiers changed from: protected */
    public void onDraw(@Nullable Canvas canvas) {
        this.path.reset();
        Paint paint2 = null;
        if (this.inRange) {
            Paint paint3 = this.paint;
            if (paint3 == null) {
                k.t("paint");
                paint3 = null;
            }
            paint3.setColor(this.inRangeColor);
            switch (this.shapeType) {
                case 0:
                    this.rectF.set((float) getPaddingLeft(), 0.0f, this.width - ((float) getPaddingRight()), this.height * ((float) 2));
                    this.path.addOval(this.rectF, Path.Direction.CW);
                    break;
                case 1:
                    this.rectF.set((float) getPaddingLeft(), 0.0f, this.width - ((float) getPaddingRight()), this.height);
                    this.path.addRect(this.rectF, Path.Direction.CW);
                    break;
                case 2:
                    Path path2 = this.path;
                    float f = this.width / ((float) 2);
                    float f2 = this.height;
                    path2.addCircle(f, f2, f2, Path.Direction.CW);
                    break;
            }
        } else {
            Paint paint4 = this.paint;
            if (paint4 == null) {
                k.t("paint");
                paint4 = null;
            }
            paint4.setColor(this.normalColor);
            switch (this.shapeType) {
                case 0:
                    RectF rectF2 = this.rectF;
                    float f3 = this.zoomSize;
                    float paddingRight = this.width - ((float) getPaddingRight());
                    float f4 = this.zoomSize;
                    rectF2.set(((float) getPaddingLeft()) + f3, f3, paddingRight - f4, (this.height - f4) * ((float) 2));
                    this.path.addOval(this.rectF, Path.Direction.CW);
                    Region region2 = this.totalRegion;
                    int paddingLeft = getPaddingLeft();
                    float f5 = this.zoomSize;
                    region2.set(paddingLeft + ((int) f5), (int) f5, (int) ((this.width - ((float) getPaddingRight())) - this.zoomSize), (int) this.height);
                    break;
                case 1:
                    this.rectF.set((float) getPaddingLeft(), this.zoomSize, this.width - ((float) getPaddingRight()), this.height);
                    this.path.addRect(this.rectF, Path.Direction.CW);
                    this.totalRegion.set(getPaddingLeft(), (int) this.zoomSize, ((int) this.width) - getPaddingRight(), (int) this.height);
                    break;
                case 2:
                    Path path3 = this.path;
                    float f6 = this.width / ((float) 2);
                    float f7 = this.height;
                    path3.addCircle(f6, f7, f7 - this.zoomSize, Path.Direction.CW);
                    this.totalRegion.set(0, (int) this.zoomSize, (int) this.width, (int) this.height);
                    break;
            }
            this.region.setPath(this.path, this.totalRegion);
        }
        if (canvas != null) {
            Path path4 = this.path;
            Paint paint5 = this.paint;
            if (paint5 == null) {
                k.t("paint");
            } else {
                paint2 = paint5;
            }
            canvas.drawPath(path4, paint2);
        }
        super.onDraw(canvas);
    }

    public void setTouchRangeListener(@NotNull MotionEvent event, @Nullable OnTouchRangeListener listener2) {
        k.e(event, NotificationCompat.CATEGORY_EVENT);
        this.listener = listener2;
        initTouchRange(event);
    }

    private final boolean initTouchRange(MotionEvent event) {
        OnTouchRangeListener onTouchRangeListener;
        int[] location = new int[2];
        getLocationOnScreen(location);
        boolean currentInRange = this.region.contains(((int) event.getRawX()) - location[0], ((int) event.getRawY()) - location[1]);
        if (currentInRange != this.inRange) {
            this.inRange = currentInRange;
            invalidate();
        }
        OnTouchRangeListener onTouchRangeListener2 = this.listener;
        if (onTouchRangeListener2 != null) {
            onTouchRangeListener2.touchInRange(currentInRange, this);
        }
        if (event.getAction() == 1 && currentInRange && (onTouchRangeListener = this.listener) != null) {
            onTouchRangeListener.touchUpInRange();
        }
        return currentInRange;
    }
}
