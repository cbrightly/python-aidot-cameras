package androidx.core.content;

import android.content.LocusId;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

public final class LocusIdCompat {
    private final String mId;
    private final LocusId mWrapped;

    public LocusIdCompat(@NonNull String id) {
        this.mId = (String) Preconditions.checkStringNotEmpty(id, "id cannot be empty");
        if (Build.VERSION.SDK_INT >= 29) {
            this.mWrapped = Api29Impl.create(id);
        } else {
            this.mWrapped = null;
        }
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        int i = 1 * 31;
        String str = this.mId;
        return i + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LocusIdCompat other = (LocusIdCompat) obj;
        String str = this.mId;
        if (str != null) {
            return str.equals(other.mId);
        }
        if (other.mId == null) {
            return true;
        }
        return false;
    }

    @NonNull
    public String toString() {
        return "LocusIdCompat[" + getSanitizedId() + "]";
    }

    @RequiresApi(29)
    @NonNull
    public LocusId toLocusId() {
        return this.mWrapped;
    }

    @RequiresApi(29)
    @NonNull
    public static LocusIdCompat toLocusIdCompat(@NonNull LocusId locusId) {
        Preconditions.checkNotNull(locusId, "locusId cannot be null");
        return new LocusIdCompat((String) Preconditions.checkStringNotEmpty(Api29Impl.getId(locusId), "id cannot be empty"));
    }

    @NonNull
    private String getSanitizedId() {
        int size = this.mId.length();
        return size + "_chars";
    }

    @RequiresApi(29)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @NonNull
        static LocusId create(@NonNull String id) {
            return new LocusId(id);
        }

        @NonNull
        static String getId(@NonNull LocusId obj) {
            return obj.getId();
        }
    }
}
