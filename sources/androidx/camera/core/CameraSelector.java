package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.LensFacingCameraFilter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public final class CameraSelector {
    @NonNull
    public static final CameraSelector DEFAULT_BACK_CAMERA = new Builder().requireLensFacing(1).build();
    @NonNull
    public static final CameraSelector DEFAULT_FRONT_CAMERA = new Builder().requireLensFacing(0).build();
    public static final int LENS_FACING_BACK = 1;
    public static final int LENS_FACING_FRONT = 0;
    private LinkedHashSet<CameraFilter> mCameraFilterSet;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LensFacing {
    }

    CameraSelector(LinkedHashSet<CameraFilter> cameraFilterSet) {
        this.mCameraFilterSet = cameraFilterSet;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraInternal select(@NonNull LinkedHashSet<CameraInternal> cameras) {
        return (CameraInternal) filter(cameras).iterator().next();
    }

    @OptIn(markerClass = {ExperimentalCameraFilter.class})
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @NonNull
    public List<CameraInfo> filter(@NonNull List<CameraInfo> cameraInfos) {
        List<CameraInfo> input = new ArrayList<>(cameraInfos);
        List<CameraInfo> output = new ArrayList<>(cameraInfos);
        Iterator it = this.mCameraFilterSet.iterator();
        while (it.hasNext()) {
            output = ((CameraFilter) it.next()).filter(Collections.unmodifiableList(output));
            if (output.isEmpty()) {
                throw new IllegalArgumentException("No available camera can be found.");
            } else if (input.containsAll(output)) {
                input.retainAll(output);
            } else {
                throw new IllegalArgumentException("The output isn't contained in the input.");
            }
        }
        return output;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public LinkedHashSet<CameraInternal> filter(@NonNull LinkedHashSet<CameraInternal> cameras) {
        List<CameraInfo> input = new ArrayList<>();
        Iterator it = cameras.iterator();
        while (it.hasNext()) {
            input.add(((CameraInternal) it.next()).getCameraInfo());
        }
        List<CameraInfo> result = filter(input);
        LinkedHashSet<CameraInternal> output = new LinkedHashSet<>();
        Iterator it2 = cameras.iterator();
        while (it2.hasNext()) {
            CameraInternal camera = (CameraInternal) it2.next();
            if (result.contains(camera.getCameraInfo())) {
                output.add(camera);
            }
        }
        return output;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public LinkedHashSet<CameraFilter> getCameraFilterSet() {
        return this.mCameraFilterSet;
    }

    @OptIn(markerClass = {ExperimentalCameraFilter.class})
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Integer getLensFacing() {
        Integer currentLensFacing = null;
        Iterator it = this.mCameraFilterSet.iterator();
        while (it.hasNext()) {
            CameraFilter filter = (CameraFilter) it.next();
            if (filter instanceof LensFacingCameraFilter) {
                Integer newLensFacing = Integer.valueOf(((LensFacingCameraFilter) filter).getLensFacing());
                if (currentLensFacing == null) {
                    currentLensFacing = newLensFacing;
                } else if (!currentLensFacing.equals(newLensFacing)) {
                    throw new IllegalStateException("Multiple conflicting lens facing requirements exist.");
                }
            }
        }
        return currentLensFacing;
    }

    public static final class Builder {
        private final LinkedHashSet<CameraFilter> mCameraFilterSet;

        public Builder() {
            this.mCameraFilterSet = new LinkedHashSet<>();
        }

        private Builder(@NonNull LinkedHashSet<CameraFilter> cameraFilterSet) {
            this.mCameraFilterSet = new LinkedHashSet<>(cameraFilterSet);
        }

        @NonNull
        @OptIn(markerClass = {ExperimentalCameraFilter.class})
        public Builder requireLensFacing(int lensFacing) {
            this.mCameraFilterSet.add(new LensFacingCameraFilter(lensFacing));
            return this;
        }

        @NonNull
        @ExperimentalCameraFilter
        public Builder addCameraFilter(@NonNull CameraFilter cameraFilter) {
            this.mCameraFilterSet.add(cameraFilter);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static Builder fromSelector(@NonNull CameraSelector cameraSelector) {
            return new Builder(cameraSelector.getCameraFilterSet());
        }

        @NonNull
        public CameraSelector build() {
            return new CameraSelector(this.mCameraFilterSet);
        }
    }
}
