package com.leedarson.newui.view.ldsnakebar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.leedarson.R$anim;
import com.leedarson.R$layout;
import com.leedarson.R$styleable;
import com.leedarson.newui.view.ldsnakebar.BaseTransientBottomBar;
import com.leedarson.newui.view.ldsnakebar.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>> {
    static final Handler a = new Handler(Looper.getMainLooper(), new d());
    /* access modifiers changed from: private */
    public static final boolean b;
    public static ChangeQuickRedirect changeQuickRedirect;
    private final ViewGroup c;
    private final Context d;
    final SnackbarBaseLayout e;
    /* access modifiers changed from: private */
    public final m f;
    private int g;
    private List<l<B>> h;
    private final AccessibilityManager i;
    final b.C0120b j = new e();

    public interface m {
        void animateContentIn(int i, int i2);

        void animateContentOut(int i, int i2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface n {
        void onViewAttachedToWindow(View view);

        void onViewDetachedFromWindow(View view);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface o {
        void onLayoutChange(View view, int i, int i2, int i3, int i4);
    }

    public static abstract class l<B> {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void a(B b, int event) {
        }

        public void b(B b) {
        }
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        b = i2 >= 16 && i2 <= 19;
    }

    public class d implements Handler.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public boolean handleMessage(Message message) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 5330, new Class[]{Message.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            switch (message.what) {
                case 0:
                    ((BaseTransientBottomBar) message.obj).q();
                    return true;
                case 1:
                    ((BaseTransientBottomBar) message.obj).i(message.arg1);
                    return true;
                default:
                    return false;
            }
        }
    }

    @SuppressLint({"RestrictedApi"})
    public BaseTransientBottomBar(@NonNull ViewGroup parent, @NonNull View content, @NonNull m contentViewCallback) {
        if (parent == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        } else if (content == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        } else if (contentViewCallback != null) {
            this.c = parent;
            this.f = contentViewCallback;
            Context context = parent.getContext();
            this.d = context;
            d.a(context);
            SnackbarBaseLayout snackbarBaseLayout = (SnackbarBaseLayout) LayoutInflater.from(context).inflate(R$layout.design_layout_topsnackbar, parent, false);
            this.e = snackbarBaseLayout;
            snackbarBaseLayout.addView(content);
            ViewCompat.setAccessibilityLiveRegion(snackbarBaseLayout, 1);
            ViewCompat.setImportantForAccessibility(snackbarBaseLayout, 1);
            ViewCompat.setFitsSystemWindows(snackbarBaseLayout, true);
            ViewCompat.setOnApplyWindowInsetsListener(snackbarBaseLayout, new OnApplyWindowInsetsListener() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v, insets}, this, changeQuickRedirect2, false, 5335, new Class[]{View.class, WindowInsetsCompat.class}, WindowInsetsCompat.class);
                    if (proxy.isSupported) {
                        return (WindowInsetsCompat) proxy.result;
                    }
                    v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), insets.getSystemWindowInsetBottom());
                    return insets;
                }
            });
            this.i = (AccessibilityManager) context.getSystemService("accessibility");
        } else {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        }
    }

    @NonNull
    public B n(int duration) {
        this.g = duration;
        return this;
    }

    @NonNull
    public View h() {
        return this.e;
    }

    public void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5316, new Class[0], Void.TYPE).isSupported) {
            b.c().n(this.g, this.j);
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5317, new Class[0], Void.TYPE).isSupported) {
            g(3);
        }
    }

    /* access modifiers changed from: package-private */
    public void g(int event) {
        Object[] objArr = {new Integer(event)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5318, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            b.c().b(this.j, event);
        }
    }

    @NonNull
    public B c(@NonNull l<B> callback) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5319, new Class[]{l.class}, BaseTransientBottomBar.class);
        if (proxy.isSupported) {
            return (BaseTransientBottomBar) proxy.result;
        }
        if (callback == null) {
            return this;
        }
        if (this.h == null) {
            this.h = new ArrayList();
        }
        this.h.add(callback);
        return this;
    }

    public boolean j() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5321, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : b.c().e(this.j);
    }

    public boolean k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5322, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : b.c().f(this.j);
    }

    public class e implements b.C0120b {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void show() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5336, new Class[0], Void.TYPE).isSupported) {
                Handler handler = BaseTransientBottomBar.a;
                handler.sendMessage(handler.obtainMessage(0, BaseTransientBottomBar.this));
            }
        }

        public void dismiss(int event) {
            if (!PatchProxy.proxy(new Object[]{new Integer(event)}, this, changeQuickRedirect, false, 5337, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                Handler handler = BaseTransientBottomBar.a;
                handler.sendMessage(handler.obtainMessage(1, event, 0, BaseTransientBottomBar.this));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5323, new Class[0], Void.TYPE).isSupported) {
            if (this.e.getParent() == null) {
                ViewGroup.LayoutParams lp = this.e.getLayoutParams();
                if (lp instanceof CoordinatorLayout.LayoutParams) {
                    CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) lp;
                    BaseTransientBottomBar<B>.Behavior behavior = new Behavior();
                    behavior.setStartAlphaSwipeDistance(0.1f);
                    behavior.setEndAlphaSwipeDistance(0.6f);
                    behavior.setSwipeDirection(0);
                    behavior.setListener(new f());
                    clp.setBehavior(behavior);
                    clp.insetEdge = 80;
                }
                this.c.addView(this.e);
            }
            this.e.setOnAttachStateChangeListener(new g());
            if (!ViewCompat.isLaidOut(this.e)) {
                this.e.setOnLayoutChangeListener(new h());
            } else if (o()) {
                d();
            } else {
                m();
            }
        }
    }

    public class f implements SwipeDismissBehavior.OnDismissListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onDismiss(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5338, new Class[]{View.class}, Void.TYPE).isSupported) {
                view.setVisibility(8);
                BaseTransientBottomBar.this.g(0);
            }
        }

        public void onDragStateChanged(int state) {
            Object[] objArr = {new Integer(state)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5339, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                switch (state) {
                    case 0:
                        b.c().l(BaseTransientBottomBar.this.j);
                        return;
                    case 1:
                    case 2:
                        b.c().k(BaseTransientBottomBar.this.j);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public class g implements n {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void onViewAttachedToWindow(View v) {
        }

        public void onViewDetachedFromWindow(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5340, new Class[]{View.class}, Void.TYPE).isSupported) {
                if (BaseTransientBottomBar.this.k()) {
                    BaseTransientBottomBar.a.post(new a());
                }
            }
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5341, new Class[0], Void.TYPE).isSupported) {
                    BaseTransientBottomBar.this.l(3);
                }
            }
        }
    }

    public class h implements o {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void onLayoutChange(View view, int i, int i2, int i3, int i4) {
            Object[] objArr = {view, new Integer(i), new Integer(i2), new Integer(i3), new Integer(i4)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {View.class, cls, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5342, clsArr, Void.TYPE).isSupported) {
                BaseTransientBottomBar.this.e.setOnLayoutChangeListener((o) null);
                if (BaseTransientBottomBar.this.o()) {
                    BaseTransientBottomBar.this.d();
                } else {
                    BaseTransientBottomBar.this.m();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5324, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 12) {
                int viewHeight = -this.e.getHeight();
                if (b) {
                    ViewCompat.offsetTopAndBottom(this.e, viewHeight);
                } else {
                    this.e.setTranslationY((float) viewHeight);
                }
                ValueAnimator animator = new ValueAnimator();
                animator.setIntValues(new int[]{viewHeight, 0});
                animator.setInterpolator(a.b);
                animator.setDuration(250);
                animator.addListener(new i());
                animator.addUpdateListener(new j(viewHeight));
                animator.start();
                return;
            }
            Animation anim = AnimationUtils.loadAnimation(this.e.getContext(), R$anim.design_snackbar_in);
            anim.setInterpolator(a.b);
            anim.setDuration(250);
            anim.setAnimationListener(new k());
            this.e.startAnimation(anim);
        }
    }

    public class i extends AnimatorListenerAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void onAnimationStart(Animator animator) {
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 5343, new Class[]{Animator.class}, Void.TYPE).isSupported) {
                BaseTransientBottomBar.this.f.animateContentIn(70, 180);
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 5344, new Class[]{Animator.class}, Void.TYPE).isSupported) {
                BaseTransientBottomBar.this.m();
            }
        }
    }

    public class j implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        private int c;
        final /* synthetic */ int d;

        j(int i) {
            this.d = i;
            this.c = i;
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 5345, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                int currentAnimatedIntValue = ((Integer) animator.getAnimatedValue()).intValue();
                if (BaseTransientBottomBar.b) {
                    ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.e, currentAnimatedIntValue - this.c);
                } else {
                    BaseTransientBottomBar.this.e.setTranslationY((float) currentAnimatedIntValue);
                }
                this.c = currentAnimatedIntValue;
            }
        }
    }

    public class k implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void onAnimationEnd(Animation animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5346, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                BaseTransientBottomBar.this.m();
            }
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    private void e(int event) {
        if (!PatchProxy.proxy(new Object[]{new Integer(event)}, this, changeQuickRedirect, false, 5325, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 12) {
                ViewCompat.setTranslationY(this.e, 0.0f);
                ValueAnimator animator = new ValueAnimator();
                animator.setIntValues(new int[]{0, -this.e.getHeight()});
                animator.setInterpolator(a.b);
                animator.setDuration(250);
                animator.addListener(new a(event));
                animator.addUpdateListener(new b());
                animator.start();
                return;
            }
            Animation anim = AnimationUtils.loadAnimation(this.e.getContext(), R$anim.design_snackbar_out);
            anim.setInterpolator(a.b);
            anim.setDuration(250);
            anim.setAnimationListener(new c(event));
            this.e.startAnimation(anim);
        }
    }

    public class a extends AnimatorListenerAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        a(int i) {
            this.c = i;
        }

        public void onAnimationStart(Animator animator) {
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 5331, new Class[]{Animator.class}, Void.TYPE).isSupported) {
                BaseTransientBottomBar.this.f.animateContentOut(0, 180);
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 5332, new Class[]{Animator.class}, Void.TYPE).isSupported) {
                BaseTransientBottomBar.this.l(this.c);
            }
        }
    }

    public class b implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        private int c = 0;

        b() {
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 5333, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                int currentAnimatedIntValue = ((Integer) animator.getAnimatedValue()).intValue();
                if (BaseTransientBottomBar.b) {
                    ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.e, currentAnimatedIntValue - this.c);
                } else {
                    BaseTransientBottomBar.this.e.setTranslationY((float) currentAnimatedIntValue);
                }
                this.c = currentAnimatedIntValue;
            }
        }
    }

    public class c implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        c(int i) {
            this.a = i;
        }

        public void onAnimationEnd(Animation animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5334, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                BaseTransientBottomBar.this.l(this.a);
            }
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /* access modifiers changed from: package-private */
    public final void i(int event) {
        Object[] objArr = {new Integer(event)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5326, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (!o() || this.e.getVisibility() != 0) {
                l(event);
            } else {
                e(event);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5327, new Class[0], Void.TYPE).isSupported) {
            b.c().j(this.j);
            List<l<B>> list = this.h;
            if (list != null) {
                for (int i2 = list.size() - 1; i2 >= 0; i2--) {
                    this.h.get(i2).b(this);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void l(int event) {
        if (!PatchProxy.proxy(new Object[]{new Integer(event)}, this, changeQuickRedirect, false, 5328, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            b.c().i(this.j);
            List<l<B>> list = this.h;
            if (list != null) {
                for (int i2 = list.size() - 1; i2 >= 0; i2--) {
                    this.h.get(i2).a(this, event);
                }
            }
            if (Build.VERSION.SDK_INT < 11) {
                this.e.setVisibility(8);
            }
            ViewParent parent = this.e.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5329, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : !this.i.isEnabled();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class SnackbarBaseLayout extends FrameLayout {
        public static ChangeQuickRedirect changeQuickRedirect;
        private o c;
        private n d;

        SnackbarBaseLayout(Context context) {
            this(context, (AttributeSet) null);
        }

        SnackbarBaseLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R$styleable.SnackbarLayout);
            int i = R$styleable.SnackbarLayout_elevation;
            if (a.hasValue(i)) {
                ViewCompat.setElevation(this, (float) a.getDimensionPixelSize(i, 0));
            }
            a.recycle();
            setClickable(true);
        }

        public void onLayout(boolean changed, int i, int i2, int i3, int i4) {
            Object[] objArr = {new Byte(changed ? (byte) 1 : 0), new Integer(i), new Integer(i2), new Integer(i3), new Integer(i4)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Boolean.TYPE, cls, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5349, clsArr, Void.TYPE).isSupported) {
                int l = i;
                int r = i3;
                int t = i2;
                int b = i4;
                super.onLayout(changed, l, t, r, b);
                o oVar = this.c;
                if (oVar != null) {
                    oVar.onLayoutChange(this, l, t, r, b);
                }
            }
        }

        public void onAttachedToWindow() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5350, new Class[0], Void.TYPE).isSupported) {
                super.onAttachedToWindow();
                n nVar = this.d;
                if (nVar != null) {
                    nVar.onViewAttachedToWindow(this);
                }
                ViewCompat.requestApplyInsets(this);
            }
        }

        public void onDetachedFromWindow() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5351, new Class[0], Void.TYPE).isSupported) {
                super.onDetachedFromWindow();
                n nVar = this.d;
                if (nVar != null) {
                    nVar.onViewDetachedFromWindow(this);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void setOnLayoutChangeListener(o onLayoutChangeListener) {
            this.c = onLayoutChangeListener;
        }

        /* access modifiers changed from: package-private */
        public void setOnAttachStateChangeListener(n listener) {
            this.d = listener;
        }
    }

    public final class Behavior extends SwipeDismissBehavior<SnackbarBaseLayout> {
        public static ChangeQuickRedirect changeQuickRedirect;

        Behavior() {
        }

        public /* bridge */ /* synthetic */ boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{coordinatorLayout, view, motionEvent}, this, changeQuickRedirect, false, 5348, new Class[]{CoordinatorLayout.class, View.class, MotionEvent.class}, Boolean.TYPE);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a(coordinatorLayout, (SnackbarBaseLayout) view, motionEvent);
        }

        public boolean canSwipeDismissView(View child) {
            return child instanceof SnackbarBaseLayout;
        }

        public boolean a(CoordinatorLayout parent, SnackbarBaseLayout child, MotionEvent event) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parent, child, event}, this, changeQuickRedirect2, false, 5347, new Class[]{CoordinatorLayout.class, SnackbarBaseLayout.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            switch (event.getActionMasked()) {
                case 0:
                    if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                        b.c().k(BaseTransientBottomBar.this.j);
                        break;
                    }
                    break;
                case 1:
                case 3:
                    b.c().l(BaseTransientBottomBar.this.j);
                    break;
            }
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }
}
