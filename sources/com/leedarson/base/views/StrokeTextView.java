package com.leedarson.base.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.leedarson.base.views.common.LDSTextView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.tutk.IOTC.AVIOCTRLDEFs;

public class StrokeTextView extends LDSTextView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTextView y = null;

    public StrokeTextView(Context context) {
        super(context);
        this.y = new LDSTextView(context);
        d();
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.y = new LDSTextView(context, attrs);
        d();
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.y = new LDSTextView(context, attrs, defStyle);
        d();
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSTREAMCTRL_RESP, new Class[0], Void.TYPE).isSupported) {
            TextPaint tp1 = this.y.getPaint();
            tp1.setStrokeWidth(2.0f);
            tp1.setStyle(Paint.Style.STROKE);
            this.y.setTextColor(-1);
            this.y.setGravity(getGravity());
        }
    }

    public void setTextColor(int color) {
        Object[] objArr = {new Integer(color)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 804, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.setTextColor(color);
            this.y.setTextColor(color);
        }
    }

    public void setTypeface(@Nullable Typeface tf) {
        if (!PatchProxy.proxy(new Object[]{tf}, this, changeQuickRedirect, false, 805, new Class[]{Typeface.class}, Void.TYPE).isSupported) {
            super.setTypeface(tf);
            LDSTextView lDSTextView = this.y;
            if (lDSTextView != null) {
                lDSTextView.setTypeface(tf);
            }
            invalidate();
        }
    }

    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if (!PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETMOTIONDETECT_REQ, new Class[]{ViewGroup.LayoutParams.class}, Void.TYPE).isSupported) {
            super.setLayoutParams(params);
            this.y.setLayoutParams(params);
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETMOTIONDETECT_RESP, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            CharSequence tt = this.y.getText();
            if (tt == null || !tt.equals(getText())) {
                this.y.setText(getText());
                postInvalidate();
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            this.y.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void onLayout(boolean z, int left, int i, int right, int i2) {
        Object[] objArr = {new Byte(z ? (byte) 1 : 0), new Integer(left), new Integer(i), new Integer(right), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Boolean.TYPE, cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 808, clsArr, Void.TYPE).isSupported) {
            boolean changed = z;
            int top = i;
            int bottom = i2;
            super.onLayout(changed, left, top, right, bottom);
            this.y.layout(left, top, right, bottom);
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 809, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            this.y.draw(canvas);
            super.onDraw(canvas);
        }
    }
}
