package com.leedarson.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.leedarson.utils.o;
import com.leedarson.view.LDSBaseIpcTexureRenderView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.DecimalFormat;
import timber.log.a;

public class IpcTextureView extends LDSBaseIpcTexureRenderView implements View.OnTouchListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    static DecimalFormat d = new DecimalFormat("0.0");
    /* access modifiers changed from: private */
    public static long f = 0;
    /* access modifiers changed from: private */
    public static float q = 0.0f;
    /* access modifiers changed from: private */
    public static float x = 0.0f;
    private int A4 = 0;
    private int B4 = 0;
    private int C4 = 0;
    private float D4 = 0.0f;
    private float E4 = 0.0f;
    private float F4;
    private float G4;
    private float H4 = 0.0f;
    private float I4 = 0.0f;
    private LDSBaseIpcTexureRenderView.b J4;
    private Paint K4;
    private float L4;
    private long M4 = 0;
    private boolean N4 = false;
    private long O4 = 0;
    private float P4 = 8.0f;
    private FloatPlayerMapWindow Q4;
    boolean R4 = true;
    float S4 = 1.0f;
    /* access modifiers changed from: private */
    public LDSBaseIpcTexureRenderView.a T4;
    /* access modifiers changed from: private */
    public boolean U4 = false;
    float V4;
    float W4;
    float X4;
    float Y4;
    private LDSBaseIpcTexureRenderView.b Z4 = new a();
    private float a1;
    private float a2 = 1.0f;
    private float p0;
    private float p1;
    private View p2;
    private int p3 = 0;
    private int p4 = 0;
    private int y = 0;
    private float z;
    private int z4 = 0;

    public IpcTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        p();
    }

    public IpcTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p();
    }

    public IpcTextureView(Context context) {
        super(context);
        p();
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11688, new Class[0], Void.TYPE).isSupported) {
            setFocusable(true);
            setClickable(true);
            setLongClickable(true);
            setOnTouchListener(this);
            this.p2 = this;
            this.K4 = new Paint();
        }
    }

    public void m(int x2, int y2) {
        Object[] objArr = {new Integer(x2), new Integer(y2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11689, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            v((float) x2, (float) y2, true);
            FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
            if (floatPlayerMapWindow != null) {
                floatPlayerMapWindow.h((float) x2, (float) y2);
            }
        }
    }

    public void setMaxScale(float maxScale) {
        this.P4 = maxScale;
    }

    public void setMode(int mode) {
        this.y = mode;
    }

    private void s(MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11690, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            int i = this.y;
            if (i == 1) {
                this.D4 = event.getRawX() - this.a1;
                float rawY = event.getRawY() - this.p1;
                this.E4 = rawY;
                v(this.D4, rawY, true);
                this.Q4.h(this.D4, this.E4);
                this.a1 = (float) ((int) event.getRawX());
                this.p1 = (float) ((int) event.getRawY());
            } else if (i == 2) {
                this.Q4.i();
                float endDis = l(event);
                timber.log.a.c("-----IpcSurfaceView----------endDis------endDis:" + endDis + " startDis:" + this.L4, new Object[0]);
                if (Math.abs(endDis - this.L4) > 20.0f) {
                    float ratio = endDis / this.L4;
                    timber.log.a.c("-----IpcSurfaceView----------Scale------ratio:" + ratio + " preRatio:" + this.a2, new Object[0]);
                    if (!o(this.a2).equals(o(ratio)) && ratio != 1.0f) {
                        t(this.p2, ratio, true);
                    }
                    this.a2 = ratio;
                }
            }
        }
    }

    private void q(MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11691, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            if (event.getPointerCount() == 2) {
                this.y = 2;
                this.L4 = l(event);
                long currentTime = System.currentTimeMillis();
                if (currentTime - this.M4 < 600) {
                    this.M4 = 0;
                    this.O4 = currentTime;
                    this.N4 = true;
                    return;
                }
                this.M4 = currentTime;
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11692, new Class[0], Void.TYPE).isSupported) {
            v(-this.H4, -this.I4, false);
            t(this, 0.0f, false);
            FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
            if (floatPlayerMapWindow != null) {
                floatPlayerMapWindow.c();
            }
        }
    }

    private void r(MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11693, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            this.y = 1;
            this.z = (float) ((int) event.getRawX());
            this.p0 = (float) ((int) event.getRawY());
            this.a1 = (float) ((int) event.getRawX());
            this.p1 = (float) ((int) event.getRawY());
            this.p3 = getWidth();
            this.p4 = getHeight();
            try {
                View view = (View) getParent().getParent();
                this.z4 = view.getWidth();
                int height = view.getHeight();
                this.A4 = height;
                this.B4 = (this.z4 - this.p3) / 2;
                this.C4 = (height - this.p4) / 2;
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.Q4.g(this.p3, this.p4, this.B4, this.C4);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11694, new Class[]{MotionEvent.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : super.dispatchTouchEvent(event);
    }

    public boolean onTouch(View view, MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 11695, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.c) {
            switch (event.getAction() & 255) {
                case 0:
                    r(event);
                    break;
                case 1:
                    this.y = 0;
                    long upTime = System.currentTimeMillis();
                    if (this.N4 && upTime - this.O4 < 200) {
                        if (((double) this.S4) == 1.0d) {
                            t(this, this.P4, true);
                        } else {
                            b();
                        }
                    }
                    this.L4 = 0.0f;
                    this.N4 = false;
                    this.O4 = 0;
                    this.Q4.c();
                    break;
                case 2:
                    s(event);
                    break;
                case 5:
                    timber.log.a.c("-----IpcSurfaceView-----onTouchEvent-----event.getPointerCount():" + event.getPointerCount(), new Object[0]);
                    q(event);
                    break;
                case 6:
                    this.y = 0;
                    break;
            }
        }
        LDSBaseIpcTexureRenderView.b bVar = this.J4;
        if (bVar != null) {
            return bVar.a(this.S4, event);
        }
        return true;
    }

    public void setHasScale(boolean hasScale) {
        Object[] objArr = {new Byte(hasScale ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11697, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.c = hasScale;
            if (!hasScale) {
                b();
            }
        }
    }

    public void setOnTouchListener(LDSBaseIpcTexureRenderView.b touchListener) {
        this.J4 = touchListener;
    }

    public void t(View v, float s, boolean showFloatW) {
        Object[] objArr = {v, new Float(s), new Byte(showFloatW ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11698, new Class[]{View.class, Float.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            float f2 = this.S4 * s;
            this.S4 = f2;
            this.F4 = (((float) this.p4) * (f2 - 1.0f)) / 2.0f;
            this.G4 = (((float) this.p3) * (f2 - 1.0f)) / 2.0f;
            float f3 = this.P4;
            if (f2 >= f3) {
                this.S4 = f3;
            }
            if (this.S4 <= 1.0f) {
                this.S4 = 1.0f;
            }
            timber.log.a.e("IpcSurfaceView---getPivot----viewHeight:" + this.p4 + " viewWidth:" + this.p3, new Object[0]);
            if (s < 1.0f && !(this.F4 == 0.0f && this.G4 == 0.0f)) {
                v(-this.H4, -this.I4, showFloatW);
                this.Q4.f();
            }
            v.setScaleX(this.S4);
            v.setScaleY(this.S4);
            timber.log.a.c("setScale:" + this.S4, new Object[0]);
            this.Q4.setScale(this.S4);
        }
    }

    private void v(float dx, float dy, boolean showFloatW) {
        Object[] objArr = {new Float(dx), new Float(dy), new Byte(showFloatW ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11699, new Class[]{cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            float f2 = this.H4 + dx;
            this.H4 = f2;
            this.I4 += dy;
            float f3 = this.S4;
            this.F4 = (((float) this.p4) * (f3 - 1.0f)) / 2.0f;
            float f4 = (((float) this.p3) * (f3 - 1.0f)) / 2.0f;
            this.G4 = f4;
            if (f3 <= 1.0f) {
                this.H4 = 0.0f;
                this.I4 = 0.0f;
                return;
            }
            if (f2 > 0.0f || f4 > Math.abs(f2)) {
                float f5 = this.H4;
                if (f5 > 0.0f && this.G4 - this.S4 <= Math.abs(f5)) {
                    this.H4 = this.G4 - this.S4;
                }
            } else {
                this.H4 = -this.G4;
            }
            float f6 = this.I4;
            if (f6 > 0.0f || this.F4 > Math.abs(f6)) {
                float f7 = this.I4;
                if (f7 > 0.0f && this.F4 <= Math.abs(f7)) {
                    this.I4 = this.F4;
                }
            } else {
                this.I4 = -this.F4;
            }
            setTranslationX(this.H4);
            setTranslationY(this.I4);
            FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
            if (floatPlayerMapWindow != null && showFloatW) {
                floatPlayerMapWindow.i();
            }
        }
    }

    private float l(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11700, new Class[]{MotionEvent.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        try {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            return (float) Math.sqrt((double) ((dx * dx) + (dy * dy)));
        } catch (Exception e) {
            return 0.0f;
        }
    }

    private String o(float num) {
        Object[] objArr = {new Float(num)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 11701, new Class[]{Float.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return d.format((double) num);
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11702, new Class[0], Void.TYPE).isSupported) {
            setOnTouchListener(this.Z4);
        }
    }

    public void setDefaultTouchEvent(LDSBaseIpcTexureRenderView.a handler) {
        this.T4 = handler;
    }

    public class a implements LDSBaseIpcTexureRenderView.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11705, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            switch (event.getAction() & 255) {
                case 0:
                    boolean unused = IpcTextureView.this.U4 = false;
                    IpcTextureView.this.V4 = event.getX();
                    IpcTextureView.this.W4 = event.getY();
                    a.b g = timber.log.a.g("IpcTextureView");
                    g.h("1onTouchEvent-ACTION_DOWN" + event.getPointerCount(), new Object[0]);
                    long unused2 = IpcTextureView.f = System.currentTimeMillis();
                    break;
                case 1:
                    long moveTime = System.currentTimeMillis() - IpcTextureView.f;
                    a.b g2 = timber.log.a.g("IpcTextureView");
                    g2.h("1onTouchEvent-ACTION_UP" + moveTime + "==x:" + IpcTextureView.q + " y:" + IpcTextureView.x, new Object[0]);
                    if (((IpcTextureView.q < 20.0f && IpcTextureView.x < 20.0f) || scale != 1.0f) && moveTime <= 200 && IpcTextureView.q < 20.0f && IpcTextureView.x < 20.0f && !IpcTextureView.this.U4 && IpcTextureView.this.T4 != null) {
                        IpcTextureView.this.T4.a();
                    }
                    IpcTextureView ipcTextureView = IpcTextureView.this;
                    ipcTextureView.X4 = 0.0f;
                    ipcTextureView.Y4 = 0.0f;
                    float unused3 = IpcTextureView.q = 0.0f;
                    float unused4 = IpcTextureView.x = 0.0f;
                    break;
                case 2:
                    IpcTextureView.this.X4 = event.getX();
                    IpcTextureView.this.Y4 = event.getY();
                    IpcTextureView ipcTextureView2 = IpcTextureView.this;
                    float unused5 = IpcTextureView.q = Math.abs(ipcTextureView2.X4 - ipcTextureView2.V4);
                    IpcTextureView ipcTextureView3 = IpcTextureView.this;
                    float unused6 = IpcTextureView.x = Math.abs(ipcTextureView3.Y4 - ipcTextureView3.W4);
                    break;
                case 5:
                    boolean unused7 = IpcTextureView.this.U4 = true;
                    a.b g3 = timber.log.a.g("IpcTextureView");
                    g3.h("1onTouchEvent-ACTION_POINTER_DOWN" + event.getPointerCount(), new Object[0]);
                    int pointerCount = event.getPointerCount();
                    break;
            }
            return false;
        }
    }

    public void setFloatPlayerMapWindow(FloatPlayerMapWindow floatPlayerMapWindow) {
        this.Q4 = floatPlayerMapWindow;
    }

    public void n(o.b bVar) {
        if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 11703, new Class[]{o.b.class}, Void.TYPE).isSupported) {
            o.b animationEnd = bVar;
            if (this.y == 0) {
                float oldScale = this.S4;
                float oldTransX = this.H4;
                float oldTransY = this.I4;
                t(this, 0.0f, false);
                o.b(this, 500, oldScale, 1.0f, oldTransX, 0.0f, oldTransY, 0.0f, animationEnd);
            }
        }
    }

    public void u(int i, int i2, int i3, float f2, float f3, float f4, o.b bVar) {
        int i4;
        Object[] objArr = {new Integer(i), new Integer(i2), new Integer(i3), new Float(f2), new Float(f3), new Float(f4), bVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class cls2 = Float.TYPE;
        Class[] clsArr = {cls, cls, cls, cls2, cls2, cls2, o.b.class};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11704, clsArr, Void.TYPE).isSupported) {
            int i5 = i2;
            float toY = f4;
            float s = f2;
            int duration = i;
            int videoH = i3;
            o.b animationEnd = bVar;
            float toX = f3;
            if (this.y == 0) {
                float oldScale = this.S4;
                float oldTransX = this.H4;
                float oldTransY = this.I4;
                this.S4 = s;
                this.p3 = getWidth();
                int height = getHeight();
                this.p4 = height;
                float f5 = this.S4;
                float f6 = (((float) height) * (f5 - 1.0f)) / 2.0f;
                this.F4 = f6;
                float f7 = (((float) this.p3) * (f5 - 1.0f)) / 2.0f;
                this.G4 = f7;
                float f8 = this.P4;
                if (f5 >= f8) {
                    this.S4 = f8;
                }
                if (this.S4 <= 1.0f) {
                    this.S4 = 1.0f;
                }
                if (s < 1.0f && !(f6 == 0.0f && f7 == 0.0f)) {
                    v(-this.H4, -this.I4, false);
                    FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
                    if (floatPlayerMapWindow != null) {
                        floatPlayerMapWindow.f();
                    }
                }
                setScaleX(this.S4);
                setScaleY(this.S4);
                FloatPlayerMapWindow floatPlayerMapWindow2 = this.Q4;
                if (floatPlayerMapWindow2 != null) {
                    floatPlayerMapWindow2.setScale(this.S4);
                }
                int i6 = this.p3;
                if (!(i6 == 0 || (i4 = this.p4) == 0)) {
                    float scaleDif = ((float) i4) / ((float) videoH);
                    float f9 = this.H4;
                    float f10 = f9 + ((toX * scaleDif) - f9);
                    this.H4 = f10;
                    float f11 = this.I4;
                    this.I4 = f11 + ((toY * scaleDif) - f11);
                    float f12 = this.S4;
                    this.F4 = (((float) i4) * (f12 - 1.0f)) / 2.0f;
                    float f13 = (((float) i6) * (f12 - 1.0f)) / 2.0f;
                    this.G4 = f13;
                    if (f12 <= 1.0f) {
                        this.H4 = 0.0f;
                        this.I4 = 0.0f;
                    } else {
                        if (f10 > 0.0f || f13 > Math.abs(f10)) {
                            float f14 = this.H4;
                            if (f14 > 0.0f && this.G4 - this.S4 <= Math.abs(f14)) {
                                this.H4 = this.G4 - this.S4;
                            }
                        } else {
                            this.H4 = -this.G4;
                        }
                        float f15 = this.I4;
                        if (f15 > 0.0f || this.F4 > Math.abs(f15)) {
                            float f16 = this.I4;
                            if (f16 > 0.0f && this.F4 <= Math.abs(f16)) {
                                this.I4 = this.F4;
                            }
                        } else {
                            this.I4 = -this.F4;
                        }
                        setTranslationX(this.H4);
                        setTranslationY(this.I4);
                    }
                }
                float oldTransY2 = oldTransY;
                float f17 = oldTransX;
                o.b(this, duration, oldScale, this.S4, oldTransX, this.H4, oldTransY2, this.I4, animationEnd);
            }
        }
    }
}
