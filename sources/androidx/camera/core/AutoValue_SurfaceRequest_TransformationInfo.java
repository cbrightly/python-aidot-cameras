package androidx.camera.core;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.camera.core.SurfaceRequest;

public final class AutoValue_SurfaceRequest_TransformationInfo extends SurfaceRequest.TransformationInfo {
    private final Rect cropRect;
    private final int rotationDegrees;
    private final int targetRotation;

    AutoValue_SurfaceRequest_TransformationInfo(Rect cropRect2, int rotationDegrees2, int targetRotation2) {
        if (cropRect2 != null) {
            this.cropRect = cropRect2;
            this.rotationDegrees = rotationDegrees2;
            this.targetRotation = targetRotation2;
            return;
        }
        throw new NullPointerException("Null cropRect");
    }

    @NonNull
    public Rect getCropRect() {
        return this.cropRect;
    }

    public int getRotationDegrees() {
        return this.rotationDegrees;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getTargetRotation() {
        return this.targetRotation;
    }

    public String toString() {
        return "TransformationInfo{cropRect=" + this.cropRect + ", rotationDegrees=" + this.rotationDegrees + ", targetRotation=" + this.targetRotation + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SurfaceRequest.TransformationInfo)) {
            return false;
        }
        SurfaceRequest.TransformationInfo that = (SurfaceRequest.TransformationInfo) o;
        if (this.cropRect.equals(that.getCropRect()) && this.rotationDegrees == that.getRotationDegrees() && this.targetRotation == that.getTargetRotation()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((1 * 1000003) ^ this.cropRect.hashCode()) * 1000003) ^ this.rotationDegrees) * 1000003) ^ this.targetRotation;
    }
}
