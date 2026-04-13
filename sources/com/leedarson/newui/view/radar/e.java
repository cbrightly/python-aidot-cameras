package com.leedarson.newui.view.radar;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.leedarson.newui.view.MiniCloudPlayBackSurfaceViewContainer;
import com.leedarson.newui.view.MiniWebRtcSurfaceViewContainer;
import com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.petterp.floatingx.assist.FxGravity;
import com.petterp.floatingx.assist.helper.c;
import com.petterp.floatingx.listener.control.d;
import io.reactivex.l;
import java.util.concurrent.TimeUnit;

/* compiled from: RadarDragHelper */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean a;
    private boolean b = true;
    d c;
    private View d;
    c.a e;

    public void g(Activity activity, boolean z, FrameLayout attachViewContainer, View attachView) {
        if (!PatchProxy.proxy(new Object[]{activity, new Byte(z ? (byte) 1 : 0), attachViewContainer, attachView}, this, changeQuickRedirect, false, 5417, new Class[]{Activity.class, Boolean.TYPE, FrameLayout.class, View.class}, Void.TYPE).isSupported) {
            try {
                this.a = true;
                this.d = attachView;
                if (attachView.getParent() != null) {
                    ((ViewGroup) this.d.getParent()).removeView(this.d);
                }
                boolean canDrag = true;
                if (attachView instanceof RadarViewLayout) {
                    ((RadarViewLayout) attachView).Q(false);
                } else if ((attachView instanceof SDCardRadarLayoutWrapper) && ((SDCardRadarLayoutWrapper) attachView).getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_COLLPOSE) {
                    canDrag = false;
                    g.a("只可点击");
                }
                c.a aVar = (c.a) ((c.a) ((c.a) ((c.a) ((c.a) ((c.a) ((c.a) ((c.a) ((c.a) c.c().g(false)).l(attachView)).i(false)).h(0.0f, 0.0f, 0.0f, 0.0f)).k(FxGravity.RIGHT_OR_TOP)).f(canDrag ? com.petterp.floatingx.assist.c.Normal : com.petterp.floatingx.assist.c.ClickOnly)).p(new a(attachView))).e(0.0f, 0.0f, 0.0f, 0.0f)).j(true, "FloatRadarWindowHelper");
                this.e = aVar;
                d<FrameLayout> d2 = ((c) aVar.b()).d(attachViewContainer);
                this.c = d2;
                d2.show();
                new Handler(Looper.getMainLooper()).postDelayed(new b(attachView), 10);
            } catch (Exception e2) {
                e2.printStackTrace();
                e("初始化并且显示雷达悬浮窗异常:" + e2.getMessage());
            }
        }
    }

    /* compiled from: RadarDragHelper */
    public class a implements com.petterp.floatingx.listener.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public boolean a = false;
        final /* synthetic */ View b;

        a(View view) {
            this.b = view;
        }

        /* renamed from: com.leedarson.newui.view.radar.e$a$a  reason: collision with other inner class name */
        /* compiled from: RadarDragHelper */
        public class C0121a implements io.reactivex.functions.e<Integer> {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0121a() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5426, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((Integer) obj);
                }
            }

            public void a(Integer num) {
                if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 5425, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                    if (!a.this.a) {
                        View view = a.this.b;
                        if (view instanceof MiniWebRtcSurfaceViewContainer) {
                            ((MiniWebRtcSurfaceViewContainer) view).a();
                        } else if (view instanceof MiniCloudPlayBackSurfaceViewContainer) {
                            ((MiniCloudPlayBackSurfaceViewContainer) view).a();
                        }
                    }
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5424, new Class[0], Void.TYPE).isSupported) {
                g.a("down...");
                l.F(1).l(120, TimeUnit.MILLISECONDS).b0(io.reactivex.android.schedulers.a.a()).J(io.reactivex.android.schedulers.a.a()).X(new C0121a());
            }
        }

        public void d() {
            this.a = false;
        }

        public void a(@NonNull MotionEvent motionEvent, float v, float v1) {
            this.a = true;
        }

        public void b(@NonNull MotionEvent motionEvent) {
        }
    }

    /* compiled from: RadarDragHelper */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ View c;

        b(View view) {
            this.c = view;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5427, new Class[0], Void.TYPE).isSupported) {
                View view = this.c;
                if (view instanceof RadarViewLayout) {
                    e.this.c.e((float) (-RadarViewLayout.d), (float) RadarViewLayout.c);
                } else {
                    boolean z = view instanceof SDCardRadarLayoutWrapper;
                }
            }
        }
    }

    public void f(com.petterp.floatingx.assist.c mode) {
        d dVar;
        if (!PatchProxy.proxy(new Object[]{mode}, this, changeQuickRedirect, false, 5418, new Class[]{com.petterp.floatingx.assist.c.class}, Void.TYPE).isSupported && (dVar = this.c) != null) {
            dVar.a().g(mode);
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5419, new Class[0], Void.TYPE).isSupported) {
            try {
                if (c()) {
                    this.b = true;
                    this.c.hide();
                    e("隐藏雷达悬浮窗,隐藏成功");
                    return;
                }
                e("雷达悬浮窗没展示，不需要隐藏");
            } catch (Exception e2) {
                e("收到web调用隐藏悬浮窗,隐藏异常:" + e2.getMessage());
            }
        }
    }

    public boolean c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5420, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.a) {
            return this.c.f();
        }
        d("悬浮窗未初始化");
        return false;
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5421, new Class[0], Void.TYPE).isSupported) {
            b();
        }
    }

    private void d(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 5422, new Class[]{String.class}, Void.TYPE).isSupported) {
            g.a("float radar:" + msg);
        }
    }

    private void e(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 5423, new Class[]{String.class}, Void.TYPE).isSupported) {
            d(msg);
        }
    }
}
