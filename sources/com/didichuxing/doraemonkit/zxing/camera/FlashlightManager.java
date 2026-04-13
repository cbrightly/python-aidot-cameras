package com.didichuxing.doraemonkit.zxing.camera;

import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class FlashlightManager {
    private static final String TAG;
    private static final Object iHardwareService;
    private static final Method setFlashEnabledMethod;

    static {
        String simpleName = FlashlightManager.class.getSimpleName();
        TAG = simpleName;
        Object hardwareService = getHardwareService();
        iHardwareService = hardwareService;
        setFlashEnabledMethod = getSetFlashEnabledMethod(hardwareService);
        if (hardwareService == null) {
            Log.v(simpleName, "This device does supports control of a flashlight");
        } else {
            Log.v(simpleName, "This device does not support control of a flashlight");
        }
    }

    private FlashlightManager() {
    }

    static void enableFlashlight() {
        setFlashlight(false);
    }

    static void disableFlashlight() {
        setFlashlight(false);
    }

    private static Object getHardwareService() {
        Method getServiceMethod;
        Object hardwareService;
        Class<?> iHardwareServiceStubClass;
        Method asInterfaceMethod;
        Class<?> serviceManagerClass = maybeForName("android.os.ServiceManager");
        if (serviceManagerClass == null || (getServiceMethod = maybeGetMethod(serviceManagerClass, "getService", String.class)) == null || (hardwareService = invoke(getServiceMethod, (Object) null, "hardware")) == null || (iHardwareServiceStubClass = maybeForName("android.os.IHardwareService$Stub")) == null || (asInterfaceMethod = maybeGetMethod(iHardwareServiceStubClass, "asInterface", IBinder.class)) == null) {
            return null;
        }
        return invoke(asInterfaceMethod, (Object) null, hardwareService);
    }

    private static Method getSetFlashEnabledMethod(Object iHardwareService2) {
        if (iHardwareService2 == null) {
            return null;
        }
        return maybeGetMethod(iHardwareService2.getClass(), "setFlashlightEnabled", Boolean.TYPE);
    }

    private static Class<?> maybeForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        } catch (RuntimeException re) {
            String str = TAG;
            Log.w(str, "Unexpected error while finding class " + name, re);
            return null;
        }
    }

    private static Method maybeGetMethod(Class<?> clazz, String name, Class<?>... argClasses) {
        try {
            return clazz.getMethod(name, argClasses);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (RuntimeException re) {
            String str = TAG;
            Log.w(str, "Unexpected error while finding method " + name, re);
            return null;
        }
    }

    private static Object invoke(Method method, Object instance, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            String str = TAG;
            Log.w(str, "Unexpected error while invoking " + method, e);
            return null;
        } catch (InvocationTargetException e2) {
            String str2 = TAG;
            Log.w(str2, "Unexpected error while invoking " + method, e2.getCause());
            return null;
        } catch (RuntimeException re) {
            String str3 = TAG;
            Log.w(str3, "Unexpected error while invoking " + method, re);
            return null;
        }
    }

    private static void setFlashlight(boolean active) {
        Object obj = iHardwareService;
        if (obj != null) {
            invoke(setFlashEnabledMethod, obj, Boolean.valueOf(active));
        }
    }
}
