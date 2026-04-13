package androidx.camera.view.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.Quirk;

public class PreviewOneThirdWiderQuirk implements Quirk {
    private static final String SAMSUNG_A3_2017 = "A3Y17LTE";
    private static final String SAMSUNG_J5_PRIME = "ON5XELTE";

    static boolean load() {
        String str = Build.DEVICE;
        boolean isSamsungJ5PrimeAndApi26 = SAMSUNG_J5_PRIME.equals(str.toUpperCase()) && Build.VERSION.SDK_INT >= 26;
        boolean isSamsungA3 = SAMSUNG_A3_2017.equals(str.toUpperCase());
        if (isSamsungJ5PrimeAndApi26 || isSamsungA3) {
            return true;
        }
        return false;
    }

    public float getCropRectScaleX() {
        return 0.75f;
    }
}
