package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.TagBundle;
import androidx.camera.core.impl.utils.ExifData;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ImmutableImageInfo implements ImageInfo {
    public abstract int getRotationDegrees();

    @NonNull
    public abstract TagBundle getTagBundle();

    public abstract long getTimestamp();

    ImmutableImageInfo() {
    }

    public static ImageInfo create(@NonNull TagBundle tag, long timestamp, int rotationDegrees) {
        return new AutoValue_ImmutableImageInfo(tag, timestamp, rotationDegrees);
    }

    public void populateExifData(@NonNull ExifData.Builder exifBuilder) {
        exifBuilder.setOrientationDegrees(getRotationDegrees());
    }
}
