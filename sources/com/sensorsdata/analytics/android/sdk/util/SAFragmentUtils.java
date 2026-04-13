package com.sensorsdata.analytics.android.sdk.util;

public class SAFragmentUtils {
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005a A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isFragmentVisible(java.lang.Object r5) {
        /*
            r0 = 0
            r1 = 0
            java.lang.Class r2 = r5.getClass()     // Catch:{ Exception -> 0x0016 }
            java.lang.String r3 = "getParentFragment"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x0016 }
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{ Exception -> 0x0016 }
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0016 }
            java.lang.Object r3 = r2.invoke(r5, r3)     // Catch:{ Exception -> 0x0016 }
            r0 = r3
            goto L_0x001a
        L_0x0016:
            r2 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r2)
        L_0x001a:
            r2 = 1
            if (r0 != 0) goto L_0x0030
            boolean r3 = fragmentIsHidden(r5)     // Catch:{ Exception -> 0x0056 }
            if (r3 != 0) goto L_0x0055
            boolean r3 = fragmentGetUserVisibleHint(r5)     // Catch:{ Exception -> 0x0056 }
            if (r3 == 0) goto L_0x0055
            boolean r3 = fragmentIsResumed(r5)     // Catch:{ Exception -> 0x0056 }
            if (r3 == 0) goto L_0x0055
            return r2
        L_0x0030:
            boolean r3 = fragmentIsHidden(r5)     // Catch:{ Exception -> 0x0056 }
            if (r3 != 0) goto L_0x0055
            boolean r3 = fragmentGetUserVisibleHint(r5)     // Catch:{ Exception -> 0x0056 }
            if (r3 == 0) goto L_0x0055
            boolean r3 = fragmentIsResumed(r5)     // Catch:{ Exception -> 0x0056 }
            if (r3 == 0) goto L_0x0055
            boolean r3 = fragmentIsHidden(r0)     // Catch:{ Exception -> 0x0056 }
            if (r3 != 0) goto L_0x0055
            boolean r3 = fragmentGetUserVisibleHint(r0)     // Catch:{ Exception -> 0x0056 }
            if (r3 == 0) goto L_0x0055
            boolean r3 = fragmentIsResumed(r0)     // Catch:{ Exception -> 0x0056 }
            if (r3 == 0) goto L_0x0055
            return r2
        L_0x0055:
            goto L_0x005a
        L_0x0056:
            r2 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r2)
        L_0x005a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.util.SAFragmentUtils.isFragmentVisible(java.lang.Object):boolean");
    }

    public static boolean fragmentGetUserVisibleHint(Object fragment) {
        try {
            return ((Boolean) fragment.getClass().getMethod("getUserVisibleHint", new Class[0]).invoke(fragment, new Object[0])).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean fragmentIsHidden(Object fragment) {
        try {
            return ((Boolean) fragment.getClass().getMethod("isHidden", new Class[0]).invoke(fragment, new Object[0])).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFragment(Object object) {
        if (object == null) {
            return false;
        }
        Class<?> supportFragmentClass = null;
        Class<?> androidXFragmentClass = null;
        Class<?> fragment = null;
        try {
            fragment = Class.forName("android.app.Fragment");
        } catch (Exception e) {
        }
        try {
            supportFragmentClass = Class.forName("androidx.fragment.app.Fragment");
        } catch (Exception e2) {
        }
        try {
            androidXFragmentClass = Class.forName("androidx.fragment.app.Fragment");
        } catch (Exception e3) {
        }
        if (supportFragmentClass == null && androidXFragmentClass == null && fragment == null) {
            return false;
        }
        if (supportFragmentClass != null) {
            try {
                if (supportFragmentClass.isInstance(object)) {
                    return true;
                }
            } catch (Exception e4) {
            }
        }
        if (androidXFragmentClass != null && androidXFragmentClass.isInstance(object)) {
            return true;
        }
        if (fragment == null || !fragment.isInstance(object)) {
            return false;
        }
        return true;
    }

    public static boolean fragmentIsResumed(Object fragment) {
        try {
            return ((Boolean) fragment.getClass().getMethod("isResumed", new Class[0]).invoke(fragment, new Object[0])).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }
}
