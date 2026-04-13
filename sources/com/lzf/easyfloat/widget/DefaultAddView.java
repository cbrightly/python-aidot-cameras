package com.lzf.easyfloat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.lzf.easyfloat.interfaces.OnTouchRangeListener;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultAddView.kt */
public final class DefaultAddView extends BaseSwitchView {
    private float height;
    private boolean inRange;
    @Nullable
    private OnTouchRangeListener listener;
    private Paint paint;
    @NotNull
    private Path path;
    @NotNull
    private Region region;
    @NotNull
    private final Region totalRegion;
    private float width;
    private float zoomSize;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DefaultAddView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 6, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DefaultAddView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    public void _$_clearFindViewByIdCache() {
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DefaultAddView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DefaultAddView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        k.e(context, "context");
        this.path = new Path();
        this.region = new Region();
        this.totalRegion = new Region();
        this.zoomSize = 18.0f;
        initPath();
        setWillNotDraw(false);
    }

    private final void initPath() {
        Paint paint2 = new Paint();
        Paint $this$initPath_u24lambda_u2d0 = paint2;
        $this$initPath_u24lambda_u2d0.setColor(Color.parseColor("#AA000000"));
        $this$initPath_u24lambda_u2d0.setStrokeWidth(10.0f);
        $this$initPath_u24lambda_u2d0.setStyle(Paint.Style.FILL);
        $this$initPath_u24lambda_u2d0.setAntiAlias(true);
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
        if (this.inRange) {
            Path path2 = this.path;
            float f = this.width;
            float f2 = this.height;
            path2.addCircle(f, f2, Math.min(f, f2), Path.Direction.CW);
        } else {
            Path path3 = this.path;
            float f3 = this.width;
            float f4 = this.height;
            path3.addCircle(f3, f4, Math.min(f3, f4) - this.zoomSize, Path.Direction.CW);
            Region region2 = this.totalRegion;
            float f5 = this.zoomSize;
            region2.set((int) f5, (int) f5, (int) this.width, (int) this.height);
            this.region.setPath(this.path, this.totalRegion);
        }
        if (canvas != null) {
            Path path4 = this.path;
            Paint paint2 = this.paint;
            if (paint2 == null) {
                k.t("paint");
                paint2 = null;
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
