package com.didichuxing.doraemonkit.reflection;

import android.content.Context;
import android.os.Build;
import java.lang.reflect.Method;

public class Reflection {
    private static final int ERROR_EXEMPT_FAILED = -21;
    private static final int ERROR_SET_APPLICATION_FAILED = -20;
    private static final String TAG = "Reflection";
    private static int UNKNOWN = -9999;
    private static Object sVmRuntime;
    private static Method setHiddenApiExemptions;
    private static int unsealed = -9999;

    private static native int unsealNative(int i);

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            java.lang.Class<java.lang.Class> r1 = java.lang.Class.class
            java.lang.String r2 = "forName"
            r3 = 1
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ all -> 0x005b }
            r5 = 0
            r4[r5] = r0     // Catch:{ all -> 0x005b }
            java.lang.reflect.Method r1 = r1.getDeclaredMethod(r2, r4)     // Catch:{ all -> 0x005b }
            java.lang.Class<java.lang.Class> r2 = java.lang.Class.class
            java.lang.String r4 = "getDeclaredMethod"
            r6 = 2
            java.lang.Class[] r7 = new java.lang.Class[r6]     // Catch:{ all -> 0x005b }
            r7[r5] = r0     // Catch:{ all -> 0x005b }
            java.lang.Class<java.lang.Class[]> r0 = java.lang.Class[].class
            r7[r3] = r0     // Catch:{ all -> 0x005b }
            java.lang.reflect.Method r0 = r2.getDeclaredMethod(r4, r7)     // Catch:{ all -> 0x005b }
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ all -> 0x005b }
            java.lang.String r4 = "dalvik.system.VMRuntime"
            r2[r5] = r4     // Catch:{ all -> 0x005b }
            r4 = 0
            java.lang.Object r2 = r1.invoke(r4, r2)     // Catch:{ all -> 0x005b }
            java.lang.Class r2 = (java.lang.Class) r2     // Catch:{ all -> 0x005b }
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ all -> 0x005b }
            java.lang.String r8 = "getRuntime"
            r7[r5] = r8     // Catch:{ all -> 0x005b }
            r7[r3] = r4     // Catch:{ all -> 0x005b }
            java.lang.Object r7 = r0.invoke(r2, r7)     // Catch:{ all -> 0x005b }
            java.lang.reflect.Method r7 = (java.lang.reflect.Method) r7     // Catch:{ all -> 0x005b }
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x005b }
            java.lang.String r8 = "setHiddenApiExemptions"
            r6[r5] = r8     // Catch:{ all -> 0x005b }
            java.lang.Class[] r8 = new java.lang.Class[r3]     // Catch:{ all -> 0x005b }
            java.lang.Class<java.lang.String[]> r9 = java.lang.String[].class
            r8[r5] = r9     // Catch:{ all -> 0x005b }
            r6[r3] = r8     // Catch:{ all -> 0x005b }
            java.lang.Object r3 = r0.invoke(r2, r6)     // Catch:{ all -> 0x005b }
            java.lang.reflect.Method r3 = (java.lang.reflect.Method) r3     // Catch:{ all -> 0x005b }
            setHiddenApiExemptions = r3     // Catch:{ all -> 0x005b }
            java.lang.Object[] r3 = new java.lang.Object[r5]     // Catch:{ all -> 0x005b }
            java.lang.Object r3 = r7.invoke(r4, r3)     // Catch:{ all -> 0x005b }
            sVmRuntime = r3     // Catch:{ all -> 0x005b }
            goto L_0x005c
        L_0x005b:
            r0 = move-exception
        L_0x005c:
            r0 = -9999(0xffffffffffffd8f1, float:NaN)
            UNKNOWN = r0
            unsealed = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.reflection.Reflection.<clinit>():void");
    }

    public static int unseal(Context context) {
        if (Build.VERSION.SDK_INT >= 28 && !exemptAll()) {
            return ERROR_EXEMPT_FAILED;
        }
        return 0;
    }

    public static boolean exempt(String method) {
        return exempt(method);
    }

    public static boolean exempt(String... methods) {
        Method method;
        Object obj = sVmRuntime;
        if (obj == null || (method = setHiddenApiExemptions) == null) {
            return false;
        }
        try {
            method.invoke(obj, new Object[]{methods});
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean exemptAll() {
        return exempt("L");
    }
}
