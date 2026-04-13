package com.petterp.floatingx.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.core.app.NotificationCompat;
import com.petterp.floatingx.assist.FxGravity;
import com.petterp.floatingx.assist.helper.b;
import com.petterp.floatingx.listener.a;
import com.petterp.floatingx.listener.d;
import com.petterp.floatingx.util.c;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressLint({"ViewConstructor"})
/* compiled from: FxManagerView.kt */
public final class FxManagerView extends FrameLayout implements View.OnLayoutChangeListener {
    @Nullable
    private View _childFxView;
    @NotNull
    private final FxClickHelper clickHelper;
    @NotNull
    private final c configHelper;
    private b helper;
    @NotNull
    private final b restoreHelper;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FxManagerView(@NotNull Context context) {
        this(context, (AttributeSet) null, 2, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FxManagerView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FxManagerView(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        k.e(context, "context");
        this.clickHelper = new FxClickHelper();
        this.restoreHelper = new b();
        this.configHelper = new c();
    }

    @Nullable
    public final View getChildFxView() {
        return this._childFxView;
    }

    public final /* synthetic */ FxManagerView init$floatingx_release(b config) {
        k.e(config, "config");
        this.helper = config;
        initView();
        return this;
    }

    private final void initView() {
        View inflateLayoutView = inflateLayoutView();
        if (inflateLayoutView == null) {
            inflateLayoutView = inflateLayoutId();
        }
        this._childFxView = inflateLayoutView;
        FxClickHelper fxClickHelper = this.clickHelper;
        b bVar = this.helper;
        if (bVar != null) {
            fxClickHelper.b(bVar);
            b bVar2 = this.restoreHelper;
            b bVar3 = this.helper;
            if (bVar3 != null) {
                bVar2.d(bVar3);
                c cVar = this.configHelper;
                Context context = getContext();
                k.d(context, "context");
                b bVar4 = this.helper;
                if (bVar4 != null) {
                    cVar.g(context, bVar4);
                    if (this._childFxView != null) {
                        initLocation();
                        updateDisplayMode$floatingx_release();
                        setBackgroundColor(0);
                        return;
                    }
                    throw new IllegalStateException("initFxView -> Error,check your layoutId or layoutView.".toString());
                }
                k.t("helper");
                throw null;
            }
            k.t("helper");
            throw null;
        }
        k.t("helper");
        throw null;
    }

    private final View inflateLayoutView() {
        b bVar = this.helper;
        if (bVar != null) {
            View view = bVar.b;
            if (view == null) {
                return null;
            }
            if (bVar != null) {
                c cVar = bVar.y;
                if (cVar != null) {
                    cVar.b("fxView-->init, way:[layoutView]");
                }
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                if (lp == null) {
                    lp = new FrameLayout.LayoutParams(-2, -2);
                }
                addView(view, lp);
                return view;
            }
            k.t("helper");
            throw null;
        }
        k.t("helper");
        throw null;
    }

    private final View inflateLayoutId() {
        b bVar = this.helper;
        if (bVar == null) {
            k.t("helper");
            throw null;
        } else if (bVar.a == 0) {
            return null;
        } else {
            if (bVar != null) {
                c cVar = bVar.y;
                if (cVar != null) {
                    cVar.b("fxView-->init, way:[layoutId]");
                }
                Context context = getContext();
                b bVar2 = this.helper;
                if (bVar2 != null) {
                    return FrameLayout.inflate(context, bVar2.a, this);
                }
                k.t("helper");
                throw null;
            }
            k.t("helper");
            throw null;
        }
    }

    private final void initLocation() {
        n<Float, Float> nVar;
        b bVar = this.helper;
        if (bVar != null) {
            a configImpl = bVar.w;
            boolean z = false;
            boolean hasConfig = configImpl == null ? false : configImpl.b();
            b bVar2 = this.helper;
            if (bVar2 != null) {
                FrameLayout.LayoutParams lp = bVar2.e;
                if (lp == null) {
                    lp = new FrameLayout.LayoutParams(-2, -2);
                }
                if (!hasConfig) {
                    b bVar3 = this.helper;
                    if (bVar3 != null) {
                        lp.gravity = bVar3.c.getValue();
                    } else {
                        k.t("helper");
                        throw null;
                    }
                }
                setLayoutParams(lp);
                if (hasConfig) {
                    k.c(configImpl);
                    nVar = t.a(Float.valueOf(configImpl.getX()), Float.valueOf(configImpl.getY()));
                } else {
                    nVar = initDefaultXY();
                }
                float initX = nVar.component1().floatValue();
                float initY = nVar.component2().floatValue();
                if (!(initX == -1.0f)) {
                    setX(initX);
                }
                if (initY == -1.0f) {
                    z = true;
                }
                if (!z) {
                    setY(initY);
                }
                b bVar4 = this.helper;
                if (bVar4 != null) {
                    c cVar = bVar4.y;
                    if (cVar != null) {
                        cVar.b("fxView->initLocation,isHasConfig-(" + hasConfig + "),defaultX-(" + initX + "),defaultY-(" + initY + ')');
                        return;
                    }
                    return;
                }
                k.t("helper");
                throw null;
            }
            k.t("helper");
            throw null;
        }
        k.t("helper");
        throw null;
    }

    private final n<Float, Float> initDefaultXY() {
        b bVar = this.helper;
        if (bVar != null) {
            if (!bVar.t) {
                if (bVar == null) {
                    k.t("helper");
                    throw null;
                } else if (!bVar.c.isDefault()) {
                    b bVar2 = this.helper;
                    if (bVar2 != null) {
                        c cVar = bVar2.y;
                        if (cVar != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("fxView--默认坐标可能初始化异常,如果显示位置异常,请检查您的gravity是否为默认配置，当前gravity:");
                            b bVar3 = this.helper;
                            if (bVar3 != null) {
                                sb.append(bVar3.c);
                                sb.append("。\n如果您要配置gravity,建议您启用辅助定位setEnableAssistDirection(),此方法将更便于定位。");
                                cVar.c(sb.toString());
                            } else {
                                k.t("helper");
                                throw null;
                            }
                        }
                    } else {
                        k.t("helper");
                        throw null;
                    }
                }
            }
            b bVar4 = this.helper;
            if (bVar4 != null) {
                Float valueOf = Float.valueOf(bVar4.h);
                b bVar5 = this.helper;
                if (bVar5 != null) {
                    return t.a(valueOf, Float.valueOf(checkDefaultY(bVar5.g)));
                }
                k.t("helper");
                throw null;
            }
            k.t("helper");
            throw null;
        }
        k.t("helper");
        throw null;
    }

    private final float checkDefaultY(float y) {
        float defaultY = y;
        b bVar = this.helper;
        if (bVar != null) {
            switch (bVar.c.getScope()) {
                case 1:
                    b bVar2 = this.helper;
                    if (bVar2 != null) {
                        return defaultY + ((float) bVar2.B);
                    }
                    k.t("helper");
                    throw null;
                case 3:
                    b bVar3 = this.helper;
                    if (bVar3 != null) {
                        return defaultY - ((float) bVar3.A);
                    }
                    k.t("helper");
                    throw null;
                default:
                    return defaultY;
            }
        } else {
            k.t("helper");
            throw null;
        }
    }

    public boolean onInterceptTouchEvent(@NotNull MotionEvent ev) {
        k.e(ev, "ev");
        boolean intercepted = false;
        switch (ev.getAction()) {
            case 0:
                initTouchDown(ev);
                b bVar = this.helper;
                if (bVar != null) {
                    c cVar = bVar.y;
                    if (cVar != null) {
                        cVar.b("fxView---onInterceptTouchEvent-[down]");
                        break;
                    }
                } else {
                    k.t("helper");
                    throw null;
                }
                break;
            case 1:
            case 3:
                touchToPointerUp(ev, false);
                b bVar2 = this.helper;
                if (bVar2 != null) {
                    c cVar2 = bVar2.y;
                    if (cVar2 != null) {
                        cVar2.b("fxView---onInterceptTouchEvent-[up]");
                        break;
                    }
                } else {
                    k.t("helper");
                    throw null;
                }
                break;
            case 2:
                intercepted = this.configHelper.a(ev);
                b bVar3 = this.helper;
                if (bVar3 != null) {
                    c cVar3 = bVar3.y;
                    if (cVar3 != null) {
                        cVar3.b(k.l("fxView---onInterceptTouchEvent-[move], interceptedTouch-", Boolean.valueOf(intercepted)));
                        break;
                    }
                } else {
                    k.t("helper");
                    throw null;
                }
                break;
        }
        return intercepted;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@NotNull MotionEvent event) {
        k.e(event, NotificationCompat.CATEGORY_EVENT);
        b bVar = this.helper;
        if (bVar != null) {
            com.petterp.floatingx.listener.c cVar = bVar.u;
            if (cVar != null) {
                cVar.b(event);
            }
            switch (event.getActionMasked()) {
                case 0:
                    initTouchDown(event);
                    break;
                case 1:
                case 3:
                case 6:
                    touchToPointerUp$default(this, event, false, 2, (Object) null);
                    break;
                case 2:
                    touchToMove(event);
                    break;
                case 5:
                    touchToPointerDown(event);
                    break;
            }
            return super.onTouchEvent(event);
        }
        k.t("helper");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        b bVar = this.helper;
        if (bVar != null) {
            d dVar = bVar.v;
            if (dVar != null) {
                dVar.c();
            }
            ViewParent parent = getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.addOnLayoutChangeListener(this);
            }
            b bVar2 = this.helper;
            if (bVar2 != null) {
                c cVar = bVar2.y;
                if (cVar != null) {
                    cVar.b("fxView-lifecycle-> onAttachedToWindow");
                    return;
                }
                return;
            }
            k.t("helper");
            throw null;
        }
        k.t("helper");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b bVar = this.helper;
        if (bVar != null) {
            d dVar = bVar.v;
            if (dVar != null) {
                dVar.g();
            }
            ViewParent parent = getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeOnLayoutChangeListener(this);
            }
            b bVar2 = this.helper;
            if (bVar2 != null) {
                c cVar = bVar2.y;
                if (cVar != null) {
                    cVar.b("fxView-lifecycle-> onDetachedFromWindow");
                    return;
                }
                return;
            }
            k.t("helper");
            throw null;
        }
        k.t("helper");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        b bVar = this.helper;
        if (bVar != null) {
            d dVar = bVar.v;
            if (dVar != null) {
                dVar.a(visibility);
            }
            b bVar2 = this.helper;
            if (bVar2 != null) {
                c cVar = bVar2.y;
                if (cVar != null) {
                    cVar.b("fxView-lifecycle-> onWindowVisibilityChanged");
                    return;
                }
                return;
            }
            k.t("helper");
            throw null;
        }
        k.t("helper");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        k.e(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        b bVar = this.helper;
        if (bVar != null) {
            c cVar = bVar.y;
            if (cVar != null) {
                cVar.b("fxView--lifecycle-> onConfigurationChanged--->");
            }
            if (this.restoreHelper.h(newConfig)) {
                float x = getX();
                float y = getY();
                this.restoreHelper.g(x, y, this.configHelper);
                b bVar2 = this.helper;
                if (bVar2 != null) {
                    c cVar2 = bVar2.y;
                    if (cVar2 != null) {
                        cVar2.b("fxView--lifecycle-> saveLocation:[x:" + x + ",y:" + y + ']');
                        return;
                    }
                    return;
                }
                k.t("helper");
                throw null;
            }
            return;
        }
        k.t("helper");
        throw null;
    }

    public void setOnClickListener(@Nullable View.OnClickListener l) {
        b bVar = this.helper;
        if (bVar != null) {
            bVar.x = l;
            if (bVar != null) {
                bVar.s = true;
            } else {
                k.t("helper");
                throw null;
            }
        } else {
            k.t("helper");
            throw null;
        }
    }

    private final void initTouchDown(MotionEvent ev) {
        if (!this.configHelper.f()) {
            this.clickHelper.c(getX(), getY());
            this.configHelper.h(ev);
            this.configHelper.s(this);
            this.configHelper.q(true);
            b bVar = this.helper;
            if (bVar != null) {
                com.petterp.floatingx.listener.c cVar = bVar.u;
                if (cVar != null) {
                    cVar.c();
                    return;
                }
                return;
            }
            k.t("helper");
            throw null;
        }
    }

    public static /* synthetic */ void moveLocation$floatingx_release$default(FxManagerView fxManagerView, float f, float f2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        fxManagerView.moveLocation$floatingx_release(f, f2, z);
    }

    public final /* synthetic */ void moveLocation$floatingx_release(float x, float y, boolean useAnimation) {
        float newX = this.configHelper.l(x);
        float newY = this.configHelper.n(y);
        if (useAnimation) {
            moveToLocation(newX, newY);
            return;
        }
        setX(x);
        setY(y);
    }

    public static /* synthetic */ void moveLocationByVector$floatingx_release$default(FxManagerView fxManagerView, float f, float f2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        fxManagerView.moveLocationByVector$floatingx_release(f, f2, z);
    }

    public final /* synthetic */ void moveLocationByVector$floatingx_release(float x, float y, boolean useAnimation) {
        moveLocation$floatingx_release(getX() + x, getY() + y, useAnimation);
    }

    public final /* synthetic */ void restoreLocation$floatingx_release(float x, float y) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            ((FrameLayout.LayoutParams) layoutParams).gravity = FxGravity.DEFAULT.getValue();
            setX(x);
            setY(y);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
    }

