package androidx.camera.core.internal;

public final class AutoValue_ImmutableZoomState extends ImmutableZoomState {
    private final float linearZoom;
    private final float maxZoomRatio;
    private final float minZoomRatio;
    private final float zoomRatio;

    AutoValue_ImmutableZoomState(float zoomRatio2, float maxZoomRatio2, float minZoomRatio2, float linearZoom2) {
        this.zoomRatio = zoomRatio2;
        this.maxZoomRatio = maxZoomRatio2;
        this.minZoomRatio = minZoomRatio2;
        this.linearZoom = linearZoom2;
    }

    public float getZoomRatio() {
        return this.zoomRatio;
    }

    public float getMaxZoomRatio() {
        return this.maxZoomRatio;
    }

    public float getMinZoomRatio() {
        return this.minZoomRatio;
    }

    public float getLinearZoom() {
        return this.linearZoom;
    }

    public String toString() {
        return "ImmutableZoomState{zoomRatio=" + this.zoomRatio + ", maxZoomRatio=" + this.maxZoomRatio + ", minZoomRatio=" + this.minZoomRatio + ", linearZoom=" + this.linearZoom + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImmutableZoomState)) {
            return false;
        }
        ImmutableZoomState that = (ImmutableZoomState) o;
        if (Float.floatToIntBits(this.zoomRatio) == Float.floatToIntBits(that.getZoomRatio()) && Float.floatToIntBits(this.maxZoomRatio) == Float.floatToIntBits(that.getMaxZoomRatio()) && Float.floatToIntBits(this.minZoomRatio) == Float.floatToIntBits(that.getMinZoomRatio()) && Float.floatToIntBits(this.linearZoom) == Float.floatToIntBits(that.getLinearZoom())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((1 * 1000003) ^ Float.floatToIntBits(this.zoomRatio)) * 1000003) ^ Float.floatToIntBits(this.maxZoomRatio)) * 1000003) ^ Float.floatToIntBits(this.minZoomRatio)) * 1000003) ^ Float.floatToIntBits(this.linearZoom);
    }
}
