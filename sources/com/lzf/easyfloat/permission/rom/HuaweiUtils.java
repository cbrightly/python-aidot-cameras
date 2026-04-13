package com.lzf.easyfloat.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import net.sqlcipher.database.SQLiteDatabase;

public class HuaweiUtils {
    private static final String TAG = "HuaweiUtils";

    public static boolean checkFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return checkOp(context, 24);
        }
        return true;
    }

    public static void applyPermission(Fragment fragment) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity"));
            if (RomUtils.getEmuiVersion() == 3.1d) {
                fragment.startActivityForResult(intent, 199);
                return;
            }
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity"));
            fragment.startActivityForResult(intent, 199);
        } catch (SecurityException e) {
            Intent intent2 = new Intent();
            intent2.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            intent2.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
            fragment.startActivityForResult(intent2, 199);
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (ActivityNotFoundException e2) {
            Intent intent3 = new Intent();
            intent3.setComponent(new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem"));
            fragment.startActivityForResult(intent3, 199);
            e2.printStackTrace();
            Log.e(TAG, Log.getStackTraceString(e2));
        } catch (Exception e3) {
            Toast.makeText(fragment.getActivity(), "进入设置页面失败，请手动设置", 1).show();
            Log.e(TAG, Log.getStackTraceString(e3));
        }
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
}
