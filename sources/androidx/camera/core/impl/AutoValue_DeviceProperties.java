package androidx.camera.core.impl;

import androidx.annotation.NonNull;

public final class AutoValue_DeviceProperties extends DeviceProperties {
    private final String manufacturer;
    private final String model;
    private final int sdkVersion;

    AutoValue_DeviceProperties(String manufacturer2, String model2, int sdkVersion2) {
        if (manufacturer2 != null) {
            this.manufacturer = manufacturer2;
            if (model2 != null) {
                this.model = model2;
                this.sdkVersion = sdkVersion2;
                return;
            }
            throw new NullPointerException("Null model");
        }
        throw new NullPointerException("Null manufacturer");
    }

    @NonNull
    public String manufacturer() {
        return this.manufacturer;
    }

    @NonNull
    public String model() {
        return this.model;
    }

    public int sdkVersion() {
        return this.sdkVersion;
    }

    public String toString() {
        return "DeviceProperties{manufacturer=" + this.manufacturer + ", model=" + this.model + ", sdkVersion=" + this.sdkVersion + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DeviceProperties)) {
            return false;
        }
        DeviceProperties that = (DeviceProperties) o;
        if (!this.manufacturer.equals(that.manufacturer()) || !this.model.equals(that.model()) || this.sdkVersion != that.sdkVersion()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((1 * 1000003) ^ this.manufacturer.hashCode()) * 1000003) ^ this.model.hashCode()) * 1000003) ^ this.sdkVersion;
    }
}
