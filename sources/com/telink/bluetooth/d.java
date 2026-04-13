package com.telink.bluetooth;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedWriter;
import java.io.IOException;

/* compiled from: TelinkLog */
public class d {
    public static boolean a = true;
    public static boolean b = false;
    public static String c = "TelinkBluetoothSDKLogger";
    public static ChangeQuickRedirect changeQuickRedirect;
    private static BufferedWriter d;

    public static String c(Throwable th) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{th}, (Object) null, changeQuickRedirect, true, 13481, new Class[]{Throwable.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (a) {
            return Log.getStackTraceString(th);
        }
        return th.getMessage();
    }

    public static int a(String msg) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 13485, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        e(msg);
        if (a) {
            return Log.d("TelinkBluetoothSDK", msg);
        }
        return 0;
    }

    public static int b(String msg, Throwable th) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{msg, th}, (Object) null, changeQuickRedirect, true, 13486, new Class[]{String.class, Throwable.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        e(msg);
        e(c(th));
        if (a) {
            return Log.d("TelinkBluetoothSDK", msg, th);
        }
        return 0;
    }

    public static int d(String msg) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 13489, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        e(msg);
        if (a) {
            return Log.w("TelinkBluetoothSDK", msg);
        }
        return 0;
    }

    private static void e(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 13494, new Class[]{String.class}, Void.TYPE).isSupported && b) {
            try {
                d.write(log);
                d.newLine();
                d.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
