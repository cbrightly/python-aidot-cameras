package com.leedarson.newui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$styleable;
import com.leedarson.newui.view.PTZCtrlView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class GradientPTZCtrlView extends ConstraintLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public b a1;
    private int a2;
    private int c = 20;
    private int d;
    private int f = 5;
    /* access modifiers changed from: private */
    public ImageView p0;
    private int p1;
    private int p2;
    private PTZCtrlView q;
    /* access modifiers changed from: private */
    public ImageView x;
    /* access modifiers changed from: private */
    public ImageView y;
    /* access modifiers changed from: private */
    public ImageView z;

    public interface b {
        void a();

        void b(int i);

        void c(int i);
    }

    public GradientPTZCtrlView(Context context) {
        super(context);
    }

    public GradientPTZCtrlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        g(context, attrs);
    }

    private void g(Context context, AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{context, attrs}, this, changeQuickRedirect, false, 4960, new Class[]{Context.class, AttributeSet.class}, Void.TYPE).isSupported) {
            Context context2 = context;
            LayoutInflater.from(context2).inflate(R$layout.layout_gradient_ptz, this, true);
            TypedArray typedArray = context2.obtainStyledAttributes(attrs, R$styleable.GradientPTZCtrlView);
            this.d = typedArray.getDimensionPixelSize(R$styleable.GradientPTZCtrlView_gptz_bmp_size, 48);
            this.c = typedArray.getDimensionPixelSize(R$styleable.GradientPTZCtrlView_gptz_bmp_margin, 20);
            this.f = typedArray.getDimensionPixelSize(R$styleable.GradientPTZCtrlView_gptz_stroke_width, 5);
            this.p1 = typedArray.getColor(R$styleable.GradientPTZCtrlView_strokeColor, getResources().getColor(R$color.text_assist_color60));
            this.a2 = typedArray.getColor(R$styleable.GradientPTZCtrlView_iconColor, getResources().getColor(R$color.text_second_color));
            this.p2 = typedArray.getColor(R$styleable.GradientPTZCtrlView_bgColor, getResources().getColor(R$color.white100));
            typedArray.recycle();
            this.q = (PTZCtrlView) findViewById(R$id.ptz_view);
            this.x = (ImageView) findViewById(R$id.img_up);
            this.y = (ImageView) findViewById(R$id.img_down);
            this.z = (ImageView) findViewById(R$id.img_left);
            this.p0 = (ImageView) findViewById(R$id.img_right);
            GradientDrawable grad = new GradientDrawable();
            grad.setColor(this.p2);
            grad.setShape(1);
            setBackground(grad);
            this.q.g(this.d, this.c, this.f, this.p1, this.a2, this.p2);
            this.q.setOnDirectTapListener(new a());
        }
    }

    public class a implements PTZCtrlView.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void c(int direction) {
            if (!PatchProxy.proxy(new Object[]{new Integer(direction)}, this, changeQuickRedirect, false, 4964, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                switch (direction) {
                    case 0:
                        GradientPTZCtrlView.this.z.setVisibility(0);
                        break;
                    case 1:
                        GradientPTZCtrlView.this.p0.setVisibility(0);
                        break;
                    case 2:
                        GradientPTZCtrlView.this.x.setVisibility(0);
                        break;
                    case 3:
                        GradientPTZCtrlView.this.y.setVisibility(0);
                        break;
                }
                if (GradientPTZCtrlView.this.a1 != null) {
                    GradientPTZCtrlView.this.a1.c(direction);
                }
            }
        }

        public void b(int direction) {
            Object[] objArr = {new Integer(direction)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4965, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (GradientPTZCtrlView.this.a1 != null) {
                    GradientPTZCtrlView.this.a1.b(direction);
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4966, new Class[0], Void.TYPE).isSupported) {
                if (GradientPTZCtrlView.this.x.getVisibility() == 0) {
                    GradientPTZCtrlView.this.x.setVisibility(8);
                }
                if (GradientPTZCtrlView.this.y.getVisibility() == 0) {
                    GradientPTZCtrlView.this.y.setVisibility(8);
                }
                if (GradientPTZCtrlView.this.z.getVisibility() == 0) {
                    GradientPTZCtrlView.this.z.setVisibility(8);
                }
                if (GradientPTZCtrlView.this.p0.getVisibility() == 0) {
                    GradientPTZCtrlView.this.p0.setVisibility(8);
                }
                if (GradientPTZCtrlView.this.a1 != null) {
                    GradientPTZCtrlView.this.a1.a();
                }
            }
        }
    }

    public void setIntervalMills(int spaceMills) {
        Object[] objArr = {new Integer(spaceMills)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4961, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.q.setIntervalMills(spaceMills);
        }
    }

    public void setDirection(int func) {
        Object[] objArr = {new Integer(func)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4962, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.q.setDirection(func);
        }
    }

    public void setOnDirectTapListener(b onDirectTapListener) {
        this.a1 = onDirectTapListener;
    }

    public void setEnable(boolean enable) {
        if (!PatchProxy.proxy(new Object[]{new Byte(enable ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4963, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (enable) {
                this.q.setEnabled(true);
                this.q.setAlpha(1.0f);
                return;
            }
            this.q.setEnabled(false);
            this.q.setAlpha(0.4f);
        }
    }
}
