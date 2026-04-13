package com.leedarson.view.easypopup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.Transition;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.widget.PopupWindowCompat;
import com.leedarson.view.easypopup.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: BasePopup */
public abstract class a<T extends a> implements PopupWindow.OnDismissListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Transition A4;
    private boolean B4 = true;
    /* access modifiers changed from: private */
    public View C4;
    /* access modifiers changed from: private */
    public int D4 = 2;
    /* access modifiers changed from: private */
    public int E4 = 1;
    /* access modifiers changed from: private */
    public int F4;
    /* access modifiers changed from: private */
    public int G4;
    private int H4 = 0;
    private int I4 = 1;
    /* access modifiers changed from: private */
    public boolean J4 = false;
    /* access modifiers changed from: private */
    public boolean K4 = false;
    /* access modifiers changed from: private */
    public boolean L4 = false;
    /* access modifiers changed from: private */
    public d M4;
    private int a1;
    private boolean a2;
    /* access modifiers changed from: private */
    public PopupWindow c;
    private Context d;
    private View f;
    /* access modifiers changed from: private */
    public int p0 = -2;
    private PopupWindow.OnDismissListener p1;
    private float p2 = 0.7f;
    @ColorInt
    private int p3 = ViewCompat.MEASURED_STATE_MASK;
    @NonNull
    private ViewGroup p4;
    private int q;
    private boolean x = true;
    private boolean y = true;
    /* access modifiers changed from: private */
    public int z = -2;
    private Transition z4;

    /* compiled from: BasePopup */
    public interface d {
        void a(a aVar, int i, int i2, int i3, int i4);
    }

    public abstract void D();

    public abstract void G(View view, T t);

    static /* synthetic */ void f(a x0, int i, int x2, View view, int x4, int x5, int x6, int x7) {
        Object[] objArr = {x0, new Integer(i), new Integer(x2), view, new Integer(x4), new Integer(x5), new Integer(x6), new Integer(x7)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11899, new Class[]{a.class, cls, cls, View.class, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            int x1 = i;
            View x3 = view;
            x0.T(x1, x2, x3, x4, x5, x6, x7);
        }
    }

    public T N() {
        return this;
    }

    public T p() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11839, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (this.c == null) {
            this.c = new PopupWindow();
        }
        J();
        E();
        L(this.f);
        int i = this.a1;
        if (i != 0) {
            this.c.setAnimationStyle(i);
        }
        F();
        this.c.setOnDismissListener(this);
        if (Build.VERSION.SDK_INT >= 23) {
            Transition transition = this.z4;
            if (transition != null) {
                this.c.setEnterTransition(transition);
            }
            Transition transition2 = this.A4;
            if (transition2 != null) {
                this.c.setExitTransition(transition2);
            }
        }
        return N();
    }

    private void E() {
        Context context;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11840, new Class[0], Void.TYPE).isSupported) {
            if (this.f == null) {
                if (this.q == 0 || (context = this.d) == null) {
                    throw new IllegalArgumentException("The content view is null,the layoutId=" + this.q + ",context=" + this.d);
                }
                this.f = LayoutInflater.from(context).inflate(this.q, (ViewGroup) null);
            }
            this.c.setContentView(this.f);
            int i = this.z;
            if (i > 0 || i == -2 || i == -1) {
                this.c.setWidth(i);
            } else {
                this.c.setWidth(-2);
            }
            int i2 = this.p0;
            if (i2 > 0 || i2 == -2 || i2 == -1) {
                this.c.setHeight(i2);
            } else {
                this.c.setHeight(-2);
            }
            I();
            M();
            this.c.setInputMethodMode(this.H4);
            this.c.setSoftInputMode(this.I4);
        }
    }

    private void F() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11841, new Class[0], Void.TYPE).isSupported) {
            if (!this.B4) {
                this.c.setFocusable(true);
                this.c.setOutsideTouchable(false);
                this.c.setBackgroundDrawable((Drawable) null);
                this.c.getContentView().setFocusable(true);
                this.c.getContentView().setFocusableInTouchMode(true);
                this.c.getContentView().setOnKeyListener(new C0197a());
                this.c.setTouchInterceptor(new b());
                return;
            }
            this.c.setFocusable(this.x);
            this.c.setOutsideTouchable(this.y);
            this.c.setBackgroundDrawable(new ColorDrawable(0));
        }
    }

    /* renamed from: com.leedarson.view.easypopup.a$a  reason: collision with other inner class name */
    /* compiled from: BasePopup */
    public class C0197a implements View.OnKeyListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0197a() {
        }

        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, new Integer(keyCode), keyEvent}, this, changeQuickRedirect, false, 11900, new Class[]{View.class, Integer.TYPE, KeyEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (keyCode != 4) {
                return false;
            }
            a.this.c.dismiss();
            return true;
        }
    }

    /* compiled from: BasePopup */
    public class b implements View.OnTouchListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 11901, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (event.getAction() == 0 && (x < 0 || x >= a.this.z || y < 0 || y >= a.this.p0)) {
                Log.d("EasyPopup", "onTouch outside:mWidth=" + a.this.z + ",mHeight=" + a.this.p0);
                return true;
            } else if (event.getAction() != 4) {
                return false;
            } else {
                Log.d("EasyPopup", "onTouch outside event:mWidth=" + a.this.z + ",mHeight=" + a.this.p0);
                return true;
            }
        }
    }

    public void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11842, new Class[0], Void.TYPE).isSupported) {
            D();
        }
    }

    public void L(View contentView) {
        if (!PatchProxy.proxy(new Object[]{contentView}, this, changeQuickRedirect, false, 11843, new Class[]{View.class}, Void.TYPE).isSupported) {
            G(contentView, N());
        }
    }

    public void K() {
    }

    private void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11844, new Class[0], Void.TYPE).isSupported) {
            View contentView = z();
            if (this.z <= 0 || this.p0 <= 0) {
                contentView.measure(0, 0);
                if (this.z <= 0) {
                    this.z = contentView.getMeasuredWidth();
                }
                if (this.p0 <= 0) {
                    this.p0 = contentView.getMeasuredHeight();
                }
            }
        }
    }

    /* compiled from: BasePopup */
    public class c implements ViewTreeObserver.OnGlobalLayoutListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onGlobalLayout() {
            int i = 0;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11902, new Class[0], Void.TYPE).isSupported) {
                a.this.z().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                a aVar = a.this;
                int unused = aVar.z = aVar.z().getWidth();
                a aVar2 = a.this;
                int unused2 = aVar2.p0 = aVar2.z().getHeight();
                boolean unused3 = a.this.K4 = true;
                boolean unused4 = a.this.J4 = false;
                if (a.this.M4 != null) {
                    d k = a.this.M4;
                    a aVar3 = a.this;
                    int b = aVar3.z;
                    int g = a.this.p0;
                    int width = a.this.C4 == null ? 0 : a.this.C4.getWidth();
                    if (a.this.C4 != null) {
                        i = a.this.C4.getHeight();
                    }
                    k.a(aVar3, b, g, width, i);
                }
                if (a.this.H() && a.this.L4) {
                    a aVar4 = a.this;
                    a.f(aVar4, aVar4.z, a.this.p0, a.this.C4, a.this.D4, a.this.E4, a.this.F4, a.this.G4);
                }
            }
        }
    }

    private void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11845, new Class[0], Void.TYPE).isSupported) {
            z().getViewTreeObserver().addOnGlobalLayoutListener(new c());
        }
    }

    private void T(int i, int i2, @NonNull View view, int i3, int i4, int i5, int i6) {
        Object[] objArr = {new Integer(i), new Integer(i2), view, new Integer(i3), new Integer(i4), new Integer(i5), new Integer(i6)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11846, new Class[]{cls, cls, View.class, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            int height = i2;
            int x2 = i5;
            int yGravity = i3;
            int width = i;
            View anchor = view;
            int y2 = i6;
            int xGravity = i4;
            if (this.c != null) {
                this.c.update(anchor, s(anchor, xGravity, width, x2), t(anchor, yGravity, height, y2), width, height);
            }
        }
    }

    public T O(Context context, @LayoutRes int layoutId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Integer(layoutId)}, this, changeQuickRedirect, false, 11850, new Class[]{Context.class, Integer.TYPE}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        this.d = context;
        this.f = null;
        this.q = layoutId;
        return N();
    }

    public T Q(boolean focusable) {
        Object[] objArr = {new Byte(focusable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 11862, new Class[]{Boolean.TYPE}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        this.x = focusable;
        return N();
    }

    public T P(boolean focusAndOutsideEnable) {
        Object[] objArr = {new Byte(focusAndOutsideEnable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 11864, new Class[]{Boolean.TYPE}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        this.B4 = focusAndOutsideEnable;
        return N();
    }

    private void u(boolean isAtAnchorView) {
        Object[] objArr = {new Byte(isAtAnchorView ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11874, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (this.L4 != isAtAnchorView) {
                this.L4 = isAtAnchorView;
            }
            if (this.c == null) {
                p();
            }
        }
    }

    public void R(@NonNull View anchor, int vertGravity, int horizGravity) {
        Object[] objArr = {anchor, new Integer(vertGravity), new Integer(horizGravity)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11881, new Class[]{View.class, cls, cls}, Void.TYPE).isSupported) {
            S(anchor, vertGravity, horizGravity, 0, 0);
        }
    }

    public void S(@NonNull View anchor, int vertGravity, int horizGravity, int x2, int y2) {
        Object[] objArr = {anchor, new Integer(vertGravity), new Integer(horizGravity), new Integer(x2), new Integer(y2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {View.class, cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11882, clsArr, Void.TYPE).isSupported) {
            u(true);
            this.C4 = anchor;
            this.F4 = x2;
            this.G4 = y2;
            this.D4 = vertGravity;
            this.E4 = horizGravity;
            B();
            int x3 = s(anchor, horizGravity, this.z, this.F4);
            int y3 = t(anchor, vertGravity, this.p0, this.G4);
            if (this.J4) {
                M();
            }
            PopupWindowCompat.showAsDropDown(this.c, anchor, x3, y3, 0);
        }
    }

    private int t(View anchor, int vertGravity, int measuredH, int y2) {
        Object[] objArr = {anchor, new Integer(vertGravity), new Integer(measuredH), new Integer(y2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11883, new Class[]{View.class, cls, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        switch (vertGravity) {
            case 0:
                return y2 - ((anchor.getHeight() / 2) + (measuredH / 2));
            case 1:
                return y2 - (anchor.getHeight() + measuredH);
            case 3:
                return y2 - anchor.getHeight();
            case 4:
                return y2 - measuredH;
            default:
                return y2;
        }
    }

    private int s(View anchor, int horizGravity, int measuredW, int x2) {
        Object[] objArr = {anchor, new Integer(horizGravity), new Integer(measuredW), new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11884, new Class[]{View.class, cls, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        switch (horizGravity) {
            case 0:
                return x2 + ((anchor.getWidth() / 2) - (measuredW / 2));
            case 1:
                return x2 - measuredW;
            case 2:
                return x2 + anchor.getWidth();
            case 4:
                return x2 - (measuredW - anchor.getWidth());
            default:
                return x2;
        }
    }

    private void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11887, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 18 && this.a2) {
                ViewGroup viewGroup = this.p4;
                if (viewGroup != null) {
                    r(viewGroup);
                } else if (z() != null && z().getContext() != null && (z().getContext() instanceof Activity)) {
                    q((Activity) z().getContext());
                }
            }
        }
    }

    @RequiresApi(api = 18)
    private void q(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 11888, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            Drawable dimDrawable = new ColorDrawable(this.p3);
            dimDrawable.setBounds(0, 0, parent.getWidth(), parent.getHeight());
            dimDrawable.setAlpha((int) (this.p2 * 255.0f));
            parent.getOverlay().add(dimDrawable);
        }
    }

    @RequiresApi(api = 18)
    private void r(ViewGroup dimView) {
        if (!PatchProxy.proxy(new Object[]{dimView}, this, changeQuickRedirect, false, 11889, new Class[]{ViewGroup.class}, Void.TYPE).isSupported) {
            Drawable dimDrawable = new ColorDrawable(this.p3);
            dimDrawable.setBounds(0, 0, dimView.getWidth(), dimView.getHeight());
            dimDrawable.setAlpha((int) (this.p2 * 255.0f));
            dimView.getOverlay().add(dimDrawable);
        }
    }

    private void v() {
        Activity activity;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11890, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 18 && this.a2) {
                ViewGroup viewGroup = this.p4;
                if (viewGroup != null) {
                    x(viewGroup);
                } else if (z() != null && (activity = (Activity) z().getContext()) != null) {
                    w(activity);
                }
            }
        }
    }

    @RequiresApi(api = 18)
    private void w(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 11891, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            ((ViewGroup) activity.getWindow().getDecorView().getRootView()).getOverlay().clear();
        }
    }

    @RequiresApi(api = 18)
    private void x(ViewGroup dimView) {
        if (!PatchProxy.proxy(new Object[]{dimView}, this, changeQuickRedirect, false, 11892, new Class[]{ViewGroup.class}, Void.TYPE).isSupported) {
            dimView.getOverlay().clear();
        }
    }

    public View z() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11893, new Class[0], View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        PopupWindow popupWindow = this.c;
        if (popupWindow != null) {
            return popupWindow.getContentView();
        }
        return null;
    }

    public PopupWindow A() {
        return this.c;
    }

    public boolean H() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11894, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        PopupWindow popupWindow = this.c;
        return popupWindow != null && popupWindow.isShowing();
    }

    public void y() {
        PopupWindow popupWindow;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11896, new Class[0], Void.TYPE).isSupported && (popupWindow = this.c) != null) {
            popupWindow.dismiss();
        }
    }

    public void onDismiss() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11897, new Class[0], Void.TYPE).isSupported) {
            C();
        }
    }

    private void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11898, new Class[0], Void.TYPE).isSupported) {
            PopupWindow.OnDismissListener onDismissListener = this.p1;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
            v();
            PopupWindow popupWindow = this.c;
            if (popupWindow != null && popupWindow.isShowing()) {
                this.c.dismiss();
            }
            K();
        }
    }
}
