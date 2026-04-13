package com.leedarson.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class FloatPlayerMapWindow extends LinearLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private float a1;
    private int a2;
    private View c;
    private float d;
    private float f;
    private float p0;
    private float p1;
    private int p2;
    private int p3;
    private int p4;
    private float q;
    private float x;
    private float y;
    private float z;
    Runnable z4;

    static /* synthetic */ void a(FloatPlayerMapWindow x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11662, new Class[]{FloatPlayerMapWindow.class}, Void.TYPE).isSupported) {
            x0.d();
        }
    }

    public FloatPlayerMapWindow(Context context) {
        this(context, (AttributeSet) null);
    }

    public FloatPlayerMapWindow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatPlayerMapWindow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.d = 1.0f;
        this.z4 = new a();
        e(context, attrs);
    }

    public void e(Context context, AttributeSet attributeSet) {
        Class[] clsArr = {Context.class, AttributeSet.class};
        if (!PatchProxy.proxy(new Object[]{context, attributeSet}, this, changeQuickRedirect, false, 11653, clsArr, Void.TYPE).isSupported) {
            View view = LayoutInflater.from(getContext()).inflate(R$layout.float_player_map_view, (ViewGroup) null);
            addView(view);
            this.c = view.findViewById(R$id.rectView);
        }
    }

    public void setScale(float playerScale) {
        if (!PatchProxy.proxy(new Object[]{new Float(playerScale)}, this, changeQuickRedirect, false, 11654, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            float f2 = this.p1;
            if (f2 > playerScale) {
                this.d *= f2 / playerScale;
            } else {
                this.d = 1.0f / playerScale;
            }
            this.p1 = playerScale;
            this.c.setScaleX(this.d);
            this.c.setScaleY(this.d);
        }
    }

    public void h(float dx, float dy) {
        Object[] objArr = {new Float(dx), new Float(dy)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11655, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (this.d < 1.0f) {
                float realDx = (Math.abs(dx) / this.q) * this.d;
                float realDy = (Math.abs(dy) / this.x) * this.d;
                float dx2 = dx < 0.0f ? realDx : -realDx;
                float dy2 = dy < 0.0f ? realDy : -realDy;
                this.y += dx2;
                this.z += dy2;
                this.p0 = (((float) getHeight()) * (1.0f - this.d)) / 2.0f;
                float f2 = this.d;
                float width = (((float) getWidth()) * (1.0f - f2)) / 2.0f;
                this.a1 = width;
                if (f2 >= 1.0f) {
                    this.y = 0.0f;
                    this.z = 0.0f;
                    return;
                }
                float f3 = this.y;
                if (f3 > 0.0f || width > Math.abs(f3)) {
                    float f4 = this.y;
                    if (f4 > 0.0f && this.a1 <= Math.abs(f4)) {
                        this.y = this.a1;
                    }
                } else {
                    this.y = -this.a1;
                }
                float f5 = this.z;
                if (f5 > 0.0f || this.p0 > Math.abs(f5)) {
                    float f6 = this.z;
                    if (f6 > 0.0f && this.p0 <= Math.abs(f6)) {
                        this.z = this.p0;
                    }
                } else {
                    this.z = -this.p0;
                }
                float f7 = this.a1;
                int i = this.p3;
                if (f7 >= ((float) i)) {
                    float maxTX = f7 - ((float) i);
                    if (Math.abs(this.y) > maxTX) {
                        if (this.y < 0.0f) {
                            this.y = -maxTX;
                        } else {
                            this.y = maxTX;
                        }
                    }
                    this.c.setTranslationX(this.y);
                }
                float maxTX2 = this.p0;
                int i2 = this.p4;
                if (maxTX2 >= ((float) i2)) {
                    float maxTY = maxTX2 - ((float) i2);
                    if (Math.abs(this.z) > maxTY) {
                        if (this.z < 0.0f) {
                            this.z = -maxTY;
                        } else {
                            this.z = maxTY;
                        }
                    }
                    this.c.setTranslationY(this.z);
                }
            }
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11656, new Class[0], Void.TYPE).isSupported) {
            Log.i("zqrbac", "重置translation");
            this.y = 0.0f;
            this.z = 0.0f;
            this.c.setTranslationX(0.0f);
            this.c.setTranslationY(this.z);
        }
    }

    public int b(Context context, float dpValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Float(dpValue)}, this, changeQuickRedirect, false, 11657, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void g(int viewWidth, int viewHeight, int playergapW, int playergapH) {
        Object[] objArr = {new Integer(viewWidth), new Integer(viewHeight), new Integer(playergapW), new Integer(playergapH)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11658, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
            if (this.a2 != viewWidth || this.p2 != viewHeight || this.p3 != playergapW || this.p4 != playergapH) {
                this.f = ((float) viewWidth) / ((float) viewHeight);
                this.a2 = viewWidth;
                this.p2 = viewHeight;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.c.getLayoutParams();
                int b = b(getContext(), 32.0f);
                params.height = b;
                params.width = (int) (this.f * ((float) b));
                this.c.setLayoutParams(params);
                float f2 = ((float) viewWidth) / ((float) params.width);
                this.q = f2;
                float f3 = ((float) viewHeight) / ((float) params.height);
                this.x = f3;
                this.p3 = (int) (((float) playergapW) / f2);
                this.p4 = (int) (((float) playergapH) / f3);
            }
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11663, new Class[0], Void.TYPE).isSupported) {
                FloatPlayerMapWindow.a(FloatPlayerMapWindow.this);
            }
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11659, new Class[0], Void.TYPE).isSupported) {
            postDelayed(this.z4, 1000);
        }
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11660, new Class[0], Void.TYPE).isSupported) {
            if (getVisibility() != 0) {
                Runnable runnable = this.z4;
                if (runnable != null) {
                    removeCallbacks(runnable);
                }
                setVisibility(0);
            }
        }
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11661, new Class[0], Void.TYPE).isSupported) {
            if (getVisibility() == 0) {
                setVisibility(8);
            }
        }
    }
}
