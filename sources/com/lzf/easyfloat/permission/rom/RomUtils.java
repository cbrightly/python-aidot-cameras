package com.lzf.easyfloat.permission.rom;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.didichuxing.doraemonkit.util.SystemUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RomUtils.kt */
public final class RomUtils {
    @NotNull
    public static final RomUtils INSTANCE = new RomUtils();
    @NotNull
    private static final String TAG = "RomUtils--->";

    private RomUtils() {
    }

    public static final double getEmuiVersion() {
        try {
            String emuiVersion = getSystemProperty("ro.build.version.emui");
            k.c(emuiVersion);
            int f0 = x.f0(emuiVersion, "_", 0, false, 6, (Object) null) + 1;
            if (emuiVersion != null) {
                String version = emuiVersion.substring(f0);
                k.d(version, "(this as java.lang.String).substring(startIndex)");
                return Double.parseDouble(version);
            }
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        } catch (Exception e) {
            e.printStackTrace();
            return 4.0d;
        }
    }

    @Nullable
    public static final String getSystemProperty(@NotNull String propName) {
        k.e(propName, "propName");
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(k.l("getprop ", propName)).getInputStream()), 1024);
            String readLine = input.readLine();
            k.d(readLine, "input.readLine()");
            String line = readLine;
            input.close();
            try {
                input.close();
            } catch (IOException e) {
                Log.e(TAG, "Exception while closing InputStream", e);
            }
            return line;
        } catch (Exception ex) {
            Log.e(TAG, k.l("Unable to read sysprop ", propName), ex);
            if (input != null) {
                try {
                    input.close();
                    Exception exc = ex;
                } catch (IOException e2) {
                    Log.e(TAG, "Exception while closing InputStream", e2);
                    Exception exc2 = ex;
                }
            }
            return null;
        } catch (Throwable th) {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e3) {
                    Log.e(TAG, "Exception while closing InputStream", e3);
                }
            }
            throw th;
        }
    }

    public final boolean checkIsHuaweiRom() {
        String str = Build.MANUFACTURER;
        k.d(str, "MANUFACTURER");
        return x.S(str, SystemUtil.PHONE_HUAWEI, false, 2, (Object) null);
    }

    public final boolean checkIsMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public final boolean checkIsMeizuRom() {
        String systemProperty = getSystemProperty("ro.build.display.id");
        if (TextUtils.isEmpty(systemProperty)) {
            return false;
        }
        k.c(systemProperty);
        if (!x.S(systemProperty, "flyme", false, 2, (Object) null)) {
            String lowerCase = systemProperty.toLowerCase();
            k.d(lowerCase, "(this as java.lang.String).toLowerCase()");
            if (x.S(lowerCase, "flyme", false, 2, (Object) null)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public final boolean checkIs360Rom() {
        String str = Build.MANUFACTURER;
        k.d(str, "MANUFACTURER");
        if (!x.S(str, "QiKU", false, 2, (Object) null)) {
            k.d(str, "MANUFACTURER");
            return x.S(str, "360", false, 2, (Object) null);
        }
    }

    public final boolean checkIsOppoRom() {
        String str = Build.MANUFACTURER;
        k.d(str, "MANUFACTURER");
        if (!x.S(str, "OPPO", false, 2, (Object) null)) {
            k.d(str, "MANUFACTURER");
            return x.S(str, SystemUtil.PHONE_OPPO, false, 2, (Object) null);
        }
    }

    public final boolean checkIsVivoRom() {
        String str = Build.MANUFACTURER;
        k.d(str, "MANUFACTURER");
        if (!x.S(str, "VIVO", false, 2, (Object) null)) {
            k.d(str, "MANUFACTURER");
            return x.S(str, SystemUtil.PHONE_VIVO, false, 2, (Object) null);
        }
    }
}
