package androidx.camera.view.video;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.video.OutputFileOptions;
import java.io.File;

public final class AutoValue_OutputFileOptions extends OutputFileOptions {
    private final ContentResolver contentResolver;
    private final ContentValues contentValues;
    private final File file;
    private final ParcelFileDescriptor fileDescriptor;
    private final Metadata metadata;
    private final Uri saveCollection;

    private AutoValue_OutputFileOptions(@Nullable File file2, @Nullable ParcelFileDescriptor fileDescriptor2, @Nullable ContentResolver contentResolver2, @Nullable Uri saveCollection2, @Nullable ContentValues contentValues2, Metadata metadata2) {
        this.file = file2;
        this.fileDescriptor = fileDescriptor2;
        this.contentResolver = contentResolver2;
        this.saveCollection = saveCollection2;
        this.contentValues = contentValues2;
        this.metadata = metadata2;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public File getFile() {
        return this.file;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ParcelFileDescriptor getFileDescriptor() {
        return this.fileDescriptor;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ContentResolver getContentResolver() {
        return this.contentResolver;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Uri getSaveCollection() {
        return this.saveCollection;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ContentValues getContentValues() {
        return this.contentValues;
    }

    @NonNull
    public Metadata getMetadata() {
        return this.metadata;
    }

    public String toString() {
        return "OutputFileOptions{file=" + this.file + ", fileDescriptor=" + this.fileDescriptor + ", contentResolver=" + this.contentResolver + ", saveCollection=" + this.saveCollection + ", contentValues=" + this.contentValues + ", metadata=" + this.metadata + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OutputFileOptions)) {
            return false;
        }
        OutputFileOptions that = (OutputFileOptions) o;
        File file2 = this.file;
        if (file2 != null ? file2.equals(that.getFile()) : that.getFile() == null) {
            ParcelFileDescriptor parcelFileDescriptor = this.fileDescriptor;
            if (parcelFileDescriptor != null ? parcelFileDescriptor.equals(that.getFileDescriptor()) : that.getFileDescriptor() == null) {
                ContentResolver contentResolver2 = this.contentResolver;
                if (contentResolver2 != null ? contentResolver2.equals(that.getContentResolver()) : that.getContentResolver() == null) {
                    Uri uri = this.saveCollection;
                    if (uri != null ? uri.equals(that.getSaveCollection()) : that.getSaveCollection() == null) {
                        ContentValues contentValues2 = this.contentValues;
                        if (contentValues2 != null ? contentValues2.equals(that.getContentValues()) : that.getContentValues() == null) {
                            if (this.metadata.equals(that.getMetadata())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1 * 1000003;
        File file2 = this.file;
        int i = 0;
        int h$2 = (h$ ^ (file2 == null ? 0 : file2.hashCode())) * 1000003;
        ParcelFileDescriptor parcelFileDescriptor = this.fileDescriptor;
        int h$3 = (h$2 ^ (parcelFileDescriptor == null ? 0 : parcelFileDescriptor.hashCode())) * 1000003;
        ContentResolver contentResolver2 = this.contentResolver;
        int h$4 = (h$3 ^ (contentResolver2 == null ? 0 : contentResolver2.hashCode())) * 1000003;
        Uri uri = this.saveCollection;
        int h$5 = (h$4 ^ (uri == null ? 0 : uri.hashCode())) * 1000003;
        ContentValues contentValues2 = this.contentValues;
        if (contentValues2 != null) {
            i = contentValues2.hashCode();
        }
        return ((h$5 ^ i) * 1000003) ^ this.metadata.hashCode();
    }

    public static final class Builder extends OutputFileOptions.Builder {
        private ContentResolver contentResolver;
        private ContentValues contentValues;
        private File file;
        private ParcelFileDescriptor fileDescriptor;
        private Metadata metadata;
        private Uri saveCollection;

        Builder() {
        }

        /* access modifiers changed from: package-private */
        public OutputFileOptions.Builder setFile(@Nullable File file2) {
            this.file = file2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public OutputFileOptions.Builder setFileDescriptor(@Nullable ParcelFileDescriptor fileDescriptor2) {
            this.fileDescriptor = fileDescriptor2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public OutputFileOptions.Builder setContentResolver(@Nullable ContentResolver contentResolver2) {
            this.contentResolver = contentResolver2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public OutputFileOptions.Builder setSaveCollection(@Nullable Uri saveCollection2) {
            this.saveCollection = saveCollection2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public OutputFileOptions.Builder setContentValues(@Nullable ContentValues contentValues2) {
            this.contentValues = contentValues2;
            return this;
        }

        public OutputFileOptions.Builder setMetadata(Metadata metadata2) {
            if (metadata2 != null) {
                this.metadata = metadata2;
                return this;
            }
            throw new NullPointerException("Null metadata");
        }

        public OutputFileOptions build() {
            String missing = "";
            if (this.metadata == null) {
                missing = missing + " metadata";
            }
            if (missing.isEmpty()) {
                return new AutoValue_OutputFileOptions(this.file, this.fileDescriptor, this.contentResolver, this.saveCollection, this.contentValues, this.metadata);
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
