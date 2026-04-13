package com.leedarson.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import com.leedarson.utils.o;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.DecimalFormat;
import timber.log.a;

public class IpcSurfaceView extends SurfaceView implements View.OnTouchListener {
    static DecimalFormat c = new DecimalFormat("0.0");
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public static long d = 0;
    /* access modifiers changed from: private */
    public static float f = 0.0f;
    /* access modifiers changed from: private */
    public static float q = 0.0f;
    private int A4 = 0;
    private int B4 = 0;
    private float C4 = 0.0f;
    private float D4 = 0.0f;
    private float E4;
    private float F4;
    private float G4 = 0.0f;
    private float H4 = 0.0f;
    private c I4;
    private Paint J4;
    private float K4;
    private long L4 = 0;
    private boolean M4 = false;
    private long N4 = 0;
    private float O4 = 8.0f;
    private boolean P4 = true;
    private FloatPlayerMapWindow Q4;
    float R4 = 1.0f;
    /* access modifiers changed from: private */
    public b S4;
    /* access modifiers changed from: private */
    public boolean T4 = false;
    float U4;
    float V4;
    float W4;
    float X4;
    private c Y4 = new a();
    private float a1;
    private View a2;
    private float p0;
    private float p1 = 1.0f;
    private int p2 = 0;
    private int p3 = 0;
    private int p4 = 0;
    private int x = 0;
    private float y;
    private float z;
    private int z4 = 0;

    public interface b {
        void a();
    }

    public interface c {
        boolean a(float f, MotionEvent motionEvent);
    }

