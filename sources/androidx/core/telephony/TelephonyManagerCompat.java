package androidx.core.telephony;

import android.annotation.SuppressLint;
import android.os.Build;
import android.telephony.TelephonyManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TelephonyManagerCompat {
    private static Method sGetDeviceIdMethod;
    private static Method sGetSubIdMethod;

    @RequiresPermission("android.permission.READ_PHONE_STATE")
    @SuppressLint({"MissingPermission"})
    @Nullable
    public static String getImei(@NonNull TelephonyManager telephonyManager) {
        int subId;
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            return Api26Impl.getImei(telephonyManager);
        }
        if (i < 22 || (subId = getSubscriptionId(telephonyManager)) == Integer.MAX_VALUE || subId == -1) {
            return telephonyManager.getDeviceId();
        }
        int slotIndex = SubscriptionManagerCompat.getSlotIndex(subId);
        if (i >= 23) {
            return Api23Impl.getDeviceId(telephonyManager, slotIndex);
        }
        try {
            if (sGetDeviceIdMethod == null) {
                Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getDeviceId", new Class[]{Integer.TYPE});
                sGetDeviceIdMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            }
            return (String) sGetDeviceIdMethod.invoke(telephonyManager, new Object[]{Integer.valueOf(slotIndex)});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    @SuppressLint({"SoonBlockedPrivateApi"})
    public static int getSubscriptionId(@NonNull TelephonyManager telephonyManager) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            return Api30Impl.getSubscriptionId(telephonyManager);
        }
        if (i < 22) {
            return Integer.MAX_VALUE;
        }
        try {
            if (sGetSubIdMethod == null) {
                Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getSubId", new Class[0]);
                sGetSubIdMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            }
            Integer subId = (Integer) sGetSubIdMethod.invoke(telephonyManager, new Object[0]);
            if (subId == null || subId.intValue() == -1) {
                return Integer.MAX_VALUE;
            }
            return subId.intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return Integer.MAX_VALUE;
        }
    }

    private TelephonyManagerCompat() {
    }

    @RequiresApi(30)
    public static class Api30Impl {
        private Api30Impl() {
        }

        @DoNotInline
        static int getSubscriptionId(TelephonyManager telephonyManager) {
            return telephonyManager.getSubscriptionId();
        }
    }

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        @SuppressLint({"MissingPermission"})
        @Nullable
        @RequiresPermission("android.permission.READ_PHONE_STATE")
        static String getImei(TelephonyManager telephonyManager) {
            return telephonyManager.getImei();
        }
    }

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        @SuppressLint({"MissingPermission"})
        @Nullable
        @RequiresPermission("android.permission.READ_PHONE_STATE")
        static String getDeviceId(TelephonyManager telephonyManager, int slotIndex) {
            return telephonyManager.getDeviceId(slotIndex);
        }
    }
}
