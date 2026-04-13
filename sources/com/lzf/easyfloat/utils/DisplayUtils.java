package com.lzf.easyfloat.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.permission.rom.RomUtils;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DisplayUtils.kt */
public final class DisplayUtils {
    @NotNull
    public static final DisplayUtils INSTANCE = new DisplayUtils();
    @NotNull
    private static final String TAG = "DisplayUtils--->";

    private DisplayUtils() {
    }

    public final int px2dp(@NotNull Context context, float pxVal) {
        k.e(context, "context");
        return (int) ((pxVal / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public final int dp2px(@NotNull Context context, float dpVal) {
        k.e(context, "context");
        return (int) ((dpVal * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public final int px2sp(@NotNull Context context, float pxValue) {
        k.e(context, "context");
        return (int) ((pxValue / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public final int sp2px(@NotNull Context context, float spValue) {
        k.e(context, "context");
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public final int getScreenWidth(@NotNull Context context) {
        k.e(context, "context");
        Object systemService = context.getSystemService("window");
        if (systemService != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager) systemService).getDefaultDisplay().getRealMetrics(metrics);
            if (context.getResources().getConfiguration().orientation == 1) {
                return metrics.widthPixels;
            }
            return metrics.widthPixels - getNavigationBarCurrentHeight(context);
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }

    public final int getScreenHeight(@NotNull Context context) {
        k.e(context, "context");
        return getScreenSize(context).y;
    }

    @NotNull
    public final Point getScreenSize(@NotNull Context context) {
        k.e(context, "context");
        Point point = new Point();
        Point $this$getScreenSize_u24lambda_u2d0 = point;
        Object systemService = context.getSystemService("window");
        if (systemService != null) {
            ((WindowManager) systemService).getDefaultDisplay().getRealSize($this$getScreenSize_u24lambda_u2d0);
            return point;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }

    public final int getStatusBarHeight(@NotNull Context context) {
        k.e(context, "context");
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public final int statusBarHeight(@NotNull View view) {
        k.e(view, "view");
        Context applicationContext = view.getContext().getApplicationContext();
        k.d(applicationContext, "view.context.applicationContext");
        return getStatusBarHeight(applicationContext);
    }

    public final int getNavigationBarHeight(@NotNull Context context) {
        k.e(context, "context");
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public final int getNavigationBarCurrentHeight(@NotNull Context context) {
        k.e(context, "context");
        if (hasNavigationBar(context)) {
            return getNavigationBarHeight(context);
        }
        return 0;
    }

    public final boolean hasNavigationBar(@NotNull Context context) {
        k.e(context, "context");
        if (getNavigationBarHeight(context) == 0) {
            return false;
        }
        RomUtils romUtils = RomUtils.INSTANCE;
        if (romUtils.checkIsHuaweiRom() && isHuaWeiHideNav(context)) {
            return false;
        }
        if (romUtils.checkIsMiuiRom() && isMiuiFullScreen(context)) {
            return false;
        }
        if (!romUtils.checkIsVivoRom() || !isVivoFullScreen(context)) {
            return isHasNavigationBar(context);
        }
        return false;
    }

    public final int rejectedNavHeight(@NotNull Context context) {
        k.e(context, "context");
        Point point = getScreenSize(context);
        int i = point.x;
        int i2 = point.y;
        if (i > i2) {
            return i2;
        }
        return i2 - getNavigationBarCurrentHeight(context);
    }

    private final boolean isHuaWeiHideNav(Context context) {
        int i;
        if (Build.VERSION.SDK_INT < 21) {
            i = Settings.System.getInt(context.getContentResolver(), "navigationbar_is_min", 0);
        } else {
            i = Settings.Global.getInt(context.getContentResolver(), "navigationbar_is_min", 0);
        }
        if (i != 0) {
            return true;
        }
        return false;
    }

    private final boolean isMiuiFullScreen(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }

    private final boolean isVivoFullScreen(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "navigation_gesture_on", 0) != 0;
    }

    private final boolean isHasNavigationBar(Context context) {
        Object systemService = context.getSystemService("window");
        if (systemService != null) {
            Display d = ((WindowManager) systemService).getDefaultDisplay();
            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            if (Build.VERSION.SDK_INT >= 17) {
                d.getRealMetrics(realDisplayMetrics);
            }
            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);
            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;
            if (getNavigationBarHeight(context) + displayHeight > realHeight) {
                return false;
            }
            if (realWidth - displayWidth > 0 || realHeight - displayHeight > 0) {
                return true;
            }
            return false;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }
}
