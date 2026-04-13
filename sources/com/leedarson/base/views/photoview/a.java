package com.leedarson.base.views.photoview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.OverScroller;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;

/* compiled from: AlbumPhotoViewAttacher */
public class a implements View.OnTouchListener, View.OnLayoutChangeListener {
    private static float c = 3.0f;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static float d = 1.75f;
    /* access modifiers changed from: private */
    public static float f = 1.0f;
    private static int q = 200;
    /* access modifiers changed from: private */
    public static int x = 1;
    private final Matrix A4 = new Matrix();
    private final Matrix B4 = new Matrix();
    /* access modifiers changed from: private */
    public final Matrix C4 = new Matrix();
    private final RectF D4 = new RectF();
    private final float[] E4 = new float[9];
    private e F4;
    /* access modifiers changed from: private */
    public g G4;
    /* access modifiers changed from: private */
    public f H4;
    /* access modifiers changed from: private */
    public k I4;
    /* access modifiers changed from: private */
    public View.OnClickListener J4;
    /* access modifiers changed from: private */
    public View.OnLongClickListener K4;
    /* access modifiers changed from: private */
    public h L4;
    /* access modifiers changed from: private */
    public i M4;
    /* access modifiers changed from: private */
    public j N4;
    /* access modifiers changed from: private */
    public f O4;
    /* access modifiers changed from: private */
    public int P4 = 2;
    /* access modifiers changed from: private */
    public int Q4 = 2;
    private float R4;
    private boolean S4 = true;
    private ImageView.ScaleType T4 = ImageView.ScaleType.FIT_CENTER;
    /* access modifiers changed from: private */
    public d U4 = new C0084a();
    private float a1 = d;
    /* access modifiers changed from: private */
    public boolean a2 = true;
    private float p0 = f;
    /* access modifiers changed from: private */
    public float p1 = c;
    /* access modifiers changed from: private */
    public boolean p2 = false;
    /* access modifiers changed from: private */
    public ImageView p3;
    private GestureDetector p4;
    /* access modifiers changed from: private */
    public Interpolator y = new AccelerateDecelerateInterpolator();
    /* access modifiers changed from: private */
    public int z = q;
    /* access modifiers changed from: private */
    public c z4;

    static /* synthetic */ int c(a x0, ImageView x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 927, new Class[]{a.class, ImageView.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x0.I(x1);
    }

    static /* synthetic */ int d(a x0, ImageView x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ, new Class[]{a.class, ImageView.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x0.H(x1);
    }

    static /* synthetic */ void s(a x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 926, new Class[]{a.class}, Void.TYPE).isSupported) {
            x0.B();
        }
    }

    /* renamed from: com.leedarson.base.views.photoview.a$a  reason: collision with other inner class name */
    /* compiled from: AlbumPhotoViewAttacher */
    public class C0084a implements d {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0084a() {
        }

        public void onDrag(float dx, float dy) {
            Object[] objArr = {new Float(dx), new Float(dy)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Float.TYPE;
            Class[] clsArr = {cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_RESP, clsArr, Void.TYPE).isSupported) {
                if (!a.this.z4.e()) {
                    if (a.this.N4 != null) {
                        a.this.N4.onDrag(dx, dy);
                    }
                    a.this.C4.postTranslate(dx, dy);
                    a.s(a.this);
                    ViewParent parent = a.this.p3.getParent();
                    if (!a.this.a2 || a.this.z4.e() || a.this.p2) {
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    } else if ((a.this.P4 == 2 || ((a.this.P4 == 0 && dx >= 1.0f) || ((a.this.P4 == 1 && dx <= -1.0f) || ((a.this.Q4 == 0 && dy >= 1.0f) || (a.this.Q4 == 1 && dy <= -1.0f))))) && parent != null) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                }
            }
        }

        public void onFling(float f, float f2, float velocityX, float velocityY) {
            Object[] objArr = {new Float(f), new Float(f2), new Float(velocityX), new Float(velocityY)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Float.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 930, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
                a aVar = a.this;
                f unused = aVar.O4 = new f(aVar.p3.getContext());
                f y = a.this.O4;
                a aVar2 = a.this;
                int c = a.c(aVar2, aVar2.p3);
                a aVar3 = a.this;
                y.b(c, a.d(aVar3, aVar3.p3), (int) velocityX, (int) velocityY);
                a.this.p3.post(a.this.O4);
            }
        }

