package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraFilter;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.ExperimentalCameraFilter;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

@OptIn(markerClass = {ExperimentalCameraFilter.class})
public class LensFacingCameraFilter implements CameraFilter {
    private int mLensFacing;

    public LensFacingCameraFilter(int lensFacing) {
        this.mLensFacing = lensFacing;
    }

    @NonNull
    public List<CameraInfo> filter(@NonNull List<CameraInfo> cameraInfos) {
        List<CameraInfo> result = new ArrayList<>();
        for (CameraInfo cameraInfo : cameraInfos) {
            Preconditions.checkArgument(cameraInfo instanceof CameraInfoInternal, "The camera info doesn't contain internal implementation.");
            Integer lensFacing = ((CameraInfoInternal) cameraInfo).getLensFacing();
            if (lensFacing != null && lensFacing.intValue() == this.mLensFacing) {
                result.add(cameraInfo);
            }
        }
        return result;
    }

    public int getLensFacing() {
        return this.mLensFacing;
    }
}
