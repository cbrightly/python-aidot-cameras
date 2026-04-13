package androidx.camera.view.transform;

import android.content.ContentResolver;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.utils.Exif;
import androidx.camera.view.TransformExperimental;
import androidx.camera.view.TransformUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@TransformExperimental
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class FileTransformFactory {
    private final boolean mUseExifOrientation;

    FileTransformFactory(boolean useExifOrientation) {
        this.mUseExifOrientation = useExifOrientation;
    }

    @NonNull
    public OutputTransform getOutputTransform(@NonNull ContentResolver contentResolver, @NonNull Uri uri) {
        InputStream inputStream = contentResolver.openInputStream(uri);
        try {
            OutputTransform outputTransform = getOutputTransform(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
            return outputTransform;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    @NonNull
    public OutputTransform getOutputTransform(@NonNull File file) {
        InputStream inputStream = new FileInputStream(file);
        try {
            OutputTransform outputTransform = getOutputTransform(inputStream);
            inputStream.close();
            return outputTransform;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    @NonNull
    public OutputTransform getOutputTransform(@NonNull InputStream inputStream) {
        Exif exif = Exif.createFromInputStream(inputStream);
        Rect cropRect = new Rect(0, 0, exif.getWidth(), exif.getHeight());
        Matrix matrix = TransformUtils.getNormalizedToBuffer(cropRect);
        if (this.mUseExifOrientation) {
            matrix.postConcat(TransformUtils.getExifTransform(exif.getOrientation(), exif.getWidth(), exif.getHeight()));
        }
        return new OutputTransform(matrix, TransformUtils.rectToSize(cropRect));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class Builder {
        boolean mUseExifOrientation = false;

        @NonNull
        public FileTransformFactory build() {
            return new FileTransformFactory(this.mUseExifOrientation);
        }

        @NonNull
        public Builder setUseExifOrientation(boolean useExifOrientation) {
            this.mUseExifOrientation = useExifOrientation;
            return this;
        }
    }
}