    public final /* synthetic */ void moveToEdge$floatingx_release() {
        this.configHelper.q(false);
        n $dstr$x$y = this.configHelper.b(getX(), getY());
        if ($dstr$x$y != null) {
            float x = $dstr$x$y.component1().floatValue();
            float y = $dstr$x$y.component2().floatValue();
            moveToLocation(x, y);
            saveLocationToStorage(x, y);
        }
    }

    public final /* synthetic */ void updateDisplayMode$floatingx_release() {
        b bVar = this.helper;
        if (bVar != null) {
            setClickable(bVar.k != com.petterp.floatingx.assist.c.DisplayOnly);
        } else {
            k.t("helper");
            throw null;
        }
    }

    private final void moveToLocation(float moveX, float moveY) {
        boolean z = true;
        if (moveX == getX()) {
            if (moveY != getY()) {
                z = false;
            }
            if (z) {
                return;
            }
        }
        b bVar = this.helper;
        if (bVar != null) {
            c cVar = bVar.y;
            if (cVar != null) {
                cVar.b("fxView-->moveToEdge---x-(" + getX() + ")，y-(" + getY() + ") ->  moveX-(" + moveX + "),moveY-(" + moveY + ')');
            }
            animate().x(moveX).y(moveY).setDuration(200).start();
            return;
        }
        k.t("helper");
        throw null;
    }

