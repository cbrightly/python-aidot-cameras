package com.google.android.gms.common.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class TelemetryLoggingOptions implements Api.ApiOptions.Optional {
    @NonNull
    public static final TelemetryLoggingOptions zaa = builder().build();
    @Nullable
    private final String zab;

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static class Builder {
        @Nullable
        private String zaa;

        private Builder() {
        }

        /* synthetic */ Builder(zaac zaac) {
        }

        @NonNull
        @KeepForSdk
        public TelemetryLoggingOptions build() {
            return new TelemetryLoggingOptions(this.zaa, (zaad) null);
        }

        @NonNull
        @KeepForSdk
        @CanIgnoreReturnValue
        public Builder setApi(@Nullable String str) {
            this.zaa = str;
            return this;
        }
    }

    /* synthetic */ TelemetryLoggingOptions(String str, zaad zaad) {
        this.zab = str;
    }

    @NonNull
    @KeepForSdk
    public static Builder builder() {
        return new Builder((zaac) null);
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TelemetryLoggingOptions)) {
            return false;
        }
        return Objects.equal(this.zab, ((TelemetryLoggingOptions) obj).zab);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zab);
    }

    @NonNull
    public final Bundle zaa() {
        Bundle bundle = new Bundle();
        String str = this.zab;
        if (str != null) {
            bundle.putString("api", str);
        }
        return bundle;
    }
}
