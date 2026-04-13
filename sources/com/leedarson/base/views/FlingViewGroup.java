package com.leedarson.base.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class FlingViewGroup extends FrameLayout {
    private static final Interpolator c = new a();
    public static ChangeQuickRedirect changeQuickRedirect;
    boolean A4;
    private b B4;
    private c C4;
    private int a1;
    private int a2;
    private int d;
    private int f;
    private VelocityTracker p0;
    private int p1;
    private int p2;
    private int p3;
    private d p4;
    private int q;
    private int x;
    private int y;
    private int z;
    private int z4;

    public interface b {
        int a();

        float b();

        void c(int i, int i2);

        void d(MotionEvent motionEvent);

        int e();
    }

    public interface c {
        boolean a(float f, MotionEvent motionEvent);
    }

    static /* synthetic */ void a(FlingViewGroup x0, int x1, int x2) {
        Object[] objArr = {x0, new Integer(x1), new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 725, new Class[]{FlingViewGroup.class, cls, cls}, Void.TYPE).isSupported) {
            x0.c(x1, x2);
        }
    }

    static /* synthetic */ void b(FlingViewGroup x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 726, new Class[]{FlingViewGroup.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.setScrollState(x1);
        }
    }

    public class a implements Interpolator {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public float getInterpolation(float t) {
            float t2 = t - 1.0f;
            return (t2 * t2 * t2 * t2 * t2) + 1.0f;
        }
    }

    public FlingViewGroup(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public FlingViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlingViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.d = 0;
        this.f = 0;
        this.q = 0;
        this.x = 0;
        this.y = 0;
        this.z = -1;
        this.z4 = 0;
        this.A4 = false;
        f();
    }

    private void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 715, new Class[0], Void.TYPE).isSupported) {
            this.p4 = new d();
            ViewConfiguration vc = ViewConfiguration.get(getContext());
            this.a2 = vc.getScaledTouchSlop();
            this.p2 = vc.getScaledMinimumFlingVelocity();
            this.p3 = vc.getScaledMaximumFlingVelocity();
            DisplayMetrics metric = getContext().getResources().getDisplayMetrics();
            this.d = metric.widthPixels;
            this.f = metric.heightPixels;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{motionEvent}, this, changeQuickRedirect, false, 716, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        if (this.B4 != null) {
            return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 717, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        g(event);
        b bVar = this.B4;
        if (bVar != null) {
            bVar.d(event);
        }
        c cVar = this.C4;
        if (cVar != null) {
            b bVar2 = this.B4;
            cVar.a(bVar2 == null ? 1.0f : bVar2.b(), event);
        }
        return true;
    }

    public void g(MotionEvent event) {
        float xVelocity;
        float yVelocity;
        int newIndex = 1;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 718, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
            if (this.p0 == null) {
                this.p0 = VelocityTracker.obtain();
            }
            boolean eventAddedToVelocityTracker = false;
            int action = event.getAction() & 255;
            int actionIndex = event.getActionIndex();
            MotionEvent vtev = MotionEvent.obtain(event);
            switch (action) {
                case 0:
                    this.z4 = 1;
                    setScrollState(0);
                    this.z = event.getPointerId(0);
                    this.a1 = (int) (event.getX() + 0.5f);
                    this.p1 = (int) (event.getY() + 0.5f);
                    break;
                case 1:
                    this.z4 = 0;
                    this.p0.addMovement(vtev);
                    eventAddedToVelocityTracker = true;
                    this.p0.computeCurrentVelocity(1000, (float) this.p3);
                    float xVelocity2 = -this.p0.getXVelocity(this.z);
                    float yVelocity2 = -this.p0.getYVelocity(this.z);
                    if (Math.abs(xVelocity2) < ((float) this.p2)) {
                        xVelocity = 0.0f;
                    } else {
                        int i = this.p3;
                        xVelocity = Math.max((float) (-i), Math.min(xVelocity2, (float) i));
                    }
                    if (Math.abs(yVelocity2) < ((float) this.p2)) {
                        yVelocity = 0.0f;
                    } else {
                        int i2 = this.p3;
                        yVelocity = Math.max((float) (-i2), Math.min(yVelocity2, (float) i2));
                    }
                    if (xVelocity == 0.0f && yVelocity == 0.0f) {
                        setScrollState(0);
                    } else {
                        this.p4.c((int) xVelocity, (int) yVelocity);
                    }
                    h();
                    break;
                case 2:
                    if (this.z4 != 2) {
                        int index = event.findPointerIndex(this.z);
                        if (index >= 0) {
                            int x2 = (int) (event.getX(index) + 0.5f);
                            int y2 = (int) (event.getY(index) + 0.5f);
                            int dx = this.a1 - x2;
                            int dy = this.p1 - y2;
                            if (this.y != 1) {
                                boolean startScroll = false;
                                if (Math.abs(dx) > this.a2 || Math.abs(dy) > this.a2) {
                                    startScroll = true;
                                }
                                if (startScroll) {
                                    setScrollState(1);
                                }
                            }
                            if (this.y == 1) {
                                this.a1 = x2;
                                this.p1 = y2;
                                c(dx, dy);
                                break;
                            }
                        } else {
                            d("Error processing scroll; pointer index for id " + this.z + " not found. Did any MotionEvents get skipped?");
                            return;
                        }
                    }
                    break;
                case 3:
                    h();
                    break;
                case 5:
                    this.z = event.getPointerId(actionIndex);
                    this.a1 = (int) (event.getX(actionIndex) + 0.5f);
                    this.p1 = (int) (event.getY(actionIndex) + 0.5f);
                    if (event.getPointerCount() == 2) {
                        this.z4 = 2;
                        break;
                    }
                    break;
                case 6:
                    this.z4 = 0;
                    if (event.getPointerId(actionIndex) == this.z) {
                        if (actionIndex != 0) {
                            newIndex = 0;
                        }
                        this.z = event.getPointerId(newIndex);
                        this.a1 = (int) (event.getX(newIndex) + 0.5f);
                        this.p1 = (int) (event.getY(newIndex) + 0.5f);
                        break;
                    }
                    break;
            }
            if (!eventAddedToVelocityTracker) {
                this.p0.addMovement(vtev);
            }
            vtev.recycle();
        }
    }

    private void h() {
        VelocityTracker velocityTracker;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 719, new Class[0], Void.TYPE).isSupported && (velocityTracker = this.p0) != null) {
            velocityTracker.clear();
        }
    }

    private void setScrollState(int state) {
        Object[] objArr = {new Integer(state)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 720, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (state != this.y) {
                this.y = state;
                if (state != 2) {
                    this.p4.stop();
                }
            }
        }
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        private int c = 0;
        private int d = 0;
        private Scroller f;
        private boolean q = false;
        private boolean x = false;

        public d() {
            this.f = new Scroller(FlingViewGroup.this.getContext());
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 727, new Class[0], Void.TYPE).isSupported) {
                a();
                Scroller scroller = this.f;
                if (scroller.computeScrollOffset()) {
                    int x2 = scroller.getCurrX();
                    this.c = x2;
                    int y2 = scroller.getCurrY();
                    this.d = y2;
                    FlingViewGroup.a(FlingViewGroup.this, x2 - this.c, y2 - this.d);
                    d();
                }
                b();
            }
        }

        public void c(int velocityX, int velocityY) {
            Object[] objArr = {new Integer(velocityX), new Integer(velocityY)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 728, clsArr, Void.TYPE).isSupported) {
                this.c = 0;
                this.d = 0;
                FlingViewGroup.b(FlingViewGroup.this, 2);
                Scroller scroller = this.f;
                scroller.fling(0, 0, velocityX, velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                d();
            }
        }

        public void stop() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 729, new Class[0], Void.TYPE).isSupported) {
                this.f.abortAnimation();
            }
        }

        private void a() {
            this.x = false;
            this.q = true;
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 730, new Class[0], Void.TYPE).isSupported) {
                this.q = false;
                if (this.x) {
                    d();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 731, new Class[0], Void.TYPE).isSupported) {
                if (this.q) {
                    this.x = true;
                } else {
                    ViewCompat.postOnAnimation(FlingViewGroup.this, this);
                }
            }
        }
    }

    private void c(int i, int i2) {
        int childHeight;
        int childWidth;
        int playerGapH;
        int playerGapW;
        boolean isForceCenterW;
        Object[] objArr = {new Integer(i), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 723, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            int dy = i2;
            int dx = i;
            if (this.B4.a() > 0 && this.B4.e() > 0) {
                Rect viewport = new Rect();
                getGlobalVisibleRect(viewport);
                int height = viewport.height();
                int width = viewport.width();
                e("viewport width " + width + " height " + height);
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                if (((double) ((this.B4.a() / this.B4.e()) - 1)) < 0.1d) {
                    float a3 = (float) this.B4.a();
                    b bVar = this.B4;
                    childWidth = (int) (a3 * (bVar == null ? 1.0f : bVar.b()));
                    float e = (float) this.B4.e();
                    b bVar2 = this.B4;
                    childHeight = (int) (e * (bVar2 == null ? 1.0f : bVar2.b()));
                } else {
                    float f2 = (float) width;
                    b bVar3 = this.B4;
                    childWidth = (int) (f2 * (bVar3 == null ? 1.0f : bVar3.b()));
                    float f3 = (float) height;
                    b bVar4 = this.B4;
                    childHeight = (int) (f3 * (bVar4 == null ? 1.0f : bVar4.b()));
                }
                e(" 当前的scale " + this.B4.b());
                e(" 计算的childWidth " + childWidth + " childHeight " + childHeight);
                e(" 实际的childWidth " + this.B4.a() + " childHeight " + this.B4.e());
                float widthMargin = (float) ((childWidth - width) / 2);
                float heightMargin = (float) ((childHeight - height) / 2);
                e(" dx " + dx + " dy " + dy);
                e(" scrollX " + scrollX + " scrollY " + scrollY);
                e(" widthMargin " + widthMargin + " heightMargin " + heightMargin);
                Rect rect = viewport;
                b bVar5 = this.B4;
                if (bVar5 == null || bVar5.a() <= 0 || this.B4.e() <= 0) {
                    playerGapW = 0;
                    playerGapH = 0;
                } else if (this.B4.a() > this.B4.e()) {
                    playerGapH = (int) ((((float) childHeight) - ((((float) this.B4.e()) / ((float) this.B4.a())) * ((float) childWidth))) / 2.0f);
                    playerGapW = 0;
                } else {
                    playerGapW = (int) ((((float) childWidth) - ((((float) this.B4.a()) / ((float) this.B4.e())) * ((float) childHeight))) / 2.0f);
                    playerGapH = 0;
                }
                boolean isForceCenterH = false;
                int i3 = childWidth;
                if (((float) this.B4.a()) * this.B4.b() < ((float) width)) {
                    playerGapW = 0;
                    isForceCenterW = true;
                } else {
                    isForceCenterW = false;
                }
                int i4 = width;
                if (((float) this.B4.e()) * this.B4.b() < ((float) height)) {
                    playerGapH = 0;
                    isForceCenterH = true;
                }
                e(" playerGapW " + playerGapW + " playerGapH " + playerGapH);
                float rightWidthMargin = widthMargin - ((float) playerGapW);
                int i5 = height;
                float leftWidthMargin = (-widthMargin) + ((float) playerGapW);
                b bVar6 = this.B4;
                if (bVar6 != null && bVar6.b() > 1.0f) {
                    leftWidthMargin -= this.B4.b();
                }
                if (isForceCenterW) {
                    dx = -getScrollX();
                } else if (((float) (scrollX + dx)) > rightWidthMargin) {
                    dx = (int) (rightWidthMargin - ((float) scrollX));
                } else if (((float) (scrollX + dx)) < leftWidthMargin) {
                    dx = (int) (leftWidthMargin - ((float) scrollX));
                }
                float bottomHeightMargin = heightMargin - ((float) playerGapH);
                boolean z2 = isForceCenterW;
                float f4 = heightMargin;
                float topHeightMargin = (-heightMargin) + ((float) playerGapH);
                if (isForceCenterH) {
                    dy = -getScrollY();
                    e("强制上下居中 dy " + dy);
                    float f5 = rightWidthMargin;
                } else if (((float) (scrollY + dy)) > bottomHeightMargin) {
                    StringBuilder sb = new StringBuilder();
                    float f6 = rightWidthMargin;
                    sb.append("下边界 bottomHeightMargin ");
                    sb.append(bottomHeightMargin);
                    sb.append(" scrollY ");
                    sb.append(scrollY);
                    e(sb.toString());
                    dy = (int) (bottomHeightMargin - ((float) scrollY));
                    e("下边界 dy " + dy);
                } else {
                    if (((float) (scrollY + dy)) < topHeightMargin) {
                        e("上边界 topHeightMargin " + topHeightMargin + " scrollY " + scrollY);
                        dy = (int) (topHeightMargin - ((float) scrollY));
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("上边界 dy ");
                        sb2.append(dy);
                        e(sb2.toString());
                    }
                }
                e("constrainScrollBy dx " + dx + " dy " + dy);
                scrollBy(dx, dy);
                b bVar7 = this.B4;
                if (bVar7 != null) {
                    bVar7.c(-dx, -dy);
                }
            }
        }
    }

    public void setCallBack(b callBack) {
        this.B4 = callBack;
    }

    public void setTouchListener(c touchListener) {
        this.C4 = touchListener;
    }

    private void e(String msg) {
    }

    private void d(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 724, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("FlingViewGroup").c(msg, new Object[0]);
        }
    }
}
