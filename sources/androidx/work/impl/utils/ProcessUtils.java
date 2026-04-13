package androidx.work.impl.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.work.Configuration;
import androidx.work.Logger;
import java.lang.reflect.Method;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ProcessUtils {
    private static final String TAG = Logger.tagWithPrefix("ProcessUtils");

    private ProcessUtils() {
    }

    public static boolean isDefaultProcess(@NonNull Context context, @NonNull Configuration configuration) {
        String processName = getProcessName(context);
        if (!TextUtils.isEmpty(configuration.getDefaultProcessName())) {
            return TextUtils.equals(processName, configuration.getDefaultProcessName());
        }
        return TextUtils.equals(processName, context.getApplicationInfo().processName);
    }

    @SuppressLint({"PrivateApi", "DiscouragedPrivateApi"})
    @Nullable
    public static String getProcessName(@NonNull Context context) {
        List<ActivityManager.RunningAppProcessInfo> processes;
        Object packageName;
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            return Application.getProcessName();
        }
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread", false, ProcessUtils.class.getClassLoader());
            if (i >= 18) {
                Method currentProcessName = activityThread.getDeclaredMethod("currentProcessName", new Class[0]);
                currentProcessName.setAccessible(true);
                packageName = currentProcessName.invoke((Object) null, new Object[0]);
            } else {
                Method getActivityThread = activityThread.getDeclaredMethod("currentActivityThread", new Class[0]);
                getActivityThread.setAccessible(true);
                Method getProcessName = activityThread.getDeclaredMethod("getProcessName", new Class[0]);
                getProcessName.setAccessible(true);
                packageName = getProcessName.invoke(getActivityThread.invoke((Object) null, new Object[0]), new Object[0]);
            }
            if (packageName instanceof String) {
                return (String) packageName;
            }
        } catch (Throwable exception) {
            Logger.get().debug(TAG, "Unable to check ActivityThread for processName", exception);
        }
        int pid = Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        if (!(am == null || (processes = am.getRunningAppProcesses()) == null || processes.isEmpty())) {
            for (ActivityManager.RunningAppProcessInfo process : processes) {
                if (process.pid == pid) {
                    return process.processName;
                }
            }
        }
        return null;
    }
}
