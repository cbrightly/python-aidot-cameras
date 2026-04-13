package com.leedarson.base.views;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.VelocityTrackerCompat;
import androidx.core.view.ViewCompat;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: FlingViewHelper */
public class e {
    private static final Interpolator a = new a();
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public View b;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = -1;
    private VelocityTracker i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private c o;
    /* access modifiers changed from: private */
    public b p;

    /* compiled from: FlingViewHelper */
    public interface b {
        void a(int i, int i2);

        void b();

        void c();
    }

    static /* synthetic */ void b(e x0, int x1, int x2) {
        Object[] objArr = {x0, new Integer(x1), new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 738, new Class[]{e.class, cls, cls}, Void.TYPE).isSupported) {
            x0.f(x1, x2);
        }
    }

    static /* synthetic */ void d(e x0, String x1) {
        Class[] clsArr = {e.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 739, clsArr, Void.TYPE).isSupported) {
            x0.h(x1);
        }
    }

    static /* synthetic */ void e(e x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 740, new Class[]{e.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.m(x1);
        }
    }

    /* compiled from: FlingViewHelper */
    public class a implements Interpolator {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public float getInterpolation(float t) {
            float t2 = t - 1.0f;
            return (t2 * t2 * t2 * t2 * t2) + 1.0f;
        }
    }

    public e(View view) {
        this.b = view;
        i();
    }

    private void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 732, new Class[0], Void.TYPE).isSupported) {
            this.o = new c();
            ViewConfiguration vc = ViewConfiguration.get(this.b.getContext());
            this.l = vc.getScaledTouchSlop();
            this.m = vc.getScaledMinimumFlingVelocity();
            this.n = vc.getScaledMaximumFlingVelocity();
            DisplayMetrics metric = this.b.getContext().getResources().getDisplayMetrics();
            this.c = metric.widthPixels;
            this.d = metric.heightPixels;
        }
    }

    public void j(View view, MotionEvent motionEvent) {
        float xVelocity;
        float yVelocity;
        int newIndex = 0;
        if (!PatchProxy.proxy(new Object[]{view, motionEvent}, this, changeQuickRedirect, false, 733, new Class[]{View.class, MotionEvent.class}, Void.TYPE).isSupported) {
            MotionEvent event = motionEvent;
            View view2 = view;
            if (this.i == null) {
                this.i = VelocityTracker.obtain();
            }
            boolean eventAddedToVelocityTracker = false;
            int action = MotionEventCompat.getActionMasked(event);
            int actionIndex = MotionEventCompat.getActionIndex(event);
            MotionEvent vtev = MotionEvent.obtain(event);
            switch (action) {
                case 0:
                    m(0);
                    this.h = event.getPointerId(0);
                    this.j = (int) (event.getX() + 0.5f);
                    this.k = (int) (event.getY() + 0.5f);
                    break;
                case 1:
                    this.i.addMovement(vtev);
                    eventAddedToVelocityTracker = true;
                    this.i.computeCurrentVelocity(1000, (float) this.n);
                    float xVelocity2 = -VelocityTrackerCompat.getXVelocity(this.i, this.h);
                    float yVelocity2 = -VelocityTrackerCompat.getYVelocity(this.i, this.h);
                    if (Math.abs(xVelocity2) < ((float) this.m)) {
                        xVelocity = 0.0f;
                    } else {
                        int i2 = this.n;
                        xVelocity = Math.max((float) (-i2), Math.min(xVelocity2, (float) i2));
                    }
                    if (Math.abs(yVelocity2) < ((float) this.m)) {
                        yVelocity = 0.0f;
                    } else {
                        int i3 = this.n;
                        yVelocity = Math.max((float) (-i3), Math.min(yVelocity2, (float) i3));
                    }
                    if (xVelocity == 0.0f && yVelocity == 0.0f) {
                        m(0);
                    } else {
                        this.o.c((int) xVelocity, (int) yVelocity);
                    }
                    k();
                    break;
                case 2:
                    int index = event.findPointerIndex(this.h);
                    if (index >= 0) {
                        int x = (int) (event.getX(index) + 0.5f);
                        int y = (int) (event.getY(index) + 0.5f);
                        int dx = this.j - x;
                        int dy = this.k - y;
                        if (this.g != 1) {
                            boolean startScroll = false;
                            if (Math.abs(dx) > this.l || Math.abs(dy) > this.l) {
                                startScroll = true;
                            }
                            if (startScroll) {
                                m(1);
                            }
                        }
                        if (this.g == 1) {
                            this.j = x;
                            this.k = y;
                            break;
                        }
                    } else {
                        g("Error processing scroll; pointer index for id " + this.h + " not found. Did any MotionEvents get skipped?");
                        return;
                    }
                    break;
                case 3:
                    k();
                    break;
                case 5:
                    this.h = event.getPointerId(actionIndex);
                    this.j = (int) (event.getX(actionIndex) + 0.5f);
                    this.k = (int) (event.getY(actionIndex) + 0.5f);
                    break;
                case 6:
                    if (event.getPointerId(actionIndex) == this.h) {
                        if (actionIndex == 0) {
                            newIndex = 1;
                        }
                        this.h = event.getPointerId(newIndex);
                        this.j = (int) (event.getX(newIndex) + 0.5f);
                        this.k = (int) (event.getY(newIndex) + 0.5f);
                        break;
                    }
                    break;
            }
            if (!eventAddedToVelocityTracker) {
                this.i.addMovement(vtev);
            }
            vtev.recycle();
        }
    }

    private void k() {
        VelocityTracker velocityTracker;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 734, new Class[0], Void.TYPE).isSupported && (velocityTracker = this.i) != null) {
            velocityTracker.clear();
        }
    }

    private void m(int state) {
        Object[] objArr = {new Integer(state)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 735, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (state != this.g) {
                this.g = state;
                if (state != 2) {
                    this.o.stop();
                }
            }
        }
    }

    /* compiled from: FlingViewHelper */
    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        private int c = 0;
        private int d = 0;
        private Scroller f;
        private boolean q = false;
        private boolean x = false;

        public c() {
            this.f = new Scroller(e.this.b.getContext());
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 741, new Class[0], Void.TYPE).isSupported) {
                a();
                Scroller scroller = this.f;
                if (scroller.computeScrollOffset()) {
                    int x2 = scroller.getCurrX();
                    this.c = x2;
                    int y2 = scroller.getCurrY();
                    this.d = y2;
                    e.b(e.this, x2 - this.c, y2 - this.d);
                    d();
                } else if (e.this.p != null) {
                    e.d(e.this, "scrollEnd");
                    e.this.p.b();
                }
                b();
            }
        }

        public void c(int i, int i2) {
            Object[] objArr = {new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 742, clsArr, Void.TYPE).isSupported) {
                int velocityY = i2;
                int velocityX = i;
                if (e.this.p != null) {
                    e.d(e.this, "scrollEnd");
                    e.this.p.c();
                }
                this.c = 0;
                this.d = 0;
                e.e(e.this, 2);
                this.f.fling(0, 0, velocityX, velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                d();
            }
        }

        public void stop() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 743, new Class[0], Void.TYPE).isSupported) {
                this.f.abortAnimation();
            }
        }

        private void a() {
            this.x = false;
            this.q = true;
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 744, new Class[0], Void.TYPE).isSupported) {
                this.q = false;
                if (this.x) {
                    d();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 745, new Class[0], Void.TYPE).isSupported) {
                if (this.q) {
                    this.x = true;
                } else {
                    ViewCompat.postOnAnimation(e.this.b, this);
                }
            }
        }
    }

    private void f(int dx, int dy) {
        Object[] objArr = {new Integer(dx), new Integer(dy)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 736, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            Rect viewport = new Rect();
            this.b.getGlobalVisibleRect(viewport);
            int height = viewport.height();
            int width = viewport.width();
            int scrollX = this.b.getScrollX();
            int scrollY = this.b.getScrollY();
            h("constrainScrollBy dx " + dx + " dy " + dy);
            b bVar = this.p;
            if (bVar != null) {
                bVar.a(-dx, -dy);
            }
        }
    }

    public void l(b callBack) {
        this.p = callBack;
    }

    private void h(String msg) {
    }

    private void g(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 737, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("FlingViewHelper").c(msg, new Object[0]);
        }
    }
}
