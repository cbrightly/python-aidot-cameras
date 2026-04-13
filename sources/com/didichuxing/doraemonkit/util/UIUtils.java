package com.didichuxing.doraemonkit.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.AnyRes;
import com.blankj.utilcode.util.f;
import com.blankj.utilcode.util.x;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class UIUtils {
    private static final String STR_VIEW_BORDER_Id = "app:id/dokit_view_border_id";
    private static final String TAG = "UIUtils";

    public static int dp2px(float dpValue) {
        return f.e(dpValue);
    }

    public static int px2dp(int px) {
        return f.i((float) px);
    }

    public static float getDensity() {
        return x.c();
    }

    public static int getDensityDpi() {
        return x.d();
    }

    public static int getWidthPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) DoraemonKit.APPLICATION.getSystemService("window");
        if (windowManager == null) {
            return 0;
        }
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getHeightPixels() {
        return getRealHeightPixels() - getStatusBarHeight();
    }

    public static int getRealHeightPixels() {
        Display display = ((WindowManager) DoraemonKit.APPLICATION.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", new Class[]{DisplayMetrics.class}).invoke(display, new Object[]{dm});
            return dm.heightPixels;
        } catch (Exception e) {
            LogHelper.d(TAG, e.toString());
            return 0;
        }
    }

    public static int getStatusBarHeight() {
        Resources resources = DoraemonKit.APPLICATION.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static Rect getViewRect(View view) {
        Rect rect = new Rect();
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        rect.left = locations[0];
        rect.top = locations[1];
        if (!checkStatusBarVisible(view.getContext())) {
            rect.top -= getStatusBarHeight();
        }
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        return rect;
    }

    public static boolean checkStatusBarVisible(Context context) {
        return checkFullScreenByTheme(context) || checkFullScreenByCode(context) || checkFullScreenByCode2(context);
    }

    public static boolean checkFullScreenByTheme(Context context) {
        Resources.Theme theme = context.getTheme();
        if (theme != null) {
            TypedValue typedValue = new TypedValue();
            if (theme.resolveAttribute(16843277, typedValue, false)) {
                typedValue.coerceToString();
                if (typedValue.type != 18 || typedValue.data == 0) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean checkFullScreenByCode(Context context) {
        Window window;
        View decorView;
        if (!(context instanceof Activity) || (window = ((Activity) context).getWindow()) == null || (decorView = window.getDecorView()) == null || (decorView.getSystemUiVisibility() & 4) != 4) {
            return false;
        }
        return true;
    }

    public static boolean checkFullScreenByCode2(Context context) {
        if (!(context instanceof Activity) || (((Activity) context).getWindow().getAttributes().flags & 1024) != 1024) {
            return false;
        }
        return true;
    }

    public static String getIdText(View view) {
        String pkgname;
        int id = view.getId();
        StringBuilder out = new StringBuilder();
        if (id != -1) {
            Resources r = view.getResources();
            if (id > 0 && resourceHasPackage(id) && r != null) {
                switch (-16777216 & id) {
                    case 16777216:
                        pkgname = "android";
                        break;
                    case AVIOCTRLDEFs.IOTYPE_USER_WIFICMD_REQ /*2130706432*/:
                        pkgname = "app";
                        break;
                    default:
                        try {
                            pkgname = r.getResourcePackageName(id);
                            break;
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                            break;
                        }
                }
                String typename = r.getResourceTypeName(id);
                String entryname = r.getResourceEntryName(id);
                out.append(" ");
                out.append(pkgname);
                out.append(":");
                out.append(typename);
                out.append("/");
                out.append(entryname);
            }
        }
        return TextUtils.isEmpty(out.toString()) ? "" : out.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0044, code lost:
        if (getIdText(r3).trim().equals(STR_VIEW_BORDER_Id) == false) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0046, code lost:
        r1 = ((com.didichuxing.doraemonkit.kit.layoutborder.ViewBorderFrameLayout) r3).getChildAt(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004f, code lost:
        r1 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0050, code lost:
        r1.setTag(java.lang.Integer.valueOf(com.didichuxing.doraemonkit.R.id.dokit_app_contentview_id));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.view.View getDokitAppContentView(android.app.Activity r6) {
        /*
            android.view.Window r0 = r6.getWindow()
            android.view.View r0 = r0.getDecorView()
            android.widget.FrameLayout r0 = (android.widget.FrameLayout) r0
            int r1 = com.didichuxing.doraemonkit.R.id.dokit_app_contentview_id
            java.lang.Object r1 = r0.getTag(r1)
            android.view.View r1 = (android.view.View) r1
            if (r1 == 0) goto L_0x0015
            return r1
        L_0x0015:
            r2 = 0
        L_0x0016:
            int r3 = r0.getChildCount()
            if (r2 >= r3) goto L_0x005d
            android.view.View r3 = r0.getChildAt(r2)
            boolean r4 = r3 instanceof android.widget.LinearLayout
            if (r4 == 0) goto L_0x0032
            java.lang.String r4 = getIdText(r3)
            java.lang.String r4 = r4.trim()
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x0036
        L_0x0032:
            boolean r4 = r3 instanceof android.widget.FrameLayout
            if (r4 == 0) goto L_0x005a
        L_0x0036:
            java.lang.String r4 = getIdText(r3)
            java.lang.String r4 = r4.trim()
            java.lang.String r5 = "app:id/dokit_view_border_id"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x004f
            r4 = r3
            com.didichuxing.doraemonkit.kit.layoutborder.ViewBorderFrameLayout r4 = (com.didichuxing.doraemonkit.kit.layoutborder.ViewBorderFrameLayout) r4
            r5 = 0
            android.view.View r1 = r4.getChildAt(r5)
            goto L_0x0050
        L_0x004f:
            r1 = r3
        L_0x0050:
            int r4 = com.didichuxing.doraemonkit.R.id.dokit_app_contentview_id
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r1.setTag(r4)
            goto L_0x005d
        L_0x005a:
            int r2 = r2 + 1
            goto L_0x0016
        L_0x005d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.util.UIUtils.getDokitAppContentView(android.app.Activity):android.view.View");
    }

    private static boolean resourceHasPackage(@AnyRes int resid) {
        return (resid >>> 24) != 0;
    }

    public static double getScreenInch(Activity context) {
        int realHeight;
        int realWidth;
        try {
            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            int i = Build.VERSION.SDK_INT;
            if (i >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (i >= 17 || i < 14) {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            } else {
                Method mGetRawH = Display.class.getMethod("getRawHeight", new Class[0]);
                realWidth = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(display, new Object[0])).intValue();
                realHeight = ((Integer) mGetRawH.invoke(display, new Object[0])).intValue();
            }
            float f = metrics.xdpi;
            float f2 = (((float) realWidth) / f) * (((float) realWidth) / f);
            float f3 = metrics.ydpi;
            return formatDouble(Math.sqrt((double) (f2 + ((((float) realHeight) / f3) * (((float) realHeight) / f3)))), 1);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    private static double formatDouble(double d, int newScale) {
        return new BigDecimal(d).setScale(newScale, 4).doubleValue();
    }
}
