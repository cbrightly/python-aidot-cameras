package com.didichuxing.doraemonkit.kit.gpsmock;

import android.os.IBinder;
import java.lang.reflect.InvocationHandler;

public class BinderHookHandler implements InvocationHandler {
    private static final String TAG = "BinderHookHandler";
    private BaseServiceHooker mHooker;
    private IBinder mOriginService;

    public BinderHookHandler(IBinder binder, BaseServiceHooker hooker) {
        this.mOriginService = binder;
        this.mHooker = hooker;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    @android.annotation.SuppressLint({"PrivateApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object invoke(java.lang.Object r6, java.lang.reflect.Method r7, java.lang.Object[] r8) {
        /*
            r5 = this;
            java.lang.String r0 = r7.getName()
            int r1 = r0.hashCode()
            r2 = 0
            switch(r1) {
                case -554320650: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x0017
        L_0x000d:
            java.lang.String r1 = "queryLocalInterface"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000c
            r0 = r2
            goto L_0x0018
        L_0x0017:
            r0 = -1
        L_0x0018:
            switch(r0) {
                case 0: goto L_0x0022;
                default: goto L_0x001b;
            }
        L_0x001b:
            android.os.IBinder r0 = r5.mOriginService
            java.lang.Object r0 = r7.invoke(r0, r8)
            return r0
        L_0x0022:
            r0 = r8[r2]     // Catch:{ ClassNotFoundException -> 0x004e }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ ClassNotFoundException -> 0x004e }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x004e }
            android.os.IBinder r1 = r5.mOriginService
            java.lang.Class r1 = r1.getClass()
            java.lang.ClassLoader r1 = r1.getClassLoader()
            r3 = 3
            java.lang.Class[] r3 = new java.lang.Class[r3]
            java.lang.Class<android.os.IInterface> r4 = android.os.IInterface.class
            r3[r2] = r4
            r2 = 1
            java.lang.Class<android.os.IBinder> r4 = android.os.IBinder.class
            r3[r2] = r4
            r2 = 2
            r3[r2] = r0
            r2 = r3
            com.didichuxing.doraemonkit.kit.gpsmock.BaseServiceHooker r3 = r5.mHooker
            java.lang.Object r3 = java.lang.reflect.Proxy.newProxyInstance(r1, r2, r3)
            return r3
        L_0x004e:
            r0 = move-exception
            r0.printStackTrace()
            android.os.IBinder r1 = r5.mOriginService
            java.lang.Object r1 = r7.invoke(r1, r8)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.gpsmock.BinderHookHandler.invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[]):java.lang.Object");
    }
}
