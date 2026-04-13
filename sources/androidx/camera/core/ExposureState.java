package androidx.camera.core;

import android.util.Range;
import android.util.Rational;
import androidx.annotation.NonNull;

@ExperimentalExposureCompensation
public interface ExposureState {
    int getExposureCompensationIndex();

    @NonNull
    Range<Integer> getExposureCompensationRange();

    @NonNull
    Rational getExposureCompensationStep();

    boolean isExposureCompensationSupported();
}
