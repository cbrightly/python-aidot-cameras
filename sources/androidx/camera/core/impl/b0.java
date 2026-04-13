package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.SessionConfig;
import androidx.core.util.Consumer;

/* compiled from: UseCaseConfig */
public final /* synthetic */ class b0 {
    @Nullable
    public static SessionConfig j(@Nullable UseCaseConfig _this, SessionConfig valueIfMissing) {
        return (SessionConfig) _this.retrieveOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, valueIfMissing);
    }

    @NonNull
    public static SessionConfig i(UseCaseConfig _this) {
        return (SessionConfig) _this.retrieveOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG);
    }

    @Nullable
    public static CaptureConfig h(@Nullable UseCaseConfig _this, CaptureConfig valueIfMissing) {
        return (CaptureConfig) _this.retrieveOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG, valueIfMissing);
    }

    @NonNull
    public static CaptureConfig g(UseCaseConfig _this) {
        return (CaptureConfig) _this.retrieveOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG);
    }

    @Nullable
    public static SessionConfig.OptionUnpacker l(@Nullable UseCaseConfig _this, SessionConfig.OptionUnpacker valueIfMissing) {
        return (SessionConfig.OptionUnpacker) _this.retrieveOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, valueIfMissing);
    }

    @NonNull
    public static SessionConfig.OptionUnpacker k(UseCaseConfig _this) {
        return (SessionConfig.OptionUnpacker) _this.retrieveOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER);
    }

    @Nullable
    public static CaptureConfig.OptionUnpacker f(@Nullable UseCaseConfig _this, CaptureConfig.OptionUnpacker valueIfMissing) {
        return (CaptureConfig.OptionUnpacker) _this.retrieveOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER, valueIfMissing);
    }

    @NonNull
    public static CaptureConfig.OptionUnpacker e(UseCaseConfig _this) {
        return (CaptureConfig.OptionUnpacker) _this.retrieveOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER);
    }

    public static int n(UseCaseConfig _this, int valueIfMissing) {
        return ((Integer) _this.retrieveOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(valueIfMissing))).intValue();
    }

    public static int m(UseCaseConfig _this) {
        return ((Integer) _this.retrieveOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY)).intValue();
    }

    @Nullable
    public static CameraSelector d(@Nullable UseCaseConfig _this, CameraSelector valueIfMissing) {
        return (CameraSelector) _this.retrieveOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, valueIfMissing);
    }

    @NonNull
    public static CameraSelector c(UseCaseConfig _this) {
        return (CameraSelector) _this.retrieveOption(UseCaseConfig.OPTION_CAMERA_SELECTOR);
    }

    @Nullable
    public static Consumer b(@Nullable UseCaseConfig _this, Consumer valueIfMissing) {
        return (Consumer) _this.retrieveOption(UseCaseConfig.OPTION_ATTACHED_USE_CASES_UPDATE_LISTENER, valueIfMissing);
    }

    @NonNull
    public static Consumer a(UseCaseConfig _this) {
        return (Consumer) _this.retrieveOption(UseCaseConfig.OPTION_ATTACHED_USE_CASES_UPDATE_LISTENER);
    }
}
