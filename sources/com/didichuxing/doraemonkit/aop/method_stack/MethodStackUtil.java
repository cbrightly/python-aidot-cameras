package com.didichuxing.doraemonkit.aop.method_stack;

import android.app.Application;
import android.util.Log;
import com.blankj.utilcode.util.q;
import com.didichuxing.doraemonkit.aop.MethodCostUtil;
import com.didichuxing.doraemonkit.kit.timecounter.TimeCounterManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MethodStackUtil.kt */
public final class MethodStackUtil {
    public static final MethodStackUtil INSTANCE = new MethodStackUtil();
    private static final g METHOD_STACKS$delegate = i.b(MethodStackUtil$METHOD_STACKS$2.INSTANCE);
    private static final String SPACE_0 = "********";
    private static final String SPACE_1 = "*************";
    private static final String SPACE_2 = "*****************";
    private static final String SPACE_3 = "*********************";
    private static final String SPACE_4 = "*************************";
    private static final String SPACE_5 = "*****************************";
    private static final String SPACE_6 = "*********************************";
    private static final String SPACE_7 = "*************************************";
    @Nullable
    public static String STR_APP_ATTACH_BASECONTEXT = null;
    @Nullable
    public static String STR_APP_ON_CREATE = null;
    private static final String TAG = "DOKIT_SLOW_METHOD";
    private static final g staticMethodObject$delegate = i.b(MethodStackUtil$staticMethodObject$2.INSTANCE);

    private final List<ConcurrentHashMap<String, MethodInvokNode>> getMETHOD_STACKS() {
        return (List) METHOD_STACKS$delegate.getValue();
    }

    private final StaticMethodObject getStaticMethodObject() {
        return (StaticMethodObject) staticMethodObject$delegate.getValue();
    }

    private MethodStackUtil() {
    }

    private final void createMethodStackList(int totalLevel) {
        if (getMETHOD_STACKS().size() != totalLevel) {
            getMETHOD_STACKS().clear();
            for (int index = 0; index < totalLevel; index++) {
                getMETHOD_STACKS().add(index, new ConcurrentHashMap());
            }
        }
    }

