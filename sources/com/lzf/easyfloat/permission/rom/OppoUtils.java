package com.lzf.easyfloat.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.util.Log;

public class OppoUtils {
    private static final String TAG = "OppoUtils";

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
            Log.e(TAG, "Below API 19 cannot invoke!");
            return false;
        }
    }

    public static void applyOppoPermission(Fragment fragment) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity"));
            fragment.startActivityForResult(intent, 199);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
