package com.leedarson.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.leedarson.log.f;
import com.leedarson.utils.o;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.DecimalFormat;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoFrame;

public class IpcWebrtcSurfaceView extends SurfaceViewRenderer implements View.OnTouchListener {
    static DecimalFormat c = new DecimalFormat("0.0");
    public static ChangeQuickRedirect changeQuickRedirect;
    private float A4 = 0.0f;
    private float B4;
    private float C4;
    private float D4 = 0.0f;
    private float E4 = 0.0f;
    private e F4;
    private Paint G4;
    private float H4;
    private long I4 = 0;
    private boolean J4 = false;
    private long K4 = 0;
    private float L4 = 8.0f;
    private boolean M4 = true;
    private FloatPlayerMapWindow N4;
    /* access modifiers changed from: private */
    public c O4;
    private b P4 = new b();
    private String Q4;
    private float R4 = 1.7777778f;
    float S4 = 1.0f;
    private int a1 = 0;
    private int a2 = 0;
    private int d = 0;
    private float f;
    private View p0;
    private int p1 = 0;
    private int p2 = 0;
    private int p3 = 0;
    private int p4 = 0;
    private float q;
    private float x;
    private float y;
    private float z = 1.0f;
    private float z4 = 0.0f;

    public interface c {
        void onFirstFrameRendered();
    }

    public interface d {
        void a(String str, String str2, String str3);
    }

    public interface e {
        boolean a(float f, MotionEvent motionEvent);
    }

