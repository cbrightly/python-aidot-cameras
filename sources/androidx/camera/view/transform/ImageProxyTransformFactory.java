package androidx.camera.view.transform;

import android.graphics.Matrix;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.camera.core.ImageProxy;
import androidx.camera.view.TransformExperimental;
import androidx.camera.view.TransformUtils;

@TransformExperimental
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ImageProxyTransformFactory {
    private final boolean mUseCropRect;
    private final boolean mUseRotationDegrees;

    ImageProxyTransformFactory(boolean useCropRect, boolean useRotationDegrees) {
        this.mUseCropRect = useCropRect;
        this.mUseRotationDegrees = useRotationDegrees;
    }

    @NonNull
    public OutputTransform getOutputTransform(@NonNull ImageProxy imageProxy) {
        int rotationDegrees = getRotationDegrees(imageProxy);
        RectF source = getCropRect(imageProxy);
        Matrix matrix = TransformUtils.getRectToRect(source, getRotatedCropRect(source, rotationDegrees), rotationDegrees);
        matrix.preConcat(TransformUtils.getNormalizedToBuffer(imageProxy.getCropRect()));
        return new OutputTransform(matrix, TransformUtils.rectToSize(imageProxy.getCropRect()));
    }

    private RectF getCropRect(@NonNull ImageProxy imageProxy) {
        if (this.mUseCropRect) {
            return new RectF(imageProxy.getCropRect());
        }
        return new RectF(0.0f, 0.0f, (float) imageProxy.getWidth(), (float) imageProxy.getHeight());
    }

    private int getRotationDegrees(@NonNull ImageProxy imageProxy) {
        if (this.mUseRotationDegrees) {
            return imageProxy.getImageInfo().getRotationDegrees();
        }
        return 0;
    }

    static RectF getRotatedCropRect(RectF rect, int rotationDegrees) {
        if (TransformUtils.is90or270(rotationDegrees)) {
            return new RectF(0.0f, 0.0f, rect.height(), rect.width());
        }
        return new RectF(0.0f, 0.0f, rect.width(), rect.height());
    }

    public static class Builder {
        private boolean mUseCropRect = false;
        private boolean mUseRotationDegrees = false;

        @NonNull
        public Builder setUseCropRect(boolean useCropRect) {
            this.mUseCropRect = useCropRect;
            return this;
        }

        @NonNull
        public Builder setUseRotationDegrees(boolean useRotationDegrees) {
            this.mUseRotationDegrees = useRotationDegrees;
            return this;
        }

        @NonNull
        public ImageProxyTransformFactory build() {
            return new ImageProxyTransformFactory(this.mUseCropRect, this.mUseRotationDegrees);
        }
    }
}
