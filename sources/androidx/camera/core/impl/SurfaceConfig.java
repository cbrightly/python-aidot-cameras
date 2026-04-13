package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SurfaceConfig {

    public enum ConfigType {
        PRIV,
        YUV,
        JPEG,
        RAW
    }

    @NonNull
    public abstract ConfigSize getConfigSize();

    @NonNull
    public abstract ConfigType getConfigType();

    SurfaceConfig() {
    }

    @NonNull
    public static SurfaceConfig create(@NonNull ConfigType type, @NonNull ConfigSize size) {
        return new AutoValue_SurfaceConfig(type, size);
    }

    public final boolean isSupported(@NonNull SurfaceConfig surfaceConfig) {
        ConfigType configType = surfaceConfig.getConfigType();
        if (surfaceConfig.getConfigSize().getId() > getConfigSize().getId() || configType != getConfigType()) {
            return false;
        }
        return true;
    }

    public enum ConfigSize {
        ANALYSIS(0),
        PREVIEW(1),
        RECORD(2),
        MAXIMUM(3),
        NOT_SUPPORT(4);
        
        final int mId;

        private ConfigSize(int id) {
            this.mId = id;
        }

        /* access modifiers changed from: package-private */
        public int getId() {
            return this.mId;
        }
    }
}
