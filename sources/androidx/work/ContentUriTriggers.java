package androidx.work;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.HashSet;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class ContentUriTriggers {
    private final Set<Trigger> mTriggers = new HashSet();

    public void add(@NonNull Uri uri, boolean triggerForDescendants) {
        this.mTriggers.add(new Trigger(uri, triggerForDescendants));
    }

    @NonNull
    public Set<Trigger> getTriggers() {
        return this.mTriggers;
    }

    public int size() {
        return this.mTriggers.size();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.mTriggers.equals(((ContentUriTriggers) o).mTriggers);
    }

    public int hashCode() {
        return this.mTriggers.hashCode();
    }

    public static final class Trigger {
        private final boolean mTriggerForDescendants;
        @NonNull
        private final Uri mUri;

        Trigger(@NonNull Uri uri, boolean triggerForDescendants) {
            this.mUri = uri;
            this.mTriggerForDescendants = triggerForDescendants;
        }

        @NonNull
        public Uri getUri() {
            return this.mUri;
        }

        public boolean shouldTriggerForDescendants() {
            return this.mTriggerForDescendants;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Trigger trigger = (Trigger) o;
            if (this.mTriggerForDescendants != trigger.mTriggerForDescendants || !this.mUri.equals(trigger.mUri)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.mUri.hashCode() * 31) + (this.mTriggerForDescendants ? 1 : 0);
        }
    }
}