    private final void saveLocationToStorage(float moveX, float moveY) {
        b bVar = this.helper;
        if (bVar == null) {
            k.t("helper");
            throw null;
        } else if (bVar.q) {
            if (bVar != null) {
                a aVar = bVar.w;
                if (aVar == null) {
                    if (bVar != null) {
                        c cVar = bVar.y;
                        if (cVar != null) {
                            cVar.c("fxView-->saveDirection---iFxConfigStorageImpl does not exist, save failed!");
                            return;
                        }
                        return;
                    }
                    k.t("helper");
                    throw null;
                } else if (bVar != null) {
                    if (aVar != null) {
                        aVar.a(moveX, moveY);
                    }
                    b bVar2 = this.helper;
                    if (bVar2 != null) {
                        c cVar2 = bVar2.y;
                        if (cVar2 != null) {
                            cVar2.b("fxView-->saveDirection---x-(" + moveX + ")，y-(" + moveY + ')');
                            return;
                        }
                        return;
                    }
                    k.t("helper");
                    throw null;
                } else {
                    k.t("helper");
                    throw null;
                }
            } else {
                k.t("helper");
                throw null;
            }
        }
    }

    private final void restoreLocation() {
        n<Float, Float> a = this.restoreHelper.a(this.configHelper);
        float x = a.component1().floatValue();
        float y = a.component2().floatValue();
        setX(x);
        setY(y);
        saveLocationToStorage(x, y);
        b bVar = this.helper;
        if (bVar != null) {
            c cVar = bVar.y;
            if (cVar != null) {
                cVar.b("fxView--lifecycle-> restoreLocation:[x:" + x + ",y:" + y + ']');
                return;
            }
            return;
        }
        k.t("helper");
        throw null;
    }

