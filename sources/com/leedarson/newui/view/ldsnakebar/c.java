package com.leedarson.newui.view.ldsnakebar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.leedarson.newui.view.ldsnakebar.BaseTransientBottomBar;
import com.leedarson.newui.view.ldsnakebar.TopSnackbar;
import com.leedarson.serviceimpl.system.notch.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.mengpeng.snackbar.R$color;
import com.mengpeng.snackbar.R$id;
import com.mengpeng.snackbar.R$layout;
import com.tutk.IOTC.AVIOCTRLDEFs;

/* compiled from: SnackbarTopUtils */
public class c {
    private static TopSnackbar a;
    private static Context b;
    private static c c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static View d;
    private static RelativeLayout e;
    private static ImageView f;
    private static TextView g;
    private static TextView h;
    private static TextView i;
    private static TopSnackbar.SnackbarLayout j;
    private static View k;
    private static int l = 50;

    public static c c(Context context, View view, int viewHeight) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, view, new Integer(viewHeight)}, (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.E_CMD_AVIO_CTRL_SESSION_MODE_REQ, new Class[]{Context.class, View.class, Integer.TYPE}, c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        b = context;
        k = view;
        l = viewHeight;
        TopSnackbar topSnackbar = a;
        if (topSnackbar != null) {
            if (topSnackbar.k()) {
                a.f();
            }
            a = null;
        }
        if (c != null) {
            c = null;
        }
        c = new c();
        TopSnackbar s = TopSnackbar.s(((Activity) context).getWindow().getDecorView(), "", -1);
        a = s;
        TopSnackbar.SnackbarLayout snackbarLayout = (TopSnackbar.SnackbarLayout) s.h();
        j = snackbarLayout;
        int paddingLeft = snackbarLayout.getPaddingLeft();
        int paddingRight = j.getPaddingRight();
        j.setPadding(0, 0, 0, 0);
        if (view == null) {
            View inflate = LayoutInflater.from(b).inflate(R$layout.snackbar_view, (ViewGroup) null);
            d = inflate;
            e = (RelativeLayout) inflate.findViewById(R$id.snackbar_container);
            f = (ImageView) d.findViewById(R$id.snackbar_icon);
            g = (TextView) d.findViewById(R$id.snackbar_content);
            h = (TextView) d.findViewById(R$id.snackbar_btn1);
            i = (TextView) d.findViewById(R$id.snackbar_btn2);
            e.setBackgroundColor(ContextCompat.getColor(b, R$color.toastDefaultColor));
            e.setPadding(paddingLeft, 0, paddingRight, 0);
        }
        return c;
    }

    public c g(int duration) {
        Object[] objArr = {new Integer(duration)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5381, new Class[]{Integer.TYPE}, c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        a.n(duration);
        return c;
    }

    public c a(BaseTransientBottomBar.l<TopSnackbar> callback) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 5386, new Class[]{BaseTransientBottomBar.l.class}, c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        a.c(callback);
        return c;
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5387, new Class[0], Void.TYPE).isSupported) {
            if (b != null) {
                h();
                return;
            }
            throw new NullPointerException("执行build之前，请务必先调用create方法...");
        }
    }

    @SuppressLint({"RestrictedApi"})
    private void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5388, new Class[0], Void.TYPE).isSupported) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, e(b, (float) l));
            params.gravity = 40;
            params.topMargin = b.b(b);
            View view = k;
            if (view == null) {
                j.addView(d, 1, params);
            } else {
                int height = view.getHeight();
                Log.d("TAG", "height:" + height);
                j.addView(k, 1, params);
                j.setBackground(k.getBackground());
                ViewGroup.LayoutParams snapParams = j.getLayoutParams();
                snapParams.height = b.b(b) + e(b, 70.0f);
                j.setLayoutParams(snapParams);
            }
            a.p();
        }
    }

    private static int e(Context context, float dpValue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, new Float(dpValue)}, (Object) null, changeQuickRedirect, true, 5389, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 5390, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        TopSnackbar topSnackbar = a;
        if (topSnackbar != null) {
            return topSnackbar.j();
        }
        return false;
    }

    public static void d() {
        TopSnackbar topSnackbar;
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 5392, new Class[0], Void.TYPE).isSupported && (topSnackbar = a) != null && topSnackbar.j()) {
            a.f();
            a = null;
        }
    }
}
