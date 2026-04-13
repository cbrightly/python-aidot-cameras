package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.TagBundle;

public final class AutoValue_ImmutableImageInfo extends ImmutableImageInfo {
    private final int rotationDegrees;
    private final TagBundle tagBundle;
    private final long timestamp;

    AutoValue_ImmutableImageInfo(TagBundle tagBundle2, long timestamp2, int rotationDegrees2) {
        if (tagBundle2 != null) {
            this.tagBundle = tagBundle2;
            this.timestamp = timestamp2;
            this.rotationDegrees = rotationDegrees2;
            return;
        }
        throw new NullPointerException("Null tagBundle");
    }

    @NonNull
    public TagBundle getTagBundle() {
        return this.tagBundle;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public int getRotationDegrees() {
        return this.rotationDegrees;
    }

    public String toString() {
        return "ImmutableImageInfo{tagBundle=" + this.tagBundle + ", timestamp=" + this.timestamp + ", rotationDegrees=" + this.rotationDegrees + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImmutableImageInfo)) {
            return false;
        }
        ImmutableImageInfo that = (ImmutableImageInfo) o;
        if (this.tagBundle.equals(that.getTagBundle()) && this.timestamp == that.getTimestamp() && this.rotationDegrees == that.getRotationDegrees()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        long j = this.timestamp;
        return (((((1 * 1000003) ^ this.tagBundle.hashCode()) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ this.rotationDegrees;
    }
}
