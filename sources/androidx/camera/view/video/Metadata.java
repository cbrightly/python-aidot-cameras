package androidx.camera.view.video;

import android.location.Location;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.video.AutoValue_Metadata;
import com.google.auto.value.AutoValue;

@AutoValue
@ExperimentalVideo
public abstract class Metadata {
    @Nullable
    public abstract Location getLocation();

    @NonNull
    public static Builder builder() {
        return new AutoValue_Metadata.Builder();
    }

    Metadata() {
    }

    @AutoValue.Builder
    public static abstract class Builder {
        @NonNull
        public abstract Metadata build();

        @NonNull
        public abstract Builder setLocation(@Nullable Location location);

        Builder() {
        }
    }
}
