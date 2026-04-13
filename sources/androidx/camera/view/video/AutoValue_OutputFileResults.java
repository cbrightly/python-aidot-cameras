package androidx.camera.view.video;

import android.net.Uri;
import androidx.annotation.Nullable;

public final class AutoValue_OutputFileResults extends OutputFileResults {
    private final Uri savedUri;

    AutoValue_OutputFileResults(@Nullable Uri savedUri2) {
        this.savedUri = savedUri2;
    }

    @Nullable
    public Uri getSavedUri() {
        return this.savedUri;
    }

    public String toString() {
        return "OutputFileResults{savedUri=" + this.savedUri + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OutputFileResults)) {
            return false;
        }
        OutputFileResults that = (OutputFileResults) o;
        Uri uri = this.savedUri;
        if (uri != null) {
            return uri.equals(that.getSavedUri());
        }
        if (that.getSavedUri() == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1 * 1000003;
        Uri uri = this.savedUri;
        return h$ ^ (uri == null ? 0 : uri.hashCode());
    }
}