    private final void touchToMove(MotionEvent event) {
        if (this.configHelper.f()) {
            b bVar = this.helper;
            if (bVar == null) {
                k.t("helper");
                throw null;
            } else if (bVar.k == com.petterp.floatingx.assist.c.Normal) {
                updateLocation(event);
            }
        }
    }

    static /* synthetic */ void touchToPointerUp$default(FxManagerView fxManagerView, MotionEvent motionEvent, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        fxManagerView.touchToPointerUp(motionEvent, z);
    }

    private final void touchToPointerUp(MotionEvent event, boolean isFxSelfEvent) {
        if (this.configHelper.i(event)) {
            touchToCancel(isFxSelfEvent);
            return;
        }
        b bVar = this.helper;
        if (bVar != null) {
            c cVar = bVar.y;
            if (cVar != null) {
                cVar.b("fxView---onTouchEvent--ACTION_POINTER_UP---id:" + com.petterp.floatingx.util.b.b(event) + "->");
                return;
            }
            return;
        }
        k.t("helper");
        throw null;
    }

    private final void touchToPointerDown(MotionEvent event) {
        b bVar = this.helper;
        if (bVar != null) {
            c cVar = bVar.y;
            if (cVar != null) {
                cVar.b("fxView---onTouchEvent--touchToPointerDown--id:" + event.getPointerId(event.getActionIndex()) + "->");
            }
            if (!this.configHelper.f() && com.petterp.floatingx.util.b.c(event.getX(), 0, Integer.valueOf(getWidth())) && com.petterp.floatingx.util.b.c(event.getY(), 0, Integer.valueOf(getHeight()))) {
                initTouchDown(event);
                return;
            }
            return;
        }
        k.t("helper");
        throw null;
    }

