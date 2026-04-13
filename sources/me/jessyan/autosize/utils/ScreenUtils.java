package me.jessyan.autosize.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {
    private ScreenUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static int getStatusBarHeight() {
        try {
            int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                return Resources.getSystem().getDimensionPixelSize(resourceId);
            }
            return 0;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int[] getScreenSize(Context context) {
        Display d = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    public static int[] getRawScreenSize(Context context) {
        int[] size = new int[2];
        Display d = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        int i = Build.VERSION.SDK_INT;
        if (i >= 14 && i < 17) {
            try {
                widthPixels = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(d, new Object[0])).intValue();
                heightPixels = ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(d, new Object[0])).intValue();
            } catch (Exception e) {
            }
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", new Class[]{Point.class}).invoke(d, new Object[]{realSize});
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception e2) {
            }
        }
        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }

    public static int getHeightOfNavigationBar(Context context) {
        if (Build.VERSION.SDK_INT < 17 || Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) == 0) {
            return getRawScreenSize(context)[1] - getScreenSize(context)[1];
        }
        return 0;
    }
}
