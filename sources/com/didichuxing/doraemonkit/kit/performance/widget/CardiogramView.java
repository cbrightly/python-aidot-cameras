package com.didichuxing.doraemonkit.kit.performance.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.kit.performance.datasource.IDataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardiogramView extends View implements Runnable {
    private static final int DEFAULT_FRAME_COUNT = 62;
    private static final int DEFAULT_FRAME_DELAY = 32;
    private static final float DEFAULT_ITEM_COUNT = 12.0f;
    private static final float MAX_ITEM_COUNT = 14.0f;
    private int mCurrentFrameCount = 0;
    private IDataSource mDataSource;
    private Handler mHandler = new Handler();
    private float mItemWidth;
    private List<LineData> mList = Collections.synchronizedList(new ArrayList());
    private LineRender mRender;
    private int mTotalFrameCount = 62;

    public CardiogramView(Context context) {
        super(context);
        init(context);
    }

    public CardiogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LineRender lineRender = new LineRender(context);
        this.mRender = lineRender;
        lineRender.setMaxValue(100);
        this.mRender.setMinValue(0);
        this.mRender.setPointSize(5.0f);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        float f = ((float) w) / DEFAULT_ITEM_COUNT;
        this.mItemWidth = f;
        this.mRender.measure(f, (float) h);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getCanvasTranslate(), 0.0f);
        drawLine(canvas);
        checkFirstItemBound();
        canvas.restore();
    }

    private void checkFirstItemBound() {
        int i = this.mCurrentFrameCount + 1;
        this.mCurrentFrameCount = i;
        if (i >= this.mTotalFrameCount) {
            this.mCurrentFrameCount = 0;
            IDataSource iDataSource = this.mDataSource;
            if (iDataSource != null) {
                this.mList.add(iDataSource.createData());
            }
            if (((float) this.mList.size()) > MAX_ITEM_COUNT) {
                this.mList.remove(0).release();
            }
        }
    }

    private float getCanvasTranslate() {
        float f = this.mItemWidth;
        return ((-f) * (((float) this.mCurrentFrameCount) / ((float) this.mTotalFrameCount))) + (f * (MAX_ITEM_COUNT - ((float) this.mList.size())));
    }

    private void drawLine(Canvas canvas) {
        for (int index = 0; ((float) index) < Math.min((float) this.mList.size(), 13.0f); index++) {
            this.mRender.setCurrentValue(index, this.mList.get(index).value);
            if (index == this.mList.size() - 2) {
                this.mRender.setShowLabel(true);
                this.mRender.setLabelAlpha(1.0f);
                this.mRender.setLabel(this.mList.get(index).label);
            } else if (index == this.mList.size() - 3) {
                this.mRender.setLabel(this.mList.get(index).label);
                this.mRender.setLabelAlpha(1.0f - (((float) this.mCurrentFrameCount) / ((float) this.mTotalFrameCount)));
                this.mRender.setShowLabel(true);
            } else {
                this.mRender.setLabel(this.mList.get(index).label);
                this.mRender.setShowLabel(false);
            }
            if (index == this.mList.size() - 1) {
                this.mRender.setNextValue(0.0f);
                this.mRender.setDrawRightLine(false);
            } else {
                this.mRender.setDrawRightLine(true);
                this.mRender.setNextValue(this.mList.get(index + 1).value);
            }
            this.mRender.draw(canvas);
        }
    }

    public void startMove() {
        this.mHandler.removeCallbacks(this);
        this.mHandler.post(this);
    }

    public void stopMove() {
        this.mHandler.removeCallbacks(this);
    }

    public void setInterval(int milliSecond) {
        this.mTotalFrameCount = milliSecond / 32;
    }

    public void setDataSource(@NonNull IDataSource dataSource) {
        this.mDataSource = dataSource;
        this.mList.clear();
        this.mList.add(dataSource.createData());
    }

    public void run() {
        invalidate();
        this.mHandler.postDelayed(this, 32);
    }
}
