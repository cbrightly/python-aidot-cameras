package com.leedarson.serviceinterface.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.ColorUtils;
import androidx.drawerlayout.widget.DrawerLayout;
import com.leedarson.module_base.R$id;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import timber.log.a;

public class StatusBarUtil {
    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;
    private static final int FAKE_STATUS_BAR_VIEW_ID = R$id.statusbarutil_fake_status_bar_view;
    private static final int FAKE_TRANSLUCENT_VIEW_ID = R$id.statusbarutil_translucent_view;
    private static final int TAG_KEY_HAVE_SET_OFFSET = -123;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void setColor(Activity activity, @ColorInt int color) {
        if (!PatchProxy.proxy(new Object[]{activity, new Integer(color)}, (Object) null, changeQuickRedirect, true, 9448, new Class[]{Activity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            setColor(activity, color, 112);
        }
    }

    public static void setColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        Object[] objArr = {activity, new Integer(color), new Integer(statusBarAlpha)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9449, new Class[]{Activity.class, cls, cls}, Void.TYPE).isSupported) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 21) {
                activity.getWindow().addFlags(Integer.MIN_VALUE);
                activity.getWindow().clearFlags(67108864);
                activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
            } else if (i >= 19) {
                activity.getWindow().addFlags(67108864);
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
                if (fakeStatusBarView != null) {
                    if (fakeStatusBarView.getVisibility() == 8) {
                        fakeStatusBarView.setVisibility(0);
                    }
                    fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
                } else {
                    decorView.addView(createStatusBarView(activity, color, statusBarAlpha));
                }
                setRootView(activity);
            }
        }
    }

    public static void setColorForSwipeBack(Activity activity, int color) {
        if (!PatchProxy.proxy(new Object[]{activity, new Integer(color)}, (Object) null, changeQuickRedirect, true, 9450, new Class[]{Activity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            setColorForSwipeBack(activity, color, 112);
        }
    }

    public static void setColorForSwipeBack(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        int i;
        boolean isNeedRequestLayout = true;
        Object[] objArr = {activity, new Integer(color), new Integer(statusBarAlpha)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9451, new Class[]{Activity.class, cls, cls}, Void.TYPE).isSupported && (i = Build.VERSION.SDK_INT) >= 19) {
            ViewGroup contentView = (ViewGroup) activity.findViewById(16908290);
            View rootView = contentView.getChildAt(0);
            int statusBarHeight = getStatusBarHeight(activity);
            if (rootView == null || !(rootView instanceof CoordinatorLayout)) {
                contentView.setPadding(0, statusBarHeight, 0, 0);
                contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
            } else {
                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
                if (i < 21) {
                    coordinatorLayout.setFitsSystemWindows(false);
                    contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
                    if (contentView.getPaddingTop() >= statusBarHeight) {
                        isNeedRequestLayout = false;
                    }
                    if (isNeedRequestLayout) {
                        contentView.setPadding(0, statusBarHeight, 0, 0);
                        coordinatorLayout.post(new Runnable() {
                            public static ChangeQuickRedirect changeQuickRedirect;

                            public void run() {
                                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9491, new Class[0], Void.TYPE).isSupported) {
                                    CoordinatorLayout.this.requestLayout();
                                }
                            }
                        });
                    }
                } else {
                    coordinatorLayout.setStatusBarBackgroundColor(calculateStatusColor(color, statusBarAlpha));
                }
            }
            setTransparentForWindow(activity);
        }
    }

    public static void setColorNoTranslucent(Activity activity, @ColorInt int color) {
        Object[] objArr = {activity, new Integer(color)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9452, new Class[]{Activity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            setColor(activity, color, 0);
        }
    }

    @Deprecated
    public static void setColorDiff(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= 19) {
            transparentStatusBar(activity);
            ViewGroup contentView = (ViewGroup) activity.findViewById(16908290);
            View fakeStatusBarView = contentView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.getVisibility() == 8) {
                    fakeStatusBarView.setVisibility(0);
                }
                fakeStatusBarView.setBackgroundColor(color);
            } else {
                contentView.addView(createStatusBarView(activity, color));
            }
            setRootView(activity);
        }
    }

    public static void setTranslucent(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9453, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            setTranslucent(activity, 112);
        }
    }

    public static void setTranslucent(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (!PatchProxy.proxy(new Object[]{activity, new Integer(statusBarAlpha)}, (Object) null, changeQuickRedirect, true, 9454, new Class[]{Activity.class, Integer.TYPE}, Void.TYPE).isSupported && Build.VERSION.SDK_INT >= 19) {
            setTransparent(activity);
            addTranslucentView(activity, statusBarAlpha);
        }
    }

    public static void setTranslucentForCoordinatorLayout(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (!PatchProxy.proxy(new Object[]{activity, new Integer(statusBarAlpha)}, (Object) null, changeQuickRedirect, true, 9455, new Class[]{Activity.class, Integer.TYPE}, Void.TYPE).isSupported && Build.VERSION.SDK_INT >= 19) {
            transparentStatusBar(activity);
            addTranslucentView(activity, statusBarAlpha);
        }
    }

    public static void setTransparent(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9456, new Class[]{Activity.class}, Void.TYPE).isSupported && Build.VERSION.SDK_INT >= 19) {
            transparentStatusBar(activity);
            setRootView(activity);
        }
    }

    @Deprecated
    public static void setTranslucentDiff(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(67108864);
            setRootView(activity);
        }
    }

    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        Object[] objArr = {activity, drawerLayout, new Integer(color)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9457, new Class[]{Activity.class, DrawerLayout.class, Integer.TYPE}, Void.TYPE).isSupported) {
            setColorForDrawerLayout(activity, drawerLayout, color, 112);
        }
    }

    public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        if (!PatchProxy.proxy(new Object[]{activity, drawerLayout, new Integer(color)}, (Object) null, changeQuickRedirect, true, 9458, new Class[]{Activity.class, DrawerLayout.class, Integer.TYPE}, Void.TYPE).isSupported) {
            setColorForDrawerLayout(activity, drawerLayout, color, 0);
        }
    }

    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        int i;
        Object[] objArr = {activity, drawerLayout, new Integer(color), new Integer(statusBarAlpha)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9459, new Class[]{Activity.class, DrawerLayout.class, cls, cls}, Void.TYPE).isSupported && (i = Build.VERSION.SDK_INT) >= 19) {
            if (i >= 21) {
                activity.getWindow().addFlags(Integer.MIN_VALUE);
                activity.getWindow().clearFlags(67108864);
                activity.getWindow().setStatusBarColor(0);
            } else {
                activity.getWindow().addFlags(67108864);
            }
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.getVisibility() == 8) {
                    fakeStatusBarView.setVisibility(0);
                }
                fakeStatusBarView.setBackgroundColor(color);
            } else {
                contentLayout.addView(createStatusBarView(activity, color), 0);
            }
            if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(), contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
            }
            setDrawerLayoutProperty(drawerLayout, contentLayout);
            addTranslucentView(activity, statusBarAlpha);
        }
    }

    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout) {
        if (!PatchProxy.proxy(new Object[]{drawerLayout, drawerLayoutContentLayout}, (Object) null, changeQuickRedirect, true, 9460, new Class[]{DrawerLayout.class, ViewGroup.class}, Void.TYPE).isSupported) {
            drawerLayout.setFitsSystemWindows(false);
            drawerLayoutContentLayout.setFitsSystemWindows(false);
            drawerLayoutContentLayout.setClipToPadding(true);
            ((ViewGroup) drawerLayout.getChildAt(1)).setFitsSystemWindows(false);
        }
    }

    @Deprecated
    public static void setColorForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(67108864);
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.getVisibility() == 8) {
                    fakeStatusBarView.setVisibility(0);
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, 112));
            } else {
                contentLayout.addView(createStatusBarView(activity, color), 0);
            }
            if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
            }
            setDrawerLayoutProperty(drawerLayout, contentLayout);
        }
    }

    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        Class[] clsArr = {Activity.class, DrawerLayout.class};
        if (!PatchProxy.proxy(new Object[]{activity, drawerLayout}, (Object) null, changeQuickRedirect, true, 9461, clsArr, Void.TYPE).isSupported) {
            setTranslucentForDrawerLayout(activity, drawerLayout, 112);
        }
    }

    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        Object[] objArr = {activity, drawerLayout, new Integer(statusBarAlpha)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9462, new Class[]{Activity.class, DrawerLayout.class, Integer.TYPE}, Void.TYPE).isSupported && Build.VERSION.SDK_INT >= 19) {
            setTransparentForDrawerLayout(activity, drawerLayout);
            addTranslucentView(activity, statusBarAlpha);
        }
    }

    public static void setTransparentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        int i;
        if (!PatchProxy.proxy(new Object[]{activity, drawerLayout}, (Object) null, changeQuickRedirect, true, 9463, new Class[]{Activity.class, DrawerLayout.class}, Void.TYPE).isSupported && (i = Build.VERSION.SDK_INT) >= 19) {
            if (i >= 21) {
                activity.getWindow().addFlags(Integer.MIN_VALUE);
                activity.getWindow().clearFlags(67108864);
                activity.getWindow().setStatusBarColor(0);
            } else {
                activity.getWindow().addFlags(67108864);
            }
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
            }
            setDrawerLayoutProperty(drawerLayout, contentLayout);
        }
    }

    @Deprecated
    public static void setTranslucentForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout) {
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().addFlags(67108864);
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            contentLayout.setFitsSystemWindows(true);
            contentLayout.setClipToPadding(true);
            ((ViewGroup) drawerLayout.getChildAt(1)).setFitsSystemWindows(false);
            drawerLayout.setFitsSystemWindows(false);
        }
    }

    public static void setTransparentForImageView(Activity activity, View needOffsetView) {
        if (!PatchProxy.proxy(new Object[]{activity, needOffsetView}, (Object) null, changeQuickRedirect, true, 9464, new Class[]{Activity.class, View.class}, Void.TYPE).isSupported) {
            setTranslucentForImageView(activity, 0, needOffsetView);
        }
    }

    public static void setTranslucentForImageView(Activity activity, View needOffsetView) {
        Class[] clsArr = {Activity.class, View.class};
        if (!PatchProxy.proxy(new Object[]{activity, needOffsetView}, (Object) null, changeQuickRedirect, true, 9465, clsArr, Void.TYPE).isSupported) {
            setTranslucentForImageView(activity, 112, needOffsetView);
        }
    }

    public static void setTranslucentForImageView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha, View needOffsetView) {
        Object[] objArr = {activity, new Integer(statusBarAlpha), needOffsetView};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9466, new Class[]{Activity.class, Integer.TYPE, View.class}, Void.TYPE).isSupported && Build.VERSION.SDK_INT >= 19) {
            setTransparentForWindow(activity);
            addTranslucentView(activity, statusBarAlpha);
            if (needOffsetView != null) {
                Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET);
                if (haveSetOffset == null || !((Boolean) haveSetOffset).booleanValue()) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
                    layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity), layoutParams.rightMargin, layoutParams.bottomMargin);
                    needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true);
                }
            }
        }
    }

    public static void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView) {
        Class[] clsArr = {Activity.class, View.class};
        if (!PatchProxy.proxy(new Object[]{activity, needOffsetView}, (Object) null, changeQuickRedirect, true, 9467, clsArr, Void.TYPE).isSupported) {
            setTranslucentForImageViewInFragment(activity, 112, needOffsetView);
        }
    }

    public static void setTransparentForImageViewInFragment(Activity activity, View needOffsetView) {
        if (!PatchProxy.proxy(new Object[]{activity, needOffsetView}, (Object) null, changeQuickRedirect, true, 9468, new Class[]{Activity.class, View.class}, Void.TYPE).isSupported) {
            setTranslucentForImageViewInFragment(activity, 0, needOffsetView);
        }
    }

    public static void setTranslucentForImageViewInFragment(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha, View needOffsetView) {
        Object[] objArr = {activity, new Integer(statusBarAlpha), needOffsetView};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9469, new Class[]{Activity.class, Integer.TYPE, View.class}, Void.TYPE).isSupported) {
            setTranslucentForImageView(activity, statusBarAlpha, needOffsetView);
            int i = Build.VERSION.SDK_INT;
            if (i >= 19 && i < 21) {
                clearPreviousSetting(activity);
            }
        }
    }

    public static void hideFakeStatusBarView(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9470, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
            if (fakeStatusBarView != null) {
                fakeStatusBarView.setVisibility(8);
            }
            View fakeTranslucentView = decorView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
            if (fakeTranslucentView != null) {
                fakeTranslucentView.setVisibility(8);
            }
        }
    }

    @TargetApi(23)
    public static void setLightMode(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9471, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            setMIUIStatusBarDarkIcon(activity, true);
            setMeizuStatusBarDarkIcon(activity, true);
            if (Build.VERSION.SDK_INT >= 23) {
                activity.getWindow().getDecorView().setSystemUiVisibility(9216);
            }
        }
    }

    @TargetApi(23)
    public static void setDarkMode(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9472, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            setMIUIStatusBarDarkIcon(activity, false);
            setMeizuStatusBarDarkIcon(activity, false);
            if (Build.VERSION.SDK_INT >= 23) {
                activity.getWindow().getDecorView().setSystemUiVisibility(1024);
            }
        }
    }

    private static void setMIUIStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        if (!PatchProxy.proxy(new Object[]{activity, new Byte(darkIcon ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 9473, new Class[]{Activity.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            Class<?> cls = activity.getWindow().getClass();
            try {
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int darkModeFlag = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(layoutParams);
                Class cls2 = Integer.TYPE;
                Method extraFlagField = cls.getMethod("setExtraFlags", new Class[]{cls2, cls2});
                Window window = activity.getWindow();
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(darkIcon ? darkModeFlag : 0);
                objArr[1] = Integer.valueOf(darkModeFlag);
                extraFlagField.invoke(window, objArr);
            } catch (Exception e) {
            }
        }
    }

    private static void setMeizuStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        int value;
        if (!PatchProxy.proxy(new Object[]{activity, new Byte(darkIcon ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 9474, new Class[]{Activity.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt((Object) null);
                int value2 = meizuFlags.getInt(lp);
                if (darkIcon) {
                    value = value2 | bit;
                } else {
                    value = value2 & (~bit);
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
            } catch (Exception e) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = (android.view.ViewGroup) r9.getWindow().getDecorView();
     */
    @android.annotation.TargetApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void clearPreviousSetting(android.app.Activity r9) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.app.Activity> r0 = android.app.Activity.class
            r6[r8] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r2 = 0
            r4 = 1
            r5 = 9475(0x2503, float:1.3277E-41)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            android.view.Window r0 = r9.getWindow()
            android.view.View r0 = r0.getDecorView()
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            int r1 = FAKE_STATUS_BAR_VIEW_ID
            android.view.View r1 = r0.findViewById(r1)
            if (r1 == 0) goto L_0x0044
            r0.removeView(r1)
            r2 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r2 = r9.findViewById(r2)
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            android.view.View r2 = r2.getChildAt(r8)
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            r2.setPadding(r8, r8, r8, r8)
        L_0x0044:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceinterface.utils.StatusBarUtil.clearPreviousSetting(android.app.Activity):void");
    }

    private static void addTranslucentView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        Object[] objArr = {activity, new Integer(statusBarAlpha)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9476, new Class[]{Activity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            ViewGroup contentView = (ViewGroup) activity.findViewById(16908290);
            View fakeTranslucentView = contentView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
            if (fakeTranslucentView != null) {
                if (fakeTranslucentView.getVisibility() == 8) {
                    fakeTranslucentView.setVisibility(0);
                }
                fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
                return;
            }
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
        }
    }

    private static View createStatusBarView(Activity activity, @ColorInt int color) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity, new Integer(color)}, (Object) null, changeQuickRedirect, true, 9477, new Class[]{Activity.class, Integer.TYPE}, View.class);
        return proxy.isSupported ? (View) proxy.result : createStatusBarView(activity, color, 0);
    }

    private static View createStatusBarView(Activity activity, @ColorInt int color, int alpha) {
        Object[] objArr = {activity, new Integer(color), new Integer(alpha)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9478, new Class[]{Activity.class, cls, cls}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        View statusBarView = new View(activity);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(-1, getStatusBarHeight(activity)));
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
        statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
        return statusBarView;
    }

    private static void setRootView(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9479, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            ViewGroup parent = (ViewGroup) activity.findViewById(16908290);
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                View childView = parent.getChildAt(i);
                if (childView instanceof ViewGroup) {
                    childView.setFitsSystemWindows(true);
                    ((ViewGroup) childView).setClipToPadding(true);
                }
            }
        }
    }

    private static void setTransparentForWindow(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9480, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 21) {
                activity.getWindow().setStatusBarColor(0);
                activity.getWindow().getDecorView().setSystemUiVisibility(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ);
            } else if (i >= 19) {
                activity.getWindow().setFlags(67108864, 67108864);
            }
        }
    }

    @TargetApi(19)
    private static void transparentStatusBar(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9481, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 21) {
                activity.getWindow().addFlags(Integer.MIN_VALUE);
                activity.getWindow().clearFlags(67108864);
                activity.getWindow().addFlags(134217728);
                activity.getWindow().setStatusBarColor(0);
                return;
            }
            activity.getWindow().addFlags(67108864);
        }
    }

    private static View createTranslucentStatusBarView(Activity activity, int alpha) {
        Object[] objArr = {activity, new Integer(alpha)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 9482, new Class[]{Activity.class, Integer.TYPE}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        View statusBarView = new View(activity);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(-1, getStatusBarHeight(activity)));
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setId(FAKE_TRANSLUCENT_VIEW_ID);
        return statusBarView;
    }

    public static int getStatusBarHeight(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9483, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
    }

    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1.0f - (((float) alpha) / 255.0f);
        return -16777216 | (((int) (((double) (((float) ((color >> 16) & 255)) * a)) + 0.5d)) << 16) | (((int) (((double) (((float) ((color >> 8) & 255)) * a)) + 0.5d)) << 8) | ((int) (((double) (((float) (color & 255)) * a)) + 0.5d));
    }

    public static void setStatusBarFullTransparent(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9484, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 21) {
                Window window = activity.getWindow();
                window.clearFlags(67108864);
                window.getDecorView().setSystemUiVisibility(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ);
                window.addFlags(Integer.MIN_VALUE);
                window.setStatusBarColor(0);
            } else if (i >= 19) {
                activity.getWindow().addFlags(67108864);
            }
        }
    }

    public static void setStatusBarTextColor(Activity activity, @ColorInt int color) {
        Object[] objArr = {activity, new Integer(color)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9485, new Class[]{Activity.class, Integer.TYPE}, Void.TYPE).isSupported || Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (isLightColor(color)) {
            activity.getWindow().getDecorView().setSystemUiVisibility(8192);
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(0);
        }
    }

    private static boolean isLightColor(@ColorInt int color) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(color)}, (Object) null, changeQuickRedirect, true, 9486, new Class[]{Integer.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return ColorUtils.calculateLuminance(color) >= 0.5d;
    }

    public static void setStatusBarHeight(Context context, View view) {
        Class[] clsArr = {Context.class, View.class};
        if (!PatchProxy.proxy(new Object[]{context, view}, (Object) null, changeQuickRedirect, true, 9487, clsArr, Void.TYPE).isSupported) {
            int height = getStatusBarHeight(context);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
    }

    public static void setStatusBarTextAuto(Activity activity, WebView webView) {
        View view;
        if (!PatchProxy.proxy(new Object[]{activity, webView}, (Object) null, changeQuickRedirect, true, 9488, new Class[]{Activity.class, WebView.class}, Void.TYPE).isSupported) {
            final Activity activity2 = activity;
            WebView webView2 = webView;
            a.g("CZB").a("setStatusBarTextAuto", new Object[0]);
            if (webView2 != null) {
                view = webView2;
            } else {
                try {
                    view = activity2.getWindow().getDecorView();
                } catch (Exception e) {
                    e = e;
                    WebView webView3 = webView2;
                    e.printStackTrace();
                    return;
                }
            }
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bitmapCache = view.getDrawingCache();
            Rect frame = new Rect();
            activity2.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            int width = activity2.getWindowManager().getDefaultDisplay().getWidth();
            if (bitmapCache == null) {
                Bitmap bitmap = bitmapCache;
                Rect rect = frame;
            } else if (bitmapCache.isRecycled()) {
                WebView webView4 = webView2;
                Bitmap bitmap2 = bitmapCache;
                Rect rect2 = frame;
            } else {
                Bitmap bitmap3 = Bitmap.createBitmap(bitmapCache, 0, statusBarHeight, width, statusBarHeight);
                int[] pixels = new int[50];
                int sum = 0;
                int i = 0;
                while (i < 50) {
                    try {
                        pixels[i] = bitmap3.getPixel(bitmap3.getWidth() / 51, 1);
                        sum += pixels[i];
                        i++;
                    } catch (Exception e2) {
                        e = e2;
                        WebView webView5 = webView2;
                        e.printStackTrace();
                        return;
                    }
                }
                int averageColor = sum / 50;
                WebView webView6 = webView2;
                Bitmap bitmap4 = bitmapCache;
                Rect rect3 = frame;
                final double colorBrightness = (((double) ((16711680 & averageColor) >> 16)) * 0.299d) + (((double) ((65280 & averageColor) >> 8)) * 0.587d) + (((double) (averageColor & 255)) * 0.114d);
                try {
                    view.destroyDrawingCache();
                    activity2.runOnUiThread(new Runnable() {
                        public static ChangeQuickRedirect changeQuickRedirect;

                        public void run() {
                            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9492, new Class[0], Void.TYPE).isSupported) {
                                if (colorBrightness > 192.0d) {
                                    StatusBarUtil.setLightMode(activity2);
                                } else {
                                    StatusBarUtil.setDarkMode(activity2);
                                }
                            }
                        }
                    });
                    return;
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    return;
                }
            }
            a.g("CZB").h("setStatusBarTextAuto bitmap==null||bitmap.isRecycled()", new Object[0]);
        }
    }

    public static void setStatusBarStyleByColor(Activity activity, int barColor) {
        if (!PatchProxy.proxy(new Object[]{activity, new Integer(barColor)}, (Object) null, changeQuickRedirect, true, 9489, new Class[]{Activity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                if (((int) ((((double) Color.red(barColor)) * 0.299d) + (((double) Color.green(barColor)) * 0.587d) + (((double) Color.blue(barColor)) * 0.114d))) > 192) {
                    setLightMode(activity);
                } else {
                    setDarkMode(activity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap activityScreenShot(Activity activity, WebView webView) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity, webView}, (Object) null, changeQuickRedirect, true, 9490, new Class[]{Activity.class, WebView.class}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        View view = webView != null ? webView : activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap == null || bitmap.isRecycled()) {
            a.g("CZB").h("setStatusBarTextAuto bitmap==null||bitmap.isRecycled()", new Object[0]);
            return null;
        }
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Bitmap b = Bitmap.createBitmap(bitmap, 0, statusBarHeight, activity.getWindowManager().getDefaultDisplay().getWidth(), statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }
}
