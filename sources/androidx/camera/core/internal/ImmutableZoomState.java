package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.camera.core.ZoomState;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ImmutableZoomState implements ZoomState {
    public abstract float getLinearZoom();

    public abstract float getMaxZoomRatio();

    public abstract float getMinZoomRatio();

    public abstract float getZoomRatio();

    @NonNull
    public static ZoomState create(float zoomRatio, float maxZoomRatio, float minZoomRatio, float linearZoom) {
        return new AutoValue_ImmutableZoomState(zoomRatio, maxZoomRatio, minZoomRatio, linearZoom);
    }

    @NonNull
    public static ZoomState create(@NonNull ZoomState zoomState) {
        return new AutoValue_ImmutableZoomState(zoomState.getZoomRatio(), zoomState.getMaxZoomRatio(), zoomState.getMinZoomRatio(), zoomState.getLinearZoom());
    }
}
