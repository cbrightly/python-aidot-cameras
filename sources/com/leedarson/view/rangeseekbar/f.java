package com.leedarson.view.rangeseekbar;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.leedarson.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: VerticalSeekBar */
public class f extends c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int T;
    VerticalRangeSeekBar U;

    public f(RangeSeekBar rangeSeekBar, AttributeSet attrs, boolean isLeft) {
        super(rangeSeekBar, attrs, isLeft);
        A(attrs);
        this.U = (VerticalRangeSeekBar) rangeSeekBar;
    }

    private void A(AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{attrs}, this, changeQuickRedirect, false, 12005, new Class[]{AttributeSet.class}, Void.TYPE).isSupported) {
            try {
                TypedArray t = d().obtainStyledAttributes(attrs, R$styleable.VerticalRangeSeekBar);
                this.T = t.getInt(R$styleable.VerticalRangeSeekBar_rsb_indicator_text_orientation, 1);
                t.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void E(Canvas canvas, Paint paint, String text2Draw) {
        Class[] clsArr = {Canvas.class, Paint.class, String.class};
        if (!PatchProxy.proxy(new Object[]{canvas, paint, text2Draw}, this, changeQuickRedirect, false, 12006, clsArr, Void.TYPE).isSupported) {
            if (text2Draw != null) {
                if (this.T == 1) {
                    V(canvas, paint, text2Draw);
                } else {
                    super.E(canvas, paint, text2Draw);
                }
            }
        }
    }

    public void V(Canvas canvas, Paint paint, String str) {
        if (!PatchProxy.proxy(new Object[]{canvas, paint, str}, this, changeQuickRedirect, false, 12007, new Class[]{Canvas.class, Paint.class, String.class}, Void.TYPE).isSupported) {
            Paint paint2 = paint;
            Canvas canvas2 = canvas;
            String text2Draw = str;
            paint2.setTextSize((float) q());
            paint2.setStyle(Paint.Style.FILL);
            paint2.setColor(f());
            paint2.getTextBounds(text2Draw, 0, text2Draw.length(), this.N);
            int realIndicatorWidth = this.N.height() + j() + k();
            if (r() > realIndicatorWidth) {
                realIndicatorWidth = r();
            }
            int realIndicatorHeight = this.N.width() + l() + i();
            if (g() > realIndicatorHeight) {
                realIndicatorHeight = g();
            }
            Rect rect = this.O;
            rect.left = (this.R / 2) - (realIndicatorWidth / 2);
            rect.top = ((this.w - realIndicatorHeight) - this.S) - h();
            Rect rect2 = this.O;
            rect2.right = rect2.left + realIndicatorWidth;
            rect2.bottom = rect2.top + realIndicatorHeight;
            if (this.E == null) {
                int ax = this.R / 2;
                int ay = rect2.bottom;
                int by = ay - e();
                this.M.reset();
                this.M.moveTo((float) ax, (float) ay);
                this.M.lineTo((float) (ax - e()), (float) by);
                this.M.lineTo((float) (e() + ax), (float) by);
                this.M.close();
                canvas2.drawPath(this.M, paint2);
                this.O.bottom -= e();
                this.O.top -= e();
            }
            int defaultPaddingOffset = e.b(d(), 1.0f);
            int leftOffset = (((this.O.width() / 2) - ((int) (((float) this.K.getProgressWidth()) * this.x))) - this.K.getProgressLeft()) + defaultPaddingOffset;
            int rightOffset = (((this.O.width() / 2) - ((int) (((float) this.K.getProgressWidth()) * (1.0f - this.x)))) - this.K.getProgressPaddingRight()) + defaultPaddingOffset;
            if (leftOffset > 0) {
                Rect rect3 = this.O;
                rect3.left += leftOffset;
                rect3.right += leftOffset;
            } else if (rightOffset > 0) {
                Rect rect4 = this.O;
                rect4.left -= rightOffset;
                rect4.right -= rightOffset;
            }
            Bitmap bitmap = this.E;
            if (bitmap != null) {
                e.c(canvas2, paint2, bitmap, this.O);
            } else if (m() > 0.0f) {
                canvas2.drawRoundRect(new RectF(this.O), m(), m(), paint2);
            } else {
                canvas2.drawRect(this.O, paint2);
            }
            Rect rect5 = this.O;
            int tx = ((rect5.left + ((rect5.width() - this.N.width()) / 2)) + j()) - k();
            Rect rect6 = this.O;
            int ty = ((rect6.bottom - ((rect6.height() - this.N.height()) / 2)) + l()) - i();
            paint2.setColor(p());
            int degrees = 0;
            float rotateX = ((float) tx) + (((float) this.N.width()) / 2.0f);
            float rotateY = ((float) ty) - (((float) this.N.height()) / 2.0f);
            if (this.T == 1) {
                if (this.U.getOrientation() == 1) {
                    degrees = 90;
                } else if (this.U.getOrientation() == 2) {
                    degrees = -90;
                }
            }
            if (degrees != 0) {
                canvas2.rotate((float) degrees, rotateX, rotateY);
            }
            canvas2.drawText(text2Draw, (float) tx, (float) ty, paint2);
            if (degrees != 0) {
                canvas2.rotate((float) (-degrees), rotateX, rotateY);
            }
        }
    }
}
