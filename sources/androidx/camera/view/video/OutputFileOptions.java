package androidx.camera.view.video;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.camera.core.VideoCapture;
import androidx.camera.view.video.AutoValue_OutputFileOptions;
import androidx.core.util.Preconditions;
import com.google.auto.value.AutoValue;
import java.io.File;

@AutoValue
@ExperimentalVideo
public abstract class OutputFileOptions {
    private static final Metadata EMPTY_METADATA = Metadata.builder().build();

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract ContentResolver getContentResolver();

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract ContentValues getContentValues();

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract File getFile();

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract ParcelFileDescriptor getFileDescriptor();

    @NonNull
    public abstract Metadata getMetadata();

    /* access modifiers changed from: package-private */
    @Nullable
    public abstract Uri getSaveCollection();

    OutputFileOptions() {
    }

    @NonNull
    public static Builder builder(@NonNull File file) {
        return new AutoValue_OutputFileOptions.Builder().setMetadata(EMPTY_METADATA).setFile(file);
    }

    @NonNull
    public static Builder builder(@NonNull ParcelFileDescriptor fileDescriptor) {
        Preconditions.checkArgument(Build.VERSION.SDK_INT >= 26, "Using a ParcelFileDescriptor to record a video is only supported for Android 8.0 or above.");
        return new AutoValue_OutputFileOptions.Builder().setMetadata(EMPTY_METADATA).setFileDescriptor(fileDescriptor);
    }

    @NonNull
    public static Builder builder(@NonNull ContentResolver contentResolver, @NonNull Uri saveCollection, @NonNull ContentValues contentValues) {
        return new AutoValue_OutputFileOptions.Builder().setMetadata(EMPTY_METADATA).setContentResolver(contentResolver).setSaveCollection(saveCollection).setContentValues(contentValues);
    }

    private boolean isSavingToMediaStore() {
        return (getSaveCollection() == null || getContentResolver() == null || getContentValues() == null) ? false : true;
    }

    private boolean isSavingToFile() {
        return getFile() != null;
    }

    private boolean isSavingToFileDescriptor() {
        return getFileDescriptor() != null;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public VideoCapture.OutputFileOptions toVideoCaptureOutputFileOptions() {
        VideoCapture.OutputFileOptions.Builder internalOutputFileOptionsBuilder;
        if (isSavingToFile()) {
            internalOutputFileOptionsBuilder = new VideoCapture.OutputFileOptions.Builder((File) Preconditions.checkNotNull(getFile()));
        } else if (isSavingToFileDescriptor()) {
            internalOutputFileOptionsBuilder = new VideoCapture.OutputFileOptions.Builder(((ParcelFileDescriptor) Preconditions.checkNotNull(getFileDescriptor())).getFileDescriptor());
        } else {
            Preconditions.checkState(isSavingToMediaStore());
            internalOutputFileOptionsBuilder = new VideoCapture.OutputFileOptions.Builder((ContentResolver) Preconditions.checkNotNull(getContentResolver()), (Uri) Preconditions.checkNotNull(getSaveCollection()), (ContentValues) Preconditions.checkNotNull(getContentValues()));
        }
        VideoCapture.Metadata internalMetadata = new VideoCapture.Metadata();
        internalMetadata.location = getMetadata().getLocation();
        internalOutputFileOptionsBuilder.setMetadata(internalMetadata);
        return internalOutputFileOptionsBuilder.build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        @NonNull
        public abstract OutputFileOptions build();

        /* access modifiers changed from: package-private */
        public abstract Builder setContentResolver(@Nullable ContentResolver contentResolver);

        /* access modifiers changed from: package-private */
        public abstract Builder setContentValues(@Nullable ContentValues contentValues);

        /* access modifiers changed from: package-private */
        public abstract Builder setFile(@Nullable File file);

        /* access modifiers changed from: package-private */
        public abstract Builder setFileDescriptor(@Nullable ParcelFileDescriptor parcelFileDescriptor);

        @NonNull
        public abstract Builder setMetadata(@NonNull Metadata metadata);

        /* access modifiers changed from: package-private */
        public abstract Builder setSaveCollection(@Nullable Uri uri);

        Builder() {
        }
    }
}
