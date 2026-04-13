package androidx.camera.view.video;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.auto.value.AutoValue;

@AutoValue
@ExperimentalVideo
public abstract class OutputFileResults {
    @Nullable
    public abstract Uri getSavedUri();

    OutputFileResults() {
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static OutputFileResults create(@Nullable Uri savedUri) {
        return new AutoValue_OutputFileResults(savedUri);
    }
}