    public IpcSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        o();
    }

    public IpcSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        o();
    }

    public IpcSurfaceView(Context context) {
        super(context);
        o();
    }

    private void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11667, new Class[0], Void.TYPE).isSupported) {
            setFocusable(true);
            setClickable(true);
            setLongClickable(true);
            setOnTouchListener(this);
            this.a2 = this;
            this.J4 = new Paint();
        }
    }

    public void l(int x2, int y2) {
        Object[] objArr = {new Integer(x2), new Integer(y2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11668, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            z((float) x2, (float) y2, true);
            FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
            if (floatPlayerMapWindow != null) {
                floatPlayerMapWindow.h((float) x2, (float) y2);
            }
        }
    }

    public void setMaxScale(float maxScale) {
        this.O4 = maxScale;
    }

    public void setMode(int mode) {
        this.x = mode;
    }

    public void setFloatMapWindow(FloatPlayerMapWindow floatMapWindow) {
        this.Q4 = floatMapWindow;
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11669, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            setLayerType(2, this.J4);
        }
    }

    private void s(MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11670, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            int i = this.x;
            if (i == 1) {
                this.C4 = event.getRawX() - this.p0;
                float rawY = event.getRawY() - this.a1;
                this.D4 = rawY;
                z(this.C4, rawY, true);
                FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
                if (floatPlayerMapWindow != null) {
                    floatPlayerMapWindow.h(this.C4, this.D4);
                }
                this.p0 = (float) ((int) event.getRawX());
                this.a1 = (float) ((int) event.getRawY());
            } else if (i == 2) {
                FloatPlayerMapWindow floatPlayerMapWindow2 = this.Q4;
                if (floatPlayerMapWindow2 != null) {
                    floatPlayerMapWindow2.i();
                }
                float endDis = k(event);
                timber.log.a.c("-----IpcSurfaceView----------endDis------endDis:" + endDis + " startDis:" + this.K4, new Object[0]);
                if (Math.abs(endDis - this.K4) > 20.0f) {
                    float ratio = endDis / this.K4;
                    timber.log.a.c("-----IpcSurfaceView----------Scale------ratio:" + ratio + " preRatio:" + this.p1, new Object[0]);
                    if (!n(this.p1).equals(n(ratio)) && ratio != 1.0f) {
                        w(this.a2, ratio, true);
                    }
                    this.p1 = ratio;
                }
            }
        }
    }

    private void q(MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11671, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            if (event.getPointerCount() == 2) {
                this.x = 2;
                this.K4 = k(event);
                long currentTime = System.currentTimeMillis();
                if (currentTime - this.L4 < 600) {
                    this.L4 = 0;
                    this.N4 = currentTime;
                    this.M4 = true;
                    return;
                }
                this.L4 = currentTime;
            }
        }
    }

    public void u() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11672, new Class[0], Void.TYPE).isSupported) {
            z(-this.G4, -this.H4, false);
            w(this, 0.0f, false);
            FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
            if (floatPlayerMapWindow != null) {
                floatPlayerMapWindow.c();
            }
        }
    }

    public void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11673, new Class[0], Void.TYPE).isSupported) {
            float oldScale = this.R4;
            u();
            o.c(this, 150, oldScale, this.R4);
        }
    }

    public void m(float f2, o.b bVar, int i) {
        if (!PatchProxy.proxy(new Object[]{new Float(f2), bVar, new Integer(i)}, this, changeQuickRedirect, false, 11674, new Class[]{Float.TYPE, o.b.class, Integer.TYPE}, Void.TYPE).isSupported) {
            o.b animationEnd = bVar;
            float scale = f2;
            int duration = i;
            if (this.x == 0) {
                float oldScale = this.R4;
                float oldTransX = this.G4;
                float oldTransY = this.H4;
                u();
                w(this, scale, false);
                if (scale < 1.0f) {
                    scale = 1.0f;
                }
                o.b(this, duration, oldScale, scale, oldTransX, 0.0f, oldTransY, 0.0f, animationEnd);
            }
        }
    }

    private void r(MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11675, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            this.x = 1;
            this.y = (float) ((int) event.getRawX());
            this.z = (float) ((int) event.getRawY());
            this.p0 = (float) ((int) event.getRawX());
            this.a1 = (float) ((int) event.getRawY());
            this.p2 = getWidth();
            this.p3 = getHeight();
            try {
                View view = (View) getParent().getParent();
                this.p4 = view.getWidth();
                int height = view.getHeight();
                this.z4 = height;
                this.A4 = (this.p4 - this.p2) / 2;
                this.B4 = (height - this.p3) / 2;
            } catch (Exception e) {
                e.printStackTrace();
            }
            FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
            if (floatPlayerMapWindow != null) {
                floatPlayerMapWindow.g(this.p2, this.p3, this.A4, this.B4);
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11676, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Log.d("IpcSurfaceView", "[hyf touch]dispatchTouchEvent: ");
        return super.dispatchTouchEvent(event);
    }

    public boolean onTouch(View view, MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 11677, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Log.d("IpcSurfaceView", "[hyf touch]onTouch: ");
        if (this.P4) {
            switch (event.getAction() & 255) {
                case 0:
                    r(event);
                    break;
                case 1:
                    Log.d("IpcSurfaceView", "[mode]ACTION_UP: ");
                    this.x = 0;
                    long upTime = System.currentTimeMillis();
                    if (this.M4 && upTime - this.N4 < 200) {
                        if (((double) this.R4) == 1.0d) {
                            w(this, this.O4, true);
                        } else {
                            u();
                        }
                    }
                    this.K4 = 0.0f;
                    this.M4 = false;
                    this.N4 = 0;
                    FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
                    if (floatPlayerMapWindow != null) {
                        floatPlayerMapWindow.c();
                        break;
                    }
                    break;
                case 2:
                    s(event);
                    break;
                case 5:
                    timber.log.a.c("-----IpcSurfaceView-----onTouchEvent-----event.getPointerCount():" + event.getPointerCount(), new Object[0]);
                    q(event);
                    break;
                case 6:
                    Log.d("IpcSurfaceView", "[mode]ACTION_POINTER_UP: ");
                    this.x = 0;
                    break;
            }
        }
        c cVar = this.I4;
        if (cVar != null) {
            return cVar.a(this.R4, event);
        }
        return true;
    }

    public void t(int orientation) {
        if (!PatchProxy.proxy(new Object[]{new Integer(orientation)}, this, changeQuickRedirect, false, 11678, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            switch (orientation) {
                case 1:
                    z(-this.G4, -this.H4, false);
                    return;
                default:
                    return;
            }
        }
    }

    public void setHasScale(boolean hasScale) {
        Object[] objArr = {new Byte(hasScale ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11679, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.P4 = hasScale;
            if (!hasScale) {
                u();
            }
        }
    }

    public void j() {
        this.P4 = false;
    }

    public void setTouchListener(c touchListener) {
        this.I4 = touchListener;
    }

    public float getScale() {
        return this.R4;
    }

    public void w(View v, float s, boolean showFloatW) {
        if (!PatchProxy.proxy(new Object[]{v, new Float(s), new Byte(showFloatW ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11680, new Class[]{View.class, Float.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            float f2 = this.R4 * s;
            this.R4 = f2;
            float f3 = (((float) this.p3) * (f2 - 1.0f)) / 2.0f;
            this.E4 = f3;
            float f4 = (((float) this.p2) * (f2 - 1.0f)) / 2.0f;
            this.F4 = f4;
            float f5 = this.O4;
            if (f2 >= f5) {
                this.R4 = f5;
            }
            if (this.R4 <= 1.0f) {
                this.R4 = 1.0f;
            }
            if (s < 1.0f && !(f3 == 0.0f && f4 == 0.0f)) {
                z(-this.G4, -this.H4, showFloatW);
                FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
                if (floatPlayerMapWindow != null) {
                    floatPlayerMapWindow.f();
                }
            }
            v.setScaleX(this.R4);
            v.setScaleY(this.R4);
            FloatPlayerMapWindow floatPlayerMapWindow2 = this.Q4;
            if (floatPlayerMapWindow2 != null) {
                floatPlayerMapWindow2.setScale(this.R4);
            }
        }
    }

    public void y(View v, float s) {
        Object[] objArr = {v, new Float(s)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11681, new Class[]{View.class, Float.TYPE}, Void.TYPE).isSupported) {
            if (s != this.R4) {
                u();
                float oldScale = this.R4;
                w(v, s, false);
                o.c(v, 100, oldScale, this.R4);
            }
        }
    }

    private void z(float dx, float dy, boolean showFloatW) {
        Object[] objArr = {new Float(dx), new Float(dy), new Byte(showFloatW ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11682, new Class[]{cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            float f2 = this.G4 + dx;
            this.G4 = f2;
            this.H4 += dy;
            float f3 = this.R4;
            this.E4 = (((float) this.p3) * (f3 - 1.0f)) / 2.0f;
            float f4 = (((float) this.p2) * (f3 - 1.0f)) / 2.0f;
            this.F4 = f4;
            if (f3 <= 1.0f) {
                this.G4 = 0.0f;
                this.H4 = 0.0f;
                return;
            }
            if (f2 > 0.0f || f4 > Math.abs(f2)) {
                float f5 = this.G4;
                if (f5 > 0.0f && this.F4 - this.R4 <= Math.abs(f5)) {
                    this.G4 = this.F4 - this.R4;
                }
            } else {
                this.G4 = -this.F4;
            }
            float f6 = this.H4;
            if (f6 > 0.0f || this.E4 > Math.abs(f6)) {
                float f7 = this.H4;
                if (f7 > 0.0f && this.E4 <= Math.abs(f7)) {
                    this.H4 = this.E4;
                }
            } else {
                this.H4 = -this.E4;
            }
            float f8 = this.F4;
            int i = this.A4;
            if (f8 >= ((float) i)) {
                float maxTX = f8 - ((float) i);
                if (Math.abs(this.G4) > maxTX) {
                    if (this.G4 < 0.0f) {
                        this.G4 = -maxTX;
                    } else {
                        this.G4 = maxTX;
                    }
                }
                setTranslationX(this.G4);
            }
            float maxTX2 = this.E4;
            int i2 = this.B4;
            if (maxTX2 >= ((float) i2)) {
                float maxTY = maxTX2 - ((float) i2);
                if (Math.abs(this.H4) > maxTY) {
                    if (this.H4 < 0.0f) {
                        this.H4 = -maxTY;
                    } else {
                        this.H4 = maxTY;
                    }
                }
                setTranslationY(this.H4);
            }
            FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
            if (floatPlayerMapWindow != null && showFloatW) {
                floatPlayerMapWindow.i();
            }
        }
    }

    public void x(int i, int i2, int i3, float f2, float f3, float f4, o.b bVar) {
        int i4;
        Object[] objArr = {new Integer(i), new Integer(i2), new Integer(i3), new Float(f2), new Float(f3), new Float(f4), bVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class cls2 = Float.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11683, new Class[]{cls, cls, cls, cls2, cls2, cls2, o.b.class}, Void.TYPE).isSupported) {
            int i5 = i2;
            float toY = f4;
            float s = f2;
            int duration = i;
            int videoH = i3;
            o.b animationEnd = bVar;
            float toX = f3;
            if (this.x == 0) {
                float oldScale = this.R4;
                float oldTransX = this.G4;
                float oldTransY = this.H4;
                this.R4 = s;
                this.p2 = getWidth();
                this.p3 = getHeight();
                try {
                    View view = (View) getParent().getParent();
                    this.p4 = view.getWidth();
                    int height = view.getHeight();
                    this.z4 = height;
                    this.A4 = (this.p4 - this.p2) / 2;
                    this.B4 = (height - this.p3) / 2;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                float f5 = this.R4;
                float f6 = (((float) this.p3) * (f5 - 1.0f)) / 2.0f;
                this.E4 = f6;
                float f7 = (((float) this.p2) * (f5 - 1.0f)) / 2.0f;
                this.F4 = f7;
                float f8 = this.O4;
                if (f5 >= f8) {
                    this.R4 = f8;
                }
                if (this.R4 <= 1.0f) {
                    this.R4 = 1.0f;
                }
                if (s < 1.0f && !(f6 == 0.0f && f7 == 0.0f)) {
                    z(-this.G4, -this.H4, false);
                    FloatPlayerMapWindow floatPlayerMapWindow = this.Q4;
                    if (floatPlayerMapWindow != null) {
                        floatPlayerMapWindow.f();
                    }
                }
                setScaleX(this.R4);
                setScaleY(this.R4);
                FloatPlayerMapWindow floatPlayerMapWindow2 = this.Q4;
                if (floatPlayerMapWindow2 != null) {
                    floatPlayerMapWindow2.setScale(this.R4);
                }
                int i6 = this.p2;
                if (!(i6 == 0 || (i4 = this.p3) == 0)) {
                    float scaleDif = ((float) i4) / ((float) videoH);
                    float f9 = this.G4;
                    float f10 = f9 + ((toX * scaleDif) - f9);
                    this.G4 = f10;
                    float f11 = this.H4;
                    this.H4 = f11 + ((toY * scaleDif) - f11);
                    float f12 = this.R4;
                    this.E4 = (((float) i4) * (f12 - 1.0f)) / 2.0f;
                    float f13 = (((float) i6) * (f12 - 1.0f)) / 2.0f;
                    this.F4 = f13;
                    if (f12 <= 1.0f) {
                        this.G4 = 0.0f;
                        this.H4 = 0.0f;
                    } else {
                        if (f10 > 0.0f || f13 > Math.abs(f10)) {
                            float f14 = this.G4;
                            if (f14 > 0.0f && this.F4 - this.R4 <= Math.abs(f14)) {
                                this.G4 = this.F4 - this.R4;
                            }
                        } else {
                            this.G4 = -this.F4;
                        }
                        float f15 = this.H4;
                        if (f15 > 0.0f || this.E4 > Math.abs(f15)) {
                            float f16 = this.H4;
                            if (f16 > 0.0f && this.E4 <= Math.abs(f16)) {
                                this.H4 = this.E4;
                            }
                        } else {
                            this.H4 = -this.E4;
                        }
                        float f17 = this.F4;
                        int i7 = this.A4;
                        if (f17 >= ((float) i7)) {
                            float maxTX = f17 - ((float) i7);
                            if (Math.abs(this.G4) > maxTX) {
                                if (this.G4 < 0.0f) {
                                    this.G4 = -maxTX;
                                } else {
                                    this.G4 = maxTX;
                                }
                            }
                            setTranslationX(this.G4);
                        } else {
                            this.G4 = oldTransX;
                        }
                        float f18 = this.E4;
                        int i8 = this.B4;
                        if (f18 >= ((float) i8)) {
                            float maxTY = f18 - ((float) i8);
                            if (Math.abs(this.H4) > maxTY) {
                                if (this.H4 < 0.0f) {
                                    this.H4 = -maxTY;
                                } else {
                                    this.H4 = maxTY;
                                }
                            }
                            setTranslationY(this.H4);
                        } else {
                            this.H4 = oldTransY;
                        }
                    }
                }
                float f19 = oldTransX;
                float f20 = oldScale;
                int i9 = videoH;
                o.b(this, duration, oldScale, this.R4, oldTransX, this.G4, oldTransY, this.H4, animationEnd);
            }
        }
    }

    private float k(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11684, new Class[]{MotionEvent.class}, Float.TYPE);
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

    private String n(float num) {
        Object[] objArr = {new Float(num)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 11685, new Class[]{Float.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return c.format((double) num);
    }

    public void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11686, new Class[0], Void.TYPE).isSupported) {
            setTouchListener(this.Y4);
        }
    }

    public void setDefaultTouchEvent(b handler) {
        this.S4 = handler;
    }

    public class a implements c {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11687, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            switch (event.getAction() & 255) {
                case 0:
                    boolean unused = IpcSurfaceView.this.T4 = false;
                    IpcSurfaceView.this.U4 = event.getX();
                    IpcSurfaceView.this.V4 = event.getY();
                    a.b g = timber.log.a.g("IpcSurfaceView");
                    g.h("1onTouchEvent-ACTION_DOWN" + event.getPointerCount(), new Object[0]);
                    long unused2 = IpcSurfaceView.d = System.currentTimeMillis();
                    break;
                case 1:
                    long moveTime = System.currentTimeMillis() - IpcSurfaceView.d;
                    a.b g2 = timber.log.a.g("IpcSurfaceView");
                    g2.h("1onTouchEvent-ACTION_UP" + moveTime + "==x:" + IpcSurfaceView.f + " y:" + IpcSurfaceView.q, new Object[0]);
                    if (((IpcSurfaceView.f < 20.0f && IpcSurfaceView.q < 20.0f) || scale != 1.0f) && moveTime <= 200 && IpcSurfaceView.f < 20.0f && IpcSurfaceView.q < 20.0f && !IpcSurfaceView.this.T4 && IpcSurfaceView.this.S4 != null) {
                        IpcSurfaceView.this.S4.a();
                    }
                    IpcSurfaceView ipcSurfaceView = IpcSurfaceView.this;
                    ipcSurfaceView.W4 = 0.0f;
                    ipcSurfaceView.X4 = 0.0f;
                    float unused3 = IpcSurfaceView.f = 0.0f;
                    float unused4 = IpcSurfaceView.q = 0.0f;
                    break;
                case 2:
                    IpcSurfaceView.this.W4 = event.getX();
                    IpcSurfaceView.this.X4 = event.getY();
                    IpcSurfaceView ipcSurfaceView2 = IpcSurfaceView.this;
                    float unused5 = IpcSurfaceView.f = Math.abs(ipcSurfaceView2.W4 - ipcSurfaceView2.U4);
                    IpcSurfaceView ipcSurfaceView3 = IpcSurfaceView.this;
                    float unused6 = IpcSurfaceView.q = Math.abs(ipcSurfaceView3.X4 - ipcSurfaceView3.V4);
                    break;
                case 5:
                    boolean unused7 = IpcSurfaceView.this.T4 = true;
                    a.b g3 = timber.log.a.g("IpcSurfaceView");
                    g3.h("1onTouchEvent-ACTION_POINTER_DOWN" + event.getPointerCount(), new Object[0]);
                    int pointerCount = event.getPointerCount();
                    break;
            }
            return false;
        }
    }
}
