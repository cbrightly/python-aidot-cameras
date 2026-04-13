package com.didichuxing.doraemonkit.kit.gpsmock;

import android.annotation.SuppressLint;
import android.os.IBinder;
import com.didichuxing.doraemonkit.util.LogHelper;
import java.lang.reflect.InvocationHandler;

public class LocationHookHandler implements InvocationHandler {
    private static final String TAG = "LocationHookHandler";
    private Object mOriginService;

    @SuppressLint({"PrivateApi"})
    public LocationHookHandler(IBinder binder) {
        try {
            this.mOriginService = Class.forName("android.location.ILocationManager$Stub").getDeclaredMethod("asInterface", new Class[]{IBinder.class}).invoke((Object) null, new Object[]{binder});
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.e(TAG, e.toString());
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object invoke(java.lang.Object r10, java.lang.reflect.Method r11, java.lang.Object[] r12) {
        /*
            r9 = this;
            java.lang.String r0 = r11.getName()
            int r1 = r0.hashCode()
            r2 = 0
            r3 = 1
            switch(r1) {
                case -874501946: goto L_0x0022;
                case -3531668: goto L_0x0018;
                case 1807102689: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x002c
        L_0x000e:
            java.lang.String r1 = "getLastLocation"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = r3
            goto L_0x002d
        L_0x0018:
            java.lang.String r1 = "getLastKnownLocation"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 2
            goto L_0x002d
        L_0x0022:
            java.lang.String r1 = "requestLocationUpdates"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = r2
            goto L_0x002d
        L_0x002c:
            r0 = -1
        L_0x002d:
            switch(r0) {
                case 0: goto L_0x0087;
                case 1: goto L_0x005d;
                case 2: goto L_0x0032;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x00bb
        L_0x0032:
            com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager r0 = com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager.getInstance()
            boolean r0 = r0.isMocking()
            if (r0 != 0) goto L_0x003e
            goto L_0x00bb
        L_0x003e:
            java.lang.Object r0 = r9.mOriginService
            java.lang.Object r0 = r11.invoke(r0, r12)
            android.location.Location r0 = (android.location.Location) r0
            com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager r1 = com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager.getInstance()
            double r1 = r1.getLongitude()
            r0.setLongitude(r1)
            com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager r1 = com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager.getInstance()
            double r1 = r1.getLatitude()
            r0.setLatitude(r1)
            return r0
        L_0x005d:
            com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager r0 = com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager.getInstance()
            boolean r0 = r0.isMocking()
            if (r0 != 0) goto L_0x0068
            goto L_0x00bb
        L_0x0068:
            java.lang.Object r0 = r9.mOriginService
            java.lang.Object r0 = r11.invoke(r0, r12)
            android.location.Location r0 = (android.location.Location) r0
            com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager r1 = com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager.getInstance()
            double r1 = r1.getLongitude()
            r0.setLongitude(r1)
            com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager r1 = com.didichuxing.doraemonkit.kit.gpsmock.GpsMockManager.getInstance()
            double r1 = r1.getLatitude()
            r0.setLatitude(r1)
            return r0
        L_0x0087:
            r0 = r12[r3]
            java.lang.Class r0 = r0.getClass()
            java.lang.reflect.Field[] r0 = r0.getDeclaredFields()
            int r1 = r0.length
            r4 = r2
        L_0x0093:
            if (r4 >= r1) goto L_0x00ba
            r5 = r0[r4]
            java.lang.Class r6 = r5.getType()
            java.lang.Class<android.location.LocationListener> r7 = android.location.LocationListener.class
            if (r6 != r7) goto L_0x00b7
            r5.setAccessible(r3)
            r6 = r12[r3]
            java.lang.Object r6 = r5.get(r6)
            android.location.LocationListener r6 = (android.location.LocationListener) r6
            com.didichuxing.doraemonkit.kit.gpsmock.LocationHookHandler$1 r7 = new com.didichuxing.doraemonkit.kit.gpsmock.LocationHookHandler$1
            r7.<init>(r6)
            r8 = r12[r3]
            r5.set(r8, r7)
            r5.setAccessible(r2)
        L_0x00b7:
            int r4 = r4 + 1
            goto L_0x0093
        L_0x00ba:
        L_0x00bb:
            java.lang.Object r0 = r9.mOriginService
            java.lang.Object r0 = r11.invoke(r0, r12)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.gpsmock.LocationHookHandler.invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[]):java.lang.Object");
    }
}
