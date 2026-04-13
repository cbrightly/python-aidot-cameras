package com.leedarson.view.rangeseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.leedarson.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class VerticalRangeSeekBar extends RangeSeekBar {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int m5;
    private int n5;
    private int o5;

    public VerticalRangeSeekBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public VerticalRangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m5 = 1;
        this.n5 = 1;
        e(attrs);
        h(attrs);
    }

    private void e(AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{attrs}, this, changeQuickRedirect, false, 11992, new Class[]{AttributeSet.class}, Void.TYPE).isSupported) {
            try {
                TypedArray t = getContext().obtainStyledAttributes(attrs, R$styleable.VerticalRangeSeekBar);
                this.m5 = t.getInt(R$styleable.VerticalRangeSeekBar_rsb_orientation, 1);
                this.n5 = t.getInt(R$styleable.VerticalRangeSeekBar_rsb_tick_mark_orientation, 1);
                t.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void h(AttributeSet attrs) {
        boolean z = true;
        if (!PatchProxy.proxy(new Object[]{attrs}, this, changeQuickRedirect, false, 11993, new Class[]{AttributeSet.class}, Void.TYPE).isSupported) {
            this.d5 = new f(this, attrs, true);
            f fVar = new f(this, attrs, false);
            this.e5 = fVar;
            if (getSeekBarMode() == 1) {
                z = false;
            }
            fVar.T(z);
        }
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        Object[] objArr = {new Integer(w), new Integer(h), new Integer(oldw), new Integer(oldh)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11994, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
            super.onSizeChanged(h, w, oldh, oldw);
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize;
        int heightNeeded;
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11995, clsArr, Void.TYPE).isSupported) {
            int widthSize2 = View.MeasureSpec.getSize(widthMeasureSpec);
            int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
            if (widthMode == 1073741824) {
                widthSize = View.MeasureSpec.makeMeasureSpec(widthSize2, 1073741824);
            } else if (widthMode == Integer.MIN_VALUE && (getParent() instanceof ViewGroup) && widthSize2 == -1) {
                widthSize = View.MeasureSpec.makeMeasureSpec(((ViewGroup) getParent()).getMeasuredHeight(), Integer.MIN_VALUE);
            } else {
                if (getGravity() == 2) {
                    heightNeeded = (getProgressTop() * 2) + getProgressHeight();
                } else {
                    heightNeeded = (int) getRawHeight();
                }
                widthSize = View.MeasureSpec.makeMeasureSpec(heightNeeded, 1073741824);
            }
            super.onMeasure(widthSize, heightMeasureSpec);
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11996, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            if (this.m5 == 1) {
                canvas.rotate(-90.0f);
                canvas.translate((float) (-getHeight()), 0.0f);
            } else {
                canvas.rotate(90.0f);
                canvas.translate(0.0f, (float) (-getWidth()));
            }
            super.onDraw(canvas);
        }
    }

    public void m(Canvas canvas, Paint paint) {
        float num;
        float y;
        if (!PatchProxy.proxy(new Object[]{canvas, paint}, this, changeQuickRedirect, false, 11997, new Class[]{Canvas.class, Paint.class}, Void.TYPE).isSupported) {
            Paint paint2 = paint;
            Canvas canvas2 = canvas;
            if (getTickMarkTextArray() != null) {
                int arrayLength = getTickMarkTextArray().length;
                int trickPartWidth = getProgressWidth() / (arrayLength - 1);
                for (int i = 0; i < arrayLength; i++) {
                    String text2Draw = getTickMarkTextArray()[i].toString();
                    if (!TextUtils.isEmpty(text2Draw)) {
                        paint2.getTextBounds(text2Draw, 0, text2Draw.length(), this.c5);
                        paint2.setColor(getTickMarkTextColor());
                        if (getTickMarkMode() != 1) {
                            float num2 = e.h(text2Draw);
                            d[] states = getRangeSeekBarState();
                            if (!(e.a(num2, states[0].c) == -1 || e.a(num2, states[1].c) == 1 || getSeekBarMode() != 2)) {
                                paint2.setColor(getTickMarkInRangeTextColor());
                            }
                            num = (((float) getProgressLeft()) + ((((float) getProgressWidth()) * (num2 - getMinProgress())) / (getMaxProgress() - getMinProgress()))) - (((float) this.c5.width()) / 2.0f);
                        } else if (getTickMarkGravity() == 2) {
                            num = (float) ((getProgressLeft() + (i * trickPartWidth)) - this.c5.width());
                        } else if (getTickMarkGravity() == 1) {
                            num = ((float) (getProgressLeft() + (i * trickPartWidth))) - (((float) this.c5.width()) / 2.0f);
                        } else {
                            num = (float) (getProgressLeft() + (i * trickPartWidth));
                        }
                        if (getTickMarkLayoutGravity() == 0) {
                            y = (float) (getProgressTop() - getTickMarkTextMargin());
                        } else {
                            y = (float) (getProgressBottom() + getTickMarkTextMargin() + this.c5.height());
                        }
                        int degrees = 0;
                        float rotateX = (((float) this.c5.width()) / 2.0f) + num;
                        float rotateY = y - (((float) this.c5.height()) / 2.0f);
                        if (this.n5 == 1) {
                            int i2 = this.m5;
                            if (i2 == 1) {
                                degrees = 90;
                            } else if (i2 == 2) {
                                degrees = -90;
                            }
                        }
                        if (degrees != 0) {
                            canvas2.rotate((float) degrees, rotateX, rotateY);
                        }
                        canvas2.drawText(text2Draw, num, y, paint2);
                        if (degrees != 0) {
                            canvas2.rotate((float) (-degrees), rotateX, rotateY);
                        }
                    }
                }
            }
        }
    }

    public int getTickMarkRawHeight() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11998, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.o5 > 0) {
            return getTickMarkTextMargin() + this.o5;
        }
        if (getTickMarkTextArray() == null || getTickMarkTextArray().length <= 0) {
            return 0;
        }
        int arrayLength = getTickMarkTextArray().length;
        this.o5 = e.g(String.valueOf(getTickMarkTextArray()[0]), (float) getTickMarkTextSize()).width();
        for (int i = 1; i < arrayLength; i++) {
            int width = e.g(String.valueOf(getTickMarkTextArray()[i]), (float) getTickMarkTextSize()).width();
            if (this.o5 < width) {
                this.o5 = width;
            }
        }
        return getTickMarkTextMargin() + this.o5;
    }

    public void setTickMarkTextSize(int tickMarkTextSize) {
        if (!PatchProxy.proxy(new Object[]{new Integer(tickMarkTextSize)}, this, changeQuickRedirect, false, 11999, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.setTickMarkTextSize(tickMarkTextSize);
            this.o5 = 0;
        }
    }

    public void setTickMarkTextArray(CharSequence[] tickMarkTextArray) {
        if (!PatchProxy.proxy(new Object[]{tickMarkTextArray}, this, changeQuickRedirect, false, 12000, new Class[]{CharSequence[].class}, Void.TYPE).isSupported) {
            super.setTickMarkTextArray(tickMarkTextArray);
            this.o5 = 0;
        }
    }

    public float c(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 12001, new Class[]{MotionEvent.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        if (this.m5 == 1) {
            return ((float) getHeight()) - event.getY();
        }
        return event.getY();
    }

    public float d(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 12002, new Class[]{MotionEvent.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        if (this.m5 == 1) {
            return event.getX();
        }
        return (-event.getX()) + ((float) getWidth());
    }

    public f getLeftSeekBar() {
        return (f) this.d5;
    }

    public f getRightSeekBar() {
        return (f) this.e5;
    }

    public int getOrientation() {
        return this.m5;
    }

    public void setOrientation(int orientation) {
        this.m5 = orientation;
    }

    public int getTickMarkDirection() {
        return this.n5;
    }

    public void setTickMarkDirection(int tickMarkDirection) {
        this.n5 = tickMarkDirection;
    }
}
