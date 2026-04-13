package androidx.core.location;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public final class LocationCompat {
    private static final String EXTRA_IS_MOCK = "mockLocation";
    @Nullable
    private static Method sSetIsFromMockProviderMethod;

    private LocationCompat() {
    }

    public static long getElapsedRealtimeNanos(@NonNull Location location) {
        if (Build.VERSION.SDK_INT >= 17) {
            return Api17Impl.getElapsedRealtimeNanos(location);
        }
        return TimeUnit.MILLISECONDS.toNanos(getElapsedRealtimeMillis(location));
    }

    public static long getElapsedRealtimeMillis(@NonNull Location location) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TimeUnit.NANOSECONDS.toMillis(Api17Impl.getElapsedRealtimeNanos(location));
        }
        long timeDeltaMs = System.currentTimeMillis() - location.getTime();
        long elapsedRealtimeMs = SystemClock.elapsedRealtime();
        if (timeDeltaMs < 0) {
            return elapsedRealtimeMs;
        }
        if (timeDeltaMs > elapsedRealtimeMs) {
            return 0;
        }
        return elapsedRealtimeMs - timeDeltaMs;
    }

    public static boolean isMock(@NonNull Location location) {
        if (Build.VERSION.SDK_INT >= 18) {
            return Api18Impl.isMock(location);
        }
        Bundle extras = location.getExtras();
        if (extras == null) {
            return false;
        }
        return extras.getBoolean("mockLocation", false);
    }

    public static void setMock(@NonNull Location location, boolean mock) {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                getSetIsFromMockProviderMethod().invoke(location, new Object[]{Boolean.valueOf(mock)});
            } catch (NoSuchMethodException e) {
                Error error = new NoSuchMethodError();
                error.initCause(e);
                throw error;
            } catch (IllegalAccessException e2) {
                Error error2 = new IllegalAccessError();
                error2.initCause(e2);
                throw error2;
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3);
            }
        } else {
            Bundle extras = location.getExtras();
            if (extras == null) {
                Bundle extras2 = new Bundle();
                extras2.putBoolean("mockLocation", true);
                location.setExtras(extras2);
                return;
            }
            extras.putBoolean("mockLocation", true);
        }
    }

    @RequiresApi(18)
    public static class Api18Impl {
        private Api18Impl() {
        }

        @DoNotInline
        static boolean isMock(Location location) {
            return location.isFromMockProvider();
        }
    }

    @RequiresApi(17)
    public static class Api17Impl {
        private Api17Impl() {
        }

        @DoNotInline
        static long getElapsedRealtimeNanos(Location location) {
            return location.getElapsedRealtimeNanos();
        }
    }

    private static Method getSetIsFromMockProviderMethod() {
        if (sSetIsFromMockProviderMethod == null) {
            Method declaredMethod = Location.class.getDeclaredMethod("setIsFromMockProvider", new Class[]{Boolean.TYPE});
            sSetIsFromMockProviderMethod = declaredMethod;
            declaredMethod.setAccessible(true);
        }
        return sSetIsFromMockProviderMethod;
    }
}
