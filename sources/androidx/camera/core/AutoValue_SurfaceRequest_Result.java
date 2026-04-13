package androidx.camera.core;

import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.camera.core.SurfaceRequest;

public final class AutoValue_SurfaceRequest_Result extends SurfaceRequest.Result {
    private final int resultCode;
    private final Surface surface;

    AutoValue_SurfaceRequest_Result(int resultCode2, Surface surface2) {
        this.resultCode = resultCode2;
        if (surface2 != null) {
            this.surface = surface2;
            return;
        }
        throw new NullPointerException("Null surface");
    }

    public int getResultCode() {
        return this.resultCode;
    }

    @NonNull
    public Surface getSurface() {
        return this.surface;
    }

    public String toString() {
        return "Result{resultCode=" + this.resultCode + ", surface=" + this.surface + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SurfaceRequest.Result)) {
            return false;
        }
        SurfaceRequest.Result that = (SurfaceRequest.Result) o;
        if (this.resultCode != that.getResultCode() || !this.surface.equals(that.getSurface())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.resultCode) * 1000003) ^ this.surface.hashCode();
    }
}
