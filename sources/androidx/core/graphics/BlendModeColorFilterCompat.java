package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BlendModeColorFilterCompat {
    @Nullable
    public static ColorFilter createBlendModeColorFilterCompat(int color, @NonNull BlendModeCompat blendModeCompat) {
        if (Build.VERSION.SDK_INT >= 29) {
            BlendMode blendMode = BlendModeUtils.obtainBlendModeFromCompat(blendModeCompat);
            if (blendMode != null) {
                return new BlendModeColorFilter(color, blendMode);
            }
            return null;
        }
        PorterDuff.Mode porterDuffMode = BlendModeUtils.obtainPorterDuffFromCompat(blendModeCompat);
        if (porterDuffMode != null) {
            return new PorterDuffColorFilter(color, porterDuffMode);
        }
        return null;
    }

    private BlendModeColorFilterCompat() {
    }
}
