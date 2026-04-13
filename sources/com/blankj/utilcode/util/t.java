package com.blankj.utilcode.util;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.annotation.RequiresPermission;

/* compiled from: PhoneUtils */
public final class t {
    @RequiresPermission("android.permission.READ_PHONE_STATE")
    public static String a() {
        return b(true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:66:0x00df A[RETURN] */
    @androidx.annotation.RequiresPermission("android.permission.READ_PHONE_STATE")
    @android.annotation.SuppressLint({"HardwareIds"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(boolean r13) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            java.lang.String r1 = ""
            r2 = 29
            if (r0 < r2) goto L_0x0009
            return r1
        L_0x0009:
            android.telephony.TelephonyManager r2 = e()
            r3 = 26
            r4 = 1
            r5 = 0
            if (r0 < r3) goto L_0x002f
            if (r13 == 0) goto L_0x0022
            java.lang.String r0 = r2.getImei(r5)
            java.lang.String r1 = r2.getImei(r4)
            java.lang.String r0 = c(r0, r1)
            return r0
        L_0x0022:
            java.lang.String r0 = r2.getMeid(r5)
            java.lang.String r1 = r2.getMeid(r4)
            java.lang.String r0 = c(r0, r1)
            return r0
        L_0x002f:
            r3 = 21
            r6 = 15
            r7 = 14
            if (r0 < r3) goto L_0x00c7
            if (r13 == 0) goto L_0x003c
            java.lang.String r0 = "ril.gsm.imei"
            goto L_0x003e
        L_0x003c:
            java.lang.String r0 = "ril.cdma.meid"
        L_0x003e:
            java.lang.String r0 = d(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r3 = 2
            if (r1 != 0) goto L_0x005e
            java.lang.String r1 = ","
            java.lang.String[] r1 = r0.split(r1)
            int r6 = r1.length
            if (r6 != r3) goto L_0x005b
            r3 = r1[r5]
            r4 = r1[r4]
            java.lang.String r3 = c(r3, r4)
            return r3
        L_0x005b:
            r3 = r1[r5]
            return r3
        L_0x005e:
            java.lang.String r1 = r2.getDeviceId()
            java.lang.String r8 = ""
            java.lang.Class r9 = r2.getClass()     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            java.lang.String r10 = "getDeviceId"
            java.lang.Class[] r11 = new java.lang.Class[r4]     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            java.lang.Class r12 = java.lang.Integer.TYPE     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            r11[r5] = r12     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            java.lang.reflect.Method r9 = r9.getMethod(r10, r11)     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            if (r13 == 0) goto L_0x0079
            goto L_0x007a
        L_0x0079:
            r4 = r3
        L_0x007a:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            r10[r5] = r3     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            java.lang.Object r3 = r9.invoke(r2, r10)     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ NoSuchMethodException -> 0x0092, IllegalAccessException -> 0x008d, InvocationTargetException -> 0x0088 }
            r8 = r3
        L_0x0087:
            goto L_0x0097
        L_0x0088:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0097
        L_0x008d:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0087
        L_0x0092:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0087
        L_0x0097:
            if (r13 == 0) goto L_0x00ae
            if (r1 == 0) goto L_0x00a3
            int r3 = r1.length()
            if (r3 >= r6) goto L_0x00a3
            java.lang.String r1 = ""
        L_0x00a3:
            if (r8 == 0) goto L_0x00c2
            int r3 = r8.length()
            if (r3 >= r6) goto L_0x00c2
            java.lang.String r8 = ""
            goto L_0x00c2
        L_0x00ae:
            if (r1 == 0) goto L_0x00b8
            int r3 = r1.length()
            if (r3 != r7) goto L_0x00b8
            java.lang.String r1 = ""
        L_0x00b8:
            if (r8 == 0) goto L_0x00c2
            int r3 = r8.length()
            if (r3 != r7) goto L_0x00c2
            java.lang.String r8 = ""
        L_0x00c2:
            java.lang.String r3 = c(r1, r8)
            return r3
        L_0x00c7:
            java.lang.String r0 = r2.getDeviceId()
            if (r13 == 0) goto L_0x00d6
            if (r0 == 0) goto L_0x00df
            int r3 = r0.length()
            if (r3 < r6) goto L_0x00df
            return r0
        L_0x00d6:
            if (r0 == 0) goto L_0x00df
            int r3 = r0.length()
            if (r3 != r7) goto L_0x00df
            return r0
        L_0x00df:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.t.b(boolean):java.lang.String");
    }

    private static String c(String s0, String s1) {
        boolean empty0 = TextUtils.isEmpty(s0);
        boolean empty1 = TextUtils.isEmpty(s1);
        if (empty0 && empty1) {
            return "";
        }
        if (empty0 || empty1) {
            if (!empty0) {
                return s0;
            }
            return s1;
        } else if (s0.compareTo(s1) <= 0) {
            return s0;
        } else {
            return s1;
        }
    }

    private static String d(String key) {
        Class<String> cls = String.class;
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            return (String) clz.getMethod("get", new Class[]{cls, cls}).invoke(clz, new Object[]{key, ""});
        } catch (Exception e) {
            return "";
        }
    }

    private static TelephonyManager e() {
        return (TelephonyManager) f0.a().getSystemService("phone");
    }
}
