package androidx.core.os;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Method;

public final class ProcessCompat {
    private ProcessCompat() {
    }

    public static boolean isApplicationUid(int uid) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 24) {
            return Api24Impl.isApplicationUid(uid);
        }
        if (i >= 17) {
            return Api17Impl.isApplicationUid(uid);
        }
        if (i == 16) {
            return Api16Impl.isApplicationUid(uid);
        }
        return true;
    }

    @RequiresApi(24)
    public static class Api24Impl {
        private Api24Impl() {
        }

        static boolean isApplicationUid(int uid) {
            return Process.isApplicationUid(uid);
        }
    }

    @RequiresApi(17)
    public static class Api17Impl {
        private static Method sMethodUserHandleIsAppMethod;
        private static boolean sResolved;
        private static final Object sResolvedLock = new Object();

        private Api17Impl() {
        }

        @SuppressLint({"DiscouragedPrivateApi"})
        static boolean isApplicationUid(int uid) {
            try {
                synchronized (sResolvedLock) {
                    if (!sResolved) {
                        sResolved = true;
                        sMethodUserHandleIsAppMethod = UserHandle.class.getDeclaredMethod("isApp", new Class[]{Integer.TYPE});
                    }
                }
                Method method = sMethodUserHandleIsAppMethod;
                if (method != null) {
                    if (((Boolean) method.invoke((Object) null, new Object[]{Integer.valueOf(uid)})) == null) {
                        throw new NullPointerException();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @RequiresApi(16)
    public static class Api16Impl {
        private static Method sMethodUserIdIsAppMethod;
        private static boolean sResolved;
        private static final Object sResolvedLock = new Object();

        private Api16Impl() {
        }

        @SuppressLint({"PrivateApi"})
        static boolean isApplicationUid(int uid) {
            try {
                synchronized (sResolvedLock) {
                    if (!sResolved) {
                        sResolved = true;
                        sMethodUserIdIsAppMethod = Class.forName("android.os.UserId").getDeclaredMethod("isApp", new Class[]{Integer.TYPE});
                    }
                }
                Method method = sMethodUserIdIsAppMethod;
                if (method != null) {
                    Boolean result = (Boolean) method.invoke((Object) null, new Object[]{Integer.valueOf(uid)});
                    if (result != null) {
                        return result.booleanValue();
                    }
                    throw new NullPointerException();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
