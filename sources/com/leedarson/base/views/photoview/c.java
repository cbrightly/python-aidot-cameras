package com.leedarson.base.views.photoview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;

/* compiled from: CustomGestureDetector */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a = -1;
    private int b = 0;
    private final ScaleGestureDetector c;
    private VelocityTracker d;
    private boolean e;
    private float f;
    private float g;
    private final float h;
    private final float i;
    /* access modifiers changed from: private */
    public d j;

    c(Context context, d listener) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.i = (float) configuration.getScaledMinimumFlingVelocity();
        this.h = (float) configuration.getScaledTouchSlop();
        this.j = listener;
        this.c = new ScaleGestureDetector(context, new a());
    }

    /* compiled from: CustomGestureDetector */
    public class a implements ScaleGestureDetector.OnScaleGestureListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean onScale(ScaleGestureDetector detector) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{detector}, this, changeQuickRedirect, false, 948, new Class[]{ScaleGestureDetector.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            float scaleFactor = detector.getScaleFactor();
            if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
                return false;
            }
            if (scaleFactor >= 0.0f) {
                c.this.j.onScale(scaleFactor, detector.getFocusX(), detector.getFocusY());
            }
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }

    private float b(MotionEvent ev) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ev}, this, changeQuickRedirect, false, 943, new Class[]{MotionEvent.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        try {
            return ev.getX(this.b);
        } catch (Exception e2) {
            return ev.getX();
        }
    }

    private float c(MotionEvent ev) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ev}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_TIMEZONE_REQ, new Class[]{MotionEvent.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        try {
            return ev.getY(this.b);
        } catch (Exception e2) {
            return ev.getY();
        }
    }

    public boolean e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_TIMEZONE_RESP, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.c.isInProgress();
    }

    public boolean d() {
        return this.e;
    }

    public boolean f(MotionEvent ev) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ev}, this, changeQuickRedirect, false, 946, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            this.c.onTouchEvent(ev);
            return g(ev);
        } catch (IllegalArgumentException e2) {
            return true;
        }
    }

    private boolean g(MotionEvent ev) {
        int i2 = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ev}, this, changeQuickRedirect, false, 947, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        switch (ev.getAction() & 255) {
            case 0:
                this.a = ev.getPointerId(0);
                VelocityTracker obtain = VelocityTracker.obtain();
                this.d = obtain;
                if (obtain != null) {
                    obtain.addMovement(ev);
                }
                this.f = b(ev);
                this.g = c(ev);
                this.e = false;
                break;
            case 1:
                this.a = -1;
                if (this.e && this.d != null) {
                    this.f = b(ev);
                    this.g = c(ev);
                    this.d.addMovement(ev);
                    this.d.computeCurrentVelocity(1000);
                    float vX = this.d.getXVelocity();
                    float vY = this.d.getYVelocity();
                    if (Math.max(Math.abs(vX), Math.abs(vY)) >= this.i) {
                        this.j.onFling(this.f, this.g, -vX, -vY);
                    }
                }
                VelocityTracker velocityTracker = this.d;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    this.d = null;
                    break;
                }
                break;
            case 2:
                float x = b(ev);
                float y = c(ev);
                float dx = x - this.f;
                float dy = y - this.g;
                if (!this.e) {
                    this.e = Math.sqrt((double) ((dx * dx) + (dy * dy))) >= ((double) this.h);
                }
                if (this.e) {
                    this.j.onDrag(dx, dy);
                    this.f = x;
                    this.g = y;
                    VelocityTracker velocityTracker2 = this.d;
                    if (velocityTracker2 != null) {
                        velocityTracker2.addMovement(ev);
                        break;
                    }
                }
                break;
            case 3:
                this.a = -1;
                VelocityTracker velocityTracker3 = this.d;
                if (velocityTracker3 != null) {
                    velocityTracker3.recycle();
                    this.d = null;
                    break;
                }
                break;
            case 6:
                int pointerIndex = l.b(ev.getAction());
                if (ev.getPointerId(pointerIndex) == this.a) {
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    this.a = ev.getPointerId(newPointerIndex);
                    this.f = ev.getX(newPointerIndex);
                    this.g = ev.getY(newPointerIndex);
                    break;
                }
                break;
        }
        int i3 = this.a;
        if (i3 != -1) {
            i2 = i3;
        }
        this.b = ev.findPointerIndex(i2);
        return true;
    }
}
