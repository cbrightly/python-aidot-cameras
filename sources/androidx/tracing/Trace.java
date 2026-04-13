package androidx.tracing;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Trace {
    static final String TAG = "Trace";
    private static Method sAsyncTraceBeginMethod;
    private static Method sAsyncTraceEndMethod;
    private static Method sIsTagEnabledMethod;
    private static Method sTraceCounterMethod;
    private static long sTraceTagApp;

    @SuppressLint({"NewApi"})
    public static boolean isEnabled() {
        try {
            if (sIsTagEnabledMethod == null) {
                return android.os.Trace.isEnabled();
            }
        } catch (NoClassDefFoundError | NoSuchMethodError e) {
        }
        return isEnabledFallback();
    }

    public static void beginSection(@NonNull String label) {
        if (Build.VERSION.SDK_INT >= 18) {
            TraceApi18Impl.beginSection(label);
        }
    }

    public static void endSection() {
        if (Build.VERSION.SDK_INT >= 18) {
            TraceApi18Impl.endSection();
        }
    }

    @SuppressLint({"NewApi"})
    public static void beginAsyncSection(@NonNull String methodName, int cookie) {
        try {
            if (sAsyncTraceBeginMethod == null) {
                TraceApi29Impl.beginAsyncSection(methodName, cookie);
                return;
            }
        } catch (NoClassDefFoundError | NoSuchMethodError e) {
        }
        beginAsyncSectionFallback(methodName, cookie);
    }

    @SuppressLint({"NewApi"})
    public static void endAsyncSection(@NonNull String methodName, int cookie) {
        try {
            if (sAsyncTraceEndMethod == null) {
                TraceApi29Impl.endAsyncSection(methodName, cookie);
                return;
            }
        } catch (NoClassDefFoundError | NoSuchMethodError e) {
        }
        endAsyncSectionFallback(methodName, cookie);
    }

    @SuppressLint({"NewApi"})
    public static void setCounter(@NonNull String counterName, int counterValue) {
        try {
            if (sTraceCounterMethod == null) {
                TraceApi29Impl.setCounter(counterName, counterValue);
                return;
            }
        } catch (NoClassDefFoundError | NoSuchMethodError e) {
        }
        setCounterFallback(counterName, counterValue);
    }

    private static boolean isEnabledFallback() {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (sIsTagEnabledMethod == null) {
                    sTraceTagApp = android.os.Trace.class.getField("TRACE_TAG_APP").getLong((Object) null);
                    sIsTagEnabledMethod = android.os.Trace.class.getMethod("isTagEnabled", new Class[]{Long.TYPE});
                }
                return ((Boolean) sIsTagEnabledMethod.invoke((Object) null, new Object[]{Long.valueOf(sTraceTagApp)})).booleanValue();
            } catch (Exception exception) {
                handleException("isTagEnabled", exception);
            }
        }
        return false;
    }

    private static void beginAsyncSectionFallback(@NonNull String methodName, int cookie) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (sAsyncTraceBeginMethod == null) {
                    sAsyncTraceBeginMethod = android.os.Trace.class.getMethod("asyncTraceBegin", new Class[]{Long.TYPE, String.class, Integer.TYPE});
                }
                sAsyncTraceBeginMethod.invoke((Object) null, new Object[]{Long.valueOf(sTraceTagApp), methodName, Integer.valueOf(cookie)});
            } catch (Exception exception) {
                handleException("asyncTraceBegin", exception);
            }
        }
    }

    private static void endAsyncSectionFallback(@NonNull String methodName, int cookie) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (sAsyncTraceEndMethod == null) {
                    sAsyncTraceEndMethod = android.os.Trace.class.getMethod("asyncTraceEnd", new Class[]{Long.TYPE, String.class, Integer.TYPE});
                }
                sAsyncTraceEndMethod.invoke((Object) null, new Object[]{Long.valueOf(sTraceTagApp), methodName, Integer.valueOf(cookie)});
            } catch (Exception exception) {
                handleException("asyncTraceEnd", exception);
            }
        }
    }

    private static void setCounterFallback(@NonNull String counterName, int counterValue) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                if (sTraceCounterMethod == null) {
                    sTraceCounterMethod = android.os.Trace.class.getMethod("traceCounter", new Class[]{Long.TYPE, String.class, Integer.TYPE});
                }
                sTraceCounterMethod.invoke((Object) null, new Object[]{Long.valueOf(sTraceTagApp), counterName, Integer.valueOf(counterValue)});
            } catch (Exception exception) {
                handleException("traceCounter", exception);
            }
        }
    }

    private static void handleException(@NonNull String methodName, @NonNull Exception exception) {
        if (exception instanceof InvocationTargetException) {
            Throwable cause = exception.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            throw new RuntimeException(cause);
        }
        Log.v(TAG, "Unable to call " + methodName + " via reflection", exception);
    }

    private Trace() {
    }
}
