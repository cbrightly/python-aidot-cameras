package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.TagBundle;
import androidx.camera.core.impl.utils.ExifData;

public interface ImageInfo {
    int getRotationDegrees();

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    TagBundle getTagBundle();

    long getTimestamp();

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    void populateExifData(@NonNull ExifData.Builder builder);
}
