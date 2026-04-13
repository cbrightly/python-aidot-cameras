package com.meituan.robust;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class PatchProxy {
    private static CopyOnWriteArrayList<RobustExtension> registerExtensionList = new CopyOnWriteArrayList<>();
    private static ThreadLocal<RobustExtension> robustExtensionThreadLocal = new ThreadLocal<>();

    public static PatchProxyResult proxy(Object[] paramsArray, Object current, ChangeQuickRedirect changeQuickRedirect, boolean isStatic, int methodNumber, Class[] paramsClassTypes, Class returnType) {
        PatchProxyResult patchProxyResult = new PatchProxyResult();
        if (isSupport(paramsArray, current, changeQuickRedirect, isStatic, methodNumber, paramsClassTypes, returnType)) {
            patchProxyResult.isSupported = true;
            patchProxyResult.result = accessDispatch(paramsArray, current, changeQuickRedirect, isStatic, methodNumber, paramsClassTypes, returnType);
        }
        return patchProxyResult;
    }

    public static boolean isSupport(Object[] paramsArray, Object current, ChangeQuickRedirect changeQuickRedirect, boolean isStatic, int methodNumber, Class[] paramsClassTypes, Class returnType) {
        if (changeQuickRedirect == null) {
            CopyOnWriteArrayList<RobustExtension> copyOnWriteArrayList = registerExtensionList;
            if (copyOnWriteArrayList == null || copyOnWriteArrayList.isEmpty()) {
                return false;
            }
            Iterator<RobustExtension> it = registerExtensionList.iterator();
            while (it.hasNext()) {
                RobustExtension robustExtension = it.next();
                if (robustExtension.isSupport(new RobustArguments(paramsArray, current, isStatic, methodNumber, paramsClassTypes, returnType))) {
                    robustExtensionThreadLocal.set(robustExtension);
                    return true;
                }
            }
            return false;
        }
        String classMethod = getClassMethod(isStatic, methodNumber);
        if (TextUtils.isEmpty(classMethod)) {
            return false;
        }
        Object[] objArr = paramsArray;
        Object obj = current;
        boolean z = isStatic;
        try {
            return changeQuickRedirect.isSupport(classMethod, getObjects(paramsArray, current, isStatic));
        } catch (Throwable th) {
            Object obj2 = th;
            return false;
        }
    }

    public static Object accessDispatch(Object[] paramsArray, Object current, ChangeQuickRedirect changeQuickRedirect, boolean isStatic, int methodNumber, Class[] paramsClassTypes, Class returnType) {
        if (changeQuickRedirect == null) {
            RobustExtension robustExtension = robustExtensionThreadLocal.get();
            robustExtensionThreadLocal.remove();
            if (robustExtension == null) {
                return null;
            }
            notify(robustExtension.describeSelfFunction());
            return robustExtension.accessDispatch(new RobustArguments(paramsArray, current, isStatic, methodNumber, paramsClassTypes, returnType));
        }
        String classMethod = getClassMethod(isStatic, methodNumber);
        if (TextUtils.isEmpty(classMethod)) {
            return null;
        }
        notify(Constants.PATCH_EXECUTE);
        return changeQuickRedirect.accessDispatch(classMethod, getObjects(paramsArray, current, isStatic));
    }

    public static void accessDispatchVoid(Object[] paramsArray, Object current, ChangeQuickRedirect changeQuickRedirect, boolean isStatic, int methodNumber, Class[] paramsClassTypes, Class returnType) {
        if (changeQuickRedirect == null) {
            RobustExtension robustExtension = robustExtensionThreadLocal.get();
            robustExtensionThreadLocal.remove();
            if (robustExtension != null) {
                notify(robustExtension.describeSelfFunction());
                robustExtension.accessDispatch(new RobustArguments(paramsArray, current, isStatic, methodNumber, paramsClassTypes, returnType));
                return;
            }
            return;
        }
        notify(Constants.PATCH_EXECUTE);
        String classMethod = getClassMethod(isStatic, methodNumber);
        if (!TextUtils.isEmpty(classMethod)) {
            changeQuickRedirect.accessDispatch(classMethod, getObjects(paramsArray, current, isStatic));
        }
    }

    private static Object[] getObjects(Object[] arrayOfObject, Object current, boolean isStatic) {
        Object[] objects;
        if (arrayOfObject == null) {
            return null;
        }
        int argNum = arrayOfObject.length;
        if (isStatic) {
            objects = new Object[argNum];
        } else {
            objects = new Object[(argNum + 1)];
        }
        int x = 0;
        while (x < argNum) {
            objects[x] = arrayOfObject[x];
            x++;
        }
        if (!isStatic) {
            objects[x] = current;
        }
        return objects;
    }

    private static String getClassMethod(boolean isStatic, int methodNumber) {
        String methodName = "";
        try {
            return "" + ":" + methodName + ":" + isStatic + ":" + methodNumber;
        } catch (Exception e) {
            return "";
        }
    }

    private static String[] getClassMethodName() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        return new String[]{stackTraceElement.getClassName(), stackTraceElement.getMethodName()};
    }

    public static synchronized boolean register(RobustExtension robustExtension) {
        boolean addIfAbsent;
        synchronized (PatchProxy.class) {
            if (registerExtensionList == null) {
                registerExtensionList = new CopyOnWriteArrayList<>();
            }
            addIfAbsent = registerExtensionList.addIfAbsent(robustExtension);
        }
        return addIfAbsent;
    }

    public static synchronized boolean unregister(RobustExtension robustExtension) {
        synchronized (PatchProxy.class) {
            CopyOnWriteArrayList<RobustExtension> copyOnWriteArrayList = registerExtensionList;
            if (copyOnWriteArrayList == null) {
                return false;
            }
            boolean remove = copyOnWriteArrayList.remove(robustExtension);
            return remove;
        }
    }

    public static void reset() {
        registerExtensionList = new CopyOnWriteArrayList<>();
        robustExtensionThreadLocal = new ThreadLocal<>();
    }

    private static void notify(String info) {
        CopyOnWriteArrayList<RobustExtension> copyOnWriteArrayList = registerExtensionList;
        if (copyOnWriteArrayList != null) {
            Iterator<RobustExtension> it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                it.next().notifyListner(info);
            }
        }
    }
}
