package com.didichuxing.doraemonkit.aop;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.util.Log;
import com.didichuxing.doraemonkit.aop.method_stack.StaticMethodObject;
import com.didichuxing.doraemonkit.kit.timecounter.TimeCounterManager;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.TypeCastException;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.text.j;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MethodCostUtil.kt */
public final class MethodCostUtil {
    public static final MethodCostUtil INSTANCE = new MethodCostUtil();
    private static final g METHOD_COSTS$delegate = i.b(MethodCostUtil$METHOD_COSTS$2.INSTANCE);
    private static final String TAG = "DOKIT_SLOW_METHOD";
    private static final g staticMethodObject$delegate = i.b(MethodCostUtil$staticMethodObject$2.INSTANCE);

    private final ConcurrentHashMap<String, Long> getMETHOD_COSTS() {
        return (ConcurrentHashMap) METHOD_COSTS$delegate.getValue();
    }

    private final StaticMethodObject getStaticMethodObject() {
        return (StaticMethodObject) staticMethodObject$delegate.getValue();
    }

    private MethodCostUtil() {
    }

    public final synchronized void recodeObjectMethodCostStart(int thresholdTime, @NotNull String methodName, @Nullable Object classObj) {
        k.f(methodName, "methodName");
        try {
            getMETHOD_COSTS().put(methodName, Long.valueOf(System.currentTimeMillis()));
            if (classObj instanceof Application) {
                Object[] array = new j("&").split(methodName, 0).toArray(new String[0]);
                if (array != null) {
                    String[] methods = (String[]) array;
                    if (methods.length == 2) {
                        if (k.a(methods[1], "onCreate")) {
                            TimeCounterManager.get().onAppCreateStart();
                        }
                        if (k.a(methods[1], "attachBaseContext")) {
                            TimeCounterManager.get().onAppAttachBaseContextStart();
                        }
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public final void recodeStaticMethodCostStart(int thresholdTime, @NotNull String methodName) {
        k.f(methodName, "methodName");
        recodeObjectMethodCostStart(thresholdTime, methodName, getStaticMethodObject());
    }

    public final void recodeObjectMethodCostEnd(int thresholdTime, @NotNull String methodName, @Nullable Object classObj) {
        char c;
        int i = thresholdTime;
        String str = methodName;
        Object obj = classObj;
        k.f(str, "methodName");
        synchronized (MethodCostUtil.class) {
            try {
                MethodCostUtil methodCostUtil = INSTANCE;
                if (methodCostUtil.getMETHOD_COSTS().containsKey(str)) {
                    Long l = methodCostUtil.getMETHOD_COSTS().get(str);
                    if (l == null) {
                        k.n();
                    }
                    k.b(l, "METHOD_COSTS[methodName]!!");
                    int costTime = (int) (System.currentTimeMillis() - l.longValue());
                    methodCostUtil.getMETHOD_COSTS().remove(str);
                    if (obj instanceof Application) {
                        Object[] array = new j("&").split(str, 0).toArray(new String[0]);
                        if (array != null) {
                            String[] methods = (String[]) array;
                            if (methods.length == 2) {
                                if (k.a(methods[1], "onCreate")) {
                                    TimeCounterManager.get().onAppCreateEnd();
                                }
                                if (k.a(methods[1], "attachBaseContext")) {
                                    TimeCounterManager.get().onAppAttachBaseContextEnd();
                                }
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                        }
                    } else if (!(obj instanceof Activity)) {
                        boolean z = obj instanceof Service;
                    }
                    if (costTime >= i) {
                        Thread currentThread = Thread.currentThread();
                        k.b(currentThread, "Thread.currentThread()");
                        String threadName = currentThread.getName();
                        Log.i(TAG, "================Dokit================");
                        Log.i(TAG, "\t methodName===>" + str + "  threadName==>" + threadName + "  thresholdTime===>" + i + "   costTime===>" + costTime);
                        Thread currentThread2 = Thread.currentThread();
                        k.b(currentThread2, "Thread.currentThread()");
                        StackTraceElement[] stackTraceElements = currentThread2.getStackTrace();
                        int length = stackTraceElements.length;
                        int i2 = 0;
                        while (i2 < length) {
                            StackTraceElement stackTraceElement = stackTraceElements[i2];
                            String stackTraceElement2 = stackTraceElement.toString();
                            k.b(stackTraceElement2, "stackTraceElement.toString()");
                            String threadName2 = threadName;
                            if (x.S(stackTraceElement2, "MethodCostUtil", false, 2, (Object) null)) {
                                c = 2;
                            } else {
                                String stackTraceElement3 = stackTraceElement.toString();
                                k.b(stackTraceElement3, "stackTraceElement.toString()");
                                if (x.S(stackTraceElement3, "dalvik.system.VMStack.getThreadStackTrace", false, 2, (Object) null)) {
                                    c = 2;
                                } else {
                                    String stackTraceElement4 = stackTraceElement.toString();
                                    k.b(stackTraceElement4, "stackTraceElement.toString()");
                                    c = 2;
                                    if (!x.S(stackTraceElement4, "java.lang.Thread.getStackTrace", false, 2, (Object) null)) {
                                        Log.i(TAG, "\tat " + stackTraceElement);
                                    }
                                }
                            }
                            i2++;
                            int i3 = thresholdTime;
                            char c2 = c;
                            threadName = threadName2;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            kotlin.x xVar = kotlin.x.a;
        }
    }

    private final void printApplicationStartTime(String methodName) {
        Log.i(TAG, "================Dokit Application start================");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        for (StackTraceElement stackTraceElement : currentThread.getStackTrace()) {
            String stackTraceElement2 = stackTraceElement.toString();
            k.b(stackTraceElement2, "stackTraceElement.toString()");
            if (!x.S(stackTraceElement2, "MethodCostUtil", false, 2, (Object) null)) {
                String stackTraceElement3 = stackTraceElement.toString();
                k.b(stackTraceElement3, "stackTraceElement.toString()");
                if (!x.S(stackTraceElement3, "dalvik.system.VMStack.getThreadStackTrace", false, 2, (Object) null)) {
                    String stackTraceElement4 = stackTraceElement.toString();
                    k.b(stackTraceElement4, "stackTraceElement.toString()");
                    if (!x.S(stackTraceElement4, "java.lang.Thread.getStackTrace", false, 2, (Object) null)) {
                        Log.i(TAG, "\tat " + stackTraceElement);
                    }
                }
            }
        }
        Log.i(TAG, "================Dokit Application  end================");
        Log.i(TAG, "\n");
    }

    private final void printActivityStartTime(String methodName) {
        Log.i(TAG, "================Dokit Activity start================");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        for (StackTraceElement stackTraceElement : currentThread.getStackTrace()) {
            String stackTraceElement2 = stackTraceElement.toString();
            k.b(stackTraceElement2, "stackTraceElement.toString()");
            if (!x.S(stackTraceElement2, "MethodCostUtil", false, 2, (Object) null)) {
                String stackTraceElement3 = stackTraceElement.toString();
                k.b(stackTraceElement3, "stackTraceElement.toString()");
                if (!x.S(stackTraceElement3, "dalvik.system.VMStack.getThreadStackTrace", false, 2, (Object) null)) {
                    String stackTraceElement4 = stackTraceElement.toString();
                    k.b(stackTraceElement4, "stackTraceElement.toString()");
                    if (!x.S(stackTraceElement4, "java.lang.Thread.getStackTrace", false, 2, (Object) null)) {
                        Log.i(TAG, "\tat " + stackTraceElement);
                    }
                }
            }
        }
        Log.i(TAG, "================Dokit Activity end================");
        Log.i(TAG, "\n");
    }

    public final void recodeStaticMethodCostEnd(int thresholdTime, @NotNull String methodName) {
        k.f(methodName, "methodName");
        recodeObjectMethodCostEnd(thresholdTime, methodName, getStaticMethodObject());
    }
}
