package androidx.camera.core.impl;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/* compiled from: ImageOutputConfig */
public final /* synthetic */ class z {
    public static boolean l(ImageOutputConfig _this) {
        return _this.containsOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO);
    }

    public static int g(ImageOutputConfig _this) {
        return ((Integer) _this.retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO)).intValue();
    }

    public static int k(ImageOutputConfig _this, int valueIfMissing) {
        return ((Integer) _this.retrieveOption(ImageOutputConfig.OPTION_TARGET_ROTATION, Integer.valueOf(valueIfMissing))).intValue();
    }

    public static int j(ImageOutputConfig _this) {
        return ((Integer) _this.retrieveOption(ImageOutputConfig.OPTION_TARGET_ROTATION)).intValue();
    }

    @Nullable
    public static Size i(@Nullable ImageOutputConfig _this, Size valueIfMissing) {
        return (Size) _this.retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, valueIfMissing);
    }

    @NonNull
    public static Size h(ImageOutputConfig _this) {
        return (Size) _this.retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION);
    }

    @Nullable
    public static Size b(@Nullable ImageOutputConfig _this, Size valueIfMissing) {
        return (Size) _this.retrieveOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, valueIfMissing);
    }

    @NonNull
    public static Size a(ImageOutputConfig _this) {
        return (Size) _this.retrieveOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION);
    }

    @Nullable
    public static Size d(@Nullable ImageOutputConfig _this, Size valueIfMissing) {
        return (Size) _this.retrieveOption(ImageOutputConfig.OPTION_MAX_RESOLUTION, valueIfMissing);
    }

    @NonNull
    public static Size c(ImageOutputConfig _this) {
        return (Size) _this.retrieveOption(ImageOutputConfig.OPTION_MAX_RESOLUTION);
    }

    @Nullable
    public static List f(@Nullable ImageOutputConfig _this, List valueIfMissing) {
        return (List) _this.retrieveOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, valueIfMissing);
    }

    @NonNull
    public static List e(ImageOutputConfig _this) {
        return (List) _this.retrieveOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS);
    }
}
