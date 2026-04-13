package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.SurfaceConfig;

public final class AutoValue_SurfaceConfig extends SurfaceConfig {
    private final SurfaceConfig.ConfigSize configSize;
    private final SurfaceConfig.ConfigType configType;

    AutoValue_SurfaceConfig(SurfaceConfig.ConfigType configType2, SurfaceConfig.ConfigSize configSize2) {
        if (configType2 != null) {
            this.configType = configType2;
            if (configSize2 != null) {
                this.configSize = configSize2;
                return;
            }
            throw new NullPointerException("Null configSize");
        }
        throw new NullPointerException("Null configType");
    }

    @NonNull
    public SurfaceConfig.ConfigType getConfigType() {
        return this.configType;
    }

    @NonNull
    public SurfaceConfig.ConfigSize getConfigSize() {
        return this.configSize;
    }

    public String toString() {
        return "SurfaceConfig{configType=" + this.configType + ", configSize=" + this.configSize + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SurfaceConfig)) {
            return false;
        }
        SurfaceConfig that = (SurfaceConfig) o;
        if (!this.configType.equals(that.getConfigType()) || !this.configSize.equals(that.getConfigSize())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.configType.hashCode()) * 1000003) ^ this.configSize.hashCode();
    }
}
