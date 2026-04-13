package com.telink.ble.mesh.core.ble;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BleReflexHelper {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean e() {
        Object iGatt;
        Method stopScan;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 12253, new Class[0], cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            Object mIBluetoothManager = d(BluetoothAdapter.getDefaultAdapter());
            if (mIBluetoothManager == null || (iGatt = c(mIBluetoothManager)) == null) {
                return false;
            }
            Class cls2 = Integer.TYPE;
            Method unregisterClient = b(iGatt, "unregisterClient", cls2);
            Exception e = null;
            try {
                stopScan = b(iGatt, "stopScan", cls2, cls);
            } catch (Exception e2) {
                stopScan = b(iGatt, "stopScan", Integer.TYPE);
                e = 1;
            }
            for (int mClientIf = 0; mClientIf <= 40; mClientIf++) {
                if (e == null) {
                    try {
                        stopScan.invoke(iGatt, new Object[]{Integer.valueOf(mClientIf), false});
                    } catch (Exception e3) {
                    }
                }
                if (e == 1) {
                    try {
                        stopScan.invoke(iGatt, new Object[]{Integer.valueOf(mClientIf)});
                    } catch (Exception e4) {
                    }
                }
                try {
                    unregisterClient.invoke(iGatt, new Object[]{Integer.valueOf(mClientIf)});
                } catch (Exception e5) {
                }
            }
            stopScan.setAccessible(false);
            unregisterClient.setAccessible(false);
            return true;
        } catch (Exception e6) {
            e6.printStackTrace();
            return false;
        }
    }

    @SuppressLint({"PrivateApi"})
    public static Object c(Object mIBluetoothManager) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mIBluetoothManager}, (Object) null, changeQuickRedirect, true, 12254, new Class[]{Object.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        Method getBluetoothGatt = b(mIBluetoothManager, "getBluetoothGatt", new Class[0]);
        Object object = new Object();
        try {
            return getBluetoothGatt.invoke(mIBluetoothManager, new Object[0]);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return object;
        }
    }

    @SuppressLint({"PrivateApi"})
    public static Object d(BluetoothAdapter adapter) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{adapter}, (Object) null, changeQuickRedirect, true, 12255, new Class[]{BluetoothAdapter.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        return a(BluetoothAdapter.class, "getBluetoothManager", new Class[0]).invoke(adapter, new Object[0]);
    }

    public static Method a(Class<?> clazz, String name, Class<?>... parameterTypes) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{clazz, name, parameterTypes}, (Object) null, changeQuickRedirect2, true, 12257, new Class[]{Class.class, String.class, Class[].class}, Method.class);
        if (proxy.isSupported) {
            return (Method) proxy.result;
        }
        Method declaredMethod = clazz.getDeclaredMethod(name, parameterTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    public static Method b(Object obj, String name, Class<?>... parameterTypes) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, name, parameterTypes}, (Object) null, changeQuickRedirect2, true, 12259, new Class[]{Object.class, String.class, Class[].class}, Method.class);
        if (proxy.isSupported) {
            return (Method) proxy.result;
        }
        Method declaredMethod = obj.getClass().getDeclaredMethod(name, parameterTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }
}
