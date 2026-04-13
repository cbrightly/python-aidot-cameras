package com.alibaba.android.arouter.utils;

import android.text.TextUtils;
import android.util.Log;
import com.meituan.robust.Constants;

/* compiled from: DefaultLogger */
public class b implements com.alibaba.android.arouter.facade.template.b {
    private static boolean a = false;
    private static boolean b = false;
    private static boolean c = false;
    private String d = "ARouter";

    public b(String defaultTag) {
        this.d = defaultTag;
    }

    public void a(String tag, String message) {
        if (a) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String f = TextUtils.isEmpty(tag) ? f() : tag;
            Log.d(f, message + g(stackTraceElement));
        }
    }

    public void c(String tag, String message) {
        if (a) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String f = TextUtils.isEmpty(tag) ? f() : tag;
            Log.i(f, message + g(stackTraceElement));
        }
    }

    public void e(String tag, String message) {
        if (a) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String f = TextUtils.isEmpty(tag) ? f() : tag;
            Log.w(f, message + g(stackTraceElement));
        }
    }

    public void d(String tag, String message) {
        if (a) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String f = TextUtils.isEmpty(tag) ? f() : tag;
            Log.e(f, message + g(stackTraceElement));
        }
    }

    public void b(String tag, String message, Throwable e) {
        if (a) {
            Log.e(TextUtils.isEmpty(tag) ? f() : tag, message, e);
        }
    }

    public String f() {
        return this.d;
    }

    public static String g(StackTraceElement stackTraceElement) {
        StringBuilder sb = new StringBuilder(Constants.ARRAY_TYPE);
        if (b) {
            String threadName = Thread.currentThread().getName();
            String fileName = stackTraceElement.getFileName();
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            long threadID = Thread.currentThread().getId();
            int lineNumber = stackTraceElement.getLineNumber();
            sb.append("ThreadId=");
            sb.append(threadID);
            sb.append(" & ");
            sb.append("ThreadName=");
            sb.append(threadName);
            sb.append(" & ");
            sb.append("FileName=");
            sb.append(fileName);
            sb.append(" & ");
            sb.append("ClassName=");
            sb.append(className);
            sb.append(" & ");
            sb.append("MethodName=");
            sb.append(methodName);
            sb.append(" & ");
            sb.append("LineNumber=");
            sb.append(lineNumber);
        }
        sb.append(" ] ");
        return sb.toString();
    }
}
