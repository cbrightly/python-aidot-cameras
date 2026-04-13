package com.petterp.floatingx.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.didichuxing.doraemonkit.util.SystemUtil;
import java.util.Iterator;
import java.util.Locale;
import kotlin.collections.g0;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.t;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: FxScreenExt.kt */
public final class FxScreenExtKt {
    private static int a;
    private static int b;
    @NotNull
    private static final String c;

    public static final int e(@NotNull Context $this$realScreenHeight) {
        k.e($this$realScreenHeight, "<this>");
        Object systemService = $this$realScreenHeight.getSystemService("window");
        if (systemService != null) {
            Display display = ((WindowManager) systemService).getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            display.getRealMetrics(dm);
            return dm.heightPixels;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }

    public static final int f(@NotNull Context $this$screenHeight) {
        k.e($this$screenHeight, "<this>");
        Object systemService = $this$screenHeight.getSystemService("window");
        if (systemService != null) {
            Display display = ((WindowManager) systemService).getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            return dm.heightPixels;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }

    public static final int g(@NotNull Activity $this$statusBarHeight) {
        k.e($this$statusBarHeight, "<this>");
        try {
            if (Build.VERSION.SDK_INT >= 24 && $this$statusBarHeight.isInMultiWindowMode()) {
                return 0;
            }
            return $this$statusBarHeight.getResources().getDimensionPixelSize($this$statusBarHeight.getResources().getIdentifier("status_bar_height", "dimen", "android"));
        } catch (Exception e) {
            return 0;
        }
    }

    public static final int a(@NotNull Activity $this$navigationBarHeight) {
        k.e($this$navigationBarHeight, "<this>");
        int newScreenHeight = f($this$navigationBarHeight);
        if (newScreenHeight == a) {
            return b;
        }
        a = newScreenHeight;
        if (Build.VERSION.SDK_INT >= 30) {
            int d = d($this$navigationBarHeight);
            b = d;
            return d;
        }
        n<Integer, Integer> c2 = c($this$navigationBarHeight);
        int showFromAndroidStates = c2.component1().intValue();
        int height = c2.component2().intValue();
        int newNavigationBarHeight = 0;
        switch (showFromAndroidStates) {
            case 0:
                return 0;
            case 1:
                b = height;
                return height;
            default:
                boolean isShow = checkNavigationBarShow($this$navigationBarHeight) || k($this$navigationBarHeight) == 0;
                int realSize = e($this$navigationBarHeight);
                if (isShow && realSize != newScreenHeight) {
                    newNavigationBarHeight = b(newScreenHeight, realSize, $this$navigationBarHeight);
                }
                b = newNavigationBarHeight;
                return newNavigationBarHeight;
        }
    }

    private static final n<Integer, Integer> c(Activity activity) {
        try {
            View decorView = activity.getWindow().getDecorView();
            ViewGroup vp = decorView instanceof ViewGroup ? (ViewGroup) decorView : null;
            if (vp == null) {
                return t.a(-1, 0);
            }
            Iterator it = kotlin.ranges.n.l(0, vp.getChildCount()).iterator();
            while (it.hasNext()) {
                View childAt = vp.getChildAt(((g0) it).nextInt());
                Integer id = childAt == null ? null : Integer.valueOf(childAt.getId());
                if (id != null && id.intValue() == 16908336) {
                    return t.a(1, Integer.valueOf(vp.findViewById(id.intValue()).getHeight()));
                }
            }
            return t.a(0, 0);
        } catch (Exception e) {
            return t.a(-1, 0);
        }
    }

    static {
        String str = Build.BRAND;
        k.d(str, "BRAND");
        Locale locale = Locale.ROOT;
        k.d(locale, "ROOT");
        String lowerCase = str.toLowerCase(locale);
        k.d(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        c = lowerCase;
    }

    private static final boolean r() {
        String str = Build.MANUFACTURER;
        k.d(str, "MANUFACTURER");
        Locale locale = Locale.ROOT;
        k.d(locale, "ROOT");
        String lowerCase = str.toLowerCase(locale);
        k.d(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        return k.a(lowerCase, SystemUtil.PHONE_XIAOMI);
    }

    private static final boolean q() {
        return x.S(c, SystemUtil.PHONE_VIVO, false, 2, (Object) null);
    }

    private static final boolean n() {
        String str = c;
        return x.S(str, SystemUtil.PHONE_OPPO, false, 2, (Object) null) || x.S(str, "realme", false, 2, (Object) null);
    }

    private static final boolean j() {
        String str = c;
        return x.S(str, "huawei", false, 2, (Object) null) || x.S(str, "honor", false, 2, (Object) null);
    }

    private static final boolean m() {
        return x.S(c, "oneplus", false, 2, (Object) null);
    }

    private static final boolean o() {
        return x.S(c, SystemUtil.PHONE_SAMSUNG, false, 2, (Object) null);
    }

    private static final boolean p() {
        return x.S(c, "smartisan", false, 2, (Object) null);
    }

    private static final boolean l() {
        return x.S(c, "nokia", false, 2, (Object) null);
    }

    private static final boolean i() {
        return x.S(c, "google", false, 2, (Object) null);
    }

    @SuppressLint({"NewApi"})
    private static final int d(Context context) {
        Object systemService = context.getSystemService("window");
        if (systemService != null) {
            WindowMetrics windowMetrics = ((WindowManager) systemService).getCurrentWindowMetrics();
            k.d(windowMetrics, "wm.currentWindowMetrics");
            WindowInsets windowInsets = windowMetrics.getWindowInsets();
            k.d(windowInsets, "windowMetrics.windowInsets");
            windowInsets.getInsets(WindowInsets.Type.navigationBars());
            Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
            k.d(insets, "windowInsets.getInsetsIgnoringVisibility(typeMask)");
            return insets.bottom;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }

    private static final boolean checkNavigationBarShow(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Object invoke = systemPropertiesClass.getMethod("get", new Class[]{String.class}).invoke(systemPropertiesClass, new Object[]{"qemu.hw.mainkeys"});
            if (invoke != null) {
                String navBarOverride = (String) invoke;
                int navigationBarIsMin = Settings.Global.getInt(context.getContentResolver(), "navigationbar_is_min", 0);
                if (!k.a("1", navBarOverride)) {
                    if (1 != navigationBarIsMin) {
                        if (k.a("0", navBarOverride)) {
                            return true;
                        }
                        return hasNavigationBar;
                    }
                }
                return false;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
        } catch (Exception e) {
            return hasNavigationBar;
        }
    }

    private static final int b(int screenSize, int realSize, Context context) {
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId <= 0) {
            return 0;
        }
        int height = context.getResources().getDimensionPixelSize(resourceId);
        if (realSize - screenSize > height - 10) {
            return height;
        }
        return 0;
    }

    private static final int k(Context context) {
        if (q()) {
            return x(context);
        }
        if (n()) {
            return u(context);
        }
        if (r()) {
            return y(context);
        }
        if (j()) {
            return h(context);
        }
        if (m()) {
            return t(context);
        }
        if (o()) {
            return v(context);
        }
        if (p()) {
            return w(context);
        }
        if (l()) {
            return s(context);
        }
        if (i()) {
            return 0;
        }
        return -1;
    }

    private static final int x(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "navigation_gesture_on", 0);
    }

    private static final int u(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "hide_navigationbar_enable", 0);
    }

    private static final int y(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0);
    }

    private static final int h(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "navigationbar_is_min", 0);
    }

    private static final int t(Context context) {
        int result = Settings.Secure.getInt(context.getContentResolver(), "navigation_mode", 0);
        if (result != 2 || Settings.System.getInt(context.getContentResolver(), "buttons_show_on_screen_navkeys", 0) == 0) {
            return result;
        }
        return 0;
    }

    private static final int v(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "navigationbar_hide_bar_enabled", 0);
    }

    private static final int w(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "navigationbar_trigger_mode", 0);
    }

    private static final int s(Context context) {
        if ((Settings.Secure.getInt(context.getContentResolver(), "swipe_up_to_switch_apps_enabled", 0) == 0 && Settings.System.getInt(context.getContentResolver(), "navigation_bar_can_hiden", 0) == 0) ? false : true) {
            return 1;
        }
        return 0;
    }
}
