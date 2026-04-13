package com.lzf.easyfloat.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.util.Log;

public class QikuUtils {
    private static final String TAG = "QikuUtils";

    public static boolean checkFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return checkOp(context, 24);
        }
        return true;
    }

    @TargetApi(19)
    private static boolean checkOp(Context context, int op) {
        if (Build.VERSION.SDK_INT >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService("appops");
            Class clazz = AppOpsManager.class;
            try {
                Class cls = Integer.TYPE;
                if (((Integer) clazz.getDeclaredMethod("checkOp", new Class[]{cls, cls, String.class}).invoke(manager, new Object[]{Integer.valueOf(op), Integer.valueOf(Binder.getCallingUid()), context.getPackageName()})).intValue() == 0) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        } else {
            Log.e("", "Below API 19 cannot invoke!");
            return false;
        }
    }

    public static void applyPermission(Fragment fragment) {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$OverlaySettingsActivity");
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 199);
            return;
        }
        intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 199);
        } else {
            Log.e(TAG, "can't open permission page with particular name, please use \"adb shell dumpsys activity\" command and tell me the name of the float window permission page");
        }
    }

    private static boolean isIntentAvailable(Intent intent, Context context) {
        if (intent != null && context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0) {
            return true;
        }
        return false;
    }
}
