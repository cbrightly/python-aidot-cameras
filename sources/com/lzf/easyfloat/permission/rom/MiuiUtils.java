package com.lzf.easyfloat.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.util.Log;

public class MiuiUtils {
    private static final String TAG = "MiuiUtils";

    public static int getMiuiVersion() {
        String version = RomUtils.getSystemProperty("ro.miui.ui.version.name");
        if (version == null) {
            return -1;
        }
        try {
            return Integer.parseInt(version.substring(1));
        } catch (Exception e) {
            Log.e(TAG, "get miui version code error, version : " + version);
            Log.e(TAG, Log.getStackTraceString(e));
            return -1;
        }
    }

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

    public static void applyMiuiPermission(Fragment fragment) {
        int versionCode = getMiuiVersion();
        if (versionCode == 5) {
            goToMiuiPermissionActivity_V5(fragment);
        } else if (versionCode == 6) {
            goToMiuiPermissionActivity_V6(fragment);
        } else if (versionCode == 7) {
            goToMiuiPermissionActivity_V7(fragment);
        } else if (versionCode >= 8) {
            goToMiuiPermissionActivity_V8(fragment);
        } else {
            Log.e(TAG, "this is a special MIUI rom version, its version code " + versionCode);
        }
    }

    private static boolean isIntentAvailable(Intent intent, Context context) {
        if (intent != null && context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0) {
            return true;
        }
        return false;
    }

    public static void goToMiuiPermissionActivity_V5(Fragment fragment) {
        String packageName = fragment.getActivity().getPackageName();
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", packageName, (String) null));
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 199);
        } else {
            Log.e(TAG, "intent is not available!");
        }
    }

    public static void goToMiuiPermissionActivity_V6(Fragment fragment) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", fragment.getActivity().getPackageName());
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 199);
        } else {
            Log.e(TAG, "Intent is not available!");
        }
    }

    public static void goToMiuiPermissionActivity_V7(Fragment fragment) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", fragment.getActivity().getPackageName());
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 199);
        } else {
            Log.e(TAG, "Intent is not available!");
        }
    }

    public static void goToMiuiPermissionActivity_V8(Fragment fragment) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", fragment.getActivity().getPackageName());
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 199);
            return;
        }
        Intent intent2 = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent2.setPackage("com.miui.securitycenter");
        intent2.putExtra("extra_pkgname", fragment.getActivity().getPackageName());
        if (isIntentAvailable(intent2, fragment.getActivity())) {
            fragment.startActivityForResult(intent2, 199);
        } else {
            Log.e(TAG, "Intent is not available!");
        }
    }
}
