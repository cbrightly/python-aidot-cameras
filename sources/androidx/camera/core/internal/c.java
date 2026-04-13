package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: TargetConfig */
public final /* synthetic */ class c {
    @Nullable
    public static Class b(@Nullable TargetConfig _this, Class valueIfMissing) {
        return (Class) _this.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, valueIfMissing);
    }

    @NonNull
    public static Class a(TargetConfig _this) {
        return (Class) _this.retrieveOption(TargetConfig.OPTION_TARGET_CLASS);
    }

    @Nullable
    public static String d(@Nullable TargetConfig _this, String valueIfMissing) {
        return (String) _this.retrieveOption(TargetConfig.OPTION_TARGET_NAME, valueIfMissing);
    }

    @NonNull
    public static String c(TargetConfig _this) {
        return (String) _this.retrieveOption(TargetConfig.OPTION_TARGET_NAME);
    }
}
