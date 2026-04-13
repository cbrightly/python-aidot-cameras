package com.didichuxing.doraemonkit.kit.timecounter.instrumentation;

import android.app.Application;
import android.os.Handler;
import com.didichuxing.doraemonkit.reflection.Reflection;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HandlerHooker {
    private static final String TAG = "HandlerHooker";
    private static boolean isHookSucceed = false;

    public static void doHook(Application app) {
        try {
            if (!isHookSucceed()) {
                Reflection.unseal(app);
                hookInstrumentation();
                isHookSucceed = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean isHookSucceed() {
        return isHookSucceed;
    }

    private static void hookInstrumentation() {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread", new Class[0]);
        boolean acc = currentActivityThreadMethod.isAccessible();
        if (!acc) {
            currentActivityThreadMethod.setAccessible(true);
        }
        Object currentActivityThreadObj = currentActivityThreadMethod.invoke((Object) null, new Object[0]);
        if (!acc) {
            currentActivityThreadMethod.setAccessible(acc);
        }
        Field handlerField = activityThreadClass.getDeclaredField("mH");
        boolean acc2 = handlerField.isAccessible();
        if (!acc2) {
            handlerField.setAccessible(true);
        }
        Handler handlerObj = (Handler) handlerField.get(currentActivityThreadObj);
        if (!acc2) {
            handlerField.setAccessible(acc2);
        }
        Field handlerCallbackField = Handler.class.getDeclaredField("mCallback");
        boolean acc3 = handlerCallbackField.isAccessible();
        if (!acc3) {
            handlerCallbackField.setAccessible(true);
        }
        handlerCallbackField.set(handlerObj, new ProxyHandlerCallback((Handler.Callback) handlerCallbackField.get(handlerObj), handlerObj));
        if (!acc3) {
            handlerCallbackField.setAccessible(acc3);
        }
    }
}
