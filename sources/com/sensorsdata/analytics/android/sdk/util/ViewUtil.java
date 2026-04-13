package com.sensorsdata.analytics.android.sdk.util;

import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.google.android.material.timepicker.TimeModel;
import com.meituan.robust.Constants;
import com.sensorsdata.analytics.android.sdk.AppStateManager;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.visual.model.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache;
import com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class ViewUtil {
    private static boolean sHaveCustomRecyclerView = false;
    private static boolean sHaveRecyclerView = haveRecyclerView();
    private static Class<?> sRecyclerViewClass;
    private static Method sRecyclerViewGetChildAdapterPositionMethod;
    private static SparseArray<String> sViewCache;

    private static boolean instanceOfSupportSwipeRefreshLayout(Object view) {
        return ReflectUtil.isInstance(view, "androidx.swiperefreshlayout.widget.SwipeRefreshLayout", "androidx.swiperefreshlayout.widget.SwipeRefreshLayout");
    }

    static boolean instanceOfSupportListMenuItemView(Object view) {
        return ReflectUtil.isInstance(view, "androidx.appcompat.view.menu.ListMenuItemView");
    }

    static boolean instanceOfAndroidXListMenuItemView(Object view) {
        return ReflectUtil.isInstance(view, "androidx.appcompat.view.menu.ListMenuItemView");
    }

    static boolean instanceOfBottomNavigationItemView(Object view) {
        return ReflectUtil.isInstance(view, "com.google.android.material.bottomnavigation.BottomNavigationItemView", "com.google.android.material.internal.NavigationMenuItemView");
    }

    static boolean instanceOfActionMenuItem(Object view) {
        return ReflectUtil.isInstance(view, "androidx.appcompat.view.menu.ActionMenuItem");
    }

    static boolean instanceOfToolbar(Object view) {
        return ReflectUtil.isInstance(view, "androidx.appcompat.widget.Toolbar", "androidx.appcompat.widget.Toolbar", "android.widget.Toolbar");
    }

    private static boolean instanceOfNavigationView(Object view) {
        return ReflectUtil.isInstance(view, "com.google.android.material.navigation.NavigationView", "com.google.android.material.navigation.NavigationView");
    }

    private static boolean instanceOfSupportViewPager(Object view) {
        return ReflectUtil.isInstance(view, "androidx.viewpager.widget.ViewPager");
    }

    private static boolean instanceOfAndroidXViewPager(Object view) {
        return ReflectUtil.isInstance(view, "androidx.viewpager.widget.ViewPager");
    }

    public static boolean instanceOfWebView(Object view) {
        return (view instanceof WebView) || instanceOfX5WebView(view) || instanceOfUCWebView(view);
    }

    public static boolean instanceOfX5WebView(Object view) {
        return ReflectUtil.isInstance(view, "com.tencent.smtt.sdk.WebView");
    }

    private static boolean instanceOfUCWebView(Object view) {
        return ReflectUtil.isInstance(view, "com.alipay.mobile.nebulauc.impl.UCWebView$WebViewEx");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0012, code lost:
        r1 = sRecyclerViewClass;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean instanceOfRecyclerView(java.lang.Object r3) {
        /*
            java.lang.String r0 = "androidx.recyclerview.widget.RecyclerView"
            java.lang.String[] r0 = new java.lang.String[]{r0, r0}
            boolean r0 = com.sensorsdata.analytics.android.sdk.util.ReflectUtil.isInstance(r3, r0)
            if (r0 != 0) goto L_0x0024
            boolean r1 = sHaveCustomRecyclerView
            if (r1 == 0) goto L_0x0022
            if (r3 == 0) goto L_0x0022
            java.lang.Class<?> r1 = sRecyclerViewClass
            if (r1 == 0) goto L_0x0022
            java.lang.Class r2 = r3.getClass()
            boolean r1 = r1.isAssignableFrom(r2)
            if (r1 == 0) goto L_0x0022
            r1 = 1
            goto L_0x0023
        L_0x0022:
            r1 = 0
        L_0x0023:
            r0 = r1
        L_0x0024:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.util.ViewUtil.instanceOfRecyclerView(java.lang.Object):boolean");
    }

    private static Object instanceOfTabView(View tabView) {
        try {
            Class<?> currentTabViewClass = ReflectUtil.getCurrentClass(new String[]{"com.google.android.material.tabs.TabLayout$TabView", "com.google.android.material.tabs.TabLayout$TabView"});
            if (currentTabViewClass == null || !currentTabViewClass.isAssignableFrom(tabView.getClass())) {
                return null;
            }
            return ReflectUtil.findField(currentTabViewClass, (Object) tabView, "mTab", "tab");
        } catch (Exception e) {
            return null;
        }
    }

    private static String getCanonicalAndCheckCustomView(Class<?> clazz) {
        String name = SnapCache.getInstance().getCanonicalName(clazz);
        if (name != null) {
            checkCustomRecyclerView(clazz, name);
        }
        return name;
    }

    private static Object instanceOfFragmentRootView(View parentView, View childView) {
        Object parentFragment = AopUtil.getFragmentFromView(parentView);
        Object childFragment = AopUtil.getFragmentFromView(childView);
        if (parentFragment != null || childFragment == null) {
            return null;
        }
        return childFragment;
    }

    private static int getChildAdapterPositionInRecyclerView(View childView, ViewGroup parentView) {
        Object object;
        if (instanceOfRecyclerView(parentView)) {
            try {
                sRecyclerViewGetChildAdapterPositionMethod = parentView.getClass().getMethod("getChildAdapterPosition", new Class[]{View.class});
            } catch (NoSuchMethodException e) {
            }
            if (sRecyclerViewGetChildAdapterPositionMethod == null) {
                try {
                    sRecyclerViewGetChildAdapterPositionMethod = parentView.getClass().getMethod("getChildPosition", new Class[]{View.class});
                } catch (NoSuchMethodException e2) {
                }
            }
            try {
                Method method = sRecyclerViewGetChildAdapterPositionMethod;
                if (method == null || (object = method.invoke(parentView, new Object[]{childView})) == null) {
                    return -1;
                }
                return ((Integer) object).intValue();
            } catch (IllegalAccessException | InvocationTargetException e3) {
                return -1;
            }
        } else if (sHaveCustomRecyclerView) {
            return invokeCRVGetChildAdapterPositionMethod(parentView, childView);
        } else {
            return -1;
        }
    }

    private static int getCurrentItem(View view) {
        try {
            Object object = view.getClass().getMethod("getCurrentItem", new Class[0]).invoke(view, new Object[0]);
            if (object != null) {
                return ((Integer) object).intValue();
            }
            return -1;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return -1;
        }
    }

    static Object getItemData(View view) {
        try {
            return view.getClass().getMethod("getItemData", new Class[0]).invoke(view, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    private static boolean haveRecyclerView() {
        try {
            Class.forName("androidx.recyclerview.widget.RecyclerView");
            return true;
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("androidx.recyclerview.widget.RecyclerView");
                return true;
            } catch (ClassNotFoundException e2) {
                return false;
            }
        }
    }

    private static void checkCustomRecyclerView(Class<?> viewClass, String viewName) {
        if (!sHaveRecyclerView && !sHaveCustomRecyclerView && viewName != null && viewName.contains("RecyclerView")) {
            try {
                if (findRecyclerInSuper(viewClass) != null && sRecyclerViewGetChildAdapterPositionMethod != null) {
                    sRecyclerViewClass = viewClass;
                    sHaveCustomRecyclerView = true;
                }
            } catch (Exception e) {
            }
        }
    }

    private static Class<?> findRecyclerInSuper(Class<?> cls) {
        Class<? super Object> viewClass;
        while (viewClass != null && !viewClass.equals(ViewGroup.class)) {
            try {
                sRecyclerViewGetChildAdapterPositionMethod = viewClass.getMethod("getChildAdapterPosition", new Class[]{View.class});
            } catch (NoSuchMethodException e) {
            }
            if (sRecyclerViewGetChildAdapterPositionMethod == null) {
                try {
                    sRecyclerViewGetChildAdapterPositionMethod = viewClass.getMethod("getChildPosition", new Class[]{View.class});
                } catch (NoSuchMethodException e2) {
                }
            }
            if (sRecyclerViewGetChildAdapterPositionMethod != null) {
                return viewClass;
            }
            Class<? super Object> superclass = viewClass.getSuperclass();
            viewClass = cls;
            viewClass = superclass;
        }
        return null;
    }

    private static int invokeCRVGetChildAdapterPositionMethod(View customRecyclerView, View childView) {
        try {
            if (customRecyclerView.getClass() != sRecyclerViewClass) {
                return -1;
            }
            return ((Integer) sRecyclerViewGetChildAdapterPositionMethod.invoke(customRecyclerView, new Object[]{childView})).intValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            return -1;
        }
    }

    private static boolean isListView(View view) {
        return (view instanceof AdapterView) || instanceOfRecyclerView(view) || instanceOfAndroidXViewPager(view) || instanceOfSupportViewPager(view);
    }

    public static boolean isViewSelfVisible(View view) {
        boolean viewLocalVisiable;
        if (view == null || view.getWindowVisibility() == 8) {
            return false;
        }
        if (WindowHelper.isDecorView(view.getClass())) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            Boolean localVisibleRect = SnapCache.getInstance().getLocalVisibleRect(view);
            if (localVisibleRect == null) {
                viewLocalVisiable = view.getLocalVisibleRect(new Rect());
                SnapCache.getInstance().setLocalVisibleRect(view, Boolean.valueOf(viewLocalVisiable));
            } else {
                viewLocalVisiable = localVisibleRect.booleanValue();
            }
            if (view.getWidth() <= 0 || view.getHeight() <= 0 || view.getAlpha() <= 0.0f || !viewLocalVisiable) {
                return false;
            }
        }
        if ((view.getVisibility() == 0 || view.getAnimation() == null || !view.getAnimation().getFillAfter()) && view.getVisibility() != 0) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0013  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean viewVisibilityInParents(android.view.View r3) {
        /*
            r0 = 0
            if (r3 != 0) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = isViewSelfVisible(r3)
            if (r1 != 0) goto L_0x000b
            return r0
        L_0x000b:
            android.view.ViewParent r1 = r3.getParent()
        L_0x000f:
            boolean r2 = r1 instanceof android.view.View
            if (r2 == 0) goto L_0x0024
            r2 = r1
            android.view.View r2 = (android.view.View) r2
            boolean r2 = isViewSelfVisible(r2)
            if (r2 != 0) goto L_0x001d
            return r0
        L_0x001d:
            android.view.ViewParent r1 = r1.getParent()
            if (r1 != 0) goto L_0x000f
            return r0
        L_0x0024:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.util.ViewUtil.viewVisibilityInParents(android.view.View):boolean");
    }

    public static void invalidateLayerTypeView(View[] views) {
        if (Build.VERSION.SDK_INT >= 11) {
            for (ViewGroup viewGroup : views) {
                if (viewVisibilityInParents(viewGroup) && viewGroup.isHardwareAccelerated()) {
                    checkAndInvalidate(viewGroup);
                    if (viewGroup instanceof ViewGroup) {
                        invalidateViewGroup(viewGroup);
                    }
                }
            }
        }
    }

    private static void checkAndInvalidate(View view) {
        if (Build.VERSION.SDK_INT >= 11 && view.getLayerType() != 0) {
            view.invalidate();
        }
    }

    private static void invalidateViewGroup(ViewGroup viewGroup) {
        for (int index = 0; index < viewGroup.getChildCount(); index++) {
            View child = viewGroup.getChildAt(index);
            if (isViewSelfVisible(child)) {
                checkAndInvalidate(child);
                if (child instanceof ViewGroup) {
                    invalidateViewGroup((ViewGroup) child);
                }
            }
        }
    }

    public static int getMainWindowCount(View[] windowRootViews) {
        int mainWindowCount = 0;
        WindowHelper.init();
        for (View windowRootView : windowRootViews) {
            if (windowRootView != null) {
                mainWindowCount += WindowHelper.getWindowPrefix(windowRootView).equals(WindowHelper.getMainWindowPrefix()) ? 1 : 0;
            }
        }
        return mainWindowCount;
    }

    public static boolean isWindowNeedTraverse(View root, String prefix, boolean skipOtherActivity) {
        if (root.hashCode() == AppStateManager.getInstance().getCurrentRootWindowsHashCode()) {
            return true;
        }
        if (root instanceof ViewGroup) {
            if (!skipOtherActivity) {
                return true;
            }
            if (!(root.getWindowVisibility() == 8 || root.getVisibility() != 0 || TextUtils.equals(prefix, WindowHelper.getMainWindowPrefix()) || root.getWidth() == 0 || root.getHeight() == 0)) {
                return true;
            }
        }
        if ((root.getWindowVisibility() == 0 || root.getVisibility() == 0) && WindowHelper.isCustomWindow(root)) {
            return true;
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: android.view.View} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: android.view.ViewGroup} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.sensorsdata.analytics.android.sdk.visual.model.ViewNode getViewPathAndPosition(android.view.View r17, boolean r18) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 8
            r0.<init>(r1)
            r7 = r17
            r0.add(r7)
            android.view.ViewParent r1 = r17.getParent()
        L_0x0010:
            boolean r2 = r1 instanceof android.view.ViewGroup
            if (r2 == 0) goto L_0x001f
            r2 = r1
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            r0.add(r2)
            android.view.ViewParent r1 = r1.getParent()
            goto L_0x0010
        L_0x001f:
            int r1 = r0.size()
            int r8 = r1 + -1
            java.lang.Object r1 = r0.get(r8)
            r9 = r1
            android.view.View r9 = (android.view.View) r9
            r1 = 0
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r10 = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r11 = r3
            boolean r3 = r9 instanceof android.view.ViewGroup
            if (r3 == 0) goto L_0x00cf
            r3 = r9
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
            int r4 = r8 + -1
            r12 = r3
        L_0x0044:
            if (r4 < 0) goto L_0x00b6
            java.lang.Object r3 = r0.get(r4)
            android.view.View r3 = (android.view.View) r3
            int r5 = r12.indexOfChild(r3)
            r13 = r18
            com.sensorsdata.analytics.android.sdk.visual.model.ViewNode r6 = getViewNode(r3, r5, r13)
            if (r6 == 0) goto L_0x00a5
            java.lang.String r14 = r6.getViewPath()
            boolean r14 = android.text.TextUtils.isEmpty(r14)
            if (r14 != 0) goto L_0x008a
            java.lang.String r14 = r6.getViewPath()
            java.lang.String r15 = "-"
            boolean r14 = r14.contains(r15)
            if (r14 == 0) goto L_0x008a
            boolean r14 = android.text.TextUtils.isEmpty(r1)
            if (r14 != 0) goto L_0x008a
            int r14 = r11.indexOf(r15)
            r15 = -1
            if (r14 == r15) goto L_0x0087
            int r15 = r14 + 1
            r16 = r0
            java.lang.String r0 = java.lang.String.valueOf(r1)
            r11.replace(r14, r15, r0)
            goto L_0x008c
        L_0x0087:
            r16 = r0
            goto L_0x008c
        L_0x008a:
            r16 = r0
        L_0x008c:
            java.lang.String r0 = r6.getViewOriginalPath()
            r10.append(r0)
            java.lang.String r0 = r6.getViewPath()
            r11.append(r0)
            java.lang.String r0 = r6.getViewPosition()
            java.lang.String r1 = r6.getViewContent()
            r2 = r1
            r1 = r0
            goto L_0x00a7
        L_0x00a5:
            r16 = r0
        L_0x00a7:
            boolean r0 = r3 instanceof android.view.ViewGroup
            if (r0 != 0) goto L_0x00ae
            r0 = r1
            r14 = r2
            goto L_0x00bc
        L_0x00ae:
            r12 = r3
            android.view.ViewGroup r12 = (android.view.ViewGroup) r12
            int r4 = r4 + -1
            r0 = r16
            goto L_0x0044
        L_0x00b6:
            r13 = r18
            r16 = r0
            r0 = r1
            r14 = r2
        L_0x00bc:
            com.sensorsdata.analytics.android.sdk.visual.model.ViewNode r15 = new com.sensorsdata.analytics.android.sdk.visual.model.ViewNode
            java.lang.String r4 = r10.toString()
            java.lang.String r5 = r11.toString()
            r1 = r15
            r2 = r17
            r3 = r0
            r6 = r14
            r1.<init>(r2, r3, r4, r5, r6)
            return r15
        L_0x00cf:
            r16 = r0
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.util.ViewUtil.getViewPathAndPosition(android.view.View, boolean):com.sensorsdata.analytics.android.sdk.visual.model.ViewNode");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: android.view.ViewParent} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.view.View} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getElementSelector(android.view.View r8) {
        /*
            com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache r0 = com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache.getInstance()
            java.lang.String r0 = r0.getSelectPath(r8)
            if (r0 == 0) goto L_0x000b
            return r0
        L_0x000b:
            r1 = 0
            android.view.ViewParent r2 = r8.getParent()
            boolean r3 = r2 instanceof android.view.ViewGroup
            if (r3 == 0) goto L_0x0017
            r1 = r2
            android.view.View r1 = (android.view.View) r1
        L_0x0017:
            r3 = 0
            if (r1 == 0) goto L_0x0022
            com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache r4 = com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache.getInstance()
            java.lang.String r3 = r4.getSelectPath(r1)
        L_0x0022:
            com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache r4 = com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache.getInstance()
            java.lang.Class r5 = r8.getClass()
            java.lang.String r4 = r4.getCanonicalName(r5)
            if (r1 == 0) goto L_0x006d
            if (r3 != 0) goto L_0x003d
            java.lang.String r3 = getElementSelectorOrigin(r1)
            com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache r5 = com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache.getInstance()
            r5.setSelectPath(r1, r3)
        L_0x003d:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            if (r3 == 0) goto L_0x0054
            java.lang.String r6 = ""
            boolean r6 = r3.equals(r6)
            if (r6 != 0) goto L_0x0054
            r5.append(r3)
            java.lang.String r6 = "/"
            r5.append(r6)
        L_0x0054:
            int r6 = com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil.getChildIndex(r2, r8)
            r5.append(r4)
            java.lang.String r7 = "["
            r5.append(r7)
            r5.append(r6)
            java.lang.String r7 = "]"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            goto L_0x0071
        L_0x006d:
            java.lang.String r5 = getElementSelectorOrigin(r8)
        L_0x0071:
            com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache r6 = com.sensorsdata.analytics.android.sdk.visual.snap.SnapCache.getInstance()
            r6.setSelectPath(r8, r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.util.ViewUtil.getElementSelector(android.view.View):java.lang.String");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: android.view.ViewParent} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: android.view.View} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getElementSelectorOrigin(android.view.View r5) {
        /*
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
        L_0x0005:
            android.view.ViewParent r1 = r5.getParent()
            int r2 = com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil.getChildIndex(r1, r5)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r5.getClass()
            java.lang.String r4 = r4.getCanonicalName()
            r3.append(r4)
            java.lang.String r4 = "["
            r3.append(r4)
            r3.append(r2)
            java.lang.String r4 = "]"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.add(r3)
            boolean r3 = r1 instanceof android.view.ViewGroup
            if (r3 == 0) goto L_0x0038
            r5 = r1
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
        L_0x0038:
            boolean r2 = r1 instanceof android.view.ViewGroup
            if (r2 != 0) goto L_0x0069
            java.util.Collections.reverse(r0)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r3 = 1
        L_0x0045:
            int r4 = r0.size()
            if (r3 >= r4) goto L_0x0064
            java.lang.Object r4 = r0.get(r3)
            java.lang.String r4 = (java.lang.String) r4
            r2.append(r4)
            int r4 = r0.size()
            int r4 = r4 + -1
            if (r3 == r4) goto L_0x0061
            java.lang.String r4 = "/"
            r2.append(r4)
        L_0x0061:
            int r3 = r3 + 1
            goto L_0x0045
        L_0x0064:
            java.lang.String r3 = r2.toString()
            return r3
        L_0x0069:
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.util.ViewUtil.getElementSelectorOrigin(android.view.View):java.lang.String");
    }

    private static int getViewPosition(View view, int viewIndex) {
        int adapterPosition;
        int idx = viewIndex;
        if (view.getParent() == null || !(view.getParent() instanceof ViewGroup)) {
            return idx;
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (instanceOfAndroidXViewPager(parent) || instanceOfSupportViewPager(parent)) {
            return getCurrentItem(parent);
        }
        if (parent instanceof AdapterView) {
            return idx + ((AdapterView) parent).getFirstVisiblePosition();
        }
        if (!instanceOfRecyclerView(parent) || (adapterPosition = getChildAdapterPositionInRecyclerView(view, parent)) < 0) {
            return idx;
        }
        return adapterPosition;
    }

    public static ViewNode getViewNode(View view, int viewIndex, boolean fromVisual) {
        boolean isListView;
        String listPos;
        int i;
        String listPos2;
        View view2 = view;
        int viewPosition = getViewPosition(view, viewIndex);
        ViewParent parentObject = view.getParent();
        if (parentObject == null) {
            return null;
        }
        if ((WindowHelper.isDecorView(view.getClass()) && !(parentObject instanceof View)) || !(parentObject instanceof View)) {
            return null;
        }
        View parentView = (View) parentObject;
        StringBuilder opx = new StringBuilder();
        StringBuilder px = new StringBuilder();
        String viewName = getCanonicalAndCheckCustomView(view.getClass());
        String listPos3 = null;
        boolean isListView2 = false;
        ViewParent parent = parentView.getParent();
        if (parent instanceof View) {
            View listParentView = (View) parent;
            if (sViewCache == null) {
                sViewCache = new SparseArray<>();
            }
            String parentPos = sViewCache.get(listParentView.hashCode());
            if (!TextUtils.isEmpty(parentPos)) {
                listPos3 = parentPos;
            }
        }
        if (parentView instanceof ExpandableListView) {
            ExpandableListView listParent = (ExpandableListView) parentView;
            long elp = listParent.getExpandableListPosition(viewPosition);
            if (ExpandableListView.getPackedPositionType(elp) != 2) {
                int groupIdx = ExpandableListView.getPackedPositionGroup(elp);
                int childIdx = ExpandableListView.getPackedPositionChild(elp);
                String str = listPos3;
                if (childIdx != -1) {
                    ViewParent viewParent = parent;
                    listPos2 = String.format(Locale.CHINA, "%d:%d", new Object[]{Integer.valueOf(groupIdx), Integer.valueOf(childIdx)});
                    px.append(opx);
                    px.append("/ELVG[");
                    px.append(groupIdx);
                    px.append("]/ELVC[-]/");
                    px.append(viewName);
                    px.append("[0]");
                    opx.append("/ELVG[");
                    opx.append(groupIdx);
                    opx.append("]/ELVC[");
                    opx.append(childIdx);
                    opx.append("]/");
                    opx.append(viewName);
                    opx.append("[0]");
                } else {
                    listPos2 = String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, new Object[]{Integer.valueOf(groupIdx)});
                    px.append(opx);
                    px.append("/ELVG[-]/");
                    px.append(viewName);
                    px.append("[0]");
                    opx.append("/ELVG[");
                    opx.append(groupIdx);
                    opx.append("]/");
                    opx.append(viewName);
                    opx.append("[0]");
                }
                isListView2 = true;
            } else {
                String listPos4 = listPos3;
                ViewParent viewParent2 = parent;
                if (viewPosition < listParent.getHeaderViewsCount()) {
                    opx.append("/ELH[");
                    opx.append(viewPosition);
                    opx.append("]/");
                    opx.append(viewName);
                    opx.append("[0]");
                    px.append("/ELH[");
                    px.append(viewPosition);
                    px.append("]/");
                    px.append(viewName);
                    px.append("[0]");
                } else {
                    int footerIndex = viewPosition - (listParent.getCount() - listParent.getFooterViewsCount());
                    opx.append("/ELF[");
                    opx.append(footerIndex);
                    opx.append("]/");
                    opx.append(viewName);
                    opx.append("[0]");
                    px.append("/ELF[");
                    px.append(footerIndex);
                    px.append("]/");
                    px.append(viewName);
                    px.append("[0]");
                }
                listPos2 = listPos4;
            }
            int i2 = viewPosition;
            String str2 = viewName;
            listPos = listPos2;
            isListView = isListView2;
        } else {
            String listPos5 = listPos3;
            ViewParent viewParent3 = parent;
            if (isListView(parentView)) {
                String listPos6 = String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, new Object[]{Integer.valueOf(viewPosition)});
                px.append(opx);
                px.append("/");
                px.append(viewName);
                px.append("[-]");
                opx.append("/");
                opx.append(viewName);
                opx.append(Constants.ARRAY_TYPE);
                opx.append(listPos6);
                opx.append("]");
                int i3 = viewPosition;
                String str3 = viewName;
                listPos = listPos6;
                isListView = true;
            } else if (instanceOfSupportSwipeRefreshLayout(parentView)) {
                opx.append("/");
                opx.append(viewName);
                opx.append("[0]");
                px.append("/");
                px.append(viewName);
                px.append("[0]");
                int i4 = viewPosition;
                String str4 = viewName;
                isListView = false;
                listPos = listPos5;
            } else {
                Object instanceOfFragmentRootView = instanceOfFragmentRootView(parentView, view2);
                Object fragment = instanceOfFragmentRootView;
                if (instanceOfFragmentRootView != null) {
                    String viewName2 = getCanonicalAndCheckCustomView(fragment.getClass());
                    opx.append("/");
                    opx.append(viewName2);
                    opx.append("[0]");
                    px.append("/");
                    px.append(viewName2);
                    px.append("[0]");
                    int i5 = viewPosition;
                    String str5 = viewName2;
                    isListView = false;
                    Object obj = fragment;
                    listPos = listPos5;
                } else {
                    int viewPosition2 = VisualUtil.getChildIndex(parentObject, view2);
                    opx.append("/");
                    opx.append(viewName);
                    opx.append(Constants.ARRAY_TYPE);
                    opx.append(viewPosition2);
                    opx.append("]");
                    px.append("/");
                    px.append(viewName);
                    px.append(Constants.ARRAY_TYPE);
                    px.append(viewPosition2);
                    px.append("]");
                    int i6 = viewPosition2;
                    String str6 = viewName;
                    isListView = false;
                    Object obj2 = fragment;
                    listPos = listPos5;
                }
            }
        }
        if (WindowHelper.isDecorView(parentView.getClass())) {
            if (opx.length() > 0) {
                i = 0;
                opx.deleteCharAt(0);
            } else {
                i = 0;
            }
            if (px.length() > 0) {
                px.deleteCharAt(i);
            }
        }
        if (!TextUtils.isEmpty(listPos)) {
            if (sViewCache == null) {
                sViewCache = new SparseArray<>();
            }
            sViewCache.put(parentView.hashCode(), listPos);
        }
        ViewNode viewNode = getViewContentAndType(view2, fromVisual);
        return new ViewNode(view, listPos, opx.toString(), px.toString(), viewNode.getViewContent(), viewNode.getViewType(), isListView);
    }

    public static void clear() {
        SparseArray<String> sparseArray = sViewCache;
        if (sparseArray != null) {
            sparseArray.clear();
        }
    }

    static boolean isTrackEvent(View view, boolean isFromUser) {
        if (view instanceof CheckBox) {
            if (!isFromUser) {
                return false;
            }
        } else if (view instanceof RadioButton) {
            if (!isFromUser) {
                return false;
            }
        } else if (view instanceof ToggleButton) {
            if (!isFromUser) {
                return false;
            }
        } else if ((view instanceof CompoundButton) && !isFromUser) {
            return false;
        }
        if (!(view instanceof RatingBar) || isFromUser) {
            return true;
        }
        return false;
    }

    public static ViewNode getViewContentAndType(View view) {
        return getViewContentAndType(view, false);
    }

    public static ViewNode getViewContentAndType(View view, boolean fromVisual) {
        String viewType;
        String cacheViewType = SnapCache.getInstance().getViewType(view);
        String cacheViewText = SnapCache.getInstance().getViewText(view);
        CharSequence viewText = null;
        if (cacheViewType == null || cacheViewText == null) {
            viewType = SnapCache.getInstance().getCanonicalName(view.getClass());
            if (view instanceof CheckBox) {
                viewType = AopUtil.getViewType(viewType, "CheckBox");
                viewText = ((CheckBox) view).getText();
            } else if (view instanceof RadioButton) {
                viewType = AopUtil.getViewType(viewType, "RadioButton");
                viewText = ((RadioButton) view).getText();
            } else if (view instanceof ToggleButton) {
                viewType = AopUtil.getViewType(viewType, "ToggleButton");
                viewText = AopUtil.getCompoundButtonText(view);
            } else if (view instanceof CompoundButton) {
                viewType = AopUtil.getViewTypeByReflect(view);
                viewText = AopUtil.getCompoundButtonText(view);
            } else if (view instanceof Button) {
                viewType = AopUtil.getViewType(viewType, "Button");
                viewText = ((Button) view).getText();
            } else if (view instanceof CheckedTextView) {
                viewType = AopUtil.getViewType(viewType, "CheckedTextView");
                viewText = ((CheckedTextView) view).getText();
            } else if (view instanceof TextView) {
                viewType = AopUtil.getViewType(viewType, "TextView");
                viewText = ((TextView) view).getText();
            } else if (view instanceof ImageView) {
                viewType = AopUtil.getViewType(viewType, "ImageView");
                ImageView imageView = (ImageView) view;
                if (!TextUtils.isEmpty(imageView.getContentDescription())) {
                    viewText = imageView.getContentDescription().toString();
                }
            } else if (view instanceof RatingBar) {
                viewType = AopUtil.getViewType(viewType, "RatingBar");
                viewText = String.valueOf(((RatingBar) view).getRating());
            } else if (view instanceof SeekBar) {
                viewType = AopUtil.getViewType(viewType, "SeekBar");
                viewText = String.valueOf(((SeekBar) view).getProgress());
            } else if (view instanceof Spinner) {
                viewType = AopUtil.getViewType(viewType, "Spinner");
                try {
                    viewText = AopUtil.traverseView(new StringBuilder(), (ViewGroup) view);
                    if (!TextUtils.isEmpty(viewText)) {
                        viewText = viewText.toString().substring(0, viewText.length() - 1);
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            } else {
                Object instanceOfTabView = instanceOfTabView(view);
                Object tab = instanceOfTabView;
                if (instanceOfTabView != null) {
                    viewText = getTabLayoutContent(tab);
                    viewType = AopUtil.getViewType(viewType, "TabLayout");
                } else if (instanceOfBottomNavigationItemView(view)) {
                    Object itemData = getItemData(view);
                    if (itemData != null) {
                        try {
                            Class<?> menuItemImplClass = ReflectUtil.getCurrentClass(new String[]{"androidx.appcompat.view.menu.MenuItemImpl"});
                            if (menuItemImplClass != null) {
                                String title = (String) ReflectUtil.findField(menuItemImplClass, itemData, "mTitle");
                                if (!TextUtils.isEmpty(title)) {
                                    viewText = title;
                                }
                            }
                        } catch (Exception e2) {
                        }
                    }
                } else if (instanceOfNavigationView(view)) {
                    viewText = isViewSelfVisible(view) ? "Open" : "Close";
                    viewType = AopUtil.getViewType(viewType, "NavigationView");
                } else if (view instanceof ViewGroup) {
                    viewType = AopUtil.getViewGroupTypeByReflect(view);
                    viewText = view.getContentDescription();
                    if (TextUtils.isEmpty(viewText)) {
                        try {
                            viewText = AopUtil.traverseView(new StringBuilder(), (ViewGroup) view);
                            if (!TextUtils.isEmpty(viewText)) {
                                viewText = viewText.toString().substring(0, viewText.length() - 1);
                            }
                        } catch (Exception e3) {
                        }
                    }
                }
            }
            if (TextUtils.isEmpty(viewText) && (view instanceof TextView)) {
                viewText = ((TextView) view).getHint();
            }
            if (TextUtils.isEmpty(viewText)) {
                viewText = view.getContentDescription();
            }
            if (viewText == null) {
                viewText = "";
            }
            SnapCache.getInstance().setViewType(view, viewType);
            SnapCache.getInstance().setViewText(view, viewText.toString());
        } else {
            viewText = cacheViewText;
            viewType = cacheViewType;
        }
        if (view instanceof EditText) {
            if (fromVisual) {
                viewText = ((EditText) view).getText();
            } else {
                viewText = "";
            }
        }
        if (viewText == null) {
            viewText = "";
        }
        return new ViewNode(viewText.toString(), viewType);
    }

    private static String getTabLayoutContent(Object tab) {
        String viewText = null;
        try {
            Class<?> currentTabClass = ReflectUtil.getCurrentClass(new String[]{"com.google.android.material.tabs.TabLayout$Tab", "com.google.android.material.tabs.TabLayout$Tab"});
            if (currentTabClass == null) {
                return null;
            }
            Object text = ReflectUtil.callMethod(tab, "getText", new Object[0]);
            if (text != null) {
                viewText = text.toString();
            }
            View customView = (View) ReflectUtil.findField(currentTabClass, tab, "mCustomView", "customView");
            if (customView == null) {
                return viewText;
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (!(customView instanceof ViewGroup)) {
                return AopUtil.getViewText(customView);
            }
            String viewText2 = AopUtil.traverseView(stringBuilder, (ViewGroup) customView);
            if (!TextUtils.isEmpty(viewText2)) {
                return viewText2.toString().substring(0, viewText2.length() - 1);
            }
            return viewText2;
        } catch (Exception e) {
            return null;
        }
    }
}
