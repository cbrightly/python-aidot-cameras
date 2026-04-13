package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;

/* compiled from: BarUtils */
public final class e {
    public static int c() {
        Resources resources = Resources.getSystem();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static boolean f(@NonNull Activity activity) {
        if (activity != null) {
            return (activity.getWindow().getAttributes().flags & 1024) == 0;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int a() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static boolean d(@NonNull Activity activity) {
        if (activity != null) {
            return e(activity.getWindow());
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean e(@NonNull Window window) {
        if (window != null) {
            boolean isVisible = false;
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            int i = 0;
            int count = decorView.getChildCount();
            while (true) {
                if (i < count) {
                    View child = decorView.getChildAt(i);
                    int id = child.getId();
                    if (id != -1 && "navigationBarBackground".equals(b(id)) && child.getVisibility() == 0) {
                        isVisible = true;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (!isVisible) {
                return isVisible;
            }
            return (decorView.getSystemUiVisibility() & 2) == 0;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static String b(int id) {
        try {
            return f0.a().getResources().getResourceEntryName(id);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean g() {
        if (Build.VERSION.SDK_INT >= 17) {
            WindowManager wm = (WindowManager) f0.a().getSystemService("window");
            if (wm == null) {
                return false;
            }
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            if (realSize.y == size.y && realSize.x == size.x) {
                return false;
            }
            return true;
        }
        boolean menu = ViewConfiguration.get(f0.a()).hasPermanentMenuKey();
        boolean back = KeyCharacterMap.deviceHasKey(4);
        if (menu || back) {
            return false;
        }
        return true;
    }
}