    public final void recodeObjectMethodCostStart(int totalLevel, int thresholdTime, int currentLevel, @Nullable String className, @NotNull String methodName, @Nullable String desc, @Nullable Object classObj) {
        k.f(methodName, "methodName");
        try {
            createMethodStackList(totalLevel);
            MethodInvokNode methodInvokNode = new MethodInvokNode();
            methodInvokNode.setStartTimeMillis(System.currentTimeMillis());
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            methodInvokNode.setCurrentThreadName(currentThread.getName());
            methodInvokNode.setClassName(className);
            methodInvokNode.setMethodName(methodName);
            methodInvokNode.setLevel(currentLevel);
            d0 d0Var = d0.a;
            String format = String.format("%s&%s", Arrays.copyOf(new Object[]{className, methodName}, 2));
            k.b(format, "java.lang.String.format(format, *args)");
            getMETHOD_STACKS().get(currentLevel).put(format, methodInvokNode);
            if (currentLevel == 0 && (classObj instanceof Application)) {
                if (k.a(methodName, "onCreate")) {
                    TimeCounterManager.get().onAppCreateStart();
                }
                if (k.a(methodName, "attachBaseContext")) {
                    TimeCounterManager.get().onAppAttachBaseContextStart();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void recodeObjectMethodCostEnd(int thresholdTime, int currentLevel, @NotNull String className, @NotNull String methodName, @Nullable String desc, @Nullable Object classObj) {
        k.f(className, "className");
        k.f(methodName, "methodName");
        synchronized (MethodCostUtil.class) {
            try {
                MethodStackUtil methodStackUtil = INSTANCE;
                d0 d0Var = d0.a;
                String format = String.format("%s&%s", Arrays.copyOf(new Object[]{className, methodName}, 2));
                k.b(format, "java.lang.String.format(format, *args)");
                MethodInvokNode methodInvokNode = (MethodInvokNode) methodStackUtil.getMETHOD_STACKS().get(currentLevel).get(format);
                if (methodInvokNode != null) {
                    methodInvokNode.setEndTimeMillis(System.currentTimeMillis());
                    methodStackUtil.bindNode(thresholdTime, currentLevel, methodInvokNode);
                }
                if (currentLevel == 0) {
                    if (methodInvokNode != null) {
                        methodStackUtil.toStack(classObj instanceof Application, methodInvokNode);
                    }
                    if (classObj instanceof Application) {
                        if (k.a(methodName, "onCreate")) {
                            TimeCounterManager.get().onAppCreateEnd();
                        }
                        if (k.a(methodName, "attachBaseContext")) {
                            TimeCounterManager.get().onAppAttachBaseContextEnd();
                        }
                    }
                    methodStackUtil.getMETHOD_STACKS().get(0).remove(className + '&' + methodName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            x xVar = x.a;
        }
    }

    private final String getParentMethod(String currentClassName, String currentMethodName) {
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        StackTraceElement[] stackTraceElements = currentThread.getStackTrace();
        int index = 0;
        k.b(stackTraceElements, "stackTraceElements");
        int length = stackTraceElements.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            StackTraceElement stackTraceElement = stackTraceElements[i];
            k.b(stackTraceElement, "stackTraceElement");
            if (k.a(currentClassName, stackTraceElement.getClassName()) && k.a(currentMethodName, stackTraceElement.getMethodName())) {
                index = i;
                break;
            }
            i++;
        }
        StackTraceElement parentStackTraceElement = stackTraceElements[index + 1];
        d0 d0Var = d0.a;
        k.b(parentStackTraceElement, "parentStackTraceElement");
        String format = String.format("%s&%s", Arrays.copyOf(new Object[]{parentStackTraceElement.getClassName(), parentStackTraceElement.getMethodName()}, 2));
        k.b(format, "java.lang.String.format(format, *args)");
        return format;
    }

    private final void bindNode(int thresholdTime, int currentLevel, MethodInvokNode methodInvokNode) {
        MethodInvokNode parentMethodNode;
        if (methodInvokNode != null && methodInvokNode.getCostTimeMillis() > thresholdTime && currentLevel >= 1 && (parentMethodNode = (MethodInvokNode) getMETHOD_STACKS().get(currentLevel - 1).get(getParentMethod(methodInvokNode.getClassName(), methodInvokNode.getMethodName()))) != null) {
            methodInvokNode.setParent(parentMethodNode);
            parentMethodNode.addChild(methodInvokNode);
        }
    }

    public final void recodeStaticMethodCostStart(int totalLevel, int thresholdTime, int currentLevel, @Nullable String className, @NotNull String methodName, @Nullable String desc) {
        k.f(methodName, "methodName");
        recodeObjectMethodCostStart(totalLevel, thresholdTime, currentLevel, className, methodName, desc, getStaticMethodObject());
    }

    public final void recodeStaticMethodCostEnd(int thresholdTime, int currentLevel, @NotNull String className, @NotNull String methodName, @Nullable String desc) {
        k.f(className, "className");
        k.f(methodName, "methodName");
        recodeObjectMethodCostEnd(thresholdTime, currentLevel, className, methodName, desc, getStaticMethodObject());
    }

    private final void jsonTravel(List<MethodStackBean> methodStackBeans, List<MethodInvokNode> methodInvokNodes) {
        if (methodInvokNodes != null) {
            for (MethodInvokNode methodInvokNode : methodInvokNodes) {
                MethodStackBean methodStackBean = new MethodStackBean();
                methodStackBean.setCostTime(methodInvokNode.getCostTimeMillis());
                methodStackBean.setFunction(methodInvokNode.getClassName() + "&" + methodInvokNode.getMethodName());
                methodStackBean.setChildren(new ArrayList());
                jsonTravel(methodStackBean.getChildren(), methodInvokNode.getChildren());
                if (methodStackBeans != null) {
                    methodStackBeans.add(methodStackBean);
                }
            }
        }
    }

    private final void stackTravel(StringBuilder stringBuilder, List<MethodInvokNode> methodInvokNodes) {
        if (methodInvokNodes != null) {
            for (MethodInvokNode methodInvokNode : methodInvokNodes) {
                d0 d0Var = d0.a;
                String format = String.format("%s%s%s%s%s", Arrays.copyOf(new Object[]{Integer.valueOf(methodInvokNode.getLevel()), SPACE_0, String.valueOf(methodInvokNode.getCostTimeMillis()) + "ms", getSpaceString(methodInvokNode.getLevel()), methodInvokNode.getClassName() + "&" + methodInvokNode.getMethodName()}, 5));
                k.b(format, "java.lang.String.format(format, *args)");
                stringBuilder.append(format);
                stringBuilder.append("\n");
                stackTravel(stringBuilder, methodInvokNode.getChildren());
            }
        }
    }

    public final void toJson() {
        List methodStackBeans = new ArrayList();
        for (MethodInvokNode methodInvokNode : getMETHOD_STACKS().get(0).values()) {
            MethodStackBean methodStackBean = new MethodStackBean();
            methodStackBean.setCostTime(methodInvokNode.getCostTimeMillis());
            methodStackBean.setFunction(methodInvokNode.getClassName() + "&" + methodInvokNode.getMethodName());
            methodStackBean.setChildren(new ArrayList());
            jsonTravel(methodStackBean.getChildren(), methodInvokNode.getChildren());
            methodStackBeans.add(methodStackBean);
        }
        q.s(com.blankj.utilcode.util.k.j(methodStackBeans));
    }

    public final void toStack(boolean isAppStart, @NotNull MethodInvokNode methodInvokNode) {
        k.f(methodInvokNode, "methodInvokNode");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=========DoKit函数调用栈==========");
        stringBuilder.append("\n");
        d0 d0Var = d0.a;
        String format = String.format("%s    %s    %s", Arrays.copyOf(new Object[]{FirebaseAnalytics.Param.LEVEL, "time", "function"}, 3));
        k.b(format, "java.lang.String.format(format, *args)");
        stringBuilder.append(format);
        stringBuilder.append("\n");
        String format2 = String.format("%s%s%s%s%s", Arrays.copyOf(new Object[]{Integer.valueOf(methodInvokNode.getLevel()), SPACE_0, String.valueOf(methodInvokNode.getCostTimeMillis()) + "ms", getSpaceString(methodInvokNode.getLevel()), methodInvokNode.getClassName() + "&" + methodInvokNode.getMethodName()}, 5));
        k.b(format2, "java.lang.String.format(format, *args)");
        stringBuilder.append(format2);
        stringBuilder.append("\n");
        stackTravel(stringBuilder, methodInvokNode.getChildren());
        Log.i(TAG, stringBuilder.toString());
        if (isAppStart && methodInvokNode.getLevel() == 0) {
            if (k.a(methodInvokNode.getMethodName(), "onCreate")) {
                STR_APP_ON_CREATE = stringBuilder.toString();
            }
            if (k.a(methodInvokNode.getMethodName(), "attachBaseContext")) {
                STR_APP_ATTACH_BASECONTEXT = stringBuilder.toString();
            }
        }
    }

    private final String getSpaceString(int level) {
        switch (level) {
            case 1:
                return SPACE_1;
            case 2:
                return SPACE_2;
            case 3:
                return SPACE_3;
            case 4:
                return SPACE_4;
            case 5:
                return SPACE_5;
            case 6:
                return SPACE_6;
            case 7:
                return SPACE_7;
            default:
                return SPACE_0;
        }
    }
}
