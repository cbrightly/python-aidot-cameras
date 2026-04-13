package com.sensorsdata.analytics.android.sdk.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TabHost;
import com.sensorsdata.analytics.android.sdk.AppStateManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class WindowHelper {
    private static boolean sArrayListWindowViews = false;
    private static final String sCustomWindowPrefix = "/CustomWindow";
    private static final String sDialogWindowPrefix = "/DialogWindow";
    private static boolean sIsInitialized = false;
    private static Method sItemViewGetDataMethod = null;
    private static Class<?> sListMenuItemViewClazz = null;
    private static final String sMainWindowPrefix = "/MainWindow";
    private static Class sPhoneWindowClazz = null;
    private static Class sPopupWindowClazz = null;
    private static final String sPopupWindowPrefix = "/PopupWindow";
    private static boolean sViewArrayWindowViews = false;
    private static Comparator<View> sViewSizeComparator = new Comparator<View>() {
        public int compare(View lhs, View rhs) {
            int lhsHashCode = lhs.hashCode();
            int rhsHashCode = rhs.hashCode();
            int currentHashCode = AppStateManager.getInstance().getCurrentRootWindowsHashCode();
            if (lhsHashCode == currentHashCode) {
                return -1;
            }
            if (rhsHashCode == currentHashCode) {
                return 1;
            }
            return (rhs.getWidth() * rhs.getHeight()) - (lhs.getWidth() * lhs.getHeight());
        }
    };
    private static Object sWindowManger;
    private static Field viewsField;

    public static void init() {
        String windowManagerClassName;
        String windowManagerString;
        if (!sIsInitialized) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 17) {
                windowManagerClassName = "android.view.WindowManagerGlobal";
            } else {
                windowManagerClassName = "android.view.WindowManagerImpl";
            }
            try {
                Class windowManager = Class.forName(windowManagerClassName);
                if (i >= 17) {
                    windowManagerString = "sDefaultWindowManager";
                } else if (i >= 13) {
                    windowManagerString = "sWindowManager";
                } else {
                    windowManagerString = "mWindowManager";
                }
                viewsField = windowManager.getDeclaredField("mViews");
                Field instanceField = windowManager.getDeclaredField(windowManagerString);
                viewsField.setAccessible(true);
                if (viewsField.getType() == ArrayList.class) {
                    sArrayListWindowViews = true;
                } else if (viewsField.getType() == View[].class) {
                    sViewArrayWindowViews = true;
                }
                instanceField.setAccessible(true);
                sWindowManger = instanceField.get((Object) null);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            }
            try {
                sListMenuItemViewClazz = Class.forName("com.android.internal.view.menu.ListMenuItemView");
                sItemViewGetDataMethod = Class.forName("com.android.internal.view.menu.MenuView$ItemView").getDeclaredMethod("getItemData", new Class[0]);
            } catch (ClassNotFoundException | NoSuchMethodException e2) {
            }
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    try {
                        sPhoneWindowClazz = Class.forName("com.android.internal.policy.PhoneWindow$DecorView");
                    } catch (ClassNotFoundException e3) {
                        sPhoneWindowClazz = Class.forName("com.android.internal.policy.DecorView");
                    }
                } else {
                    sPhoneWindowClazz = Class.forName("com.android.internal.policy.impl.PhoneWindow$DecorView");
                }
            } catch (ClassNotFoundException e4) {
            }
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    sPopupWindowClazz = Class.forName("android.widget.PopupWindow$PopupDecorView");
                } else {
                    sPopupWindowClazz = Class.forName("android.widget.PopupWindow$PopupViewContainer");
                }
            } catch (ClassNotFoundException e5) {
            }
            sIsInitialized = true;
        }
    }

    private static View[] getWindowViews() {
        View[] result = new View[0];
        Object obj = sWindowManger;
        if (obj == null) {
            Activity current = AppStateManager.getInstance().getForegroundActivity();
            View decorView = null;
            if (current != null) {
                Window window = current.getWindow();
                if (window.isActive()) {
                    decorView = window.getDecorView();
                }
            }
            if (current == null) {
                return result;
            }
            return new View[]{decorView};
        }
        View[] views = null;
        try {
            if (sArrayListWindowViews) {
                views = (View[]) ((ArrayList) viewsField.get(obj)).toArray(result);
            } else if (sViewArrayWindowViews) {
                views = (View[]) viewsField.get(obj);
            }
            if (views != null) {
                result = views;
            }
        } catch (Exception e) {
        }
        return filterNullAndDismissToastView(result);
    }

    public static View[] getSortedWindowViews() {
        View[] views = getWindowViews();
        if (views.length <= 1) {
            return views;
        }
        View[] views2 = (View[]) Arrays.copyOf(views, views.length);
        Arrays.sort(views2, sViewSizeComparator);
        return views2;
    }

    private static View[] filterNullAndDismissToastView(View[] array) {
        List<View> list = new ArrayList<>(array.length);
        long currentTimeMillis = System.currentTimeMillis();
        View[] result = array;
        int length = array.length;
        for (int i = 0; i < length; i++) {
            View view = result[i];
            if (view != null) {
                list.add(view);
            }
        }
        View[] result2 = new View[list.size()];
        list.toArray(result2);
        return result2;
    }

    public static boolean isDecorView(Class rootClass) {
        if (!sIsInitialized) {
            init();
        }
        return rootClass == sPhoneWindowClazz || rootClass == sPopupWindowClazz;
    }

    @SuppressLint({"RestrictedApi"})
    private static Object getMenuItemData(View view) {
        if (view.getClass() == sListMenuItemViewClazz) {
            return sItemViewGetDataMethod.invoke(view, new Object[0]);
        }
        if (ViewUtil.instanceOfAndroidXListMenuItemView(view) || ViewUtil.instanceOfSupportListMenuItemView(view) || ViewUtil.instanceOfBottomNavigationItemView(view)) {
            return ViewUtil.getItemData(view);
        }
        return null;
    }

    private static View findMenuItemView(View view, MenuItem item) {
        View navButtonView;
        if ((ViewUtil.instanceOfActionMenuItem(item) && item.getItemId() == 16908332 && ViewUtil.instanceOfToolbar(view.getParent()) && (view instanceof ImageButton) && (navButtonView = (View) ReflectUtil.findField(new String[]{"androidx.appcompat.widget.Toolbar", "androidx.appcompat.widget.Toolbar", "android.widget.Toolbar"}, (Object) view.getParent(), "mNavButtonView")) != null && navButtonView == view) || getMenuItemData(view) == item) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            View menuView = findMenuItemView(((ViewGroup) view).getChildAt(i), item);
            if (menuView != null) {
                return menuView;
            }
        }
        return null;
    }

    public static View getClickView(MenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }
        init();
        View[] windows = getWindowViews();
        try {
            for (View window2 : windows) {
                if (window2.getClass() == sPopupWindowClazz) {
                    View findMenuItemView = findMenuItemView(window2, menuItem);
                    View menuView = findMenuItemView;
                    if (findMenuItemView != null) {
                        return menuView;
                    }
                }
            }
            for (View window : windows) {
                if (window.getClass() != sPopupWindowClazz) {
                    View findMenuItemView2 = findMenuItemView(window, menuItem);
                    View menuView2 = findMenuItemView2;
                    if (findMenuItemView2 != null) {
                        return menuView2;
                    }
                }
            }
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (IllegalAccessException e2) {
            return null;
        } catch (Exception e3) {
            return null;
        }
    }

    public static View getClickView(String tabHostTag) {
        int i = 0;
        if (TextUtils.isEmpty(tabHostTag)) {
            return null;
        }
        init();
        View[] windows = getWindowViews();
        while (i < windows.length) {
            try {
                View window = windows[i];
                if (window.getClass() != sPopupWindowClazz) {
                    View findTabView = findTabView(window, tabHostTag);
                    View tabHostView = findTabView;
                    if (findTabView != null) {
                        return tabHostView;
                    }
                }
                i++;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private static View findTabView(View view, String tabHostTag) {
        if (TextUtils.equals(tabHostTag, getTabHostTag(view))) {
            return (View) ReflectUtil.callMethod(view, "getCurrentTabView", new Object[0]);
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            View tabHostView = findTabView(((ViewGroup) view).getChildAt(i), tabHostTag);
            if (tabHostView != null) {
                return tabHostView;
            }
        }
        return null;
    }

    private static String getTabHostTag(View view) {
        if (view instanceof TabHost) {
            return (String) ReflectUtil.callMethod(view, "getCurrentTabTag", new Object[0]);
        }
        return null;
    }

    public static String getWindowPrefix(View root) {
        if (root.hashCode() == AppStateManager.getInstance().getCurrentRootWindowsHashCode()) {
            return getMainWindowPrefix();
        }
        return getSubWindowPrefix(root);
    }

    public static String getMainWindowPrefix() {
        return sMainWindowPrefix;
    }

    private static String getSubWindowPrefix(View root) {
        ViewGroup.LayoutParams params = root.getLayoutParams();
        if (params != null && (params instanceof WindowManager.LayoutParams)) {
            int type = ((WindowManager.LayoutParams) params).type;
            if (type == 1) {
                return sMainWindowPrefix;
            }
            if (type < 99 && root.getClass() == sPhoneWindowClazz) {
                return sDialogWindowPrefix;
            }
            if (type < 1999 && root.getClass() == sPopupWindowClazz) {
                return sPopupWindowPrefix;
            }
            if (type < 2999) {
                return sCustomWindowPrefix;
            }
        }
        Class rootClazz = root.getClass();
        if (rootClazz == sPhoneWindowClazz) {
            return sDialogWindowPrefix;
        }
        return rootClazz == sPopupWindowClazz ? sPopupWindowPrefix : sCustomWindowPrefix;
    }

    public static boolean isMainWindow(View root) {
        ViewGroup.LayoutParams params = root.getLayoutParams();
        if (!(params instanceof WindowManager.LayoutParams) || ((WindowManager.LayoutParams) params).type != 1) {
            return false;
        }
        return true;
    }

    public static boolean isDialogOrPopupWindow(View root) {
        String prefix = getSubWindowPrefix(root);
        return TextUtils.equals(sDialogWindowPrefix, prefix) || TextUtils.equals(sPopupWindowPrefix, prefix);
    }

    public static boolean isCustomWindow(View root) {
        return TextUtils.equals(sCustomWindowPrefix, getSubWindowPrefix(root));
    }
}
