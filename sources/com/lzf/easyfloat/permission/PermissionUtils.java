package com.lzf.easyfloat.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.lzf.easyfloat.interfaces.OnPermissionResult;
import com.lzf.easyfloat.permission.rom.HuaweiUtils;
import com.lzf.easyfloat.permission.rom.MeizuUtils;
import com.lzf.easyfloat.permission.rom.MiuiUtils;
import com.lzf.easyfloat.permission.rom.OppoUtils;
import com.lzf.easyfloat.permission.rom.QikuUtils;
import com.lzf.easyfloat.permission.rom.RomUtils;
import com.lzf.easyfloat.utils.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: PermissionUtils.kt */
public final class PermissionUtils {
    @NotNull
    public static final PermissionUtils INSTANCE = new PermissionUtils();
    @NotNull
    private static final String TAG = "PermissionUtils--->";
    public static final int requestCode = 199;

    private PermissionUtils() {
    }

    public static final boolean checkPermission(@NotNull Context context) {
        k.e(context, "context");
        if (Build.VERSION.SDK_INT >= 23) {
            return INSTANCE.commonROMPermissionCheck(context);
        }
        RomUtils romUtils = RomUtils.INSTANCE;
        if (romUtils.checkIsHuaweiRom()) {
            return INSTANCE.huaweiPermissionCheck(context);
        }
        if (romUtils.checkIsMiuiRom()) {
            return INSTANCE.miuiPermissionCheck(context);
        }
        if (romUtils.checkIsOppoRom()) {
            return INSTANCE.oppoROMPermissionCheck(context);
        }
        if (romUtils.checkIsMeizuRom()) {
            return INSTANCE.meizuPermissionCheck(context);
        }
        if (romUtils.checkIs360Rom()) {
            return INSTANCE.qikuPermissionCheck(context);
        }
        return true;
    }

    public static final void requestPermission(@NotNull Activity activity, @NotNull OnPermissionResult onPermissionResult) {
        k.e(activity, "activity");
        k.e(onPermissionResult, "onPermissionResult");
        PermissionFragment.Companion.requestPermission(activity, onPermissionResult);
    }

    public final void requestPermission$easyfloat_release(@NotNull Fragment fragment) {
        k.e(fragment, "fragment");
        if (Build.VERSION.SDK_INT < 23) {
            RomUtils romUtils = RomUtils.INSTANCE;
            if (romUtils.checkIsHuaweiRom()) {
                HuaweiUtils.applyPermission(fragment);
            } else if (romUtils.checkIsMiuiRom()) {
                MiuiUtils.applyMiuiPermission(fragment);
            } else if (romUtils.checkIsOppoRom()) {
                OppoUtils.applyOppoPermission(fragment);
            } else if (romUtils.checkIsMeizuRom()) {
                MeizuUtils.applyPermission(fragment);
            } else if (romUtils.checkIs360Rom()) {
                QikuUtils.applyPermission(fragment);
            } else {
                Logger.INSTANCE.i(TAG, "原生 Android 6.0 以下无需权限申请");
            }
        } else {
            commonROMPermissionApply(fragment);
        }
    }

    private final boolean huaweiPermissionCheck(Context context) {
        return HuaweiUtils.checkFloatWindowPermission(context);
    }

    private final boolean miuiPermissionCheck(Context context) {
        return MiuiUtils.checkFloatWindowPermission(context);
    }

    private final boolean meizuPermissionCheck(Context context) {
        return MeizuUtils.checkFloatWindowPermission(context);
    }

    private final boolean qikuPermissionCheck(Context context) {
        return QikuUtils.checkFloatWindowPermission(context);
    }

    private final boolean oppoROMPermissionCheck(Context context) {
        return OppoUtils.checkFloatWindowPermission(context);
    }

    private final boolean commonROMPermissionCheck(Context context) {
        if (RomUtils.INSTANCE.checkIsMeizuRom()) {
            return meizuPermissionCheck(context);
        }
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        try {
            Method canDrawOverlays = Settings.class.getDeclaredMethod("canDrawOverlays", new Class[]{Context.class});
            k.d(canDrawOverlays, "clazz.getDeclaredMethod(…ys\", Context::class.java)");
            Object invoke = canDrawOverlays.invoke((Object) null, new Object[]{context});
            if (invoke != null) {
                return ((Boolean) invoke).booleanValue();
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Boolean");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return true;
        }
    }

    private final void commonROMPermissionApply(Fragment fragment) {
        if (RomUtils.INSTANCE.checkIsMeizuRom()) {
            MeizuUtils.applyPermission(fragment);
        } else if (Build.VERSION.SDK_INT >= 23) {
            try {
                commonROMPermissionApplyInternal(fragment);
            } catch (Exception e) {
                Logger logger = Logger.INSTANCE;
                String stackTraceString = Log.getStackTraceString(e);
                k.d(stackTraceString, "getStackTraceString(e)");
                logger.e(TAG, stackTraceString);
            }
        } else {
            Logger.INSTANCE.d(TAG, "user manually refuse OVERLAY_PERMISSION");
        }
    }

    public static final void commonROMPermissionApplyInternal(@NotNull Fragment fragment) {
        k.e(fragment, "fragment");
        try {
            Field field = Settings.class.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
            k.d(field, "clazz.getDeclaredField(\"…NAGE_OVERLAY_PERMISSION\")");
            Intent intent = new Intent(field.get((Object) null).toString());
            intent.setData(Uri.parse(k.l("package:", fragment.getActivity().getPackageName())));
            fragment.startActivityForResult(intent, 199);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, String.valueOf(e));
        }
    }
}