        public void onScale(float scaleFactor, float focusX, float focusY) {
            Object[] objArr = {new Float(scaleFactor), new Float(focusX), new Float(focusY)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Float.TYPE;
            Class[] clsArr = {cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 931, clsArr, Void.TYPE).isSupported) {
                if (a.this.M() < a.this.p1 || scaleFactor < 1.0f) {
                    if (a.this.L4 != null) {
                        a.this.L4.onScaleChange(scaleFactor, focusX, focusY);
                    }
                    a.this.C4.postScale(scaleFactor, scaleFactor, focusX, focusY);
                    a.s(a.this);
                }
            }
        }
    }

    public a(ImageView imageView) {
        this.p3 = imageView;
        imageView.setOnTouchListener(this);
        imageView.addOnLayoutChangeListener(this);
        if (!imageView.isInEditMode()) {
            this.R4 = 0.0f;
            this.z4 = new c(imageView.getContext(), this.U4);
            GestureDetector gestureDetector = new GestureDetector(imageView.getContext(), new b());
            this.p4 = gestureDetector;
            gestureDetector.setOnDoubleTapListener(new c());
        }
    }

    /* compiled from: AlbumPhotoViewAttacher */
    public class b extends GestureDetector.SimpleOnGestureListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onLongPress(MotionEvent motionEvent) {
            if (!PatchProxy.proxy(new Object[]{motionEvent}, this, changeQuickRedirect, false, 932, new Class[]{MotionEvent.class}, Void.TYPE).isSupported) {
                if (a.this.K4 != null) {
                    a.this.K4.onLongClick(a.this.p3);
                }
            }
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Object[] objArr = {e1, e2, new Float(velocityX), new Float(velocityY)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Float.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 933, new Class[]{MotionEvent.class, MotionEvent.class, cls, cls}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (a.this.M4 == null || a.this.M() > a.f || e1.getPointerCount() > a.x || e2.getPointerCount() > a.x) {
                return false;
            }
            return a.this.M4.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /* compiled from: AlbumPhotoViewAttacher */
    public class c implements GestureDetector.OnDoubleTapListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 934, new Class[]{MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (a.this.J4 != null) {
                a.this.J4.onClick(a.this.p3);
            }
            RectF displayRect = a.this.D();
            float x = e.getX();
            float y = e.getY();
            if (a.this.I4 != null) {
                a.this.I4.onViewTap(a.this.p3, x, y);
            }
            if (displayRect != null) {
                if (displayRect.contains(x, y)) {
                    float xResult = (x - displayRect.left) / displayRect.width();
                    float yResult = (y - displayRect.top) / displayRect.height();
                    if (a.this.G4 != null) {
                        a.this.G4.onPhotoTap(a.this.p3, xResult, yResult);
                    }
                    return true;
                } else if (a.this.H4 != null) {
                    a.this.H4.onOutsidePhotoTap(a.this.p3);
                }
            }
            return false;
        }

        public boolean onDoubleTap(MotionEvent ev) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ev}, this, changeQuickRedirect, false, 935, new Class[]{MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            try {
                float scale = a.this.M();
                float x = ev.getX();
                float y = ev.getY();
                if (scale < a.this.K()) {
                    a aVar = a.this;
                    aVar.Y(aVar.K(), x, y, true);
                } else if (scale < a.this.K() || scale >= a.this.J()) {
                    a aVar2 = a.this;
                    aVar2.Y(aVar2.L(), x, y, true);
                } else {
                    a aVar3 = a.this;
                    aVar3.Y(aVar3.J(), x, y, true);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener) {
        if (!PatchProxy.proxy(new Object[]{newOnDoubleTapListener}, this, changeQuickRedirect, false, 894, new Class[]{GestureDetector.OnDoubleTapListener.class}, Void.TYPE).isSupported) {
            this.p4.setOnDoubleTapListener(newOnDoubleTapListener);
        }
    }

    public void setOnScaleChangeListener(h onScaleChangeListener) {
        this.L4 = onScaleChangeListener;
    }

    public void setOnSingleFlingListener(i onSingleFlingListener) {
        this.M4 = onSingleFlingListener;
    }

    public RectF D() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 895, new Class[0], RectF.class);
        if (proxy.isSupported) {
            return (RectF) proxy.result;
        }
        C();
        return E(F());
    }

