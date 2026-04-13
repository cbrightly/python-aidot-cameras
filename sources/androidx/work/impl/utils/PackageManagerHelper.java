package androidx.work.impl.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.work.Logger;

public class PackageManagerHelper {
    private static final String TAG = Logger.tagWithPrefix("PackageManagerHelper");

    private PackageManagerHelper() {
    }

    public static void setComponentEnabled(@NonNull Context context, @NonNull Class<?> klazz, boolean enabled) {
        int i;
        String str;
        String str2 = "enabled";
        try {
            PackageManager packageManager = context.getPackageManager();
            ComponentName componentName = new ComponentName(context, klazz.getName());
            if (enabled) {
                i = 1;
            } else {
                i = 2;
            }
            packageManager.setComponentEnabledSetting(componentName, i, 1);
            Logger logger = Logger.get();
            String str3 = TAG;
            Object[] objArr = new Object[2];
            objArr[0] = klazz.getName();
            if (enabled) {
                str = str2;
            } else {
                str = "disabled";
            }
            objArr[1] = str;
            logger.debug(str3, String.format("%s %s", objArr), new Throwable[0]);
        } catch (Exception exception) {
            Logger logger2 = Logger.get();
            String str4 = TAG;
            Object[] objArr2 = new Object[2];
            objArr2[0] = klazz.getName();
            if (!enabled) {
                str2 = "disabled";
            }
            objArr2[1] = str2;
            logger2.debug(str4, String.format("%s could not be %s", objArr2), exception);
        }
    }

    public static boolean isComponentExplicitlyEnabled(Context context, Class<?> klazz) {
        return isComponentExplicitlyEnabled(context, klazz.getName());
    }

    public static boolean isComponentExplicitlyEnabled(Context context, String className) {
        return context.getPackageManager().getComponentEnabledSetting(new ComponentName(context, className)) == 1;
    }
}