    static /* synthetic */ void touchToCancel$default(FxManagerView fxManagerView, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        fxManagerView.touchToCancel(z);
    }

    private final void touchToCancel(boolean isFxSelfEvent) {
        b bVar = this.helper;
        if (bVar != null) {
            com.petterp.floatingx.listener.c cVar = bVar.u;
            if (cVar != null) {
                cVar.d();
            }
            this.configHelper.p(-1);
            if (isFxSelfEvent) {
                moveToEdge$floatingx_release();
                this.clickHelper.performClick(this);
            }
            b bVar2 = this.helper;
            if (bVar2 != null) {
                c cVar2 = bVar2.y;
                if (cVar2 != null) {
                    cVar2.b("fxView---onTouchEvent---MainTouchCancel->");
                    return;
                }
                return;
            }
            k.t("helper");
            throw null;
        }
        k.t("helper");
        throw null;
    }

    private final void refreshLocation(int w, int h) {
        if (this.configHelper.r(w, h, this)) {
            if (this.restoreHelper.e()) {
                checkOrFixLocation();
            } else if (this.restoreHelper.f()) {
                restoreLocation();
            } else {
                moveToEdge$floatingx_release();
            }
        }
    }

    private final void checkOrFixLocation() {
        moveToLocation(this.configHelper.l(getX()), this.configHelper.n(getY()));
    }

    private final void updateLocation(MotionEvent event) {
        float disX = this.configHelper.m(getX(), event);
        float disY = this.configHelper.o(getY(), event);
        setX(disX);
        setY(disY);
        this.clickHelper.a(disX, disY);
        b bVar = this.helper;
        if (bVar != null) {
            com.petterp.floatingx.listener.c cVar = bVar.u;
            if (cVar != null) {
                cVar.a(event, disX, disY);
            }
            b bVar2 = this.helper;
            if (bVar2 != null) {
                c cVar2 = bVar2.y;
                if (cVar2 != null) {
                    cVar2.d("fxView---scrollListener--drag-event--x(" + disX + ")-y(" + disY + ')');
                    return;
                }
                return;
            }
            k.t("helper");
            throw null;
        }
        k.t("helper");
        throw null;
    }

    public void onLayoutChange(@Nullable View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (v != null) {
            refreshLocation(v.getWidth(), v.getHeight());
        }
    }
}