    public void W(float degrees) {
        Object[] objArr = {new Float(degrees)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 898, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.C4.setRotate(degrees % 360.0f);
            B();
        }
    }

    public void V(float degrees) {
        Object[] objArr = {new Float(degrees)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 899, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.C4.postRotate(degrees % 360.0f);
            B();
        }
    }

    public float L() {
        return this.p0;
    }

    public float K() {
        return this.a1;
    }

    public float J() {
        return this.p1;
    }

    public float M() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 900, new Class[0], Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        return (float) Math.sqrt((double) (((float) Math.pow((double) O(this.C4, 0), 2.0d)) + ((float) Math.pow((double) O(this.C4, 3), 2.0d))));
    }

    public ImageView.ScaleType N() {
        return this.T4;
    }

    public void onLayoutChange(View view, int left, int i, int i2, int i3, int oldLeft, int i4, int i5, int i6) {
        Object[] objArr = {view, new Integer(left), new Integer(i), new Integer(i2), new Integer(i3), new Integer(oldLeft), new Integer(i4), new Integer(i5), new Integer(i6)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {View.class, cls, cls, cls, cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 901, clsArr, Void.TYPE).isSupported) {
            int right = i2;
            int oldRight = i5;
            int oldBottom = i6;
            View view2 = view;
            int top = i;
            int oldTop = i4;
            int bottom = i3;
            if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
                e0(this.p3.getDrawable());
            }
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        RectF rect;
        boolean z2 = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v, ev}, this, changeQuickRedirect, false, 902, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean handled = false;
        if (!this.S4 || !l.c((ImageView) v)) {
            return false;
        }
        switch (ev.getAction()) {
            case 0:
                ViewParent parent = v.getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                A();
                break;
            case 1:
            case 3:
                if (M() >= this.p0) {
                    if (M() > this.p1 && (rect = D()) != null) {
                        v.post(new e(M(), this.p1, rect.centerX(), rect.centerY()));
                        handled = true;
                        break;
                    }
                } else {
                    RectF rect2 = D();
                    if (rect2 != null) {
                        v.post(new e(M(), this.p0, rect2.centerX(), rect2.centerY()));
                        handled = true;
                        break;
                    }
                }
                break;
        }
        c cVar = this.z4;
        if (cVar != null) {
            boolean wasScaling = cVar.e();
            boolean wasDragging = this.z4.d();
            boolean handled2 = this.z4.f(ev);
            boolean didntScale = !wasScaling && !this.z4.e();
            boolean didntDrag = !wasDragging && !this.z4.d();
            if (didntScale && didntDrag) {
                z2 = true;
            }
            this.p2 = z2;
            handled = handled2;
        }
        GestureDetector gestureDetector = this.p4;
        if (gestureDetector == null || !gestureDetector.onTouchEvent(ev)) {
            return handled;
        }
        return true;
    }

    public void Q(boolean allow) {
        this.a2 = allow;
    }

    public void U(float minimumScale) {
        Object[] objArr = {new Float(minimumScale)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, TypedValues.Custom.TYPE_STRING, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            l.a(minimumScale, this.a1, this.p1);
            this.p0 = minimumScale;
        }
    }

    public void T(float mediumScale) {
        Object[] objArr = {new Float(mediumScale)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, TypedValues.Custom.TYPE_BOOLEAN, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            l.a(this.p0, mediumScale, this.p1);
            this.a1 = mediumScale;
        }
    }

    public void S(float maximumScale) {
        Object[] objArr = {new Float(maximumScale)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, TypedValues.Custom.TYPE_DIMENSION, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            l.a(this.p0, this.a1, maximumScale);
            this.p1 = maximumScale;
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.K4 = listener;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.J4 = listener;
    }

    public void setOnMatrixChangeListener(e listener) {
        this.F4 = listener;
    }

    public void setOnPhotoTapListener(g listener) {
        this.G4 = listener;
    }

    public void setOnOutsidePhotoTapListener(f mOutsidePhotoTapListener) {
        this.H4 = mOutsidePhotoTapListener;
    }

    public void setOnViewTapListener(k listener) {
        this.I4 = listener;
    }

    public void setOnViewDragListener(j listener) {
        this.N4 = listener;
    }

    public void X(float scale) {
        if (!PatchProxy.proxy(new Object[]{new Float(scale)}, this, changeQuickRedirect, false, 907, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            Z(scale, false);
        }
    }

    public void Z(float scale, boolean animate) {
        Object[] objArr = {new Float(scale), new Byte(animate ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 908, new Class[]{Float.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            Y(scale, (float) (this.p3.getRight() / 2), (float) (this.p3.getBottom() / 2), animate);
        }
    }

    public void Y(float scale, float focalX, float focalY, boolean animate) {
        Object[] objArr = {new Float(scale), new Float(focalX), new Float(focalY), new Byte(animate ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, PictureConfig.REQUEST_CAMERA, new Class[]{cls, cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (scale < this.p0 || scale > this.p1) {
                throw new IllegalArgumentException("Scale must be within the range of minScale and maxScale");
            } else if (animate) {
                this.p3.post(new e(M(), scale, focalX, focalY));
            } else {
                this.C4.setScale(scale, scale, focalX, focalY);
                B();
            }
        }
    }

    public void a0(ImageView.ScaleType scaleType) {
        if (!PatchProxy.proxy(new Object[]{scaleType}, this, changeQuickRedirect, false, 910, new Class[]{ImageView.ScaleType.class}, Void.TYPE).isSupported) {
            if (l.d(scaleType) && scaleType != this.T4) {
                this.T4 = scaleType;
                d0();
            }
        }
    }

    public void c0(boolean zoomable) {
        Object[] objArr = {new Byte(zoomable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 911, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.S4 = zoomable;
            d0();
        }
    }

    public void d0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_FLOWINFO_REQ, new Class[0], Void.TYPE).isSupported) {
            if (this.S4) {
                e0(this.p3.getDrawable());
            } else {
                P();
            }
        }
    }

    private Matrix F() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 915, new Class[0], Matrix.class);
        if (proxy.isSupported) {
            return (Matrix) proxy.result;
        }
        this.B4.set(this.A4);
        this.B4.postConcat(this.C4);
        return this.B4;
    }

    public Matrix G() {
        return this.B4;
    }

    public void b0(int milliseconds) {
        this.z = milliseconds;
    }

    private float O(Matrix matrix, int whichValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{matrix, new Integer(whichValue)}, this, changeQuickRedirect, false, 916, new Class[]{Matrix.class, Integer.TYPE}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        matrix.getValues(this.E4);
        return this.E4[whichValue];
    }

    private void P() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 917, new Class[0], Void.TYPE).isSupported) {
            this.C4.reset();
            V(this.R4);
            R(F());
            C();
        }
    }

    private void R(Matrix matrix) {
        RectF displayRect;
        if (!PatchProxy.proxy(new Object[]{matrix}, this, changeQuickRedirect, false, 918, new Class[]{Matrix.class}, Void.TYPE).isSupported) {
            this.p3.setImageMatrix(matrix);
            if (this.F4 != null && (displayRect = E(matrix)) != null) {
                this.F4.onMatrixChanged(displayRect);
            }
        }
    }

    private void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 919, new Class[0], Void.TYPE).isSupported) {
            if (C()) {
                R(F());
            }
        }
    }

    private RectF E(Matrix matrix) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{matrix}, this, changeQuickRedirect, false, 920, new Class[]{Matrix.class}, RectF.class);
        if (proxy.isSupported) {
            return (RectF) proxy.result;
        }
        Drawable d2 = this.p3.getDrawable();
        if (d2 == null) {
            return null;
        }
        this.D4.set(0.0f, 0.0f, (float) d2.getIntrinsicWidth(), (float) d2.getIntrinsicHeight());
        matrix.mapRect(this.D4);
        return this.D4;
    }

    private void e0(Drawable drawable) {
        if (!PatchProxy.proxy(new Object[]{drawable}, this, changeQuickRedirect, false, 921, new Class[]{Drawable.class}, Void.TYPE).isSupported) {
            if (drawable != null) {
                float viewWidth = (float) I(this.p3);
                float viewHeight = (float) H(this.p3);
                int drawableWidth = drawable.getIntrinsicWidth();
                int drawableHeight = drawable.getIntrinsicHeight();
                this.A4.reset();
                float widthScale = viewWidth / ((float) drawableWidth);
                float heightScale = viewHeight / ((float) drawableHeight);
                ImageView.ScaleType scaleType = this.T4;
                if (scaleType != ImageView.ScaleType.CENTER) {
                    if (scaleType != ImageView.ScaleType.CENTER_CROP) {
                        if (scaleType != ImageView.ScaleType.CENTER_INSIDE) {
                            RectF mTempSrc = new RectF(0.0f, 0.0f, (float) drawableWidth, (float) drawableHeight);
                            RectF mTempDst = new RectF(0.0f, 0.0f, viewWidth, viewHeight);
                            if (((int) this.R4) % 180 != 0) {
                                mTempSrc = new RectF(0.0f, 0.0f, (float) drawableHeight, (float) drawableWidth);
                            }
                            switch (d.a[this.T4.ordinal()]) {
                                case 1:
                                    this.A4.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.CENTER);
                                    break;
                                case 2:
                                    this.A4.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.START);
                                    break;
                                case 3:
                                    this.A4.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.END);
                                    break;
                                case 4:
                                    this.A4.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.FILL);
                                    break;
                            }
                        } else {
                            float scale = Math.min(1.0f, Math.min(widthScale, heightScale));
                            this.A4.postScale(scale, scale);
                            this.A4.postTranslate((viewWidth - (((float) drawableWidth) * scale)) / 2.0f, (viewHeight - (((float) drawableHeight) * scale)) / 2.0f);
                        }
                    } else {
                        float scale2 = Math.max(widthScale, heightScale);
                        this.A4.postScale(scale2, scale2);
                        this.A4.postTranslate((viewWidth - (((float) drawableWidth) * scale2)) / 2.0f, (viewHeight - (((float) drawableHeight) * scale2)) / 2.0f);
                    }
                } else {
                    this.A4.postTranslate((viewWidth - ((float) drawableWidth)) / 2.0f, (viewHeight - ((float) drawableHeight)) / 2.0f);
                }
                P();
            }
        }
    }

    /* compiled from: AlbumPhotoViewAttacher */
    public static /* synthetic */ class d {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            a = iArr;
            try {
                iArr[ImageView.ScaleType.FIT_CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[ImageView.ScaleType.FIT_START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[ImageView.ScaleType.FIT_END.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[ImageView.ScaleType.FIT_XY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private boolean C() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 922, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        RectF rect = E(F());
        if (rect == null) {
            return false;
        }
        float height = rect.height();
        float width = rect.width();
        float deltaX = 0.0f;
        float deltaY = 0.0f;
        int viewHeight = H(this.p3);
        if (height <= ((float) viewHeight)) {
            switch (d.a[this.T4.ordinal()]) {
                case 2:
                    deltaY = -rect.top;
                    break;
                case 3:
                    deltaY = (((float) viewHeight) - height) - rect.top;
                    break;
                default:
                    deltaY = ((((float) viewHeight) - height) / 2.0f) - rect.top;
                    break;
            }
            this.Q4 = 2;
        } else {
            float f2 = rect.top;
            if (f2 > 0.0f) {
                this.Q4 = 0;
                deltaY = -f2;
            } else {
                float f3 = rect.bottom;
                if (f3 < ((float) viewHeight)) {
                    this.Q4 = 1;
                    deltaY = ((float) viewHeight) - f3;
                } else {
                    this.Q4 = -1;
                }
            }
        }
        int viewWidth = I(this.p3);
        if (width <= ((float) viewWidth)) {
            switch (d.a[this.T4.ordinal()]) {
                case 2:
                    deltaX = -rect.left;
                    break;
                case 3:
                    deltaX = (((float) viewWidth) - width) - rect.left;
                    break;
                default:
                    deltaX = ((((float) viewWidth) - width) / 2.0f) - rect.left;
                    break;
            }
            this.P4 = 2;
        } else {
            float f4 = rect.left;
            if (f4 > 0.0f) {
                this.P4 = 0;
                deltaX = -f4;
            } else {
                float f5 = rect.right;
                if (f5 < ((float) viewWidth)) {
                    deltaX = ((float) viewWidth) - f5;
                    this.P4 = 1;
                } else {
                    this.P4 = -1;
                }
            }
        }
        this.C4.postTranslate(deltaX, deltaY);
        return true;
    }

    private int I(ImageView imageView) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{imageView}, this, changeQuickRedirect, false, 923, new Class[]{ImageView.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (imageView.getWidth() - imageView.getPaddingLeft()) - imageView.getPaddingRight();
    }

    private int H(ImageView imageView) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{imageView}, this, changeQuickRedirect, false, 924, new Class[]{ImageView.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (imageView.getHeight() - imageView.getPaddingTop()) - imageView.getPaddingBottom();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void A() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 925(0x39d, float:1.296E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.base.views.photoview.a$f r1 = r0.O4
            if (r1 == 0) goto L_0x0021
            r1.a()
            r1 = 0
            r0.O4 = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.views.photoview.a.A():void");
    }

    /* compiled from: AlbumPhotoViewAttacher */
    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        private final float c;
        private final float d;
        private final long f = System.currentTimeMillis();
        private final float q;
        private final float x;

        public e(float currentZoom, float targetZoom, float focalX, float focalY) {
            this.c = focalX;
            this.d = focalY;
            this.q = currentZoom;
            this.x = targetZoom;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 936, new Class[0], Void.TYPE).isSupported) {
                float t = a();
                float f2 = this.q;
                a.this.U4.onScale((f2 + ((this.x - f2) * t)) / a.this.M(), this.c, this.d);
                if (t < 1.0f) {
                    b.a(a.this.p3, this);
                }
            }
        }

        private float a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 937, new Class[0], Float.TYPE);
            if (proxy.isSupported) {
                return ((Float) proxy.result).floatValue();
            }
            return a.this.y.getInterpolation(Math.min(1.0f, (((float) (System.currentTimeMillis() - this.f)) * 1.0f) / ((float) a.this.z)));
        }
    }

    /* compiled from: AlbumPhotoViewAttacher */
    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        private final OverScroller c;
        private int d;
        private int f;

        public f(Context context) {
            this.c = new OverScroller(context);
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 938, new Class[0], Void.TYPE).isSupported) {
                this.c.forceFinished(true);
            }
        }

        public void b(int i, int i2, int i3, int i4) {
            int maxX;
            int minX;
            int maxY;
            int minY;
            Object[] objArr = {new Integer(i), new Integer(i2), new Integer(i3), new Integer(i4)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 939, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
                int viewHeight = i2;
                int velocityY = i4;
                int viewWidth = i;
                int velocityX = i3;
                RectF rect = a.this.D();
                if (rect != null) {
                    int startX = Math.round(-rect.left);
                    if (((float) viewWidth) < rect.width()) {
                        minX = 0;
                        maxX = Math.round(rect.width() - ((float) viewWidth));
                    } else {
                        maxX = startX;
                        minX = startX;
                    }
                    int startY = Math.round(-rect.top);
                    if (((float) viewHeight) < rect.height()) {
                        minY = 0;
                        maxY = Math.round(rect.height() - ((float) viewHeight));
                    } else {
                        minY = startY;
                        maxY = startY;
                    }
                    this.d = startX;
                    this.f = startY;
                    if (startX == maxX && startY == maxY) {
                        int i5 = maxY;
                        return;
                    }
                    this.c.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0);
                }
            }
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 940, new Class[0], Void.TYPE).isSupported) {
                if (!this.c.isFinished() && this.c.computeScrollOffset()) {
                    int newX = this.c.getCurrX();
                    int newY = this.c.getCurrY();
                    a.this.C4.postTranslate((float) (this.d - newX), (float) (this.f - newY));
                    a.s(a.this);
                    this.d = newX;
                    this.f = newY;
                    b.a(a.this.p3, this);
                }
            }
        }
    }
}
