package com.didichuxing.doraemonkit.kit.viewcheck;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.model.ViewInfo;
import java.util.ArrayList;
import java.util.List;

public class LayoutBorderView extends View {
    private static final String TAG1 = "LayoutBorderView";
    private Paint mRectPaint;
    private List<ViewInfo> mViewInfos;

    public LayoutBorderView(Context context) {
        this(context, (AttributeSet) null);
    }

    public LayoutBorderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LayoutBorderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mViewInfos = new ArrayList();
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LayoutBorderView);
        boolean fill = a.getBoolean(R.styleable.LayoutBorderView_dkFill, false);
        Paint paint = new Paint();
        this.mRectPaint = paint;
        if (fill) {
            paint.setStyle(Paint.Style.FILL);
            this.mRectPaint.setColor(SupportMenu.CATEGORY_MASK);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            this.mRectPaint.setStrokeWidth(4.0f);
            this.mRectPaint.setPathEffect(new DashPathEffect(new float[]{4.0f, 4.0f}, 0.0f));
            this.mRectPaint.setColor(SupportMenu.CATEGORY_MASK);
        }
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        for (ViewInfo viewInfo : this.mViewInfos) {
            if (this.mRectPaint.getStyle() == Paint.Style.FILL) {
                this.mRectPaint.setAlpha(viewInfo.getDrawTimeLevel() * 255);
            }
            canvas.drawRect(viewInfo.viewRect, this.mRectPaint);
        }
    }

    public void showViewLayoutBorder(ViewInfo info) {
        this.mViewInfos.clear();
        if (info != null) {
            this.mViewInfos.add(info);
        }
        invalidate();
    }

    public void showViewLayoutBorder(List<ViewInfo> viewInfos) {
        if (viewInfos != null) {
            this.mViewInfos.clear();
            this.mViewInfos.addAll(viewInfos);
            invalidate();
        }
    }
}