    public IpcWebrtcSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        h();
    }

    public IpcWebrtcSurfaceView(Context context) {
        super(context);
        h();
    }

    private void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11706, new Class[0], Void.TYPE).isSupported) {
            setFocusable(true);
            setClickable(true);
            setLongClickable(true);
            setOnTouchListener(this);
            this.p0 = this;
            this.G4 = new Paint();
        }
    }

    public void e(int x2, int y2) {
        Object[] objArr = {new Integer(x2), new Integer(y2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11707, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            q((float) x2, (float) y2, true);
            FloatPlayerMapWindow floatPlayerMapWindow = this.N4;
            if (floatPlayerMapWindow != null) {
                floatPlayerMapWindow.h((float) x2, (float) y2);
            }
        }
    }

    public void setMaxScale(float maxScale) {
        this.L4 = maxScale;
    }

    public void setMode(int mode) {
        this.d = mode;
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11708, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            setLayerType(2, this.G4);
        }
    }

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Object[] objArr = {new Byte(changed ? (byte) 1 : 0), new Integer(left), new Integer(top), new Integer(right), new Integer(bottom)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Boolean.TYPE, cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11709, clsArr, Void.TYPE).isSupported) {
            super.onLayout(changed, left, top, right, bottom);
        }
    }

    private void k(MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11710, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            int i = this.d;
            if (i == 1) {
                this.z4 = event.getRawX() - this.x;
                float rawY = event.getRawY() - this.y;
                this.A4 = rawY;
                q(this.z4, rawY, true);
                FloatPlayerMapWindow floatPlayerMapWindow = this.N4;
                if (floatPlayerMapWindow != null) {
                    floatPlayerMapWindow.h(this.z4, this.A4);
                }
                this.x = (float) ((int) event.getRawX());
                this.y = (float) ((int) event.getRawY());
            } else if (i == 2) {
                FloatPlayerMapWindow floatPlayerMapWindow2 = this.N4;
                if (floatPlayerMapWindow2 != null) {
                    floatPlayerMapWindow2.i();
                }
                float endDis = d(event);
                timber.log.a.c("-----IpcSurfaceView----------endDis------endDis:" + endDis + " startDis:" + this.H4, new Object[0]);
                if (Math.abs(endDis - this.H4) > 20.0f) {
                    float ratio = endDis / this.H4;
                    timber.log.a.c("-----IpcSurfaceView----------Scale------ratio:" + ratio + " preRatio:" + this.z, new Object[0]);
                    if (!g(this.z).equals(g(ratio)) && ratio != 1.0f) {
                        n(this.p0, ratio, true);
                    }
                    this.z = ratio;
                }
            }
        }
    }

    private void i(MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11711, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            if (event.getPointerCount() == 2) {
                this.d = 2;
                this.H4 = d(event);
                long currentTime = System.currentTimeMillis();
                if (currentTime - this.I4 < 600) {
                    this.I4 = 0;
                    this.K4 = currentTime;
                    this.J4 = true;
                    return;
                }
                this.I4 = currentTime;
            }
        }
    }

    public void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11712, new Class[0], Void.TYPE).isSupported) {
            n(this, 0.0f, false);
            FloatPlayerMapWindow floatPlayerMapWindow = this.N4;
            if (floatPlayerMapWindow != null) {
                floatPlayerMapWindow.c();
            }
        }
    }

    public void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11713, new Class[0], Void.TYPE).isSupported) {
            if (this.S4 != 0.0f) {
                float oldScale = this.S4;
                l();
                o.c(this, 100, oldScale, this.S4);
            }
        }
    }

    private void j(MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11714, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            this.d = 1;
            this.f = (float) ((int) event.getRawX());
            this.q = (float) ((int) event.getRawY());
            this.x = (float) ((int) event.getRawX());
            this.y = (float) ((int) event.getRawY());
            this.a1 = getWidth();
            this.p1 = getHeight();
            try {
                View view = (View) getParent().getParent();
                this.a2 = view.getWidth();
                int height = view.getHeight();
                this.p2 = height;
                this.p3 = (this.a2 - this.a1) / 2;
                this.p4 = (height - this.p1) / 2;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            FloatPlayerMapWindow floatPlayerMapWindow = this.N4;
            if (floatPlayerMapWindow != null) {
                floatPlayerMapWindow.g(this.a1, this.p1, this.p3, this.p4);
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11715, new Class[]{MotionEvent.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : super.dispatchTouchEvent(event);
    }

    public boolean onTouch(View view, MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 11716, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.M4) {
            switch (event.getAction() & 255) {
                case 0:
                    j(event);
                    break;
                case 1:
                    this.d = 0;
                    long upTime = System.currentTimeMillis();
                    if (this.J4 && upTime - this.K4 < 200) {
                        if (((double) this.S4) == 1.0d) {
                            n(this, this.L4, true);
                        } else {
                            l();
                        }
                    }
                    this.H4 = 0.0f;
                    this.J4 = false;
                    this.K4 = 0;
                    FloatPlayerMapWindow floatPlayerMapWindow = this.N4;
                    if (floatPlayerMapWindow != null) {
                        floatPlayerMapWindow.c();
                        break;
                    }
                    break;
                case 2:
                    k(event);
                    break;
                case 5:
                    timber.log.a.c("-----IpcSurfaceView-----onTouchEvent-----event.getPointerCount():" + event.getPointerCount(), new Object[0]);
                    i(event);
                    break;
                case 6:
                    this.d = 0;
                    break;
            }
        }
        e eVar = this.F4;
        if (eVar != null) {
            return eVar.a(this.S4, event);
        }
        return true;
    }

    public void setHasScale(boolean hasScale) {
        Object[] objArr = {new Byte(hasScale ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11717, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.M4 = hasScale;
            if (!hasScale) {
                l();
            }
        }
    }

    public void c() {
        this.M4 = false;
    }

    public void setFloatMapWindow(FloatPlayerMapWindow floatMapWindow) {
        this.N4 = floatMapWindow;
    }

    public void setDefaulWHPer(String modelId) {
        this.Q4 = modelId;
    }

    public void o(int i, int i2, int i3, float f2, float f3, float f4, o.b bVar) {
        int i4;
        Object[] objArr = {new Integer(i), new Integer(i2), new Integer(i3), new Float(f2), new Float(f3), new Float(f4), bVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class cls2 = Float.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11718, new Class[]{cls, cls, cls, cls2, cls2, cls2, o.b.class}, Void.TYPE).isSupported) {
            int i5 = i2;
            float toY = f4;
            float s = f2;
            int duration = i;
            int videoH = i3;
            o.b animationEnd = bVar;
            float toX = f3;
            if (this.d == 0) {
                float oldScale = this.S4;
                float oldTransX = this.D4;
                float oldTransY = this.E4;
                this.S4 = s;
                this.a1 = getWidth();
                this.p1 = getHeight();
                try {
                    View view = (View) getParent().getParent();
                    this.a2 = view.getWidth();
                    int height = view.getHeight();
                    this.p2 = height;
                    this.p3 = (this.a2 - this.a1) / 2;
                    this.p4 = (height - this.p1) / 2;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                float f5 = this.S4;
                float f6 = (((float) this.p1) * (f5 - 1.0f)) / 2.0f;
                this.B4 = f6;
                float f7 = (((float) this.a1) * (f5 - 1.0f)) / 2.0f;
                this.C4 = f7;
                float f8 = this.L4;
                if (f5 >= f8) {
                    this.S4 = f8;
                }
                if (this.S4 <= 1.0f) {
                    this.S4 = 1.0f;
                }
                if (s <= 1.0f && !(f6 == 0.0f && f7 == 0.0f)) {
                    q(-this.D4, -this.E4, false);
                    setTranslationX(0.0f);
                    setTranslationY(0.0f);
                    FloatPlayerMapWindow floatPlayerMapWindow = this.N4;
                    if (floatPlayerMapWindow != null) {
                        floatPlayerMapWindow.f();
                    }
                }
                setScaleX(this.S4);
                setScaleY(this.S4);
                FloatPlayerMapWindow floatPlayerMapWindow2 = this.N4;
                if (floatPlayerMapWindow2 != null) {
                    floatPlayerMapWindow2.setScale(this.S4);
                }
                int i6 = this.a1;
                if (!(i6 == 0 || (i4 = this.p1) == 0)) {
                    float scaleDif = ((float) i4) / ((float) videoH);
                    float f9 = this.D4;
                    float f10 = f9 + ((toX * scaleDif) - f9);
                    this.D4 = f10;
                    float f11 = this.E4;
                    this.E4 = f11 + ((toY * scaleDif) - f11);
                    float f12 = this.S4;
                    this.B4 = (((float) i4) * (f12 - 1.0f)) / 2.0f;
                    float f13 = (((float) i6) * (f12 - 1.0f)) / 2.0f;
                    this.C4 = f13;
                    if (f12 <= 1.0f) {
                        this.D4 = 0.0f;
                        this.E4 = 0.0f;
                    } else {
                        if (f10 > 0.0f || f13 > Math.abs(f10)) {
                            float f14 = this.D4;
                            if (f14 > 0.0f && this.C4 - this.S4 <= Math.abs(f14)) {
                                this.D4 = this.C4 - this.S4;
                            }
                        } else {
                            this.D4 = -this.C4;
                        }
                        float f15 = this.E4;
                        if (f15 > 0.0f || this.B4 > Math.abs(f15)) {
                            float f16 = this.E4;
                            if (f16 > 0.0f && this.B4 <= Math.abs(f16)) {
                                this.E4 = this.B4;
                            }
                        } else {
                            this.E4 = -this.B4;
                        }
                        float f17 = this.C4;
                        int i7 = this.p3;
                        if (f17 >= ((float) i7)) {
                            float maxTX = f17 - ((float) i7);
                            if (Math.abs(this.D4) > maxTX) {
                                if (this.D4 < 0.0f) {
                                    this.D4 = -maxTX;
                                } else {
                                    this.D4 = maxTX;
                                }
                            }
                            setTranslationX(this.D4);
                        } else {
                            this.D4 = oldTransX;
                        }
                        float f18 = this.B4;
                        int i8 = this.p4;
                        if (f18 >= ((float) i8)) {
                            float maxTY = f18 - ((float) i8);
                            if (Math.abs(this.E4) > maxTY) {
                                if (this.E4 < 0.0f) {
                                    this.E4 = -maxTY;
                                } else {
                                    this.E4 = maxTY;
                                }
                            }
                            setTranslationY(this.E4);
                        } else {
                            this.E4 = oldTransY;
                        }
                    }
                }
                float f19 = oldTransX;
                float f20 = oldScale;
                int i9 = videoH;
                o.b(this, duration, oldScale, this.S4, oldTransX, this.D4, oldTransY, this.E4, animationEnd);
            }
        }
    }

    public void f(float f2, o.b bVar, int i) {
        if (!PatchProxy.proxy(new Object[]{new Float(f2), bVar, new Integer(i)}, this, changeQuickRedirect, false, 11719, new Class[]{Float.TYPE, o.b.class, Integer.TYPE}, Void.TYPE).isSupported) {
            o.b animationEnd = bVar;
            float scale = f2;
            int duration = i;
            if (this.d == 0) {
                float oldScale = this.S4;
                float oldTransX = this.D4;
                float oldTransY = this.E4;
                l();
                n(this, scale, false);
                if (scale < 1.0f) {
                    scale = 1.0f;
                }
                o.b(this, duration, oldScale, scale, oldTransX, 0.0f, oldTransY, 0.0f, animationEnd);
            }
        }
    }

    public void setTouchListener(e touchListener) {
        this.F4 = touchListener;
    }

    public void n(View v, float s, boolean showFloatW) {
        if (!PatchProxy.proxy(new Object[]{v, new Float(s), new Byte(showFloatW ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11720, new Class[]{View.class, Float.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            float f2 = this.S4 * s;
            this.S4 = f2;
            float f3 = (((float) this.p1) * (f2 - 1.0f)) / 2.0f;
            this.B4 = f3;
            float f4 = (((float) this.a1) * (f2 - 1.0f)) / 2.0f;
            this.C4 = f4;
            float f5 = this.L4;
            if (f2 >= f5) {
                this.S4 = f5;
            }
            if (this.S4 <= 1.0f) {
                this.S4 = 1.0f;
            }
            if (s <= 1.0f && !(f3 == 0.0f && f4 == 0.0f)) {
                q(-this.D4, -this.E4, showFloatW);
                setTranslationX(0.0f);
                setTranslationY(0.0f);
                FloatPlayerMapWindow floatPlayerMapWindow = this.N4;
                if (floatPlayerMapWindow != null) {
                    floatPlayerMapWindow.f();
                }
            }
            v.setScaleX(this.S4);
            v.setScaleY(this.S4);
            FloatPlayerMapWindow floatPlayerMapWindow2 = this.N4;
            if (floatPlayerMapWindow2 != null) {
                floatPlayerMapWindow2.setScale(this.S4);
            }
        }
    }

    public void p(View v, float s) {
        Object[] objArr = {v, new Float(s)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11721, new Class[]{View.class, Float.TYPE}, Void.TYPE).isSupported) {
            if (s != this.S4) {
                l();
                float oldScale = this.S4;
                n(v, s, false);
                o.c(v, 50, oldScale, this.S4);
            }
        }
    }

    private void q(float dx, float dy, boolean showFloatW) {
        Object[] objArr = {new Float(dx), new Float(dy), new Byte(showFloatW ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11722, new Class[]{cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            float f2 = this.D4 + dx;
            this.D4 = f2;
            this.E4 += dy;
            float f3 = this.S4;
            this.B4 = (((float) this.p1) * (f3 - 1.0f)) / 2.0f;
            float f4 = (((float) this.a1) * (f3 - 1.0f)) / 2.0f;
            this.C4 = f4;
            if (f3 <= 1.0f) {
                this.D4 = 0.0f;
                this.E4 = 0.0f;
                return;
            }
            if (f2 > 0.0f || f4 > Math.abs(f2)) {
                float f5 = this.D4;
                if (f5 > 0.0f && this.C4 - this.S4 <= Math.abs(f5)) {
                    this.D4 = this.C4 - this.S4;
                }
            } else {
                this.D4 = -this.C4;
            }
            float f6 = this.E4;
            if (f6 > 0.0f || this.B4 > Math.abs(f6)) {
                float f7 = this.E4;
                if (f7 > 0.0f && this.B4 <= Math.abs(f7)) {
                    this.E4 = this.B4;
                }
            } else {
                this.E4 = -this.B4;
            }
            float f8 = this.C4;
            int i = this.p3;
            if (f8 >= ((float) i)) {
                float maxTX = f8 - ((float) i);
                if (Math.abs(this.D4) > maxTX) {
                    if (this.D4 < 0.0f) {
                        this.D4 = -maxTX;
                    } else {
                        this.D4 = maxTX;
                    }
                }
                setTranslationX(this.D4);
            }
            float maxTX2 = this.B4;
            int i2 = this.p4;
            if (maxTX2 >= ((float) i2)) {
                float maxTY = maxTX2 - ((float) i2);
                if (Math.abs(this.E4) > maxTY) {
                    if (this.E4 < 0.0f) {
                        this.E4 = -maxTY;
                    } else {
                        this.E4 = maxTY;
                    }
                }
                setTranslationY(this.E4);
            }
            FloatPlayerMapWindow floatPlayerMapWindow = this.N4;
            if (floatPlayerMapWindow != null && showFloatW) {
                floatPlayerMapWindow.i();
            }
        }
    }

    private float d(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11723, new Class[]{MotionEvent.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        try {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            return (float) Math.sqrt((double) ((dx * dx) + (dy * dy)));
        } catch (Exception e2) {
            return 0.0f;
        }
    }

    private String g(float num) {
        Object[] objArr = {new Float(num)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 11724, new Class[]{Float.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return c.format((double) num);
    }

    public void onFirstFrameRendered() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11725, new Class[0], Void.TYPE).isSupported) {
            super.onFirstFrameRendered();
            f.b("IpcWebrtcSurfaceView", "onFirstFrameRendered");
            Message msg = Message.obtain();
            b bVar = this.P4;
            msg.what = 1;
            bVar.sendMessage(msg);
        }
    }

    public void setOnFrameListener(c onFrameListener) {
        this.O4 = onFrameListener;
    }

    public void onFrame(VideoFrame frame) {
        if (!PatchProxy.proxy(new Object[]{frame}, this, changeQuickRedirect, false, 11726, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            try {
                super.onFrame(frame);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class b extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private b() {
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 11728, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        if (IpcWebrtcSurfaceView.this.O4 != null) {
                            IpcWebrtcSurfaceView.this.O4.onFirstFrameRendered();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void release() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11727, new Class[0], Void.TYPE).isSupported) {
            super.release();
            this.O4 = null;
            this.F4 = null;
            this.N4 = null;
        }
    }
}
