package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.VisibleForTesting;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: HardwareConfigState */
public final class r {
    public static final boolean a;
    public static final boolean b;
    private static final File c = new File("/proc/self/fd");
    private static volatile r d;
    private static volatile int e = -1;
    private final boolean f = f();
    private final int g;
    private final int h;
    @GuardedBy("this")
    private int i;
    @GuardedBy("this")
    private boolean j = true;
    private final AtomicBoolean k = new AtomicBoolean(false);

    static {
        int i2 = Build.VERSION.SDK_INT;
        boolean z = true;
        a = i2 < 29;
        if (i2 < 26) {
            z = false;
        }
        b = z;
    }

    public static r b() {
        if (d == null) {
            synchronized (r.class) {
                if (d == null) {
                    d = new r();
                }
            }
        }
        return d;
    }

    @VisibleForTesting
    r() {
        if (Build.VERSION.SDK_INT >= 28) {
            this.g = GroupCtrlAdapter.TIMEOUT_WAIT_MESH_ONLINE;
            this.h = 0;
            return;
        }
        this.g = 700;
        this.h = 128;
    }

    public boolean e(int targetWidth, int targetHeight, boolean isHardwareConfigAllowed, boolean isExifOrientationRequired) {
        if (!isHardwareConfigAllowed) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed by caller");
            }
            return false;
        } else if (!this.f) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed by device model");
            }
            return false;
        } else if (!b) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed by sdk");
            }
            return false;
        } else if (a()) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed by app state");
            }
            return false;
        } else if (isExifOrientationRequired) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed because exif orientation is required");
            }
            return false;
        } else {
            int i2 = this.h;
            if (targetWidth < i2) {
                if (Log.isLoggable("HardwareConfig", 2)) {
                    Log.v("HardwareConfig", "Hardware config disallowed because width is too small");
                }
                return false;
            } else if (targetHeight < i2) {
                if (Log.isLoggable("HardwareConfig", 2)) {
                    Log.v("HardwareConfig", "Hardware config disallowed because height is too small");
                }
                return false;
            } else if (d()) {
                return true;
            } else {
                if (Log.isLoggable("HardwareConfig", 2)) {
                    Log.v("HardwareConfig", "Hardware config disallowed because there are insufficient FDs");
                }
                return false;
            }
        }
    }

    private boolean a() {
        return a && !this.k.get();
    }

    /* access modifiers changed from: package-private */
    @TargetApi(26)
    public boolean i(int targetWidth, int targetHeight, BitmapFactory.Options optionsWithScaling, boolean isHardwareConfigAllowed, boolean isExifOrientationRequired) {
        boolean result = e(targetWidth, targetHeight, isHardwareConfigAllowed, isExifOrientationRequired);
        if (result) {
            optionsWithScaling.inPreferredConfig = Bitmap.Config.HARDWARE;
            optionsWithScaling.inMutable = false;
        }
        return result;
    }

    private static boolean f() {
        return !g() && !h();
    }

    private static boolean h() {
        if (Build.VERSION.SDK_INT != 27) {
            return false;
        }
        return Arrays.asList(new String[]{"LG-M250", "LG-M320", "LG-Q710AL", "LG-Q710PL", "LGM-K121K", "LGM-K121L", "LGM-K121S", "LGM-X320K", "LGM-X320L", "LGM-X320S", "LGM-X401L", "LGM-X401S", "LM-Q610.FG", "LM-Q610.FGN", "LM-Q617.FG", "LM-Q617.FGN", "LM-Q710.FG", "LM-Q710.FGN", "LM-X220PM", "LM-X220QMA", "LM-X410PM"}).contains(Build.MODEL);
    }

    private static boolean g() {
        if (Build.VERSION.SDK_INT != 26) {
            return false;
        }
        for (String prefixOrModelName : Arrays.asList(new String[]{"SC-04J", "SM-N935", "SM-J720", "SM-G570F", "SM-G570M", "SM-G960", "SM-G965", "SM-G935", "SM-G930", "SM-A520", "SM-A720F", "moto e5", "moto e5 play", "moto e5 plus", "moto e5 cruise", "moto g(6) forge", "moto g(6) play"})) {
            if (Build.MODEL.startsWith(prefixOrModelName)) {
                return true;
            }
        }
        return false;
    }

    private int c() {
        if (e != -1) {
            return e;
        }
        return this.g;
    }

    private synchronized boolean d() {
        boolean z = true;
        int i2 = this.i + 1;
        this.i = i2;
        if (i2 >= 50) {
            this.i = 0;
            int currentFds = c.list().length;
            long maxFdCount = (long) c();
            if (((long) currentFds) >= maxFdCount) {
                z = false;
            }
            this.j = z;
            if (!z && Log.isLoggable("Downsampler", 5)) {
                Log.w("Downsampler", "Excluding HARDWARE bitmap config because we're over the file descriptor limit, file descriptors " + currentFds + ", limit " + maxFdCount);
            }
        }
        return this.j;
    }
}
